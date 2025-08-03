package net.sinny.journeyreforged.mixin.entities.xp;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.sinny.journeyreforged.debug.xp.ExperienceOrbData;
import net.sinny.journeyreforged.debug.xp.XpSourceCategory;
import net.sinny.journeyreforged.debug.xp.XpSourceDataComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;

@Mixin(Block.class)
public class BlockXpMixin {

    @ModifyVariable(
            method = "dropExperienceWhenMined",
            at = @At(value = "STORE"),
            ordinal = 0
    )
    private int journeyreforged$modifyOreXp(int originalXp) {
        Block self = (Block) (Object) this;

        if (self == Blocks.DIAMOND_ORE || self == Blocks.DEEPSLATE_DIAMOND_ORE || self == Blocks.EMERALD_ORE || self == Blocks.DEEPSLATE_EMERALD_ORE) {
            return 9;
        } else if (self == Blocks.LAPIS_ORE || self == Blocks.DEEPSLATE_LAPIS_ORE || self == Blocks.REDSTONE_ORE || self == Blocks.DEEPSLATE_REDSTONE_ORE || self == Blocks.NETHER_QUARTZ_ORE) {
            return 4;
        } else if (self == Blocks.NETHER_GOLD_ORE) {
            return 2;
        } else if (self == Blocks.COAL_ORE || self == Blocks.DEEPSLATE_COAL_ORE) {
            return 1;
        }

        return originalXp;
    }

    @Inject(
            method = "dropExperienceWhenMined",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/Block;dropExperience(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;I)V"
            ),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    private void journeyreforged$tagMiningXp(ServerWorld world, BlockPos pos, ItemStack tool, IntProvider experience, CallbackInfo ci, int xpAmount) {
        Block self = (Block)(Object)this;
        ExperienceOrbEntity orb = new ExperienceOrbEntity(world, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, xpAmount);
        orb.getDataTracker().set(ExperienceOrbData.XP_SOURCE_DATA, Optional.of(new XpSourceDataComponent(XpSourceCategory.MINING, Registries.BLOCK.getId(self))));
        world.spawnEntity(orb);
        ci.cancel();
    }
}