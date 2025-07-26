package net.sinny.journeyreforged;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.sinny.journeyreforged.client.model.*;
import net.sinny.journeyreforged.client.render.entity.ThrownDaggerEntityRenderer;
import net.sinny.journeyreforged.registry.EntityRegistry;

public class JourneyReforgedClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // --- Register All Model Layers ---
        // You must register the shape of every model you want to use.
        EntityModelLayerRegistry.registerModelLayer(WoodenDaggerThrownModel.LAYER_LOCATION, WoodenDaggerThrownModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(StoneDaggerThrownModel.LAYER_LOCATION, StoneDaggerThrownModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(IronDaggerThrownModel.LAYER_LOCATION, IronDaggerThrownModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(GoldenDaggerThrownModel.LAYER_LOCATION, GoldenDaggerThrownModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(DiamondDaggerThrownModel.LAYER_LOCATION, DiamondDaggerThrownModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(NetheriteDaggerThrownModel.LAYER_LOCATION, NetheriteDaggerThrownModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(PrismarineDaggerThrownModel.LAYER_LOCATION, PrismarineDaggerThrownModel::getTexturedModelData);

        // --- Register the Single Universal Renderer ---
        EntityRendererRegistry.register(EntityRegistry.THROWN_DAGGER, ThrownDaggerEntityRenderer::new);
    }
}