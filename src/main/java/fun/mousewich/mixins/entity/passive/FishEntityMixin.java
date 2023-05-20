package fun.mousewich.mixins.entity.passive;

import fun.mousewich.ModBase;
import fun.mousewich.entity.FishBreedableEntity;
import fun.mousewich.entity.ai.goal.FishMateGoal;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import fun.mousewich.entity.passive.frog.TadpoleEntity;
import fun.mousewich.sound.IdentifiedSounds;
import net.minecraft.entity.Bucketable;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(FishEntity.class)
public abstract class FishEntityMixin extends WaterCreatureEntity implements Bucketable, FishBreedableEntity, EntityWithBloodType {
	protected FishEntityMixin(EntityType<? extends FishEntity> entityType, World world) { super(entityType, world); }

	@Inject(method="getSwimSound", at = @At("HEAD"), cancellable = true)
	private void GetSwimSound(CallbackInfoReturnable<SoundEvent> cir) {
		SoundEvent sound = IdentifiedSounds.getSwimSound(this);
		if (sound != null) cir.setReturnValue(sound);
	}

	@Inject(method="initGoals", at = @At("TAIL"))
	private void AddBreedingGoals(CallbackInfo ci) {
		if (ShouldStop()) return;
		this.goalSelector.add(3, new FishMateGoal((FishEntity)(Object)this, 1.0));
	}
	@Override
	public void handleStatus(byte status) {
		if (status == EntityStatuses.ADD_BREEDING_PARTICLES) {
			for (int i = 0; i < 7; ++i) {
				double d = this.random.nextGaussian() * 0.02;
				double e = this.random.nextGaussian() * 0.02;
				double f = this.random.nextGaussian() * 0.02;
				this.world.addParticle(ParticleTypes.HEART, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), d, e, f);
			}
		}
		else super.handleStatus(status);
	}

	@SuppressWarnings("ConstantConditions")
	private boolean ShouldStop() { return ((Object)this) instanceof TadpoleEntity; }

	@Inject(method="writeCustomDataToNbt", at=@At("TAIL"))
	private void WriteCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
		if (ShouldStop()) return;
		nbt.putInt("Age", this.breedingAge);
		nbt.putInt("InLove", this.loveTicks);
		if (this.lovingPlayer != null) {
			nbt.putUuid("LoveCause", this.lovingPlayer);
		}
	}
	@Inject(method="readCustomDataFromNbt", at=@At("TAIL"))
	private void ReadCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
		if (ShouldStop()) return;
		this.setBreedingAge(nbt.getInt("Age"));
		this.loveTicks = nbt.getInt("InLove");
		this.lovingPlayer = nbt.containsUuid("LoveCause") ? nbt.getUuid("LoveCause") : null;
	}

	private UUID lovingPlayer;
	@Override
	public ServerPlayerEntity getLovingPlayer() {
		if (this.lovingPlayer == null) return null;
		if (this.world.getPlayerByUuid(this.lovingPlayer) instanceof ServerPlayerEntity player) return player;
		return null;
	}
	private void lovePlayer(PlayerEntity player) {
		this.loveTicks = 600;
		if (player != null) this.lovingPlayer = player.getUuid();
		this.world.sendEntityStatus(this, EntityStatuses.ADD_BREEDING_PARTICLES);
	}

	@Inject(method="interactMob", at=@At("HEAD"),cancellable = true)
	private void InteractMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(ModBase.CHUM)) {
			int i = this.breedingAge;
			if (!this.world.isClient && i == 0 && this.loveTicks <= 0) {
				if (!player.getAbilities().creativeMode) itemStack.decrement(1);
				this.lovePlayer(player);
				this.emitGameEvent(GameEvent.MOB_INTERACT, this.getCameraBlockPos());
				cir.setReturnValue(ActionResult.SUCCESS);
				return;
			}
			if (this.world.isClient) cir.setReturnValue(ActionResult.CONSUME);
		}
	}

	private int loveTicks;
	@Override
	public boolean isInLove() { return this.loveTicks > 0; }
	@Override
	public void resetLoveTicks() { this.loveTicks = 0; }

	private int breedingAge;
	@Override
	public void setBreedingAge(int age) { this.breedingAge = age; }
	@Override
	public void breed(ServerWorld world, FishEntity other) {
		if (!(other instanceof FishBreedableEntity breedable)) return;
		FishBreedableEntity child = (FishBreedableEntity)this.getType().create(world);
		if (child == null) return;
		ServerPlayerEntity player = this.getLovingPlayer();
		if (player == null) player = breedable.getLovingPlayer();
		if (player != null) player.incrementStat(Stats.ANIMALS_BRED);
		this.setBreedingAge(FishBreedableEntity.BREEDING_COOLDOWN);
		breedable.setBreedingAge(FishBreedableEntity.BREEDING_COOLDOWN);
		this.resetLoveTicks();
		breedable.resetLoveTicks();
		((FishEntity)child).refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), 0.0f, 0.0f);
		world.spawnEntityAndPassengers((FishEntity)child);
		world.sendEntityStatus(this, (byte)18);
		if (world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
			world.spawnEntity(new ExperienceOrbEntity(world, this.getX(), this.getY(), this.getZ(), this.getRandom().nextInt(7) + 1));
		}
	}

	@Override public BloodType GetDefaultBloodType() { return ModBase.FISH_BLOOD_TYPE; }
}
