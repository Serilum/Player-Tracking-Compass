package com.serilum.playertrackingcompass.items;

import com.serilum.playertrackingcompass.config.ConfigHandler;
import com.serilum.playertrackingcompass.util.Util;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class TrackingCompassItem extends Item {
	public TrackingCompassItem(Properties properties) {
		super(properties);
	}

	@Override
	public @NotNull InteractionResult use(Level level, @NotNull Player player, @NotNull InteractionHand hand) {
		if (level.isClientSide()) {
			return InteractionResult.PASS;
		}

		ItemStack compassStack = player.getItemInHand(hand);
		ServerPlayer serverPlayer = (ServerPlayer)player;
		if (player.isShiftKeyDown()) {
			Util.toggleBinding(serverPlayer, compassStack);
			return InteractionResult.PASS;
		}

		Util.trackWithCompass(serverPlayer, compassStack);

		if (ConfigHandler.cooldownInSecondsAfterUse > 0) {
			player.getCooldowns().addCooldown(compassStack, ConfigHandler.cooldownInSecondsAfterUse * 20);
		}

		return InteractionResult.PASS;
	}
}
