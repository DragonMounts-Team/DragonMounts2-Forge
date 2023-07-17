package net.dragonmounts3.client.model.dragon;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DragonLegConfig {
    public static final int LEG_BASE_LENGTH = 26;
    public static final int LEG_BASE_WIDTH = 9;
    public static final int FOOT_HEIGHT = 4;
    public static final DragonLegConfig DEFAULT = new DragonLegConfig(LEG_BASE_LENGTH, LEG_BASE_WIDTH, FOOT_HEIGHT);
    public static final DragonLegConfig SKELETON = new DragonLegConfig(LEG_BASE_LENGTH, LEG_BASE_WIDTH, FOOT_HEIGHT);
    public final int length;
    public final int width;
    public final int footHeight;
    public final int shankWidth;
    public final float defaultOffset;
    public final float shankOffset;
    public final float footOffset;

    public DragonLegConfig(int length, int width, int footHeight) {
        this.length = length;
        this.width = width;
        this.footHeight = footHeight;
        this.shankWidth = width - 2;
        this.defaultOffset = -width / 2f;
        this.shankOffset = -this.shankWidth / 2f;
        this.footOffset = -this.footHeight / 2f;
    }

    public int getThighLength(boolean hind) {
        return (int) (this.length * (hind ? 0.90f : 0.77f));
    }

    public int getShankLength(boolean hind) {
        return (int) (hind ? this.length * 0.70f - 2 : this.length * 0.80f);
    }

    public int getFootLength(boolean hind) {
        return (int) (this.length * (hind ? 0.67f : 0.34f));
    }

    public int getToeLength(boolean hind) {
        return (int) (this.length * (hind ? 0.27f : 0.33f));
    }
}
