package haven.mixins.entities.monster;

import haven.events.HavenGameEvent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderDragonEntity.class)
public abstract class EnderDragonEntityMixin extends MobEntity implements Monster {
	protected EnderDragonEntityMixin(EntityType<? extends MobEntity> entityType, World world) { super(entityType, world); }

	@Inject(method="kill", at = @At("HEAD"))
	public void Kill(CallbackInfo ci) {
		this.emitGameEvent(HavenGameEvent.ENTITY_DIE);
	}

	@Inject(method="updatePostDeath", at = @At("TAIL"))
	protected void updatePostDeath(CallbackInfo ci) {
		if (((EnderDragonEntity)(Object)this).ticksSinceDeath == 200 && this.world instanceof ServerWorld) {
			this.emitGameEvent(HavenGameEvent.ENTITY_DIE);
		}
	}
}
