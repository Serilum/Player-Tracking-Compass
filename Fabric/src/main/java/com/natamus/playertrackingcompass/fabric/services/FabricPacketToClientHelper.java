package com.natamus.playertrackingcompass.fabric.services;

import com.natamus.playertrackingcompass.fabric.network.NetworkConstants;
import com.natamus.playertrackingcompass.fabric.network.PacketToClientUpdateTarget;
import com.natamus.playertrackingcompass.services.helpers.PacketToClientHelper;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;

public class FabricPacketToClientHelper implements PacketToClientHelper {
    @Override
    public void setTrackingTarget(ServerPlayer serverPlayer, BlockPos targetPos) {
        ServerPlayNetworking.send(serverPlayer, NetworkConstants.clientNetworkChannel, PacketToClientUpdateTarget.createBuffer(targetPos));
    }
}