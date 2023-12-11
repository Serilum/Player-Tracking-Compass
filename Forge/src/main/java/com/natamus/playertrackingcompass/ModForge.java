package com.natamus.playertrackingcompass;

import com.natamus.collective.check.RegisterMod;
import com.natamus.playertrackingcompass.forge.events.RegisterCreativeTabEvent;
import com.natamus.playertrackingcompass.forge.network.NetworkConstants;
import com.natamus.playertrackingcompass.forge.network.PacketToClientUpdateTarget;
import com.natamus.playertrackingcompass.forge.network.RequestServerPacket;
import com.natamus.playertrackingcompass.items.CompassVariables;
import com.natamus.playertrackingcompass.items.TrackingCompassItem;
import com.natamus.playertrackingcompass.util.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
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
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		ITEMS.register(modEventBus);
		modEventBus.addListener(this::commonSetup);
		modEventBus.addListener(this::loadComplete);
		modEventBus.register(new RegisterCreativeTabEvent());

		setGlobalConstants();
		ModCommon.init();

		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}

    private void commonSetup(final FMLCommonSetupEvent event) {
		NetworkConstants.network = NetworkRegistry.newSimpleChannel(new ResourceLocation(Reference.MOD_ID, Reference.MOD_ID), () -> "1.0", s -> true, s -> true);

		NetworkConstants.network.registerMessage(0, RequestServerPacket.class, RequestServerPacket::toBytes, RequestServerPacket::new, RequestServerPacket::handle);

		NetworkConstants.network.registerMessage(1, PacketToClientUpdateTarget.class, PacketToClientUpdateTarget::toBytes, PacketToClientUpdateTarget::new, PacketToClientUpdateTarget::handle);
    }

	private void loadComplete(final FMLLoadCompleteEvent event) {

	}

	private static void setGlobalConstants() {

	}
}