package json.jayson;

import json.jayson.client.LMClient;
import json.jayson.client.model.LMItemModelHandler;
import json.jayson.common.objects.entity.coil_head.CoilHeadEntity;
import json.jayson.common.objects.event.listener.PlayerDropItemEventListener;
import json.jayson.common.init.*;
import json.jayson.network.LMNetwork;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LM implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("lm");
	public static final String ID = "lm";
	public static ModContainer CONTAINER;

	@Override
	public void onInitialize() {
		CONTAINER = FabricLoader.getInstance().getModContainer(LM.ID).get();
		/* INITS FOR STATICS */
		LMTabs.register();
		LMItems.register();
		LMSounds.register();
		LMBlocks.register();
		LMBlockEntities.register();


		/* EVENTS */
		PlayerDropItemEventListener.register();
		/*ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			handler.player.setSprinting(false);
		});*/
		/* ENTITY ATTRIBUTES */
		FabricDefaultAttributeRegistry.register(LMEntities.COIL_HEAD, CoilHeadEntity.attributes());

		/* MODELS */
		addItemModels();

		/* NETWORK */
		LMNetwork.registerC2S();
	}


	public void addItemModels() {
		/*
		* FIRST IS ITEM
		* SECOND IS PATH TO THE 3D MODEL JSONS (assets/lm/models/item/)
		* THIRD IS PATH TO THE TEXUTRE (assets/lm/textures/item/)
		* */
		LMItemModelHandler.add(LMItems.AXOLOTL_PLUSHIE, "3d/axolotl/pink", "axolotl/pink_plushie");
		LMItemModelHandler.add(LMItems.BLUE_AXOLOTL_PLUSHIE, "3d/axolotl/blue",  "axolotl/blue_plushie");
		LMItemModelHandler.add(LMItems.BROWN_AXOLOTL_PLUSHIE, "3d/axolotl/brown",  "axolotl/brown_plushie");
		LMItemModelHandler.add(LMItems.WHITE_AXOLOTL_PLUSHIE, "3d/axolotl/white", "axolotl/white_plushie");
		LMItemModelHandler.add(LMItems.YELLOW_AXOLOTL_PLUSHIE, "3d/axolotl/yellow", "axolotl/yellow_plushie");

		LMItemModelHandler.add(LMItems.PLASTIC_COD, "3d/fish/cod", "fish/plastic_cod");
		LMItemModelHandler.add(LMItems.PLASTIC_SALMON, "3d/fish/salmon", "fish/plastic_salmon");
		LMItemModelHandler.add(LMItems.PLASTIC_TROPICAL, "3d/fish/tropical", "fish/plastic_tropical");

		LMItemModelHandler.add(LMItems.DEFAULT_FLASHLIGHT, "3d/flashlight/default/lime", "flashlights/default/lime_flashlight");
		LMItemModelHandler.add(LMItems.IRON_STOP_SIGN, "signs/stop/iron_stop_sign");
		LMItemModelHandler.add(LMItems.GOLDEN_YIELD_SIGN, "signs/yield/golden_yield_sign");
	}
}