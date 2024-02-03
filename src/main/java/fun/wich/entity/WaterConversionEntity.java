package fun.wich.entity;

import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.AbstractSkeletonEntity;

public interface WaterConversionEntity {
	TrackedData<Boolean> SKELETON_CONVERTING_IN_WATER = DataTracker.registerData(AbstractSkeletonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	boolean isConvertingInWater();
	default boolean canConvertInWater() { return true; }
	void convertInWater();
}
