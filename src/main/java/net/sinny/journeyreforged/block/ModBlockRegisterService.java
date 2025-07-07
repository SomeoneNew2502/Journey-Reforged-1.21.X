package net.sinny.journeyreforged.block;

import lombok.extern.slf4j.Slf4j;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sinny.journeyreforged.JourneyReforged;

@Slf4j
public final class ModBlockRegisterService {

    private static final ModBlockRegisterService instance = new ModBlockRegisterService();


    public static ModBlockRegisterService getInstance() {
        return instance;
    }


    public void registerModBlocks() {
        for (ModBlock modBlock : ModBlock.values()) {
            registerModBlock(modBlock);
        }
    }

    private static void registerModBlock(ModBlock modBlock) {
        String name = asBlockName(modBlock.name());

        Registry.register(Registries.BLOCK, Identifier.of(JourneyReforged.MOD_ID, name), modBlock.getBlock());
        Registry.register(Registries.ITEM, Identifier.of(JourneyReforged.MOD_ID, name), modBlock.getBlockItem());

        log.info("Registering Mod Block {} for {}", name, JourneyReforged.MOD_ID);
    }


    private static String asBlockName(String name) {
        return name.toLowerCase() + "_block";
    }
}
