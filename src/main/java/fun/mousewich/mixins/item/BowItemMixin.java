package fun.mousewich.mixins.item;

import fun.mousewich.util.ModProjectileUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BowItem.class)
public abstract class BowItemMixin {
	@Redirect(method="onStoppedUsing", at=@At(value="INVOKE", target="Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
	private boolean AllowNonstandardArrows(ItemStack instance, Item item) {
		if (item == Items.SPECTRAL_ARROW) return instance.getItem() instanceof ArrowItem || instance.isOf(item);
		return instance.isOf(item);
	}
	@Redirect(method="onStoppedUsing", at=@At(value="INVOKE", target="Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"))
	private boolean ModifyArrow(World instance, Entity entity) {
		if (entity instanceof PersistentProjectileEntity projectile) {
			if (projectile.getOwner() instanceof LivingEntity owner) {
				ModProjectileUtil.ModifyProjectile(projectile, owner.getMainHandStack());
			}
		}
		return instance.spawnEntity(entity);
	}
}
