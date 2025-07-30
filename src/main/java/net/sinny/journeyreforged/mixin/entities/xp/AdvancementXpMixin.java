package net.sinny.journeyreforged.mixin.entities.xp;

import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.server.network.ServerPlayerEntity;
import net.sinny.journeyreforged.debug.xp.XpSourceCategory;
import net.sinny.journeyreforged.debug.xp.XpTrackingManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerAdvancementTracker.class)
public abstract class AdvancementXpMixin {

    @Shadow private ServerPlayerEntity owner;

    @Inject(
            method = "grantCriterion",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/advancement/AdvancementRewards;apply(Lnet/minecraft/server/network/ServerPlayerEntity;)V",
                    shift = At.Shift.BEFORE
            )
    )
    private void journeyreforged$captureAdvancementXp(AdvancementEntry advancement, String criterionName, CallbackInfoReturnable<Boolean> cir) {
        // Re-obtain the AdvancementRewards instance as it's not reliably captured by LocalCapture
        AdvancementRewards rewards = advancement.value().rewards();
        int amount = rewards.experience();

        // Only log if the advancement actually gives XP
        if (amount > 0) {
            XpTrackingManager.INSTANCE.addXp(this.owner, XpSourceCategory.ADVANCEMENT_REWARDS, advancement.id(), amount);
        }
    }
}