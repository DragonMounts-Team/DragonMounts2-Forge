package net.dragonmounts3.objects.entity.entitycarriage;

import net.dragonmounts3.inits.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntityCarriage extends Entity {

    private static final DataParameter<Float> DAMAGE = EntityDataManager.defineId(EntityCarriage.class, DataSerializers.FLOAT);
    private static final DataParameter<Integer> FORWARD_DIRECTION = EntityDataManager.defineId(EntityCarriage.class, DataSerializers.INT);
    private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.defineId(EntityCarriage.class, DataSerializers.INT);
    private static final DataParameter<Integer> TYPE = EntityDataManager.defineId(EntityCarriage.class, DataSerializers.INT);
    public static float defaultMaxSpeedAirLateral = 0.4f;
    public static float defaultMaxSpeedAirVertical = -1f;
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

    public EntityCarriage(World world) {
        super(ModEntities.ENTITY_CARRIAGE.get(), world);
        this.blocksBuilding = true;
    }

    public EntityCarriage(World world, double x, double y, double z) {
        this(world);
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
        this.entityData.define(TYPE, compound.getInt("Type"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT compound) {
        compound.putFloat("damage", this.getDamage());
        compound.putInt("forward", this.getForwardDirection());
        compound.putInt("TimeSinceHit", this.getTimeSinceHit());
        compound.putInt("Type", this.entityData.get(TYPE));
    }

    public EnumCarriageTypes getCarriageType() {
        return EnumCarriageTypes.byId(this.entityData.get(TYPE));
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}