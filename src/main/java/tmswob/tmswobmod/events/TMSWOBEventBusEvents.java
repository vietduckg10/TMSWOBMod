package tmswob.tmswobmod.events;

import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tmswob.tmswobmod.TMSWOBMod;
import tmswob.tmswobmod.entity.TMSWOBEntityTypes;
import tmswob.tmswobmod.entity.custom.BeanEntity;
import tmswob.tmswobmod.item.custom.TMSWOBSpawnEggItem;

@Mod.EventBusSubscriber(modid = TMSWOBMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TMSWOBEventBusEvents {
    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(TMSWOBEntityTypes.BEAN.get(), BeanEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void onRegisterEntities(RegistryEvent.Register<EntityType<?>> event) {
        TMSWOBSpawnEggItem.initSpawnEggs();
    }
}
