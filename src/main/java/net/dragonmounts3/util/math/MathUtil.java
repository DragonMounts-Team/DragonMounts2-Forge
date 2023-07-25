package net.dragonmounts3.util.math;

public class MathUtil {
    public static final float PI = (float) Math.PI;
    public static float getColor(int color, int area) {
        return (color >> (area * 8) & 0xFF) / 255F;
    }
}
