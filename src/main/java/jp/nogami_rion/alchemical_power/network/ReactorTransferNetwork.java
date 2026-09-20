package jp.nogami_rion.alchemical_power.network;

import jp.nogami_rion.alchemical_power.recipe.AlchemicalReactorRecipe;
import jp.nogami_rion.alchemical_power.screen.AlchemicalReactorMenu;
import jp.nogami_rion.alchemical_power.util.ReactorRecipeTransfer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import java.util.function.Supplier;

/** The client sends only the recipe ID; all items/counts are recomputed from server data. */
public final class ReactorTransferNetwork {
    private static final String VERSION = "3";
    private static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("alchemical_power", "reactor_transfer"), () -> VERSION, VERSION::equals, VERSION::equals);
    private ReactorTransferNetwork() {}
    public static void register() {
        CHANNEL.messageBuilder(Request.class, 0, NetworkDirection.PLAY_TO_SERVER)
                .encoder(Request::encode).decoder(Request::decode).consumerMainThread(Request::handle).add();
    }
    public static void send(int containerId, ResourceLocation recipeId, boolean max) {
        CHANNEL.sendToServer(new Request(containerId, recipeId, max));
    }
    private record Request(int containerId, ResourceLocation recipeId, boolean max) {
        void encode(FriendlyByteBuf buf) { buf.writeVarInt(containerId); buf.writeResourceLocation(recipeId); buf.writeBoolean(max); }
        static Request decode(FriendlyByteBuf buf) { return new Request(buf.readVarInt(), buf.readResourceLocation(), buf.readBoolean()); }
        void handle(Supplier<NetworkEvent.Context> context) {
            var player = context.get().getSender();
            if (player != null && player.containerMenu instanceof AlchemicalReactorMenu menu
                    && menu.containerId == containerId && menu.stillValid(player)) {
                var found = player.level().getRecipeManager().byKey(recipeId).orElse(null);
                if (found instanceof AlchemicalReactorRecipe recipe) {
                    var error = ReactorRecipeTransfer.transfer(menu, player, recipe, max, true);
                    if (error != ReactorRecipeTransfer.Error.NONE)
                        player.displayClientMessage(Component.translatable("gui.alchemical_power.reactor.transfer." + error.name().toLowerCase(java.util.Locale.ROOT)), true);
                }
            }
            context.get().setPacketHandled(true);
        }
    }
}
