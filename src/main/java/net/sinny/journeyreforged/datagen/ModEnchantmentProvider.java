package net.sinny.journeyreforged.datagen;// package net.sinny.journeyreforged.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntryList;
import net.sinny.journeyreforged.registry.ModEnchantments;

import java.util.concurrent.CompletableFuture;

public class ModEnchantmentProvider extends FabricDynamicRegistryProvider {

    public ModEnchantmentProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        // The previous version had a line here declaring a variable of the wrong type.
        // It was unused, so it has been removed. The main logic is below.

        entries.add(ModEnchantments.REPOSSESSION_KEY, Enchantment.builder(
                Enchantment.definition(
                        // This line is correct. registries.getWrapperOrThrow() returns a
                        // RegistryWrapper.Impl<Item>, which has the getOrThrow(TagKey) method.
                        registries.getWrapperOrThrow(RegistryKeys.ITEM).getOrThrow(ModEnchantments.REPOSSESSION_ENCHANTABLE),
                        1, // Weight (Very Rare)
                        3, // Max Level
                        Enchantment.leveledCost(15, 9), // Min cost
                        Enchantment.leveledCost(65, 9), // Max cost
                        4, // Anvil cost
                        AttributeModifierSlot.MAINHAND, AttributeModifierSlot.OFFHAND
                )
        ).build(ModEnchantments.REPOSSESSION_ID));
    }

    // This bootstrap method remains correct.
    public static void bootstrap(Registerable<Enchantment> registry) {
        // Using an empty list as a placeholder here is the correct approach.
        registry.register(ModEnchantments.REPOSSESSION_KEY, Enchantment.builder(
                Enchantment.definition(
                        RegistryEntryList.of(),
                        1, 1,
                        Enchantment.constantCost(1), Enchantment.constantCost(1),
                        1, AttributeModifierSlot.MAINHAND
                )
        ).build(ModEnchantments.REPOSSESSION_ID));
    }

    @Override
    public String getName() {
        return "Enchantments";
    }
}