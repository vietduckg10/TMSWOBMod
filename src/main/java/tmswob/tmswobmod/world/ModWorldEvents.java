package tmswob.tmswobmod.world;

import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tmswob.tmswobmod.TMSWOBMod;
import tmswob.tmswobmod.world.gen.ModOreGeneration;

@Mod.EventBusSubscriber(modid = TMSWOBMod.MODID)
public class ModWorldEvents {
    @SubscribeEvent
    public static void biomeLoadingEvent(final BiomeLoadingEvent event){
        ModOreGeneration.generateOres(event);
    }
}
