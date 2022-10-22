package haven.mixins.entities.monster;

import haven.events.HavenGameEvent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RavagerEntity.class)
public abstract class RavagerEntityMixin extends RaiderEntity {
	protected RavagerEntityMixin(EntityType<? extends RaiderEntity> entityType, World world) { super(entityType, world); }

	@Inject(method="roar", at = @At("TAIL"))
	private void roar(CallbackInfo ci) {
		if (this.isAlive()) this.emitGameEvent(HavenGameEvent.ENTITY_ROAR);
	}
}
