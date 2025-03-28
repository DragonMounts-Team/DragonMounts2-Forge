package net.dragonmounts.entity;

import net.dragonmounts.init.CarriageTypes;
import net.dragonmounts.init.DMEntities;
import net.dragonmounts.registry.CarriageType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;

public class CarriageEntity extends Entity {

    private static final DataParameter<Float> DAMAGE = EntityDataManager.defineId(CarriageEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Integer> FORWARD_DIRECTION = EntityDataManager.defineId(CarriageEntity.class, DataSerializers.INT);
    private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.defineId(CarriageEntity.class, DataSerializers.INT);
    private static final DataParameter<CarriageType> DATA_TYPE = EntityDataManager.defineId(CarriageEntity.class, CarriageType.REGISTRY);
    public static float defaultMaxSpeedAirLateral = 0.4F;
    public static float defaultMaxSpeedAirVertical = -1F;
    public static double defaultDragAir = 0.94999998807907104D;
    protected float maxSpeedAirLateral = defaultMaxSpeedAirLateral;
    protected float maxSpeedAirVertical = defaultMaxSpeedAirVertical;
    protected double dragAir = defaultDragAir;
    private boolean isInReverse;
    private int lerpSteps;
    private double boatPitch;
    private double lerpY;
    private double lerpZ;
    private double boatYaw;
    private double lerpXRot;

    public CarriageEntity(EntityType<? extends CarriageEntity> type, World world) {
        super(type, world);
        this.blocksBuilding = true;
    }

    public CarriageEntity(World world) {
        this(DMEntities.CARRIAGE.get(), world);
    }

    public CarriageEntity(World world, double x, double y, double z) {
        this(DMEntities.CARRIAGE.get(), world);
        this.setPos(x, y, z);
        this.setDeltaMovement(0.0D, 0.0D, 0.0D);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    public float getDamage() {
        return this.entityData.get(DAMAGE);
    }

    public int getForwardDirection() {
        return this.entityData.get(FORWARD_DIRECTION);
    }

    public int getTimeSinceHit() {
        return this.entityData.get(TIME_SINCE_HIT);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DAMAGE, 0.0F);
        this.entityData.define(FORWARD_DIRECTION, 1);
        this.entityData.define(TIME_SINCE_HIT, 2);
        this.entityData.define(DATA_TYPE, CarriageTypes.OAK);
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT tag) {
        this.entityData.set(DAMAGE, tag.getFloat("damage"));
        this.entityData.set(FORWARD_DIRECTION, tag.getInt("forward"));
        this.entityData.set(TIME_SINCE_HIT, tag.getInt("TimeSinceHit"));
        this.entityData.set(DATA_TYPE, CarriageType.byName(tag.getString("Type")));
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT tag) {
        tag.putFloat("damage", this.getDamage());
        tag.putInt("forward", this.getForwardDirection());
        tag.putInt("TimeSinceHit", this.getTimeSinceHit());
        tag.putString("Type", this.getCarriageType().getSerializedName().toString());
    }

    public void setCarriageType(CarriageType type) {
        this.entityData.set(DATA_TYPE, type);
    }

    public CarriageType getCarriageType() {
        return this.entityData.get(DATA_TYPE);
    }

    @Nonnull
    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}