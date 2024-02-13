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
import net.minecraft.item.SaddleItem;
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
    public final DragonAnimationContext context = new DragonAnimationContext(this);
    private byte rideFlag = -1;

    public ClientDragonEntity(EntityType<? extends TameableDragonEntity> type, World world) {
        super(type, world);
    }

    public void onWingsDown(float speed) {
        if (!this.isInWater()) {
            // play wing sounds
            this.level.playLocalSound(
                    this.getX(),
                    this.getY(),
                    this.getZ(),
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
        this.context.tick(this.firstTick);
        if (!this.isAgeLocked()) {
            if (this.age < 0) {
                ++this.age;
            } else if (this.age > 0) {
                --this.age;
            }
        }
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
