package fun.mousewich.mixins.entity.passive;

import fun.mousewich.ModBase;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LlamaEntity.class)
public abstract class LlamaEntityMixin extends AbstractDonkeyEntity implements RangedAttackMob, EntityWithBloodType {
	protected LlamaEntityMixin(EntityType<? extends AbstractDonkeyEntity> entityType, World world) { super(entityType, world); }

	@Override public BloodType GetDefaultBloodType() { return ModBase.LLAMA_BLOOD_TYPE; }
}
