package fun.mousewich.mixins.entity.hostile;

import fun.mousewich.ModBase;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.Hoglin;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.ZoglinEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ZoglinEntity.class)
public abstract class ZoglinEntityMixin extends HostileEntity implements Monster, Hoglin, EntityWithBloodType {
	protected ZoglinEntityMixin(EntityType<? extends HostileEntity> entityType, World world) { super(entityType, world); }

	@Override public BloodType GetDefaultBloodType() { return ModBase.ZOGLIN_BLOOD_TYPE; }
}
