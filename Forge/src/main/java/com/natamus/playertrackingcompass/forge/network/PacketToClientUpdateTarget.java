package com.natamus.playertrackingcompass.forge.network;

import com.natamus.playertrackingcompass.network.CompassTrack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketToClientUpdateTarget {
	private int x;
	private int y;
	private int z;

	public PacketToClientUpdateTarget() {}

	public PacketToClientUpdateTarget(BlockPos newTarget) {
		this.x = newTarget.getX();
		this.y = newTarget.getY();
		this.z = newTarget.getZ();
	}

	public PacketToClientUpdateTarget(FriendlyByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
	}

	public void toBytes(FriendlyByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
	}

	public void handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			CompassTrack.updateTarget(x, y, z);
		});
		ctx.get().setPacketHandled(true);
	}
}
