package fun.wich.mixins.entity.vehicle;

import net.minecraft.entity.vehicle.BoatEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BoatEntity.class)
public interface BoatEntityAccessor {
	@Accessor("fallVelocity")
	double getFallVelocity();
	@Accessor("fallVelocity")
	void setFallVelocity(double value);
	@Accessor("location")
	BoatEntity.Location getLocation();
	@Accessor("location")
	void setLocation(BoatEntity.Location value);
	@Accessor("yawVelocity")
	float getYawVelocity();
	@Accessor("lastLocation")
	void setLastLocation(BoatEntity.Location location);
	@Accessor("ticksUnderwater")
	float getTicksUnderwater();
	@Accessor("ticksUnderwater")
	void setTicksUnderwater(float value);
	@Accessor("field_7708")
	int getField_7708();
	@Accessor("field_7708")
	void setField_7708(int value);
	@Accessor("boatYaw")
	double getBoatYaw();
	@Accessor("boatYaw")
	void setBoatYaw(double value);
	@Accessor("boatPitch")
	double getBoatPitch();
	@Accessor("boatPitch")
	void setBoatPitch(double value);

	@Accessor("x")
	double getBoatX();
	@Accessor("x")
	void setBoatX(double value);
	@Accessor("y")
	double getBoatY();
	@Accessor("y")
	void setBoatY(double value);
	@Accessor("z")
	double getBoatZ();
	@Accessor("z")
	void setBoatZ(double value);
	@Accessor("paddlePhases")
	float[] getPaddlePhases();

	@Invoker("checkLocation")
	BoatEntity.Location CheckLocation();
	@Invoker("updateVelocity")
	void UpdateVelocity();
	@Invoker("updatePaddles")
	void UpdatePaddles();
	@Invoker("handleBubbleColumn")
	void HandleBubbleColumn();
}
