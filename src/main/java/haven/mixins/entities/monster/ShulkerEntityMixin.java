package haven.mixins.entities.monster;

import haven.events.HavenGameEvent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShulkerEntity.class)
public abstract class ShulkerEntityMixin extends GolemEntity implements Monster {
	protected ShulkerEntityMixin(EntityType<? extends GolemEntity> entityType, World world) { super(entityType, world); }

	@Inject(method="tryTeleport", at = @At(value="INVOKE", target="Lnet/minecraft/entity/mob/ShulkerEntity;playSound(Lnet/minecraft/sound/SoundEvent;FF)V"))
	protected void tryTeleport(CallbackInfoReturnable<Boolean> cir) {
		this.world.emitGameEvent(this, HavenGameEvent.TELEPORT, getBlockPos());
	}
}
