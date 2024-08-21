package net.dragonmounts.client;

import net.dragonmounts.data.tag.DMItemTags;
import net.dragonmounts.entity.dragon.DragonLifeStage;
import net.dragonmounts.entity.dragon.TameableDragonEntity;
import net.dragonmounts.item.DragonArmorItem;
import net.dragonmounts.network.CRideDragonPacket;
import net.dragonmounts.network.SSyncDragonAgePacket;
import net.dragonmounts.util.DragonFood;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SaddleItem;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static net.dragonmounts.network.DMPacketHandler.CHANNEL;

@ParametersAreNonnullByDefault
public class ClientDragonEntity extends TameableDragonEntity {
    public final DragonRendererContext context = new DragonRendererContext(this);
    public boolean renderCrystalBeams = true;
    private int rideFlag = -1;

    public ClientDragonEntity(EntityType<? extends TameableDragonEntity> type, World world) {
        super(type, world);
    }

    @Override
    public void setSaddle(@Nonnull ItemStack stack, boolean sync) {
        this.context.isSaddled = this.isSaddled = stack.getItem() instanceof SaddleItem;
        if (!stack.isEmpty()) {
            stack.setCount(1);
        }
        this.saddle = stack;
    }

    @Override
    public void onSyncedDataUpdated(DataParameter<?> key) {
        if (DATA_SADDLE_ITEM.equals(key)) {
            this.saddle = this.entityData.get(DATA_SADDLE_ITEM);
            this.context.isSaddled = this.isSaddled = this.saddle.getItem() instanceof SaddleItem;
        } else if (DATA_ARMOR_ITEM.equals(key)) {
            this.armor = this.entityData.get(DATA_ARMOR_ITEM);
        } else if (DATA_CHEST_ITEM.equals(key)) {
            this.chest = this.entityData.get(DATA_CHEST_ITEM);
            this.hasChest = Tags.Items.CHESTS_WOODEN.contains(this.chest.getItem());
        } else {
            super.onSyncedDataUpdated(key);
        }
    }

    public void onWingsDown(float speed) {
        if (!this.isInWater()) {
            Vector3d pos = this.position();
            // play wing sounds
            this.level.playLocalSound(
                    pos.x,
                    pos.y,
                    pos.z,
                    SoundEvents.ENDER_DRAGON_FLAP,
                    SoundCategory.VOICE,
                    (1 - speed) * this.getVoicePitch(),
                    (0.5F - speed * 0.2F) * this.getSoundVolume(),
                    true
            );
        }
    }

    @Override
    public void aiStep() {
        if (this.isDeadOrDying()) {
            this.nearestCrystal = null;
        } else {
            this.checkCrystals();
        }
        super.aiStep();
        if (!this.isAgeLocked()) {
            if (this.age < 0) {
                ++this.age;
            } else if (this.age > 0) {
                --this.age;
            }
        }
        this.context.tick(this.firstTick);
    }

    @Override
    protected void checkCrystals() {
        if (this.nearestCrystal != null && this.nearestCrystal.isAlive()) {
            if (this.random.nextInt(20) == 0) {
                this.nearestCrystal = this.findCrystal();
            }
        } else {
            this.nearestCrystal = this.random.nextInt(10) == 0 ? this.findCrystal() : null;
        }
    }

    @Nonnull
    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        Item item = player.getItemInHand(hand).getItem();
        return DragonFood.test(item) || (
                this.isOwnedBy(player) && (
                        item instanceof SaddleItem ||
                                item instanceof DragonArmorItem ||
                                DMItemTags.BATONS.contains(item) ||
                                Tags.Items.CHESTS_WOODEN.contains(item)
                )
        ) ? ActionResultType.CONSUME : ActionResultType.PASS;
    }

    @Override
    public void setLifeStage(DragonLifeStage stage, boolean reset, boolean sync) {
        if (this.stage == stage) return;
        this.stage = stage;
        if (reset) {
            this.refreshAge();
        }
        this.reapplyPosition();
        this.refreshDimensions();
    }

    @Override
    public void travel(Vector3d vector) {
        super.travel(vector);
        if (this.isControlledByLocalInstance()) {
            CRideDragonPacket packet = new CRideDragonPacket(this.getId());
            if (this.rideFlag != packet.flag) {
                this.rideFlag = packet.flag;
                CHANNEL.sendToServer(packet);
            }
        }
    }

    public void handleAgeSync(SSyncDragonAgePacket packet) {
        this.age = packet.age;
        this.setLifeStage(packet.stage, false, false);
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }

    public void refreshForcedAgeTimer() {
        if (this.forcedAgeTimer <= 0) {
            this.forcedAgeTimer = 40;
        }
    }
}
