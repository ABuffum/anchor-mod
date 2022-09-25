package haven.items.syringe;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;

public class SyringeItem extends BaseSyringeItem {
	public final StatusEffectInstance statusEffect;

	public SyringeItem(StatusEffectInstance statusEffect) {
		super();
		this.statusEffect = statusEffect;
	}
	public SyringeItem(StatusEffectInstance statusEffect, Settings settings) {
		super(settings);
		this.statusEffect = statusEffect;
	}

	@Override
	public void ApplyEffect(PlayerEntity user, LivingEntity entity) {
		entity.addStatusEffect(new StatusEffectInstance(statusEffect), user);
	}
}
