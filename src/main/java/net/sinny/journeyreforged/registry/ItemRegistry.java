package net.sinny.journeyreforged.registry;

import lombok.extern.slf4j.Slf4j;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.sinny.journeyreforged.JourneyReforged;
import net.sinny.journeyreforged.item.DaggerItem;
import net.sinny.journeyreforged.item.JRArmorMaterials;
import net.sinny.journeyreforged.item.JRToolMaterials;
import net.sinny.journeyreforged.item.PrismarineArmorItem;

import java.util.List;

@Slf4j
public class ItemRegistry {

    private static final int PRISMARINE_DURABILITY_MULTIPLIER = 35; // Diamond is 33, Netherite is 37

    private static List<Identifier> getPrismarineUpgradeBaseSlotTextures() {
        // CORRECTED PATHS: The textures are in the "item" directory, not "smithing"
        return List.of(
                Identifier.ofVanilla("item/empty_slot_sword"),
                Identifier.ofVanilla("item/empty_slot_pickaxe"),
                Identifier.ofVanilla("item/empty_slot_axe"),
                Identifier.ofVanilla("item/empty_slot_shovel"),
                Identifier.ofVanilla("item/empty_slot_hoe"),
                Identifier.ofVanilla("item/empty_slot_helmet"),
                Identifier.ofVanilla("item/empty_slot_chestplate"),
                Identifier.ofVanilla("item/empty_slot_leggings"),
                Identifier.ofVanilla("item/empty_slot_boots")
        );
    }

    private static List<Identifier> getPrismarineUpgradeAdditionSlotTextures() {
        // CORRECTED PATHS: The textures are in the "item" directory, not "smithing"
        return List.of(Identifier.ofVanilla("item/empty_slot_ingot"));
    }

    // Tooltip: "Applies to:"
    private static final Text PRISMARINE_UPGRADE_APPLIES_TO_TEXT = Text.translatable(
            "upgrade.journey-reforged.prismarine_upgrade.applies_to").formatted(Formatting.BLUE);

    // Tooltip: "Ingredients:"
    private static final Text PRISMARINE_UPGRADE_INGREDIENTS_TEXT = Text.translatable(
            "upgrade.journey-reforged.prismarine_upgrade.ingredients").formatted(Formatting.BLUE);

    // Title in Smithing Table UI
    private static final Text PRISMARINE_UPGRADE_TEXT = Text.translatable(
            "upgrade.journey-reforged.prismarine_upgrade");

    // NEW FOR 1.21: Hover text for the base item slot
    private static final Text PRISMARINE_UPGRADE_BASE_SLOT_DESCRIPTION_TEXT = Text.translatable(
            "item.journey-reforged.smithing_template.prismarine_upgrade.base_slot_description");

