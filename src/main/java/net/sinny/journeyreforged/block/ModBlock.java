package net.sinny.journeyreforged.block;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.sinny.journeyreforged.block.custom.WarpedWeaveBlock;
import net.sinny.journeyreforged.block.custom.WarpedWeaveCarpet;

import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
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

    WARPED_WEAVE_CARPET(
            () -> new WarpedWeaveCarpet(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_AQUA)
                    .hardness(0.1f)
                    .resistance(0.1f)
                    .sounds(BlockSoundGroup.WOOL)),
            (block) -> new BlockItem(block, new Item.Settings())
    ),
    ;


    private final Block block;
    private final BlockItem blockItem;


    ModBlock(Supplier<Block> blockCreator, Function<Block, BlockItem> blockItemCreator) {
        this.block = blockCreator.get();
        this.blockItem = blockItemCreator.apply(block);
    }
}
