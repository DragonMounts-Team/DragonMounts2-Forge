package net.dragonmounts.entity.dragon;

import net.dragonmounts.api.IDragonTypified;
import net.dragonmounts.block.HatchableDragonEggBlock;
import net.dragonmounts.config.ServerConfig;
import net.dragonmounts.init.*;
import net.dragonmounts.item.DragonScalesItem;
import net.dragonmounts.network.SShakeDragonEggPacket;
import net.dragonmounts.network.SSyncEggAgePacket;
import net.dragonmounts.registry.DragonType;
import net.dragonmounts.registry.DragonVariant;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.potion.Effects;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static net.dragonmounts.entity.dragon.TameableDragonEntity.AGE_DATA_PARAMETER_KEY;
import static net.dragonmounts.network.DMPacketHandler.CHANNEL;
import static net.dragonmounts.util.EntityUtil.addOrMergeEffect;
import static net.dragonmounts.util.math.MathUtil.TO_RAD_FACTOR;
import static net.dragonmounts.util.math.MathUtil.parseColor;
import static net.minecraftforge.fml.network.PacketDistributor.PLAYER;
import static net.minecraftforge.fml.network.PacketDistributor.TRACKING_ENTITY;

public class HatchableDragonEggEntity extends LivingEntity implements IDragonTypified.Mutable {
    protected static final DataParameter<DragonType> DATA_DRAGON_TYPE = EntityDataManager.defineId(HatchableDragonEggEntity.class, DragonType.REGISTRY);
    private static final float EGG_CRACK_PROCESS_THRESHOLD = 0.9F;
    private static final float EGG_SHAKE_PROCESS_THRESHOLD = 0.75F;
    private static final float EGG_SHAKE_BASE_CHANCE = 20F;
    public static final int MIN_HATCHING_TIME = 36000;
    public static final int EGG_CRACK_THRESHOLD = (int) (EGG_CRACK_PROCESS_THRESHOLD * MIN_HATCHING_TIME);
    public static final int EGG_SHAKE_THRESHOLD = (int) (EGG_SHAKE_PROCESS_THRESHOLD * MIN_HATCHING_TIME);
    public final Predicate<Entity> pushablePredicate = (entity) -> !entity.isSpectator() && entity.isPushable() && !entity.hasPassenger(this);
    protected String variant;
    protected UUID owner;
    protected float rotationAxis = 0;
    protected float amplitude = 0;
    protected float amplitudeO = 0;
    protected int shaking = 0;
    protected int age = 0;
    protected boolean hatched = false;

    public HatchableDragonEggEntity(EntityType<? extends HatchableDragonEggEntity> type, World world) {
        super(type, world);
        this.pushthrough = 0.625F;
        //noinspection DataFlowIssue
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(world.getGameRules().getRule(DMGameRules.DRAGON_BASE_HEALTH).get());
    }

