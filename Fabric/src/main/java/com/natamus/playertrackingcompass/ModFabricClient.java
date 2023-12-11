package com.natamus.playertrackingcompass;

import com.natamus.playertrackingcompass.fabric.items.TrackingCompassPropertyFunction;
import com.natamus.playertrackingcompass.fabric.network.PacketToClientUpdateTarget;
import com.natamus.playertrackingcompass.items.CompassVariables;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class ModFabricClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() { 
		registerEvents();
	}
	
	private void registerEvents() {
		ItemProperties.register(CompassVariables.TRACKING_COMPASS, new ResourceLocation("angle"), new TrackingCompassPropertyFunction());

		PacketToClientUpdateTarget.registerHandle();
	}
}
