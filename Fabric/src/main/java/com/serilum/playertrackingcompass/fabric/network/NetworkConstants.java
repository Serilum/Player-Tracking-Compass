package com.serilum.playertrackingcompass.fabric.network;

import com.serilum.playertrackingcompass.util.Reference;
import net.minecraft.resources.ResourceLocation;

public class NetworkConstants {
	public static ResourceLocation clientNetworkChannel = new ResourceLocation(Reference.MOD_ID, "clientnetworkchannel");
	public static ResourceLocation serverNetworkChannel = new ResourceLocation(Reference.MOD_ID, "servernetworkchannel");
}
