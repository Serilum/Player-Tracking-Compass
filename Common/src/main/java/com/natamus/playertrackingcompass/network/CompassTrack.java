package com.natamus.playertrackingcompass.network;

import com.natamus.collective.functions.StringFunctions;
import com.natamus.playertrackingcompass.items.CompassVariables;
import com.natamus.playertrackingcompass.services.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class CompassTrack {
    public static void request(ServerPlayer serverPlayer) {
        BlockPos targetPos = new BlockPos(0, 0, 0);
        BlockPos serverPlayerPos = serverPlayer.blockPosition();
        BlockPos comparepp = new BlockPos(serverPlayerPos.getX(), 1, serverPlayerPos.getZ());

        ServerPlayer closestplayer = null;
        double closestdistance = 999999999999.0;

        ServerLevel world = serverPlayer.getLevel();
        for (ServerPlayer oplayer : world.players()) {
            BlockPos oplayerpos = oplayer.blockPosition();
            BlockPos compareop = new BlockPos(oplayerpos.getX(), 1, oplayerpos.getZ());

            double distance = comparepp.distManhattan(compareop);
            if (distance < 10) {
                continue;
            }
            if (distance < closestdistance) {
                closestdistance = distance;
                closestplayer = oplayer;
            }
        }

        if (closestplayer != null) {
            targetPos = closestplayer.blockPosition().immutable();

            StringFunctions.sendMessage(serverPlayer, "The compass is pointing at " + closestplayer.getName().getString() + ".", ChatFormatting.YELLOW);
        }
        else {
            StringFunctions.sendMessage(serverPlayer, "Unable to redirect the compass. There are no players around or they're too close.", ChatFormatting.YELLOW);
        }

        Services.PACKETTOCLIENT.setTrackingTarget(serverPlayer, targetPos);
    }

    public static void updateTarget(int x, int y, int z) {
        CompassVariables.trackingTarget = new int[]{x, y, z};
    }
}
