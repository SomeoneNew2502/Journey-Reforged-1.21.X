package net.sinny.journeyreforged.registry;

import net.sinny.journeyreforged.event.loot_tables.entity.ElderGuardian;

public class LootTableRegistry {

    public static void init() {

        ElderGuardian.modifyLootTables();
    }
}
