package net.dragonmounts3.entity.dragon;

import net.dragonmounts3.DragonMountsConfig;
import net.dragonmounts3.api.DragonType;
import net.dragonmounts3.api.IMutableDragonTypified;
import net.dragonmounts3.block.HatchableDragonEggBlock;
import net.dragonmounts3.inits.ModBlocks;
import net.dragonmounts3.inits.ModEntities;
import net.dragonmounts3.inits.ModItems;
import net.dragonmounts3.inits.ModSounds;
import net.dragonmounts3.item.DragonScalesItem;
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
import net.minecraft.util.*;
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

@ParametersAreNonnullByDefault
public class HatchableDragonEggEntity extends LivingEntity implements IMutableDragonTypified {
    private static final DataParameter<String> DATA_DRAGON_TYPE = EntityDataManager.defineId(HatchableDragonEggEntity.class, DataSerializers.STRING);
    private static final float EGG_CRACK_THRESHOLD = 0.9f;
    private static final float EGG_WRIGGLE_THRESHOLD = 0.75f;
    private static final float EGG_WRIGGLE_BASE_CHANCE = 20;
    private static final int MIN_HATCHING_TIME = 36000;
    private static final int MAX_HATCHING_TIME = 48000;
    protected int wriggleX = 0;
    protected int wriggleZ = 0;
    protected int age = 0;
    protected boolean hatched = false;//to keep uuid
    protected Map<ScoreObjective, Score> scores = null;//to keep score
    protected ScorePlayerTeam team = null;//to keep team

    public HatchableDragonEggEntity(EntityType<? extends HatchableDragonEggEntity> type, World world) {
        super(type, world);
        Objects.requireNonNull(this.getAttributes().getInstance(Attributes.MAX_HEALTH)).setBaseValue(DragonMountsConfig.BASE_HEALTH.get());
    }

    public HatchableDragonEggEntity(World world) {
        this(ModEntities.HATCHABLE_DRAGON_EGG.get(), world);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, DragonMountsConfig.BASE_HEALTH.get())
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

    protected void crack(int amount, SoundEvent sound) {
        DragonType type = this.getDragonType();
        HatchableDragonEggBlock block = ModBlocks.HATCHABLE_DRAGON_EGG.get(type);
        if (block != null) {
            this.level.levelEvent(2001, blockPosition(), Block.getId(block.defaultBlockState()));
        }
        if (amount > 0 && !this.level.isClientSide) {
            DragonScalesItem scales = ModItems.DRAGON_SCALES.get(type);
            if (scales != null && this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
                this.spawnAtLocation(new ItemStack(scales, amount), 1.25f);
            }
        }
        this.level.playSound(null, this, sound, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }

    public void hatch() {
        this.crack(this.random.nextInt(4) + 4, ModSounds.DRAGON_HATCHED.get());
        if (!this.level.isClientSide) {
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
        if (this.hatched && !this.level.isClientSide) {
            ServerWorld world = (ServerWorld) level;
            Scoreboard scoreboard = world.getScoreboard();
            TameableDragonEntity dragon = new TameableDragonEntity(world);
            String scoreboardName = dragon.getScoreboardName();
            CompoundNBT compound = this.saveWithoutId(new CompoundNBT());
            compound.remove(AGE_DATA_PARAMETER_KEY);
            dragon.load(compound);
            dragon.setLifeStage(DragonLifeStage.NEWBORN, true);
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

    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        if (id > 64) {
            if ((id & 0B0010) == 0B0010) {
                this.wriggleX = 20;
            } else if ((id & 0B0001) == 0B0001) {
                this.wriggleX = 10;
            }
            if ((id & 0B1000) == 0B1000) {
                this.wriggleZ = 20;
            } else if ((id & 0B0100) == 0B0100) {
                this.wriggleZ = 10;
            }
        } else {
            super.handleEntityEvent(id);
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
            HatchableDragonEggBlock block = ModBlocks.HATCHABLE_DRAGON_EGG.get(this.getDragonType());
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
        if (this.level.isClientSide) {
            if (this.wriggleX > 0) {
                --this.wriggleX;
            }
            if (this.wriggleZ > 0) {
                --this.wriggleZ;
            }
            // spawn generic particles
            double px = getX() + (this.random.nextDouble() - 0.5);
            double py = getY() + (this.random.nextDouble() - 0.3);
            double pz = getZ() + (this.random.nextDouble() - 0.5);
            double ox = (this.random.nextDouble() - 0.5) * 2;
            double oy = (this.random.nextDouble() - 0.3) * 2;
            double oz = (this.random.nextDouble() - 0.5) * 2;
            this.level.addParticle(this.getDragonType().getEggParticle(), px, py, pz, ox, oy, oz);
        } else {
            int age = this.getAge() + 1;
            // animate egg wriggle based on the time the eggs take to hatch
            float progress = (float) age / MIN_HATCHING_TIME;
            // wait until the egg is nearly hatched
            if (progress > EGG_WRIGGLE_THRESHOLD) {
                float chance = (progress - EGG_WRIGGLE_THRESHOLD) / EGG_WRIGGLE_BASE_CHANCE * (1 - EGG_WRIGGLE_THRESHOLD);
                if (age >= MAX_HATCHING_TIME || age >= MIN_HATCHING_TIME && this.random.nextFloat() < chance) {
                    this.hatch();
                } else {
                    byte state = 0B1000000;//64
                    if (this.wriggleX > 0) {
                        --this.wriggleX;
                    } else if (this.random.nextFloat() < chance) {
                        if (this.random.nextBoolean()) {
                            this.wriggleX = 10;
                            state |= 0B0100;
                        } else {
                            this.wriggleX = 20;
                            state |= 0B1000;
                        }
                    }
                    if (this.wriggleZ > 0) {
                        --this.wriggleZ;
                    } else if (this.random.nextFloat() < chance) {
                        if (this.random.nextBoolean()) {
                            this.wriggleZ = 10;
                            state |= 0B0001;
                        } else {
                            this.wriggleZ = 20;
                            state |= 0B0010;
                        }
                    }
                    if (state != 0B1000000) {
                        this.level.broadcastEntityEvent(this, state);
                        if (progress > EGG_CRACK_THRESHOLD) {
                            this.crack(1, ModSounds.DRAGON_HATCHING.get());
                        }
                    }
                }
            }
            this.setAge(age);
        }
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(ModBlocks.HATCHABLE_DRAGON_EGG.get(getDragonType()));
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

    public int getWriggleX() {
        return this.wriggleX;
    }

    public int getWriggleZ() {
        return this.wriggleZ;
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
        DragonType type = DragonType.byName(this.entityData.get(DATA_DRAGON_TYPE));
        if (type == null) {
            return DragonType.ENDER;
        }
        return type;
    }
}
