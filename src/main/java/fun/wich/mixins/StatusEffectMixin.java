package fun.wich.mixins;

import fun.wich.origins.power.PowersUtil;
import fun.wich.origins.power.UndeadPotionEffectsPower;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(StatusEffect.class)
public class StatusEffectMixin {
	@Redirect(method="applyUpdateEffect", at=@At(value="INVOKE", target="Lnet/minecraft/entity/LivingEntity;isUndead()Z"))
	public boolean InvertPotionsForUpdateEffect(LivingEntity instance) {
		return instance.isUndead() || PowersUtil.Active(instance, UndeadPotionEffectsPower.class);
	}
	@Redirect(method="applyInstantEffect", at=@At(value="INVOKE", target="Lnet/minecraft/entity/LivingEntity;isUndead()Z"))
	public boolean InvertPotionsForInstantEffect(LivingEntity instance) {
		return instance.isUndead() || PowersUtil.Active(instance, UndeadPotionEffectsPower.class);
	}
}