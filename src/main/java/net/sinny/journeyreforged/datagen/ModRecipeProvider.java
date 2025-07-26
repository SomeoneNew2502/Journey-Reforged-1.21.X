package net.sinny.journeyreforged.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.sinny.journeyreforged.JourneyReforged;
import net.sinny.journeyreforged.registry.BlockRegistry;
import net.sinny.journeyreforged.registry.ItemRegistry;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter recipeExporter) {

        offer2x2CompactingRecipe(recipeExporter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.PEARL.getBlock(), ItemRegistry.PEARL);
        offerShapelessRecipe(recipeExporter, ItemRegistry.PEARL, BlockRegistry.PEARL.getBlock(), "pearl", 4);

        offerShapelessRecipe(recipeExporter, Blocks.WARPED_PLANKS, BlockRegistry.DETHREADED_WARPED_STEM.getBlock(), "dethreaded_warped_stem", 4);
        offerShapelessRecipe(recipeExporter, Blocks.WARPED_PLANKS, BlockRegistry.DETHREADED_WARPED_HYPHAE.getBlock(), "dethreaded_warped_hyphae", 4);

        offer2x2CompactingRecipe(recipeExporter, RecipeCategory.DECORATIONS, BlockRegistry.WARPED_WEAVE.getBlock(), ItemRegistry.WARPED_THREAD);

        offerCompactingRecipe(recipeExporter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.PRISMARINE_ALLOY.getBlock(), ItemRegistry.PRISMARINE_INGOT);
        offerShapelessRecipe(recipeExporter, ItemRegistry.PRISMARINE_INGOT, BlockRegistry.PRISMARINE_ALLOY.getBlock(), "prismarine_ingot", 9);

        offerReversibleCompactingRecipes(recipeExporter, RecipeCategory.MISC, ItemRegistry.PRISMARINE_NUGGET, RecipeCategory.MISC, ItemRegistry.PRISMARINE_INGOT, "prismarine_ingot_from_nugget", "prismarine_ingot", "prismarine_nugget_from_ingot", "prismarine_nugget");

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ItemRegistry.PRISMARINE_INGOT)
                .pattern("CCC")
                .pattern("CSS")
                .pattern("SS ")
                .input('C', Items.COPPER_INGOT)
                .input('S', ItemRegistry.ELDER_GUARDIAN_SCALE)
                .group("prismarine_ingot")
                .criterion(hasItem(ItemRegistry.PRISMARINE_INGOT), conditionsFromItem(ItemRegistry.PRISMARINE_INGOT))
                .criterion(hasItem(ItemRegistry.ELDER_GUARDIAN_SCALE), conditionsFromItem(ItemRegistry.ELDER_GUARDIAN_SCALE))
                .offerTo(recipeExporter, Identifier.of(JourneyReforged.MOD_ID, "prismarine_ingot_from_scales"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, BlockRegistry.WARPED_WEAVE_CARPET.getBlock())
                .pattern("WW ")
                .pattern("   ")
                .pattern("   ")
                .input('W', BlockRegistry.WARPED_WEAVE.getBlock())
                .criterion(hasItem(BlockRegistry.WARPED_WEAVE_CARPET.getBlock()), conditionsFromItem(BlockRegistry.WARPED_WEAVE_CARPET.getBlock()))
                .criterion(hasItem(BlockRegistry.WARPED_WEAVE.getBlock()), conditionsFromItem(BlockRegistry.WARPED_WEAVE.getBlock()))
                .offerTo(recipeExporter);


        //Prismarine Sword + Tools
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ItemRegistry.PRISMARINE_SWORD)
                .pattern(" P ")
                .pattern(" P ")
                .pattern(" S ")
                .input('P', ItemRegistry.PRISMARINE_INGOT)
                .input('S', Items.STICK)
                .criterion(hasItem(ItemRegistry.PRISMARINE_SWORD), conditionsFromItem(ItemRegistry.PRISMARINE_SWORD))
                .criterion(hasItem(ItemRegistry.PRISMARINE_INGOT), conditionsFromItem(ItemRegistry.PRISMARINE_INGOT))
                .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ItemRegistry.PRISMARINE_PICKAXE)
                .pattern("PPP")
                .pattern(" S ")
                .pattern(" S ")
                .input('P', ItemRegistry.PRISMARINE_INGOT)
                .input('S', Items.STICK)
                .criterion(hasItem(ItemRegistry.PRISMARINE_PICKAXE), conditionsFromItem(ItemRegistry.PRISMARINE_PICKAXE))
                .criterion(hasItem(ItemRegistry.PRISMARINE_INGOT), conditionsFromItem(ItemRegistry.PRISMARINE_INGOT))
                .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ItemRegistry.PRISMARINE_AXE)
                .pattern(" PP")
                .pattern(" SP")
                .pattern(" S ")
                .input('P', ItemRegistry.PRISMARINE_INGOT)
                .input('S', Items.STICK)
                .criterion(hasItem(ItemRegistry.PRISMARINE_AXE), conditionsFromItem(ItemRegistry.PRISMARINE_AXE))
                .criterion(hasItem(ItemRegistry.PRISMARINE_INGOT), conditionsFromItem(ItemRegistry.PRISMARINE_INGOT))
                .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ItemRegistry.PRISMARINE_SHOVEL)
                .pattern(" P ")
                .pattern(" S ")
                .pattern(" S ")
                .input('P', ItemRegistry.PRISMARINE_INGOT)
                .input('S', Items.STICK)
                .criterion(hasItem(ItemRegistry.PRISMARINE_SHOVEL), conditionsFromItem(ItemRegistry.PRISMARINE_SHOVEL))
                .criterion(hasItem(ItemRegistry.PRISMARINE_INGOT), conditionsFromItem(ItemRegistry.PRISMARINE_INGOT))
                .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ItemRegistry.PRISMARINE_HOE)
                .pattern(" PP")
                .pattern(" S ")
                .pattern(" S ")
                .input('P', ItemRegistry.PRISMARINE_INGOT)
                .input('S', Items.STICK)
                .criterion(hasItem(ItemRegistry.PRISMARINE_HOE), conditionsFromItem(ItemRegistry.PRISMARINE_HOE))
                .criterion(hasItem(ItemRegistry.PRISMARINE_INGOT), conditionsFromItem(ItemRegistry.PRISMARINE_INGOT))
                .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.DETHREADED_WARPED_HYPHAE.getBlock(), 3)
                .pattern("SS ")
                .pattern("SS ")
                .pattern("   ")
                .input('S', BlockRegistry.DETHREADED_WARPED_STEM.getBlock())
                .criterion(hasItem(BlockRegistry.DETHREADED_WARPED_HYPHAE.getBlock()), conditionsFromItem(BlockRegistry.DETHREADED_WARPED_HYPHAE.getBlock()))
                .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.ENDER_PEARL, 1)
                .pattern(" S ")
                .pattern("SPS")
                .pattern(" S ")
                .input('P', ItemRegistry.PEARL)
                .input('S', ItemRegistry.WARPED_THREAD)
                .criterion(hasItem(ItemRegistry.PEARL), conditionsFromItem(ItemRegistry.PEARL))
                .criterion(hasItem(ItemRegistry.WARPED_THREAD), conditionsFromItem(ItemRegistry.WARPED_THREAD))
                .criterion(hasItem(Items.ENDER_PEARL), conditionsFromItem(Items.ENDER_PEARL))
                .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.CHAINMAIL_HELMET, 1)
                .pattern("CCC")
                .pattern("C C")
                .pattern("   ")
                .input('C', Items.CHAIN)
                .criterion(hasItem(Items.CHAIN), conditionsFromItem(Items.CHAIN))
                .criterion(hasItem(Items.CHAINMAIL_HELMET), conditionsFromItem(Items.CHAINMAIL_HELMET))
                .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.CHAINMAIL_CHESTPLATE, 1)
                .pattern("C C")
                .pattern("CCC")
                .pattern("CCC")
                .input('C', Items.CHAIN)
                .criterion(hasItem(Items.CHAIN), conditionsFromItem(Items.CHAIN))
                .criterion(hasItem(Items.CHAINMAIL_CHESTPLATE), conditionsFromItem(Items.CHAINMAIL_CHESTPLATE))
                .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.CHAINMAIL_LEGGINGS, 1)
                .pattern("CCC")
                .pattern("C C")
                .pattern("C C")
                .input('C', Items.CHAIN)
                .criterion(hasItem(Items.CHAIN), conditionsFromItem(Items.CHAIN))
                .criterion(hasItem(Items.CHAINMAIL_LEGGINGS), conditionsFromItem(Items.CHAINMAIL_LEGGINGS))
                .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.CHAINMAIL_BOOTS, 1)
                .pattern("   ")
                .pattern("C C")
                .pattern("C C")
                .input('C', Items.CHAIN)
                .criterion(hasItem(Items.CHAIN), conditionsFromItem(Items.CHAIN))
                .criterion(hasItem(Items.CHAINMAIL_BOOTS), conditionsFromItem(Items.CHAINMAIL_BOOTS))
                .offerTo(recipeExporter);
    }
}