package net.dragonmounts3.util;

public class MathUtil {
    public static final float PI = (float) Math.PI;

    public static float clampedLerp(float start, float end, float delta) {
        if (delta < 0.0f) return start;
        if (delta > 1.0f) return end;
        return start + delta * (end - start);
    }

    public static float smoothClampedLerp(float start, float end, float delta) {
        if (delta < 0.0f) return start;
        if (delta > 1.0f) return end;
        delta = delta * delta * (3 - 2 * delta);
        return start + delta * (end - start);
    }
}
