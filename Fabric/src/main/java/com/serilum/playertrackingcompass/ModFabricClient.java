package com.serilum.playertrackingcompass;

import com.serilum.playertrackingcompass.fabric.items.TrackingCompassPropertyFunction;
import com.serilum.playertrackingcompass.fabric.network.PacketToClientUpdateTarget;
import com.serilum.playertrackingcompass.items.CompassVariables;
import net.fabricmc.api.ClientModInitializer;
import com.serilum.playertrackingcompass.util.Reference;
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
