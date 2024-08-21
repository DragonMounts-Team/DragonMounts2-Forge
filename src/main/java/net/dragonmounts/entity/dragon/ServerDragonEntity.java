package net.dragonmounts.entity.dragon;

import net.dragonmounts.api.IDragonFood;
import net.dragonmounts.block.DragonCoreBlock;
import net.dragonmounts.config.ServerConfig;
import net.dragonmounts.data.tag.DMItemTags;
import net.dragonmounts.entity.ai.DragonFollowOwnerGoal;
import net.dragonmounts.entity.ai.DragonHurtByTargetGoal;
import net.dragonmounts.entity.ai.PlayerControlledGoal;
import net.dragonmounts.init.DMBlocks;
import net.dragonmounts.init.DMEntities;
import net.dragonmounts.init.DMItems;
import net.dragonmounts.init.DragonTypes;
import net.dragonmounts.inventory.DragonInventory;
import net.dragonmounts.inventory.LimitedSlot;
import net.dragonmounts.item.DragonEssenceItem;
import net.dragonmounts.network.CRideDragonPacket;
import net.dragonmounts.network.SFeedDragonPacket;
import net.dragonmounts.network.SSyncDragonAgePacket;
import net.dragonmounts.registry.DragonType;
import net.dragonmounts.registry.DragonVariant;
import net.dragonmounts.util.DragonFood;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
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
import net.minecraft.item.SaddleItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.Effects;
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
import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import static net.dragonmounts.network.DMPacketHandler.CHANNEL;
import static net.dragonmounts.util.EntityUtil.addOrMergeEffect;
import static net.dragonmounts.util.EntityUtil.addOrResetEffect;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;
import static net.minecraftforge.fml.network.PacketDistributor.PLAYER;
import static net.minecraftforge.fml.network.PacketDistributor.TRACKING_ENTITY;

public class ServerDragonEntity extends TameableDragonEntity {
    public final PlayerControlledGoal playerControlledGoal;
    public final DragonFollowOwnerGoal followOwnerGoal;
    protected ServerPlayerEntity controller;
    protected boolean climbing;
    protected boolean descending;
    protected boolean convergePitch;
    protected boolean convergeYaw;

