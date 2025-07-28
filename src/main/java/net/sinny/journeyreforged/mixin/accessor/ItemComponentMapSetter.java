package net.sinny.journeyreforged.mixin.accessor;

import net.minecraft.component.ComponentMap;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Item.class)
public interface ItemComponentMapSetter {
    @Mutable
    @Accessor("components")
    void journey$setComponents(ComponentMap map);
}