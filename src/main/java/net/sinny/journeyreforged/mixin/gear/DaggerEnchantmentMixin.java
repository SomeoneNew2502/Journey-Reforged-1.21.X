package net.sinny.journeyreforged.mixin.gear;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.sinny.journeyreforged.item.DaggerItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(Enchantment.class)
public abstract class DaggerEnchantmentMixin {

    @Shadow public abstract Text description();

    @Unique
    private static final Set<String> BANNED_DAGGER_TRANSLATION_KEYS = Set.of(
            "enchantment.minecraft.sweeping_edge",
            "enchantment.minecraft.knockback",
            "enchantment.minecraft.flame",
            "enchantment.minecraft.power",
            "enchantment.minecraft.infinity"
    );

    @Inject(method = "isPrimaryItem(Lnet/minecraft/item/ItemStack;)Z", at = @At("HEAD"), cancellable = true)
    private void journeyReforged$preventBannedEnchantsAsPrimary(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.getItem() instanceof DaggerItem) {
            Text description = this.description();

            if (description.getContent() instanceof TranslatableTextContent content) {
                String translationKey = content.getKey();


                if (BANNED_DAGGER_TRANSLATION_KEYS.contains(translationKey)) {
                    cir.setReturnValue(false);
                }
            }
        }
    }
}