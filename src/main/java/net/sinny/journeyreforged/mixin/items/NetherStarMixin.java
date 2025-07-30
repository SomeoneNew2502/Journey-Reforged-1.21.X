package net.sinny.journeyreforged.mixin.items;

import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Items;
import net.minecraft.util.Unit;
import net.sinny.journeyreforged.mixin.accessor.ItemComponentMapSetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Items.class)
public class NetherStarMixin {

    @Inject(
            method = "<clinit>",
            at = @At("TAIL")
    )
    private static void journeyreforged$makeNetherStarFireproof(CallbackInfo ci) {
        ComponentMap.Builder builder = ComponentMap.builder().addAll(Items.NETHER_STAR.getComponents());
        builder.add(DataComponentTypes.FIRE_RESISTANT, Unit.INSTANCE);
        ((ItemComponentMapSetter) Items.NETHER_STAR).journey$setComponents(builder.build());
    }
}