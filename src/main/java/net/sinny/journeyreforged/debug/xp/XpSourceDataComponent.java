package net.sinny.journeyreforged.debug.xp;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.util.Identifier;

public record XpSourceDataComponent(XpSourceCategory category, Identifier specificSource) {

    public static final Codec<XpSourceDataComponent> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    XpSourceCategory.CODEC.fieldOf("category").forGetter(XpSourceDataComponent::category),
                    Identifier.CODEC.fieldOf("specific_source").forGetter(XpSourceDataComponent::specificSource)
            ).apply(instance, XpSourceDataComponent::new)
    );

    public static final PacketCodec<RegistryByteBuf, XpSourceDataComponent> PACKET_CODEC = PacketCodec.tuple(
            XpSourceCategory.PACKET_CODEC, XpSourceDataComponent::category,
            Identifier.PACKET_CODEC, XpSourceDataComponent::specificSource,
            XpSourceDataComponent::new
    );
}