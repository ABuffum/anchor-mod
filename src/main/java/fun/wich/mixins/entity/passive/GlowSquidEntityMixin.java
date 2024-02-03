package fun.wich.mixins.entity.passive;

import fun.wich.ModBase;
import fun.wich.entity.blood.BloodType;
import fun.wich.entity.blood.EntityWithBloodType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.GlowSquidEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GlowSquidEntity.class)
public abstract class GlowSquidEntityMixin extends WaterCreatureEntity implements EntityWithBloodType {
	protected GlowSquidEntityMixin(EntityType<? extends WaterCreatureEntity> entityType, World world) { super(entityType, world); }

	@Override public BloodType GetDefaultBloodType() { return ModBase.GLOW_SQUID_BLOOD_TYPE; }
}
