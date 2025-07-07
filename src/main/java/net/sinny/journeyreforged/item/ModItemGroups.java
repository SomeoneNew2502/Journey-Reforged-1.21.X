package net.sinny.journeyreforged.item;

import lombok.extern.slf4j.Slf4j;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.sinny.journeyreforged.JourneyReforged;
import net.sinny.journeyreforged.block.ModBlock;

@Slf4j
public class ModItemGroups {

    public static final ItemGroup BLOCKS = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(JourneyReforged.MOD_ID, "blocks"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(ModBlock.PEARL.getBlock()))
                    .displayName(Text.translatable("itemgroup.journey-reforged.blocks"))
                    .entries(((displayContext, entries) -> {
                        entries.add(ModBlock.PEARL.getBlock());
                        entries.add(ModBlock.PRISMARINE_ALLOY.getBlock());
                        entries.add(ModBlock.WARPED_WEAVE.getBlock());
                    })).build());

    public static final ItemGroup INGREDIENTS = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(JourneyReforged.MOD_ID, "ingredients"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(ModItems.PEARL))
                    .displayName(Text.translatable("itemgroup.journey-reforged.ingredients"))
                    .entries(((displayContext, entries) -> {
                        entries.add(ModItems.PEARL);
                        entries.add(ModItems.WARPED_THREAD);
                        entries.add(ModItems.ELDER_GUARDIAN_SCALE);
                        entries.add(ModItems.PRISMARINE_NUGGET);
                        entries.add(ModItems.PRISMARINE_INGOT);
                    })).build());

    public static void registerItemGroups() {
        log.info("Registering Item Groups for " + JourneyReforged.MOD_ID);
    }
}
