package net.sinny.journeyreforged.mixin.entities.xp;

import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.sinny.journeyreforged.debug.xp.ExperienceOrbData;
import net.sinny.journeyreforged.debug.xp.XpSourceCategory;
import net.sinny.journeyreforged.debug.xp.XpSourceDataComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;

@Mixin(FishingBobberEntity.class)
public abstract class FishingXpMixin {

    @Inject(
            method = "use(Lnet/minecraft/item/ItemStack;)I",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z",
                    ordinal = 1
            ),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    private void tagAndSpawnFishingXpOrb(ItemStack usedItem, CallbackInfoReturnable<Integer> cir, PlayerEntity playerEntity) {
        FishingBobberEntity self = (FishingBobberEntity)(Object)this;
        int amount = self.getRandom().nextInt(6) + 1;
        ExperienceOrbEntity orb = new ExperienceOrbEntity(playerEntity.getWorld(), playerEntity.getX(), playerEntity.getY() + 0.5, playerEntity.getZ() + 0.5, amount);

        orb.getDataTracker().set(ExperienceOrbData.XP_SOURCE_DATA, Optional.of(new XpSourceDataComponent(XpSourceCategory.FISHING, Registries.ITEM.getId(Items.FISHING_ROD))));

        playerEntity.getWorld().spawnEntity(orb);
        cir.cancel();
    }
}