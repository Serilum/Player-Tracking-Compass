package com.serilum.playertrackingcompass.fabric.services;

import com.serilum.playertrackingcompass.fabric.network.NetworkConstants;
import com.serilum.playertrackingcompass.fabric.network.PacketToServerRequestTarget;
import com.serilum.playertrackingcompass.services.helpers.PacketToServerHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class FabricPacketToServerHelper implements PacketToServerHelper {
	@Override
	public void requestCompassTrack() {
		ClientPlayNetworking.send(NetworkConstants.serverNetworkChannel, PacketToServerRequestTarget.createBuffer());
	}
}