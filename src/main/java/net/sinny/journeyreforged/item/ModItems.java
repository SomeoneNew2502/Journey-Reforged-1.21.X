package net.sinny.journeyreforged.item;

import lombok.extern.slf4j.Slf4j;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sinny.journeyreforged.JourneyReforged;

@Slf4j
public class ModItems {

    public static final Item PEARL = registerItem("pearl", new Item(new Item.Settings()));
    public static final Item WARPED_THREAD = registerItem("warped_thread", new Item(new Item.Settings()));
    public static final Item ELDER_GUARDIAN_SCALE = registerItem("elder_guardian_scale", new Item(new Item.Settings()));
    public static final Item PRISMARINE_NUGGET = registerItem("prismarine_nugget", new Item(new Item.Settings()));
    public static final Item PRISMARINE_INGOT = registerItem("prismarine_ingot", new Item(new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(JourneyReforged.MOD_ID, name), item);
    }

    public static void registerModItems() {
        log.info("Registering Mod Items for " + JourneyReforged.MOD_ID);
    }
}
