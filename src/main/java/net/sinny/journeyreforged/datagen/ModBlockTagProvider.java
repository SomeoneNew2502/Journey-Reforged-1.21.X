package net.sinny.journeyreforged.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.sinny.journeyreforged.block.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
                .add(Blocks.OAK_LEAVES)
                .add(Blocks.SPRUCE_LEAVES)
                .add(Blocks.BIRCH_LEAVES)
                .add(Blocks.JUNGLE_LEAVES)
                .add(Blocks.ACACIA_LEAVES)
                .add(Blocks.DARK_OAK_LEAVES)
                .add(Blocks.MANGROVE_LEAVES)
                .add(Blocks.AZALEA_LEAVES)
                .add(Blocks.FLOWERING_AZALEA_LEAVES)
                .add(Blocks.CHERRY_LEAVES);

        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.PEARL_BLOCK)
                .add(ModBlocks.PRISMARINE_ALLOY_BLOCK);

        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.PEARL_BLOCK);

        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.PRISMARINE_ALLOY_BLOCK);

        getOrCreateTagBuilder(BlockTags.BEACON_BASE_BLOCKS)
                .add(ModBlocks.PRISMARINE_ALLOY_BLOCK);

        getOrCreateTagBuilder(BlockTags.DAMPENS_VIBRATIONS)
                .add(ModBlocks.WARPED_WEAVE_BLOCK);

        getOrCreateTagBuilder(BlockTags.OCCLUDES_VIBRATION_SIGNALS)
                .add(ModBlocks.WARPED_WEAVE_BLOCK);

        getOrCreateTagBuilder(BlockTags.WOOL_CARPETS)
                .add(ModBlocks.WARPED_WEAVE_CARPET);
    }
}
