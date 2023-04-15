package net.dragonmounts3.entity.dragon;

import net.dragonmounts3.inits.ModEntities;
import net.dragonmounts3.objects.DragonType;
import net.dragonmounts3.objects.IDragonTypified;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;

import static net.minecraftforge.event.ForgeEventFactory.onLivingConvert;

@ParametersAreNonnullByDefault
public class DragonEggEntity extends LivingEntity implements IDragonTypified {
    private static final DataParameter<Integer> DRAGON_TYPE = EntityDataManager.defineId(DragonEggEntity.class, DataSerializers.INT);

    public DragonEggEntity(EntityType<? extends DragonEggEntity> type, World world) {
        super(type, world);
    }

    public DragonEggEntity(World world) {
        this(ModEntities.ENTITY_DRAGON_EGG.get(), world);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DRAGON_TYPE, DragonType.ENDER.ordinal());
    }

    protected void onHatch() {
        if (isAlive()) {
            TameableDragonEntity dragon = ModEntities.ENTITY_TAMEABLE_DRAGON.get().create(this.level);
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
            onHatch();//test
        }
        return ActionResultType.PASS;
    }

    public int getDragonTypeId() {
        return this.entityData.get(DRAGON_TYPE);
    }

    @Override
    public DragonType getDragonType() {
        return DragonType.values()[this.getDragonTypeId()];
    }
}
