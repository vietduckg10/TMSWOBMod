package tmswob.tmswobmod.world;

import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tmswob.tmswobmod.TMSWOBMod;
import tmswob.tmswobmod.world.gen.TMSWOBOreGeneration;
import tmswob.tmswobmod.world.gen.TMSWOBEntityGeneration;

@Mod.EventBusSubscriber(modid = TMSWOBMod.MODID)
public class TMSWOBWorldEvents {
    @SubscribeEvent
    public static void biomeLoadingEvent(final BiomeLoadingEvent event){
        TMSWOBOreGeneration.generateOres(event);
        TMSWOBEntityGeneration.onEntitySpawn(event);
    }
}
