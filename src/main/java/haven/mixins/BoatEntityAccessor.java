package haven.mixins;

import net.minecraft.entity.vehicle.BoatEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BoatEntity.class)
public interface BoatEntityAccessor {
	@Accessor("fallVelocity")
	public double getFallVelocity();
	@Accessor("fallVelocity")
	public void setFallVelocity(double value);
	@Accessor("location")
	public BoatEntity.Location getLocation();
}
