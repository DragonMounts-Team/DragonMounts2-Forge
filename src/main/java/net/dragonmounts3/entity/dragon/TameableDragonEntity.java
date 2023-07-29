package net.dragonmounts3.entity.dragon;

import net.dragonmounts3.DragonMountsConfig;
import net.dragonmounts3.api.DragonType;
import net.dragonmounts3.api.IDragonFood;
import net.dragonmounts3.api.IMutableDragonTypified;
import net.dragonmounts3.api.variant.AbstractVariant;
import net.dragonmounts3.api.variant.DragonVariants;
import net.dragonmounts3.block.entity.DragonCoreBlockEntity;
import net.dragonmounts3.client.DragonAnimator;
import net.dragonmounts3.data.tags.DMItemTags;
import net.dragonmounts3.entity.ai.DragonBodyController;
import net.dragonmounts3.entity.ai.DragonMovementController;
import net.dragonmounts3.entity.ai.PlayerControlledGoal;
import net.dragonmounts3.entity.path.DragonFlyingPathNavigator;
import net.dragonmounts3.init.DMBlocks;
import net.dragonmounts3.init.DMEntities;
import net.dragonmounts3.init.DMItems;
import net.dragonmounts3.init.DMSounds;
import net.dragonmounts3.inventory.DragonInventory;
import net.dragonmounts3.inventory.LimitedSlot;
import net.dragonmounts3.item.DragonArmorItem;
import net.dragonmounts3.item.DragonScalesItem;
import net.dragonmounts3.item.IDragonContainer;
import net.dragonmounts3.item.TieredShearsItem;
import net.dragonmounts3.network.CRideDragonPacket;
import net.dragonmounts3.network.SFeedDragonPacket;
import net.dragonmounts3.network.SSyncAppearancePacket;
import net.dragonmounts3.network.SSyncDragonAgePacket;
import net.dragonmounts3.util.DragonFood;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.ai.goal.SitGoal;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SaddleItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.PacketDistributor;
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
public class TameableDragonEntity extends TameableEntity implements IForgeShearable, IMutableDragonTypified, IFlyingAnimal {
    public static final UUID AGE_MODIFIER_UUID = UUID.fromString("2d147cda-121b-540e-bb24-435680aa374a");
    // base attributes
    public static final double BASE_GROUND_SPEED = 0.4;
    public static final double BASE_AIR_SPEED = 0.9;
    public static final double BASE_TOUGHNESS = 30.0D;
    public static final double BASE_FOLLOW_RANGE = 70;
    public static final double BASE_FOLLOW_RANGE_FLYING = BASE_FOLLOW_RANGE * 2;
    public static final int HOME_RADIUS = 64;
    public static final double LIFTOFF_THRESHOLD = 10;
    private static final Logger LOGGER = LogManager.getLogger();

