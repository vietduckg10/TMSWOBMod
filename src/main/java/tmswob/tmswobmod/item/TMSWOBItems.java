package tmswob.tmswobmod.item;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tmswob.tmswobmod.TMSWOBMod;
import tmswob.tmswobmod.entity.TMSWOBEntityTypes;
import tmswob.tmswobmod.item.custom.*;

public class TMSWOBItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TMSWOBMod.MODID);
    public static void init(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    public static final RegistryObject<Item> ORIGIN_SHARD = ITEMS.register("origin_shard", () ->
            new Item(
                    new Item.Properties().tab(TMSWOBItemGroup.TMSWOB_GROUP)
            )
    );
    public static final RegistryObject<Item> ORIGIN_INFUSE_APPLE = ITEMS.register("origin_infuse_apple", () ->
            new Item(
                    new OriginInfuseApple.Properties()
                            .tab(TMSWOBItemGroup.TMSWOB_GROUP)
                            .food(TMSWOBFoodItems.ORIGIN_INFUSE_APPLE)
            )
    );
    public static final RegistryObject<Item> ORIGIN_INFUSE_NETHERITE_HELMET = ITEMS.register("origin_infuse_netherite_helmet", () ->
            new TMSWOBArmorItem(
                    TMSWOBArmorMaterial.ORIGIN_INFUSE_NETHERITE,
                    EquipmentSlotType.HEAD,
                    new Item.Properties().tab(TMSWOBItemGroup.TMSWOB_GROUP)
            )
    );
    public static final RegistryObject<Item> ORIGIN_INFUSE_NETHERITE_CHESTPLATE = ITEMS.register("origin_infuse_netherite_chestplate", () ->
            new TMSWOBArmorItem(
                    TMSWOBArmorMaterial.ORIGIN_INFUSE_NETHERITE,
                    EquipmentSlotType.CHEST,
                    new Item.Properties().tab(TMSWOBItemGroup.TMSWOB_GROUP)
            )
    );
    public static final RegistryObject<Item> ORIGIN_INFUSE_NETHERITE_LEGGINGS = ITEMS.register("origin_infuse_netherite_leggings", () ->
            new TMSWOBArmorItem(
                    TMSWOBArmorMaterial.ORIGIN_INFUSE_NETHERITE,
                    EquipmentSlotType.LEGS,
                    new Item.Properties().tab(TMSWOBItemGroup.TMSWOB_GROUP)
            )
    );
    public static final RegistryObject<Item> ORIGIN_INFUSE_NETHERITE_BOOTS = ITEMS.register("origin_infuse_netherite_boots", () ->
            new TMSWOBArmorItem(
                    TMSWOBArmorMaterial.ORIGIN_INFUSE_NETHERITE,
                    EquipmentSlotType.FEET,
                    new Item.Properties().tab(TMSWOBItemGroup.TMSWOB_GROUP)
            )
    );
    public static final RegistryObject<Item> ORIGIN_INFUSE_NETHERITE_SWORD = ITEMS.register("origin_infuse_netherite_sword", () ->
            new TMSWOBSwordItem(
                    TMSWOBItemTier.ORIGIN_INFUSE_NETHERITE,
                    3, -2.4F,
                    new Item.Properties().tab(TMSWOBItemGroup.TMSWOB_GROUP).fireResistant()
            )
    );
    public static final RegistryObject<Item> ORIGIN_INFUSE_NETHERITE_PICKAXE = ITEMS.register("origin_infuse_netherite_pickaxe", () ->
            new TMSWOBPickaxeItem(
                    TMSWOBItemTier.ORIGIN_INFUSE_NETHERITE,
                    1, -2.8F,
                    new Item.Properties().tab(TMSWOBItemGroup.TMSWOB_GROUP).fireResistant()
            )
    );
    public static final RegistryObject<Item> ORIGIN_INFUSE_NETHERITE_AXE = ITEMS.register("origin_infuse_netherite_axe", () ->
            new TMSWOBAxeItem(
                    TMSWOBItemTier.ORIGIN_INFUSE_NETHERITE,
                    5, -3F,
                    new Item.Properties().tab(TMSWOBItemGroup.TMSWOB_GROUP).fireResistant()
            )
    );
    public static final RegistryObject<Item> ORIGIN_INFUSE_NETHERITE_SHOVEL = ITEMS.register("origin_infuse_netherite_shovel", () ->
            new TMSWOBShovelItem(
                    TMSWOBItemTier.ORIGIN_INFUSE_NETHERITE,
                    (float) 1.5, -3F,
                    new Item.Properties().tab(TMSWOBItemGroup.TMSWOB_GROUP).fireResistant()
            )
    );
    public static final RegistryObject<Item> ORIGIN_INFUSE_NETHERITE_HOE = ITEMS.register("origin_infuse_netherite_hoe", () ->
            new TMSWOBHoeItem(
                    TMSWOBItemTier.ORIGIN_INFUSE_NETHERITE,
                    -4, 0,
                    new Item.Properties().tab(TMSWOBItemGroup.TMSWOB_GROUP).fireResistant()
            )
    );
    public static final RegistryObject<TMSWOBSpawnEggItem> BEAN_SPAWN_EGG = ITEMS.register("bean_spawn_egg", () ->
            new TMSWOBSpawnEggItem(TMSWOBEntityTypes.BEAN, 0x4ffc70, 0x1a1a1b,
                    new Item.Properties().tab(TMSWOBItemGroup.TMSWOB_GROUP)
            )
    );
}
