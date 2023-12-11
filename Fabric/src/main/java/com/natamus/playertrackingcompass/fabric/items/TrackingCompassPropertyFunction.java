package com.natamus.playertrackingcompass.fabric.items;

import com.natamus.playertrackingcompass.items.CompassVariables;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
@Environment(EnvType.CLIENT)
public class TrackingCompassPropertyFunction implements ItemPropertyFunction, ClampedItemPropertyFunction {

	private double prevAngle = 0.0D;
	private double prevWobble = 0.0D;
	private long prevWorldTime = 0L;

	@Override
	public float call(@NotNull ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i) {
		return callCode(itemStack, clientLevel, livingEntity, i);
	}

	@Override
	public float unclampedCall(@NotNull ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i) {
		return callCode(itemStack, clientLevel, livingEntity, i);
	}

	private float callCode(ItemStack itemStack, ClientLevel clientLevel, LivingEntity livingEntity, int i) {
		boolean isLiving = livingEntity != null;

		if (!isLiving && !itemStack.isFramed()) {
			return 0;
		}

		Entity entity = isLiving ? livingEntity : itemStack.getFrame();

		if (clientLevel == null) {
			clientLevel = (ClientLevel)entity.level();
		}

		if (CompassVariables.trackingTarget == null) {
			return 0;
		}
		double angle;

		double entityAngle = isLiving ? entity.getYRot() : getFrameAngle((ItemFrame) entity);
		entityAngle /= 360.0D;
		entityAngle = Mth.positiveModulo(entityAngle, 1.0D);
		double posAngle = getPosToAngle(CompassVariables.trackingTarget, entity);
		posAngle /= Math.PI * 2D;
		angle = 0.5D - (entityAngle - 0.25D - posAngle);

		if (isLiving) {
			angle = wobble(clientLevel, angle);
		}

		return Mth.positiveModulo((float) angle, 1.0F);
	}

	private double wobble(Level world, double angle) {
		long worldTime = world.getGameTime();
		if (worldTime != prevWorldTime) {
			prevWorldTime = worldTime;
			double angleDifference = angle - prevAngle;
			angleDifference = Mth.positiveModulo(angleDifference + 0.5D, 1.0D) - 0.5D;
			prevWobble += angleDifference * 0.1D;
			prevWobble *= 0.8D;
			prevAngle = Mth.positiveModulo(prevAngle + prevWobble, 1.0D);
		}
		return prevAngle;
	}

	private double getFrameAngle(ItemFrame entity) {
		return Mth.wrapDegrees(180 + entity.getDirection().get2DDataValue() * 90);
	}

	private double getPosToAngle(int[] pos, Entity entity) {
		return Math.atan2(pos[2] - entity.getZ(), pos[0] - entity.getX());
	}
}
