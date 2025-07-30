package net.sinny.journeyreforged.mixin.entities.xp;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.sinny.journeyreforged.debug.xp.ExperienceOrbData;
import net.sinny.journeyreforged.debug.xp.XpSourceDataComponent;
import net.sinny.journeyreforged.debug.xp.XpTrackingManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(ExperienceOrbEntity.class)
public abstract class ExperienceOrbGainMixin {

    @Shadow public abstract int getExperienceAmount();

    @Inject(
            method = "onPlayerCollision",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;addExperience(I)V"
            )
    )
    private void journeyreforged$onXpGain(PlayerEntity player, CallbackInfo ci) {
        Entity self = (Entity)(Object)this;
        Optional<XpSourceDataComponent> sourceDataOpt = self.getDataTracker().get(ExperienceOrbData.XP_SOURCE_DATA);

        sourceDataOpt.ifPresent(sourceData -> XpTrackingManager.INSTANCE.addXp(
                player,
                sourceData.category(),
                sourceData.specificSource(),
                this.getExperienceAmount()
        ));
    }
}