package com.serilum.playertrackingcompass;

import com.natamus.collective.check.RegisterMod;
import com.natamus.collective.check.ShouldLoadCheck;
import com.serilum.playertrackingcompass.fabric.network.PacketToServerRequestTarget;
import com.serilum.playertrackingcompass.items.CompassVariables;
import com.serilum.playertrackingcompass.items.TrackingCompassItem;
import com.serilum.playertrackingcompass.util.Reference;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

public class ModFabric implements ModInitializer {
	
	@Override
	public void onInitialize() {
		if (!ShouldLoadCheck.shouldLoad(Reference.MOD_ID)) {
			return;
		}

		setGlobalConstants();
		ModCommon.init();

		registerItems();
		loadEvents();

		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}

	@SuppressWarnings("UnstableApiUsage")
	private void registerItems() {
		CompassVariables.TRACKING_COMPASS = new TrackingCompassItem((new Item.Properties()));

		Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Reference.MOD_ID, "tracking_compass"), CompassVariables.TRACKING_COMPASS);
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> entries.prepend(CompassVariables.TRACKING_COMPASS));
	}

	private void loadEvents() {
		PacketToServerRequestTarget.registerHandle();
	}

	private static void setGlobalConstants() {

	}
}
