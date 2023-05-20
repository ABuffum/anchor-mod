package fun.mousewich.mixins.entity.hostile;

import fun.mousewich.ModBase;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(IllagerEntity.class)
public abstract class IllagerEntityMixin extends RaiderEntity implements EntityWithBloodType {
	protected IllagerEntityMixin(EntityType<? extends RaiderEntity> entityType, World world) { super(entityType, world); }

	@Override public BloodType GetDefaultBloodType() { return ModBase.VILLAGER_BLOOD_TYPE; }
}
