package com.natamus.playertrackingcompass.fabric.network;

import com.natamus.playertrackingcompass.network.CompassTrack;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

public class PacketToClientUpdateTarget {
	public static FriendlyByteBuf createBuffer(BlockPos newTarget) {
		FriendlyByteBuf buf = PacketByteBufs.create();
		buf.writeInt(newTarget.getX());
		buf.writeInt(newTarget.getY());
		buf.writeInt(newTarget.getZ());
		return buf;
	}

	public static void registerHandle() {
		ClientPlayNetworking.registerGlobalReceiver(NetworkConstants.clientNetworkChannel, (client, handler, buf, responseSender) -> {
			int x = buf.readInt();
			int y = buf.readInt();
			int z = buf.readInt();

			client.execute(() -> {
				CompassTrack.updateTarget(x, y, z);
			});
		});
	}
}
