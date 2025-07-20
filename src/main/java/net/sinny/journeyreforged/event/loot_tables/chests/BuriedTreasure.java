package net.sinny.journeyreforged.event.loot_tables.chests;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;
import net.sinny.journeyreforged.registry.ItemRegistry;

public class BuriedTreasure {
    private static final Identifier BURIED_TREASURE = Identifier.of("minecraft", "chests/buried_treasure");

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((id, tableBuilder, source, registries) -> {
            if (BURIED_TREASURE.equals(id.getValue())) {
                tableBuilder.pool(
                        LootPool.builder()
                                .rolls(UniformLootNumberProvider.create(1, 2))
                                .with(ItemEntry.builder(ItemRegistry.PEARL).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 2)))                                )
                                .build()
                );
            }
        });
    }
}
