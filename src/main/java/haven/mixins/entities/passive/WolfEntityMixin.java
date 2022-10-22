package haven.mixins.entities.passive;

import haven.events.HavenGameEvent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WolfEntity.class)
public abstract class WolfEntityMixin extends TameableEntity implements Angerable {
	protected WolfEntityMixin(EntityType<? extends TameableEntity> entityType, World world) { super(entityType, world); }

	@Shadow
	private boolean furWet;
	@Shadow
	private boolean canShakeWaterOff;
	@Shadow
	private float shakeProgress;

	@Inject(method="tick", at = @At("HEAD"))
	public void tick(CallbackInfo ci) {
		if (this.isAlive() && !this.isWet()) {
			if ((this.furWet || this.canShakeWaterOff) && this.canShakeWaterOff) {
				if (this.shakeProgress == 0.0f) this.emitGameEvent(HavenGameEvent.ENTITY_SHAKE);
			}
		}
	}
}
