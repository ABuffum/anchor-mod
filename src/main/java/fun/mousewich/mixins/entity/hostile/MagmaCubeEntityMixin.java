package fun.mousewich.mixins.entity.hostile;

import fun.mousewich.ModBase;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MagmaCubeEntity.class)
public abstract class MagmaCubeEntityMixin extends SlimeEntity implements EntityWithBloodType {
	public MagmaCubeEntityMixin(EntityType<? extends SlimeEntity> entityType, World world) { super(entityType, world); }

	@Override public BloodType GetDefaultBloodType() { return ModBase.MAGMA_CREAM_BLOOD_TYPE; }
}
