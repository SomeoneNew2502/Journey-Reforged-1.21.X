package net.sinny.journeyreforged.mixin.gear;

import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.sinny.journeyreforged.mixin.accessor.ItemComponentMapSetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void rebalanceAxeAttributes(
            ToolMaterial material,
            Item.Settings settings,
            CallbackInfo ci
    ) {
        AxeItem self = (AxeItem) (Object) this;

        float finalDamage, attackSpeed;
        if (material == ToolMaterials.WOOD) {
            finalDamage = 5.0F; attackSpeed = 0.7F;
        } else if (material == ToolMaterials.STONE || material == ToolMaterials.GOLD) {
            finalDamage = 6.0F; attackSpeed = 0.7F;
        } else if (material == ToolMaterials.IRON) {
            finalDamage = 7.0F; attackSpeed = 0.8F;
        } else if (material == ToolMaterials.DIAMOND) {
            finalDamage = 8.0F; attackSpeed = 0.9F;
        } else if (material == ToolMaterials.NETHERITE) {
            finalDamage = 9.0F; attackSpeed = 0.9F;
        } else {
            return;
        }

        AttributeModifiersComponent modifiers = AttributeModifiersComponent.builder()
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE,
                        new EntityAttributeModifier(Item.BASE_ATTACK_DAMAGE_MODIFIER_ID, finalDamage - 1.0F,
                                EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED,
                        new EntityAttributeModifier(Item.BASE_ATTACK_SPEED_MODIFIER_ID, attackSpeed - 4.0F,
                                EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND)
                .build();

        ComponentMap.Builder builder = ComponentMap.builder().addAll(self.getComponents());
        builder.add(DataComponentTypes.ATTRIBUTE_MODIFIERS, modifiers);

        ((ItemComponentMapSetter) self).journey$setComponents(builder.build());
    }
}