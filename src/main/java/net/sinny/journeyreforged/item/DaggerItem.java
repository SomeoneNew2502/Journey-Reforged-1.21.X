package net.sinny.journeyreforged.item;

import net.minecraft.block.Blocks;
import net.minecraft.component.Component;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.sinny.journeyreforged.entity.ThrownDaggerEntity;

import java.util.List;
import java.util.Locale;

public final class DaggerItem extends ToolItem {

    private final ToolMaterial material;

    public DaggerItem(ToolMaterial material, Settings settings) {
        super(material, settings
                .component(DataComponentTypes.TOOL, createToolComponent())
                //stack.set(DataComponentTypes.ENCHANTMENTS, stack.getEnchantments().withShowInTooltip(false));
        );
        this.material = material;
    }

    public ToolMaterial getMaterial() {
        return this.material;
    }

    private static ToolComponent createToolComponent() {
        return new ToolComponent(
                List.of(ToolComponent.Rule.ofAlwaysDropping(List.of(Blocks.COBWEB), 15.0F),
                        ToolComponent.Rule.of(BlockTags.SWORD_EFFICIENT, 1.5F)
                ), 1.0F, 0);
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
        // --- STEP 1: Manually render the tooltip sections you want to control ---
        // We will add enchantments and your custom attributes in the precise order you want.

        stack.set(DataComponentTypes.ENCHANTMENTS, stack.getEnchantments().withShowInTooltip(false));

        ItemEnchantmentsComponent enchantments = stack.get(DataComponentTypes.ENCHANTMENTS);
        if (enchantments != null && !enchantments.isEmpty()) {
            for (RegistryEntry<Enchantment> enchantmentEntry : enchantments.getEnchantments()) {
                tooltip.add(Enchantment.getName(enchantmentEntry, enchantments.getLevel(enchantmentEntry)));
            }
        }

        AttributeModifiersComponent modifiers = stack.get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
        if (modifiers != null && !modifiers.modifiers().isEmpty()) {
            if (enchantments != null && !enchantments.isEmpty()) {
                tooltip.add(Text.literal(""));
            }
            tooltip.add(Text.translatable("item.modifiers.mainhand").formatted(Formatting.GRAY));
            for (AttributeModifiersComponent.Entry entry : modifiers.modifiers()) {
                EntityAttributeModifier modifier = entry.modifier();
                double finalValue;
                if (modifier.idMatches(Item.BASE_ATTACK_DAMAGE_MODIFIER_ID)) {
                    finalValue = modifier.value() + 1.0;
                    tooltip.add(Text.literal(" ").append(Text.translatable("attribute.modifier.equals.0", String.format(Locale.US, "%.1f", finalValue), Text.translatable(entry.attribute().value().getTranslationKey()))).formatted(Formatting.DARK_GREEN));
                } else if (modifier.idMatches(Item.BASE_ATTACK_SPEED_MODIFIER_ID)) {
                    finalValue = modifier.value() + 4.0;
                    tooltip.add(Text.literal(" ").append(Text.translatable("attribute.modifier.equals.0", String.format(Locale.US, "%.1f", finalValue), Text.translatable(entry.attribute().value().getTranslationKey()))).formatted(Formatting.DARK_GREEN));
                }
            }
        }

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.modifiers.when_thrown").formatted(Formatting.GRAY));
        tooltip.add(Text.literal(" ").append(Text.translatable("attribute.modifier.equals.0", String.format(Locale.US, "%.1f", this.getThrownDamage()), Text.translatable("attribute.name.journeyreforged.thrown_damage"))).formatted(Formatting.DARK_GREEN));
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
                // This correctly hides the default attribute tooltip.
                .build().withShowInTooltip(false);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if (!world.isClient) {
            ThrownDaggerEntity thrownDagger = new ThrownDaggerEntity(world, user, hand, itemStack);
            thrownDagger.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 2.0F, 1.0F);
            world.spawnEntity(thrownDagger);
        }

        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }
        user.swingHand(hand);
        return TypedActionResult.success(itemStack, false);
    }

    private float getThrownDamage() {
        if (this.material == ToolMaterials.WOOD) return 3.0f;
        if (this.material == ToolMaterials.STONE || this.material == ToolMaterials.GOLD) return 3.5f;
        if (this.material == ToolMaterials.IRON) return 4.5f;
        if (this.material == ToolMaterials.DIAMOND) return 5.0f;
        if (this.material == ToolMaterials.NETHERITE) return 6.0f;
        if (this.material == JRToolMaterials.PRISMARINE) return 6.5f;

        return 2.0f;
    }
}