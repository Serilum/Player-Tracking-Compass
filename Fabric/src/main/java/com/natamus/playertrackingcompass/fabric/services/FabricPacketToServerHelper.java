package com.natamus.playertrackingcompass.fabric.services;

import com.natamus.playertrackingcompass.fabric.network.NetworkConstants;
import com.natamus.playertrackingcompass.fabric.network.PacketToServerRequestTarget;
import com.natamus.playertrackingcompass.services.helpers.PacketToServerHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class FabricPacketToServerHelper implements PacketToServerHelper {
    @Override
    public void requestCompassTrack() {
        ClientPlayNetworking.send(NetworkConstants.serverNetworkChannel, PacketToServerRequestTarget.createBuffer());
    }
}