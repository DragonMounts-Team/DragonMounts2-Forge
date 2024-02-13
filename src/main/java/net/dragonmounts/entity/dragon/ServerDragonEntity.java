package net.dragonmounts.entity.dragon;

import net.dragonmounts.DragonMountsConfig;
import net.dragonmounts.api.IDragonFood;
import net.dragonmounts.block.entity.DragonCoreBlockEntity;
import net.dragonmounts.data.tag.DMItemTags;
import net.dragonmounts.entity.ai.DragonFollowOwnerGoal;
import net.dragonmounts.entity.ai.PlayerControlledGoal;
import net.dragonmounts.init.DMBlocks;
import net.dragonmounts.init.DMEntities;
import net.dragonmounts.init.DMItems;
import net.dragonmounts.init.DragonTypes;
import net.dragonmounts.inventory.LimitedSlot;
import net.dragonmounts.item.DragonEssenceItem;
import net.dragonmounts.network.SFeedDragonPacket;
import net.dragonmounts.network.SSyncDragonAgePacket;
import net.dragonmounts.registry.DragonType;
import net.dragonmounts.util.DragonFood;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;

import static net.dragonmounts.network.DMPacketHandler.CHANNEL;
import static net.dragonmounts.util.EntityUtil.addOrMergeEffect;
import static net.dragonmounts.util.EntityUtil.addOrResetEffect;
import static net.minecraftforge.fml.network.PacketDistributor.PLAYER;
import static net.minecraftforge.fml.network.PacketDistributor.TRACKING_ENTITY;

public class ServerDragonEntity extends TameableDragonEntity {
    public final PlayerControlledGoal playerControlledGoal;

    public ServerDragonEntity(EntityType<? extends TameableDragonEntity> type, World world) {
        super(type, world);
        this.goalSelector.addGoal(0, this.playerControlledGoal = new PlayerControlledGoal(this));
    }

    public ServerDragonEntity(World world) {
        this(DMEntities.TAMEABLE_DRAGON.get(), world);
    }

