package fun.mousewich.mixins.entity.neutral;

import fun.mousewich.ModBase;
import net.minecraft.entity.CrossbowUser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.InventoryOwner;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PiglinEntity.class)
public abstract class PiglinEntityMixin extends AbstractPiglinEntity implements CrossbowUser, InventoryOwner {
	public PiglinEntityMixin(EntityType<? extends AbstractPiglinEntity> entityType, World world) { super(entityType, world); }
	
	@Inject(method="dropEquipment", at=@At("TAIL"))
	private void DropHeadFromChargedCreeper(DamageSource source, int lootingMultiplier, boolean allowDrops, CallbackInfo ci) {
		Entity entity = source.getAttacker();
		if (entity instanceof CreeperEntity creeperEntity && creeperEntity.shouldDropHead()) {
			ItemStack itemStack = new ItemStack(ModBase.PIGLIN_HEAD);
			creeperEntity.onHeadDropped();
			this.dropStack(itemStack);
		}
	}
}
