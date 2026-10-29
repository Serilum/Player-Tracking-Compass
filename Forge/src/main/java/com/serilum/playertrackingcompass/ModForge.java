package com.serilum.playertrackingcompass;

import com.natamus.collective.check.RegisterMod;
import com.natamus.collective.check.ShouldLoadCheck;
import com.serilum.playertrackingcompass.forge.config.IntegrateForgeConfig;
import com.serilum.playertrackingcompass.forge.events.RegisterCreativeTabEvent;
import com.serilum.playertrackingcompass.forge.events.RegisterItemPropertiesEvent;
import com.serilum.playertrackingcompass.items.CompassVariables;
import com.serilum.playertrackingcompass.items.TrackingCompassItem;
import com.serilum.playertrackingcompass.util.Reference;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(Reference.MOD_ID)
public class ModForge {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);
	public static final RegistryObject<Item> TRACKING_COMPASS = ITEMS.register("tracking_compass", () ->
		CompassVariables.TRACKING_COMPASS = new TrackingCompassItem((new Item.Properties()))
	);

	public ModForge() {
		if (!ShouldLoadCheck.shouldLoad(Reference.MOD_ID)) {
			return;
		}

		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		ITEMS.register(modEventBus);
		modEventBus.addListener(this::loadComplete);
		modEventBus.register(new RegisterCreativeTabEvent());

		if (FMLEnvironment.dist.equals(Dist.CLIENT)) {
			modEventBus.addListener(RegisterItemPropertiesEvent::onClientSetup);
		}

		setGlobalConstants();
		ModCommon.init();

		IntegrateForgeConfig.registerScreen(ModLoadingContext.get());

		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}

	private void loadComplete(final FMLLoadCompleteEvent event) {

	}

	private static void setGlobalConstants() {

	}
}
