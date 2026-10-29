package com.serilum.playertrackingcompass.forge.events;

import com.serilum.playertrackingcompass.items.CompassVariables;
import com.serilum.playertrackingcompass.util.Util;
import net.minecraft.client.renderer.item.CompassItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class RegisterItemPropertiesEvent {
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent e) {
		e.enqueueWork(() -> {
			ItemProperties.register(CompassVariables.TRACKING_COMPASS, new ResourceLocation("angle"), new CompassItemPropertyFunction((clientLevel, itemStack, entity) -> Util.getNeedleTarget(clientLevel, itemStack)));
		});
	}
}
