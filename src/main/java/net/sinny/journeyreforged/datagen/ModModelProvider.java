package net.sinny.journeyreforged.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;
import net.sinny.journeyreforged.registry.BlockRegistry;
import net.sinny.journeyreforged.registry.ItemRegistry;

import java.util.Map;
import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(BlockRegistry.PEARL.getBlock());
        blockStateModelGenerator.registerSimpleCubeAll(BlockRegistry.PRISMARINE_ALLOY.getBlock());
        blockStateModelGenerator.registerWoolAndCarpet(BlockRegistry.WARPED_WEAVE.getBlock(), BlockRegistry.WARPED_WEAVE_CARPET.getBlock());
        blockStateModelGenerator.registerLog(BlockRegistry.DETHREADED_WARPED_STEM.getBlock()).log(BlockRegistry.DETHREADED_WARPED_STEM.getBlock());
        blockStateModelGenerator.registerSimpleCubeAll(BlockRegistry.DETHREADED_WARPED_HYPHAE.getBlock());
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        // --- Standard Items ---
        itemModelGenerator.register(ItemRegistry.PEARL, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.PRISMARINE_NUGGET, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.PRISMARINE_INGOT, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.WARPED_THREAD, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.ELDER_GUARDIAN_SCALE, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.PRISMARINE_UPGRADE_SMITHING_TEMPLATE, Models.GENERATED);

        itemModelGenerator.register(ItemRegistry.PRISMARINE_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ItemRegistry.PRISMARINE_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ItemRegistry.PRISMARINE_AXE, Models.HANDHELD);
        itemModelGenerator.register(ItemRegistry.PRISMARINE_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ItemRegistry.PRISMARINE_HOE, Models.HANDHELD);

        // --- Daggers: Register with our custom model ---
        Model daggerModel = new CustomDaggerModel();

        itemModelGenerator.register(ItemRegistry.WOODEN_DAGGER, daggerModel);
        itemModelGenerator.register(ItemRegistry.STONE_DAGGER, daggerModel);
        itemModelGenerator.register(ItemRegistry.GOLDEN_DAGGER, daggerModel);
        itemModelGenerator.register(ItemRegistry.IRON_DAGGER, daggerModel);
        itemModelGenerator.register(ItemRegistry.DIAMOND_DAGGER, daggerModel);
        itemModelGenerator.register(ItemRegistry.NETHERITE_DAGGER, daggerModel);
        itemModelGenerator.register(ItemRegistry.PRISMARINE_DAGGER, daggerModel);
    }

    /**
     * A custom Model that generates JSON identical to "item/handheld" but adds a "ground"
     * display transform, which is required for rendering thrown entities correctly.
     */
    private static class CustomDaggerModel extends Model {

        // FIX: Use Identifier.of() as the constructor is private in 1.21
        public CustomDaggerModel() {
            super(Optional.of(Identifier.of("minecraft", "item/handheld")), Optional.empty(), TextureKey.LAYER0);
        }

        @Override
        public JsonObject createJson(Identifier id, Map<TextureKey, Identifier> textures) {
            JsonObject root = super.createJson(id, textures);
            JsonObject display = new JsonObject();

            display.add("thirdperson_righthand", createTransform(new float[]{0, -90, 55}, new float[]{0, 4.0f, 0.5f}, new float[]{0.85f, 0.85f, 0.85f}));
            display.add("thirdperson_lefthand", createTransform(new float[]{0, 90, -55}, new float[]{0, 4.0f, 0.5f}, new float[]{0.85f, 0.85f, 0.85f}));
            display.add("firstperson_righthand", createTransform(new float[]{0, -90, 25}, new float[]{1.13f, 3.2f, 1.13f}, new float[]{0.68f, 0.68f, 0.68f}));
            display.add("firstperson_lefthand", createTransform(new float[]{0, 90, -25}, new float[]{1.13f, 3.2f, 1.13f}, new float[]{0.68f, 0.68f, 0.68f}));
            display.add("ground", createTransform(new float[]{0, 0, 90}, new float[]{0, 2, 0}, new float[]{0.5f, 0.5f, 0.5f}));

            root.add("display", display);
            return root;
        }

        // FIX: Replaced missing JsonHelper with a manual JsonArray builder
        private static JsonArray createFloatArray(float... values) {
            JsonArray jsonArray = new JsonArray();
            for (float value : values) {
                jsonArray.add(value);
            }
            return jsonArray;
        }

        private static JsonObject createTransform(float[] rotation, float[] translation, float[] scale) {
            JsonObject transform = new JsonObject();
            transform.add("rotation", createFloatArray(rotation));
            transform.add("translation", createFloatArray(translation));
            transform.add("scale", createFloatArray(scale));
            return transform;
        }
    }
}