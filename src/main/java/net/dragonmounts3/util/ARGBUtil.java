package net.dragonmounts3.util;

public class ARGBUtil {
    public static float getColorR(int color) {
        return ((color >> 16) & 0xFF) / 255F;
    }

    public static float getColorG(int color) {
        return ((color >> 8) & 0xFF) / 255F;
    }

    public static float getColorB(int color) {
        return (color & 0xFF) / 255F;
    }

    public static float getColorA(int color) {
        return ((color >> 24) & 0xFF) / 255F;
    }
}
