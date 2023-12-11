package com.natamus.playertrackingcompass.forge.events;

import com.natamus.playertrackingcompass.items.CompassVariables;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class RegisterCreativeTabEvent {
    @SubscribeEvent
    public void buildContents(BuildCreativeModeTabContentsEvent e) {
        if (e.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            e.accept(CompassVariables.TRACKING_COMPASS);
        }
    }
}
