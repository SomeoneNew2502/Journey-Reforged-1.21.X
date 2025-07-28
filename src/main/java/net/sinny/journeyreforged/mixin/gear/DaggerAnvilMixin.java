package net.sinny.journeyreforged.mixin.gear;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.sinny.journeyreforged.item.DaggerItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(AnvilScreenHandler.class)
public abstract class DaggerAnvilMixin {

    @Unique
    private static final String SWEEPING_EDGE_TRANSLATION_KEY = "enchantment.minecraft.sweeping_edge";
    private static final String KNOCKBACK_TRANSLATION_KEY = "enchantment.minecraft.knockback";
    private static final String FLAME_TRANSLATION_KEY = "enchantment.minecraft.flame";
    private static final String INFINITY_TRANSLATION_KEY = "enchantment.minecraft.infinity";
    private static final String POWER_TRANSLATION_KEY = "enchantment.minecraft.power";

    @ModifyVariable(
            method = "updateResult",
            at = @At(value = "STORE"),
            name = "bl4"
    )
    private boolean journeyReforged$preventSweepingOnDagger(
            boolean original,
            @Local Enchantment enchantment,
            @Local(ordinal = 0) ItemStack itemStack
    ) {
        if (!original) {
            return false;
        }

        Text description = enchantment.description();
        if (description.getContent() instanceof TranslatableTextContent translatableTextContent) {
            if (translatableTextContent.getKey().equals(SWEEPING_EDGE_TRANSLATION_KEY)
                    || translatableTextContent.getKey().equals(KNOCKBACK_TRANSLATION_KEY)
                    || translatableTextContent.getKey().equals(FLAME_TRANSLATION_KEY)
                    || translatableTextContent.getKey().equals(POWER_TRANSLATION_KEY)
                    || translatableTextContent.getKey().equals(INFINITY_TRANSLATION_KEY)) {
                if (itemStack.getItem() instanceof DaggerItem) {
                    return false;
                }
            }
        }

        return original;
    }
}