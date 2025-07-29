package net.sinny.journeyreforged.mixin.gear;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class BowMixin {

    @Inject(
            method = "canBeCombined",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void journeyreforged$allowInfinityAndMending(
            RegistryEntry<Enchantment> first,
            RegistryEntry<Enchantment> second,
            CallbackInfoReturnable<Boolean> cir
    ) {
        RegistryKey<Enchantment> mendingKey = Enchantments.MENDING;
        RegistryKey<Enchantment> infinityKey = Enchantments.INFINITY;

        boolean isMendingAndInfinity =
                (first.matchesKey(mendingKey) && second.matchesKey(infinityKey)) ||
                        (first.matchesKey(infinityKey) && second.matchesKey(mendingKey));

        if (isMendingAndInfinity) {
            cir.setReturnValue(true);
        }
    }
}