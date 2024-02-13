package fun.wich.mixins.entity.passive;

import fun.wich.ModBase;
import fun.wich.entity.blood.BloodType;
import fun.wich.entity.blood.EntityWithBloodType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PolarBearEntity.class)
public abstract class PolarBearEntityMixin extends AnimalEntity implements Angerable, EntityWithBloodType {
	protected PolarBearEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) { super(entityType, world); }

	@Override public BloodType GetDefaultBloodType() { return ModBase.BEAR_BLOOD_TYPE; }
}
