package net.dragonmounts3.client.model.dragon;

import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

public class DragonLegModelPart extends ModelRenderer {
    public static final int LEG_BASE_LENGTH = 26;
    public static final int LEG_BASE_WIDTH = 9;
    public static final int FOOT_HEIGHT = 4;
    public final ModelRenderer shank;
    public final ModelRenderer foot;
    public final ModelRenderer toe;

    public DragonLegModelPart(Model model, Config config, boolean hind) {
        super(model);
        buildThigh(config, hind);
        this.shank = this.createShank(model, config, hind);
        this.addChild(this.shank);
        this.foot = this.createFoot(model, config, hind);
        this.shank.addChild(this.foot);
        this.toe = this.createToe(model, config, hind);
        this.foot.addChild(this.toe);
    }

    protected void buildThigh(Config config, boolean hind) {
        final int length = config.getThighLength(hind);
        final int width;
        if (hind) {
            width = config.width + 1;
            this.setPos(-11, 13, 4);
            this.texOffs(112, 29);
        } else {
            width = config.width;
            this.setPos(-11, 18, 4);
            this.texOffs(112, 0);
        }
        this.addBox(config.defaultOffset, config.defaultOffset, config.defaultOffset, width, length, width);
    }

    protected ModelRenderer createShank(Model model, Config config, boolean hind) {
        final int length = config.getShankLength(hind);
        final ModelRenderer renderer = new ModelRenderer(model);
        if (hind) {
            renderer.texOffs(152, 29);
        } else {
            renderer.texOffs(148, 0);
        }
        renderer.setPos(0, config.getThighLength(hind) + config.defaultOffset, 0);
        return renderer.addBox(config.shankOffset, config.shankOffset, config.shankOffset, config.shankWidth, length, config.shankWidth);
    }

    protected ModelRenderer createFoot(Model model, Config config, boolean hind) {
        final int length = config.getFootLength(hind);
        final ModelRenderer renderer = new ModelRenderer(model);
        if (hind) {
            renderer.texOffs(180, 29);
        } else {
            renderer.texOffs(210, 0);
        }
        renderer.setPos(0, config.getShankLength(hind) + config.shankOffset, 0);
        return renderer.addBox(config.defaultOffset, config.footOffset, length * -0.75f, config.width, config.footHeight, length);
    }

    protected ModelRenderer createToe(Model model, Config config, boolean hind) {
        final int length = config.getToeLength(hind);
        final ModelRenderer renderer = new ModelRenderer(model);
        if (hind) {
            renderer.setPos(0, 0, config.getFootLength(true) * -0.75f - config.footOffset / 2f);
            renderer.texOffs(215, 29);
        } else {
            renderer.setPos(0, 0, config.getFootLength(false) * -0.75f - config.footOffset / 2f);
            renderer.texOffs(176, 0);
        }
        return renderer.addBox(config.defaultOffset, config.footOffset, -length, config.width, config.footHeight, length);
    }

    public static class Config {
        public static final Config DEFAULT = new Config(LEG_BASE_LENGTH, LEG_BASE_WIDTH, FOOT_HEIGHT);
        public static final Config SKELETON = new Config(LEG_BASE_LENGTH, LEG_BASE_WIDTH, FOOT_HEIGHT);
        public final int length;
        public final int width;
        public final int footHeight;
        public final int shankWidth;
        public final float defaultOffset;
        public final float shankOffset;
        public final float footOffset;

        public Config(int length, int width, int footHeight) {
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
}
