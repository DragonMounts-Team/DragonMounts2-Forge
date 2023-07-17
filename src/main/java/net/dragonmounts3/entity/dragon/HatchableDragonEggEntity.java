package net.dragonmounts3.entity.dragon;

import net.dragonmounts3.DragonMountsConfig;
import net.dragonmounts3.api.DragonType;
import net.dragonmounts3.api.IMutableDragonTypified;
import net.dragonmounts3.block.HatchableDragonEggBlock;
import net.dragonmounts3.init.DMBlocks;
import net.dragonmounts3.init.DMEntities;
import net.dragonmounts3.init.DMItems;
import net.dragonmounts3.init.DMSounds;
import net.dragonmounts3.item.DragonScalesItem;
import net.dragonmounts3.network.SShakeDragonEggPacket;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import static net.dragonmounts3.entity.dragon.TameableDragonEntity.AGE_DATA_PARAMETER_KEY;
import static net.dragonmounts3.network.DMPacketHandler.CHANNEL;
import static net.minecraftforge.fml.network.PacketDistributor.TRACKING_ENTITY;

@ParametersAreNonnullByDefault
public class HatchableDragonEggEntity extends LivingEntity implements IMutableDragonTypified {
    private static final DataParameter<String> DATA_DRAGON_TYPE = EntityDataManager.defineId(HatchableDragonEggEntity.class, DataSerializers.STRING);
    public static final int MIN_HATCHING_TIME = 36000;
    private static final float EGG_CRACK_PROCESS_THRESHOLD = 0.9f;
    private static final float EGG_SHAKE_PROCESS_THRESHOLD = 0.75f;
    private static final int EGG_SHAKE_THRESHOLD = (int) (EGG_SHAKE_PROCESS_THRESHOLD * MIN_HATCHING_TIME);
    private static final float EGG_SHAKE_BASE_CHANCE = 20f;
    protected DragonType type = DragonType.ENDER;
    protected float rotationAxis = 0;
    protected int amplitude = 0;
    protected int age = 0;
    protected boolean hatched = false;//to keep uuid
    protected Map<ScoreObjective, Score> scores = null;//to keep score
    protected ScorePlayerTeam team = null;//to keep team

    public HatchableDragonEggEntity(EntityType<? extends HatchableDragonEggEntity> type, World world) {
        super(type, world);
        Objects.requireNonNull(this.getAttributes().getInstance(Attributes.MAX_HEALTH)).setBaseValue(DragonMountsConfig.SERVER.base_health.get());
    }

