package fun.mousewich.mixins.entity.passive;

import fun.mousewich.ModBase;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemSteerable;
import net.minecraft.entity.Saddleable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.StriderEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(StriderEntity.class)
public abstract class StriderEntityMixin extends AnimalEntity implements ItemSteerable, Saddleable, EntityWithBloodType {
	protected StriderEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) { super(entityType, world); }

	@Override public BloodType GetDefaultBloodType() { return ModBase.STRIDER_BLOOD_TYPE; }
}
