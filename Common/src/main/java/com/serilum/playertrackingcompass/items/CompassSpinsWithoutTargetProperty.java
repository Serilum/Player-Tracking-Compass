package com.serilum.playertrackingcompass.items;

import com.mojang.serialization.MapCodec;
import com.serilum.playertrackingcompass.config.ConfigHandler;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CompassSpinsWithoutTargetProperty implements ConditionalItemModelProperty {
	public static final MapCodec<CompassSpinsWithoutTargetProperty> MAP_CODEC = MapCodec.unit(new CompassSpinsWithoutTargetProperty());

	@Override
	public boolean get(@NotNull ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity owner, int seed, @NotNull ItemDisplayContext displayContext) {
		return ConfigHandler.compassSpinsWithoutTarget;
	}

	@Override
	public @NotNull MapCodec<CompassSpinsWithoutTargetProperty> type() {
		return MAP_CODEC;
	}
}
