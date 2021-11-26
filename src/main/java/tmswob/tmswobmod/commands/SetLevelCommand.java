package tmswob.tmswobmod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import tmswob.tmswobmod.item.TMSWOBArmorMaterial;
import tmswob.tmswobmod.item.custom.*;

import java.util.Arrays;

public class SetLevelCommand {

    private final String[] validItem = new String[] {
            "Origin Infuse Netherite Chestplate",
            "Origin Infuse Netherite Helmet",
            "Origin Infuse Netherite Boots",
            "Origin Infuse Netherite Leggings",
            "Origin Infuse Netherite Sword",
            "Origin Infuse Netherite Pickaxe",
            "Origin Infuse Netherite Axe",
            "Origin Infuse Netherite Shovel",
            "Origin Infuse Netherite Hoe"
    };

    public SetLevelCommand(CommandDispatcher<CommandSource> dispatcher){
        dispatcher.register(Commands.literal("tmswob")
                .then(Commands.literal("setlevel")
                        .then(Commands.argument("level", IntegerArgumentType.integer(1,100))
                                .executes((command) -> {
                                    return setLevel(command.getSource(), IntegerArgumentType.getInteger(command, "level"));
                                }))));
    }

    public int setLevel(CommandSource source, int level) throws CommandSyntaxException {
        ServerPlayerEntity playerEntity = source.getPlayerOrException();
        ItemStack stack = playerEntity.getItemInHand(Hand.MAIN_HAND);
        if(Arrays.stream(validItem).anyMatch(stack.getItem().getName(stack).getString()::equals)){
            CompoundNBT nbt = stack.getTag();
            nbt.putInt("Level", level);
            if (stack.getItem() instanceof TMSWOBArmorItem){
                nbt.putLong("MaxExp", ((TMSWOBArmorItem)stack.getItem()).setMaxExp(stack));
                ((TMSWOBArmorItem) stack.getItem()).upgradeStats(stack);
            }
            if (stack.getItem() instanceof TMSWOBSwordItem){
                nbt.putLong("MaxExp", ((TMSWOBSwordItem)stack.getItem()).setMaxExp(stack));
                ((TMSWOBSwordItem) stack.getItem()).upgradeStats(stack);
            }
            if (stack.getItem() instanceof TMSWOBPickaxeItem){
                nbt.putLong("MaxExp", ((TMSWOBPickaxeItem)stack.getItem()).setMaxExp(stack));
                ((TMSWOBPickaxeItem) stack.getItem()).upgradeStats(stack);
            }
            if (stack.getItem() instanceof TMSWOBAxeItem){
                nbt.putLong("MaxExp", ((TMSWOBAxeItem)stack.getItem()).setMaxExp(stack));
                ((TMSWOBAxeItem) stack.getItem()).upgradeStats(stack);
            }
            if (stack.getItem() instanceof TMSWOBShovelItem){
                nbt.putLong("MaxExp", ((TMSWOBShovelItem)stack.getItem()).setMaxExp(stack));
                ((TMSWOBShovelItem) stack.getItem()).upgradeStats(stack);
            }
            if (stack.getItem() instanceof TMSWOBHoeItem){
                nbt.putLong("MaxExp", ((TMSWOBHoeItem)stack.getItem()).setMaxExp(stack));
                ((TMSWOBHoeItem) stack.getItem()).upgradeStats(stack);
            }
        }
        return 1;
    }
}
