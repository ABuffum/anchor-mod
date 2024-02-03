package fun.wich.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.ZombieEntity;

public interface FreezeConversionEntity {
	TrackedData<Boolean> ZOMBIE_CONVERTING_IN_SNOW = DataTracker.registerData(ZombieEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	boolean isConvertingInSnow();
	default boolean canConvertInSnow() { return true; }
	boolean isShaking();
	static boolean InPowderSnow(LivingEntity entity) { return entity.inPowderSnow || entity.wasInPowderSnow; }
}
