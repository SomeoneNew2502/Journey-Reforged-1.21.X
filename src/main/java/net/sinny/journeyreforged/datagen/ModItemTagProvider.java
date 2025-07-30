package net.sinny.journeyreforged.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
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

    Item[] stoneToolMaterials = new Item[]{
            Items.TERRACOTTA, Items.WHITE_TERRACOTTA, Items.ORANGE_TERRACOTTA, Items.MAGENTA_TERRACOTTA,
            Items.LIGHT_BLUE_TERRACOTTA, Items.YELLOW_TERRACOTTA, Items.LIME_TERRACOTTA, Items.PINK_TERRACOTTA,
            Items.GRAY_TERRACOTTA, Items.LIGHT_GRAY_TERRACOTTA, Items.CYAN_TERRACOTTA, Items.PURPLE_TERRACOTTA,
            Items.BLUE_TERRACOTTA, Items.BROWN_TERRACOTTA, Items.GREEN_TERRACOTTA, Items.RED_TERRACOTTA,
            Items.BLACK_TERRACOTTA, Items.SANDSTONE, Items.BASALT, Items.CALCITE
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

        getOrCreateTagBuilder(ItemTags.STONE_TOOL_MATERIALS)
                .add(stoneToolMaterials);
    }
}