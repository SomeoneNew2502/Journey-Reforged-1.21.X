package net.sinny.journeyreforged.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.sinny.journeyreforged.client.model.*;
import net.sinny.journeyreforged.entity.ThrownDaggerEntity;
import net.sinny.journeyreforged.registry.ItemRegistry;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class ThrownDaggerEntityRenderer extends EntityRenderer<ThrownDaggerEntity> {

    private final Map<Item, EntityModel<ThrownDaggerEntity>> models;

    private static final Map<Item, Identifier> TEXTURES = Map.ofEntries(
            Map.entry(ItemRegistry.WOODEN_DAGGER, Identifier.of("journey-reforged", "textures/entity/wooden_dagger_thrown.png")),
            Map.entry(ItemRegistry.STONE_DAGGER, Identifier.of("journey-reforged", "textures/entity/stone_dagger_thrown.png")),
            Map.entry(ItemRegistry.IRON_DAGGER, Identifier.of("journey-reforged", "textures/entity/iron_dagger_thrown.png")),
            Map.entry(ItemRegistry.GOLDEN_DAGGER, Identifier.of("journey-reforged", "textures/entity/golden_dagger_thrown.png")),
            Map.entry(ItemRegistry.DIAMOND_DAGGER, Identifier.of("journey-reforged", "textures/entity/diamond_dagger_thrown.png")),
            Map.entry(ItemRegistry.NETHERITE_DAGGER, Identifier.of("journey-reforged", "textures/entity/netherite_dagger_thrown.png")),
            Map.entry(ItemRegistry.PRISMARINE_DAGGER, Identifier.of("journey-reforged", "textures/entity/prismarine_dagger_thrown.png"))
    );

    public ThrownDaggerEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        models = Map.ofEntries(
                Map.entry(ItemRegistry.WOODEN_DAGGER, new WoodenDaggerThrownModel(context.getPart(WoodenDaggerThrownModel.LAYER_LOCATION))),
                Map.entry(ItemRegistry.STONE_DAGGER, new StoneDaggerThrownModel(context.getPart(StoneDaggerThrownModel.LAYER_LOCATION))),
                Map.entry(ItemRegistry.IRON_DAGGER, new IronDaggerThrownModel(context.getPart(IronDaggerThrownModel.LAYER_LOCATION))),
                Map.entry(ItemRegistry.GOLDEN_DAGGER, new GoldenDaggerThrownModel(context.getPart(GoldenDaggerThrownModel.LAYER_LOCATION))),
                Map.entry(ItemRegistry.DIAMOND_DAGGER, new DiamondDaggerThrownModel(context.getPart(DiamondDaggerThrownModel.LAYER_LOCATION))),
                Map.entry(ItemRegistry.NETHERITE_DAGGER, new NetheriteDaggerThrownModel(context.getPart(NetheriteDaggerThrownModel.LAYER_LOCATION))),
                Map.entry(ItemRegistry.PRISMARINE_DAGGER, new PrismarineDaggerThrownModel(context.getPart(PrismarineDaggerThrownModel.LAYER_LOCATION)))
        );
    }

    // In ThrownDaggerEntityRenderer.java

    // In ThrownDaggerEntityRenderer.java

    @Override
    public void render(ThrownDaggerEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        ItemStack stack = entity.getDaggerStack();
        EntityModel<ThrownDaggerEntity> model = models.get(stack.getItem());
        Identifier texture = getTexture(entity);

        if (model == null || texture == null) {
            matrices.pop();
            return;
        }

        // --- STEP 1: AIM THE DAGGER (Your Original, Correct Aiming Logic) ---
        float finalYaw = entity.isStuck() ? entity.getYaw() : MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw());
        float finalPitch = entity.isStuck() ? entity.getPitch() : MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch());

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(finalYaw - 90.0F));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(finalPitch + 90.0F));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0F));

        // --- STEP 2: CORRECT THE PIVOT POINT AND SPIN (The New, Correct Logic) ---
        if (!entity.isStuck()) {
            // DEFINE THE OFFSET TO FIND THE MODEL'S TRUE CENTER.
            // You will need to tweak this value through trial and error.
            // Start with a small value and see how it affects the wobble.
            // A value of 0.0 means no correction.
            double pivotOffsetY = 0.0; // <-- TWEAK THIS VALUE (e.g., 0.25, -0.3, etc.)

            // 2a. Move the pivot point to the model's true center.
            matrices.translate(0.0, pivotOffsetY, 0.0);

            // 2b. Perform the spin around the new, correct pivot.
            float spinAngle = (entity.age + tickDelta) * 90.0F;
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(spinAngle));

            // 2c. CRITICAL: Move the pivot point back to its original location.
            matrices.translate(0.0, -pivotOffsetY, 0.0);
        }

        // --- STEP 3: RENDER THE MODEL ---
        model.render(matrices, vertexConsumers.getBuffer(model.getLayer(texture)), light, OverlayTexture.DEFAULT_UV, -1);

        matrices.pop();
    }

    @Override
    public Identifier getTexture(ThrownDaggerEntity entity) {
        return TEXTURES.get(entity.getDaggerStack().getItem());
    }
}