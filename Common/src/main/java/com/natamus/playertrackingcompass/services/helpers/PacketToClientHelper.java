package com.natamus.playertrackingcompass.services.helpers;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;

public interface PacketToClientHelper {
    void setTrackingTarget(ServerPlayer serverPlayer, BlockPos targetPos);
}