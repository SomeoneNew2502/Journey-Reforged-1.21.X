package net.sinny.journeyreforged.registry;

import lombok.extern.slf4j.Slf4j;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.sinny.journeyreforged.JourneyReforged;

@Slf4j
public class ItemGroupRegistry {

    public static final ItemGroup BLOCKS = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(JourneyReforged.MOD_ID, "blocks"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(BlockRegistry.PEARL.getBlock()))
                    .displayName(Text.translatable("itemgroup.journey-reforged.blocks"))
                    .entries(((displayContext, entries) -> {
                        entries.add(BlockRegistry.PEARL.getBlock());
                        entries.add(BlockRegistry.PRISMARINE_ALLOY.getBlock());
                        entries.add(BlockRegistry.WARPED_WEAVE.getBlock());
                        entries.add(BlockRegistry.WARPED_WEAVE_CARPET.getBlock());
                    })).build());

    public static final ItemGroup INGREDIENTS = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(JourneyReforged.MOD_ID, "ingredients"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(ItemRegistry.PEARL))
                    .displayName(Text.translatable("itemgroup.journey-reforged.ingredients"))
                    .entries(((displayContext, entries) -> {
                        entries.add(ItemRegistry.PEARL);
                        entries.add(ItemRegistry.WARPED_THREAD);
                        entries.add(ItemRegistry.ELDER_GUARDIAN_SCALE);
                        entries.add(ItemRegistry.PRISMARINE_NUGGET);
                        entries.add(ItemRegistry.PRISMARINE_INGOT);
                    })).build());

    public static final ItemGroup GEAR = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(JourneyReforged.MOD_ID, "gear"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(ItemRegistry.PRISMARINE_PICKAXE))
                    .displayName(Text.translatable("itemgroup.journey-reforged.gear"))
                    .entries(((displayContext, entries) -> {
                        entries.add(ItemRegistry.PRISMARINE_SWORD);
                        entries.add(ItemRegistry.PRISMARINE_PICKAXE);
                        entries.add(ItemRegistry.PRISMARINE_AXE);
                        entries.add(ItemRegistry.PRISMARINE_SHOVEL);
                        entries.add(ItemRegistry.PRISMARINE_HOE);
                        entries.add(ItemRegistry.WOODEN_DAGGER);
                        entries.add(ItemRegistry.STONE_DAGGER);
                        entries.add(ItemRegistry.GOLDEN_DAGGER);
                        entries.add(ItemRegistry.IRON_DAGGER);
                        entries.add(ItemRegistry.DIAMOND_DAGGER);
                        entries.add(ItemRegistry.NETHERITE_DAGGER);
                        entries.add(ItemRegistry.PRISMARINE_DAGGER);
                    })).build());

    public static void registerItemGroups() {
        log.info("Registering Item Groups for " + JourneyReforged.MOD_ID);
    }
}