    public ServerDragonEntity(HatchableDragonEggEntity egg, DragonLifeStage stage) {
        this(DMEntities.TAMEABLE_DRAGON.get(), egg.level);
        CompoundNBT data = egg.saveWithoutId(new CompoundNBT());
        data.remove(AGE_DATA_PARAMETER_KEY);
        data.remove(DragonLifeStage.DATA_PARAMETER_KEY);
        this.load(data);
        this.setDragonType(egg.getDragonType(), false);
        this.setLifeStage(stage, true, true);
        this.setHealth(this.getMaxHealth() + egg.getHealth() - egg.getMaxHealth());
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(3, new DragonFollowOwnerGoal(this));
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, MobEntity.class, 5, false, false, entity -> entity instanceof IMob));
    }

    /**
     * Causes this entity to lift off if it can fly.
     */
    public void liftOff() {
        if (!this.isBaby()) {
            boolean flag = this.isVehicle() && (this.isInLava() || this.isInWaterOrBubble());
            this.hasImpulse = true;
            // stronger jump for an easier lift-off
            this.setDeltaMovement(this.getDeltaMovement().add(0, flag ? 0.7 : 6, 0));
            this.flightTicks += flag ? 3 : 4;
        }
    }

    public void spawnEssence(ItemStack stack) {
        BlockPos pos = this.blockPosition();
        if (this.level.isEmptyBlock(pos)) {
            BlockState state = DMBlocks.DRAGON_CORE.defaultBlockState().setValue(HorizontalBlock.FACING, this.getDirection());
            if (this.level.setBlock(pos, state, 3)) {
                TileEntity entity = this.level.getBlockEntity(pos);
                if (entity instanceof DragonCoreBlockEntity) {
                    ((DragonCoreBlockEntity) entity).setItem(0, stack);
                    return;
                }
            }
        }
        this.level.addFreshEntity(new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), stack));
    }

    @Override
    protected void checkCrystals() {
        if (this.nearestCrystal != null && this.nearestCrystal.isAlive()) {
            if (++this.crystalTicks > 0 && this.getHealth() < this.getMaxHealth()) {
                this.crystalTicks = -10;
                this.setHealth(this.getHealth() + 1.0F);
                addOrResetEffect(this, Effects.DAMAGE_BOOST, 300, 0, false, true, true, 101);//15s
            }
            if (this.random.nextInt(20) == 0) {
                this.nearestCrystal = this.findCrystal();
            }
        } else {
            this.nearestCrystal = this.random.nextInt(10) == 0 ? this.findCrystal() : null;
        }
    }

    @Override
    public void aiStep() {
        if (this.isDeadOrDying()) {
            this.nearestCrystal = null;
        } else {
            this.checkCrystals();
        }
        if (this.shearCooldown > 0) {
            this.setSheared(this.shearCooldown - 1);
        }
        if (this.isAgeLocked()) {
            int age = this.age;
            this.age = 0;
            super.aiStep();
            this.age = age;
        } else {
            super.aiStep();
        }
        /*if (this.isOnGround()) {
            this.flightTicks = 0;
            this.setFlying(false);
        } else {
            this.setFlying(++this.flightTicks > LIFTOFF_THRESHOLD && !this.isBaby() && this.getControllingPassenger() != null);
                if (this.isFlying() != flag) {
                    getEntityAttribute(FOLLOW_RANGE).setBaseValue(getDragonSpeed());
                }
        }*/
    }

    @Nonnull
    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        Item item = stack.getItem();
        IDragonFood food = DragonFood.get(item, null);
        if (food != null) {
            if (!food.isEatable(this, player, stack, hand)) return ActionResultType.FAIL;
            food.eat(this, player, stack, hand);
            CHANNEL.send(TRACKING_ENTITY.with(() -> this), new SFeedDragonPacket(this, item));
        } else if (!this.isOwnedBy(player)) {
            return ActionResultType.PASS;
        } else if (DMItemTags.BATONS.contains(item)) {
            this.setOrderedToSit(!this.isOrderedToSit());
        } else if (!this.isBaby() && this.getSaddle().isEmpty() && LimitedSlot.Saddle.mayPlace(item)) {
            this.setSaddle(stack.split(1), true);
        } else if (this.getArmor().isEmpty() && LimitedSlot.DragonArmor.mayPlace(item)) {
            this.setArmor(stack.split(1), true);
        } else if (this.getChest().isEmpty() && LimitedSlot.SingleWoodenChest.mayPlace(item)) {
            this.setChest(stack.split(1), true);
        } else {
            ActionResultType result = item.interactLivingEntity(stack, player, this, hand);
            if (result.consumesAction()) return result;
            if (!DragonMountsConfig.SERVER.debug.get() || player.isSecondaryUseActive()) {
                this.openInventory((ServerPlayerEntity) player);
            } else if (this.isBaby()) {
                this.setTarget(null);
                this.getNavigation().stop();
                this.setInSittingPose(false);
                CompoundNBT tag = new CompoundNBT();
                if (this.saveAsPassenger(tag) && player.setEntityOnShoulder(tag)) {
                    this.remove(false);
                }
            } else if (this.isSaddled) {
                player.yRot = this.yRot;
                player.xRot = this.xRot;
                player.startRiding(this);
            } else {
                this.openInventory((ServerPlayerEntity) player);
            }
        }
        return ActionResultType.SUCCESS;
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

    @Override
    public void die(DamageSource cause) {
        super.die(cause);
        if (this.isTame()) {
            this.spawnEssence(this.getDragonType().getInstance(DragonEssenceItem.class, DMItems.ENDER_DRAGON_ESSENCE).saveEntity(this));
        }
    }

    @Override
    public void startSeenByPlayer(ServerPlayerEntity player) {
        CHANNEL.send(PLAYER.with(() -> player), new SSyncDragonAgePacket(this));
    }

    @Override
    public void setLifeStage(DragonLifeStage stage, boolean reset, boolean sync) {
        ModifiableAttributeInstance attribute = this.getAttribute(Attributes.MAX_HEALTH);
        if (attribute != null) {
            double temp = attribute.getValue();
            attribute.removeModifier(AGE_MODIFIER_UUID);
            attribute.addTransientModifier(new AttributeModifier(
                    AGE_MODIFIER_UUID,
                    "DragonAgeBonus",
                    Math.max(DragonLifeStage.getSizeAverage(stage), 0.1F),
                    AttributeModifier.Operation.MULTIPLY_TOTAL
            ));
            temp = attribute.getValue() - temp;
            this.setHealth(temp > 0 ? this.getHealth() + (float) temp : this.getHealth());
        }
        if (this.stage == stage) return;
        this.stage = stage;
        if (reset) {
            this.refreshAge();
        }
        this.reapplyPosition();
        this.refreshDimensions();
        if (sync) {
            CHANNEL.send(TRACKING_ENTITY.with(() -> this), new SSyncDragonAgePacket(this));
        }
    }

    @Override
    public void travel(Vector3d travelVector) {
        // disable method while flying, the movement is done entirely by
        // moveEntity() and this one just makes the dragon to fall slowly when
        if (!this.isFlying()) {
            super.travel(travelVector);
        }
    }

    @Override
    public void setAge(int age) {
        if (this.age == age) return;
        if (this.age < 0 && age >= 0 || this.age > 0 && age <= 0) {
            this.ageBoundaryReached();
        } else {
            this.age = age;
        }
        CHANNEL.send(TRACKING_ENTITY.with(() -> this), new SSyncDragonAgePacket(this));
    }

    public final void openInventory(ServerPlayerEntity player) {
        NetworkHooks.openGui(player, this.inventory, this::writeId);
    }
}
