package net.sinny.journeyreforged.mixin.gear;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemDurabilityMixin {

    @Inject(
            method = "postMine",
            at = @At("HEAD"),
            cancellable = true
    )
    private void journeyreforged$preventDurabilityOnLeaves(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner, CallbackInfoReturnable<Boolean> cir) {
        Item self = (Item) (Object) this;

        if (self instanceof AxeItem && state.isIn(BlockTags.LEAVES)) {
            cir.setReturnValue(true);
        }
    }
}