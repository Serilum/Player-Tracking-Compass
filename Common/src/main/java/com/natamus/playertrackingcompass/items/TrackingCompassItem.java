package com.natamus.playertrackingcompass.items;

import com.natamus.playertrackingcompass.services.Services;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class TrackingCompassItem extends Item {
	public TrackingCompassItem (Properties builder) {
		super(builder);
	}
	
	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(Level world, @NotNull Player player, @NotNull InteractionHand hand) {
		if (world.isClientSide) {
			Services.PACKETTOSERVER.requestCompassTrack();
		}
	
		return new InteractionResultHolder<ItemStack>(InteractionResult.PASS, player.getItemInHand(hand));
	}
}
