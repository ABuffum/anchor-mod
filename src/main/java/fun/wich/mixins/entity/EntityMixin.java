package fun.wich.mixins.entity;

import fun.wich.ModBase;
import fun.wich.event.ModGameEvent;
import fun.wich.gen.data.tag.ModBlockTags;
import fun.wich.origins.power.BurnForeverPower;
import fun.wich.origins.power.CrackBlocksPower;
import fun.wich.origins.power.FireImmunePower;
import fun.wich.origins.power.PowersUtil;
import fun.wich.sound.IdentifiedSounds;
import fun.wich.sound.ModSoundEvents;
import fun.wich.sound.SoundUtil;
import fun.wich.util.CrackedBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.entity.EntityLike;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(Entity.class)
public abstract class EntityMixin implements Nameable, EntityLike, CommandOutput {
	@Shadow @Final protected Random random;
	@Shadow public int age;
	@Shadow public World world;
	@Shadow private int lastChimeAge;
	@Shadow private float field_26997;

	@Shadow public abstract void emitGameEvent(GameEvent event, Entity entity, BlockPos pos);
	@Shadow public abstract void emitGameEvent(GameEvent event, Entity entity);
	@Shadow public abstract void emitGameEvent(GameEvent event, BlockPos pos);
	@Shadow public abstract void emitGameEvent(GameEvent event);
	@Shadow public abstract void playSound(SoundEvent sound, float volume, float pitch);
	@Shadow public abstract BlockPos getBlockPos();
	@Shadow public abstract BlockState getBlockStateAtPos();


	@Shadow public float fallDistance;

	@Inject(method="kill", at = @At("TAIL"))
	public void Kill(CallbackInfo ci) { this.emitGameEvent(ModGameEvent.ENTITY_DIE); }

	@Inject(method="playStepSound", at = @At("HEAD"), cancellable = true)
	protected void PlayStepSound(BlockPos pos, BlockState state, CallbackInfo ci) {
		if (state.getMaterial().isLiquid()) return;
		BlockState blockState = this.world.getBlockState(pos.up());
		blockState = blockState.isIn(BlockTags.INSIDE_STEP_SOUND_BLOCKS) || blockState.isIn(BlockTags.CARPETS)
				|| blockState.isOf(Blocks.MOSS_CARPET) || blockState.isOf(ModBase.GLOW_LICHEN_CARPET.asBlock()) ? blockState : state;
		if (blockState.isOf(Blocks.BARRIER) || blockState.isOf(Blocks.STRUCTURE_VOID)) { ci.cancel(); return; }
		SoundUtil.playIdentifiedStepSound((Entity)(Object)this);
		SoundEvent stepSound = IdentifiedSounds.getStepSound(blockState);
		if (stepSound != null) {
			this.playSound(stepSound, 0.15f, 1);
			ci.cancel();
		}
	}
	@Inject(method="getSwimSound", at = @At("HEAD"), cancellable = true)
	protected void GetSwimSound(CallbackInfoReturnable<SoundEvent> cir) {
		SoundEvent swimSound = IdentifiedSounds.getSwimSound((Entity)(Object)this);
		if (swimSound != null) cir.setReturnValue(swimSound);
	}
	@Inject(method="getSplashSound", at = @At("HEAD"), cancellable = true)
	protected void GetSplashSound(CallbackInfoReturnable<SoundEvent> cir) {
		SoundEvent sound = IdentifiedSounds.getSplashSound((Entity)(Object)this);
		if (sound != null) cir.setReturnValue(sound);
	}
	@Inject(method="getHighSpeedSplashSound", at = @At("HEAD"), cancellable = true)
	protected void GetHighSpeedSplashSound(CallbackInfoReturnable<SoundEvent> cir) {
		SoundEvent sound = IdentifiedSounds.getHighSpeedSplashSound((Entity)(Object)this);
		if (sound != null) cir.setReturnValue(sound);
	}
	@Inject(method="addPassenger", at = @At("TAIL"))
	protected void AddPassenger(Entity passenger, CallbackInfo ci) {
		this.emitGameEvent(ModGameEvent.ENTITY_MOUNT, passenger);
	}
	@Inject(method="removePassenger", at = @At("TAIL"))
	protected void RemovePassenger(Entity passenger, CallbackInfo ci) {
		this.emitGameEvent(ModGameEvent.ENTITY_DISMOUNT, passenger);
	}

	@Inject(method="playAmethystChimeSound", at = @At("HEAD"))
	private void playEchoChimeSound(BlockState state, CallbackInfo ci) {
		if (state.isIn(ModBlockTags.ECHO_SOUND_BLOCKS) && this.age >= this.lastChimeAge + 20) {
			this.field_26997 *= (float)Math.pow(0.997, this.age - this.lastChimeAge);
			this.field_26997 = Math.min(1.0f, this.field_26997 + 0.07f);
			float f = 0.5f + this.field_26997 * this.random.nextFloat() * 1.2f;
			float g = 0.1f + this.field_26997 * 1.2f;
			this.playSound(ModSoundEvents.BLOCK_ECHO_BLOCK_CHIME, g, f);
			this.lastChimeAge = this.age;
		}
	}

	@Inject(method="isFireImmune", at=@At("HEAD"), cancellable=true)
	public void IsFireImmune(CallbackInfoReturnable<Boolean> cir) {
		if (PowersUtil.Active((Entity)(Object)this, FireImmunePower.class)) cir.setReturnValue(true);
	}

	@Redirect(method="baseTick", at=@At(value="INVOKE", target="Lnet/minecraft/entity/Entity;setFireTicks(I)V"))
	public void StayBurningBaseTick(Entity instance, int fireTicks) {
		if (PowersUtil.Active(instance, BurnForeverPower.class)) {
			instance.setFireTicks(Math.max(instance.getFireTicks(), fireTicks));
		}
		else instance.setFireTicks(fireTicks);
	}
	@Redirect(method="setOnFireFor", at=@At(value="INVOKE", target="Lnet/minecraft/entity/Entity;setFireTicks(I)V"))
	public void StayBurningSetOnFireFor(Entity instance, int fireTicks) {
		if (fireTicks > 0 && PowersUtil.Active(instance, BurnForeverPower.class)) {
			instance.setFireTicks(Math.max(instance.getFireTicks(), fireTicks));
		}
		else instance.setFireTicks(fireTicks);
	}

	@Inject(method="fall", at=@At(value="INVOKE", shift=At.Shift.AFTER, target="Lnet/minecraft/block/Block;onLandedUpon(Lnet/minecraft/world/World;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/Entity;F)V"))
	public void CrackBlocksOnFall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition, CallbackInfo ci) {
		int distance = CrackBlocksPower.getDistance((Entity)(Object)this);
		if (distance > 0) {
			if (this.fallDistance > distance) CrackedBlocks.Crack(this.world, landedPosition);
		}
	}
}
