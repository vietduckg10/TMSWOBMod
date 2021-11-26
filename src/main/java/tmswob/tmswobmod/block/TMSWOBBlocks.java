package tmswob.tmswobmod.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tmswob.tmswobmod.TMSWOBMod;
import tmswob.tmswobmod.item.TMSWOBItemGroup;
import tmswob.tmswobmod.item.TMSWOBItems;

import java.util.function.Supplier;

public class TMSWOBBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TMSWOBMod.MODID);
    public static void init(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> returnBlock = BLOCKS.register(name, block);
        registerBlockItem(name, returnBlock);
        return returnBlock;
    }
    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block){
        TMSWOBItems.ITEMS.register(name, () ->
                new BlockItem(block.get(),
                        new Item.Properties().tab(TMSWOBItemGroup.TMSWOB_GROUP)));
    }

    public static final RegistryObject<Block> BLOCK_OF_ORIGIN = registerBlock("block_of_origin", () ->
            new Block(AbstractBlock.Properties.of(Material.METAL)
                    .strength(5.0f, 6.0f)
                    .harvestLevel(2)
                    .harvestTool(ToolType.PICKAXE)
                    .sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()
                    .lightLevel((p_235470_0_) -> {return 15;}))
    );
    public static final RegistryObject<Block> SLAB_OF_ORIGIN = registerBlock("slab_of_origin", () ->
            new SlabBlock(AbstractBlock.Properties.copy(BLOCK_OF_ORIGIN.get()))
    );
    public static final RegistryObject<Block> STAIRS_OF_ORIGIN = registerBlock("stairs_of_origin", () ->
            new StairsBlock(() -> BLOCK_OF_ORIGIN.get().defaultBlockState(),
                    AbstractBlock.Properties.copy(BLOCK_OF_ORIGIN.get()))
    );
    public static final RegistryObject<Block> PRESSURE_PLATE_OF_ORIGIN = registerBlock("pressure_plate_of_origin", () ->
            new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, AbstractBlock.Properties.of(Material.METAL)
                    .strength(0.5f)
                    .harvestLevel(2)
                    .harvestTool(ToolType.PICKAXE)
                    .sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()
                    .lightLevel((p_235470_0_) -> {return 15;})
                    .noCollission())
    );
    public static final RegistryObject<Block> WALL_OF_ORIGIN = registerBlock("wall_of_origin", () ->
            new WallBlock(AbstractBlock.Properties.copy(BLOCK_OF_ORIGIN.get()))
    );
    public static final RegistryObject<Block> BUTTON_OF_ORIGIN = registerBlock("button_of_origin", () ->
            new StoneButtonBlock(AbstractBlock.Properties.of(Material.METAL)
                    .strength(0.5f)
                    .harvestLevel(2)
                    .harvestTool(ToolType.PICKAXE)
                    .sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()
                    .lightLevel((p_235470_0_) -> {return 15;})
                    .noCollission())
    );
    public static final RegistryObject<Block> ORIGIN_STONE = registerBlock("origin_stone", () ->
            new Block(AbstractBlock.Properties.of(Material.METAL)
                    .strength(5.0f, 6.0f)
                    .harvestLevel(2)
                    .harvestTool(ToolType.PICKAXE)
                    .sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()
                    .lightLevel((p_235470_0_) -> {return 15;}))
    );
    public static final RegistryObject<Block> ORIGIN_SLAB = registerBlock("origin_slab", () ->
            new SlabBlock(AbstractBlock.Properties.copy(ORIGIN_STONE.get()))
    );
    public static final RegistryObject<Block> ORIGIN_STAIRS = registerBlock("origin_stairs", () ->
            new StairsBlock(() -> ORIGIN_STONE.get().defaultBlockState(),
                    AbstractBlock.Properties.copy(ORIGIN_STONE.get()))
    );
    public static final RegistryObject<Block> ORIGIN_PRESSURE_PLATE = registerBlock("origin_pressure_plate", () ->
            new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, AbstractBlock.Properties.of(Material.METAL)
                    .strength(0.5f)
                    .harvestLevel(2)
                    .harvestTool(ToolType.PICKAXE)
                    .sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()
                    .lightLevel((p_235470_0_) -> {return 15;})
                    .noCollission())
    );
    public static final RegistryObject<Block> ORIGIN_WALL = registerBlock("origin_wall", () ->
            new WallBlock(AbstractBlock.Properties.copy(ORIGIN_STONE.get()))
    );
    public static final RegistryObject<Block> ORIGIN_BUTTON = registerBlock("origin_button", () ->
            new StoneButtonBlock(AbstractBlock.Properties.of(Material.METAL)
                    .strength(0.5f)
                    .harvestLevel(2)
                    .harvestTool(ToolType.PICKAXE)
                    .sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()
                    .lightLevel((p_235470_0_) -> {return 15;})
                    .noCollission())
    );
//    public static final RegistryObject<Block> ORIGIN_CHEST = registerBlock("origin_chest", () ->
//            new ChestBlock(AbstractBlock.Properties.of(Material.WOOD)
//                    .strength(2.5f)
//                    .sound(SoundType.WOOD)
//                    .lightLevel((p_235470_0_) -> {return 13;}),
//                    () -> {
//                        return TileEntityType.CHEST;
//                    }
//            )
//    );
}
