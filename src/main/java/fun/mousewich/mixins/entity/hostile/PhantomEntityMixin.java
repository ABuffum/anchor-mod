package fun.mousewich.mixins.entity.hostile;

import fun.mousewich.ModBase;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PhantomEntity.class)
public abstract class PhantomEntityMixin extends FlyingEntity implements Monster, EntityWithBloodType {
	protected PhantomEntityMixin(EntityType<? extends FlyingEntity> entityType, World world) { super(entityType, world); }

	@Override public BloodType GetDefaultBloodType() { return ModBase.PHANTOM_BLOOD_TYPE; }
}
