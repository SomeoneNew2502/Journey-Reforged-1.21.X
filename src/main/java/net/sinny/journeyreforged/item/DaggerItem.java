package net.sinny.journeyreforged.item;

import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;
import java.util.Locale;

public final class DaggerItem extends ToolItem {

    public DaggerItem(ToolMaterial material, Settings settings) {
        super(material, settings.component(DataComponentTypes.TOOL, createToolComponent()));
    }

    private static ToolComponent createToolComponent() {
        return new ToolComponent(
                List.of(ToolComponent.Rule.ofAlwaysDropping(List.of(Blocks.COBWEB), 15.0F),
                        ToolComponent.Rule.of(BlockTags.SWORD_EFFICIENT, 1.5F)
                ), 1.0F, 0);
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);

        AttributeModifiersComponent modifiers = stack.get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
        if (modifiers == null) {
            return;
        }

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.modifiers.mainhand").formatted(Formatting.GRAY));

        for (AttributeModifiersComponent.Entry entry : modifiers.modifiers()) {
            EntityAttributeModifier modifier = entry.modifier();
            double finalValue;

            if (modifier.idMatches(Item.BASE_ATTACK_DAMAGE_MODIFIER_ID)) {
                finalValue = modifier.value() + 1.0;
                tooltip.add(
                        Text.literal(" ")
                                .append(Text.translatable("attribute.modifier.equals.0", String.format(Locale.US, "%.1f", finalValue), Text.translatable(entry.attribute().value().getTranslationKey())))
                                .formatted(Formatting.DARK_GREEN)
                );
            }
            else if (modifier.idMatches(Item.BASE_ATTACK_SPEED_MODIFIER_ID)) {
                finalValue = modifier.value() + 4.0;
                tooltip.add(
                        Text.literal(" ")
                                .append(Text.translatable("attribute.modifier.equals.0", String.format(Locale.US, "%.1f", finalValue), Text.translatable(entry.attribute().value().getTranslationKey())))
                                .formatted(Formatting.DARK_GREEN)
                );
            }
        }
    }

    public static AttributeModifiersComponent createDaggerAttributes(ToolMaterial material, float baseAttackDamage, float attackSpeed) {
        return AttributeModifiersComponent.builder()
                .add(
                        EntityAttributes.GENERIC_ATTACK_DAMAGE,
                        new EntityAttributeModifier(
                                Item.BASE_ATTACK_DAMAGE_MODIFIER_ID,
                                baseAttackDamage + material.getAttackDamage(),
                                EntityAttributeModifier.Operation.ADD_VALUE
                        ),
                        AttributeModifierSlot.MAINHAND
                )
                .add(
                        EntityAttributes.GENERIC_ATTACK_SPEED,
                        new EntityAttributeModifier(
                                Item.BASE_ATTACK_SPEED_MODIFIER_ID,
                                attackSpeed,
                                EntityAttributeModifier.Operation.ADD_VALUE
                        ),
                        AttributeModifierSlot.MAINHAND
                )
                .build().withShowInTooltip(false);
    }
}