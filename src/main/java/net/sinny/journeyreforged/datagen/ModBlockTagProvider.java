package net.sinny.journeyreforged.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.sinny.journeyreforged.block.ModBlock;

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
                .add(ModBlock.PEARL.getBlock())
                .add(ModBlock.PRISMARINE_ALLOY.getBlock());

        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlock.PEARL.getBlock());

        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlock.PRISMARINE_ALLOY.getBlock());

        getOrCreateTagBuilder(BlockTags.BEACON_BASE_BLOCKS)
                .add(ModBlock.PRISMARINE_ALLOY.getBlock());

        getOrCreateTagBuilder(BlockTags.DAMPENS_VIBRATIONS)
                .add(ModBlock.WARPED_WEAVE.getBlock());

        getOrCreateTagBuilder(BlockTags.OCCLUDES_VIBRATION_SIGNALS)
                .add(ModBlock.WARPED_WEAVE.getBlock());

        getOrCreateTagBuilder(BlockTags.WOOL_CARPETS)
                .add(ModBlock.WARPED_WEAVE_CARPET.getBlock());
    }
}
