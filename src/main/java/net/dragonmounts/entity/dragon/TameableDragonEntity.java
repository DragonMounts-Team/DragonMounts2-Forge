package net.dragonmounts.entity.dragon;

import net.dragonmounts.api.IDragonTypified;
import net.dragonmounts.client.ClientDragonEntity;
import net.dragonmounts.entity.ai.DragonBodyController;
import net.dragonmounts.entity.ai.DragonMovementController;
import net.dragonmounts.entity.path.DragonPathNavigator;
import net.dragonmounts.init.DMItems;
import net.dragonmounts.init.DMSounds;
import net.dragonmounts.init.DragonVariants;
import net.dragonmounts.inventory.DragonInventory;
import net.dragonmounts.inventory.LimitedSlot;
import net.dragonmounts.item.DragonArmorItem;
import net.dragonmounts.item.DragonScalesItem;
import net.dragonmounts.item.DragonSpawnEggItem;
import net.dragonmounts.item.TieredShearsItem;
import net.dragonmounts.registry.DragonType;
import net.dragonmounts.registry.DragonVariant;
import net.dragonmounts.util.DragonFood;
import net.dragonmounts.util.math.MathUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.GameRules;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.common.Tags;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

import static net.dragonmounts.init.DMGameRules.*;
import static net.dragonmounts.util.BlockUtil.isSolid;
/**
 * @see net.minecraft.entity.passive.horse.MuleEntity
 * @see net.minecraft.entity.passive.horse.HorseEntity
 */
