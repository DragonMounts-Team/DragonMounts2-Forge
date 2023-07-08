package net.dragonmounts3.entity.dragon;

import net.dragonmounts3.DragonMountsConfig;
import net.dragonmounts3.api.DragonType;
import net.dragonmounts3.api.IDragonFood;
import net.dragonmounts3.api.IMutableDragonTypified;
import net.dragonmounts3.entity.dragon.helper.DragonBodyHelper;
import net.dragonmounts3.entity.dragon.helper.DragonHelper;
import net.dragonmounts3.init.DMAttributes;
import net.dragonmounts3.init.DMEntities;
import net.dragonmounts3.init.DMItems;
import net.dragonmounts3.init.DMSounds;
import net.dragonmounts3.inventory.DragonInventory;
import net.dragonmounts3.item.DragonScalesItem;
import net.dragonmounts3.item.TieredShearsItem;
import net.dragonmounts3.network.SFeedDragonPacket;
import net.dragonmounts3.network.SSyncDragonAgePacket;
import net.dragonmounts3.util.DragonFood;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IForgeShearable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

import static net.dragonmounts3.network.DMPacketHandler.CHANNEL;
import static net.minecraftforge.fml.network.PacketDistributor.PLAYER;
import static net.minecraftforge.fml.network.PacketDistributor.TRACKING_ENTITY;

/**
 * @see net.minecraft.entity.passive.horse.MuleEntity
 * @see net.minecraft.entity.passive.horse.HorseEntity
 */
@ParametersAreNonnullByDefault
public class TameableDragonEntity extends TameableEntity implements IEquipable, IForgeShearable, IMutableDragonTypified {
    // base attributes
    public static final double BASE_GROUND_SPEED = 0.4;
    public static final double BASE_AIR_SPEED = 0.9;
    public static final double BASE_DAMAGE = DragonMountsConfig.BASE_DAMAGE.get();
    public static final double BASE_ARMOR = DragonMountsConfig.BASE_ARMOR.get();
    public static final double BASE_TOUGHNESS = 30.0D;
    public static final float RESISTANCE = 10.0f;
    public static final double BASE_FOLLOW_RANGE = 70;
    public static final double BASE_FOLLOW_RANGE_FLYING = BASE_FOLLOW_RANGE * 2;
    public static final int HOME_RADIUS = 64;
    public static final double IN_AIR_THRESH = 10;
    private static final Logger LOGGER = LogManager.getLogger();

