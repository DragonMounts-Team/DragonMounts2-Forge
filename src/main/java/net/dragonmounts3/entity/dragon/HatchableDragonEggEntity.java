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
import net.minecraft.client.Minecraft;
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
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static net.dragonmounts3.entity.dragon.TameableDragonEntity.AGE_DATA_PARAMETER_KEY;
import static net.dragonmounts3.network.DMPacketHandler.CHANNEL;
import static net.dragonmounts3.util.math.MathUtil.getColor;
import static net.minecraftforge.fml.network.PacketDistributor.TRACKING_ENTITY;

@ParametersAreNonnullByDefault
public class HatchableDragonEggEntity extends LivingEntity implements IMutableDragonTypified {
    private static final DataParameter<String> DATA_DRAGON_TYPE = EntityDataManager.defineId(HatchableDragonEggEntity.class, DataSerializers.STRING);
    public static final int MIN_HATCHING_TIME = 36000;
    private static final int CONVERSION_RANGE = 2;
    private static final float EGG_CRACK_PROCESS_THRESHOLD = 0.9F;
    private static final float EGG_SHAKE_PROCESS_THRESHOLD = 0.75F;
    private static final int EGG_SHAKE_THRESHOLD = (int) (EGG_SHAKE_PROCESS_THRESHOLD * MIN_HATCHING_TIME);
    private static final float EGG_SHAKE_BASE_CHANCE = 20F;
    protected DragonType type = DragonType.ENDER;
    protected float rotationAxis = 0;
    protected int amplitude = 0;
    protected int age = 0;
    protected boolean hatched = false;//to keep uuid
    protected Map<ScoreObjective, Score> scores = null;//to keep score
    protected ScorePlayerTeam team = null;//to keep team

    public HatchableDragonEggEntity(EntityType<? extends HatchableDragonEggEntity> type, World world) {
        super(type, world);
        Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(DragonMountsConfig.SERVER.base_health.get());
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
                this.spawnAtLocation(new ItemStack(scales, amount), 1.25F);
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
            if (!this.level.isClientSide) {
                this.playSound(DMSounds.DRAGON_HATCHED.get(), 1.0F, 1.0F);
                ServerWorld world = (ServerWorld) level;
                Scoreboard scoreboard = world.getScoreboard();
                TameableDragonEntity dragon = new TameableDragonEntity(this, DragonLifeStage.NEWBORN);
                String scoreboardName = dragon.getScoreboardName();
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
            this.level.setBlockAndUpdate(this.blockPosition(), block.defaultBlockState());
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
            DragonType type = this.getDragonType();
            double px = getX() + (this.random.nextDouble() - 0.5);
            double py = getY() + (this.random.nextDouble() - 0.3);
            double pz = getZ() + (this.random.nextDouble() - 0.5);
            double ox = (this.random.nextDouble() - 0.5) * 2;
            double oy = (this.random.nextDouble() - 0.3) * 2;
            double oz = (this.random.nextDouble() - 0.5) * 2;
            this.level.addParticle(type.getEggParticle(), px, py, pz, ox, oy, oz);
            if ((this.tickCount & 1) == 0 && type != DragonType.ENDER) {
                int color = type.getColor();
                this.level.addParticle(new RedstoneParticleData(getColor(color, 2), getColor(color, 1), getColor(color, 0), 1.0F), px, py + 0.8, pz, ox, oy, oz);
            }
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
                this.rotationAxis = this.random.nextFloat() * 2F;
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
        if ((this.tickCount & 0b0001_1111) == 0) {//check every 32 ticks
            this.convertDragonType();
        }
    }

    public void convertDragonType() {
        DragonType current = this.getDragonType();
        if (!current.convertible) return;
        Map<DragonType, AtomicInteger> score = new HashMap<>();
        score.put(current, new AtomicInteger(1));
        Optional<RegistryKey<Biome>> biome = this.level.getBiomeName(this.blockPosition());
        Collection<DragonType> types = DragonType.values();
        // update egg breed every second on the server
        BlockPos.betweenClosedStream(this.getBoundingBox().inflate(CONVERSION_RANGE)).forEach(pos -> {
            for (DragonType type : types) {
                if (type.isHabitat(this.level.getBlockState(pos).getBlock())) {
                    AtomicInteger value = score.get(type);
                    if (value == null) {
                        score.put(type, new AtomicInteger(1));
                    } else {
                        value.incrementAndGet();
                    }
                }
            }
        });
        int maxScore = 0;
        ArrayList<DragonType> result = new ArrayList<>();
        for (DragonType type : types) {
            int temp = 0;
            if (type.isHabitat(biome.orElse(null))) {
                ++temp;
            }
            if (type.isHabitatEnvironment.test(this)) {
                ++temp;
            }
            AtomicInteger value = score.get(type);
            if (value != null) {
                temp = value.addAndGet(temp);
            }
            if (temp > maxScore) {
                maxScore = temp;
                result.clear();
                result.add(type);
            } else if (temp == maxScore) {
                result.add(type);
            }
        }
        if (result.isEmpty() || result.contains(current)) return;
        this.setDragonType(result.get(this.random.nextInt(result.size())), false);
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(DMBlocks.HATCHABLE_DRAGON_EGG.get(this.getDragonType()));
    }

    @Nonnull
    @Override
    protected ITextComponent getTypeName() {
        return this.type.getTypifiedName("entity.dragonmounts.dragon_egg.name");
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
        this.level.playSound(Minecraft.getInstance().player, this.getX(), this.getY(), this.getZ(), DMSounds.DRAGON_HATCHING.get(), SoundCategory.NEUTRAL, 1.0F, 1.0F);
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
