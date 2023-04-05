package net.dragonmounts3.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.dragonmounts3.objects.entity.carriage.EntityCarriage;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

import static net.dragonmounts3.util.ModelUtil.setRotateAngle;

public class CarriageModel extends EntityModel<EntityCarriage> {

    public ModelRenderer field_78154_a2;
    public ModelRenderer field_78154_a3;
    public ModelRenderer field_78154_a1;
    public ModelRenderer field_78154_a6;
    public ModelRenderer field_78154_a4;
    public ModelRenderer field_78154_a5;

    public CarriageModel() {
        this.texWidth = 128;
        this.texHeight = 32;
        this.field_78154_a1 = new ModelRenderer(this, 0, 18);
        this.field_78154_a1.setPos(0.0F, 4.0F, -2.0F);
        this.field_78154_a1.addBox(-10.0F, -1.0F, -4.0F, 20, 1, 12, 0.0F);
        this.field_78154_a4 = new ModelRenderer(this, 40, 0);
        this.field_78154_a4.setPos(0.0F, 4.0F, -7.0F);
        this.field_78154_a4.addBox(-8.0F, -5.0F, -3.0F, 16, 4, 2, 0.0F);
        setRotateAngle(field_78154_a4, 0.0F, 3.141592653589793F, 0.0F);
        this.field_78154_a2 = new ModelRenderer(this, 0, 0);
        this.field_78154_a2.setPos(-4.0F, 4.0F, 0.0F);
        this.field_78154_a2.addBox(-6.0F, -4.0F, -6.0F, 12, 3, 2, 0.0F);
        setRotateAngle(field_78154_a2, 0.0F, 1.5707963267948966F, 0.0F);
        this.field_78154_a3 = new ModelRenderer(this, 0, 0);
        this.field_78154_a3.setPos(9.0F, 4.0F, 0.0F);
        this.field_78154_a3.addBox(-6.0F, -4.0F, -1.0F, 12, 3, 2, 0.0F);
        setRotateAngle(field_78154_a3, 0.0F, 1.5707963267948966F, 0.0F);
        this.field_78154_a6 = new ModelRenderer(this, 68, 18);
        this.field_78154_a6.setPos(0.0F, 2.5F, 0.0F);
        this.field_78154_a6.addBox(-8.0F, -1.0F, -6.0F, 16, 1, 12, 0.0F);
        this.field_78154_a5 = new ModelRenderer(this, 40, 0);
        this.field_78154_a5.setPos(0.0F, 4.0F, 7.0F);
        this.field_78154_a5.addBox(-8.0F, -5.0F, -3.0F, 16, 4, 2, 0.0F);
    }

    @Override
    public void setupAnim(EntityCarriage entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.field_78154_a1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.field_78154_a4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.field_78154_a2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.field_78154_a3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.field_78154_a6.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.field_78154_a5.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}