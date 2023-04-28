package net.dragonmounts3.entity.dragon;

import net.dragonmounts3.block.HatchableDragonEggBlock;
import net.dragonmounts3.inits.ModBlocks;
import net.dragonmounts3.inits.ModEntities;
import net.dragonmounts3.inits.ModSounds;
import net.dragonmounts3.objects.DragonType;
import net.dragonmounts3.objects.IDragonTypified;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;
import java.util.Objects;

import static net.minecraftforge.event.ForgeEventFactory.onLivingConvert;

@ParametersAreNonnullByDefault
public class HatchableDragonEggEntity extends LivingEntity implements IDragonTypified {
    private static final DataParameter<Integer> DATA_DRAGON_TYPE = EntityDataManager.defineId(HatchableDragonEggEntity.class, DataSerializers.INT);
    public static final String AGE_DATA_PARAMETER_KEY = "Age";
    private static final float EGG_CRACK_THRESHOLD = 0.9f;
    private static final float EGG_WRIGGLE_THRESHOLD = 0.75f;
    private static final float EGG_WRIGGLE_BASE_CHANCE = 20;
    private static final int MIN_HATCHING_TIME = 36000;
    private static final int MAX_HATCHING_TIME = 48000;
    protected int wriggleX = 0;
    protected int wriggleZ = 0;
    protected int age = 0;

    public HatchableDragonEggEntity(EntityType<? extends HatchableDragonEggEntity> type, World world) {
        super(type, world);
        final double health = this.getDragonType().getConfig().getMaxHealth();
        Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(health);
        this.setHealth((float) health);
    }

    public HatchableDragonEggEntity(World world) {
        this(ModEntities.HATCHABLE_DRAGON_EGG.get(), world);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return LivingEntity.createLivingAttributes().add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_DRAGON_TYPE, DragonType.ENDER.ordinal());
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt(DragonType.DATA_PARAMETER_KEY, this.entityData.get(DATA_DRAGON_TYPE));
        compound.putInt(AGE_DATA_PARAMETER_KEY, this.age);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains(DragonType.DATA_PARAMETER_KEY)) {
            this.entityData.set(DATA_DRAGON_TYPE, compound.getInt(DragonType.DATA_PARAMETER_KEY));
        }
        if (compound.contains(AGE_DATA_PARAMETER_KEY)) {
            this.age = compound.getInt(AGE_DATA_PARAMETER_KEY);
        }
    }

    protected void crack() {
        HatchableDragonEggBlock block = ModBlocks.HATCHABLE_DRAGON_EGG.get(getDragonType());
        if (block != null) {
            this.level.levelEvent(2001, blockPosition(), Block.getId(block.defaultBlockState()));
        }
        this.level.playSound(null, blockPosition(), ModSounds.DRAGON_HATCHING, SoundCategory.BLOCKS, +1.0F, 1.0F);
    }

    public void hatch() {
        this.crack();
        TameableDragonEntity dragon = ModEntities.TAMEABLE_DRAGON.get().create(this.level);
        if (dragon != null) {
            dragon.copyPosition(this);
            if (this.hasCustomName()) {
                dragon.setCustomName(this.getCustomName());
                dragon.setCustomNameVisible(this.isCustomNameVisible());
            }
            dragon.setInvulnerable(this.isInvulnerable());
            this.crack();
            this.level.addFreshEntity(dragon);
            this.remove();
            onLivingConvert(this, dragon);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        if (id > 16 && id < 32) {
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
            this.level.addParticle(this.getDragonType().getConfig().getEggParticle(), px, py, pz, ox, oy, oz);
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
                    byte state = 0B10000;//16
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
                    if (state != 0B10000) {
                        this.level.broadcastEntityEvent(this, state);
                        if (progress > EGG_CRACK_THRESHOLD) {
                            this.crack();
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
        this.entityData.set(DATA_DRAGON_TYPE, type.ordinal());
        if (reset) {
            final double health = this.getDragonType().getConfig().getMaxHealth();
            Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(health);
            this.setHealth((float) health);
        }
    }

    public int getDragonTypeId() {
        return this.entityData.get(DATA_DRAGON_TYPE);
    }

    @Override
    public DragonType getDragonType() {
        return DragonType.values()[this.getDragonTypeId()];
    }
}
