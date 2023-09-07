package fun.mousewich.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

public class StickyEffect extends ModStatusEffect {
	public StickyEffect() {
		super(StatusEffectType.HARMFUL, 0x76BE6D);
		addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160891",
				-0.05f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
	}
	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		int k = 20 >> Math.min(4, Math.max(amplifier, 2));
		return k <= 1 || duration % k == 0;
	}
	@Override
	public void applyUpdateEffect(LivingEntity entity, int i) {
		if (entity instanceof PlayerEntity player && player.getAbilities().creativeMode) return;
		Vec3d vec3d = entity.getVelocity();
		if (vec3d.y < -0.13) {
			double d = -0.05 / vec3d.y;
			entity.setVelocity(new Vec3d(vec3d.x * d, -0.05, vec3d.z * d));
		}
		else entity.setVelocity(new Vec3d(vec3d.x, -0.05, vec3d.z));
		entity.fallDistance = 0.0f;
	}
}