    public HatchableDragonEggEntity(World world) {
        this(DMEntities.HATCHABLE_DRAGON_EGG.get(), world);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, DragonMountsConfig.SERVER.base_health.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_DRAGON_TYPE, DragonType.ENDER.getSerializedName());
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putString(DragonType.DATA_PARAMETER_KEY, this.entityData.get(DATA_DRAGON_TYPE));
        compound.putInt(AGE_DATA_PARAMETER_KEY, this.age);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.setDragonType(DragonType.byName(compound.getString(DragonType.DATA_PARAMETER_KEY)));
        if (compound.contains(AGE_DATA_PARAMETER_KEY)) {
            this.age = compound.getInt(AGE_DATA_PARAMETER_KEY);
        }
    }

    @Override
    public void onSyncedDataUpdated(DataParameter<?> key) {
        if (DATA_DRAGON_TYPE.equals(key)) {
            this.type = DragonType.byName(this.entityData.get(DATA_DRAGON_TYPE));
        } else {
            super.onSyncedDataUpdated(key);
        }
    }

    protected void spawnScales(int amount) {
        if (amount > 0) {
            DragonScalesItem scales = DMItems.DRAGON_SCALES.get(this.getDragonType());
            if (scales != null && this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
                this.spawnAtLocation(new ItemStack(scales, amount), 1.25f);
            }
        }
    }

    public void hatch() {
        if (!this.level.isClientSide) {
            this.spawnScales(this.random.nextInt(4) + 4);
            String scoreboardName = this.getScoreboardName();
            Scoreboard scoreboard = this.level.getScoreboard();
            this.scores = scoreboard.getPlayerScores(scoreboardName);
            this.team = scoreboard.getPlayersTeam(scoreboardName);
            this.hatched = true;
        }
        this.remove();
    }

    @Override
    public void onRemovedFromWorld() {
        super.onRemovedFromWorld();
        if (this.hatched) {
            if (this.level.isClientSide) {
                this.playSound(DMSounds.DRAGON_HATCHED.get(), 1.0F, 1.0F);
            } else {
                ServerWorld world = (ServerWorld) level;
                Scoreboard scoreboard = world.getScoreboard();
                TameableDragonEntity dragon = new TameableDragonEntity(world);
                String scoreboardName = dragon.getScoreboardName();
                CompoundNBT compound = this.saveWithoutId(new CompoundNBT());
                compound.remove(AGE_DATA_PARAMETER_KEY);
                dragon.load(compound);
                dragon.setLifeStage(DragonLifeStage.NEWBORN, true, false);
                if (this.team != null) {
                    scoreboard.addPlayerToTeam(scoreboardName, this.team);
                }
                if (this.scores != null) {
                    Score target;
                    Score cache;
                    for (Map.Entry<ScoreObjective, Score> entry : this.scores.entrySet()) {
                        cache = entry.getValue();
                        target = scoreboard.getOrCreatePlayerScore(scoreboardName, entry.getKey());
                        target.setScore(cache.getScore());
                        target.setLocked(cache.isLocked());
                    }
                }
                world.addFreshEntity(dragon);
            }
        }
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
    public void setItemSlot(EquipmentSlotType slot, @Nullable ItemStack stack) {
    }

    @Nonnull
    @Override
    public HandSide getMainArm() {
        return HandSide.RIGHT;
    }

    @Nonnull
    @Override
    public ActionResultType interact(PlayerEntity player, Hand hand) {
        if (!this.isAlive()) {
            return ActionResultType.PASS;
        } else if (player.isShiftKeyDown()) {
            HatchableDragonEggBlock block = DMBlocks.HATCHABLE_DRAGON_EGG.get(this.getDragonType());
            if (block == null) {
                return ActionResultType.FAIL;
            }
            this.remove();
            this.level.setBlockAndUpdate(new BlockPos(this.getX(), this.getY(), this.getZ()), block.defaultBlockState());
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.amplitude > 0) {
            --this.amplitude;
        } else if (this.amplitude < 0) {
            ++this.amplitude;
        }
        if (this.level.isClientSide) {
            // spawn generic particles
            double px = getX() + (this.random.nextDouble() - 0.5);
            double py = getY() + (this.random.nextDouble() - 0.3);
            double pz = getZ() + (this.random.nextDouble() - 0.5);
            double ox = (this.random.nextDouble() - 0.5) * 2;
            double oy = (this.random.nextDouble() - 0.3) * 2;
            double oz = (this.random.nextDouble() - 0.5) * 2;
            this.level.addParticle(this.getDragonType().getEggParticle(), px, py, pz, ox, oy, oz);
            return;
        }
        // play the egg shake animation based on the time the eggs take to hatch
        if (++this.age > EGG_SHAKE_THRESHOLD && this.amplitude == 0) {
            float progress = (float) this.age / MIN_HATCHING_TIME;
            // wait until the egg is nearly hatched
            float chance = (progress - EGG_SHAKE_PROCESS_THRESHOLD) / EGG_SHAKE_BASE_CHANCE * (1 - EGG_SHAKE_PROCESS_THRESHOLD);
            if (this.age >= MIN_HATCHING_TIME && this.random.nextFloat() * 2 < chance) {
                this.hatch();
                return;
            }
            if (this.random.nextFloat() < chance) {
                this.rotationAxis = this.random.nextFloat() * 2f;
                this.amplitude = this.random.nextBoolean() ? 10 : 20;
                boolean flag = progress > EGG_CRACK_PROCESS_THRESHOLD;
                if (flag) {
                    this.spawnScales(1);
                }
                CHANNEL.send(
                        TRACKING_ENTITY.with(() -> this),
                        new SShakeDragonEggPacket(this.getId(), this.rotationAxis, this.amplitude, flag)
                );
            }
        }
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(DMBlocks.HATCHABLE_DRAGON_EGG.get(this.getDragonType()));
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
    public void push(Entity entity) {
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return this.age;
    }

    public float getRotationAxis() {
        return this.rotationAxis;
    }

    public int getAmplitude() {
        return this.amplitude;
    }

    @OnlyIn(Dist.CLIENT)
    public void applyPacket(SShakeDragonEggPacket packet) {
        this.rotationAxis = packet.axis;
        this.amplitude = packet.amplitude;
        if (packet.particle) {
            DragonType type = this.getDragonType();
            HatchableDragonEggBlock block = DMBlocks.HATCHABLE_DRAGON_EGG.get(type);
            if (block != null) {
                this.level.levelEvent(2001, this.blockPosition(), Block.getId(block.defaultBlockState()));
            }
        }
        this.playSound(DMSounds.DRAGON_HATCHING.get(), 1.0F, 1.0F);
    }

    public void setDragonType(DragonType type, boolean reset) {
        AttributeModifierManager manager = this.getAttributes();
        manager.removeAttributeModifiers(this.getDragonType().getAttributeModifiers());
        this.entityData.set(DATA_DRAGON_TYPE, type.getSerializedName());
        manager.addTransientAttributeModifiers(type.getAttributeModifiers());
        if (reset) {
            ModifiableAttributeInstance health = this.getAttribute(Attributes.MAX_HEALTH);
            if (health != null) {
                this.setHealth((float) health.getValue());
            }
        }
    }

    @Override
    public void setDragonType(DragonType type) {
        this.setDragonType(type, false);
    }

    @Override
    public DragonType getDragonType() {
        return this.type;
    }
}
