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
        log.info("[WarpedWeaveCarpet] onSteppedOn triggered at {} by entity {}", pos, entity.getName().getString());

        if (entity instanceof LivingEntity) {

            log.info("[WarpedWeaveCarpet] Entity is a LivingEntity.");

            float currentFallDistance = entity.fallDistance;

            if (entity.fallDistance > 0.0F) {

                log.info("[WarpedWeaveCarpet] Entity has fall distance: {}. Applying dmage reduction.", currentFallDistance);

                entity.handleFallDamage(entity.fallDistance, 0.15F, entity.getDamageSources().fall());
                entity.fallDistance = 0.0F;

                log.info("[WarpedWeaveCarpet] Fall distance reset to 0.");
            }
        }
    }
}