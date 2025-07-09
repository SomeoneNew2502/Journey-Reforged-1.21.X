package net.sinny.journeyreforged.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;
import net.sinny.journeyreforged.registry.BlockRegistry;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(BlockRegistry.PEARL.getBlock());
        addDrop(BlockRegistry.PRISMARINE_ALLOY.getBlock());
        addDrop(BlockRegistry.WARPED_WEAVE.getBlock());
        addDrop(BlockRegistry.WARPED_WEAVE_CARPET.getBlock());
    }
}
