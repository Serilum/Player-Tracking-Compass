package com.serilum.playertrackingcompass.forge.services;

import com.serilum.playertrackingcompass.forge.network.NetworkConstants;
import com.serilum.playertrackingcompass.forge.network.RequestServerPacket;
import com.serilum.playertrackingcompass.services.helpers.PacketToServerHelper;

public class ForgePacketToServerHelper implements PacketToServerHelper {
	@Override
	public void requestCompassTrack() {
		NetworkConstants.network.sendToServer(new RequestServerPacket());
	}
}