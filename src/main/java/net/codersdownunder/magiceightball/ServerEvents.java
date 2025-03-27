package net.codersdownunder.magiceightball;

import net.codersdownunder.magiceightball.network.payload.SyncCubeVariantsPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(modid = MagicEightCube.MODID)
public final class ServerEvents {
    private ServerEvents() {}

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPlayerJoinServer(PlayerEvent.@NotNull PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if (!(player instanceof ServerPlayer serverPlayer)) return;

        sendCubeVariantsToClient(serverPlayer);
        MagicEightCube.CUBE_VARIANTS.getErrorMessages(serverPlayer).forEach(((ServerPlayer) player)::sendSystemMessage);
    }

    private static void sendCubeVariantsToClient(@NotNull ServerPlayer playerMP) {
        var payload = new SyncCubeVariantsPayload();
        MagicEightCube.LOGGER.info("Sending {} cube variants to {}", payload.cubeVariants().size(), playerMP.getScoreboardName());
        PacketDistributor.sendToPlayer(playerMP, payload);
    }
}
