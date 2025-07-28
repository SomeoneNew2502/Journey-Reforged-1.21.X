package net.sinny.journeyreforged.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.sinny.journeyreforged.registry.ItemRegistry;
import net.sinny.journeyreforged.registry.ModEnchantments;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    Item[] daggers = new Item[]{
            ItemRegistry.WOODEN_DAGGER,
            ItemRegistry.STONE_DAGGER,
            ItemRegistry.GOLDEN_DAGGER,
            ItemRegistry.IRON_DAGGER,
            ItemRegistry.DIAMOND_DAGGER,
            ItemRegistry.NETHERITE_DAGGER,
            ItemRegistry.PRISMARINE_DAGGER
    };

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ItemTags.BEACON_PAYMENT_ITEMS)
                .add(ItemRegistry.PRISMARINE_INGOT);

        getOrCreateTagBuilder(ItemTags.SWORD_ENCHANTABLE)
                .add(daggers);

        getOrCreateTagBuilder(ItemTags.SHARP_WEAPON_ENCHANTABLE)
                .add(daggers);

        getOrCreateTagBuilder(ItemTags.DURABILITY_ENCHANTABLE)
                .add(daggers);

        getOrCreateTagBuilder(ItemTags.BOW_ENCHANTABLE)
                .add(daggers);

        getOrCreateTagBuilder(ModEnchantments.REPOSSESSION_ENCHANTABLE)
                .add(daggers);

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