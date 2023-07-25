package net.dragonmounts3.client;

import net.dragonmounts3.client.model.dragon.*;
import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.dragonmounts3.util.CircularBuffer;
import net.dragonmounts3.util.math.LinearInterpolation;
import net.dragonmounts3.util.math.MathUtil;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

import javax.annotation.Nullable;

import static net.dragonmounts3.util.ModelUtil.applyRotateAngle;
import static net.dragonmounts3.util.math.Interpolation.*;

/**
 * Animation control class to put useless reptiles in motion.
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class DragonAnimator {
    public final TameableDragonEntity dragon;
    // constants
    private static final int JAW_OPENING_TIME_FOR_ATTACK = 5;
    private static final float HEAD_TILT_DURING_BREATH = -0.1F;
    public static final float SIZE_OF_ADULT_HEAD = 1.0F;
    public static final float SIZE_OF_BABY_HEAD = 2.0F;
    public static final float SCALE_OF_BABY = 0.2F;
    public static final float SCALE_OF_ADULT = 1.0F;

    // entity parameters
    private float partialTicks;
    private float moveTime;
    private float moveSpeed;
    private float lookYaw;
    private float lookPitch;
    private double prevRenderYawOffset;
    private double yawAbs;

    // timing vars
    private float animBase;
    private float cycleOfs;
    private float anim;
    private float ground;
    private float flutter;
    private float walk;
    private float sit;
    private float jaw;
    private float speed;

    // timing interp vars
    private final LinearInterpolation animTimer = new LinearInterpolation(0);
    private final LinearInterpolation groundTimer = new LinearInterpolation.Clamped(1, 0, 1);
    private final LinearInterpolation flutterTimer = new LinearInterpolation.Clamped(0, 0, 1);
    private final LinearInterpolation walkTimer = new LinearInterpolation.Clamped(0, 0, 1);
    private final LinearInterpolation sitTimer = new LinearInterpolation.Clamped(0, 0, 1);
    private final LinearInterpolation biteTimer = new LinearInterpolation.Clamped(0, 0, 1);
    private final LinearInterpolation speedTimer = new LinearInterpolation.Clamped(1, 0, 1);

    // trails
    private boolean initTrails = false;
    private final CircularBuffer yTrail = new CircularBuffer(8);
    private final CircularBuffer yawTrail = new CircularBuffer(16);
    private final CircularBuffer pitchTrail = new CircularBuffer(16);

    // model flags
    private boolean onGround;
    private boolean openJaw;
    private boolean wingsDown;

    // animation parameters
    private final float[] wingArm = new float[3];
    private final float[] wingForearm = new float[3];
    private final float[] wingArmFlutter = new float[3];
    private final float[] wingForearmFlutter = new float[3];
    private final float[] wingArmGlide = new float[3];
    private final float[] wingForearmGlide = new float[3];
    private final float[] wingArmGround = new float[3];
    private final float[] wingForearmGround = new float[3];

    // final X rotation angles for ground
    private final float[] xGround = {0, 0, 0, 0};

    // X rotation angles for ground
    // 1st dim - front, hind
    // 2nd dim - thigh, crus, foot, toe
    private final float[][] xGroundStand = {
            {0.8F, -1.5F, 1.3F, 0},
            {-0.3F, 1.5F, -0.2F, 0},
    };
    private final float[][] xGroundSit = {
            {0.3F, -1.8F, 1.8F, 0},
            {-0.8F, 1.8F, -0.9F, 0},
    };

    // X rotation angles for walking
    // 1st dim - animation keyframe
    // 2nd dim - front, hind
    // 3rd dim - thigh, crus, foot, toe
    private final float[][][] xGroundWalk = {{
            {0.4F, -1.4F, 1.3F, 0},    // move down and forward
            {0.1F, 1.2F, -0.5F, 0}     // move back
    }, {
            {1.2F, -1.6F, 1.3F, 0},    // move back
            {-0.3F, 2.1F, -0.9F, 0.6F} // move up and forward
    }, {
            {0.9F, -2.1F, 1.8F, 0.6F}, // move up and forward
            {-0.7F, 1.4F, -0.2F, 0}    // move down and forward
    }};

    // final X rotation angles for walking
    private final float[] xGroundWalk2 = {0, 0, 0, 0};

    // Y rotation angles for ground, thigh only
    private final float[] yGroundStand = {-0.25F, 0.25F};
    private final float[] yGroundSit = {0.1F, 0.35F};
    private final float[] yGroundWalk = {-0.1F, 0.1F};

    // X rotation angles for air
    // 1st dim - front, hind
    // 2nd dim - thigh, crus, foot, toe
    private final float[][] xAirAll = {{0, 0, 0, 0}, {0, 0, 0, 0}};

    // Y rotation angles for air, thigh only
    private final float[] yAirAll = {-0.1F, 0.1F};

    private DragonHeadModelPart headCache = null;

    public DragonAnimator(TameableDragonEntity dragon) {
        this.dragon = dragon;
    }

    @Nullable
    public Vector3d getThroatPosition(double offsetX, double offsetY, double offsetZ) {
        return this.getThroatPosition(this.headCache, offsetX, offsetY, offsetZ);
    }

    /**
     * Calculate the position of the dragon's throat
     *
     * @return the world [x,y,z] of the throat
     */
    @Nullable
    public Vector3d getThroatPosition(DragonHeadModelPart head, double offsetX, double offsetY, double offsetZ) {
        if (head == null) return null;
        Vector3d bodyOrigin = this.dragon.position().add(0, this.dragon.getEyeHeight(), 0);
        float scale = this.dragon.getScale();
        final float modelScale = scale * this.dragon.getDragonType().resources.modelPositionScale;
        final float headScale = modelScale * this.getRelativeHeadSize(scale);
        // the head offset plus the headLocation.rotationPoint is the origin of the head, i.e. the point about which the
        // head rotates, relative to the origin of the body (getPositionEyes)
        Vector3d headOffset = new Vector3d(-head.x * modelScale, -head.y * modelScale, (head.z - 15) * modelScale);
        // offset of the throat position relative to the head origin - rotate and pitch to match head
        Vector3d throatOffset = new Vector3d(offsetX * headScale, (offsetY + 2) * headScale, (offsetZ - 8) * headScale);
        throatOffset = throatOffset.xRot(head.xRot);
        throatOffset = throatOffset.yRot(-head.yRot);
        Vector3d totalOffset = headOffset.add(throatOffset);
        Vector3d centerOffset = new Vector3d(0, 6 * modelScale, 19 * modelScale);
        //rotate body
        totalOffset = totalOffset.add(centerOffset);
        totalOffset = totalOffset.xRot(-(float) Math.toRadians(this.getModelPitch()));
        totalOffset = totalOffset.subtract(centerOffset);
        totalOffset = totalOffset.yRot((float) (Math.toRadians(-this.dragon.yBodyRot) + Math.PI));
        return bodyOrigin.add(totalOffset);
    }

    public void setPartialTicks(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public void setMovement(float moveTime, float moveSpeed) {
        this.moveTime = moveTime;
        this.moveSpeed = moveSpeed;
    }

    public void setLook(float lookYaw, float lookPitch) {
        // don't twist the neck
        this.lookYaw = MathHelper.clamp(lookYaw, -120, 120);
        this.lookPitch = MathHelper.clamp(lookPitch, -90, 90);
    }

    /**
     * Applies the animations on the model. Called every frame before the model
     * is rendered.
     *
     * @param model model to animate
     */
    public void animate(DragonModel model) {
        anim = animTimer.get(partialTicks);
        ground = groundTimer.get(partialTicks);
        flutter = flutterTimer.get(partialTicks);
        walk = walkTimer.get(partialTicks);
        sit = sitTimer.get(partialTicks);
        jaw = biteTimer.get(partialTicks);
        speed = speedTimer.get(partialTicks);

        animBase = anim * MathUtil.PI * 2;
        cycleOfs = MathHelper.sin(animBase - 1) + 1;

        // check if the wings are moving down and trigger the event
        boolean newWingsDown = cycleOfs > 1;
        if (newWingsDown && !wingsDown && flutter != 0) {
            dragon.onWingsDown(speed);
        }
        wingsDown = newWingsDown;

        // update flags
        model.body.back.visible = !this.dragon.isSaddled();

        cycleOfs = (cycleOfs * cycleOfs + cycleOfs * 2) * 0.05F;

        // reduce up/down amplitude
        cycleOfs *= MathHelper.clampedLerp(0.5F, 1, flutter);
        cycleOfs *= MathHelper.clampedLerp(1, 0.5F, ground);

        // animate body parts
        this.animHeadAndNeck(model.head, model.neck);
        this.animTail(model.tail);
        this.animWings(model.wing);
        this.animLegs(model);
    }

    public void tick() {
        setOnGround(!this.dragon.isFlying());

        // init trails
        if (!initTrails) {
            yTrail.fill((float) dragon.getY());
            yawTrail.fill(dragon.yBodyRot);
            pitchTrail.fill(getModelPitch());
            initTrails = true;
        }

        // don't move anything during death sequence
        if (dragon.getHealth() <= 0) {
            animTimer.sync();
            groundTimer.sync();
            flutterTimer.sync();
            walkTimer.sync();
            sitTimer.sync();
            //roarTimer.sync();
            return;
        }

        float speedMax = 0.05F;
        Vector3d motion = dragon.getDeltaMovement();
        float speedEnt = (float) (motion.x * motion.x + motion.z * motion.z);
        float speedMulti = MathHelper.clamp(speedEnt / speedMax, 0, 1);

        // update main animation timer
        float animAdd = 0.035F;

        // depend timing speed on movement
        if (!onGround) {
            animAdd += (1 - speedMulti) * animAdd;
        }

        animTimer.add(animAdd);

        // update ground transition
        float groundVal = groundTimer.get(1);
        if (onGround) {
            groundVal *= 0.95F;
            groundVal += 0.08F;
        } else {
            groundVal -= 0.1F;
        }
        groundTimer.set(groundVal);

        // update flutter transition
        boolean flutterFlag = !onGround && (dragon.verticalCollision || motion.y > -0.1 || speedEnt < speedMax);
        flutterTimer.add(flutterFlag ? 0.1F : -0.1F);

        // update walking transition
        boolean walkFlag = moveSpeed > 0.1 && !dragon.isInSittingPose();
        float walkVal = 0.1F;
        walkTimer.add(walkFlag ? walkVal : -walkVal);

        // update sitting transisiton
        float sitVal = sitTimer.get(1);
        sitVal += dragon.isInSittingPose() ? 0.1F : -0.1F;
        sitVal *= 0.95F;
        sitTimer.set(sitVal);

        // update bite opening transition and breath transitions
        /*DragonBreathHelper.BreathState breathState = dragon.getBreathHelper().getCurrentBreathState();
        switch (breathState) {
            case IDLE: {  // breath is idle, handle bite attack
                int ticksSinceLastAttack = dragon.getTicksSinceLastAttack();
                final int JAW_OPENING_TIME_FOR_ATTACK = 5;
                boolean jawFlag = (ticksSinceLastAttack >= 0 && ticksSinceLastAttack < JAW_OPENING_TIME_FOR_ATTACK);
                biteTimer.add(jawFlag ? 0.2F : -0.2F);
                breathTimer.set(0.0F);

                int roarticks = dragon.roarTicks;
                final int JAW_OPENING_TIME_FOR_ROAR = 20;
                boolean jawFlag1 = (roarticks >= 0 && roarticks < JAW_OPENING_TIME_FOR_ROAR);
                roarTimer.add(jawFlag1 ? 0.2F : -0.2F);
                break;
            }
            case STARTING: {
                biteTimer.set(0.0F);
                breathTimer.set(dragon.getBreathHelper().getBreathStateFractionComplete());
                break;
            }
            case STOPPING: {
                float breathStateFractionComplete = dragon.getBreathHelper().getBreathStateFractionComplete();
                breathTimer.set(1.0F - breathStateFractionComplete);
                break;
            }
            case SUSTAIN: {
                breathTimer.set(1.0F);
                break;
            }
            default: {
                DragonMounts.loggerLimit.error_once("unexpected breathstate:" + breathState);
                return;
            }
        }*/

        // update speed transition
        boolean nearGround = onGround || !dragon.isHighEnough((int) (4 * dragon.getScale()));
        boolean speedFlag = speedEnt > speedMax || nearGround;
        float speedValue = 0.05F;
        speedTimer.add(speedFlag ? speedValue : -speedValue);

        // update trailers
        double yawDiff = dragon.yBodyRot - prevRenderYawOffset;
        prevRenderYawOffset = dragon.yBodyRot;

        // filter out 360 degrees wrapping
        if (yawDiff < 180 && yawDiff > -180) {
            yawAbs += yawDiff;
        }

        // TODO: where's yOffset?
        //yTrail.update(entity.posY - entity.yOffset);
        yTrail.update((float) dragon.getY());
        yawTrail.update((float) -yawAbs);
        pitchTrail.update(getModelPitch());
    }

    protected void animHeadAndNeck(DragonHeadModelPart head, DragonNeckModelPart neck) {
        this.headCache = head;
        neck.setPos(0, 14, -8);
        applyRotateAngle(neck, 0, 0, 0);
        float health = dragon.getHealthRelative();
        float neckSize;
        for (int i = 0; i < DragonNeckModelPart.NECK_SEGMENT_COUNT; ++i) {
            float vertMulti = (i + 1) / (float) DragonNeckModelPart.NECK_SEGMENT_COUNT;
            float baseRotX = MathHelper.cos((float) i * 0.45F + animBase) * 0.15F;
            baseRotX *= MathHelper.clampedLerp(0.2F, 1, flutter);
            baseRotX *= MathHelper.clampedLerp(1, 0.2F, sit);
            float ofsRotX = MathHelper.sin(vertMulti * ((float) Math.PI) * 0.9F) * 0.63F;
            // basic up/down movement
            neck.xRot = baseRotX;
            // reduce rotation when on ground
            neck.xRot *= clampedSmoothLinear(1, 0.5F, walk);
            // flex neck down when hovering
            neck.xRot += (1 - speed) * vertMulti;
            // lower neck on low health
            neck.xRot -= MathHelper.clampedLerp(0, ofsRotX, ground * health);
            // use looking yaw
            neck.yRot = (float) Math.toRadians(lookYaw) * vertMulti * speed;
            // update scale
            float v = clampedLinear(1.6F, 1, vertMulti);
            neck.scaleX = neck.scaleY = v;
            neck.scaleZ = 0.6F;
            // hide the first and every second scale
            neck.scale.visible = i % 2 != 0 || i == 0;
            // update proxy
            neck.save(i);
            // move next proxy behind the current one
            neckSize = DragonNeckModelPart.NECK_SIZE * neck.scaleZ - 1.4F;
            neck.x -= MathHelper.sin(neck.yRot) * MathHelper.cos(neck.xRot) * neckSize;
            neck.y += MathHelper.sin(neck.xRot) * neckSize;
            neck.z -= MathHelper.cos(neck.yRot) * MathHelper.cos(neck.xRot) * neckSize;
        }
        head.xRot = (float) (Math.toRadians(lookPitch) + (1 - speed));
        head.yRot = neck.yRot;
        head.zRot = neck.zRot * 0.2F;
        head.x = neck.x;
        head.y = neck.y;
        head.z = neck.z;
        head.lowerJaw.xRot = jaw * 0.75F;
        head.lowerJaw.xRot += (1 - MathHelper.sin(animBase)) * 0.1F * flutter;
    }

    protected void animWings(DragonWingModelPart wing) {
        // move wings slower while sitting
        float aSpeed = sit > 0 ? 0.6F : 1;
        // animation speeds
        float a1 = animBase * aSpeed * 0.35F;
        float a2 = animBase * aSpeed * 0.5F;
        float a3 = animBase * aSpeed * 0.75F;
        if (ground < 1) {
            // fluttering
            wingArmFlutter[0] = 0.125F - MathHelper.cos(animBase) * 0.2F;
            wingArmFlutter[1] = 0.25F;
            wingArmFlutter[2] = (MathHelper.sin(animBase) + 0.125F) * 0.8F;
            wingForearmFlutter[0] = 0;
            wingForearmFlutter[1] = -wingArmFlutter[1] * 2;
            wingForearmFlutter[2] = -(MathHelper.sin(animBase + 2) + 0.5F) * 0.75F;
            // gliding
            wingArmGlide[0] = -0.25F - MathHelper.cos(animBase * 2) * MathHelper.cos(animBase * 1.5F) * 0.04F;
            wingArmGlide[1] = 0.25F;
            wingArmGlide[2] = 0.35F + MathHelper.sin(animBase) * 0.05F;
            wingForearmGlide[0] = 0;
            wingForearmGlide[1] = -wingArmGlide[1] * 2;
            wingForearmGlide[2] = -0.25F + (MathHelper.sin(animBase + 2) + 0.5F) * 0.05F;
        }
        if (ground > 0) {
            // standing
            wingArmGround[0] = 0;
            wingArmGround[1] = 1.4F - MathHelper.sin(a1) * MathHelper.sin(a2) * 0.02F;
            wingArmGround[2] = 0.8F + MathHelper.sin(a2) * MathHelper.sin(a3) * 0.05F;
            // walking
            wingArmGround[1] += MathHelper.sin(moveTime * 0.5F) * 0.02F * walk;
            wingArmGround[2] += MathHelper.cos(moveTime * 0.5F) * 0.05F * walk;
            wingForearmGround[0] = 0;
            wingForearmGround[1] = -wingArmGround[1] * 2;
            wingForearmGround[2] = 0;
        }
        // interpolate between fluttering and gliding
        slerpArrays(wingArmGlide, wingArmFlutter, wingArm, flutter);
        slerpArrays(wingForearmGlide, wingForearmFlutter, wingForearm, flutter);
        // interpolate between flying and grounded
        slerpArrays(wingArm, wingArmGround, wingArm, ground);
        slerpArrays(wingForearm, wingForearmGround, wingForearm, ground);
        // apply angles
        wing.xRot = wingArm[0];
        //model.wingArm.xRot += 1 - speed;
        wing.yRot = wingArm[1];
        wing.zRot = wingArm[2];
        wing.forearm.xRot = wingForearm[0];
        wing.forearm.yRot = wingForearm[1];
        wing.forearm.zRot = wingForearm[2];
        // interpolate between folded and unfolded wing angles
        float[] yFold = new float[]{2.7F, 2.8F, 2.9F, 3.0F};
        float[] yUnfold = new float[]{0.1F, 0.9F, 1.7F, 2.5F};
        // set wing finger angles
        float rotX = 0;
        float rotYOfs = MathHelper.sin(a1) * MathHelper.sin(a2) * 0.03F;
        float rotYMulti = 1;
        for (int i = 0; i < DragonWingModelPart.FINGER_COUNT; ++i) {
            ModelRenderer finger = wing.getFinger(i);
            finger.xRot = rotX += 0.005F; // reduce Z-fighting
            finger.yRot = clampedSmoothLinear(yUnfold[i], yFold[i] + rotYOfs * rotYMulti, ground);
            rotYMulti -= 0.2F;
        }
    }

    protected void animTail(DragonTailModelPart tail) {
        tail.x = 0;
        tail.y = 16;
        tail.z = 62;

        tail.xRot = 0;
        tail.yRot = 0;
        tail.zRot = 0;

        float rotXStand;
        float rotYStand = 0;
        float rotXSit;
        float rotYSit;
        float rotXAir = 0;
        float rotYAir = 0;

        for (int i = 0; i < DragonTailModelPart.TAIL_SEGMENT_COUNT; ++i) {
            float vertMulti = (i + 1) / (float) DragonTailModelPart.TAIL_SEGMENT_COUNT;
            // idle
            float amp = 0.1F + i / (DragonTailModelPart.TAIL_SEGMENT_COUNT * 2F);
            rotXStand = (i - DragonTailModelPart.TAIL_SEGMENT_COUNT * 0.6F) * -amp * 0.4F;
            rotXStand += (MathHelper.sin(animBase * 0.2F) * MathHelper.sin(animBase * 0.37F) * 0.4F * amp - 0.1F) * (1 - sit);
            rotXSit = rotXStand * 0.8F;
            rotYStand = (rotYStand + MathHelper.sin(i * 0.45F + animBase * 0.5F)) * amp * 0.4F;
            rotYSit = MathHelper.sin(vertMulti * MathUtil.PI * MathUtil.PI * 1.2F - 0.5F); // curl to the left
            rotXAir -= MathHelper.sin(i * 0.45F + animBase) * 0.04F * MathHelper.clampedLerp(0.3F, 1, flutter);
            // interpolate between sitting and standing
            tail.xRot = clampedLinear(rotXStand, rotXSit, sit);
            tail.yRot = clampedLinear(rotYStand, rotYSit, sit);
            // interpolate between flying and grounded
            tail.xRot = clampedLinear(rotXAir, tail.xRot, ground);
            tail.yRot = clampedLinear(rotYAir, tail.yRot, ground);
            // body movement
            float angleLimit = 160 * vertMulti;
            float yawOfs = MathHelper.clamp(yawTrail.get(partialTicks, 0, i + 1) * 2, -angleLimit, angleLimit);
            float pitchOfs = MathHelper.clamp(pitchTrail.get(partialTicks, 0, i + 1) * 2, -angleLimit, angleLimit);
            tail.xRot += Math.toRadians(pitchOfs);
            tail.xRot -= (1 - speed) * vertMulti * 2;
            tail.yRot += Math.toRadians(180 - yawOfs);
            // display horns near the tip
            tail.leftHorn.visible = tail.rightHorn.visible = this.dragon.getDragonType().resources.hasTailHorns
                    && i > DragonTailModelPart.TAIL_SEGMENT_COUNT - 7
                    && i < DragonTailModelPart.TAIL_SEGMENT_COUNT - 3;
            // update scale
            float neckScale = clampedLinear(1.5F, 0.3F, vertMulti);
            tail.scaleX = tail.scaleY = tail.scaleZ = neckScale;
            // update proxy
            tail.save(i);
            // move next proxy behind the current one
            float tailSize = DragonTailModelPart.TAIL_SIZE * tail.scaleZ - 0.7F;
            tail.y += MathHelper.sin(tail.xRot) * tailSize;
            tail.z -= MathHelper.cos(tail.yRot) * MathHelper.cos(tail.xRot) * tailSize;
            tail.x -= MathHelper.sin(tail.yRot) * MathHelper.cos(tail.xRot) * tailSize;
        }
    }

    protected void animLeg(DragonLegModelPart model, boolean hind, boolean left) {
        int index = hind ? 1 : 0;
        model.z = hind ? 46 : 4;
        // final X rotation angles for air
        float[] xAir = xAirAll[index];
        // interpolate between sitting and standing
        slerpArrays(xGroundStand[index], xGroundSit[index], xGround, sit);
        // align the toes so they're always horizontal on the ground
        xGround[3] = -(xGround[0] + xGround[1] + xGround[2]);
        // apply walking cycle
        if (walk > 0) {
            // interpolate between the keyframes, based on the cycle
            splineArrays(moveTime * 0.2F, left, xGroundWalk2, xGroundWalk[0][index], xGroundWalk[1][index], xGroundWalk[2][index]);
            // align the toes so they're always horizontal on the ground
            xGroundWalk2[3] -= xGroundWalk2[0] + xGroundWalk2[1] + xGroundWalk2[2];
            slerpArrays(xGround, xGroundWalk2, xGround, walk);
        }
        float yAir = yAirAll[index];
        float yGround;
        // interpolate between sitting and standing
        yGround = clampedSmoothLinear(yGroundStand[index], yGroundSit[index], sit);
        // interpolate between standing and walking
        yGround = clampedSmoothLinear(yGround, yGroundWalk[index], walk);
        // interpolate between flying and grounded
        model.yRot = clampedSmoothLinear(yAir, yGround, ground);
        model.xRot = clampedSmoothLinear(xAir[0], xGround[0], ground);
        model.shank.xRot = clampedSmoothLinear(xAir[1], xGround[1], ground);
        model.foot.xRot = clampedSmoothLinear(xAir[2], xGround[2], ground);
        model.toe.xRot = clampedSmoothLinear(xAir[3], xGround[3], ground);
        //update proxy: model.thighProxy[i].update();
    }

    protected void animLegs(DragonModel model) {
        // dangling legs for flying
        if (this.ground < 1) {
            float footAirOfs = this.cycleOfs * 0.1F;
            float footAirX = 0.75F + this.cycleOfs * 0.1F;
            this.xAirAll[0][0] = 1.3F + footAirOfs;
            this.xAirAll[0][1] = -(0.7F * this.speed + 0.1F + footAirOfs);
            this.xAirAll[0][2] = footAirX;
            this.xAirAll[0][3] = footAirX * 0.5F;
            this.xAirAll[1][0] = footAirOfs + 0.6F;
            this.xAirAll[1][1] = footAirOfs + 0.8F;
            this.xAirAll[1][2] = footAirX;
            this.xAirAll[1][3] = footAirX * 0.5F;
        }
        DragonLegConfig config = this.dragon.getDragonType().isSkeleton ? DragonLegConfig.SKELETON : DragonLegConfig.DEFAULT;
        this.animLeg(model.foreRightLeg.load(config), false, false);
        this.animLeg(model.hindRightLeg.load(config), true, false);
        this.animLeg(model.foreLeftLeg.load(config), false, true);
        this.animLeg(model.hindLeftLeg.load(config), true, true);
    }

    public float getModelPitch() {
        return getModelPitch(partialTicks);
    }

    public float getModelPitch(float pt) {
        float pitchMovingMax = 90;
        float pitchMoving = MathHelper.clamp(yTrail.get(pt, 5, 0) * 10, -pitchMovingMax, pitchMovingMax);
        float pitchHover = 60;
        return clampedSmoothLinear(pitchHover, pitchMoving, speed);
    }

    public float getModelOffsetX() {
        return 0;
    }

    public float getModelOffsetY() {
        return 1.5F + (-sit * 0.6F);
    }

    public float getModelOffsetZ() {
        return -1.5F;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public void setOpenJaw(boolean openJaw) {
        this.openJaw = openJaw;
    }

    private static void slerpArrays(float[] a, float[] b, float[] c, float x) {
        if (a.length != b.length || b.length != c.length) {
            throw new IllegalArgumentException();
        }

        if (x <= 0) {
            System.arraycopy(a, 0, c, 0, a.length);
            return;
        }
        if (x >= 1) {
            System.arraycopy(b, 0, c, 0, a.length);
            return;
        }

        for (int i = 0; i < c.length; ++i) {
            c[i] = clampedSmoothLinear(a[i], b[i], x);
        }
    }

    private static void splineArrays(float x, boolean shift, float[] result, float[]... nodes) {
        int i1 = (int) x % nodes.length;
        int i2 = (i1 + 1) % nodes.length;
        int i3 = (i1 + 2) % nodes.length;

        float[] a1 = nodes[i1];
        float[] a2 = nodes[i2];
        float[] a3 = nodes[i3];

        float xn = x % nodes.length - i1;

        if (shift) catmullRomSpline(xn, result, a2, a3, a1, a2);
        else catmullRomSpline(xn, result, a1, a2, a3, a1);
    }

    /**
     * Baby dragon has a relatively larger head compared to its body size (makes it look cuter)
     */
    public float getRelativeHeadSize(float scale) {
        // used to be 1.4F / (scale + 0.4F) i.e. a rational function of the form head_size = A / (scale + B)
        // We want the headsize of the adult to be SIZE_OF_ADULT_HEAD at SCALE_OF_ADULT, and
        //    headsize of the baby to be SIZE_OF_BABY_HEAD at SCALE_OF_BABY
        //  we can rearrange to solve for A and B
        final float B = (SIZE_OF_ADULT_HEAD * SCALE_OF_ADULT - SIZE_OF_BABY_HEAD * SCALE_OF_BABY);
        final float A = SIZE_OF_ADULT_HEAD * (SCALE_OF_ADULT + B);

        scale = MathHelper.clamp(scale, SCALE_OF_BABY, SCALE_OF_ADULT);
        return A * (scale + B);
    }
}

