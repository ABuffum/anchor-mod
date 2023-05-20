package fun.mousewich.mixins.entity.passive;

import fun.mousewich.ModBase;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CatEntity.class)
public abstract class CatEntityMixin extends TameableEntity implements EntityWithBloodType {
	protected CatEntityMixin(EntityType<? extends TameableEntity> entityType, World world) { super(entityType, world); }

	@Override public BloodType GetDefaultBloodType() { return ModBase.CAT_BLOOD_TYPE; }
}
