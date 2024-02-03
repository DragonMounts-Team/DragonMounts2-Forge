package net.dragonmounts.entity.ai;

import net.dragonmounts.entity.dragon.TameableDragonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class DragonCatchOwnerGoal extends DragonBaseGoal {

    protected PlayerEntity owner = (PlayerEntity) this.dragon.getOwner();

    public DragonCatchOwnerGoal(TameableDragonEntity dragon) {
        super(dragon);
    }

    @Override
    public boolean canUse() {
        ItemStack itemStack = this.owner.getItemBySlot(EquipmentSlotType.CHEST);
        if (this.owner == null) {
            return false;
        } else if (this.dragon.isLeashed()) {
            return false;
        } else if (this.owner.abilities.instabuild) {
            return false;
        } else if (this.dragon.isInSittingPose()) {
            return false;
        } else if (!this.dragon.isSaddled()) {
            return false;
        } else if (!itemStack.isEmpty() && itemStack.getItem() == Items.ELYTRA && ElytraItem.isFlyEnabled(itemStack)) {
            return false;
        }

        return this.owner.fallDistance > 4;
    }

    @Override
    public void tick() {
        // catch owner in flight if possible
        if (!this.dragon.isFlying()) {
            this.dragon.liftOff();
        }

        double followRange = this.getFollowRange();
        //TODO: Need "Boost" data.
//        this.dragon.setBoosting(this.dragon.getDistance(this.owner) < 1);
        if (this.dragon.distanceTo(this.owner) < followRange) {
            // mount owner if close enough, otherwise move to owner
            if (this.dragon.distanceTo(this.owner) <= this.dragon.getBbWidth() || this.dragon.distanceTo(this.owner) <= this.dragon.getBbHeight() && !this.owner.isSuppressingBounce() && this.dragon.isFlying()) {
                this.owner.startRiding(this.dragon);
            } else {
                this.dragon.getNavigation().moveTo(this.owner, 1);
            }
        }
    }

}