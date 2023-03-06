package net.dragonmounts3.ROTD.util.math;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

import java.util.Random;

/**
 * Interpolation utility class.
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
@SuppressWarnings("unused")
public class MathX {

    public static boolean useLUT = true;
    public static final double PI_D = Math.PI;
    public static final float PI_F = (float) Math.PI;
    public final static double MINIMUM_SIGNIFICANT_DIFFERENCE = 1e-3;

    private MathX() {}

    // float sine function, may use LUT
    public static float sin(float a) {
        return (float) Math.sin(a);
    }

    // float cosine function, may use LUT
    public static float cos(float a) {
        return (float) Math.cos(a);
    }

    // float tangent function
    public static float tan(float a) {
        return (float) Math.tan(a);
    }

    // float atan2 function
    public static float atan2(float y, float x) {
        return (float) Math.atan2(y, x);
    }

    // float degrees to radians conversion
    public static float toRadians(float angdeg) {
        return (float) Math.toRadians(angdeg);
    }

    // float radians to degrees conversion
    public static float toDegrees(float angrad) {
        return (float) Math.toDegrees(angrad);
    }

    // normalizes a float degrees angle to between +180 and -180
    public static float normDeg(float a) {
        a %= 360;
        if (a >= 180) {
            a -= 360;
        }
        if (a < -180) {
            a += 360;
        }
        return a;
    }

    // normalizes a double degrees angle to between +180 and -180
    public static double normDeg(double a) {
        a %= 360;
        if (a >= 180) {
            a -= 360;
        }
        if (a < -180) {
            a += 360;
        }
        return a;
    }

    // normalizes a float radians angle to between +π and -π
    public static float normRad(float a) {
        a %= PI_F * 2;
        if (a >= PI_F) {
            a -= PI_F * 2;
        }
        if (a < -PI_F) {
            a += PI_F * 2;
        }
        return a;
    }

    // normalizes a double radians angle to between +π and -π
    public static double normRad(double a) {
        a %= PI_D * 2;
        if (a >= PI_D) {
            a -= PI_D * 2;
        }
        if (a < -PI_D) {
            a += PI_D * 2;
        }
        return a;
    }

    /**
     * return a random value from a truncated gaussian distribution with
     * mean and standard deviation = threeSigma/3
     * distribution is truncated to +/- threeSigma.
     *
     * @param mean the mean of the distribution
     * @param threeSigma three times the standard deviation of the distribution
     */
    public static double getTruncatedGaussian(Random rand, double mean, double threeSigma) {
        double rawValue = rand.nextGaussian();
        rawValue = MathHelper.clamp(rawValue, -3.0, +3.0);
        return mean + rawValue * threeSigma / 3.0;
    }

    // float square root
    public static float sqrtf(float f) {
        return (float) Math.sqrt(f);
    }

    public static float clamp(float value, float min, float max) {
        return (value < min ? min : (Math.min(value, max)));
    }

    public static double clamp(double value, double min, double max) {
        return (value < min ? min : (Math.min(value, max)));
    }

    public static int clamp(int value, int min, int max) {
        return (value < min ? min : (Math.min(value, max)));
    }

    public static int clamps(int value, int min, int max) {
        return (value < min ? min : (Math.min(value, max)));
    }

    public static float updateRotation(float r1, float r2, float step) {
        return r1 + clamp(normDeg(r2 - r1), -step, step);
    }

    public static float lerp(float a, float b, float x) {
        return a * (1 - x) + b * x;
    }

    public static double lerp(double a, double b, double x) {
        return a * (1 - x) + b * x;
    }

    public static float slerp(float a, float b, float x) {
        if (x <= 0) {
            return a;
        }
        if (x >= 1) {
            return b;
        }

        return lerp(a, b, x * x * (3 - 2 * x));
    }

    public static float terp(float a, float b, float x) {
        if (x <= 0) {
            return a;
        }
        if (x >= 1) {
            return b;
        }

        float mu2 = (1 - cos(x * PI_F)) / 2.0f;
        return a * (1 - mu2) + b * mu2;
    }

    public static double terp(double a, double b, double x) {
        if (x <= 0) {
            return a;
        }
        if (x >= 1) {
            return b;
        }

        double mu2 = (1 - Math.cos(x * PI_D)) / 2.0;
        return a * (1 - mu2) + b * mu2;
    }

    public static float constrainAngle(float targetAngle, float centreAngle, float maximumDifference) {
        return centreAngle + clamp(normDeg(targetAngle - centreAngle), -maximumDifference, maximumDifference);
    }

    public static Vector3d multiply(Vector3d source, double multiplier) {
        return new Vector3d(source.x * multiplier, source.y * multiplier, source.z * multiplier);
    }

    public static boolean isApproximatelyEqual(double x1, double x2) {
        return Math.abs(x1 - x2) <= MINIMUM_SIGNIFICANT_DIFFERENCE;
    }

    public static boolean isSignificantlyDifferent(double x1, double x2) {
        return Math.abs(x1 - x2) > MINIMUM_SIGNIFICANT_DIFFERENCE;
    }

    public static int modulus(int numerator, int divisor) {
        return (numerator % divisor + divisor) % divisor;
    }

    public static float wrapAngleTo180(float angle) {
        angle %= 360.0F;

        if (angle >= 180.0F) {
            angle -= 360.0F;
        }

        if (angle < -180.0F) {
            angle += 360.0F;
        }

        return angle;
    }

    public static double wrapAngleTo180(double angle) {
        angle %= 360.0D;

        if (angle >= 180.0D) {
            angle -= 360.0D;
        }

        if (angle < -180.0D) {
            angle += 360.0D;
        }

        return angle;
    }

    public static float invSqrt(float x) {
        float xHalf = 0.5f * x;
        int i = Float.floatToIntBits(x);
        i = 0x5f3759df - (i >> 1);
        x = Float.intBitsToFloat(i);
        x *= (1.5f - xHalf * x * x);
        return x;
    }

    // calculate the yaw from the given direction
    // returns from -180 to +180
    public static double calculateYaw(Vector3d direction) {
        double yaw = (Math.atan2(direction.z, direction.x) * 180.0D / Math.PI) - 90.0F;
        yaw = MathX.normDeg(yaw);
        return yaw;
    }

    // calculate the pitch from the given direction
    // returns from -90 to +90
    public static double calculatePitch(Vector3d direction) {
        double xz_norm = MathHelper.sqrt(direction.x * direction.x + direction.z * direction.z);
        return -(Math.atan2(direction.y, xz_norm) * 180.0D / Math.PI);
    }

    public static int getRandomInRange(Random random, int minValue, int maxValue) {
        return random.nextInt(maxValue - minValue + 1) + minValue;
    }

    public static float getRandomInRange(Random random, float minValue, float maxValue) {
        return random.nextFloat() * (maxValue - minValue) + minValue;
    }

    public static double getClosestDistanceSQ(AxisAlignedBB aabb, Vector3d point) {
        double dx = Math.max(Math.max(0, aabb.minX - point.x), point.x - aabb.maxX);
        double dy = Math.max(Math.max(0, aabb.minY - point.y), point.y - aabb.maxY);
        double dz = Math.max(Math.max(0, aabb.minZ - point.z), point.z - aabb.maxZ);
        return dx * dx + dy * dy + dz * dz;
    }

    // the angle is reduced to an angle between -180 and +180 by mod, and a 360 check.
    public static float wrapDegrees(float value) {
        value = value % 360.0F;
        if (value >= 180.0F) {
            value -= 360.0F;
        }

        if (value < -180.0F) {
            value += 360.0F;
        }

        return value;
    }

}
