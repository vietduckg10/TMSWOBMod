package tmswob.tmswobmod.world.gen;


import net.minecraft.block.Block;
import net.minecraftforge.common.util.Lazy;
import tmswob.tmswobmod.block.TMSWOBBlocks;

public enum OreType {

    BLOCK_OF_ORIGIN(Lazy.of(TMSWOBBlocks.BLOCK_OF_ORIGIN),6, 5,15);

    private final Lazy<Block> block;
    private final int maxVeinSize;
    private final int minHeight;
    private final int maxHeight;

    OreType(Lazy<Block> block, int maxVeinSize, int minHeight, int maxHeight) {
        this.block = block;
        this.maxVeinSize = maxVeinSize;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }

    public Lazy<Block> getBlock() {
        return block;
    }

    public int getMaxVeinSize() {
        return maxVeinSize;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public static OreType get(Block block){
        for (OreType ore : values()){
            return ore;
        }
        return null;
    }
}