    public ServerDragonEntity(EntityType<? extends TameableDragonEntity> type, World level) {
        super(type, level);
        this.goalSelector.addGoal(0, this.playerControlledGoal = new PlayerControlledGoal(this));
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(3, this.followOwnerGoal = new DragonFollowOwnerGoal(this));
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new DragonHurtByTargetGoal(this));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, MobEntity.class, 5, false, false, entity -> entity instanceof IMob));
    }

    @Override
    public void setSaddle(@Nonnull ItemStack stack, boolean sync) {
        boolean original = this.isSaddled;
        this.isSaddled = stack.getItem() instanceof SaddleItem;
        if (!stack.isEmpty()) {
            stack.setCount(1);
        }
        this.saddle = stack;
        if (!original && this.isSaddled) {
            this.playSound(SoundEvents.HORSE_SADDLE, 0.5F, 1.0F);
        }
        if (sync) {
            this.entityData.set(DATA_SADDLE_ITEM, stack.copy());
        }
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
    public void addAdditionalSaveData(CompoundNBT tag) {
        super.addAdditionalSaveData(tag);
        tag.putString(DragonVariant.DATA_PARAMETER_KEY, this.getVariant().getSerializedName().toString());
        tag.putString(DragonLifeStage.DATA_PARAMETER_KEY, this.stage.getSerializedName());
        tag.putBoolean(AGE_LOCKED_DATA_PARAMETER_KEY, this.isAgeLocked());
        tag.putInt(SHEARED_DATA_PARAMETER_KEY, this.isSheared() ? this.shearCooldown : 0);
        ListNBT items = this.inventory.createTag();
        if (!items.isEmpty()) {
            tag.put(DragonInventory.DATA_PARAMETER_KEY, items);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT tag) {
        int age = this.age;
        DragonLifeStage stage = this.stage;
        if (tag.contains(DragonLifeStage.DATA_PARAMETER_KEY)) {
            this.setLifeStage(DragonLifeStage.byName(tag.getString(DragonLifeStage.DATA_PARAMETER_KEY)), false, false);
        }
        super.readAdditionalSaveData(tag);
        if (!this.firstTick && (this.age != age || stage != this.stage)) {
            CHANNEL.send(TRACKING_ENTITY.with(this::getEntity), new SSyncDragonAgePacket(this));
        }
        if (tag.contains(DragonVariant.DATA_PARAMETER_KEY)) {
            this.setVariant(DragonVariant.byName(tag.getString(DragonVariant.DATA_PARAMETER_KEY)));
        } else if (tag.contains(DragonType.DATA_PARAMETER_KEY)) {
            this.setVariant(DragonType.byName(tag.getString(DragonType.DATA_PARAMETER_KEY)).variants.draw(this.random, null));
        }
        if (tag.contains(SADDLE_DATA_PARAMETER_KEY)) {
            this.setSaddle(ItemStack.of(tag.getCompound(SADDLE_DATA_PARAMETER_KEY)), true);
        }
        if (tag.contains(SHEARED_DATA_PARAMETER_KEY)) {
            this.setSheared(tag.getInt(SHEARED_DATA_PARAMETER_KEY));
        }
        if (tag.contains(AGE_LOCKED_DATA_PARAMETER_KEY)) {
            this.setAgeLocked(tag.getBoolean(AGE_LOCKED_DATA_PARAMETER_KEY));
        }
        if (tag.contains(DragonInventory.DATA_PARAMETER_KEY)) {
            this.inventory.fromTag(tag.getList(DragonInventory.DATA_PARAMETER_KEY, 10));
        }
    }

    public void liftOff() {
        if (!this.isBaby()) {
            boolean flag = this.isVehicle() && (this.isInLava() || this.isInWaterOrBubble());
            this.hasImpulse = true;
            Vector3d current = this.getDeltaMovement();
            // stronger jump for an easier lift-off
            this.setDeltaMovement(current.x, current.y + (flag ? 0.7 : 6), current.z);
        }
    }

    public void spawnEssence(ItemStack stack) {
        BlockPos pos = this.blockPosition();
        World level = this.level;
        BlockState state = DMBlocks.DRAGON_CORE.defaultBlockState().setValue(HORIZONTAL_FACING, this.getDirection());
        if (!DragonCoreBlock.tryPlaceAt(level, pos, state, stack)) {
            int y = pos.getY(), max = Math.min(y + 5, level.getMaxBuildHeight());
            BlockPos.Mutable mutable = pos.mutable();
            while (++y < max) {
                mutable.setY(y);
                if (DragonCoreBlock.tryPlaceAt(level, mutable, state, stack)) {
                    return;
                }
            }
        } else return;
        level.addFreshEntity(new ItemEntity(level, this.getX(), this.getY(), this.getZ(), stack));
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
        IDragonFood food = DragonFood.get(item);
        if (food != IDragonFood.UNKNOWN) {
            if (!food.canFeed(this, player, stack, hand)) return ActionResultType.FAIL;
            food.feed(this, player, stack, hand);
            CHANNEL.send(TRACKING_ENTITY.with(this::getEntity), new SFeedDragonPacket(this, item));
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
            if (!ServerConfig.INSTANCE.debug.get() || player.isSecondaryUseActive()) {
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
                player.startRiding(this, false);
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

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void startSeenByPlayer(ServerPlayerEntity player) {
        CHANNEL.send(PLAYER.with((Supplier) player::getEntity), new SSyncDragonAgePacket(this));
    }

    @Override
    public void setLifeStage(DragonLifeStage stage, boolean reset, boolean sync) {
        AttributeModifier modifier = stage.modifier;
        ModifiableAttributeInstance health = this.getAttribute(Attributes.MAX_HEALTH);
        ModifiableAttributeInstance damage = this.getAttribute(Attributes.ATTACK_DAMAGE);
        ModifiableAttributeInstance armor = this.getAttribute(Attributes.ARMOR);
        assert health != null && damage != null && armor != null;
        double temp = health.getValue();
        health.removeModifier(STAGE_MODIFIER_UUID);
        health.addTransientModifier(modifier);
        temp = health.getValue() - temp;
        this.setHealth(temp > 0 ? this.getHealth() + (float) temp : this.getHealth());
        damage.removeModifier(STAGE_MODIFIER_UUID);
        damage.addTransientModifier(modifier);
        armor.removeModifier(STAGE_MODIFIER_UUID);
        armor.addTransientModifier(modifier);
        if (reset) {
            this.stage = stage;
            this.refreshAge();
        } else if (this.stage != stage) {
            this.stage = stage;
        } else return;
        this.reapplyPosition();
        this.refreshDimensions();
        if (sync) {
            CHANNEL.send(TRACKING_ENTITY.with(this::getEntity), new SSyncDragonAgePacket(this));
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
    public boolean isControlledByLocalInstance() {
        return false;
    }

    @Override
    public void setAge(int age) {
        if (this.age == age) return;
        if (this.age < 0 && age >= 0 || this.age > 0 && age <= 0) {
            this.ageBoundaryReached();
        } else {
            this.age = age;
        }
        CHANNEL.send(TRACKING_ENTITY.with(this::getEntity), new SSyncDragonAgePacket(this));
    }

    public final void openInventory(ServerPlayerEntity player) {
        NetworkHooks.openGui(player, this.inventory, this::writeId);
    }

    @Override
    public void setOwnerUUID(@Nullable UUID uuid) {
        super.setOwnerUUID(uuid);
        this.followOwnerGoal.stop();
    }

    @Nullable
    @Override
    public Entity getControllingPassenger() {
        // In most cases, the controlling passenger would be the player
        return this.controller == null ? super.getControllingPassenger() : this.controller;
    }

    @Nullable
    @Override
    public ServerPlayerEntity getControllingPlayer() {
        return this.controller;
    }

    @Override
    protected void addPassenger(Entity entity) {
        super.addPassenger(entity);
        if (this.controller == null && entity instanceof ServerPlayerEntity) {
            this.controller = (ServerPlayerEntity) entity;
            this.setOrderedToSit(false);
            this.getNavigation().stop();
        }
    }

    @Override
    protected void removePassenger(Entity entity) {
        super.removePassenger(entity);
        List<Entity> passengers = this.getPassengers();
        if (passengers.isEmpty()) {
            this.controller = null;
        } else if (entity == this.controller) {
            Entity candidate = passengers.get(0);
            this.controller = candidate instanceof ServerPlayerEntity ? (ServerPlayerEntity) candidate : null;
        }
    }

    public void updateInput(CRideDragonPacket packet) {
        int flag = packet.flag;
        this.climbing = (flag & 0b0001) == 0b0001;
        this.descending = (flag & 0b0010) == 0b0010;
        this.convergePitch = (flag & 0b0100) == 0b0100;
        this.convergeYaw = (flag & 0b1000) == 0b1000;
        LOGGER.info("updateInput {}", flag);
    }

}
