package net.sinny.journeyreforged.datagen;

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
        entries.add(ModEnchantments.REPOSSESSION_KEY, Enchantment.builder(
                Enchantment.definition(
                        registries.getWrapperOrThrow(RegistryKeys.ITEM).getOrThrow(ModEnchantments.REPOSSESSION_ENCHANTABLE),
                        1,
                        3,
                        Enchantment.leveledCost(15, 9), // Min cost
                        Enchantment.leveledCost(65, 9), // Max cost
                        4,
                        AttributeModifierSlot.MAINHAND, AttributeModifierSlot.OFFHAND
                )
        ).build(ModEnchantments.REPOSSESSION_ID));
    }

    public static void bootstrap(Registerable<Enchantment> registry) {
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