package com.serilum.playertrackingcompass.items;

import com.serilum.playertrackingcompass.config.ConfigHandler;
import com.serilum.playertrackingcompass.util.Util;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class TrackingCompassItem extends Item {
	public TrackingCompassItem(Properties builder) {
		super(builder);
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(Level level, @NotNull Player player, @NotNull InteractionHand hand) {
		ItemStack compassStack = player.getItemInHand(hand);
		if (level.isClientSide) {
			return InteractionResultHolder.pass(compassStack);
		}

		ServerPlayer serverPlayer = (ServerPlayer)player;
		if (player.isShiftKeyDown()) {
			Util.toggleBinding(serverPlayer, compassStack);
			return InteractionResultHolder.pass(compassStack);
		}

		Util.trackWithCompass(serverPlayer, compassStack);

		if (ConfigHandler.cooldownInSecondsAfterUse > 0) {
			player.getCooldowns().addCooldown(this, ConfigHandler.cooldownInSecondsAfterUse * 20);
		}

		return InteractionResultHolder.pass(compassStack);
	}

	@Override
	public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag tooltipFlag) {
		String boundPlayerName = Util.getBoundPlayerName(itemStack);
		if (!boundPlayerName.isEmpty()) {
			tooltip.add(Component.literal(" > ").append(Component.translatable("collective.playertrackingcompass.gui.tracks", boundPlayerName)).withStyle(ChatFormatting.GRAY));
		}
	}
}
