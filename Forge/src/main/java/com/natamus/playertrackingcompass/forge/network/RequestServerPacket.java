package com.natamus.playertrackingcompass.forge.network;

import com.natamus.playertrackingcompass.network.CompassTrack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RequestServerPacket {
	public RequestServerPacket() {}

	public RequestServerPacket(FriendlyByteBuf buf) {}

	public void fromBytes(FriendlyByteBuf buf) {}

	public void toBytes(FriendlyByteBuf buf) {}

	public void handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			CompassTrack.request(ctx.get().getSender());
		});
		ctx.get().setPacketHandled(true);
	}
}
