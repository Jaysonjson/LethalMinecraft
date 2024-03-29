package json.jayson.network;

import java.util.UUID;

import json.jayson.network.packets.c2s.OnBlockUsePacket;
import json.jayson.network.packets.c2s.PickUpScrapPacket;
import json.jayson.network.packets.c2s.RequestScanPacket;
import json.jayson.network.packets.s2c.ScanLootPacket;
import json.jayson.network.packets.s2c.WeightUpdatePacket;
import json.jayson.util.LMIdentifier;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class LMNetwork {

    public static final Identifier PICKUP_SCRAP_ID = LMIdentifier.network("pickup_scrap");
    public static final Identifier SCAN_LOOT_ID = LMIdentifier.network("scan_loot");
    public static final Identifier REQUEST_SCAN_ID = LMIdentifier.network("request_scan");
    public static final Identifier BLOCK_USE_ID = LMIdentifier.network("block_use");
    public static final Identifier UPDATE_WEIGHT_ID = LMIdentifier.network("update_weight");


    public static void registerS2C() {
        ClientPlayNetworking.registerGlobalReceiver(SCAN_LOOT_ID, ScanLootPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(UPDATE_WEIGHT_ID, WeightUpdatePacket::receive);
    }

    public static void registerC2S() {
        ServerPlayNetworking.registerGlobalReceiver(PICKUP_SCRAP_ID, PickUpScrapPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(REQUEST_SCAN_ID, RequestScanPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(BLOCK_USE_ID, OnBlockUsePacket::receive);
    }

    public static class Client {

        public static void sendPickUpScrapPacket(UUID scrapEntity) {
            PacketByteBuf byteBufs = PacketByteBufs.create();
            byteBufs.writeUuid(scrapEntity);
            ClientPlayNetworking.send(LMNetwork.PICKUP_SCRAP_ID, byteBufs);
        }


        public static void sendBlockUsePacket(BlockPos pos) {
            PacketByteBuf byteBufs = PacketByteBufs.create();
            byteBufs.writeBlockPos(pos);
            ClientPlayNetworking.send(LMNetwork.BLOCK_USE_ID, byteBufs);
        }

        @Deprecated
        public static void requestScan(BlockPos pos) {
            PacketByteBuf byteBufs = PacketByteBufs.create();
            byteBufs.writeBlockPos(pos);
            ClientPlayNetworking.send(LMNetwork.REQUEST_SCAN_ID, byteBufs);
        }

    }

    public static class Server {
        @Deprecated
        public static void sendScanLootPacket(ServerPlayerEntity player, int loot) {
            PacketByteBuf byteBufs = PacketByteBufs.create();
            byteBufs.writeInt(loot);
            ServerPlayNetworking.send(player, LMNetwork.SCAN_LOOT_ID, byteBufs);
        }

        public static void sendWeightUpdate(ServerPlayerEntity player, float weight) {
            PacketByteBuf byteBufs = PacketByteBufs.create();
            byteBufs.writeFloat(weight);
            ServerPlayNetworking.send(player, LMNetwork.UPDATE_WEIGHT_ID, byteBufs);
        }
    }

}
