package net.sinny.journeyreforged.mixin.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreeperEntity.class)
public abstract class CreeperExplosionMixin {

    @Inject(method = "explode", at = @At("TAIL"))
    private void shieldCooldownOnExplosion(CallbackInfo ci) {
        CreeperEntity creeper = (CreeperEntity) (Object) this;

        for (Entity hit : creeper.getWorld().getOtherEntities(
                creeper,
                creeper.getBoundingBox().expand(3.0D)
        )) {
            if (hit instanceof PlayerEntity player && player.isBlocking()) {
                ItemStack shield = player.getStackInHand(Hand.MAIN_HAND).isOf(Items.SHIELD)
                        ? player.getStackInHand(Hand.MAIN_HAND)
                        : player.getStackInHand(Hand.OFF_HAND);

                if (!shield.isEmpty()) {
                    player.disableShield();
                }
            }
        }
    }
}