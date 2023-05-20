package fun.mousewich.mixins.entity.hostile;

import fun.mousewich.entity.WaterConversionEntity;
import fun.mousewich.sound.ModSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.StrayEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(StrayEntity.class)
public abstract class StrayEntityMixin extends AbstractSkeletonEntity implements WaterConversionEntity {
	protected StrayEntityMixin(EntityType<? extends AbstractSkeletonEntity> entityType, World world) { super(entityType, world); }
	public void convertInWater() {
		convertTo(EntityType.SKELETON, true);
		if (!this.isSilent()) {
			this.world.playSound(null, this.getBlockPos(), ModSoundEvents.ENTITY_STRAY_CONVERTED_TO_SKELETON, SoundCategory.HOSTILE, 2.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f);
		}
	}
}
