package com.serilum.playertrackingcompass.fabric.services;

import com.serilum.playertrackingcompass.fabric.network.NetworkConstants;
import com.serilum.playertrackingcompass.fabric.network.PacketToClientUpdateTarget;
import com.serilum.playertrackingcompass.services.helpers.PacketToClientHelper;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;

public class FabricPacketToClientHelper implements PacketToClientHelper {
	@Override
	public void setTrackingTarget(ServerPlayer serverPlayer, BlockPos targetPos) {
		ServerPlayNetworking.send(serverPlayer, NetworkConstants.clientNetworkChannel, PacketToClientUpdateTarget.createBuffer(targetPos));
	}
}