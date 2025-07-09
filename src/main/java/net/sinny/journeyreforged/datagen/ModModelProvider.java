package net.sinny.journeyreforged.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.sinny.journeyreforged.registry.BlockRegistry;
import net.sinny.journeyreforged.registry.ItemRegistry;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(BlockRegistry.PEARL.getBlock());
        blockStateModelGenerator.registerSimpleCubeAll(BlockRegistry.PRISMARINE_ALLOY.getBlock());
        blockStateModelGenerator.registerWoolAndCarpet(BlockRegistry.WARPED_WEAVE.getBlock(), BlockRegistry.WARPED_WEAVE_CARPET.getBlock());
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ItemRegistry.PEARL, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.PRISMARINE_NUGGET, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.PRISMARINE_INGOT, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.WARPED_THREAD, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.ELDER_GUARDIAN_SCALE, Models.GENERATED);

        itemModelGenerator.register(ItemRegistry.PRISMARINE_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ItemRegistry.PRISMARINE_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ItemRegistry.PRISMARINE_AXE, Models.HANDHELD);
        itemModelGenerator.register(ItemRegistry.PRISMARINE_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ItemRegistry.PRISMARINE_HOE, Models.HANDHELD);
    }
}
