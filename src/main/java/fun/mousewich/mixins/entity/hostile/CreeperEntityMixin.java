package fun.mousewich.mixins.entity.hostile;

import fun.mousewich.ModBase;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import net.minecraft.client.render.entity.feature.SkinOverlayOwner;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin extends HostileEntity implements SkinOverlayOwner, EntityWithBloodType {
	protected CreeperEntityMixin(EntityType<? extends HostileEntity> entityType, World world) { super(entityType, world); }

	@Override public BloodType GetDefaultBloodType() { return ModBase.CREEPER_BLOOD_TYPE; }
}