    // NEW FOR 1.21: Hover text for the additions slot
    private static final Text PRISMARINE_UPGRADE_ADDITIONS_SLOT_DESCRIPTION_TEXT = Text.translatable(
            "item.journey-reforged.smithing_template.prismarine_upgrade.additions_slot_description");


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
                    .attributeModifiers(AxeItem.createAttributeModifiers(JRToolMaterials.PRISMARINE, 3, -3.1f))));

    public static final Item PRISMARINE_SHOVEL = registerItem("prismarine_shovel",
            new ShovelItem(JRToolMaterials.PRISMARINE, new Item.Settings()
                    .attributeModifiers(ShovelItem.createAttributeModifiers(JRToolMaterials.PRISMARINE, 1.5f, -3.0f))));

    public static final Item PRISMARINE_HOE = registerItem("prismarine_hoe",
            new HoeItem(JRToolMaterials.PRISMARINE, new Item.Settings()
                    .attributeModifiers(HoeItem.createAttributeModifiers(JRToolMaterials.PRISMARINE, -5, 0.0f))));


    public static final Item PRISMARINE_HELMET = registerItem("prismarine_helmet",
            new PrismarineArmorItem(JRArmorMaterials.PRISMARINE, ArmorItem.Type.HELMET, new Item.Settings() // Use new class
                    .maxDamage(ArmorItem.Type.HELMET.getMaxDamage(PRISMARINE_DURABILITY_MULTIPLIER))));

    public static final Item PRISMARINE_CHESTPLATE = registerItem("prismarine_chestplate",
            new PrismarineArmorItem(JRArmorMaterials.PRISMARINE, ArmorItem.Type.CHESTPLATE, new Item.Settings() // Use new class
                    .maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(PRISMARINE_DURABILITY_MULTIPLIER))));

    public static final Item PRISMARINE_LEGGINGS = registerItem("prismarine_leggings",
            new PrismarineArmorItem(JRArmorMaterials.PRISMARINE, ArmorItem.Type.LEGGINGS, new Item.Settings() // Use new class
                    .maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(PRISMARINE_DURABILITY_MULTIPLIER))));

    public static final Item PRISMARINE_BOOTS = registerItem("prismarine_boots",
            new PrismarineArmorItem(JRArmorMaterials.PRISMARINE, ArmorItem.Type.BOOTS, new Item.Settings() // Use new class
                    .maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(PRISMARINE_DURABILITY_MULTIPLIER))));


    public static final Item WOODEN_DAGGER = registerItem("wooden_dagger",
            new DaggerItem(ToolMaterials.WOOD, new Item.Settings()
                    .attributeModifiers(DaggerItem.createDaggerAttributes(ToolMaterials.WOOD, 1.0F, -2.0f))));

    public static final Item STONE_DAGGER = registerItem("stone_dagger",
            new DaggerItem(ToolMaterials.STONE, new Item.Settings()
                    .attributeModifiers(DaggerItem.createDaggerAttributes(ToolMaterials.STONE, 0.5F, -2.0f))));

    public static final Item IRON_DAGGER = registerItem("iron_dagger",
            new DaggerItem(ToolMaterials.IRON, new Item.Settings()
                    .attributeModifiers(DaggerItem.createDaggerAttributes(ToolMaterials.IRON, 0.5F, -2.0f))));

    public static final Item GOLDEN_DAGGER = registerItem("golden_dagger",
            new DaggerItem(ToolMaterials.GOLD, new Item.Settings()
                    .attributeModifiers(DaggerItem.createDaggerAttributes(ToolMaterials.GOLD, 1.5F, -2.0f))));

    public static final Item DIAMOND_DAGGER = registerItem("diamond_dagger",
            new DaggerItem(ToolMaterials.DIAMOND, new Item.Settings()
                    .attributeModifiers(DaggerItem.createDaggerAttributes(ToolMaterials.DIAMOND, 0.0F, -2.0f))));

    public static final Item NETHERITE_DAGGER = registerItem("netherite_dagger",
            new DaggerItem(ToolMaterials.NETHERITE, new Item.Settings()
                    .attributeModifiers(DaggerItem.createDaggerAttributes(ToolMaterials.NETHERITE, 0.0F, -2.0f))));

    public static final Item PRISMARINE_DAGGER = registerItem("prismarine_dagger",
            new DaggerItem(JRToolMaterials.PRISMARINE, new Item.Settings()
                    .attributeModifiers(DaggerItem.createDaggerAttributes(JRToolMaterials.PRISMARINE, -0.5F, -2.0f))));


    public static final Item PRISMARINE_UPGRADE_SMITHING_TEMPLATE = registerItem("prismarine_upgrade_smithing_template",
            new SmithingTemplateItem(
                    PRISMARINE_UPGRADE_APPLIES_TO_TEXT,
                    PRISMARINE_UPGRADE_INGREDIENTS_TEXT,
                    PRISMARINE_UPGRADE_TEXT,
                    PRISMARINE_UPGRADE_BASE_SLOT_DESCRIPTION_TEXT,
                    PRISMARINE_UPGRADE_ADDITIONS_SLOT_DESCRIPTION_TEXT,
                    getPrismarineUpgradeBaseSlotTextures(),
                    getPrismarineUpgradeAdditionSlotTextures()
            ));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(JourneyReforged.MOD_ID, name), item);
    }

    public static void registerModItems() {
        log.info("Registering Mod Items for " + JourneyReforged.MOD_ID);
    }
}