package net.dragonmounts3.entity.dragon;

import net.dragonmounts3.block.DragonEggBlock;
import net.dragonmounts3.inits.ModBlocks;
import net.dragonmounts3.inits.ModEntities;
import net.dragonmounts3.objects.DragonType;
import net.dragonmounts3.objects.IDragonTypified;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;
import java.util.Objects;

import static net.minecraftforge.event.ForgeEventFactory.onLivingConvert;

@ParametersAreNonnullByDefault
public class DragonEggEntity extends LivingEntity implements IDragonTypified {
    private static final DataParameter<Integer> DATA_DRAGON_TYPE = EntityDataManager.defineId(DragonEggEntity.class, DataSerializers.INT);
    private static final String KEY_DRAGON_TYPE = "DragonType";
    public static final double KNOCKBACK_RESISTANCE = 1.0D;

    public DragonEggEntity(EntityType<? extends DragonEggEntity> type, World world) {
        super(type, world);
        final double health = this.getDragonType().getConfig().getMaxHealth();
        Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(health);
        this.setHealth((float) health);
    }

    public DragonEggEntity(World world) {
        this(ModEntities.DRAGON_EGG.get(), world);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return LivingEntity.createLivingAttributes().add(Attributes.KNOCKBACK_RESISTANCE, KNOCKBACK_RESISTANCE);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_DRAGON_TYPE, DragonType.ENDER.ordinal());
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt(KEY_DRAGON_TYPE, this.entityData.get(DATA_DRAGON_TYPE));
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains(KEY_DRAGON_TYPE)) {
            this.entityData.set(DATA_DRAGON_TYPE, compound.getInt(KEY_DRAGON_TYPE));
        }
    }

    protected void onHatch() {
        if (isAlive()) {
            TameableDragonEntity dragon = ModEntities.TAMEABLE_DRAGON.get().create(this.level);
            if (dragon != null) {
                dragon.copyPosition(this);
                dragon.setBaby(this.isBaby());
                if (this.hasCustomName()) {
                    dragon.setCustomName(this.getCustomName());
                    dragon.setCustomNameVisible(this.isCustomNameVisible());
                }
                dragon.setInvulnerable(this.isInvulnerable());
                this.level.addFreshEntity(dragon);
                this.remove();
                onLivingConvert(this, dragon);
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
        } else {
            DragonEggBlock block = ModBlocks.DRAGON_EGG.get(this.getDragonType());
            if (block == null) {
                return ActionResultType.FAIL;
            }
            this.remove();
            this.level.setBlockAndUpdate(new BlockPos(this.getX(), this.getY(), this.getZ()), block.defaultBlockState());
            return ActionResultType.SUCCESS;
        }
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
