package net.sinny.journeyreforged.event.loot_tables.entity;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;
import net.sinny.journeyreforged.registry.ItemRegistry;

public class ElderGuardian {

    private static final Identifier ELDER_GUARDIAN =  Identifier.of("minecraft", "entities/elder_guardian");

    public static void modifyLootTables() {

        //Adds a new pool to the Elder Guardian loottable
        LootTableEvents.MODIFY.register((id, tableBuilder, source, registries) -> {
            if (ELDER_GUARDIAN.equals(id.getValue())) {
                tableBuilder.pool(LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ItemRegistry.ELDER_GUARDIAN_SCALE)).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(3.0f, 5.0f))).build());

                tableBuilder.pool(LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ItemRegistry.PRISMARINE_UPGRADE_SMITHING_TEMPLATE))
                        .build());
            }
        });
    }
}
