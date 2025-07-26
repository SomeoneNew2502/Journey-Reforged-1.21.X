package net.sinny.journeyreforged.item;

import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
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
        super(material, settings.component(DataComponentTypes.TOOL, createToolComponent()));
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

    // --- Add Throwing Logic ---

    /**
     * Called when the player right-clicks while holding the dagger.
     */
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        // Optional: Play a sound when the dagger is thrown
        // world.playSound(
        //     null, // Player entity (null means play for everyone nearby)
        //     user.getX(), user.getY(), user.getZ(), // Position
        //     SoundEvents.ENTITY_SNOWBALL_THROW, // Sound event (you can choose a different one)
        //     SoundCategory.NEUTRAL, // Sound category
        //     0.5F, // Volume
        //     0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F) // Pitch
        // );

        if (!world.isClient) {
            // --- Server-side logic ---
            // Create the thrown dagger entity
            ThrownDaggerEntity thrownDagger = new ThrownDaggerEntity(world, user, hand, itemStack);

            // Set the dagger's velocity based on the player's look direction
            // The `setVelocity` method is available in ProjectileEntity (via Entity)
            // Parameters: yaw, pitch, roll (usually 0), speed, divergence
            thrownDagger.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 2.0F, 1.0F);

            // Spawn the entity in the world
            world.spawnEntity(thrownDagger);
        }

        // --- Consume the dagger from the player's hand ---
        // This makes the dagger single-use per throw, like a snowball.
        // If you want it to be reusable (e.g., like a boomerang or if you have infinite daggers in creative),
        // you would modify this logic.
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1); // Decrease the stack size by 1
        }

        // Swing the player's arm to show the throwing animation
        user.swingHand(hand);

        // Return success. The second parameter (false) indicates the hand swing
        // packet should NOT be sent, because we already called user.swingHand(hand).
        return TypedActionResult.success(itemStack, false);
    }
}