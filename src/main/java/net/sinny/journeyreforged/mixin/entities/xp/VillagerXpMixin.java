package net.sinny.journeyreforged.mixin.entities.xp;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.village.TradeOffer;
import net.sinny.journeyreforged.debug.xp.ExperienceOrbData;
import net.sinny.journeyreforged.debug.xp.XpSourceCategory;
import net.sinny.journeyreforged.debug.xp.XpSourceDataComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;

@Mixin(VillagerEntity.class)
public abstract class VillagerXpMixin {

    @Inject(
            method = "afterUsing(Lnet/minecraft/village/TradeOffer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"
            ),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    private void tagVillagerXpOrb(TradeOffer offer, CallbackInfo ci, int xpAmount) {
        VillagerEntity self = (VillagerEntity)(Object)this;
        ExperienceOrbEntity orb = new ExperienceOrbEntity(self.getWorld(), self.getX(), self.getY() + 0.5, self.getZ(), xpAmount);

        orb.getDataTracker().set(ExperienceOrbData.XP_SOURCE_DATA, Optional.of(new XpSourceDataComponent(XpSourceCategory.VILLAGER_TRADING, EntityType.getId(self.getType()))));

        self.getWorld().spawnEntity(orb);
        ci.cancel();
    }
}