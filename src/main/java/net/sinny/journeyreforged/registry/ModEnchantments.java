package net.sinny.journeyreforged.registry;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.sinny.journeyreforged.JourneyReforged;

public class ModEnchantments {
    public static final Identifier REPOSSESSION_ID = Identifier.of(JourneyReforged.MOD_ID, "repossession");

    public static final RegistryKey<Enchantment> REPOSSESSION_KEY = RegistryKey.of(RegistryKeys.ENCHANTMENT, REPOSSESSION_ID);

    public static final TagKey<Item> REPOSSESSION_ENCHANTABLE = TagKey.of(RegistryKeys.ITEM, Identifier.of("c", "enchantable/repossession"));

    public static void initialize() {
    }
}