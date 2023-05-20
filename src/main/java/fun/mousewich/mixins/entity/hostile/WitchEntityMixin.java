package fun.mousewich.mixins.entity.hostile;

import fun.mousewich.ModBase;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(WitchEntity.class)
public abstract class WitchEntityMixin extends RaiderEntity implements RangedAttackMob, EntityWithBloodType {
	protected WitchEntityMixin(EntityType<? extends RaiderEntity> entityType, World world) { super(entityType, world); }

	@Override public BloodType GetDefaultBloodType() { return ModBase.VILLAGER_BLOOD_TYPE; }
}
