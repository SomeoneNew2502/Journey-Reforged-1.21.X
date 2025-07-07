package net.sinny.journeyreforged.block.custom;

import lombok.extern.slf4j.Slf4j;
import net.minecraft.block.BlockState;
import net.minecraft.block.CarpetBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

//TODO move fall dmg value to properties
@Slf4j
public class WarpedWeaveCarpet extends CarpetBlock {
    public WarpedWeaveCarpet(Settings settings) {
        super(settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {

        if (entity instanceof LivingEntity) {

            if (entity.fallDistance > 0.0F) {

                entity.handleFallDamage(entity.fallDistance, 0.15F, entity.getDamageSources().fall());
                entity.fallDistance = 0.0F;
            }
        }
    }
}