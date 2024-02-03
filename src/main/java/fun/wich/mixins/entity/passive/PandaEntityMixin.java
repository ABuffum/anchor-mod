package fun.wich.mixins.entity.passive;

import fun.wich.ModBase;
import fun.wich.entity.blood.BloodType;
import fun.wich.entity.blood.EntityWithBloodType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PandaEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PandaEntity.class)
public abstract class PandaEntityMixin extends AnimalEntity implements EntityWithBloodType {
	protected PandaEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) { super(entityType, world); }

	@Override public BloodType GetDefaultBloodType() { return ModBase.PANDA_BLOOD_TYPE; }
}
