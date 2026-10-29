package com.serilum.playertrackingcompass.neoforge.events;

import com.serilum.playertrackingcompass.items.CompassVariables;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

public class RegisterCreativeTabEvent {
	public static void buildContents(BuildCreativeModeTabContentsEvent e) {
		if (e.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
			e.accept(CompassVariables.TRACKING_COMPASS);
		}
	}
}
