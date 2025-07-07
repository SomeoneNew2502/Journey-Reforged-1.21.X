package net.sinny.journeyreforged.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.sinny.journeyreforged.block.ModBlock;
import net.sinny.journeyreforged.item.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlock.PEARL.getBlock());
        blockStateModelGenerator.registerSimpleCubeAll(ModBlock.PRISMARINE_ALLOY.getBlock());
        blockStateModelGenerator.registerWoolAndCarpet(ModBlock.WARPED_WEAVE.getBlock(), ModBlock.WARPED_WEAVE_CARPET.getBlock());
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.PEARL, Models.GENERATED);
        itemModelGenerator.register(ModItems.PRISMARINE_NUGGET, Models.GENERATED);
        itemModelGenerator.register(ModItems.PRISMARINE_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.WARPED_THREAD, Models.GENERATED);
        itemModelGenerator.register(ModItems.ELDER_GUARDIAN_SCALE, Models.GENERATED);
    }
}
