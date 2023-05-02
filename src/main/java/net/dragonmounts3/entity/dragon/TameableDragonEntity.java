package net.dragonmounts3.entity.dragon;

import net.dragonmounts3.DragonMountsConfig;
import net.dragonmounts3.entity.dragon.config.DragonLifeStage;
import net.dragonmounts3.entity.dragon.helper.DragonBodyHelper;
import net.dragonmounts3.entity.dragon.helper.DragonHelper;
import net.dragonmounts3.inits.ModAttributes;
import net.dragonmounts3.inits.ModEntities;
import net.dragonmounts3.inventory.DragonInventoryContainer;
import net.dragonmounts3.registry.DragonType;
import net.dragonmounts3.registry.IMutableDragonTypified;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IForgeShearable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

/**
 * @see net.minecraft.entity.passive.horse.MuleEntity
 * @see net.minecraft.entity.passive.horse.HorseEntity
 */
@ParametersAreNonnullByDefault
public class TameableDragonEntity extends TameableEntity implements IInventory, IForgeShearable, IMutableDragonTypified {
    // base attributes
    public static final double BASE_GROUND_SPEED = 0.4;
    public static final double BASE_AIR_SPEED = 0.9;
    public static final double BASE_DAMAGE = DragonMountsConfig.BASE_DAMAGE.get();
    public static final double BASE_ARMOR = DragonMountsConfig.ARMOR.get();
    public static final double BASE_TOUGHNESS = 30.0D;
    public static final float RESISTANCE = 10.0f;
    public static final double BASE_FOLLOW_RANGE = 70;
    public static final double BASE_FOLLOW_RANGE_FLYING = BASE_FOLLOW_RANGE * 2;
    public static final int HOME_RADIUS = 64;
    public static final double IN_AIR_THRESH = 10;
    private static final Logger LOGGER = LogManager.getLogger();

