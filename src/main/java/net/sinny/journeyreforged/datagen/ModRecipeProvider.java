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
import net.sinny.journeyreforged.block.ModBlock;
import net.sinny.journeyreforged.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter recipeExporter) {

        offer2x2CompactingRecipe(recipeExporter, RecipeCategory.BUILDING_BLOCKS, ModBlock.PEARL.getBlock(), ModItems.PEARL);
        offerShapelessRecipe(recipeExporter, ModItems.PEARL, ModBlock.PEARL.getBlock(), "pearl", 4);


        offerCompactingRecipe(recipeExporter, RecipeCategory.BUILDING_BLOCKS, ModBlock.PRISMARINE_ALLOY.getBlock(), ModItems.PRISMARINE_INGOT);
        offerShapelessRecipe(recipeExporter, ModItems.PRISMARINE_INGOT, ModBlock.PRISMARINE_ALLOY.getBlock(), "prismarine_ingot", 9);

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

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlock.WARPED_WEAVE_CARPET.getBlock())
                .pattern("WW ")
                .pattern("   ")
                .pattern("   ")
                .input('W', ModBlock.WARPED_WEAVE.getBlock())
                .criterion(hasItem(ModBlock.WARPED_WEAVE_CARPET.getBlock()), conditionsFromItem(ModBlock.WARPED_WEAVE_CARPET.getBlock()))
                .criterion(hasItem(ModBlock.WARPED_WEAVE.getBlock()), conditionsFromItem(ModBlock.WARPED_WEAVE.getBlock()))
                .offerTo(recipeExporter);
    }
}