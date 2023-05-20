package fun.mousewich.haven.effect;

import fun.mousewich.damage.ModDamageSource;
import fun.mousewich.effect.ModStatusEffect;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.haven.HavenMod;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.player.PlayerEntity;

public class IchoredEffect extends ModStatusEffect {
	public IchoredEffect() { super(StatusEffectType.HARMFUL, 0x000000); }

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier)  {
		int k = 20 >> amplifier;
		if (k > 0) return duration % k == 0;
		else return true;
	}

	@Override
	public void applyUpdateEffect(LivingEntity entity, int i) {
		if (entity instanceof PlayerEntity player) {
			BloodType type = BloodType.Get(player);
			if (type != HavenMod.ICHOR_BLOOD_TYPE) entity.damage(ModDamageSource.ICHOR, i);
		}
	}
}
