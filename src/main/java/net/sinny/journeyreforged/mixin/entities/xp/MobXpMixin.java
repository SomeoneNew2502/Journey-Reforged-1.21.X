package net.sinny.journeyreforged.mixin.entities.xp;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.server.world.ServerWorld;
import net.sinny.journeyreforged.debug.xp.ExperienceOrbData;
import net.sinny.journeyreforged.debug.xp.XpSourceCategory;
import net.sinny.journeyreforged.debug.xp.XpSourceDataComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;
import java.util.Optional;

@Mixin(LivingEntity.class)
public abstract class MobXpMixin {

    private static final Map<EntityType<?>, Integer> XP_VALUES = new ImmutableMap.Builder<EntityType<?>, Integer>()
            .put(EntityType.ZOMBIE, 7)
            .put(EntityType.DROWNED, 7)
            .put(EntityType.HUSK, 7)
            .put(EntityType.SKELETON, 7)
            .put(EntityType.CREEPER, 7)
            .put(EntityType.SPIDER, 7)
            .put(EntityType.ENDERMAN, 9)
            .put(EntityType.GHAST, 10)
            .put(EntityType.HOGLIN, 10)
            .put(EntityType.PIGLIN, 7)
            .put(EntityType.PILLAGER, 7)
            .put(EntityType.SHULKER, 10)
            .put(EntityType.STRAY, 7)
            .put(EntityType.VINDICATOR, 9)
            .put(EntityType.WITCH, 10)
            .put(EntityType.WITHER_SKELETON, 10)
            .put(EntityType.ZOGLIN, 20)
            .put(EntityType.ZOMBIFIED_PIGLIN, 7)
            .put(EntityType.EVOKER, 20)
            .put(EntityType.ELDER_GUARDIAN, 100)
            .build();

    @Inject(
            method = "getXpToDrop(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/Entity;)I",
            at = @At("RETURN"),
            cancellable = true
    )
    private void journeyreforged$modifyXpDropValue(ServerWorld world, Entity attacker, CallbackInfoReturnable<Integer> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        int originalAmount = cir.getReturnValueI();
        int newAmount;

        if (self.isBaby()) {
            if (self instanceof ZombieEntity || self instanceof ZombifiedPiglinEntity) {
                newAmount = 15;
            } else {
                newAmount = originalAmount;
            }
        } else {
            newAmount = XP_VALUES.getOrDefault(self.getType(), originalAmount);
        }
        cir.setReturnValue(newAmount);
    }

    @Inject(
            method = "dropXp(Lnet/minecraft/entity/Entity;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/ExperienceOrbEntity;spawn(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/Vec3d;I)V"
            ),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    private void journeyreforged$tagMobXpOrb(Entity attacker, CallbackInfo ci, ServerWorld serverWorld) {
        LivingEntity self = (LivingEntity) (Object) this;
        int amount = self.getXpToDrop(serverWorld, attacker);

        if (amount > 0) {
            ExperienceOrbEntity orb = new ExperienceOrbEntity(serverWorld, self.getX(), self.getY(), self.getZ(), amount);

            XpSourceCategory category;
            if (self.getAttacking() == self) {
                category = XpSourceCategory.PLAYER_DEATH;
            } else if (self.getType() == EntityType.ENDER_DRAGON) {
                category = XpSourceCategory.ENDER_DRAGON_DEATH;
            } else {
                category = XpSourceCategory.MOB_KILLS;
            }

            orb.getDataTracker().set(ExperienceOrbData.XP_SOURCE_DATA, Optional.of(new XpSourceDataComponent(category, EntityType.getId(self.getType()))));
            serverWorld.spawnEntity(orb);
        }

        ci.cancel();
    }
}