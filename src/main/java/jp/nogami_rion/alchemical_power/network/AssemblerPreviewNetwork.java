package jp.nogami_rion.alchemical_power.network;

import jp.nogami_rion.alchemical_power.screen.AutoAlchemicalAssemblerMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

/** Synchronizes a display-only result without adding an inventory slot. */
public final class AssemblerPreviewNetwork {
    private static final String VERSION = "1";
    private static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("alchemical_power", "assembler_preview"),
            () -> VERSION, VERSION::equals, VERSION::equals);

    private AssemblerPreviewNetwork() {}

    public static void register() {
        CHANNEL.messageBuilder(Preview.class, 0, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(Preview::encode).decoder(Preview::decode).consumerMainThread(Preview::handle).add();
    }

    public static void send(ServerPlayer player, int containerId, ItemStack result) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new Preview(containerId, result.copy()));
    }

    private record Preview(int containerId, ItemStack result) {
        void encode(FriendlyByteBuf buf) {
            buf.writeVarInt(containerId);
            buf.writeItem(result);
        }

        static Preview decode(FriendlyByteBuf buf) {
            return new Preview(buf.readVarInt(), buf.readItem());
        }

        void handle(Supplier<NetworkEvent.Context> context) {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientHandler.handle(this));
            context.get().setPacketHandled(true);
        }
    }

    private static final class ClientHandler {
        private static void handle(Preview preview) {
            var player = Minecraft.getInstance().player;
            if (player != null && player.containerMenu instanceof AutoAlchemicalAssemblerMenu menu
                    && menu.containerId == preview.containerId()) {
                menu.setRecipePreview(preview.result());
            }
        }
    }
}
