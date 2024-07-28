package com.natamus.playertrackingcompass;

import com.natamus.playertrackingcompass.fabric.items.TrackingCompassPropertyFunction;
import com.natamus.playertrackingcompass.fabric.network.PacketToClientUpdateTarget;
import com.natamus.playertrackingcompass.items.CompassVariables;
import net.fabricmc.api.ClientModInitializer;
import com.natamus.playertrackingcompass.util.Reference;
import com.natamus.collective.check.ShouldLoadCheck;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class ModFabricClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() { 
		if (!ShouldLoadCheck.shouldLoad(Reference.MOD_ID)) {
			return;
		}

		registerEvents();
	}
	
	private void registerEvents() {
		ItemProperties.register(CompassVariables.TRACKING_COMPASS, new ResourceLocation("angle"), new TrackingCompassPropertyFunction());

		PacketToClientUpdateTarget.registerHandle();
	}
}