public abstract class TameableDragonEntity extends TameableEntity implements IForgeShearable, IDragonTypified.Mutable, IFlyingAnimal {
    public static TameableDragonEntity construct(EntityType<? extends TameableDragonEntity> type, World world) {
        return world.isClientSide ? new ClientDragonEntity(type, world) : new ServerDragonEntity(type, world);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, DEFAULT_DRAGON_BASE_HEALTH)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
                .add(Attributes.FLYING_SPEED, BASE_AIR_SPEED)
                .add(Attributes.MOVEMENT_SPEED, BASE_GROUND_SPEED)
                .add(Attributes.ATTACK_DAMAGE, DEFAULT_DRAGON_BASE_DAMAGE)
                .add(Attributes.ATTACK_KNOCKBACK)
                .add(Attributes.FOLLOW_RANGE, BASE_FOLLOW_RANGE)
                .add(Attributes.ARMOR, DEFAULT_DRAGON_BASE_ARMOR)
                .add(Attributes.ARMOR_TOUGHNESS, BASE_TOUGHNESS);
    }

    public static final UUID STAGE_MODIFIER_UUID = UUID.fromString("2d147cda-121b-540e-bb24-435680aa374a");
    // base attributes
    public static final double BASE_GROUND_SPEED = 0.4;
    public static final double BASE_AIR_SPEED = 0.9;
    public static final double BASE_TOUGHNESS = 30.0D;
    public static final double BASE_FOLLOW_RANGE = 64;
    public static final double BASE_FOLLOW_RANGE_FLYING = BASE_FOLLOW_RANGE * 2;
    public static final float ADULT_DIMENSIONS_HEIGHT = 4.2F;
    public static final float ADULT_DIMENSIONS_WIDTH = 4.8F;
    public static final int HOME_RADIUS = 64;
    public static final double LIFTOFF_THRESHOLD = 10;
    protected static final Logger LOGGER = LogManager.getLogger();
    // data value IDs
    private static final DataParameter<Boolean> DATA_FLYING = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DATA_AGE_LOCKED = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DATA_CAN_HOVER = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DATA_BREATHING = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DATA_ALT_BREATHING = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> GOING_DOWN = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> ALLOW_OTHER_PLAYERS = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HOVER_CANCELLED = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Optional<UUID>> DATA_BREEDER = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.OPTIONAL_UUID);
    private static final DataParameter<Integer> DATA_REPO_COUNT = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.INT);
    private static final DataParameter<Boolean> DATA_SHEARED = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HAS_ADJUDICATOR_STONE = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HAS_ELDER_STONE = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    //private static final DataParameter<Boolean> SLEEP = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<String> DATA_BREATH_WEAPON_TARGET = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.STRING);
    private static final DataParameter<Integer> DATA_BREATH_WEAPON_MODE = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.INT);
    protected static final DataParameter<ItemStack> DATA_SADDLE_ITEM = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.ITEM_STACK);
    protected static final DataParameter<ItemStack> DATA_ARMOR_ITEM = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.ITEM_STACK);
    protected static final DataParameter<ItemStack> DATA_CHEST_ITEM = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.ITEM_STACK);
    private static final DataParameter<DragonVariant> DATA_DRAGON_VARIANT = EntityDataManager.defineId(TameableDragonEntity.class, DragonVariant.REGISTRY);
    public static final String AGE_DATA_PARAMETER_KEY = "Age";
    public static final String AGE_LOCKED_DATA_PARAMETER_KEY = "AgeLocked";
    public static final String FLYING_DATA_PARAMETER_KEY = "Flying";
    public static final String SADDLE_DATA_PARAMETER_KEY = "Saddle";
    public static final String SHEARED_DATA_PARAMETER_KEY = "ShearCooldown";
    @Nullable
    public EnderCrystalEntity nearestCrystal;
    public final DragonInventory inventory = new DragonInventory(this);
    protected DragonLifeStage stage = DragonLifeStage.ADULT;
    protected ItemStack saddle = ItemStack.EMPTY;
    protected ItemStack armor = ItemStack.EMPTY;
    protected ItemStack chest = ItemStack.EMPTY;
    protected boolean hasChest = false;
    protected boolean isSaddled = false;
    protected int flightTicks = 0;
    protected int crystalTicks;
    protected int shearCooldown;
    public int roarTicks;
    protected int ticksSinceLastAttack;
    protected boolean altBreathing;
    protected boolean isGoingDown;

    public TameableDragonEntity(EntityType<? extends TameableDragonEntity> type, World level) {
        super(type, level);
        this.resetAttributes();
        this.maxUpStep = 1.0F;
        this.blocksBuilding = true;
        this.moveControl = new DragonMovementController(this);
        this.navigation = new DragonPathNavigator(this, level);
    }

    public void resetAttributes() {
        GameRules rules = this.level.getGameRules();
        AttributeModifierManager manager = this.getAttributes();
        //noinspection DataFlowIssue
        manager.getInstance(Attributes.MAX_HEALTH).setBaseValue(rules.getRule(DRAGON_BASE_HEALTH).get());
        //noinspection DataFlowIssue
        manager.getInstance(Attributes.ATTACK_DAMAGE).setBaseValue(rules.getRule(DRAGON_BASE_DAMAGE).get());
        //noinspection DataFlowIssue
        manager.getInstance(Attributes.ARMOR).setBaseValue(rules.getRule(DRAGON_BASE_ARMOR).get());
    }

    public abstract void setSaddle(@Nonnull ItemStack stack, boolean sync);

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
            this.inventory.dropContents(true, 0);
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

    public final DragonVariant getVariant() {
        return this.entityData.get(DATA_DRAGON_VARIANT);
    }

    public final void setVariant(DragonVariant variant) {
        this.entityData.set(DATA_DRAGON_VARIANT, variant);
    }

    /**
     * Returns the int-precision distance to solid ground.
     * @param limit an inclusive limit to reduce iterations
     */
    public int getAltitude(int limit) {
        BlockPos.Mutable pos = this.blockPosition().mutable();
        if (isSolid(this.level, pos)) return 0;// your dragon might get stuck in block!
        final int min = 0;// world lowest build height
        int i = 0;
        int y = pos.getY();
        do {
            if (--y < min) return limit;// void
            pos.setY(y);
            if (isSolid(this.level, pos)) return i;// ground
        } while (++i < limit);
        return limit;
    }

    /**
     * Returns the distance to the ground while the entity is flying.
     */
    public int getAltitude() {
        return this.getAltitude(this.level.getMaxBuildHeight());
    }

    public boolean isHighEnough() {
        return this.isHighEnough((int) (8.4F * this.getScale()));
    }

    public boolean isHighEnough(int height) {
        return this.getAltitude(height) >= height;
    }

    public float getMaxDeathTime() {
        return 120.0F;
    }

    public final boolean isFlying() {
        return this.entityData.get(DATA_FLYING);
    }

    public final boolean canHover() {
        return this.entityData.get(DATA_CAN_HOVER);
    }

    protected final void setFlying(boolean flying) {
        this.entityData.set(DATA_FLYING, flying);
    }

    protected abstract void checkCrystals();

    protected EnderCrystalEntity findCrystal() {
        EnderCrystalEntity result = null;
        double min = Double.MAX_VALUE;
        for (EnderCrystalEntity crystal : this.level.getEntitiesOfClass(EnderCrystalEntity.class, this.getBoundingBox().inflate(32.0))) {
            double distance = crystal.distanceToSqr(this);
            if (distance < min) {
                min = distance;
                result = crystal;
            }
        }
        return result;
    }

    //----------Entity----------

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FLYING, false);
        this.entityData.define(DATA_SHEARED, false);
        this.entityData.define(DATA_AGE_LOCKED, false);
        this.entityData.define(DATA_CAN_HOVER, true);
        this.entityData.define(DATA_SADDLE_ITEM, ItemStack.EMPTY);
        this.entityData.define(DATA_ARMOR_ITEM, ItemStack.EMPTY);
        this.entityData.define(DATA_CHEST_ITEM, ItemStack.EMPTY);
        this.entityData.define(DATA_DRAGON_VARIANT, DragonVariants.ENDER_FEMALE);
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld serverWorld, AgeableEntity entity) {
        return null;
    }

    public boolean hasChest() {
        return this.hasChest;
    }

    public final boolean isSaddled() {
        return this.isSaddled;
    }

    @Override
    public final void refreshDimensions() {
        Vector3d pos = this.position();
        super.refreshDimensions();
        this.setPos(pos.x, pos.y, pos.z);
    }

    @Override
    public final float getScale() {
        return DragonLifeStage.getSize(this.stage, this.age);
    }

    @Nonnull
    @Override
    public final EntitySize getDimensions(Pose pose) {
        return this.stage.dimensions;
    }

    @Override
    public final boolean isFood(ItemStack stack) {
        return !stack.isEmpty() && DragonFood.test(stack.getItem());
    }

    /*@Override
    public boolean hurt(DamageSource source, float amount) {
        return super.hurt(source, amount);
    }*/

    @Nonnull
    @Override
    protected ResourceLocation getDefaultLootTable() {
        return this.getDragonType().getLootTable();
    }

    @Override
    protected void dropEquipment() {
        super.dropEquipment();
        this.inventory.dropContents(false, 0);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        Entity entity = source.getEntity();
        return (entity != null && (entity == this || this.hasPassenger(entity))) || super.isInvulnerableTo(source) || this.getDragonType().isInvulnerableTo(source);
    }

    @Override
    public boolean causeFallDamage(float distance, float multiplier) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
    }

    public abstract void setLifeStage(DragonLifeStage stage, boolean reset, boolean sync);

    public final DragonLifeStage getLifeStage() {
        return this.stage;
    }

    @Override
    public boolean isPersistenceRequired() {
        return true;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(this.getDragonType().getInstance(DragonSpawnEggItem.class, DMItems.ENDER_DRAGON_SPAWN_EGG));
    }

    @Nonnull
    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return Collections.singletonList(this.getArmor());
    }

    @Nullable
    @Override
    public Entity getControllingPassenger() {
        List<Entity> passengers = this.getPassengers();
        return passengers.isEmpty() ? null : passengers.get(0);
    }

    @Nullable
    public PlayerEntity getControllingPlayer() {
        List<Entity> passengers = this.getPassengers();
        if (passengers.isEmpty()) return null;
        Entity entity = passengers.get(0);
        return entity instanceof PlayerEntity ? (PlayerEntity) entity : null;
    }

    @Override
    public void positionRider(Entity passenger) {
        int index = this.getPassengers().indexOf(passenger);
        if (index >= 0) {
            Vector3d pos = this.getDragonType().passengerLocator.locate(index, this.isInSittingPose())
                    .scale(this.getScale())
                    .yRot(MathUtil.TO_RAD_FACTOR * -this.yBodyRot)
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
        return this.getDragonType().passengerLocator.locate(0, this.isInSittingPose()).y * this.getScale();
    }

    @Override
    public float getEyeHeight(Pose pose) {
        float height = super.getStandingEyeHeight(pose, this.getDimensions(pose));
        return this.isInSittingPose() ? height * 0.8F : height;
    }

    @Nonnull
    @Override
    protected ITextComponent getTypeName() {
        return this.getDragonType().getFormattedName("entity.dragonmounts.dragon.name");
    }

    //----------LivingEntity----------

    @Override
    protected void tickDeath() {
        if (++this.deathTime >= this.getMaxDeathTime()) {
            this.remove(false);
            World level = this.level;
            Random random = this.random;
            for (int i = 0; i < 20; ++i) {
                level.addParticle(
                        ParticleTypes.POOF,
                        this.getRandomX(1D),
                        this.getRandomY(),
                        this.getRandomZ(1D),
                        random.nextGaussian() * 0.02,
                        random.nextGaussian() * 0.02,
                        random.nextGaussian() * 0.02
                );
            }
        }
    }

    @Override
    public boolean canBeAffected(EffectInstance effect) {
        return effect.getEffect() != Effects.WEAKNESS && super.canBeAffected(effect);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean rideableUnderWater() {
        return true;
    }

    //----------MobEntity----------

    @Nonnull
    @Override
    public PathNavigator getNavigation() {
        if (this.isPassenger()) {//?
            Entity vehicle = this.getVehicle();
            if (vehicle instanceof MobEntity) {
                return ((MobEntity) vehicle).getNavigation();
            }
        }
        return this.navigation;
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
        return this.getControllingPlayer() != null;
    }

    @Override
    public boolean setSlot(int index, ItemStack stack) {
        if (index < DragonInventory.INVENTORY_SIZE) {
            return this.inventory.setItemAfterChecked(index, stack);
        }
        switch (index) {
            case 98:
                this.handItems.set(0, stack);
                return true;
            case 99:
                this.handItems.set(1, stack);
                return true;
            case 100:
            case 101:
            case 102:
            case 103:
                if (stack.isEmpty() || LimitedSlot.DragonArmor.mayPlace(stack.getItem())) {
                    this.setArmor(stack, true);
                    return true;
                }
            default: return false;
        }
    }

    @Override
    public void setItemSlot(EquipmentSlotType slot, ItemStack stack) {
        if (slot.getType() == EquipmentSlotType.Group.ARMOR) {
            this.setArmor(stack, true);
        } else {
            this.handItems.set(slot.getIndex(), stack);
        }
    }

    @Nonnull
    @Override
    public ItemStack getItemBySlot(EquipmentSlotType slot) {
        if (slot.getType() == EquipmentSlotType.Group.ARMOR) {
            return this.getArmor();
        }
        return this.handItems.get(slot.getIndex());
    }

    //----------AgeableEntity----------

    protected void refreshAge() {
        switch (this.stage.ordinal()) {
            case 0:// NEWBORN
            case 1:// INFANT
                this.age = -this.stage.duration;
                return;
            case 2:// JUVENILE
            case 3:// PREJUVENILE
                this.age = this.stage.duration;
                return;
            default:
                this.age = 0;
        }
    }

    public void setAgeLocked(boolean locked) {
        this.entityData.set(DATA_AGE_LOCKED, locked);
    }

    public final boolean isAgeLocked() {
        return this.entityData.get(DATA_AGE_LOCKED);
    }

    @Override
    protected void ageBoundaryReached() {
        this.setLifeStage(DragonLifeStage.byId(this.stage.ordinal() + 1), true, false);
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
    public final void setBaby(boolean value) {
        this.setLifeStage(value ? DragonLifeStage.NEWBORN : DragonLifeStage.ADULT, true, true);
    }

    @Override
    protected int getExperienceReward(PlayerEntity player) {
        return 0;
    }

    //----------IDragonTypified.Mutable----------

    public final void setDragonType(DragonType type, boolean reset) {
        AttributeModifierManager manager = this.getAttributes();
        DragonVariant previous = this.getVariant();
        manager.removeAttributeModifiers(previous.type.attributes);
        if (previous.type != type || reset) {
            this.setVariant(type.variants.draw(this.random, previous));
        }
        manager.addTransientAttributeModifiers(type.attributes);
        if (reset) {
            this.setHealth((float) manager.getValue(Attributes.MAX_HEALTH));
        }
    }

    @Override
    public final void setDragonType(DragonType type) {
        this.setDragonType(type, false);
    }

    @Override
    public final DragonType getDragonType() {
        return this.getVariant().type;
    }

    //----------IForgeShearable----------

    public final boolean isSheared() {
        return this.entityData.get(DATA_SHEARED);
    }

    public final void setSheared(int cooldown) {
        this.shearCooldown = cooldown;
        this.entityData.set(DATA_SHEARED, cooldown > 0);
    }

    @Override
    public final boolean isShearable(@Nonnull ItemStack stack, World world, BlockPos pos) {
        Item item = stack.getItem();
        return this.isAlive() && this.stage.ordinal() >= 2 && !this.isSheared() && item instanceof TieredShearsItem && ((TieredShearsItem) item).getTier().getLevel() >= 3;
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nullable PlayerEntity player, @Nonnull ItemStack stack, World world, BlockPos pos, int fortune) {
        DragonScalesItem scale = this.getDragonType().getInstance(DragonScalesItem.class, null);
        if (scale != null) {
            this.setSheared(2500 + this.random.nextInt(1000));
            this.playSound(DMSounds.DRAGON_GROWL.get(), 1.0F, 1.0F);
            return Collections.singletonList(new ItemStack(scale, 2 + this.random.nextInt(3)));
        }
        return Collections.emptyList();
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld level, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData data, @Nullable CompoundNBT tag) {
        //noinspection DataFlowIssue
        this.getAttribute(Attributes.FOLLOW_RANGE).addPermanentModifier(new AttributeModifier(
                "Random spawn bonus",
                this.random.nextGaussian() * 0.05D,
                AttributeModifier.Operation.MULTIPLY_BASE
        ));
        this.setLeftHanded(this.random.nextFloat() < 0.05F);
        this.setLifeStage(tag != null && tag.contains(DragonLifeStage.DATA_PARAMETER_KEY)
                        ? DragonLifeStage.byName(tag.getString(DragonLifeStage.DATA_PARAMETER_KEY))
                        : DragonLifeStage.ADULT,
                true, false
        );
        return data;
    }

    public final PacketBuffer writeId(PacketBuffer buffer) {
        return buffer.writeVarInt(this.getId());
    }
}