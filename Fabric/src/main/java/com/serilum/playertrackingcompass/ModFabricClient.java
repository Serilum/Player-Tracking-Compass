package com.serilum.playertrackingcompass;

import com.natamus.collective.check.ShouldLoadCheck;
import com.serilum.playertrackingcompass.items.CompassVariables;
import com.serilum.playertrackingcompass.util.Reference;
import com.serilum.playertrackingcompass.util.Util;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.item.CompassItemPropertyFunction;
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
		ItemProperties.register(CompassVariables.TRACKING_COMPASS, ResourceLocation.withDefaultNamespace("angle"), new CompassItemPropertyFunction((clientLevel, itemStack, entity) -> Util.getNeedleTarget(clientLevel, itemStack)));
	}
}
