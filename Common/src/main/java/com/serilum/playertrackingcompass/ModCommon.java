package com.serilum.playertrackingcompass;

import com.natamus.collective.functions.CreativeModeTabFunctions;
import com.natamus.collective.services.Services;
import com.serilum.playertrackingcompass.config.ConfigHandler;
import com.serilum.playertrackingcompass.items.CompassSpinsWithoutTargetProperty;
import com.serilum.playertrackingcompass.items.CompassVariables;
import com.serilum.playertrackingcompass.items.TrackingCompassItem;
import com.serilum.playertrackingcompass.util.Reference;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

public class ModCommon {

	public static void init() {
		ConfigHandler.initConfig();
		load();
	}

	private static void load() {

	}

	public static void registerAssets(Object modEventBusObject) {
		Services.REGISTERITEM.registerItem(modEventBusObject, Identifier.fromNamespaceAndPath(Reference.MOD_ID, "tracking_compass"), (properties) -> new TrackingCompassItem(properties), new Item.Properties(), CreativeModeTabFunctions.getCreativeModeTabResourceKey("tools_and_utilities"), true);
	}

	public static void setAssets() {
		CompassVariables.TRACKING_COMPASS = Services.REGISTERITEM.getRegisteredItem(Identifier.fromNamespaceAndPath(Reference.MOD_ID, "tracking_compass"));
	}

	public static void registerClientAssets() {
		Services.REGISTERITEMMODELPROPERTY.registerConditionalItemModelProperty(Identifier.fromNamespaceAndPath(Reference.MOD_ID, "compass_spins_without_target"), CompassSpinsWithoutTargetProperty.MAP_CODEC);
	}
}
