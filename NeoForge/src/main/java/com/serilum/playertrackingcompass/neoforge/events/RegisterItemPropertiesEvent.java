package com.serilum.playertrackingcompass.neoforge.events;

import com.serilum.playertrackingcompass.items.CompassVariables;
import com.serilum.playertrackingcompass.util.Util;
import net.minecraft.client.renderer.item.CompassItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

public class RegisterItemPropertiesEvent {
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent e) {
		e.enqueueWork(() -> {
			ItemProperties.register(CompassVariables.TRACKING_COMPASS, ResourceLocation.withDefaultNamespace("angle"), new CompassItemPropertyFunction((clientLevel, itemStack, entity) -> Util.getNeedleTarget(clientLevel, itemStack)));
		});
	}
}
