package fun.wich.mixins.entity.projectile;

import fun.wich.util.ModProjectileUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ProjectileUtil.class)
public class ProjectileUtilMixin {
	@Inject(method="createArrowProjectile", at=@At("RETURN"))
	private static void ModifyArrowProjectile(LivingEntity entity, ItemStack stack, float damageModifier, CallbackInfoReturnable<PersistentProjectileEntity> cir) {
		ModProjectileUtil.ModifyProjectile(cir.getReturnValue(), entity.getMainHandStack());
	}
}
