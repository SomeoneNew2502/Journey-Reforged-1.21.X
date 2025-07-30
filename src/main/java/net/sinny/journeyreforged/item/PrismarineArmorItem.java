package net.sinny.journeyreforged.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;

public class PrismarineArmorItem extends ArmorItem {
    public PrismarineArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient() && entity instanceof PlayerEntity player) {
            if (hasFullSuitOfArmorOn(player) && player.isTouchingWater()) {
                evaluateAndApplyEffects(player);
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    private void evaluateAndApplyEffects(PlayerEntity player) {
        // Apply Water Breathing
        player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.WATER_BREATHING,
                40,
                0,
                false,
                false,
                true
        ));

        // Apply Dolphin's Grace
        player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.DOLPHINS_GRACE,
                40,
                0,
                false,
                false,
                true
        ));
    }

    private boolean hasFullSuitOfArmorOn(PlayerEntity player) {
        ItemStack helmet = player.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack chestplate = player.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack leggings = player.getEquippedStack(EquipmentSlot.LEGS);
        ItemStack boots = player.getEquippedStack(EquipmentSlot.FEET);

        return !helmet.isEmpty() && !chestplate.isEmpty() && !leggings.isEmpty() && !boots.isEmpty() &&
                helmet.getItem() instanceof ArmorItem &&
                chestplate.getItem() instanceof ArmorItem &&
                leggings.getItem() instanceof ArmorItem &&
                boots.getItem() instanceof ArmorItem;
    }
}