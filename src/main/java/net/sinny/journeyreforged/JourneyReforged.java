package net.sinny.journeyreforged;

import net.fabricmc.api.ModInitializer;
import net.sinny.journeyreforged.item.ModItemGroups;
import net.sinny.journeyreforged.item.ModItems;

public class JourneyReforged implements ModInitializer {

	public static final String MOD_ID = "journey-reforged";


	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();

		ModItems.registerModItems();
	}
}