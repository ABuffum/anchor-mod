package fun.wich.origins.power;

import fun.wich.ModBase;
import fun.wich.ModId;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class ZombifiedPiglinAllyPower extends Power {
	public ZombifiedPiglinAllyPower(PowerType<?> type, LivingEntity entity) { super(type, entity); }
	public static PowerFactory<ZombifiedPiglinAllyPower> createFactory() {
		return new PowerFactory<ZombifiedPiglinAllyPower>(ModId.ID("zombified_piglin_ally"), new SerializableData(), data -> ZombifiedPiglinAllyPower::new).allowCondition();
	}

	public static void AngerNearbyPiglins(LivingEntity powerHolder, LivingEntity target, float radius) {
		World world = powerHolder.world;
		if (powerHolder instanceof ZombifiedPiglinEntity || target instanceof ZombifiedPiglinEntity) return;
		if (ModBase.IS_ZOMBIFIED_PIGLIN.isActive(powerHolder) || ModBase.IS_ZOMBIFIED_PIGLIN.isActive(powerHolder)) return;
		Box box = Box.from(powerHolder.getPos()).expand(radius, 10.0, radius);
		world.getEntitiesByClass(ZombifiedPiglinEntity.class, box, EntityPredicates.EXCEPT_SPECTATOR)
				//.stream().filter(zombifiedPiglin -> zombifiedPiglin.getTarget() == null).filter(zombifiedPiglin -> !zombifiedPiglin.isTeammate(target))
				.forEach(zombifiedPiglin -> zombifiedPiglin.setTarget(target));
	}
}