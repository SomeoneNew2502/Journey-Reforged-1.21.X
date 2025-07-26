package net.sinny.journeyreforged;

import net.fabricmc.api.ModInitializer;
import net.sinny.journeyreforged.event.block.ShearBlockEventHandler;
import net.sinny.journeyreforged.registry.*;

public class JourneyReforged implements ModInitializer {

	public static final String MOD_ID = "journey-reforged";


	@Override
	public void onInitialize() {
		EntityRegistry.registerModEntities();

		ItemGroupRegistry.registerItemGroups();

		ItemRegistry.registerModItems();
		BlockRegistryService.getInstance().registerModBlocks();

		LootTableRegistry.init();

		ShearBlockEventHandler.registerEvents();
	}
}