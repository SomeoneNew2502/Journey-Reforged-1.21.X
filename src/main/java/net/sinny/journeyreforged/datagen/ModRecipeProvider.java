package net.sinny.journeyreforged.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.SmithingTransformRecipeJsonBuilder;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.sinny.journeyreforged.JourneyReforged;
import net.sinny.journeyreforged.item.DaggerItem;
import net.sinny.journeyreforged.registry.BlockRegistry;
import net.sinny.journeyreforged.registry.ItemRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
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

        offerCompactingRecipe(recipeExporter,    RecipeCategory.BUILDING_BLOCKS, BlockRegistry.PRISMARINE_ALLOY.getBlock(), ItemRegistry.PRISMARINE_INGOT);
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

        Map<Item, Item> prismarineUpgrades = new LinkedHashMap<>();
        prismarineUpgrades.put(Items.DIAMOND_SWORD, ItemRegistry.PRISMARINE_SWORD);
        prismarineUpgrades.put(Items.DIAMOND_PICKAXE, ItemRegistry.PRISMARINE_PICKAXE);
        prismarineUpgrades.put(Items.DIAMOND_AXE, ItemRegistry.PRISMARINE_AXE);
        prismarineUpgrades.put(Items.DIAMOND_SHOVEL, ItemRegistry.PRISMARINE_SHOVEL);
        prismarineUpgrades.put(Items.DIAMOND_HOE, ItemRegistry.PRISMARINE_HOE);
        prismarineUpgrades.put(Items.DIAMOND_HELMET, ItemRegistry.PRISMARINE_HELMET);
        prismarineUpgrades.put(Items.DIAMOND_CHESTPLATE, ItemRegistry.PRISMARINE_CHESTPLATE);
        prismarineUpgrades.put(Items.DIAMOND_LEGGINGS, ItemRegistry.PRISMARINE_LEGGINGS);
        prismarineUpgrades.put(Items.DIAMOND_BOOTS, ItemRegistry.PRISMARINE_BOOTS);
        prismarineUpgrades.put(ItemRegistry.DIAMOND_DAGGER, ItemRegistry.PRISMARINE_DAGGER);

        prismarineUpgrades.forEach((diamondTool, prismarineTool) -> {
            RecipeCategory category;
            if (prismarineTool instanceof SwordItem || prismarineTool instanceof DaggerItem || prismarineTool instanceof ArmorItem) {
                category = RecipeCategory.COMBAT;
            } else {
                category = RecipeCategory.TOOLS;
            }

            SmithingTransformRecipeJsonBuilder.create(
                            Ingredient.ofItems(ItemRegistry.PRISMARINE_UPGRADE_SMITHING_TEMPLATE),
                            Ingredient.ofItems(diamondTool),
                            Ingredient.ofItems(ItemRegistry.PRISMARINE_INGOT),
                            category,
                            prismarineTool)
                    .criterion(hasItem(diamondTool), conditionsFromItem(diamondTool))
                    .criterion(hasItem(ItemRegistry.PRISMARINE_INGOT), conditionsFromItem(ItemRegistry.PRISMARINE_INGOT))
                    .offerTo(recipeExporter, getItemPath(prismarineTool) + "_smithing");
        });

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

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ItemRegistry.WOODEN_DAGGER, 1)
                .pattern("   ")
                .pattern(" P ")
                .pattern(" S ")
                .input('P', ItemTags.PLANKS)
                .input('S', Items.STICK)
                .criterion(hasItem(ItemRegistry.WOODEN_DAGGER), conditionsFromItem(ItemRegistry.WOODEN_DAGGER))
                .criterion("has_planks", conditionsFromTag(ItemTags.PLANKS))
                .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ItemRegistry.STONE_DAGGER, 1)
                .pattern("   ")
                .pattern(" C ")
                .pattern(" S ")
                .input('C', Items.COBBLESTONE)
                .input('S', Items.STICK)
                .criterion(hasItem(ItemRegistry.STONE_DAGGER), conditionsFromItem(ItemRegistry.STONE_DAGGER))
                .criterion(hasItem(Items.COBBLESTONE), conditionsFromItem(Items.COBBLESTONE))
                .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ItemRegistry.IRON_DAGGER, 1)
                .pattern("   ")
                .pattern(" I ")
                .pattern(" S ")
                .input('I', Items.IRON_INGOT)
                .input('S', Items.STICK)
                .criterion(hasItem(ItemRegistry.IRON_DAGGER), conditionsFromItem(ItemRegistry.IRON_DAGGER))
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ItemRegistry.GOLDEN_DAGGER, 1)
                .pattern("   ")
                .pattern(" G ")
                .pattern(" S ")
                .input('G', Items.GOLD_INGOT)
                .input('S', Items.STICK)
                .criterion(hasItem(ItemRegistry.GOLDEN_DAGGER), conditionsFromItem(ItemRegistry.GOLDEN_DAGGER))
                .criterion(hasItem(Items.GOLD_INGOT), conditionsFromItem(Items.GOLD_INGOT))
                .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ItemRegistry.DIAMOND_DAGGER, 1)
                .pattern("   ")
                .pattern(" D ")
                .pattern(" S ")
                .input('D', Items.DIAMOND)
                .input('S', Items.STICK)
                .criterion(hasItem(ItemRegistry.DIAMOND_DAGGER), conditionsFromItem(ItemRegistry.DIAMOND_DAGGER))
                .criterion(hasItem(Items.DIAMOND), conditionsFromItem(Items.DIAMOND))
                .offerTo(recipeExporter);

        SmithingTransformRecipeJsonBuilder.create(
                        Ingredient.ofItems(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.ofItems(ItemRegistry.DIAMOND_DAGGER),
                        Ingredient.ofItems(Items.NETHERITE_INGOT),
                        RecipeCategory.COMBAT,
                        ItemRegistry.NETHERITE_DAGGER)
                .criterion(hasItem(ItemRegistry.NETHERITE_DAGGER), conditionsFromItem(ItemRegistry.NETHERITE_DAGGER))
                .criterion(hasItem(Items.NETHERITE_INGOT), conditionsFromItem(Items.NETHERITE_INGOT))
                .offerTo(recipeExporter, getItemPath(ItemRegistry.NETHERITE_DAGGER) + "_smithing");

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ItemRegistry.PRISMARINE_UPGRADE_SMITHING_TEMPLATE, 1)
                .pattern("PSP")
                .pattern("PMP")
                .pattern("PPP")
                .input('S', ItemRegistry.PRISMARINE_UPGRADE_SMITHING_TEMPLATE)
                .input('P', ItemRegistry.PRISMARINE_INGOT)
                .input('M', Items.PRISMARINE)
                .criterion(hasItem(ItemRegistry.PRISMARINE_UPGRADE_SMITHING_TEMPLATE), conditionsFromItem(ItemRegistry.PRISMARINE_UPGRADE_SMITHING_TEMPLATE))
                .criterion(hasItem(ItemRegistry.PRISMARINE_INGOT), conditionsFromItem(ItemRegistry.PRISMARINE_INGOT))
                .offerTo(recipeExporter);

        RecipeExporter vanillaExporter = new VanillaRecipeExporter(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.CHAIN, 2)
                .pattern("N")
                .pattern("I")
                .pattern("N")
                .input('N', Items.IRON_NUGGET)
                .input('I', Items.IRON_INGOT)
                .criterion(hasItem(Items.IRON_NUGGET), conditionsFromItem(Items.IRON_NUGGET))
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .offerTo(vanillaExporter, "chain_");
    }

    private static class VanillaRecipeExporter implements RecipeExporter {
        private final RecipeExporter originalExporter;

        private VanillaRecipeExporter(RecipeExporter originalExporter) {
            this.originalExporter = originalExporter;
        }

        @Override
        public void accept(Identifier id, Recipe<?> recipe, @Nullable AdvancementEntry advancement) {
            Identifier vanillaId = Identifier.ofVanilla(id.getPath());
            this.originalExporter.accept(vanillaId, recipe, advancement);
        }

        @Override
        public Advancement.Builder getAdvancementBuilder() {
            return this.originalExporter.getAdvancementBuilder();
        }
    }
}