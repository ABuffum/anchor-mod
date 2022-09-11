package haven.mixins.entities;

import net.minecraft.entity.vehicle.BoatEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BoatEntity.class)
public interface BoatEntityAccessor {
	@Accessor("fallVelocity")
	public double getFallVelocity();
	@Accessor("fallVelocity")
	public void setFallVelocity(double value);
	@Accessor("location")
	public BoatEntity.Location getLocation();
	@Accessor("location")
	public void setLocation(BoatEntity.Location value);
}
