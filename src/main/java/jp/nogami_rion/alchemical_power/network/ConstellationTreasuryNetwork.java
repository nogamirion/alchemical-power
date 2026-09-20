package jp.nogami_rion.alchemical_power.network;

import jp.nogami_rion.alchemical_power.util.ConstellationTreasuryCombat;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public final class ConstellationTreasuryNetwork {
    private static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("alchemical_power", "constellation_treasury"), () -> "2", "2"::equals, "2"::equals);
    private record Attack() {}
    private record OpenStorage() {}
    public static void register() {
        CHANNEL.messageBuilder(Attack.class, 0, NetworkDirection.PLAY_TO_SERVER)
                .encoder((request, buffer) -> {}).decoder(buffer -> new Attack())
                .consumerMainThread((request, context) -> {
                    var sender = context.get().getSender();
                    if (sender != null) ConstellationTreasuryCombat.attack(sender);
                    context.get().setPacketHandled(true);
                }).add();
        CHANNEL.messageBuilder(OpenStorage.class, 1, NetworkDirection.PLAY_TO_SERVER)
                .encoder((request, buffer) -> {}).decoder(buffer -> new OpenStorage())
                .consumerMainThread((request, context) -> {
                    var sender = context.get().getSender();
                    if (sender != null) jp.nogami_rion.alchemical_power.util.ConstellationTreasuryStorage.openHeld(sender);
                    context.get().setPacketHandled(true);
                }).add();
    }
    public static void attack() { CHANNEL.sendToServer(new Attack()); }
    public static void openStorage() { CHANNEL.sendToServer(new OpenStorage()); }
}
