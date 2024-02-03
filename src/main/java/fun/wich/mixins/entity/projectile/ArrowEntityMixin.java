package fun.wich.mixins.entity.projectile;

import fun.wich.enchantment.RecyclingEnchantment;
import fun.wich.origins.power.RecycleArrowsPower;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArrowEntity.class)
public abstract class ArrowEntityMixin extends PersistentProjectileEntity {
	protected ArrowEntityMixin(EntityType<? extends PersistentProjectileEntity> entityType, World world) { super(entityType, world); }
	protected ArrowEntityMixin(EntityType<? extends PersistentProjectileEntity> type, double x, double y, double z, World world) { super(type, x, y, z, world); }
	protected ArrowEntityMixin(EntityType<? extends PersistentProjectileEntity> type, LivingEntity owner, World world) { super(type, owner, world); }

	@Inject(method="onHit", at=@At("TAIL"))
	private void TryRecycleArrow(LivingEntity target, CallbackInfo ci) {
		if (target instanceof PlayerEntity player) {
			float chance = RecycleArrowsPower.GetChance(target);
			if (chance > 0 && player.getRandom().nextFloat() <= chance) {
				RecyclingEnchantment.Recycle(this, player);
			}
		}
	}
}
