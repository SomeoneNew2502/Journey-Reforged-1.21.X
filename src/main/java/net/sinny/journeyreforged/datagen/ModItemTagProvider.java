package net.sinny.journeyreforged.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.sinny.journeyreforged.registry.ItemRegistry;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ItemTags.BEACON_PAYMENT_ITEMS)
                .add(ItemRegistry.PRISMARINE_INGOT);

        getOrCreateTagBuilder(ItemTags.SWORDS)
                .add(ItemRegistry.PRISMARINE_SWORD);

        getOrCreateTagBuilder(ItemTags.PICKAXES)
                .add(ItemRegistry.PRISMARINE_PICKAXE);

        getOrCreateTagBuilder(ItemTags.AXES)
                .add(ItemRegistry.PRISMARINE_AXE);

        getOrCreateTagBuilder(ItemTags.SHOVELS)
                .add(ItemRegistry.PRISMARINE_SHOVEL);

        getOrCreateTagBuilder(ItemTags.HOES)
                .add(ItemRegistry.PRISMARINE_HOE);
    }
}