package haven.mixins.entities.passive;

import haven.ModTags;
import haven.entities.passive.FancyChickenEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChickenEntity.class)
public abstract class ChickenEntityMixin extends AnimalEntity {
	protected ChickenEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) { super(entityType, world); }

	@Inject(method="initGoals", at = @At("HEAD"))
	protected void initGoals(CallbackInfo ci) {
		if (getClass().equals(ChickenEntity.class) || getClass().equals(ChickenEntityMixin.class)) {
			this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D, ChickenEntity.class));
			this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D, FancyChickenEntity.class));
			this.goalSelector.add(3, new TemptGoal(this, 1.0D, Ingredient.ofStacks(ModTags.Items.SEEDS.values().stream().map(ItemStack::new)), false));
		}
	}

	@Inject(method="isBreedingItem", at = @At("HEAD"), cancellable = true)
	public void isBreedingItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		if (stack.isIn(ModTags.Items.SEEDS)) cir.setReturnValue(true);
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
