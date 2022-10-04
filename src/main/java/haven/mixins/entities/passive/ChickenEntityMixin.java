package haven.mixins.entities.passive;

import haven.entities.passive.FancyChickenEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChickenEntity.class)
public abstract class ChickenEntityMixin extends AnimalEntity {
	protected ChickenEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) { super(entityType, world); }

	@Inject(method="initGoals", at = @At("HEAD"))
	protected void initGoals(CallbackInfo ci) {
		if (getClass().equals(ChickenEntity.class) || getClass().equals(ChickenEntityMixin.class)) {
			this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D, ChickenEntity.class));
			this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D, FancyChickenEntity.class));
		}
	}

	@Override
	public boolean canBreedWith(AnimalEntity other) {
		if (other == this) return false;
		Class<?> otherClass = other.getClass();
		if (otherClass != this.getClass()
				&& otherClass != ChickenEntity.class
				&& otherClass != FancyChickenEntity.class
		) return false;
		else return this.isInLove() && other.isInLove();
	}
}
