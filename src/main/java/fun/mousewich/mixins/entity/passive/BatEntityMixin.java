package fun.mousewich.mixins.entity.passive;

import fun.mousewich.ModBase;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BatEntity.class)
public abstract class BatEntityMixin extends AmbientEntity implements EntityWithBloodType {
	protected BatEntityMixin(EntityType<? extends AmbientEntity> entityType, World world) { super(entityType, world); }

	@Override public BloodType GetDefaultBloodType() { return ModBase.BAT_BLOOD_TYPE; }
}
