package tmswob.tmswobmod.events;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;
import tmswob.tmswobmod.TMSWOBMod;
import tmswob.tmswobmod.commands.SetLevelCommand;

@Mod.EventBusSubscriber(modid = TMSWOBMod.MODID)
public class TMSWOBEvents {

    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event){
        new SetLevelCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }
}
