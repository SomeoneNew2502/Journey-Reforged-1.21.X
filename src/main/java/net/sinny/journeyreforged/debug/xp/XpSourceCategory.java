package net.sinny.journeyreforged.debug.xp;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.StringIdentifiable;

public enum XpSourceCategory implements StringIdentifiable {
    MOB_KILLS,
    MINING,
    FISHING,
    VILLAGER_TRADING,
    ANIMAL_BREEDING,
    BOTTLE_O_ENCHANTING,
    ADVANCEMENT_REWARDS,
    PLAYER_DEATH,
    ENDER_DRAGON_DEATH;

    public static final Codec<XpSourceCategory> CODEC = StringIdentifiable.createCodec(XpSourceCategory::values);
    public static final PacketCodec<ByteBuf, XpSourceCategory> PACKET_CODEC = PacketCodecs.indexed(id -> XpSourceCategory.values()[id], XpSourceCategory::ordinal);

    @Override
    public String asString() {
        return this.name().toLowerCase();
    }
}