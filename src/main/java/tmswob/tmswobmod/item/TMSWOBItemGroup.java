package tmswob.tmswobmod.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import tmswob.tmswobmod.block.TMSWOBBlocks;

public class TMSWOBItemGroup {
    public static final ItemGroup TMSWOB_GROUP = new ItemGroup("TMSWOB") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(TMSWOBBlocks.BLOCK_OF_ORIGIN.get());
        }
    };
}
