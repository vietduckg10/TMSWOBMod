package tmswob.tmswobmod.entity.render;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import tmswob.tmswobmod.TMSWOBMod;
import tmswob.tmswobmod.entity.custom.BeanEntity;
import tmswob.tmswobmod.entity.model.BeanModel;

public class BeanRenderer extends MobRenderer<BeanEntity, BeanModel<BeanEntity>> {

    protected static final ResourceLocation TEXTURE =
            new ResourceLocation(TMSWOBMod.MODID, "textures/entity/bean.png");


    public BeanRenderer(EntityRendererManager rendererManager) {
        super(rendererManager, new BeanModel<BeanEntity>(), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(BeanEntity p_110775_1_) {
        return TEXTURE;
    }

}
