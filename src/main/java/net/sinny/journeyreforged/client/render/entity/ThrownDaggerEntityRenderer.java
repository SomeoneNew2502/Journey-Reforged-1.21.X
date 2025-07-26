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

        // --- Realistic Sticking Logic ---

        // 1. Aim the model using Yaw and Pitch. This establishes the base orientation.
        // We use non-lerped values for the stuck dagger to prevent visual jitter.
        float finalYaw = entity.isStuck() ? entity.getYaw() : MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw());
        float finalPitch = entity.isStuck() ? entity.getPitch() : MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch());

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(finalYaw - 90.0F));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(finalPitch + 90.0F));

        // 2. Apply the orientation flip to make the dagger model vertical.
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0F));

        // 3. Apply the spin ONLY if the dagger is NOT stuck.
        if (!entity.isStuck()) {
            float spinAngle = (entity.age + tickDelta) * 90.0F; // Your spin speed
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(spinAngle));
        }

        // As you discovered, no translation is needed, which is great.

        // Render the model.
        model.render(matrices, vertexConsumers.getBuffer(model.getLayer(texture)), light, OverlayTexture.DEFAULT_UV, -1);

        matrices.pop();
    }

    @Override
    public Identifier getTexture(ThrownDaggerEntity entity) {
        return TEXTURES.get(entity.getDaggerStack().getItem());
    }
}