    // data value IDs
    private static final DataParameter<Integer> DATA_DRAGON_TYPE = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.INT);
    private static final DataParameter<Integer> DATA_LIFE_STAGE = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.INT);
    private static final DataParameter<Boolean> DATA_FLYING = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DATA_SADDLED = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
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
    private static final DataParameter<String> DATA_BREED = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.STRING);
    private static final DataParameter<String> FOREST_TEXTURES = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.STRING);
    private static final DataParameter<Integer> DATA_REPO_COUNT = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.INT);
    private static final DataParameter<Integer> DATA_TICKS_SINCE_CREATION = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.INT);
    private static final DataParameter<Byte> DRAGON_SCALES = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BYTE);
    private static final DataParameter<Boolean> HAS_ADJUDICATOR_STONE = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HAS_ELDER_STONE = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    //private static final DataParameter<Boolean> SLEEP = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<String> DATA_BREATH_WEAPON_TARGET = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.STRING);
    private static final DataParameter<Integer> DATA_BREATH_WEAPON_MODE = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.INT);
    public static final String FLYING_DATA_PARAMETER_KEY = "Flying";
    public static final String SADDLE_DATA_PARAMETER_KEY = "Saddle";
    private final DragonBodyHelper dragonBodyHelper = new DragonBodyHelper(this);
    private final Map<Class<?>, DragonHelper> helpers = new HashMap<>();
    public EnderCrystalEntity healingEnderCrystal;
    protected DragonInventoryContainer inventory;
    public static int ticksShear;
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
        final double health = this.getDragonType().getConfig().getMaxHealth();
        Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(health);
        this.setHealth((float) health);
        this.maxUpStep = 1.0F;
        this.blocksBuilding = true;
        createInventory();
    }

    public TameableDragonEntity(World world) {
        this(ModEntities.TAMEABLE_DRAGON.get(), world);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(ModAttributes.FLIGHT_SPEED.get(), BASE_AIR_SPEED)
                .add(Attributes.MOVEMENT_SPEED, BASE_GROUND_SPEED)
                .add(Attributes.ATTACK_DAMAGE, BASE_DAMAGE)
                .add(Attributes.FOLLOW_RANGE, BASE_FOLLOW_RANGE)
                .add(Attributes.KNOCKBACK_RESISTANCE, RESISTANCE)
                .add(Attributes.ARMOR, BASE_ARMOR)
                .add(Attributes.ARMOR_TOUGHNESS, BASE_TOUGHNESS);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_CHESTED, false);
        this.entityData.define(DATA_DRAGON_TYPE, DragonType.ENDER.ordinal());
        this.entityData.define(DATA_LIFE_STAGE, DragonLifeStage.ADULT.ordinal());
        this.entityData.define(DATA_FLYING, false);
        this.entityData.define(DATA_SADDLED, false);
    }

    public CompoundNBT getData() {
        CompoundNBT compound = this.saveWithoutId(new CompoundNBT());
        this.addAdditionalSaveData(compound);
        compound.remove(FLYING_DATA_PARAMETER_KEY);
        compound.remove("Air");
        compound.remove("DeathTime");
        compound.remove("FallDistance");
        compound.remove("FallFlying");
        compound.remove("Fire");
        compound.remove("HurtByTimestamp");
        compound.remove("HurtTime");
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
        compound.putInt(DragonType.DATA_PARAMETER_KEY, this.entityData.get(DATA_DRAGON_TYPE));
        compound.putInt(DragonLifeStage.DATA_PARAMETER_KEY, this.entityData.get(DATA_LIFE_STAGE));
        compound.putBoolean(FLYING_DATA_PARAMETER_KEY, this.entityData.get(DATA_FLYING));
        this.saveChestData(compound);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains(DragonType.DATA_PARAMETER_KEY)) {
            this.entityData.set(DATA_DRAGON_TYPE, compound.getInt(DragonType.DATA_PARAMETER_KEY));
        }
        if (compound.contains(DragonLifeStage.DATA_PARAMETER_KEY)) {
            this.entityData.set(DATA_LIFE_STAGE, compound.getInt(DragonLifeStage.DATA_PARAMETER_KEY));
            this.refreshDimensions();
        }
        if (compound.contains(FLYING_DATA_PARAMETER_KEY)) {
            this.entityData.set(DATA_FLYING, compound.getBoolean(FLYING_DATA_PARAMETER_KEY));
        }
        if (compound.contains(SADDLE_DATA_PARAMETER_KEY)) {
            ItemStack itemstack = ItemStack.of(compound.getCompound(SADDLE_DATA_PARAMETER_KEY));
            if (itemstack.getItem() == Items.SADDLE) {
                this.inventory.setItem(0, itemstack);
            }
        }
        this.updateContainerEquipment();
    }

    @Override
    public void onSyncedDataUpdated(DataParameter<?> key) {
        if (DATA_LIFE_STAGE.equals(key)) {
            this.refreshDimensions();
            this.yRot = this.yHeadRot;
            this.yBodyRot = this.yHeadRot;
        }
        super.onSyncedDataUpdated(key);
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld serverWorld, AgeableEntity entity) {
        return null;
    }

    public boolean isFlying() {
        return this.entityData.get(DATA_FLYING);
    }

    public boolean isSaddled() {
        return this.entityData.get(DATA_SADDLED);
    }

    public boolean hasChest() {
        return this.entityData.get(DATA_CHESTED);
    }

    protected void updateContainerEquipment() {
        if (!this.level.isClientSide) {
            //this.entityData.set(DATA_SADDLED, !this.inventory.getItem(0).isEmpty());
        }
    }

    public void containerChanged(IInventory pInvBasic) {
        boolean flag = this.isSaddled();
        this.updateContainerEquipment();
        if (this.tickCount > 20 && !flag && this.isSaddled()) {
            this.playSound(SoundEvents.HORSE_SADDLE, 0.5F, 1.0F);
        }

    }

    protected void createInventory() {
        /*Inventory inventory = this.inventory;
        this.inventory = new DragonInventoryContainer(44, this);
        if (inventory != null) {
            int i = Math.min(inventory.getContainerSize(), this.inventory.getContainerSize());
            for (int j = 0; j < i; ++j) {
                ItemStack itemstack = inventory.getItem(j);
                if (!itemstack.isEmpty()) {
                    this.inventory.setItem(j, itemstack.copy());
                }
            }
        }*/
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
        return DragonLifeStage.getSize(this.getLifeStage(), this.age);
    }

    @Nonnull
    @Override
    public EntitySize getDimensions(Pose pose) {
        return super.getDimensions(pose).scale(this.getScale());
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        return super.hurt(pSource, pAmount);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        Entity entity = source.getEntity();
        if (entity != null) {
            if (entity == this || this.hasPassenger(entity)) {
                return true;
            }
        }
        return super.isInvulnerableTo(source) || this.getDragonType().getConfig().isInvulnerableTo(source);
    }

    public void setLifeStage(DragonLifeStage stage) {
        this.entityData.set(DATA_LIFE_STAGE, stage.ordinal());
        this.refreshDimensions();
        this.reapplyPosition();
    }

    public DragonLifeStage getLifeStage() {
        return DragonLifeStage.byId(this.entityData.get(DATA_LIFE_STAGE));
    }

    public void setDragonType(DragonType type, boolean reset) {
        this.entityData.set(DATA_DRAGON_TYPE, type.ordinal());
        if (reset) {
            final double health = this.getDragonType().getConfig().getMaxHealth();
            Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(health);
            this.setHealth((float) health);
        }
    }

    @Override
    public void setDragonType(DragonType type) {
        this.setDragonType(type, false);
    }

    @Override
    public DragonType getDragonType() {
        return DragonType.byId(this.entityData.get(DATA_DRAGON_TYPE));
    }

    @Override
    public boolean isPersistenceRequired() {
        return true;
    }

    //IInventory

    @Override
    public int getContainerSize() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getItem(int pIndex) {
        return null;
    }

    @Override
    public ItemStack removeItem(int pIndex, int pCount) {
        return null;
    }

    @Override
    public ItemStack removeItemNoUpdate(int pIndex) {
        return null;
    }

    @Override
    public void setItem(int pIndex, ItemStack pStack) {

    }

    @Override
    public void setChanged() {

    }

    @Override
    public boolean stillValid(PlayerEntity pPlayer) {
        return false;
    }

    @Override
    public void clearContent() {

    }
}