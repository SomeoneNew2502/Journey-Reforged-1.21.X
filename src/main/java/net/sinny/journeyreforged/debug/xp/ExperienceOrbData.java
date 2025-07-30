package net.sinny.journeyreforged.debug.xp;

import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;

import java.util.Optional;

public class ExperienceOrbData {

    private static final TrackedDataHandler<Optional<XpSourceDataComponent>> OPTIONAL_XP_SOURCE_COMPONENT_HANDLER = TrackedDataHandler.create(
            PacketCodec.of(
                    (value, buf) -> buf.writeOptional(value, (b, c) -> XpSourceDataComponent.PACKET_CODEC.encode((RegistryByteBuf) b, c)),
                    buf -> buf.readOptional(b -> XpSourceDataComponent.PACKET_CODEC.decode((RegistryByteBuf) b))
            )
    );

    public static final TrackedData<Optional<XpSourceDataComponent>> XP_SOURCE_DATA;

    static {
        TrackedDataHandlerRegistry.register(OPTIONAL_XP_SOURCE_COMPONENT_HANDLER);
        XP_SOURCE_DATA = DataTracker.registerData(ExperienceOrbEntity.class, OPTIONAL_XP_SOURCE_COMPONENT_HANDLER);
    }

    public static void initialize() {
    }
}