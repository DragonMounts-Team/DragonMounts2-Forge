package net.dragonmounts3.entity.dragon;

import net.dragonmounts3.DragonMountsConfig;
import net.dragonmounts3.api.DragonType;
import net.dragonmounts3.api.IMutableDragonTypified;
import net.dragonmounts3.data.tags.DMItemTags;
import net.dragonmounts3.entity.dragon.helper.DragonBodyHelper;
import net.dragonmounts3.entity.dragon.helper.DragonHelper;
import net.dragonmounts3.inits.ModAttributes;
import net.dragonmounts3.inits.ModEntities;
import net.dragonmounts3.inits.ModItems;
import net.dragonmounts3.inits.ModSounds;
import net.dragonmounts3.inventory.DragonInventoryContainer;
import net.dragonmounts3.item.DragonScalesItem;
import net.dragonmounts3.item.TieredShearsItem;
import net.dragonmounts3.network.SEatItemParticlePacket;
import net.dragonmounts3.network.SUpdateAgePacket;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
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
    private static final DataParameter<String> DATA_LIFE_STAGE = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.STRING);
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
    private static final DataParameter<String> VARIANT = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.STRING);
    private static final DataParameter<Integer> DATA_REPO_COUNT = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.INT);
    private static final DataParameter<Boolean> DATA_SHEARED = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HAS_ADJUDICATOR_STONE = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HAS_ELDER_STONE = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    //private static final DataParameter<Boolean> SLEEP = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<String> DATA_BREATH_WEAPON_TARGET = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.STRING);
    private static final DataParameter<Integer> DATA_BREATH_WEAPON_MODE = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.INT);
    public static final String AGE_DATA_PARAMETER_KEY = "Age";
    public static final String FLYING_DATA_PARAMETER_KEY = "Flying";
    public static final String SADDLE_DATA_PARAMETER_KEY = "Saddle";
    public static final String SHEARED_DATA_PARAMETER_KEY = "ShearCooldown";
    private static final LazyValue<Ingredient> DRAGON_FOOD = new LazyValue<>(() -> Ingredient.of(DMItemTags.DRAGON_FOOD));
    private final DragonBodyHelper dragonBodyHelper = new DragonBodyHelper(this);
    private final Map<Class<?>, DragonHelper> helpers = new HashMap<>();
    public EnderCrystalEntity healingEnderCrystal;
    protected DragonInventoryContainer inventory;
    private int shearCooldown;
    private int ageSnapshot = 0;
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
        this(ModEntities.TAMEABLE_DRAGON.get(), world);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, DragonMountsConfig.BASE_HEALTH.get())
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
        this.entityData.define(DATA_DRAGON_TYPE, DragonType.ENDER.getSerializedName());
        this.entityData.define(DATA_LIFE_STAGE, DragonLifeStage.ADULT.getSerializedName());
        this.entityData.define(DATA_FLYING, false);
        this.entityData.define(DATA_SADDLED, false);
        this.entityData.define(DATA_SHEARED, false);
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
        compound.putString(DragonLifeStage.DATA_PARAMETER_KEY, this.entityData.get(DATA_LIFE_STAGE));
        compound.putBoolean(FLYING_DATA_PARAMETER_KEY, this.entityData.get(DATA_FLYING));
        compound.putInt(SHEARED_DATA_PARAMETER_KEY, this.isSheared() ? this.shearCooldown : 0);
        this.saveChestData(compound);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        this.ageSnapshot = this.age;
        if (compound.contains(DragonLifeStage.DATA_PARAMETER_KEY)) {
            this.entityData.set(DATA_LIFE_STAGE, compound.getString(DragonLifeStage.DATA_PARAMETER_KEY));
            this.refreshDimensions();
        }
        super.readAdditionalSaveData(compound);
        if (this.ageSnapshot != this.age) {
            SUpdateAgePacket.send(this);
        }
        this.setDragonType(DragonType.byName(compound.getString(DragonType.DATA_PARAMETER_KEY)));
        if (compound.contains(FLYING_DATA_PARAMETER_KEY)) {
            this.entityData.set(DATA_FLYING, compound.getBoolean(FLYING_DATA_PARAMETER_KEY));
        }
        if (compound.contains(SADDLE_DATA_PARAMETER_KEY)) {
            ItemStack itemstack = ItemStack.of(compound.getCompound(SADDLE_DATA_PARAMETER_KEY));
            if (itemstack.getItem() == Items.SADDLE) {
                this.inventory.setItem(0, itemstack);
            }
        }
        if (compound.contains(SHEARED_DATA_PARAMETER_KEY)) {
            this.setSheared(compound.getInt(SHEARED_DATA_PARAMETER_KEY));
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

    @Override
    public void tick() {
        super.tick();
        if (this.level.isClientSide) {
            int i = this.age;
            if (i < 0) {
                ++this.age;
            } else if (i > 0) {
                --this.age;
            }
        } else {
            if (this.shearCooldown > 0) {
                this.setSheared(this.shearCooldown - 1);
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
        DragonLifeStage stage = this.getLifeStage();
        return super.getDimensions(pose).scale(DragonLifeStage.getSize(stage, stage.duration >> 1));
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return DRAGON_FOOD.get().test(stack);
    }

    protected void usePlayerItem(PlayerEntity player, ItemStack stack) {
        int age = this.getAge();
        Item item = stack.getItem();
        //TODO: make increase in age related to food
        if (this.level.isClientSide) {
            if (age > 0) {
                this.age = age - 2000;
                if (this.forcedAgeTimer <= 0) {
                    this.forcedAgeTimer = 40;
                }
            } else if (age < 0) {
                this.age = age + 2000;
                if (this.forcedAgeTimer <= 0) {
                    this.forcedAgeTimer = 40;
                }
            }
        } else {
            if (!player.abilities.instabuild) {
                stack.shrink(1);
                ItemStack result = null;
                if (item instanceof SoupItem) {
                    result = new ItemStack(Items.BOWL);
                } else if (item instanceof BucketItem) {
                    result = new ItemStack(Items.BUCKET);
                }
                if (result != null && !player.inventory.add(result)) {
                    player.drop(result, false);
                }
            }
            this.playSound(SoundEvents.GENERIC_EAT, 1f, 0.75f);
            if (item instanceof BucketItem) {
                this.playSound(SoundEvents.GENERIC_DRINK, 0.25f, 0.75f);
                if (item == Items.COD_BUCKET) {
                    SEatItemParticlePacket.send(this, Items.COD);
                } else if (item == Items.SALMON_BUCKET) {
                    SEatItemParticlePacket.send(this, Items.SALMON);
                } else {
                    SEatItemParticlePacket.send(this, Items.TROPICAL_FISH);
                }
            } else {
                SEatItemParticlePacket.send(this, item);
            }
            if (age > 0) {
                this.setAge(age - 2000);
            } else if (age < 0) {
                this.setAge(age + 2000);
            }
        }
    }

    @Nonnull
    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (this.isFood(itemstack)) {
            this.usePlayerItem(player, itemstack);
            if (this.level.isClientSide) {
                return ActionResultType.CONSUME;
            }
            if (this.getLifeStage() == DragonLifeStage.ADULT && this.canFallInLove()) {
                this.setInLove(player);
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
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
        return super.isInvulnerableTo(source) || this.getDragonType().isInvulnerableTo(source);
    }

    @Override
    public void startSeenByPlayer(ServerPlayerEntity player) {
        SUpdateAgePacket.send(player, this);
    }

    @Override
    public int getAge() {
        return this.age;
    }

    @Override
    public void setAge(int age) {
        if (this.level.isClientSide) {
            this.age = age;
        } else {
            int old = this.age;
            this.age = age;
            if (old < 0 && age >= 0 || old > 0 && age <= 0) {
                this.ageBoundaryReached();
            }
        }
    }

    @Override
    public void setBaby(boolean value) {
        this.setLifeStage(value ? DragonLifeStage.NEWBORN : DragonLifeStage.ADULT, true);
    }

    public void setLifeStage(DragonLifeStage stage, boolean reset) {
        this.entityData.set(DATA_LIFE_STAGE, stage.getSerializedName());
        if (reset) {
            switch (stage) {
                case NEWBORN:
                case INFANT:
                    this.age = -stage.duration;
                    break;
                case PREJUVENILE:
                case JUVENILE:
                    this.age = stage.duration;
                    break;
                case ADULT:
                    this.age = 0;
                    break;
            }
            this.ageSnapshot = this.age;
            SUpdateAgePacket.send(this);
        }
        this.refreshDimensions();
        this.reapplyPosition();
    }

    public DragonLifeStage getLifeStage() {
        return DragonLifeStage.byName(this.entityData.get(DATA_LIFE_STAGE));
    }

    @Override
    protected void ageBoundaryReached() {
        this.setLifeStage(DragonLifeStage.byId(this.getLifeStage().ordinal() + 1), true);
    }

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
        DragonType type = DragonType.byName(this.entityData.get(DATA_DRAGON_TYPE));
        if (type == null) {
            return DragonType.ENDER;
        }
        return type;
    }

    @Override
    public boolean isPersistenceRequired() {
        return true;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(ModItems.DRAGON_SPAWN_EGG.get(this.getDragonType()));
    }

    //IForgeShearable

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
        DragonLifeStage stage = this.getLifeStage();
        if (stage == null) {
            stage = DragonLifeStage.ADULT;
        }
        return this.isAlive() && stage.ordinal() >= 2 && !this.isSheared() && item instanceof TieredShearsItem && ((TieredShearsItem) item).getTier().getLevel() >= 3;
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nullable PlayerEntity player, @Nonnull ItemStack stack, World world, BlockPos pos, int fortune) {
        DragonScalesItem scale = ModItems.DRAGON_SCALES.get(this.getDragonType());
        if (scale != null) {
            this.setSheared(2500 + this.random.nextInt(1000));
            this.playSound(SoundEvents.ITEM_BREAK, 1.0F, 1.0F);
            this.playSound(ModSounds.ENTITY_DRAGON_GROWL.get(), 1.0F, 1.0F);
            return Collections.singletonList(new ItemStack(scale, 2 + this.random.nextInt(3)));
        }
        return Collections.emptyList();
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