package net.sinny.journeyreforged.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.sinny.journeyreforged.JourneyReforged;
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

        offerReversibleCompactingRecipes(recipeExporter, RecipeCategory.MISC, ModItems.PRISMARINE_NUGGET, RecipeCategory.MISC, ModItems.PRISMARINE_INGOT, "prismarine_ingot_from_nugget", "prismarine_ingot", "prismarine_nugget_from_ingot", "prismarine_nugget");

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.PRISMARINE_INGOT)
                .pattern("CCC")
                .pattern("CSS")
                .pattern("SS ")
                .input('C', Items.COPPER_INGOT)
                .input('S', ModItems.ELDER_GUARDIAN_SCALE)
                .group("prismarine_ingot")
                .criterion(hasItem(ModItems.PRISMARINE_INGOT), conditionsFromItem(ModItems.PRISMARINE_INGOT))
                .criterion(hasItem(ModItems.ELDER_GUARDIAN_SCALE), conditionsFromItem(ModItems.ELDER_GUARDIAN_SCALE))
                .offerTo(recipeExporter, Identifier.of(JourneyReforged.MOD_ID, "prismarine_ingot_from_scales"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.WARPED_WEAVE_CARPET)
                .pattern("WW ")
                .pattern("   ")
                .pattern("   ")
                .input('W', ModBlocks.WARPED_WEAVE_BLOCK)
                .criterion(hasItem(ModBlocks.WARPED_WEAVE_CARPET), conditionsFromItem(ModBlocks.WARPED_WEAVE_CARPET))
                .criterion(hasItem(ModBlocks.WARPED_WEAVE_BLOCK), conditionsFromItem(ModBlocks.WARPED_WEAVE_BLOCK))
                .offerTo(recipeExporter);
    }
}