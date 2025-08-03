package net.sinny.journeyreforged.event.loot_tables.chests;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.EmptyEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetEnchantmentsLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.RegistryKeys;

public class MendingLoot {
    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((id, tableBuilder, source, registries) -> {
            String path = id.getValue().toString();
            int emptyWeight = -1;

            switch (path) {
                case "minecraft:chests/bastion_treasure":
                    emptyWeight = 3; // 1 in 20 chance
                    break;
                case "minecraft:chests/buried_treasure":
                    emptyWeight = 5; // 1 in 40 chance
                    break;
                case "minecraft:chests/end_city_treasure":
                    emptyWeight = 3; // 1 in 15 chance
                    break;
                case "minecraft:chests/stronghold_library":
                    emptyWeight = 7;  // 1 in 10 chance
                    break;
                case "minecraft:chests/woodland_mansion":
                    emptyWeight = 6; // 1 in 25 chance
                    break;
            }

            if (emptyWeight > -1) {
                var enchantmentRegistry = registries.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
                int finalEmptyWeight = emptyWeight;
                enchantmentRegistry.getOptional(Enchantments.MENDING).ifPresent(enchantmentEntry -> {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(1.0f))
                            .with(ItemEntry.builder(Items.BOOK)
                                    .apply(new SetEnchantmentsLootFunction.Builder()
                                            .enchantment(enchantmentEntry, ConstantLootNumberProvider.create(1.0f))
                                    ).weight(1)
                            )
                            .with(EmptyEntry.builder().weight(finalEmptyWeight));

                    tableBuilder.pool(poolBuilder);
                });
            }
        });
    }
}