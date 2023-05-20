package fun.mousewich.mixins.entity.projectile;

import fun.mousewich.enchantment.GravityEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ShulkerBulletEntity.class)
public class ShulkerBulletEntityMixin {
	@Redirect(method="onEntityHit", at=@At(value="INVOKE", target="Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
	protected boolean ReduceBulletDamage(Entity instance, DamageSource source, float amount) {
		float newAmount = amount;
		if (instance instanceof LivingEntity livingEntity) {
			if (GravityEnchantment.wearingShulkerHelmet(livingEntity)) newAmount--;
			if (GravityEnchantment.wearingShulkerChestplate(livingEntity)) newAmount--;
			if (GravityEnchantment.wearingShulkerLeggings(livingEntity)) newAmount--;
			if (GravityEnchantment.wearingShulkerBoots(livingEntity)) newAmount--;
		}
		return instance.damage(source, MathHelper.clamp(newAmount, 0, amount));
	}
	@Redirect(method="onEntityHit", at=@At(value="INVOKE", target="Lnet/minecraft/entity/LivingEntity;addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;Lnet/minecraft/entity/Entity;)Z"))
	protected boolean DoNotLevitateEntitiesWearingShulkerArmor(LivingEntity instance, StatusEffectInstance effect, Entity source) {
		if (GravityEnchantment.wearingShulkerArmor(instance)) return false;
		return instance.addStatusEffect(effect, source);
	}
}
