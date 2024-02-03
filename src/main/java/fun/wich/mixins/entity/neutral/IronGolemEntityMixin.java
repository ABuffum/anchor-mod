package fun.wich.mixins.entity.neutral;

import fun.wich.origins.power.MobHostilityPower;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IronGolemEntity.class)
public abstract class IronGolemEntityMixin extends GolemEntity implements Angerable {
	protected IronGolemEntityMixin(EntityType<? extends GolemEntity> entityType, World world) { super(entityType, world); }

	@Inject(method="initGoals", at = @At("TAIL"))
	protected void initGoals(CallbackInfo ci) {
		this.targetSelector.add(3, MobHostilityPower.makeHostilityGoal(this, 10, true, false, EntityType.IRON_GOLEM));
	}
}
