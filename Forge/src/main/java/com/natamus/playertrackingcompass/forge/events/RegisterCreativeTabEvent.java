package com.natamus.playertrackingcompass.forge.events;

import com.natamus.playertrackingcompass.items.CompassVariables;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class RegisterCreativeTabEvent {
    @SubscribeEvent
    public void onCreativeTab(CreativeModeTabEvent.BuildContents e) {
        if (e.getTab().equals(CreativeModeTabs.TOOLS_AND_UTILITIES)) {
            e.accept(CompassVariables.TRACKING_COMPASS);
        }
    }
}
