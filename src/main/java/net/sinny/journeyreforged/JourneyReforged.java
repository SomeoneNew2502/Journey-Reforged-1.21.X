package net.sinny.journeyreforged;

import net.fabricmc.api.ModInitializer;
import net.sinny.journeyreforged.registry.BlockRegistryService;
import net.sinny.journeyreforged.registry.ItemGroupRegistry;
import net.sinny.journeyreforged.registry.ItemRegistry;
import net.sinny.journeyreforged.registry.LootTableRegistry;

public class JourneyReforged implements ModInitializer {

	public static final String MOD_ID = "journey-reforged";


	@Override
	public void onInitialize() {
		ItemGroupRegistry.registerItemGroups();

		ItemRegistry.registerModItems();
		BlockRegistryService.getInstance().registerModBlocks();

		LootTableRegistry.init();
	}
}