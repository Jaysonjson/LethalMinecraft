package json.jayson.common.objects.event.listener.client;

import json.jayson.client.overlay.BlockOverlay;
import json.jayson.client.overlay.PickupScrapOverlay;
import json.jayson.common.objects.block.IBlockOverlay;
import json.jayson.common.objects.entity.ScrapLootEntity;
import json.jayson.common.objects.event.custom.ClientPlayerMovementCallback;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

public class ClientPlayerMovementListener {

    public static void register() {
        ClientPlayerMovementCallback.EVENT.register((player -> {
            MinecraftClient client = MinecraftClient.getInstance();
            HitResult hit = client.crosshairTarget;
            if (hit.getType() == HitResult.Type.ENTITY) {
                EntityHitResult entityHit = (EntityHitResult) hit;
                Entity entity = entityHit.getEntity();
                if(entity instanceof ScrapLootEntity scrapLootEntity) {
                    if(scrapLootEntity.hasItem()) {
                        PickupScrapOverlay.SHOW = true;
                        PickupScrapOverlay.SCRAP = Text.translatable(scrapLootEntity.getItem().getTranslationKey()).getString();
                    }
                } else {
                    PickupScrapOverlay.SHOW = false;
                }
            } else {
                PickupScrapOverlay.SHOW = false;
            }

            if(hit.getType() == HitResult.Type.BLOCK) {
                BlockHitResult blockHit = (BlockHitResult) hit;
                Block block = client.player.getWorld().getBlockState(blockHit.getBlockPos()).getBlock();
                if(block instanceof IBlockOverlay blockOverlay) {
                    BlockOverlay.BLOCK = blockOverlay;
                } else {
                    BlockOverlay.BLOCK = null;
                }
            } else {
                BlockOverlay.BLOCK = null;
            }
        }));
    }

}
