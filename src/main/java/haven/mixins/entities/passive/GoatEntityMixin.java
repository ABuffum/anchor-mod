package haven.mixins.entities.passive;

import haven.util.GoatUtils;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.GoatBrain;
import net.minecraft.entity.passive.GoatEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(GoatEntity.class)
public abstract class GoatEntityMixin extends AnimalEntity {
	protected GoatEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) { super(entityType, world); }

	@Inject(method="initialize", at = @At("TAIL"))
	public void Initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt, CallbackInfoReturnable<EntityData> cir) {
		Random random = world.getRandom();
		this.onGrowUp();
		if (!this.isBaby() && (double)random.nextFloat() < (double)0.1f) {
			TrackedData<Boolean> trackedData = random.nextBoolean() ? GoatUtils.LEFT_HORN : GoatUtils.RIGHT_HORN;
			this.dataTracker.set(trackedData, false);
		}
	}

	@Inject(method="initDataTracker", at = @At("TAIL"))
	protected void InitDataTracker(CallbackInfo ci) {
		this.dataTracker.startTracking(GoatUtils.LEFT_HORN, true);
		this.dataTracker.startTracking(GoatUtils.RIGHT_HORN, true);
	}

	@Inject(method="writeCustomDataToNbt", at = @At("TAIL"))
	public void WriteCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
		nbt.putBoolean("HasLeftHorn", GoatUtils.hasLeftHorn(this));
		nbt.putBoolean("HasRightHorn", GoatUtils.hasRightHorn(this));
	}

	@Inject(method="readCustomDataFromNbt", at = @At("TAIL"))
	public void ReadCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
		this.dataTracker.set(GoatUtils.LEFT_HORN, nbt.getBoolean("HasLeftHorn"));
		this.dataTracker.set(GoatUtils.RIGHT_HORN, nbt.getBoolean("HasRightHorn"));
	}
	@Inject(method="onGrowUp", at = @At("TAIL"))
	protected void OnGrowUp(CallbackInfo ci) {
		if (this.isBaby()) GoatUtils.removeHorns(this);
		else GoatUtils.addHorns(this);
	}
}