    public HatchableDragonEggEntity(World world) {
        this(DMEntities.HATCHABLE_DRAGON_EGG.get(), world);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, DMGameRules.DEFAULT_DRAGON_BASE_HEALTH)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_DRAGON_TYPE, DragonTypes.ENDER);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT tag) {
        super.addAdditionalSaveData(tag);
        ResourceLocation key = DragonType.REGISTRY.getKey(this.entityData.get(DATA_DRAGON_TYPE));
        tag.putString(DragonType.DATA_PARAMETER_KEY, (key == null ? DragonType.DEFAULT_KEY : key).toString());
        tag.putInt(AGE_DATA_PARAMETER_KEY, this.age);
        if (this.owner != null) {
            tag.putUUID("Owner", this.owner);
        }
        if (this.variant != null) {
            tag.putString(DragonVariant.DATA_PARAMETER_KEY, this.variant);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains(DragonType.DATA_PARAMETER_KEY)) {
            this.setDragonType(DragonType.byName(tag.getString(DragonType.DATA_PARAMETER_KEY)), false);
        }
        if (tag.contains(DragonVariant.DATA_PARAMETER_KEY)) {
            this.variant = tag.getString(DragonVariant.DATA_PARAMETER_KEY);
        }
        if (tag.contains(AGE_DATA_PARAMETER_KEY)) {
            this.setAge(tag.getInt(AGE_DATA_PARAMETER_KEY), true);
        }
        if (tag.hasUUID("Owner")) {
            this.owner = tag.getUUID("Owner");
        } else {
            MinecraftServer server = this.getServer();
            if (server != null) {
                this.owner = PreYggdrasilConverter.convertMobOwnerIfNecessary(server, tag.getString("Owner"));
            }
        }
    }

    protected void spawnScales(int amount) {
        if (amount > 0) {
            DragonScalesItem scales = this.getDragonType().getInstance(DragonScalesItem.class, null);
            if (scales != null && this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
                this.spawnAtLocation(new ItemStack(scales, amount), 1.25F);
            }
        }
    }

    public void hatch() {
        if (!this.level.isClientSide) {
            this.spawnScales(this.random.nextInt(4) + 4);
            this.hatched = true;
        }
        this.remove();
    }

    public boolean isHatched() {
        return this.hatched;
    }

    @Nonnull
    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return Collections.singleton(ItemStack.EMPTY);
    }

    @Nonnull
    @Override
    public ItemStack getItemBySlot(EquipmentSlotType slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlotType slot, @Nullable ItemStack stack) {}

    @Nonnull
    @Override
    public HandSide getMainArm() {
        return HandSide.RIGHT;
    }

    @Nonnull
    @Override
    public ActionResultType interact(PlayerEntity player, Hand hand) {
        if (ServerConfig.INSTANCE.debug.get() && this.isAlive() && player.getItemInHand(hand).getItem() == Items.DEBUG_STICK) {
            if (player.isShiftKeyDown() || this.level.isClientSide) {
                LOGGER.info("amplitude: {};   axis: {}", this.shaking, this.rotationAxis);
            } else {
                Random random = this.random;
                CHANNEL.send(TRACKING_ENTITY.with(this::getEntity), new SShakeDragonEggPacket(
                        this.getId(),
                        this.shaking = random.nextInt(21) + 10,//[10, 30]
                        random.nextInt(180),
                        random.nextBoolean(),
                        this.age > EGG_CRACK_THRESHOLD
                ));
            }
            return ActionResultType.SUCCESS;
        }
        if (this.isAlive() && player.isShiftKeyDown()) {
            HatchableDragonEggBlock block = this.getDragonType().getInstance(HatchableDragonEggBlock.class, null);
            if (block == null) return ActionResultType.FAIL;
            this.remove();
            this.level.setBlockAndUpdate(this.blockPosition(), block.defaultBlockState());
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public void tick() {
        World level = this.level;
        Random random = this.random;
        super.tick();
        if (level.isClientSide) {
            if (--this.shaking > 0) {
                this.amplitudeO = this.amplitude;
                this.amplitude = MathHelper.sin(level.getGameTime() * 0.5F) * Math.min(this.shaking, 15);
            }
            // spawn generic particles
            DragonType type = this.getDragonType();
            double px = getX() + (random.nextDouble() - 0.5);
            double py = getY() + (random.nextDouble() - 0.3);
            double pz = getZ() + (random.nextDouble() - 0.5);
            double ox = (random.nextDouble() - 0.5) * 2;
            double oy = (random.nextDouble() - 0.3) * 2;
            double oz = (random.nextDouble() - 0.5) * 2;
            level.addParticle(type.eggParticle, px, py, pz, ox, oy, oz);
            if ((++this.age & 1) == 0 && type != DragonTypes.ENDER) {
                int color = type.color;
                level.addParticle(new RedstoneParticleData(parseColor(color, 2), parseColor(color, 1), parseColor(color, 0), 1.0F), px, py + 0.8, pz, ox, oy, oz);
            }
            return;
        }
        --this.shaking;
        // play the egg shake animation based on the time the eggs take to hatch
        if (++this.age > EGG_SHAKE_THRESHOLD && this.shaking < 0) {
            float progress = (float) this.age / MIN_HATCHING_TIME;
            // wait until the egg is nearly hatched
            float chance = (progress - EGG_SHAKE_PROCESS_THRESHOLD) / EGG_SHAKE_BASE_CHANCE * (1 - EGG_SHAKE_PROCESS_THRESHOLD);
            if (this.age >= MIN_HATCHING_TIME && random.nextFloat() * 2 < chance) {
                this.hatch();
                return;
            }
            if (random.nextFloat() < chance) {
                boolean crack = progress > EGG_CRACK_PROCESS_THRESHOLD;
                if (crack) {
                    this.spawnScales(1);
                }
                CHANNEL.send(TRACKING_ENTITY.with(this::getEntity), new SShakeDragonEggPacket(
                        this.getId(),
                        this.shaking = random.nextInt(21) + 10,//[10, 30]
                        random.nextInt(180),
                        random.nextBoolean(),
                        crack
                ));
            }
        }
        if (level.getGameRules().getBoolean(DMGameRules.IS_EGG_PUSHABLE)) {
            List<Entity> list = level.getEntities(this, this.getBoundingBox().inflate(0.125F, -0.01F, 0.125F), this.pushablePredicate);
            if (!list.isEmpty()) {
                for (Entity entity : list) {
                    this.push(entity);
                }
            }
        }
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(this.getDragonType().getInstance(HatchableDragonEggBlock.class, DMBlocks.ENDER_DRAGON_EGG));
    }

    @Nonnull
    @Override
    protected ITextComponent getTypeName() {
        return this.getDragonType().getFormattedName("entity.dragonmounts.dragon_egg.name");
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return super.isInvulnerableTo(source) || this.getDragonType().isInvulnerableTo(source);
    }

    @Override
    protected boolean isMovementNoisy() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return this.level.getGameRules().getBoolean(DMGameRules.IS_EGG_PUSHABLE) && super.isPushable();
    }

    @Override
    public void push(Entity entity) {
        if (this.level.getGameRules().getBoolean(DMGameRules.IS_EGG_PUSHABLE)) {
            super.push(entity);
        }
    }

    @Override
    protected boolean isImmobile() {
        return false;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public void thunderHit(ServerWorld level, LightningBoltEntity lightning) {
        super.thunderHit(level, lightning);
        addOrMergeEffect(this, Effects.DAMAGE_BOOST, 700, 0, false, true, true);//35s
        DragonType current = this.getDragonType();
        if (current == DragonTypes.SKELETON) {
            this.setDragonType(DragonTypes.WITHER, false);
        } else if (current == DragonTypes.WATER) {
            this.setDragonType(DragonTypes.STORM, false);
        } else return;
        this.playSound(SoundEvents.END_PORTAL_SPAWN, 2, 1);
        this.playSound(SoundEvents.PORTAL_TRIGGER, 2, 1);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void startSeenByPlayer(ServerPlayerEntity player) {
        CHANNEL.send(PLAYER.with((Supplier) player::getEntity), new SSyncEggAgePacket(this.getId(), this.age));
    }

    public void setAge(int age, boolean lazySync) {
        if (lazySync && this.age != age) {
            CHANNEL.send(TRACKING_ENTITY.with(this::getEntity), new SSyncEggAgePacket(this.getId(), age));
        }
        this.age = age;
    }

    public int getAge() {
        return this.age;
    }

    public float getRotationAxis() {
        return this.rotationAxis;
    }

    public float getAmplitude(float partialTicks) {
        return this.shaking <= 0 ? 0 : MathHelper.lerp(partialTicks, this.amplitudeO, this.amplitude);
    }

    @OnlyIn(Dist.CLIENT)
    public void syncShake(int amplitude, int axis, boolean crack) {
        World level = this.level;
        this.shaking = amplitude;
        this.rotationAxis = axis * TO_RAD_FACTOR;
        // use game time to make amplitude consistent between clients
        float target = MathHelper.sin(level.getGameTime() * 0.5F) * Math.min(amplitude, 15);
        // multiply with a factor to make it smoother
        this.amplitudeO = target * 0.25F;
        this.amplitude = target * 0.75F;
        if (crack) {
            level.levelEvent(2001, this.blockPosition(), Block.getId(
                    this.getDragonType().getInstance(HatchableDragonEggBlock.class, DMBlocks.ENDER_DRAGON_EGG).defaultBlockState())
            );
        }
        Vector3d pos = this.position();
        level.playLocalSound(pos.x, pos.y, pos.z, DMSounds.DRAGON_HATCHING.get(), SoundCategory.NEUTRAL, 1.0F, 1.0F, false);
    }

    public void setDragonType(DragonType type, boolean resetHealth) {
        AttributeModifierManager manager = this.getAttributes();
        manager.removeAttributeModifiers(this.getDragonType().attributes);
        this.entityData.set(DATA_DRAGON_TYPE, type);
        manager.addTransientAttributeModifiers(type.attributes);
        if (resetHealth) {
            ModifiableAttributeInstance health = this.getAttribute(Attributes.MAX_HEALTH);
            assert health != null;
            this.setHealth((float) health.getValue());
        }
    }

    @Override
    public final void setDragonType(DragonType type) {
        this.setDragonType(type, false);
    }

    @Override
    public final DragonType getDragonType() {
        return this.entityData.get(DATA_DRAGON_TYPE);
    }
}
