package net.sinny.journeyreforged.mixin.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.registry.tag.DamageTypeTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class CreeperExplosionMixin {

    @Inject(
            method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private void journeyreforged$onDamageFromCreeperExplosion(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;

        if (!(livingEntity instanceof CreeperEntity thisCreeper)) {
            return;
        }

        Entity attacker = source.getAttacker();

        if (source.isIn(DamageTypeTags.IS_EXPLOSION) && attacker instanceof CreeperEntity && attacker != thisCreeper) {

            if (!thisCreeper.getWorld().isClient()) {
                thisCreeper.ignite();
            }

            cir.setReturnValue(false);
        }
    }
}