package net.sinny.journeyreforged.registry;

import lombok.extern.slf4j.Slf4j;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sinny.journeyreforged.JourneyReforged;
import net.sinny.journeyreforged.item.DaggerItem;
import net.sinny.journeyreforged.item.JRToolMaterials;

@Slf4j
public class ItemRegistry {

    public static final Item PEARL = registerItem("pearl", new Item(new Item.Settings()));
    public static final Item WARPED_THREAD = registerItem("warped_thread", new Item(new Item.Settings()));
    public static final Item ELDER_GUARDIAN_SCALE = registerItem("elder_guardian_scale", new Item(new Item.Settings()));
    public static final Item PRISMARINE_NUGGET = registerItem("prismarine_nugget", new Item(new Item.Settings()));
    public static final Item PRISMARINE_INGOT = registerItem("prismarine_ingot", new Item(new Item.Settings()));


    public static final Item PRISMARINE_SWORD = registerItem("prismarine_sword",
            new SwordItem(JRToolMaterials.PRISMARINE, new Item.Settings()
                    .attributeModifiers(SwordItem.createAttributeModifiers(JRToolMaterials.PRISMARINE, 3, -2.4f))));

    public static final Item PRISMARINE_PICKAXE = registerItem("prismarine_pickaxe",
            new PickaxeItem(JRToolMaterials.PRISMARINE, new Item.Settings()
                    .attributeModifiers(PickaxeItem.createAttributeModifiers(JRToolMaterials.PRISMARINE, 1, -2.8f))));

    public static final Item PRISMARINE_AXE = registerItem("prismarine_axe",
            new AxeItem(JRToolMaterials.PRISMARINE, new Item.Settings()
                    .attributeModifiers(AxeItem.createAttributeModifiers(JRToolMaterials.PRISMARINE, 5, -3.0f))));

    public static final Item PRISMARINE_SHOVEL = registerItem("prismarine_shovel",
            new ShovelItem(JRToolMaterials.PRISMARINE, new Item.Settings()
                    .attributeModifiers(ShovelItem.createAttributeModifiers(JRToolMaterials.PRISMARINE, 1.5f, -3.0f))));

    public static final Item PRISMARINE_HOE = registerItem("prismarine_hoe",
            new HoeItem(JRToolMaterials.PRISMARINE, new Item.Settings()
                    .attributeModifiers(HoeItem.createAttributeModifiers(JRToolMaterials.PRISMARINE, -5, 0.0f))));

    public static final Item WOODEN_DAGGER = registerItem("wooden_dagger",
            new DaggerItem(ToolMaterials.WOOD, new Item.Settings()
                    .attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.WOOD, 1, -2.0f))));

    public static final Item STONE_DAGGER = registerItem("stone_dagger",
            new DaggerItem(ToolMaterials.STONE, new Item.Settings()
                    .attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.STONE, 2, -2.0f))));

    public static final Item IRON_DAGGER = registerItem("iron_dagger",
            new DaggerItem(ToolMaterials.IRON, new Item.Settings()
                    .attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.IRON, 3, -2.0f))));

    public static final Item GOLDEN_DAGGER = registerItem("golden_dagger",
            new DaggerItem(ToolMaterials.GOLD, new Item.Settings()
                    .attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.GOLD, 1, -2.0f))));

    public static final Item DIAMOND_DAGGER = registerItem("diamond_dagger",
            new DaggerItem(ToolMaterials.DIAMOND, new Item.Settings()
                    .attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.DIAMOND, 4, -2.0f))));

    public static final Item NETHERITE_DAGGER = registerItem("netherite_dagger",
            new DaggerItem(ToolMaterials.NETHERITE, new Item.Settings()
                    .attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.NETHERITE, 5, -2.0f))));

    public static final Item PRISMARINE_DAGGER = registerItem("prismarine_dagger",
            new DaggerItem(JRToolMaterials.PRISMARINE, new Item.Settings()
                    .attributeModifiers(SwordItem.createAttributeModifiers(JRToolMaterials.PRISMARINE, 4, -2.0f))));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(JourneyReforged.MOD_ID, name), item);
    }

    public static void registerModItems() {
        log.info("Registering Mod Items for " + JourneyReforged.MOD_ID);
    }
}