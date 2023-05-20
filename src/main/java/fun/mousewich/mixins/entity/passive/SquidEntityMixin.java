package fun.mousewich.mixins.entity.passive;

import fun.mousewich.ModBase;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SquidEntity.class)
public abstract class SquidEntityMixin extends WaterCreatureEntity implements EntityWithBloodType {
	protected SquidEntityMixin(EntityType<? extends WaterCreatureEntity> entityType, World world) { super(entityType, world); }

	@Override public BloodType GetDefaultBloodType() { return ModBase.SQUID_BLOOD_TYPE; }
}
