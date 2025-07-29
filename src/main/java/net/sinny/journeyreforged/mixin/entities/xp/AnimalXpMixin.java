package net.sinny.journeyreforged.mixin.entities.xp;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.sinny.journeyreforged.debug.xp.ExperienceOrbData;
import net.sinny.journeyreforged.debug.xp.XpSourceCategory;
import net.sinny.journeyreforged.debug.xp.XpSourceDataComponent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(AnimalEntity.class)
public abstract class AnimalXpMixin {

    @Inject(
            method = "breed(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/passive/AnimalEntity;Lnet/minecraft/entity/passive/PassiveEntity;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/world/ServerWorld;spawnEntity(Lnet/minecraft/entity/Entity;)Z"
            ),
            cancellable = true
    )
    private void tagBreedingXpOrb(ServerWorld world, AnimalEntity other, @Nullable PassiveEntity baby, CallbackInfo ci) {
        AnimalEntity self = (AnimalEntity) (Object) this;
        int amount = self.getRandom().nextInt(7) + 1;

        ExperienceOrbEntity orb = new ExperienceOrbEntity(world, self.getX(), self.getY(), self.getZ(), amount);

        if (baby != null) {
            orb.getDataTracker().set(ExperienceOrbData.XP_SOURCE_DATA, Optional.of(new XpSourceDataComponent(XpSourceCategory.ANIMAL_BREEDING, EntityType.getId(baby.getType()))));
        }

        world.spawnEntity(orb);
        ci.cancel();
    }
}