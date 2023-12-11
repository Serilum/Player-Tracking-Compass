package com.natamus.playertrackingcompass.forge.services;

import com.natamus.playertrackingcompass.forge.network.NetworkConstants;
import com.natamus.playertrackingcompass.forge.network.RequestServerPacket;
import com.natamus.playertrackingcompass.services.helpers.PacketToServerHelper;

public class ForgePacketToServerHelper implements PacketToServerHelper {
    @Override
    public void requestCompassTrack() {
        NetworkConstants.network.sendToServer(new RequestServerPacket());
    }
}