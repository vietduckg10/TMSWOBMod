package tmswob.tmswobmod.entity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tmswob.tmswobmod.TMSWOBMod;
import tmswob.tmswobmod.entity.custom.BeanEntity;
import tmswob.tmswobmod.entity.custom.BeanWithHandEntity;

public class TMSWOBEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(
            ForgeRegistries.ENTITIES, TMSWOBMod.MODID
    );

    public static final RegistryObject<EntityType<BeanEntity>> BEAN =
            ENTITY_TYPES.register("bean", () ->
                    EntityType.Builder.of(BeanEntity::new, EntityClassification.CREATURE)
                            .sized(0.5f, 0.625f)
                            .build(new ResourceLocation(TMSWOBMod.MODID, "bean").toString()));
    public static final RegistryObject<EntityType<BeanWithHandEntity>> BEAN_WITH_HAND =
            ENTITY_TYPES.register("bean_with_hand", () ->
                    EntityType.Builder.of(BeanWithHandEntity::new, EntityClassification.CREATURE)
                            .sized(0.5f, 0.625f)
                            .build(new ResourceLocation(TMSWOBMod.MODID, "bean_with_hand").toString()));

    public static void init(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}