    // data value IDs
    private static final DataParameter<Boolean> DATA_FLYING = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DATA_AGE_LOCKED = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DATA_BREATHING = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DATA_ALT_BREATHING = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> GOING_DOWN = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> ALLOW_OTHER_PLAYERS = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> BOOSTING = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HOVER_CANCELLED = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> ALT_TEXTURE = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Optional<UUID>> DATA_BREEDER = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.OPTIONAL_UUID);
    private static final DataParameter<Integer> DATA_REPO_COUNT = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.INT);
    private static final DataParameter<Boolean> DATA_SHEARED = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HAS_ADJUDICATOR_STONE = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HAS_ELDER_STONE = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    //private static final DataParameter<Boolean> SLEEP = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<String> DATA_BREATH_WEAPON_TARGET = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.STRING);
    private static final DataParameter<Integer> DATA_BREATH_WEAPON_MODE = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.INT);
    public static final DataParameter<ItemStack> DATA_SADDLE_ITEM = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.ITEM_STACK);
    public static final DataParameter<ItemStack> DATA_ARMOR_ITEM = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.ITEM_STACK);
    public static final DataParameter<ItemStack> DATA_CHEST_ITEM = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.ITEM_STACK);
    public static final String AGE_DATA_PARAMETER_KEY = "Age";
    public static final String AGE_LOCKED_DATA_PARAMETER_KEY = "AgeLocked";
    public static final String FLYING_DATA_PARAMETER_KEY = "Flying";
    public static final String SADDLE_DATA_PARAMETER_KEY = "Saddle";
    public static final String SHEARED_DATA_PARAMETER_KEY = "ShearCooldown";
    protected static final Map<DragonType, ResourceLocation> DRAGON_TYPIFIED_LOOT_TABLE_MAP = new HashMap<>();
    protected final GroundPathNavigator groundNavigation;
    protected final DragonFlyingPathNavigator flyingNavigation;
    @Nullable
    public EnderCrystalEntity nearestCrystal;
    public final DragonAnimator animator;
    public final DragonInventory inventory = new DragonInventory(this);
    public final PlayerControlledGoal playerControlledGoal;
    protected DragonType type = DragonType.ENDER;
    protected AbstractVariant variant = DragonVariants.ENDER_FEMALE;
    protected DragonLifeStage stage = DragonLifeStage.ADULT;
    protected ItemStack saddle = ItemStack.EMPTY;
    protected ItemStack armor = ItemStack.EMPTY;
    protected ItemStack chest = ItemStack.EMPTY;
    protected boolean hasChest = false;
    protected boolean isSaddled = false;
    protected int flightTicks = 0;
    private int shearCooldown;
    public int roarTicks;
    protected int ticksSinceLastAttack;
    private boolean altBreathing;
    private boolean isGoingDown;
    private boolean isUnHovered;

    public TameableDragonEntity(EntityType<? extends TameableDragonEntity> type, World world) {
        super(type, world);
        this.resetAttributes();
        this.maxUpStep = 1.0F;
        this.blocksBuilding = true;
        this.moveControl = new DragonMovementController(this);
        this.groundNavigation = new GroundPathNavigator(this, level);
        this.groundNavigation.setCanFloat(true);
        this.flyingNavigation = new DragonFlyingPathNavigator(this, level);
        this.flyingNavigation.setCanFloat(true);
        this.navigation = this.groundNavigation;
        if (world.isClientSide) {
            this.animator = new DragonAnimator(this);
            this.playerControlledGoal = null;
        } else {
            this.animator = null;
            this.playerControlledGoal = new PlayerControlledGoal(this);
            this.goalSelector.addGoal(0, this.playerControlledGoal);
        }
    }

    public TameableDragonEntity(World world) {
        this(DMEntities.TAMEABLE_DRAGON.get(), world);
    }

    public TameableDragonEntity(HatchableDragonEggEntity egg, DragonLifeStage stage) {
        this(DMEntities.TAMEABLE_DRAGON.get(), egg.level);
        CompoundNBT compound = egg.saveWithoutId(new CompoundNBT());
        compound.remove(AGE_DATA_PARAMETER_KEY);
        compound.remove(DragonLifeStage.DATA_PARAMETER_KEY);
        compound.remove(DragonType.DATA_PARAMETER_KEY);
        this.load(compound);
        this.setDragonType(egg.getDragonType(), false);
        this.setLifeStage(stage, true, true);
        this.setHealth(this.getMaxHealth() + egg.getHealth() - egg.getMaxHealth());
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, DragonMountsConfig.SERVER.base_health.get())
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
                .add(Attributes.FLYING_SPEED, BASE_AIR_SPEED)
                .add(Attributes.MOVEMENT_SPEED, BASE_GROUND_SPEED)
                .add(Attributes.ATTACK_DAMAGE, DragonMountsConfig.SERVER.base_damage.get())
                .add(Attributes.FOLLOW_RANGE, BASE_FOLLOW_RANGE)
                .add(Attributes.ARMOR, DragonMountsConfig.SERVER.base_armor.get())
                .add(Attributes.ARMOR_TOUGHNESS, BASE_TOUGHNESS);
    }

    public void resetAttributes() {
        AttributeModifierManager manager = this.getAttributes();
        Objects.requireNonNull(manager.getInstance(Attributes.MAX_HEALTH)).setBaseValue(DragonMountsConfig.SERVER.base_health.get());
        Objects.requireNonNull(manager.getInstance(Attributes.ATTACK_DAMAGE)).setBaseValue(DragonMountsConfig.SERVER.base_damage.get());
        Objects.requireNonNull(manager.getInstance(Attributes.ARMOR)).setBaseValue(DragonMountsConfig.SERVER.base_armor.get());
    }

    public void setSaddle(@Nonnull ItemStack stack, boolean sync) {
        boolean original = this.isSaddled;
        this.isSaddled = stack.getItem() instanceof SaddleItem;
        if (!original && this.isSaddled && !this.level.isClientSide) {
            this.playSound(SoundEvents.HORSE_SADDLE, 0.5F, 1.0F);
        }
        if (!stack.isEmpty()) {
            stack.setCount(1);
        }
        this.saddle = stack;
        if (sync && !this.level.isClientSide) {
            this.entityData.set(DATA_SADDLE_ITEM, stack.copy());
        }
    }

    public void setArmor(@Nonnull ItemStack stack, boolean sync) {
        if (!stack.isEmpty()) {
            stack.setCount(1);
        }
        this.armor = stack;
        if (this.level.isClientSide) return;
        ModifiableAttributeInstance attribute = this.getAttribute(Attributes.ARMOR);
        if (attribute != null) {
            attribute.removeModifier(DragonArmorItem.MODIFIER_UUID);
            Item item = stack.getItem();
            if (item instanceof DragonArmorItem) {
                attribute.addTransientModifier(new AttributeModifier(
                        DragonArmorItem.MODIFIER_UUID,
                        "Dragon armor bonus",
                        ((DragonArmorItem) item).getProtection(),
                        AttributeModifier.Operation.ADDITION
                ));
            }
        }
        if (sync) {
            this.entityData.set(DATA_ARMOR_ITEM, stack.copy());
        }
    }

    public void setChest(@Nonnull ItemStack stack, boolean sync) {
        this.hasChest = Tags.Items.CHESTS_WOODEN.contains(stack.getItem());
        if (!this.hasChest) {
            this.inventory.dropContents(true);
        }
        if (!stack.isEmpty()) {
            stack.setCount(1);
        }
        this.chest = stack;
        if (sync && !this.level.isClientSide) {
            this.entityData.set(DATA_CHEST_ITEM, stack.copy());
        }
    }

    public ItemStack getSaddle() {
        return this.saddle;
    }

    public ItemStack getArmor() {
        return this.armor;
    }

    public ItemStack getChest() {
        return this.chest;
    }

    public void inventoryChanged() {
    }

    public void applyPacket(SSyncAppearancePacket packet) {
        this.type = packet.type;
        this.variant = packet.variant;
    }

    public AbstractVariant getVariant() {
        return this.variant;
    }

    public void setVariant(AbstractVariant variant, boolean sync) {
        this.variant = variant;
        if (sync) {
            CHANNEL.send(TRACKING_ENTITY.with(() -> this), new SSyncAppearancePacket(this));
        }
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

    /**
     * Returns the int-precision distance to solid ground.
     * Describe an inclusive limit to reduce iterations.
     */
    public double getAltitude(int limit) {
        BlockPos.Mutable pos = blockPosition().mutable().move(0, -1, 0);
        //BlockPos.Mutable min = this.level.dimensionType().minY(); -> 0
        int i = 0;
        while (i <= limit && pos.getY() > 0 && !this.level.getBlockState(pos).getMaterial().isSolid()) {
            pos.setY(((int) this.getY()) - ++i);
        }
        return i;
    }

    /**
     * Returns the distance to the ground while the entity is flying.
     */
    public double getAltitude() {
        return getAltitude(this.level.getMaxBuildHeight());
    }

    public boolean isHighEnough(int height) {
        return this.getAltitude(height) >= height;
    }

    public float getMaxDeathTime() {
        return 120.0F;
    }

    public boolean isFlying() {
        return this.entityData.get(DATA_FLYING);
    }

    protected void setFlying(boolean flying) {
        this.entityData.set(DATA_FLYING, flying);
        this.navigation = flying ? this.flyingNavigation : this.groundNavigation;
    }

    public void spawnEssence(ItemStack stack) {
        Block block = DMBlocks.DRAGON_CORE.get();
        BlockPos pos = this.blockPosition();
        if (this.level.isEmptyBlock(pos)) {
            BlockState state = block.defaultBlockState().setValue(HorizontalBlock.FACING, this.getDirection());
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

    protected void checkCrystals() {
        if (this.nearestCrystal != null) {
            if (this.nearestCrystal.isAlive()) {
                if (this.tickCount % 10 == 0 && this.getHealth() < this.getMaxHealth()) {
                    this.setHealth(this.getHealth() + 1.0F);
                    this.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 300));//15s
                }
            } else {
                this.nearestCrystal = null;
            }
        }
        if (this.random.nextInt(10) == 0) {
            EnderCrystalEntity nearest = null;
            double minDistance = Double.MAX_VALUE;
            for (EnderCrystalEntity crystal : this.level.getEntitiesOfClass(EnderCrystalEntity.class, this.getBoundingBox().inflate(32.0))) {
                double distance = crystal.distanceToSqr(this);
                if (distance < minDistance) {
                    minDistance = distance;
                    nearest = crystal;
                }
            }
            this.nearestCrystal = nearest;
        }
    }

    //----------Entity----------

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FLYING, false);
        this.entityData.define(DATA_SHEARED, false);
        this.entityData.define(DATA_AGE_LOCKED, false);
        this.entityData.define(DATA_SADDLE_ITEM, ItemStack.EMPTY);
        this.entityData.define(DATA_ARMOR_ITEM, ItemStack.EMPTY);
        this.entityData.define(DATA_CHEST_ITEM, ItemStack.EMPTY);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putString(DragonType.DATA_PARAMETER_KEY, this.getDragonType().getSerializedName());
        compound.putString("Variant", this.getVariant().getSerializedName());
        compound.putString(DragonLifeStage.DATA_PARAMETER_KEY, this.stage.getSerializedName());
        compound.putBoolean(FLYING_DATA_PARAMETER_KEY, this.entityData.get(DATA_FLYING));
        compound.putBoolean(AGE_LOCKED_DATA_PARAMETER_KEY, this.entityData.get(DATA_AGE_LOCKED));
        compound.putInt(SHEARED_DATA_PARAMETER_KEY, this.isSheared() ? this.shearCooldown : 0);
        ListNBT items = this.inventory.createTag();
        if (!items.isEmpty()) {
            compound.put("Items", items);
        }
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
        DragonType type = DragonType.byName(compound.getString(DragonType.DATA_PARAMETER_KEY));
        this.setDragonType(type);
        if (compound.contains("Variant")) {
            this.setVariant(type.variants.get(compound.getString("Variant")), false);
        }
        if (compound.contains(FLYING_DATA_PARAMETER_KEY)) {
            this.entityData.set(DATA_FLYING, compound.getBoolean(FLYING_DATA_PARAMETER_KEY));
        }
        if (compound.contains(SADDLE_DATA_PARAMETER_KEY)) {
            this.setSaddle(ItemStack.of(compound.getCompound(SADDLE_DATA_PARAMETER_KEY)), true);
        }
        if (compound.contains(SHEARED_DATA_PARAMETER_KEY)) {
            this.setSheared(compound.getInt(SHEARED_DATA_PARAMETER_KEY));
        }
        if (compound.contains(AGE_LOCKED_DATA_PARAMETER_KEY)) {
            this.entityData.set(DATA_AGE_LOCKED, compound.getBoolean(AGE_LOCKED_DATA_PARAMETER_KEY));
        }
        if (compound.contains("Items")) {
            this.inventory.fromTag(compound.getList("Items", 10));
        }
    }

    @Override
    public void onSyncedDataUpdated(DataParameter<?> key) {
        if (DATA_SADDLE_ITEM.equals(key)) {
            this.saddle = this.entityData.get(DATA_SADDLE_ITEM);
            this.isSaddled = this.saddle.getItem() instanceof SaddleItem;
        } else if (DATA_ARMOR_ITEM.equals(key)) {
            this.armor = this.entityData.get(DATA_ARMOR_ITEM);
        } else if (DATA_CHEST_ITEM.equals(key)) {
            this.chest = this.entityData.get(DATA_CHEST_ITEM);
            this.hasChest = Tags.Items.CHESTS_WOODEN.contains(this.chest.getItem());
        } else {
            super.onSyncedDataUpdated(key);
        }
    }

    @Override
    public void aiStep() {
        int age = this.age;
        if (this.isDeadOrDying()) {
            this.nearestCrystal = null;
        } else {
            this.checkCrystals();
        }
        if (this.level.isClientSide) {
            super.aiStep();
            this.animator.tick();
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
            if (this.isOnGround()) {
                this.flightTicks = 0;
                this.setFlying(false);
            } else {
                this.setFlying(++this.flightTicks > LIFTOFF_THRESHOLD && !this.isBaby() && this.getControllingPassenger() != null);
                /*if (this.isFlying() != flag) {
                    getEntityAttribute(FOLLOW_RANGE).setBaseValue(getDragonSpeed());
                }*/
            }
        }
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld serverWorld, AgeableEntity entity) {
        return null;
    }

    public boolean hasChest() {
        return this.hasChest;
    }

    public boolean isSaddled() {
        return this.isSaddled;
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
        return this.getType().getDimensions().scale(DragonLifeStage.getSizeAverage(this.stage));
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
            if (DragonFood.test(item) || DMItemTags.BATONS.contains(item) || Tags.Items.CHESTS_WOODEN.contains(item) || item instanceof SaddleItem || item instanceof DragonArmorItem || this.isOwnedBy(player)) {
                return ActionResultType.CONSUME;
            }
            return ActionResultType.PASS;
        }
        IDragonFood food = DragonFood.get(item);
        if (food != null) {
            if (!food.isEatable(this, player, stack, hand)) return ActionResultType.FAIL;
            food.eat(this, player, stack, hand);
            CHANNEL.send(TRACKING_ENTITY.with(() -> this), new SFeedDragonPacket(this, item));
            return ActionResultType.SUCCESS;
        }
        if (this.isOwnedBy(player)) {
            if (DMItemTags.BATONS.contains(item)) {
                this.setOrderedToSit(!this.isOrderedToSit());
                return ActionResultType.SUCCESS;
            }
            if (!this.isBaby() && this.getSaddle().isEmpty() && LimitedSlot.Saddle.mayPlace(item)) {
                this.setSaddle(stack.split(1), true);
                return ActionResultType.SUCCESS;
            }
            if (this.getArmor().isEmpty() && LimitedSlot.DragonArmor.mayPlace(item)) {
                this.setArmor(stack.split(1), true);
                return ActionResultType.SUCCESS;
            }
            if (this.getChest().isEmpty() && LimitedSlot.SingleWoodenChest.mayPlace(item)) {
                this.setChest(stack.split(1), true);
                return ActionResultType.SUCCESS;
            }
            ActionResultType result = item.interactLivingEntity(stack, player, this, hand);
            if (result.consumesAction()) return result;
            boolean flag = player.isSecondaryUseActive();
            if (this.isBaby() && !flag) {
                /*this.setAttackTarget(null);
                this.getNavigator().clearPath();
                this.getAISit().setSitting(false);*/
                this.startRiding(player, true);
                return ActionResultType.SUCCESS;
            }
            if (!this.isSaddled || flag || this.isVehicle()) {
                NetworkHooks.openGui((ServerPlayerEntity) player, this.inventory, buffer -> buffer.writeVarInt(this.getId()));
            }/* else {
                player.yRot = this.yRot;
                player.xRot = this.xRot;
                player.startRiding(this);
            }*/
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public void thunderHit(ServerWorld level, LightningBoltEntity lightning) {
        super.thunderHit(level, lightning);
        this.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 700));//35s
        DragonType current = this.getDragonType();
        if (current == DragonType.SKELETON) {
            this.setDragonType(DragonType.WITHER, false);
        } else if (current == DragonType.WATER) {
            this.setDragonType(DragonType.STORM);
        } else {
            return;
        }
        this.playSound(SoundEvents.END_PORTAL_SPAWN, 2, 1);
        this.playSound(SoundEvents.PORTAL_TRIGGER, 2, 1);
    }

    /*@Override
    public boolean hurt(DamageSource source, float amount) {
        return super.hurt(source, amount);
    }*/

    @Nonnull
    @Override
    protected ResourceLocation getDefaultLootTable() {
        return DRAGON_TYPIFIED_LOOT_TABLE_MAP.computeIfAbsent(this.getDragonType(), DragonType::createDragonLootTable);
    }

    @Override
    public void die(DamageSource cause) {
        super.die(cause);
        if (!this.level.isClientSide && this.isTame()) {
            this.setLifeStage(DragonLifeStage.NEWBORN, true, false);
            this.spawnEssence(IDragonContainer.fill(this, DMItems.DRAGON_ESSENCE.get(this.getDragonType()), true));
        }
    }

    @Override
    protected void dropEquipment() {
        super.dropEquipment();
        this.inventory.dropContents(false);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        Entity entity = source.getEntity();
        return (entity != null && (entity == this || this.hasPassenger(entity))) || super.isInvulnerableTo(source) || this.getDragonType().isInvulnerableTo(source);
    }

    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
    }

    @Override
    public void startSeenByPlayer(ServerPlayerEntity player) {
        PacketDistributor.PacketTarget target = PLAYER.with(() -> player);
        CHANNEL.send(target, new SSyncDragonAgePacket(this));
        CHANNEL.send(target, new SSyncAppearancePacket(this));
    }

    public void setLifeStage(DragonLifeStage stage, boolean reset, boolean sync) {
        boolean flag = !this.level.isClientSide;
        if (flag) {
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
        }
        if (this.stage == stage) return;
        this.stage = stage;
        if (reset) {
            this.refreshAge();
        }
        this.refreshDimensions();
        this.reapplyPosition();
        if (flag && sync) {
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

    @Nonnull
    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return Collections.singletonList(this.getArmor());
    }

    @Nullable
    public Entity getControllingPassenger() {
        List<Entity> passengers = this.getPassengers();
        return passengers.isEmpty() ? null : passengers.get(0);
    }

    @Override
    public void positionRider(Entity passenger) {
        int index = this.getPassengers().indexOf(passenger);
        if (index >= 0) {
            Vector3d pos = this.getDragonType().passengerOffset.apply(index, this.isInSittingPose())
                    .scale(this.getScale())
                    .yRot((float) Math.toRadians(-this.yBodyRot))
                    .add(this.position());
            passenger.setPos(pos.x, /*passenger instanceof PlayerEntity ? pos.y - this.getScale() * 0.2D :*/ pos.y, pos.z);
            if (index == 0) {
                this.onPassengerTurned(passenger);
                passenger.xRotO = passenger.xRot;
            }
        }
    }

    @Override
    public double getPassengersRidingOffset() {
        return this.getDragonType().passengerOffset.apply(0, this.isInSittingPose()).y * this.getScale();
    }

    @Override
    public float getEyeHeight(Pose pose) {
        float height = super.getStandingEyeHeight(pose, this.getDimensions(pose));
        return this.isInSittingPose() ? height * 0.8F : height;
    }

    @Nonnull
    @Override
    protected ITextComponent getTypeName() {
        return this.getDragonType().getTypifiedName("entity.dragonmounts.dragon.name");
    }

    //----------LivingEntity----------

    @Override
    protected void tickDeath() {
        ++this.deathTime;
        if (this.deathTime >= this.getMaxDeathTime()) {
            this.remove(false);
            for (int i = 0; i < 20; ++i) {
                double d0 = this.random.nextGaussian() * 0.02;
                double d1 = this.random.nextGaussian() * 0.02;
                double d2 = this.random.nextGaussian() * 0.02;
                this.level.addParticle(ParticleTypes.POOF, this.getRandomX(1), this.getRandomY(), this.getRandomZ(1), d0, d1, d2);
            }
        }
    }

    @Override
    public void travel(Vector3d travelVector) {
        if (this.level.isClientSide && this.isControlledByLocalInstance()) {
            CHANNEL.sendToServer(CRideDragonPacket.create(this.getId()));
        }
        // disable method while flying, the movement is done entirely by
        // moveEntity() and this one just makes the dragon to fall slowly when
        if (!this.isFlying()) {
            super.travel(travelVector);
        }
    }

    @Override
    public boolean canBeAffected(EffectInstance effect) {
        return effect.getEffect() != Effects.WEAKNESS && super.canBeAffected(effect);
    }

    //----------MobEntity----------

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SitGoal(this));
        /*this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(7, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(9, new BegGoal(this, 8.0F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(5, new NonTamedTargetGoal<>(this, AnimalEntity.class, false, PREY_SELECTOR));
        this.targetSelector.addGoal(6, new NonTamedTargetGoal<>(this, TurtleEntity.class, false, TurtleEntity.BABY_ON_LAND_SELECTOR));
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, AbstractSkeletonEntity.class, false));*/
    }

    @Nonnull
    @Override
    protected DragonBodyController createBodyControl() {
        return new DragonBodyController(this);
    }

    @Override
    public int getMaxHeadYRot() {
        return 90;
    }

    @Override
    public boolean canBeControlledByRider() {
        return this.getControllingPassenger() instanceof LivingEntity;
    }

    @Override
    public boolean setSlot(int index, ItemStack stack) {
        if (index < DragonInventory.INVENTORY_SIZE) {
            return this.inventory.setItemAfterChecked(index, stack);
        }
        return super.setSlot(index, stack);
    }

    //----------AgeableEntity----------

    public void applyPacket(SSyncDragonAgePacket packet) {
        this.age = packet.age;
        this.setLifeStage(packet.stage, false, false);
    }

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
        this.type = type;
        manager.addTransientAttributeModifiers(type.getAttributeModifiers());
        if (reset) {
            this.setHealth((float) Objects.requireNonNull(manager.getInstance(Attributes.MAX_HEALTH)).getValue());
            this.setVariant(type.variants.get(this.random), false);
        }
        CHANNEL.send(TRACKING_ENTITY.with(() -> this), new SSyncAppearancePacket(this));
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

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nullable PlayerEntity player, @Nonnull ItemStack stack, World world, BlockPos pos, int fortune) {
        DragonScalesItem scale = DMItems.DRAGON_SCALES.get(this.getDragonType());
        if (scale != null) {
            this.setSheared(2500 + this.random.nextInt(1000));
            this.playSound(DMSounds.DRAGON_GROWL.get(), 1.0F, 1.0F);
            return Collections.singletonList(new ItemStack(scale, 2 + this.random.nextInt(3)));
        }
        return Collections.emptyList();
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld level, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData spawnData, @Nullable CompoundNBT compound) {
        Objects.requireNonNull(this.getAttribute(Attributes.FOLLOW_RANGE)).addPermanentModifier(new AttributeModifier("Random spawn bonus", this.random.nextGaussian() * 0.05D, AttributeModifier.Operation.MULTIPLY_BASE));
        this.setLeftHanded(this.random.nextFloat() < 0.05F);
        if (compound != null && compound.contains(DragonLifeStage.DATA_PARAMETER_KEY)) {
            this.setLifeStage(DragonLifeStage.byName(compound.getString(DragonLifeStage.DATA_PARAMETER_KEY)), true, false);
        } else {
            this.setLifeStage(DragonLifeStage.ADULT, true, false);
        }
        return spawnData;
    }
}