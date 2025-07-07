package net.sinny.journeyreforged.block;

import lombok.extern.slf4j.Slf4j;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.sinny.journeyreforged.JourneyReforged;
import net.sinny.journeyreforged.block.custom.WarpedWeaveBlock;
import net.sinny.journeyreforged.block.custom.WarpedWeaveCarpet;

@Slf4j
public final class ModBlocks {

    public static final Block PEARL_BLOCK = registerblock("pearl_block",
            new Block(AbstractBlock.Settings.create()
                    .mapColor(MapColor.WHITE)
                    .hardness(1.5f)
                    .resistance(6.0f)
                    .sounds(BlockSoundGroup.STONE)
                    .requiresTool()));

    public static final Block PRISMARINE_ALLOY_BLOCK = registerblock("prismarine_alloy_block",
            new Block(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_AQUA)
                    .hardness(5.0f)
                    .resistance(6.0f)
                    .sounds(BlockSoundGroup.NETHERITE)
                    .requiresTool()));

    public static final Block WARPED_WEAVE_BLOCK = registerblock("warped_weave_block",
            new WarpedWeaveBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_AQUA)
                    .hardness(0.8f)
                    .resistance(0.8f)
                    .sounds(BlockSoundGroup.WOOL)));

    public static final Block WARPED_WEAVE_CARPET = registerblock("warped_weave_carpet",
            new WarpedWeaveCarpet(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_AQUA)
                    .hardness(0.1f)
                    .resistance(0.1f)
                    .sounds(BlockSoundGroup.WOOL)));


    //todo fix implementation or rename method
    public static void registerModBlocks() {
        log.info("Registering Mod Blocks for {}", JourneyReforged.MOD_ID);
    }


    private static Block registerblock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(JourneyReforged.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(JourneyReforged.MOD_ID, name),
                new BlockItem(block, new Item.Settings().maxCount(16)));
    }
}
