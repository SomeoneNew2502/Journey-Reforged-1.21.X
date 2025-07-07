package net.sinny.journeyreforged.block;

import lombok.Getter;
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

import java.util.function.Function;
import java.util.function.Supplier;

@Getter
public enum ModBlock {

    PEARL(
            () -> new Block(AbstractBlock.Settings.create()
                    .mapColor(MapColor.WHITE)
                    .hardness(1.5f)
                    .resistance(6.0f)
                    .sounds(BlockSoundGroup.STONE)
                    .requiresTool()),
            (block) -> new BlockItem(block, new Item.Settings()
                    .maxCount(16))
    ),

    PRISMARINE_ALLOY(
            () -> new Block(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_AQUA)
                    .hardness(5.0f)
                    .resistance(6.0f)
                    .sounds(BlockSoundGroup.NETHERITE)
                    .requiresTool()),
            (block) -> new BlockItem(block, new Item.Settings())
    ),

    WARPED_WEAVE(
            () -> new WarpedWeaveBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_AQUA)
                    .hardness(0.8f)
                    .resistance(0.8f)
                    .sounds(BlockSoundGroup.WOOL)
                    .burnable()),
            (block) -> new BlockItem(block, new Item.Settings())
    ),

    /*WARPED_WEAVE_CARPET(
            () -> new WarpedWeaveCarpet(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_AQUA)
                    .hardness(0.1f)
                    .resistance(0.1f)
                    .sounds(BlockSoundGroup.WOOL)),
            (block) -> new BlockItem(block, new Item.Settings())
    ),*/
    ;


    private final Block block;
    private final BlockItem blockItem;


    ModBlock(Supplier<Block> blockCreator, Function<Block, BlockItem> blockItemCreator) {
        String name = asBlockName(name());

        this.block = registerBlock(name, blockCreator.get());
        this.blockItem = registerBlockItem(name, blockItemCreator.apply(block));
    }


    private static Block registerBlock(String name, Block block) {
        return Registry.register(Registries.BLOCK, Identifier.of(JourneyReforged.MOD_ID, name), block);
    }

    private static BlockItem registerBlockItem(String name, BlockItem blockItem) {
        return Registry.register(Registries.ITEM, Identifier.of(JourneyReforged.MOD_ID, name), blockItem);
    }


    private static String asBlockName(String name) {
        return name.toLowerCase() + "_block";
    }
}
