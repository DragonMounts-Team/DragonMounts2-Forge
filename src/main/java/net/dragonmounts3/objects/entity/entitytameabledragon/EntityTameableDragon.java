package net.dragonmounts3.objects.entity.entitytameabledragon;

import net.dragonmounts3.DragonMountsConfig;
import net.dragonmounts3.objects.entity.entitytameabledragon.helper.DragonBodyHelper;
import net.dragonmounts3.objects.entity.entitytameabledragon.helper.DragonHelper;
import net.dragonmounts3.objects.entity.entitytameabledragon.inventoty.DragonInventory;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@ParametersAreNonnullByDefault
public class EntityTameableDragon extends TameableEntity implements IForgeShearable {

    // base attributes
    public static final double BASE_GROUND_SPEED = 0.4;
    public static final double BASE_AIR_SPEED = 0.9;
    public static final Attribute MOVEMENT_SPEED_AIR = new RangedAttribute("generic.movementSpeedAir",
            0.9, 0.0, Double.MAX_VALUE).setRegistryName("Movement Speed Air").setSyncable(true);
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
    private static final DataParameter<Boolean> DATA_FLYING = EntityDataManager.defineId(EntityTameableDragon.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> GROWTH_PAUSED = EntityDataManager.defineId(EntityTameableDragon.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DATA_SADDLED = EntityDataManager.defineId(EntityTameableDragon.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DATA_BREATHING = EntityDataManager.defineId(EntityTameableDragon.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DATA_ALT_BREATHING = EntityDataManager.defineId(EntityTameableDragon.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> GOING_DOWN = EntityDataManager.defineId(EntityTameableDragon.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> CHESTED = EntityDataManager.defineId(EntityTameableDragon.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> ALLOW_OTHER_PLAYERS = EntityDataManager.defineId(EntityTameableDragon.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> BOOSTING = EntityDataManager.defineId(EntityTameableDragon.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_MALE = EntityDataManager.defineId(EntityTameableDragon.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> ARMOR = EntityDataManager.defineId(EntityTameableDragon.class, DataSerializers.INT);
    private static final DataParameter<Boolean> HOVER_CANCELLED = EntityDataManager.defineId(EntityTameableDragon.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> Y_LOCKED = EntityDataManager.defineId(EntityTameableDragon.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> ALT_TEXTURE = EntityDataManager.defineId(EntityTameableDragon.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> FOLLOW_YAW = EntityDataManager.defineId(EntityTameableDragon.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Optional<UUID>> DATA_BREEDER = EntityDataManager.defineId(EntityTameableDragon.class, DataSerializers.OPTIONAL_UUID);
    private static final DataParameter<String> DATA_BREED = EntityDataManager.defineId(EntityTameableDragon.class, DataSerializers.STRING);
    private static final DataParameter<String> FOREST_TEXTURES = EntityDataManager.defineId(EntityTameableDragon.class, DataSerializers.STRING);
    private static final DataParameter<Integer> DATA_REPO_COUNT = EntityDataManager.defineId(EntityTameableDragon.class, DataSerializers.INT);
    private static final DataParameter<Integer> HUNGER = EntityDataManager.defineId(EntityTameableDragon.class, DataSerializers.INT);
    private static final DataParameter<Integer> DATA_TICKS_SINCE_CREATION = EntityDataManager.defineId(EntityTameableDragon.class, DataSerializers.INT);
    private static final DataParameter<Byte> DRAGON_SCALES = EntityDataManager.defineId(EntityTameableDragon.class, DataSerializers.BYTE);
    private static final DataParameter<ItemStack> BANNER1 = EntityDataManager.defineId(EntityTameableDragon.class, DataSerializers.ITEM_STACK);
    private static final DataParameter<ItemStack> BANNER2 = EntityDataManager.defineId(EntityTameableDragon.class, DataSerializers.ITEM_STACK);
    private static final DataParameter<ItemStack> BANNER3 = EntityDataManager.defineId(EntityTameableDragon.class, DataSerializers.ITEM_STACK);
    private static final DataParameter<ItemStack> BANNER4 = EntityDataManager.defineId(EntityTameableDragon.class, DataSerializers.ITEM_STACK);
    private static final DataParameter<Boolean> HAS_ADJUDICATOR_STONE = EntityDataManager.defineId(EntityTameableDragon.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HAS_ELDER_STONE = EntityDataManager.defineId(EntityTameableDragon.class, DataSerializers.BOOLEAN);
    private static final DataParameter<ItemStack> WHISTLE = EntityDataManager.defineId(EntityTameableDragon.class, DataSerializers.ITEM_STACK);
    private static final DataParameter<Boolean> SLEEP = EntityDataManager.defineId(EntityTameableDragon.class, DataSerializers.BOOLEAN);
    private static final DataParameter<String> DATA_BREATH_WEAPON_TARGET = EntityDataManager.defineId(EntityTameableDragon.class, DataSerializers.STRING);
    private static final DataParameter<Integer> DATA_BREATH_WEAPON_MODE = EntityDataManager.defineId(EntityTameableDragon.class, DataSerializers.INT);
    private final DragonBodyHelper dragonBodyHelper = new DragonBodyHelper(this);
    private final Map<Class<?>, DragonHelper> helpers = new HashMap<>();
    public EnderCrystalEntity healingEnderCrystal;
    public DragonInventory dragonInv;
    public static int ticksShear;
    public int inAirTicks;
    public int roarTicks;
    protected int ticksSinceLastAttack;
    float damageReduction = (float) this.getArmorResistance() + 3.0F;
    private boolean hasChestVarChanged = false;
    private boolean isUsingBreathWeapon;
    private boolean altBreathing;
    private boolean isGoingDown;
    private boolean isUnHovered;
    private boolean yLocked;
    private boolean followYaw;

    protected EntityTameableDragon(EntityType<? extends EntityTameableDragon> type, World world) {
        super(type, world);
        this.maxUpStep = 1.0F;

    }

    @Nullable @Override
    public AgeableEntity getBreedOffspring(ServerWorld serverWorld, AgeableEntity entity) {
        return null;
    }

    public int getArmor() {
        return this.entityData.get(ARMOR);
    }

    public void setArmor(int armorType) {
        this.entityData.set(ARMOR, armorType);
    }

    public double getArmorResistance() {
        switch (this.getArmor()) {
            case 1:
            case 4: return 1.5;
            case 2: return 1.4;
            case 3: return 1.7;
            default: return 0;
        }
    }

    public boolean isFlying() {
        return this.entityData.get(DATA_FLYING);
    }

}