package tmswob.tmswobmod.entity.render;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import tmswob.tmswobmod.TMSWOBMod;
import tmswob.tmswobmod.entity.custom.BeanWithHandEntity;
import tmswob.tmswobmod.entity.model.BeanWithHandModel;

public class BeanWithHandRenderer extends MobRenderer<BeanWithHandEntity, BeanWithHandModel<BeanWithHandEntity>> {

    protected static final ResourceLocation TEXTURE =
            new ResourceLocation(TMSWOBMod.MODID, "textures/entity/bean.png");


    public BeanWithHandRenderer(EntityRendererManager rendererManager) {
        super(rendererManager, new BeanWithHandModel<BeanWithHandEntity>(), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(BeanWithHandEntity p_110775_1_) {
        return TEXTURE;
    }

}
