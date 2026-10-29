package com.serilum.playertrackingcompass.util;

import com.natamus.collective.functions.MessageFunctions;
import com.serilum.playertrackingcompass.config.ConfigHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.CompassItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class Util {
	private static final String targetPositionTag = "TrackingTargetPos";
	private static final String targetDimensionTag = "TrackingTargetDimension";
	private static final String boundPlayerTag = "TrackedPlayer";

	public static void trackWithCompass(ServerPlayer tracker, ItemStack compassStack) {
		String boundPlayerName = getBoundPlayerName(compassStack);
		if (boundPlayerName.isEmpty()) {
			trackNearestPlayer(tracker, compassStack);
		}
		else {
			trackBoundPlayer(tracker, compassStack, boundPlayerName);
		}
	}

	public static void trackNearestPlayer(ServerPlayer tracker, ItemStack compassStack) {
		ServerPlayer nearestPlayer = getNearestTrackablePlayer(tracker);
		if (nearestPlayer == null) {
			MessageFunctions.sendTranslatableMessage(tracker, "collective.playertrackingcompass.message.unableredirectcompass", ChatFormatting.YELLOW);
			return;
		}

		setTrackingTarget(compassStack, nearestPlayer);

		if (ConfigHandler.showTrackedPlayerNameInMessage) {
			MessageFunctions.sendTranslatableMessage(tracker, "collective.playertrackingcompass.message.compasspointing", ChatFormatting.YELLOW, nearestPlayer.getName().getString());
		}
		else {
			MessageFunctions.sendTranslatableMessage(tracker, "collective.playertrackingcompass.message.compasspointingnearestplayer", ChatFormatting.YELLOW);
		}
	}

	private static void trackBoundPlayer(ServerPlayer tracker, ItemStack compassStack, String boundPlayerName) {
		ServerPlayer boundPlayer = tracker.getServer().getPlayerList().getPlayerByName(boundPlayerName);
		if (!isTrackableBoundPlayer(tracker, boundPlayer)) {
			MessageFunctions.sendTranslatableMessage(tracker, "collective.playertrackingcompass.message.unabletrackboundplayer", ChatFormatting.YELLOW, boundPlayerName);
			return;
		}

		setTrackingTarget(compassStack, boundPlayer);
		MessageFunctions.sendTranslatableMessage(tracker, "collective.playertrackingcompass.message.compasspointing", ChatFormatting.YELLOW, boundPlayer.getName().getString());
	}

	public static void toggleBinding(ServerPlayer player, ItemStack compassStack) {
		if (!getBoundPlayerName(compassStack).isEmpty()) {
			compassStack.getOrCreateTag().remove(boundPlayerTag);
			MessageFunctions.sendTranslatableMessage(player, "collective.playertrackingcompass.message.compassunbound", ChatFormatting.YELLOW);
			return;
		}

		compassStack.getOrCreateTag().putString(boundPlayerTag, player.getGameProfile().getName());
		MessageFunctions.sendTranslatableMessage(player, "collective.playertrackingcompass.message.compassboundtoyou", ChatFormatting.YELLOW);
	}

	public static String getBoundPlayerName(ItemStack compassStack) {
		CompoundTag compassTag = compassStack.getTag();
		if (compassTag == null) {
			return "";
		}

		return compassTag.getString(boundPlayerTag);
	}

	private static ServerPlayer getNearestTrackablePlayer(ServerPlayer tracker) {
		ServerPlayer nearestPlayer = null;
		double nearestDistanceSquared = Double.MAX_VALUE;

		for (ServerPlayer otherPlayer : tracker.serverLevel().players()) {
			if (!canBeTracked(tracker, otherPlayer)) {
				continue;
			}

			double distanceSquared = getHorizontalDistanceSquared(tracker, otherPlayer);
			if (!isBeyondMinimumDistance(distanceSquared) || !isWithinMaximumDistance(distanceSquared)) {
				continue;
			}

			if (distanceSquared < nearestDistanceSquared) {
				nearestDistanceSquared = distanceSquared;
				nearestPlayer = otherPlayer;
			}
		}

		return nearestPlayer;
	}

	private static boolean isTrackableBoundPlayer(ServerPlayer tracker, @Nullable ServerPlayer boundPlayer) {
		if (boundPlayer == null) {
			return false;
		}

		if (!boundPlayer.level().dimension().equals(tracker.level().dimension())) {
			return false;
		}

		if (!canBeTracked(tracker, boundPlayer)) {
			return false;
		}

		return isWithinMaximumDistance(getHorizontalDistanceSquared(tracker, boundPlayer));
	}

	private static boolean canBeTracked(ServerPlayer tracker, ServerPlayer otherPlayer) {
		if (otherPlayer.equals(tracker)) {
			return false;
		}

		if (ConfigHandler.ignorePlayersInSpectatorMode && otherPlayer.isSpectator()) {
			return false;
		}

		if (ConfigHandler.ignoreInvisiblePlayers && otherPlayer.hasEffect(MobEffects.INVISIBILITY)) {
			return false;
		}

		return true;
	}

	private static double getHorizontalDistanceSquared(Entity from, Entity to) {
		double xDifference = to.getX() - from.getX();
		double zDifference = to.getZ() - from.getZ();
		return (xDifference * xDifference) + (zDifference * zDifference);
	}

	private static boolean isBeyondMinimumDistance(double distanceSquared) {
		double minimumDistance = ConfigHandler.minimumTrackingDistanceInBlocks;
		return distanceSquared >= minimumDistance * minimumDistance;
	}

	private static boolean isWithinMaximumDistance(double distanceSquared) {
		if (ConfigHandler.maximumTrackingDistanceInBlocks == 0) {
			return true;
		}

		double maximumDistance = ConfigHandler.maximumTrackingDistanceInBlocks;
		return distanceSquared <= maximumDistance * maximumDistance;
	}

	private static void setTrackingTarget(ItemStack compassStack, ServerPlayer target) {
		CompoundTag compassTag = compassStack.getOrCreateTag();
		compassTag.put(targetPositionTag, NbtUtils.writeBlockPos(target.blockPosition()));
		compassTag.putString(targetDimensionTag, target.level().dimension().location().toString());
	}

	public static @Nullable GlobalPos getTrackingTarget(ItemStack compassStack) {
		CompoundTag compassTag = compassStack.getTag();
		if (compassTag == null || !compassTag.contains(targetPositionTag) || !compassTag.contains(targetDimensionTag)) {
			return null;
		}

		ResourceLocation dimensionLocation = ResourceLocation.tryParse(compassTag.getString(targetDimensionTag));
		if (dimensionLocation == null) {
			return null;
		}

		ResourceKey<Level> dimension = ResourceKey.create(Registries.DIMENSION, dimensionLocation);
		BlockPos targetPos = NbtUtils.readBlockPos(compassTag.getCompound(targetPositionTag));
		return GlobalPos.of(dimension, targetPos);
	}

	public static @Nullable GlobalPos getNeedleTarget(Level level, ItemStack compassStack) {
		GlobalPos trackingTarget = getTrackingTarget(compassStack);
		if (trackingTarget != null) {
			return trackingTarget;
		}

		if (ConfigHandler.compassSpinsWithoutTarget) {
			return null;
		}

		return CompassItem.getSpawnPosition(level);
	}
}
