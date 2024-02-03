package fun.wich.mixins.entity.hostile;

import fun.wich.ModBase;
import fun.wich.entity.blood.BloodType;
import fun.wich.entity.blood.EntityWithBloodType;
import fun.wich.entity.hostile.SlimeCreeperEntity;
import net.minecraft.client.render.entity.feature.SkinOverlayOwner;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin extends HostileEntity implements SkinOverlayOwner, EntityWithBloodType {
	protected CreeperEntityMixin(EntityType<? extends HostileEntity> entityType, World world) { super(entityType, world); }

	@Override public BloodType GetDefaultBloodType() { return ModBase.CREEPER_BLOOD_TYPE; }

	@Redirect(method="tick", at=@At(value="INVOKE", target="Lnet/minecraft/entity/mob/CreeperEntity;isAlive()Z"))
	public boolean RedirectIsAlive(CreeperEntity instance) {
		return instance.isAlive() && !(instance instanceof SlimeCreeperEntity);
	}
}
