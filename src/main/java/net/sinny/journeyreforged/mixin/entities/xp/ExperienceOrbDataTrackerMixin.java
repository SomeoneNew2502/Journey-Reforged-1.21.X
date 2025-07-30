package net.sinny.journeyreforged.mixin.entities.xp;

import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.data.DataTracker;
import net.sinny.journeyreforged.debug.xp.ExperienceOrbData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(ExperienceOrbEntity.class)
public abstract class ExperienceOrbDataTrackerMixin {

    @Inject(method = "initDataTracker", at = @At("RETURN"))
    protected void initDataTracker(DataTracker.Builder builder, CallbackInfo ci) {
        builder.add(ExperienceOrbData.XP_SOURCE_DATA, Optional.empty());
    }
}