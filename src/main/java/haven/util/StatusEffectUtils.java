package haven.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StatusEffectUtils {
	public static List<ServerPlayerEntity> addEffectToPlayersWithinDistance(ServerWorld world, @Nullable Entity entity, Vec3d origin, double range, StatusEffectInstance statusEffectInstance, int duration) {
		StatusEffect statusEffect = statusEffectInstance.getEffectType();
		List<ServerPlayerEntity> list = world.getPlayers(player -> !(!player.interactionManager.isSurvivalLike() || entity != null && entity.isTeammate((Entity)player) || !origin.isInRange(player.getPos(), range) || player.hasStatusEffect(statusEffect) && player.getStatusEffect(statusEffect).getAmplifier() >= statusEffectInstance.getAmplifier() && player.getStatusEffect(statusEffect).getDuration() >= duration));
		list.forEach(player -> player.addStatusEffect(new StatusEffectInstance(statusEffectInstance), entity));
		return list;
	}
}
