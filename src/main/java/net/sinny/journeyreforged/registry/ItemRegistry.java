package net.sinny.journeyreforged.registry;

import lombok.extern.slf4j.Slf4j;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sinny.journeyreforged.JourneyReforged;
import net.sinny.journeyreforged.item.ToolMaterials;

@Slf4j
public class ItemRegistry {

    public static final Item PEARL = registerItem("pearl", new Item(new Item.Settings()));
    public static final Item WARPED_THREAD = registerItem("warped_thread", new Item(new Item.Settings()));
    public static final Item ELDER_GUARDIAN_SCALE = registerItem("elder_guardian_scale", new Item(new Item.Settings()));
    public static final Item PRISMARINE_NUGGET = registerItem("prismarine_nugget", new Item(new Item.Settings()));
    public static final Item PRISMARINE_INGOT = registerItem("prismarine_ingot", new Item(new Item.Settings()));


    public static final Item PRISMARINE_SWORD = registerItem("prismarine_sword",
            new SwordItem(ToolMaterials.PRISMARINE, new Item.Settings()
                    .attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.PRISMARINE, 3, -2.4f))));

    public static final Item PRISMARINE_PICKAXE = registerItem("prismarine_pickaxe",
            new PickaxeItem(ToolMaterials.PRISMARINE, new Item.Settings()
                    .attributeModifiers(PickaxeItem.createAttributeModifiers(ToolMaterials.PRISMARINE, 1, -2.8f))));

    public static final Item PRISMARINE_AXE = registerItem("prismarine_axe",
            new AxeItem(ToolMaterials.PRISMARINE, new Item.Settings()
                    .attributeModifiers(AxeItem.createAttributeModifiers(ToolMaterials.PRISMARINE, 5, -3.0f))));

    public static final Item PRISMARINE_SHOVEL = registerItem("prismarine_shovel",
            new ShovelItem(ToolMaterials.PRISMARINE, new Item.Settings()
                    .attributeModifiers(ShovelItem.createAttributeModifiers(ToolMaterials.PRISMARINE, 1.5f, -3.0f))));

    public static final Item PRISMARINE_HOE = registerItem("prismarine_hoe",
            new HoeItem(ToolMaterials.PRISMARINE, new Item.Settings()
                    .attributeModifiers(HoeItem.createAttributeModifiers(ToolMaterials.PRISMARINE, -5, 0.0f))));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(JourneyReforged.MOD_ID, name), item);
    }

    public static void registerModItems() {
        log.info("Registering Mod Items for " + JourneyReforged.MOD_ID);
    }
}