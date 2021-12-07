package tmswob.tmswobmod.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import tmswob.tmswobmod.entity.custom.BeanWithHandEntity;

import java.util.function.Consumer;

public class BeanWithHandModel<T extends BeanWithHandEntity> extends EntityModel<T> {
    private final ModelRenderer body;
    private final ModelRenderer hand;
    private final ModelRenderer right_hand;
    private final ModelRenderer left_hand;

    public BeanWithHandModel() {
        texWidth = 32;
        texHeight = 32;

        body = new ModelRenderer(this);
        body.setPos(0.0F, 24.0F, 0.0F);
        body.texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, 0.0F, false);

        hand = new ModelRenderer(this);
        hand.setPos(5.0F, 17.0F, -2.0F);


        right_hand = new ModelRenderer(this);
        right_hand.setPos(-10.0F, 0.0F, 0.0F);
        hand.addChild(right_hand);
        right_hand.texOffs(8, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, true);

        left_hand = new ModelRenderer(this);
        left_hand.setPos(0.0F, 0.0F, 0.0F);
        hand.addChild(left_hand);
        left_hand.texOffs(8, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {

    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        body.render(matrixStack, buffer, packedLight, packedOverlay);
        hand.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    @Override
    public Consumer<ModelRenderer> andThen(Consumer<? super ModelRenderer> after) {
        return super.andThen(after);
    }
}
