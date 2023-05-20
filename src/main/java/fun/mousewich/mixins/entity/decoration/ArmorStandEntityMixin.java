package fun.mousewich.mixins.entity.decoration;

import fun.mousewich.event.ModGameEvent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorStandEntity.class)
public abstract class ArmorStandEntityMixin extends LivingEntity {
	protected ArmorStandEntityMixin(EntityType<? extends LivingEntity> entityType, World world) { super(entityType, world); }

	@Inject(method="kill", at = @At("HEAD"))
	public void Kill(CallbackInfo ci) { this.emitGameEvent(ModGameEvent.ENTITY_DIE); }
}
