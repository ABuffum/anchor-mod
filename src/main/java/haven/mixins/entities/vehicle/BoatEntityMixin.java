package haven.mixins.entities.vehicle;

import haven.events.HavenGameEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoatEntity.class)
public abstract class BoatEntityMixin extends Entity {
	public BoatEntityMixin(EntityType<?> type, World world) { super(type, world); }

	@Inject(method="damage", at = @At("HEAD"))
	public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		if (this.isInvulnerableTo(source) || this.world.isClient || this.isRemoved()) return;
		this.emitGameEvent(HavenGameEvent.ENTITY_DAMAGE, source.getAttacker());
	}
}
