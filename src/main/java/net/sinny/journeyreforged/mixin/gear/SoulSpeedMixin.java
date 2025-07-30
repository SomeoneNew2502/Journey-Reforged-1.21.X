package net.sinny.journeyreforged.mixin.gear;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.effect.entity.DamageItemEnchantmentEffect;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DamageItemEnchantmentEffect.class)
public class SoulSpeedMixin {

    @Inject(
            method = "apply(Lnet/minecraft/server/world/ServerWorld;ILnet/minecraft/enchantment/EnchantmentEffectContext;Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Vec3d;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;damage(ILnet/minecraft/server/world/ServerWorld;Lnet/minecraft/server/network/ServerPlayerEntity;Ljava/util/function/Consumer;)V"
            ),
            cancellable = true
    )
    private void journeyreforged$preventSoulSpeedDamage(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos, CallbackInfo ci) {
        ItemStack stack = context.stack();
        boolean hasSoulSpeed = false;

        for (RegistryEntry<Enchantment> entry : EnchantmentHelper.getEnchantments(stack).getEnchantments()) {
            if (entry.matchesKey(Enchantments.SOUL_SPEED)) {
                hasSoulSpeed = true;
                break;
            }
        }

        if (hasSoulSpeed) {
            ci.cancel();
        }
    }
}