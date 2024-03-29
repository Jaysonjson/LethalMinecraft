package json.jayson.network.packets.s2c;

import json.jayson.common.init.LMDataAttachments;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class WeightUpdatePacket {

    public static void receive(MinecraftClient client, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf packetByteBuf, PacketSender packetSender) {
        client.player.setAttached(LMDataAttachments.WEIGHT, packetByteBuf.readFloat());
    }

}
