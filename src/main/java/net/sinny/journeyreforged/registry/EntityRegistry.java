package net.sinny.journeyreforged.registry; // Or wherever your registries are

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sinny.journeyreforged.JourneyReforged;
import net.sinny.journeyreforged.entity.ThrownDaggerEntity;

public class EntityRegistry {

    public static final EntityType<ThrownDaggerEntity> THROWN_DAGGER =
            Registry.register(
                    Registries.ENTITY_TYPE,
                    Identifier.of(JourneyReforged.MOD_ID, "thrown_dagger"),
                    EntityType.Builder.<ThrownDaggerEntity>create(ThrownDaggerEntity::new, SpawnGroup.MISC)
                            .dimensions(0.25F, 0.25F)
                            .build()
            );

    public static void registerModEntities() {
    }
}