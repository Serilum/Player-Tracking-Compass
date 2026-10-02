package com.serilum.playertrackingcompass.fabric.network;

import com.serilum.playertrackingcompass.network.CompassTrack;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;

public class PacketToServerRequestTarget {
	public static FriendlyByteBuf createBuffer() {
		return PacketByteBufs.create();
	}

	public static void registerHandle() {
		ServerPlayNetworking.registerGlobalReceiver(NetworkConstants.serverNetworkChannel, (server, player, handler, buf, responseSender) -> {
			server.execute(() -> {
				CompassTrack.request(player);
			});
		});
	}
}
