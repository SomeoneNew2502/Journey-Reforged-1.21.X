package net.sinny.journeyreforged.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

//TODO move fall dmg value to properties
public class WarpedWeaveBlock extends Block {
    public WarpedWeaveBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        entity.handleFallDamage(fallDistance, 0.15F, entity.getDamageSources().fall());
    }
}