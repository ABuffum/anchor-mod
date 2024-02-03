package fun.wich.mixins.entity.passive;

import fun.wich.entity.blood.BloodType;
import fun.wich.entity.blood.EntityWithBloodType;
import fun.wich.sound.IdentifiedSounds;
import fun.wich.sound.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.SkeletonHorseEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SkeletonHorseEntity.class)
public abstract class SkeletonHorseEntityMixin extends HorseBaseEntity implements EntityWithBloodType {
	protected SkeletonHorseEntityMixin(EntityType<? extends HorseBaseEntity> entityType, World world) { super(entityType, world); }

	@Inject(method="playJumpSound", at = @At("HEAD"), cancellable = true)
	protected void PlayJumpSound(CallbackInfo ci) {
		if (!this.isTouchingWater()) {
			this.playSound(ModSoundEvents.ENTITY_SKELETON_HORSE_JUMP, 0.4f, 1.0f);
			ci.cancel();
		}
	}

	@Override
	public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
		int i;
		if (fallDistance > 1.0f) this.playSound(ModSoundEvents.ENTITY_SKELETON_HORSE_LAND, 0.4f, 1.0f);
		if ((i = this.computeFallDamage(fallDistance, damageMultiplier)) <= 0) return false;
		this.damage(damageSource, i);
		if (this.hasPassengers()) {
			for (Entity entity : this.getPassengersDeep()) entity.damage(damageSource, i);
		}
		this.playBlockFallSound();
		return true;
	}

	@Override
	public void onInventoryChanged(Inventory sender) {
		boolean bl = this.isSaddled();
		this.updateSaddle();
		if (this.age > 20 && !bl && this.isSaddled()) this.playSound(ModSoundEvents.ENTITY_SKELETON_HORSE_SADDLE, 0.5f, 1.0f);
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		if (state.getMaterial().isLiquid()) return;
		BlockState blockState = this.world.getBlockState(pos.up());
		BlockSoundGroup blockSoundGroup = state.getSoundGroup();
		if (blockState.isOf(Blocks.SNOW)) blockSoundGroup = blockState.getSoundGroup();
		SoundEvent sound = null;
		if (this.hasPassengers() && this.playExtraHorseSounds) {
			++this.soundTicks;
			if (this.soundTicks > 5 && this.soundTicks % 3 == 0) sound = ModSoundEvents.ENTITY_SKELETON_HORSE_GALLOP;
			else if (this.soundTicks <= 5) sound = ModSoundEvents.ENTITY_SKELETON_HORSE_STEP_WOOD;
		}
		else if (blockSoundGroup == BlockSoundGroup.WOOD) sound = ModSoundEvents.ENTITY_SKELETON_HORSE_STEP_WOOD;
		else sound = ModSoundEvents.ENTITY_SKELETON_HORSE_STEP;
		if (sound != null) this.playSound(sound, blockSoundGroup.getVolume() * 0.15f, blockSoundGroup.getPitch());
	}

	@Inject(method="getSwimSound", at = @At("HEAD"), cancellable = true)
	protected void GetSwimSound(CallbackInfoReturnable<SoundEvent> cir) {
		if (!this.onGround) {
			SoundEvent sound = IdentifiedSounds.getSwimSound(this);
			if (sound != null) cir.setReturnValue(sound);
		}
	}

	@Override public BloodType GetDefaultBloodType() { return BloodType.NONE; }
}
