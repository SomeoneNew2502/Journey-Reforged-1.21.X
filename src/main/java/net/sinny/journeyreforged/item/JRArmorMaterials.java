package net.sinny.journeyreforged.item;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.sinny.journeyreforged.JourneyReforged;
import net.sinny.journeyreforged.registry.ItemRegistry;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class JRArmorMaterials {
    public static final RegistryEntry<ArmorMaterial> PRISMARINE = register(
            "prismarine",
            new EnumMap<>(ArmorItem.Type.class) {{
                put(ArmorItem.Type.BOOTS, 3);
                put(ArmorItem.Type.LEGGINGS, 4);
                put(ArmorItem.Type.CHESTPLATE, 5);
                put(ArmorItem.Type.HELMET, 3);
                put(ArmorItem.Type.BODY, 11);
            }},
            22,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND,
            () -> Ingredient.ofItems(ItemRegistry.PRISMARINE_INGOT),
            2.0F,
            0.00F
    );

    private static RegistryEntry<ArmorMaterial> register(
            String id,
            Map<ArmorItem.Type, Integer> defense,
            int enchantability,
            RegistryEntry<SoundEvent> equipSound,
            Supplier<Ingredient> repairIngredient,
            float toughness,
            float knockbackResistance
    ) {
        List<ArmorMaterial.Layer> layers = List.of(new ArmorMaterial.Layer(Identifier.of(JourneyReforged.MOD_ID, id)));

        var material = new ArmorMaterial(defense, enchantability, equipSound, repairIngredient, layers, toughness, knockbackResistance);

        return Registry.registerReference(Registries.ARMOR_MATERIAL, Identifier.of(JourneyReforged.MOD_ID, id), material);
    }
}