package net.sinny.journeyreforged.mixin.gear;

import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.sinny.journeyreforged.mixin.accessor.ItemComponentMapSetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorItem.class)
public abstract class ArmorNerfMixin {

    @Inject(
            method = "<init>(Lnet/minecraft/registry/entry/RegistryEntry;Lnet/minecraft/item/ArmorItem$Type;Lnet/minecraft/item/Item$Settings;)V",
            at = @At("TAIL")
    )
    private void rebalanceArmorAttributes(
            RegistryEntry<ArmorMaterial> material,
            ArmorItem.Type type,
            Item.Settings settings,
            CallbackInfo ci
    ) {
        ArmorItem self = (ArmorItem) (Object) this;

        int newProtection = getNewProtectionValue(material, type);
        if (newProtection == -1) {
            return;
        }

        AttributeModifiersComponent.Builder attrBuilder = AttributeModifiersComponent.builder();
        Identifier modifierId = Identifier.ofVanilla("armor." + type.getName());
        AttributeModifierSlot slot = AttributeModifierSlot.forEquipmentSlot(type.getEquipmentSlot());

        attrBuilder.add(
                EntityAttributes.GENERIC_ARMOR,
                new EntityAttributeModifier(modifierId, newProtection, EntityAttributeModifier.Operation.ADD_VALUE),
                slot
        );

        float toughness = material.value().toughness();
        if (toughness > 0) {
            attrBuilder.add(
                    EntityAttributes.GENERIC_ARMOR_TOUGHNESS,
                    new EntityAttributeModifier(modifierId, toughness, EntityAttributeModifier.Operation.ADD_VALUE),
                    slot
            );
        }

        float knockbackResistance = material.value().knockbackResistance();
        if (knockbackResistance > 0) {
            attrBuilder.add(
                    EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE,
                    new EntityAttributeModifier(modifierId, knockbackResistance, EntityAttributeModifier.Operation.ADD_VALUE),
                    slot
            );
        }

        AttributeModifiersComponent newModifiers = attrBuilder.build();

        ComponentMap.Builder componentBuilder = ComponentMap.builder().addAll(self.getComponents());
        componentBuilder.add(DataComponentTypes.ATTRIBUTE_MODIFIERS, newModifiers);

        ((ItemComponentMapSetter) self).journey$setComponents(componentBuilder.build());
    }

    private int getNewProtectionValue(RegistryEntry<ArmorMaterial> material, ArmorItem.Type type) {
        if (material.matchesId(Identifier.ofVanilla("leather")) || material.matchesId(Identifier.ofVanilla("gold"))) {
            return switch (type) {
                case HELMET -> 1;
                case CHESTPLATE -> 3;
                case LEGGINGS -> 2;
                case BOOTS -> 1;
                default -> -1;
            };
        } else if (material.matchesId(Identifier.ofVanilla("chainmail"))) {
            return switch (type) {
                case HELMET -> 2;
                case CHESTPLATE -> 3;
                case LEGGINGS -> 3;
                case BOOTS -> 1;
                default -> -1;
            };
        } else if (material.matchesId(Identifier.ofVanilla("iron"))) {
            return switch (type) {
                case HELMET -> 2;
                case CHESTPLATE -> 4;
                case LEGGINGS -> 3;
                case BOOTS -> 2;
                default -> -1;
            };
        } else if (material.matchesId(Identifier.ofVanilla("diamond"))) {
            return switch (type) {
                case HELMET -> 3;
                case CHESTPLATE -> 5;
                case LEGGINGS -> 4;
                case BOOTS -> 3;
                default -> -1;
            };
        }
        return -1;
    }
}