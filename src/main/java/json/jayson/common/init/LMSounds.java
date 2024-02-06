package json.jayson.common.init;

import json.jayson.util.LMUtil;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class LMSounds {
	
	public static final SoundEvent SMACK = registerSoundEvent("item_shovel_hit");
	public static final SoundEvent SCAN = registerSoundEvent("scan");
	public static final SoundEvent PICKUP_METAL = registerSoundEvent("pickup_metal");
	public static final SoundEvent PICKUP_PLASTIC = registerSoundEvent("pickup_plastic");


	private static SoundEvent registerSoundEvent(String name) {
		Identifier id = LMUtil.createLocation(name);
		return Registry.register(Registries.SOUND_EVENT,id,SoundEvent.of(id));
	}
	
	public static void register() {}

}
