package net.dragonmounts3.client.model.dragon;

import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class DragonLegModelPart extends ModelRenderer {
    public final ModelRenderer shank;
    public final ModelRenderer foot;
    public final ModelRenderer toe;

    public DragonLegModelPart(Model model, DragonLegConfig config) {
        super(model);
        buildThigh(config);
        this.shank = this.createShank(model, config);
        this.addChild(this.shank);
        this.foot = this.createFoot(model, config);
        this.shank.addChild(this.foot);
        this.toe = this.createToe(model, config);
        this.foot.addChild(this.toe);
    }

    abstract protected void buildThigh(DragonLegConfig config);

    abstract protected ModelRenderer createShank(Model model, DragonLegConfig config);

    abstract protected ModelRenderer createFoot(Model model, DragonLegConfig config);

    abstract protected ModelRenderer createToe(Model model, DragonLegConfig config);

    public static class Fore extends DragonLegModelPart {
        public Fore(Model model, DragonLegConfig config) {
            super(model, config);
        }

        @Override
        protected void buildThigh(DragonLegConfig config) {
            final int length = config.getThighLength(false);
            final int width = config.width;
            this.setPos(-11, 18, 4);
            this.texOffs(112, 0);
            this.addBox(config.defaultOffset, config.defaultOffset, config.defaultOffset, width, length, width);

        }

        @Override
        protected ModelRenderer createShank(Model model, DragonLegConfig config) {
            final int length = config.getShankLength(false);
            final ModelRenderer renderer = new ModelRenderer(model);
            renderer.texOffs(148, 0);
            renderer.setPos(0, config.getThighLength(false) + config.defaultOffset, 0);
            return renderer.addBox(config.shankOffset, config.shankOffset, config.shankOffset, config.shankWidth, length, config.shankWidth);
        }

        @Override
        protected ModelRenderer createFoot(Model model, DragonLegConfig config) {
            final int length = config.getFootLength(false);
            final ModelRenderer renderer = new ModelRenderer(model);
            renderer.texOffs(210, 0);
            renderer.setPos(0, config.getShankLength(false) + config.shankOffset, 0);
            return renderer.addBox(config.defaultOffset, config.footOffset, length * -0.75f, config.width, config.footHeight, length);
        }

        @Override
        protected ModelRenderer createToe(Model model, DragonLegConfig config) {
            final int length = config.getToeLength(false);
            final ModelRenderer renderer = new ModelRenderer(model);
            renderer.setPos(0, 0, config.getFootLength(false) * -0.75f - config.footOffset / 2f);
            renderer.texOffs(176, 0);
            return renderer.addBox(config.defaultOffset, config.footOffset, -length, config.width, config.footHeight, length);
        }
    }

    public static class Hind extends DragonLegModelPart {
        public Hind(Model model, DragonLegConfig config) {
            super(model, config);
        }

        @Override
        protected void buildThigh(DragonLegConfig config) {
            final int length = config.getThighLength(true);
            final int width = config.width + 1;
            this.setPos(-11, 13, 4);
            this.texOffs(112, 29);
            this.addBox(config.defaultOffset, config.defaultOffset, config.defaultOffset, width, length, width);
        }

        @Override
        protected ModelRenderer createShank(Model model, DragonLegConfig config) {
            final int length = config.getShankLength(true);
            final ModelRenderer renderer = new ModelRenderer(model);
            renderer.texOffs(152, 29);
            renderer.setPos(0, config.getThighLength(true) + config.defaultOffset, 0);
            return renderer.addBox(config.shankOffset, config.shankOffset, config.shankOffset, config.shankWidth, length, config.shankWidth);
        }

        @Override
        protected ModelRenderer createFoot(Model model, DragonLegConfig config) {
            final int length = config.getFootLength(true);
            final ModelRenderer renderer = new ModelRenderer(model);
            renderer.texOffs(180, 29);
            renderer.setPos(0, config.getShankLength(true) + config.shankOffset, 0);
            return renderer.addBox(config.defaultOffset, config.footOffset, length * -0.75f, config.width, config.footHeight, length);
        }

        @Override
        protected ModelRenderer createToe(Model model, DragonLegConfig config) {
            final int length = config.getToeLength(true);
            final ModelRenderer renderer = new ModelRenderer(model);
            renderer.setPos(0, 0, config.getFootLength(true) * -0.75f - config.footOffset / 2f);
            renderer.texOffs(215, 29);
            return renderer.addBox(config.defaultOffset, config.footOffset, -length, config.width, config.footHeight, length);
        }
    }
}
