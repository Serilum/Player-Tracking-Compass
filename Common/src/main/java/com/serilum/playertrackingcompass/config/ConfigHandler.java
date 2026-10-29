package com.serilum.playertrackingcompass.config;

import com.natamus.collective.config.DuskConfig;
import com.serilum.playertrackingcompass.util.Reference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConfigHandler extends DuskConfig {
	public static HashMap<String, List<String>> configMetaData = new HashMap<>();

	@Entry(min = 0, max = 100000) public static int minimumTrackingDistanceInBlocks = 10;
	@Entry(min = 0, max = 100000) public static int maximumTrackingDistanceInBlocks = 0;
	@Entry public static boolean ignorePlayersInSpectatorMode = true;
	@Entry public static boolean ignoreInvisiblePlayers = true;
	@Entry public static boolean showTrackedPlayerNameInMessage = true;
	@Entry(min = 0, max = 3600) public static int cooldownInSecondsAfterUse = 0;
	@Entry public static boolean compassSpinsWithoutTarget = true;

	public static void initConfig() {
		configMetaData.put("minimumTrackingDistanceInBlocks", Arrays.asList(
			"Players closer than this horizontal distance in blocks are skipped, so the compass points at the next nearest player instead. Useful for teams."
		));
		configMetaData.put("maximumTrackingDistanceInBlocks", Arrays.asList(
			"Players further away than this horizontal distance in blocks can't be tracked. 0 means there is no limit."
		));
		configMetaData.put("ignorePlayersInSpectatorMode", Arrays.asList(
			"Whether players in spectator mode are skipped by the compass."
		));
		configMetaData.put("ignoreInvisiblePlayers", Arrays.asList(
			"Whether players with the Invisibility effect are skipped by the compass, so drinking a Potion of Invisibility hides you from it."
		));
		configMetaData.put("showTrackedPlayerNameInMessage", Arrays.asList(
			"Whether the chat message after using the compass names the tracked player. When disabled, it only says the compass is pointing at the nearest player."
		));
		configMetaData.put("cooldownInSecondsAfterUse", Arrays.asList(
			"How many seconds a player has to wait before the Tracking Compass can be used again. 0 disables the cooldown."
		));
		configMetaData.put("compassSpinsWithoutTarget", Arrays.asList(
			"Whether a Tracking Compass that hasn't tracked anyone yet spins. When disabled, it points at the world spawn like a normal compass."
		));

		DuskConfig.init(Reference.NAME, Reference.MOD_ID, ConfigHandler.class);
	}
}
