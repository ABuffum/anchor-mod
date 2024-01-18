package fun.mousewich.mixins.entity.hostile;

import fun.mousewich.origins.power.PhantomNeutralityPower;
import fun.mousewich.origins.power.PowersUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(PhantomEntity.FindTargetGoal.class)
public class PhantomEntityFindTargetGoalMixin {
	private final TargetPredicate NON_NEUTRAL_PLAYERS_IN_RANGE_PREDICATE = TargetPredicate.createAttackable().setBaseMaxDistance(64.0)
			.setPredicate(entity -> !PowersUtil.Active(entity, PhantomNeutralityPower.class));

	@Redirect(method="canStart", at=@At(value="INVOKE", target="Lnet/minecraft/world/World;getPlayers(Lnet/minecraft/entity/ai/TargetPredicate;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/util/math/Box;)Ljava/util/List;"))
	private List<PlayerEntity> GetPlayersWithoutNeutralityPower(World instance, TargetPredicate targetPredicate, LivingEntity livingEntity, Box box) {
		return instance.getPlayers(NON_NEUTRAL_PLAYERS_IN_RANGE_PREDICATE, livingEntity, box);
	}
}
