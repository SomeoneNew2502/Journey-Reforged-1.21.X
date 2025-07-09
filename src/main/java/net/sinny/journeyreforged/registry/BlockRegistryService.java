package net.sinny.journeyreforged.registry;

import lombok.extern.slf4j.Slf4j;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sinny.journeyreforged.JourneyReforged;

@Slf4j
public class BlockRegistryService {

    private static final BlockRegistryService instance = new BlockRegistryService();


    public static BlockRegistryService getInstance() {
        return instance;
    }


    public void registerModBlocks() {
        for (BlockRegistry blockRegistry : BlockRegistry.values()) {
            registerModBlock(blockRegistry);
        }
    }

    private void registerModBlock(BlockRegistry blockRegistry) {
        String name = asBlockName(blockRegistry.name());

        Registry.register(Registries.BLOCK, Identifier.of(JourneyReforged.MOD_ID, name), blockRegistry.getBlock());
        Registry.register(Registries.ITEM, Identifier.of(JourneyReforged.MOD_ID, name), blockRegistry.getBlockItem());

        log.info("Registering Mod Block {} for {}", name, JourneyReforged.MOD_ID);
    }


    private static String asBlockName(String name) {
        return name.toLowerCase() + "_block";
    }
}