    // data value IDs
    private static final DataParameter<String> DATA_DRAGON_TYPE = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.STRING);
    private static final DataParameter<Boolean> DATA_FLYING = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DATA_SADDLED = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DATA_AGE_LOCKED = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DATA_BREATHING = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DATA_ALT_BREATHING = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> GOING_DOWN = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DATA_CHESTED = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> ALLOW_OTHER_PLAYERS = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> BOOSTING = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HOVER_CANCELLED = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> Y_LOCKED = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> ALT_TEXTURE = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> FOLLOW_YAW = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Optional<UUID>> DATA_BREEDER = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.OPTIONAL_UUID);
    private static final DataParameter<String> VARIANT = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.STRING);
    private static final DataParameter<Integer> DATA_REPO_COUNT = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.INT);
    private static final DataParameter<Boolean> DATA_SHEARED = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HAS_ADJUDICATOR_STONE = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HAS_ELDER_STONE = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    //private static final DataParameter<Boolean> SLEEP = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<String> DATA_BREATH_WEAPON_TARGET = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.STRING);
    private static final DataParameter<Integer> DATA_BREATH_WEAPON_MODE = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.INT);
    public static final String AGE_DATA_PARAMETER_KEY = "Age";
    public static final String AGE_LOCKED_DATA_PARAMETER_KEY = "AgeLocked";
    public static final String FLYING_DATA_PARAMETER_KEY = "Flying";
    public static final String SADDLE_DATA_PARAMETER_KEY = "Saddle";
    public static final String SHEARED_DATA_PARAMETER_KEY = "ShearCooldown";
    private final DragonBodyHelper dragonBodyHelper = new DragonBodyHelper(this);
    private final Map<Class<?>, DragonHelper> helpers = new HashMap<>();
    public EnderCrystalEntity healingEnderCrystal;
    protected DragonInventory inventory = new DragonInventory(this);
    protected DragonType type = DragonType.ENDER;
    protected DragonLifeStage stage = DragonLifeStage.ADULT;
    private int shearCooldown;
    public int inAirTicks;
    public int roarTicks;
    protected int ticksSinceLastAttack;
    private boolean hasChestVarChanged = false;
    private boolean isUsingBreathWeapon;
    private boolean altBreathing;
    private boolean isGoingDown;
    private boolean isUnHovered;
    private boolean yLocked;
    private boolean followYaw;

    public TameableDragonEntity(EntityType<? extends TameableDragonEntity> type, World world) {
        super(type, world);
        this.maxUpStep = 1.0F;
        this.blocksBuilding = true;
        createInventory();
        Objects.requireNonNull(this.getAttributes().getInstance(Attributes.MAX_HEALTH)).setBaseValue(DragonMountsConfig.BASE_HEALTH.get());
    }

    public TameableDragonEntity(World world) {
        this(DMEntities.TAMEABLE_DRAGON.get(), world);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, DragonMountsConfig.BASE_HEALTH.get())
                .add(DMAttributes.FLIGHT_SPEED.get(), BASE_AIR_SPEED)
                .add(Attributes.MOVEMENT_SPEED, BASE_GROUND_SPEED)
                .add(Attributes.ATTACK_DAMAGE, BASE_DAMAGE)
                .add(Attributes.FOLLOW_RANGE, BASE_FOLLOW_RANGE)
                .add(Attributes.KNOCKBACK_RESISTANCE, RESISTANCE)
                .add(Attributes.ARMOR, BASE_ARMOR)
                .add(Attributes.ARMOR_TOUGHNESS, BASE_TOUGHNESS);
    }

    public DragonInventory getInventory() {
        return this.inventory;
    }

    public void containerChanged() {
        boolean flag = this.isSaddled();
        this.updateContainerEquipment();
        if (this.tickCount > 20 && !flag && this.isSaddled()) {
            this.playSound(SoundEvents.HORSE_SADDLE, 0.5F, 1.0F);
        }

    }

    //----------Entity----------

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_CHESTED, false);
        this.entityData.define(DATA_DRAGON_TYPE, DragonType.ENDER.getSerializedName());
        this.entityData.define(DATA_FLYING, false);
        this.entityData.define(DATA_SADDLED, false);
        this.entityData.define(DATA_SHEARED, false);
        this.entityData.define(DATA_AGE_LOCKED, false);
    }

    protected void saveChestData(CompoundNBT compound) {
        if (this.hasChest()) {
            /*compound.putBoolean("Chested", true);
            ListNBT listnbt = new ListNBT();
            for (int i = 2; i < this.inventory.getContainerSize(); ++i) {
                ItemStack itemstack = this.inventory.getItem(i);
                if (!itemstack.isEmpty()) {
                    CompoundNBT compoundnbt = new CompoundNBT();
                    compoundnbt.putByte("Slot", (byte) i);
                    itemstack.save(compoundnbt);
                    listnbt.add(compoundnbt);
                }
            }
            compound.put("Items", listnbt);*/
        } else {
            compound.putBoolean("Chested", false);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putString(DragonType.DATA_PARAMETER_KEY, this.entityData.get(DATA_DRAGON_TYPE));
        compound.putString(DragonLifeStage.DATA_PARAMETER_KEY, this.stage.getSerializedName());
        compound.putBoolean(FLYING_DATA_PARAMETER_KEY, this.entityData.get(DATA_FLYING));
        compound.putBoolean(AGE_LOCKED_DATA_PARAMETER_KEY, this.entityData.get(DATA_AGE_LOCKED));
        compound.putInt(SHEARED_DATA_PARAMETER_KEY, this.isSheared() ? this.shearCooldown : 0);
        this.saveChestData(compound);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        int age = this.age;
        DragonLifeStage stage = this.stage;
        if (compound.contains(DragonLifeStage.DATA_PARAMETER_KEY)) {
            this.setLifeStage(DragonLifeStage.byName(compound.getString(DragonLifeStage.DATA_PARAMETER_KEY)), false, false);
        }
        super.readAdditionalSaveData(compound);
        if (!this.firstTick && (this.age != age || stage != this.stage)) {
            CHANNEL.send(TRACKING_ENTITY.with(() -> this), new SSyncDragonAgePacket(this));
        }
        this.setDragonType(DragonType.byName(compound.getString(DragonType.DATA_PARAMETER_KEY)));
        if (compound.contains(FLYING_DATA_PARAMETER_KEY)) {
            this.entityData.set(DATA_FLYING, compound.getBoolean(FLYING_DATA_PARAMETER_KEY));
        }
        if (compound.contains(SADDLE_DATA_PARAMETER_KEY)) {
            this.inventory.setSaddle(ItemStack.of(compound.getCompound(SADDLE_DATA_PARAMETER_KEY)));
        }
        if (compound.contains(SHEARED_DATA_PARAMETER_KEY)) {
            this.setSheared(compound.getInt(SHEARED_DATA_PARAMETER_KEY));
        }
        if (compound.contains(AGE_LOCKED_DATA_PARAMETER_KEY)) {
            this.entityData.set(DATA_FLYING, compound.getBoolean(FLYING_DATA_PARAMETER_KEY));
        }
        this.updateContainerEquipment();
    }

    @Override
    public void onSyncedDataUpdated(DataParameter<?> key) {
        if (DATA_DRAGON_TYPE.equals(key)) {
            this.type = DragonType.byName(this.entityData.get(DATA_DRAGON_TYPE));
        } else {
            super.onSyncedDataUpdated(key);
        }
    }

    @Override
    public void aiStep() {
        int age = this.age;
        if (this.level.isClientSide) {
            super.aiStep();
            if (!this.isAgeLocked()) {
                if (age < 0) {
                    ++this.age;
                } else if (age > 0) {
                    --this.age;
                }
            }
        } else {
            if (this.shearCooldown > 0) {
                this.setSheared(this.shearCooldown - 1);
            }
            if (this.isAgeLocked()) {
                this.age = 0;
                super.aiStep();
                this.age = age;
            } else {
                super.aiStep();
            }
        }
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld serverWorld, AgeableEntity entity) {
        return null;
    }

    public boolean isFlying() {
        return this.entityData.get(DATA_FLYING);
    }

    public boolean hasChest() {
        return this.entityData.get(DATA_CHESTED);
    }

    protected void updateContainerEquipment() {
        if (!this.level.isClientSide) {
            //this.entityData.set(DATA_SADDLED, !this.inventory.getItem(0).isEmpty());
        }
    }

    protected void createInventory() {

        //this.updateContainerEquipment();
        //this.itemHandler = net.minecraftforge.common.util.LazyOptional.of(() -> new net.minecraftforge.items.wrapper.InvWrapper(this.inventory));
    }

    @Override
    public void refreshDimensions() {
        double d0 = this.getX();
        double d1 = this.getY();
        double d2 = this.getZ();
        super.refreshDimensions();
        this.setPos(d0, d1, d2);
    }

    @Override
    public float getScale() {
        return DragonLifeStage.getSize(this.stage, this.age);
    }

    @Nonnull
    @Override
    public EntitySize getDimensions(Pose pose) {
        return super.getDimensions(pose).scale(DragonLifeStage.getSize(this.stage, this.stage.duration >> 1));
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return DragonFood.test(stack.getItem());
    }

    @Nonnull
    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        Item item = stack.getItem();
        if (this.level.isClientSide) {
            if (DragonFood.test(item)) return ActionResultType.CONSUME;
            return ActionResultType.PASS;
        }
        IDragonFood food = DragonFood.get(item);
        if (food != null) {
            food.eat(this, player, stack, hand);
            CHANNEL.send(TRACKING_ENTITY.with(() -> this), new SFeedDragonPacket(this, item));
            if (this.stage == DragonLifeStage.ADULT && this.canFallInLove()) {
                this.setInLove(player);
            }
            return ActionResultType.SUCCESS;
        }/*
        if (this.isOwnedBy(player)) {
            NetworkHooks.openGui((ServerPlayerEntity) player, this.inventory, buffer -> {
                buffer.writeVarInt(this.getId());
            });
        }*/
        return ActionResultType.PASS;
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        return super.hurt(pSource, pAmount);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        Entity entity = source.getEntity();
        return (entity != null && (entity == this || this.hasPassenger(entity))) || super.isInvulnerableTo(source) || this.getDragonType().isInvulnerableTo(source);
    }

    @Override
    public void startSeenByPlayer(ServerPlayerEntity player) {
        CHANNEL.send(PLAYER.with(() -> player), new SSyncDragonAgePacket(this));
    }

    public void setLifeStage(DragonLifeStage stage, boolean reset, boolean sync) {
        if (this.stage == stage) return;
        this.stage = stage;
        if (reset) {
            this.refreshAge();
        }
        this.refreshDimensions();
        this.reapplyPosition();
        if (sync && !this.level.isClientSide) {
            CHANNEL.send(TRACKING_ENTITY.with(() -> this), new SSyncDragonAgePacket(this));
        }
    }

    public final DragonLifeStage getLifeStage() {
        return this.stage;
    }

    @Override
    public boolean isPersistenceRequired() {
        return true;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(DMItems.DRAGON_SPAWN_EGG.get(this.getDragonType()));
    }

    public void applyPacket(SSyncDragonAgePacket packet) {
        this.age = packet.age;
        this.setLifeStage(packet.stage, false, false);
    }

    //----------AgeableEntity----------

    protected void refreshAge() {
        switch (this.stage) {
            case NEWBORN:
            case INFANT:
                this.age = -this.stage.duration;
                break;
            case JUVENILE:
            case PREJUVENILE:
                this.age = this.stage.duration;
                break;
            default:
                this.age = 0;
        }
    }

    public void setAgeLocked(boolean locked) {
        this.entityData.set(DATA_AGE_LOCKED, locked);
    }

    public boolean isAgeLocked() {
        return this.entityData.get(DATA_AGE_LOCKED);
    }

    @Override
    protected void ageBoundaryReached() {
        this.setLifeStage(DragonLifeStage.byId(this.stage.ordinal() + 1), true, false);
    }

    /**
     * Call the field {@link TameableDragonEntity#age} directly for internal use.
     */
    public void setAgeAsync(int age) {
        this.age = age;
    }

    @Override
    public void setAge(int age) {
        if (this.age == age) return;
        if (this.level.isClientSide) {
            this.age = age;
            return;
        }
        if (this.age < 0 && age >= 0 || this.age > 0 && age <= 0) {
            this.ageBoundaryReached();
        } else {
            this.age = age;
        }
        CHANNEL.send(TRACKING_ENTITY.with(() -> this), new SSyncDragonAgePacket(this));
    }

    @Override
    public void ageUp(int delta, boolean forced) {
        int backup = this.age;
        //Notice:                                ↓↓           ↓↓                             ↓↓           ↓↓
        if (!this.isAgeLocked() && (this.age < 0 && (this.age += delta) >= 0 || this.age > 0 && (this.age -= delta) <= 0)) {
            this.ageBoundaryReached();
            if (forced) {
                this.forcedAge += backup < 0 ? -backup : backup;
            }
        }
    }

    @Override
    public final int getAge() {
        return this.age;
    }

    @Override
    public void setBaby(boolean value) {
        this.setLifeStage(value ? DragonLifeStage.NEWBORN : DragonLifeStage.ADULT, true, true);
    }

    public void refreshForcedAgeTimer() {
        if (this.forcedAgeTimer <= 0) {
            this.forcedAgeTimer = 40;
        }
    }

    //----------IMutableDragonTypified----------

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

    //----------IForgeShearable----------

    public boolean isSheared() {
        return this.entityData.get(DATA_SHEARED);
    }

    public void setSheared(int cooldown) {
        this.shearCooldown = cooldown;
        this.entityData.set(DATA_SHEARED, cooldown > 0);
    }

    @Override
    public boolean isShearable(@Nonnull ItemStack stack, World world, BlockPos pos) {
        Item item = stack.getItem();
        return this.isAlive() && this.stage.ordinal() >= 2 && !this.isSheared() && item instanceof TieredShearsItem && ((TieredShearsItem) item).getTier().getLevel() >= 3;
    }

    //----------IEquipable----------

    @Override
    public boolean isSaddled() {
        return this.entityData.get(DATA_SADDLED);
    }

    @Override
    public boolean isSaddleable() {
        return this.isAlive() && !this.isBaby();
    }

    @Override
    public void equipSaddle(@Nullable SoundCategory category) {

    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nullable PlayerEntity player, @Nonnull ItemStack stack, World world, BlockPos pos, int fortune) {
        DragonScalesItem scale = DMItems.DRAGON_SCALES.get(this.getDragonType());
        if (scale != null) {
            this.setSheared(2500 + this.random.nextInt(1000));
            this.playSound(DMSounds.ENTITY_DRAGON_GROWL.get(), 1.0F, 1.0F);
            return Collections.singletonList(new ItemStack(scale, 2 + this.random.nextInt(3)));
        }
        return Collections.emptyList();
    }

    public static CompoundNBT simplifyData(CompoundNBT compound) {
        compound.remove(FLYING_DATA_PARAMETER_KEY);
        compound.remove("Air");
        compound.remove("DeathTime");
        compound.remove("FallDistance");
        compound.remove("FallFlying");
        compound.remove("Fire");
        compound.remove("HurtByTimestamp");
        compound.remove("HurtTime");
        compound.remove("InLove");
        compound.remove("Leash");
        compound.remove("Motion");
        compound.remove("OnGround");
        compound.remove("PortalCooldown");
        compound.remove("Pos");
        compound.remove("Rotation");
        compound.remove("SleepingX");
        compound.remove("SleepingY");
        compound.remove("SleepingZ");
        compound.remove("TicksFrozen");
        return compound;
    }
}