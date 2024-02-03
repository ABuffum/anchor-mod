package fun.wich.mixins.entity.passive;

import fun.wich.ModBase;
import fun.wich.entity.blood.BloodType;
import fun.wich.entity.blood.EntityWithBloodType;
import fun.wich.item.bucket.BucketProvided;
import fun.wich.sound.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CowEntity.class)
public abstract class CowEntityMixin extends AnimalEntity implements EntityWithBloodType {
	protected CowEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) { super(entityType, world); }

	@Inject(method="getAmbientSound", at = @At("HEAD"), cancellable = true)
	protected void getAmbientSound(CallbackInfoReturnable<SoundEvent> cir) {
		if (getType() == EntityType.MOOSHROOM) cir.setReturnValue(ModSoundEvents.ENTITY_MOOSHROOM_AMBIENT);
	}
	@Inject(method="getHurtSound", at = @At("HEAD"), cancellable = true)
	protected void getHurtSound(DamageSource source, CallbackInfoReturnable<SoundEvent> cir) {
		if (getType() == EntityType.MOOSHROOM) cir.setReturnValue(ModSoundEvents.ENTITY_MOOSHROOM_HURT);
	}
	@Inject(method="getDeathSound", at = @At("HEAD"), cancellable = true)
	protected void getDeathSound(CallbackInfoReturnable<SoundEvent> cir) {
		if (getType() == EntityType.MOOSHROOM) cir.setReturnValue(ModSoundEvents.ENTITY_MOOSHROOM_DEATH);
	}

	@Inject(method="playStepSound", at = @At("HEAD"), cancellable = true)
	protected void playStepSound(BlockPos pos, BlockState state, CallbackInfo ci) {
		if (getType() == EntityType.MOOSHROOM) {
			this.playSound(ModSoundEvents.ENTITY_MOOSHROOM_STEP, 0.15f, 1.0f);
			ci.cancel();
		}
	}

	@Inject(method="interactMob", at = @At("HEAD"), cancellable = true)
	public void InteractMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack != null && !itemStack.isEmpty() && !this.isBaby()) {
			Item item = itemStack.getItem(), outItem = null;
			if (item instanceof BucketProvided bp) outItem = bp.getBucketProvider().getMilkBucket();
			else if (item == Items.BOWL) outItem = ModBase.MILK_BOWL;
			if (outItem != null) {
				player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
				ItemStack itemStack2 = ItemUsage.exchangeStack(itemStack, player, outItem.getDefaultStack());
				player.setStackInHand(hand, itemStack2);
				cir.setReturnValue(ActionResult.success(this.world.isClient));
			}
		}
	}

	@Override public BloodType GetDefaultBloodType() { return ModBase.COW_BLOOD_TYPE; }
}
