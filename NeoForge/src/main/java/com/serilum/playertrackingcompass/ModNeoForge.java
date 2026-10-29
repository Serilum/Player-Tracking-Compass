package com.serilum.playertrackingcompass;

import com.natamus.collective.check.RegisterMod;
import com.natamus.collective.check.ShouldLoadCheck;
import com.serilum.playertrackingcompass.items.CompassVariables;
import com.serilum.playertrackingcompass.items.TrackingCompassItem;
import com.serilum.playertrackingcompass.neoforge.config.IntegrateNeoForgeConfig;
import com.serilum.playertrackingcompass.neoforge.events.RegisterCreativeTabEvent;
import com.serilum.playertrackingcompass.neoforge.events.RegisterItemPropertiesEvent;
import com.serilum.playertrackingcompass.util.Reference;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(Reference.MOD_ID)
public class ModNeoForge {

	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Reference.MOD_ID);
	public static final DeferredItem<Item> TRACKING_COMPASS = ITEMS.register("tracking_compass", () ->
		CompassVariables.TRACKING_COMPASS = new TrackingCompassItem((new Item.Properties()))
	);

	public ModNeoForge(IEventBus modEventBus) {
		if (!ShouldLoadCheck.shouldLoad(Reference.MOD_ID)) {
			return;
		}

		ITEMS.register(modEventBus);
		modEventBus.addListener(this::loadComplete);
		modEventBus.addListener(RegisterCreativeTabEvent::buildContents);

		if (FMLEnvironment.dist.equals(Dist.CLIENT)) {
			modEventBus.addListener(RegisterItemPropertiesEvent::onClientSetup);
		}

		setGlobalConstants();
		ModCommon.init();

		IntegrateNeoForgeConfig.registerScreen(ModLoadingContext.get());

		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}

	private void loadComplete(final FMLLoadCompleteEvent event) {

	}

	private static void setGlobalConstants() {

	}
}
