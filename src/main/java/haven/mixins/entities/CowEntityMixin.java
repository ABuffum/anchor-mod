package haven.mixins.entities;

import haven.HavenMod;
import haven.entities.passive.cow.CowcoaEntity;
import haven.entities.passive.cow.CowfeeEntity;
import haven.entities.passive.cow.StrawbovineEntity;
import haven.items.buckets.BucketProvided;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CowEntity.class)
public abstract class CowEntityMixin extends AnimalEntity {
	protected CowEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method="initGoals", at = @At("HEAD"))
	protected void initGoals(CallbackInfo ci) {
		if (getClass().equals(CowEntity.class)) {
			this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D, CowEntity.class));
			this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D, CowcoaEntity.class));
			this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D, CowfeeEntity.class));
			this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D, StrawbovineEntity.class));
		}
	}

	@Inject(method="interactMob", at = @At("HEAD"), cancellable = true)
	public void InteractMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack != null && !itemStack.isEmpty() && !this.isBaby()) {
			Item item = itemStack.getItem(), outItem = null;
			if (item instanceof BucketProvided bp) outItem = bp.getBucketProvider().getMilkBucket();
			else if (item == Items.BOWL) outItem = HavenMod.MILK_BOWL;
			if (outItem != null) {
				player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
				ItemStack itemStack2 = ItemUsage.exchangeStack(itemStack, player, outItem.getDefaultStack());
				player.setStackInHand(hand, itemStack2);
				cir.setReturnValue(ActionResult.success(this.world.isClient));
			}
		}
	}

	@Override
	public boolean canBreedWith(AnimalEntity other) {
		if (other == this) return false;
		Class<?> otherClass = other.getClass();
		if (otherClass != this.getClass()
				&& otherClass != CowEntity.class
				&& otherClass != CowcoaEntity.class
				&& otherClass != CowfeeEntity.class
				&& otherClass != StrawbovineEntity.class
		) return false;
		else return this.isInLove() && other.isInLove();
	}
}
