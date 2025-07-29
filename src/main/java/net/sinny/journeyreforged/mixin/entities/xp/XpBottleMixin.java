package net.sinny.journeyreforged.mixin.entities.xp;

import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.projectile.thrown.ExperienceBottleEntity;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.HitResult;
import net.sinny.journeyreforged.debug.xp.ExperienceOrbData;
import net.sinny.journeyreforged.debug.xp.XpSourceCategory;
import net.sinny.journeyreforged.debug.xp.XpSourceDataComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;

@Mixin(ExperienceBottleEntity.class)
public abstract class XpBottleMixin {

    @Inject(
            method = "onCollision(Lnet/minecraft/util/hit/HitResult;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/ExperienceOrbEntity;spawn(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/Vec3d;I)V"
            ),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    private void tagXpBottleOrb(HitResult hitResult, CallbackInfo ci, int xpAmount) {
        ExperienceBottleEntity self = (ExperienceBottleEntity)(Object)this;
        if (self.getWorld() instanceof ServerWorld serverWorld) {
            ExperienceOrbEntity orb = new ExperienceOrbEntity(serverWorld, self.getX(), self.getY(), self.getZ(), xpAmount);
            orb.getDataTracker().set(ExperienceOrbData.XP_SOURCE_DATA, Optional.of(new XpSourceDataComponent(XpSourceCategory.BOTTLE_O_ENCHANTING, Registries.ITEM.getId(Items.EXPERIENCE_BOTTLE))));
            serverWorld.spawnEntity(orb);
        }
        ci.cancel();
    }
}