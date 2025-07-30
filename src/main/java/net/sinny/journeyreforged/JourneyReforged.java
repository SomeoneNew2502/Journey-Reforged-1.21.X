package net.sinny.journeyreforged;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.sinny.journeyreforged.debug.xp.ExperienceOrbData;
import net.sinny.journeyreforged.debug.xp.XpTrackingManager;
import net.sinny.journeyreforged.event.block.ShearBlockEventHandler;
import net.sinny.journeyreforged.registry.*;
import net.sinny.journeyreforged.villager.ModVillagerTrades;

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

		ModVillagerTrades.removeVillagerTrades();

		ExperienceOrbData.initialize();
		registerXpTrackerEvents();
	}

	private void registerXpTrackerEvents() {
		// When a server is about to start, reset any lingering data.
		ServerLifecycleEvents.SERVER_STARTING.register(server -> {
			XpTrackingManager.INSTANCE.resetData();
		});

		// When a server is stopping, save all collected data to a file.
		ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
			XpTrackingManager.INSTANCE.saveDataToFile();
		});
	}
}