package net.dragonmounts3.entity.carriage;

import net.dragonmounts3.init.DMEntities;
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
    private static final DataParameter<Integer> TYPE = EntityDataManager.defineId(CarriageEntity.class, DataSerializers.INT);
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
        this.entityData.define(TYPE, 0);
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT compound) {
        this.entityData.set(DAMAGE, compound.getFloat("damage"));
        this.entityData.set(FORWARD_DIRECTION, compound.getInt("forward"));
        this.entityData.set(TIME_SINCE_HIT, compound.getInt("TimeSinceHit"));
        this.entityData.set(TYPE, compound.getInt("Type"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT compound) {
        compound.putFloat("damage", this.getDamage());
        compound.putInt("forward", this.getForwardDirection());
        compound.putInt("TimeSinceHit", this.getTimeSinceHit());
        compound.putInt("Type", this.entityData.get(TYPE));
    }

    public void setCarriageType(CarriageType type) {
        this.entityData.set(TYPE, type.ordinal());
    }

    public int getCarriageTypeId() {
        return this.entityData.get(TYPE);
    }

    public CarriageType getCarriageType() {
        return CarriageType.byId(this.getCarriageTypeId());
    }

    @Nonnull
    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}