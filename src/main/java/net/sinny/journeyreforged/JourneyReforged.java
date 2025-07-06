package net.sinny.journeyreforged;

import net.fabricmc.api.ModInitializer;

import net.sinny.journeyreforged.block.ModBlocks;
import net.sinny.journeyreforged.item.ModItemGroups;
import net.sinny.journeyreforged.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JourneyReforged implements ModInitializer {
	public static final String MOD_ID = "journey-reforged";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
	}
}