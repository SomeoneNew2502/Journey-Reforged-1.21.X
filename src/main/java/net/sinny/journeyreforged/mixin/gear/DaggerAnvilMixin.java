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
    @Unique
    private static final String KNOCKBACK_TRANSLATION_KEY = "enchantment.minecraft.knockback";
    @Unique
    private static final String FLAME_TRANSLATION_KEY = "enchantment.minecraft.flame";
    @Unique
    private static final String INFINITY_TRANSLATION_KEY = "enchantment.minecraft.infinity";
    @Unique
    private static final String POWER_TRANSLATION_KEY = "enchantment.minecraft.power";

    /**
     * Uses @ModifyVariable to intercept the boolean result of isAcceptableItem() right after
     * it is stored. This is a robust and compatible alternative to @Redirect.
     */
    @ModifyVariable(
            method = "updateResult",
            // Target the instruction that STORES the result into the local variable
            at = @At(value = "STORE"),
            // From the source, this is the 4th boolean variable in the method (bl, bl2, bl3, bl4).
            // Ordinal is 0-indexed, so we use 3.
            ordinal = 3
    )
    private boolean journeyReforged$preventInvalidDaggerEnchants(
            boolean original, // The original boolean value that was about to be stored
            @Local Enchantment enchantment,
            @Local(ordinal = 0) ItemStack itemStack
    ) {
        // If the enchantment was already going to be rejected, or if the item isn't a dagger,
        // we don't need to change anything.
        if (!original || !(itemStack.getItem() instanceof DaggerItem)) {
            return original;
        }

        // Now we know it's a dagger and the enchantment is acceptable by default.
        // We perform our custom check.
        Text description = enchantment.description();
        if (description.getContent() instanceof TranslatableTextContent translatable) {
            String key = translatable.getKey();
            if (key.equals(SWEEPING_EDGE_TRANSLATION_KEY)
                    || key.equals(KNOCKBACK_TRANSLATION_KEY)
                    || key.equals(FLAME_TRANSLATION_KEY)
                    || key.equals(POWER_TRANSLATION_KEY)
                    || key.equals(INFINITY_TRANSLATION_KEY)) {
                // It's a dagger and a blacklisted enchantment, so we change the result to false.
                return false;
            }
        }

        // It's a dagger, but the enchantment is not on our blacklist, so we return the original value.
        return original;
    }
}