package net.codersdownunder.magiceightball.network.payload;

import net.codersdownunder.magiceightball.MagicEightCube;
import net.codersdownunder.magiceightball.data.CustomMagicEightCubeVariant;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public record SyncCubeVariantsPayload(
        Map<ResourceLocation, CustomMagicEightCubeVariant> cubeVariants
) implements CustomPacketPayload, DataResourcesPayload<CustomMagicEightCubeVariant> {
    public static final Type<SyncCubeVariantsPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(MagicEightCube.MODID,"sync_cube_variants"));

    public SyncCubeVariantsPayload() {
        this(MagicEightCube.CUBE_VARIANTS.copyOfMap());
        //MagicEightCube.LOGGER.info(MagicEightCube.CUBE_VARIANTS.);
    }

    private static final StreamCodec<RegistryFriendlyByteBuf, HashMap<ResourceLocation, CustomMagicEightCubeVariant>> MAP_STREAM_CODEC = ByteBufCodecs.map(
            HashMap::new,
            ResourceLocation.STREAM_CODEC,
            CustomMagicEightCubeVariant.STREAM_CODEC
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncCubeVariantsPayload> STREAM_CODEC = StreamCodec.of(
            (buf, data) -> MAP_STREAM_CODEC.encode(buf, new HashMap<>(data.cubeVariants)),
            buf -> new SyncCubeVariantsPayload(MAP_STREAM_CODEC.decode(buf))
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    @Override
    public Map<ResourceLocation, CustomMagicEightCubeVariant> values() {
        return this.cubeVariants;
    }
}