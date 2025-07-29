package net.sinny.journeyreforged.mixin.gear;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class TotemMixin {

    @Inject(
            method = "tryUseTotem",
            at = @At("HEAD")
    )
    private void journeyreforged$useTotemFromHotbar(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;

        if (!(self instanceof PlayerEntity player)) {
            return;
        }

        if (player.getStackInHand(Hand.MAIN_HAND).isOf(Items.TOTEM_OF_UNDYING) || player.getStackInHand(Hand.OFF_HAND).isOf(Items.TOTEM_OF_UNDYING)) {
            return;
        }

        for (int i = 0; i < PlayerInventory.getHotbarSize(); ++i) {
            ItemStack stackInSlot = player.getInventory().getStack(i);
            if (stackInSlot.isOf(Items.TOTEM_OF_UNDYING)) {
                ItemStack offHandStack = player.getStackInHand(Hand.OFF_HAND);

                player.getInventory().setStack(i, offHandStack);
                player.setStackInHand(Hand.OFF_HAND, stackInSlot);

                break;
            }
        }
    }
}