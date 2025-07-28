package net.sinny.journeyreforged.event.block;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import net.sinny.journeyreforged.registry.BlockRegistry;
import net.sinny.journeyreforged.registry.ItemRegistry;

public class ShearBlockEventHandler {

    public static void registerEvents() {
        UseBlockCallback.EVENT.register(ShearBlockEventHandler::onBlockUse);
    }

    private static ActionResult onBlockUse(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        ItemStack stack = player.getStackInHand(hand);

        // Check if player is using shears
        if (stack.getItem() != Items.SHEARS) {
            return ActionResult.PASS;
        }

        BlockState state = world.getBlockState(hitResult.getBlockPos());
        Block block = state.getBlock();

        // Check if it's one of the blocks we want to shear
        if (block == Blocks.WARPED_STEM) {
            return shearBlock(player, world, hand, hitResult, BlockRegistry.DETHREADED_WARPED_STEM.getBlock(), stack);
        } else if (block == Blocks.WARPED_HYPHAE) {
            return shearBlock(player, world, hand, hitResult, BlockRegistry.DETHREADED_WARPED_HYPHAE.getBlock(), stack);
        }

        return ActionResult.PASS;
    }

    private static ActionResult shearBlock(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult, Block newBlock, ItemStack shears) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }

        // Damage the shears
        if (!player.isCreative()) {
            // Ensure we're on the server side
            if (world instanceof ServerWorld serverWorld && player instanceof ServerPlayerEntity serverPlayer) {
                shears.damage(1, serverWorld, serverPlayer, (Item item) -> {
                    // Optionally log when the shears break
                    System.out.println("Shears broke!");
                });
            }
        }

        // Replace the block
        world.setBlockState(hitResult.getBlockPos(), newBlock.getDefaultState()
                .withIfExists(net.minecraft.state.property.Properties.AXIS,
                        world.getBlockState(hitResult.getBlockPos()).get(net.minecraft.state.property.Properties.AXIS)));

        // Drop warped thread (1-3)
        int threadCount = world.random.nextInt(3) + 1;
        Block.dropStack(world, hitResult.getBlockPos(), new ItemStack(ItemRegistry.WARPED_THREAD, threadCount));

        return ActionResult.SUCCESS;
    }
}