package net.sinny.journeyreforged.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.sinny.journeyreforged.block.ModBlocks;
import net.sinny.journeyreforged.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter recipeExporter) {

        offer2x2CompactingRecipe(recipeExporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.PEARL_BLOCK, ModItems.PEARL);
        offerShapelessRecipe(recipeExporter, ModItems.PEARL, ModBlocks.PEARL_BLOCK, "pearl", 4);


        offerCompactingRecipe(recipeExporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.PRISMARINE_ALLOY_BLOCK, ModItems.PRISMARINE_INGOT);
        offerShapelessRecipe(recipeExporter, ModItems.PRISMARINE_INGOT, ModBlocks.PRISMARINE_ALLOY_BLOCK, "prismarine_ingot", 9);

        offerCompactingRecipe(recipeExporter, RecipeCategory.MISC, ModItems.PRISMARINE_INGOT, ModItems.PRISMARINE_NUGGET, "prismarine_ingot_from_nugget");

        offerReversibleCompactingRecipes(recipeExporter, RecipeCategory.MISC, ModItems.PRISMARINE_NUGGET, RecipeCategory.MISC, ModItems.PRISMARINE_INGOT, "prismarine_ingot_from_nugget", "prismarine_ingot", "prismarine_nugget_from_ingot", "prismarine_nugget");
    }
}
