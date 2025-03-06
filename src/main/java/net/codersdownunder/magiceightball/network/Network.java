package net.codersdownunder.magiceightball.network;

import net.codersdownunder.magiceightball.MagicEightCube;
import net.codersdownunder.magiceightball.network.payload.SyncCubeVariantsPayload;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@EventBusSubscriber(modid = MagicEightCube.MODID, bus = EventBusSubscriber.Bus.MOD)
public final class Network {
    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        final var registrar = event.registrar("2.0.0");

        registrar.playToClient(
                SyncCubeVariantsPayload.TYPE,
                SyncCubeVariantsPayload.STREAM_CODEC,
                (data, ctx) -> ctx.enqueueWork(() -> MagicEightCube.CUBE_VARIANTS.handleSyncPacket(data, ctx))
                        .exceptionally(e -> {
                            ctx.disconnect(Component.literal("Magic Eight Cube network failure: " + e.getMessage()));
                            return null;
                        })
        );
    }
}