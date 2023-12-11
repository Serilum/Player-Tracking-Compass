package com.natamus.playertrackingcompass.forge.services;

import com.natamus.playertrackingcompass.forge.network.NetworkConstants;
import com.natamus.playertrackingcompass.forge.network.PacketToClientUpdateTarget;
import com.natamus.playertrackingcompass.services.helpers.PacketToClientHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;

public class ForgePacketToClientHelper implements PacketToClientHelper {
    @Override
    public void setTrackingTarget(ServerPlayer serverPlayer, BlockPos targetPos) {
        NetworkConstants.network.sendTo(new PacketToClientUpdateTarget(targetPos), serverPlayer.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
    }
}