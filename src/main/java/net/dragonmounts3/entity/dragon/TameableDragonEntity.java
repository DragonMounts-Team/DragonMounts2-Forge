package net.dragonmounts3.entity.dragon;

import net.dragonmounts3.DragonMountsConfig;
import net.dragonmounts3.entity.dragon.helper.DragonBodyHelper;
import net.dragonmounts3.entity.dragon.helper.DragonHelper;
import net.dragonmounts3.entity.dragon.inventoty.DragonInventory;
import net.dragonmounts3.inits.ModAttributes;
import net.dragonmounts3.inits.ModEntities;
import net.dragonmounts3.objects.DragonType;
import net.dragonmounts3.objects.IDragonTypified;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IForgeShearable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

@ParametersAreNonnullByDefault
public class TameableDragonEntity extends TameableEntity implements IForgeShearable, IDragonTypified {
    protected DragonType type;
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
    private static final DataParameter<Boolean> DATA_FLYING = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> GROWTH_PAUSED = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DATA_SADDLED = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DATA_BREATHING = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DATA_ALT_BREATHING = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> GOING_DOWN = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> CHESTED = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> ALLOW_OTHER_PLAYERS = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> BOOSTING = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_MALE = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HOVER_CANCELLED = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> Y_LOCKED = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> ALT_TEXTURE = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> FOLLOW_YAW = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Optional<UUID>> DATA_BREEDER = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.OPTIONAL_UUID);
    private static final DataParameter<String> DATA_BREED = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.STRING);
    private static final DataParameter<String> FOREST_TEXTURES = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.STRING);
    private static final DataParameter<Integer> DATA_REPO_COUNT = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.INT);
    private static final DataParameter<Integer> HUNGER = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.INT);
    private static final DataParameter<Integer> DATA_TICKS_SINCE_CREATION = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.INT);
    private static final DataParameter<Byte> DRAGON_SCALES = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BYTE);
    private static final DataParameter<ItemStack> BANNER1 = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.ITEM_STACK);
    private static final DataParameter<ItemStack> BANNER2 = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.ITEM_STACK);
    private static final DataParameter<ItemStack> BANNER3 = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.ITEM_STACK);
    private static final DataParameter<ItemStack> BANNER4 = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.ITEM_STACK);
    private static final DataParameter<Boolean> HAS_ADJUDICATOR_STONE = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HAS_ELDER_STONE = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<ItemStack> WHISTLE = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.ITEM_STACK);
    private static final DataParameter<Boolean> SLEEP = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<String> DATA_BREATH_WEAPON_TARGET = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.STRING);
    private static final DataParameter<Integer> DATA_BREATH_WEAPON_MODE = EntityDataManager.defineId(TameableDragonEntity.class, DataSerializers.INT);
    private final DragonBodyHelper dragonBodyHelper = new DragonBodyHelper(this);
    private final Map<Class<?>, DragonHelper> helpers = new HashMap<>();
    public EnderCrystalEntity healingEnderCrystal;
    public DragonInventory dragonInv;
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
    }

    public TameableDragonEntity(World world) {
        this(ModEntities.TAMEABLE_DRAGON.get(), world);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(ModAttributes.MOVEMENT_SPEED_AIR.get(), BASE_AIR_SPEED)
                .add(Attributes.MOVEMENT_SPEED, BASE_GROUND_SPEED)
                .add(Attributes.ATTACK_DAMAGE, BASE_DAMAGE)
                .add(Attributes.FOLLOW_RANGE, BASE_FOLLOW_RANGE)
                .add(Attributes.KNOCKBACK_RESISTANCE, RESISTANCE)
                .add(Attributes.ARMOR, BASE_ARMOR)
                .add(Attributes.ARMOR_TOUGHNESS, BASE_TOUGHNESS);
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld serverWorld, AgeableEntity entity) {
        return null;
    }

    public boolean isFlying() {
        return this.entityData.get(DATA_FLYING);
    }

    @Override
    public DragonType getDragonType() {
        return this.type;
    }

    @Override
    public boolean isPersistenceRequired() {
        return true;
    }
}