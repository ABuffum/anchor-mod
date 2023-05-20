package fun.mousewich.mixins.entity.neutral;

import fun.mousewich.ModBase;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import fun.mousewich.entity.variants.GoatVariant;
import fun.mousewich.util.GoatUtil;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.GoatEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.DyeColor;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(GoatEntity.class)
public abstract class GoatEntityMixin extends AnimalEntity implements EntityWithBloodType {
	protected GoatEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) { super(entityType, world); }

	@Inject(method="initialize", at = @At("TAIL"))
	public void Initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt, CallbackInfoReturnable<EntityData> cir) {
		Random random = world.getRandom();
		this.onGrowUp();
		if (!this.isBaby() && (double)random.nextFloat() < (double)0.1f) {
			TrackedData<Boolean> trackedData = random.nextBoolean() ? GoatUtil.LEFT_HORN : GoatUtil.RIGHT_HORN;
			this.dataTracker.set(trackedData, false);
		}
	}

	@Inject(method="initDataTracker", at = @At("TAIL"))
	protected void AddHornsAndVariantToDataTracker(CallbackInfo ci) {
		this.dataTracker.startTracking(GoatUtil.LEFT_HORN, true);
		this.dataTracker.startTracking(GoatUtil.RIGHT_HORN, true);
		GoatVariant variant = GoatVariant.DEFAULT;
		if (random.nextInt(10) == 1) variant = GoatVariant.DARK;
		this.dataTracker.startTracking(GoatVariant.VARIANT, variant.ordinal());
	}

	@Inject(at=@At(value="RETURN"), method="createChild(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/passive/PassiveEntity;)Lnet/minecraft/entity/passive/GoatEntity;")
	public void CreateChild(ServerWorld serverWorld, PassiveEntity passiveEntity, CallbackInfoReturnable<GoatEntity> cir) {
		GoatEntity child = cir.getReturnValue();
		if (child != null) {
			GoatVariant.setVariant(child, GoatVariant.getVariant(random.nextBoolean() ? (GoatEntity)(Object)this : (GoatEntity)passiveEntity));
		}
	}

	@Inject(method="writeCustomDataToNbt", at = @At("TAIL"))
	public void WriteCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
		nbt.putBoolean("HasLeftHorn", GoatUtil.hasLeftHorn(this));
		nbt.putBoolean("HasRightHorn", GoatUtil.hasRightHorn(this));
		nbt.putInt("Variant", this.getDataTracker().get(GoatVariant.VARIANT));
	}

	@Inject(method="readCustomDataFromNbt", at = @At("TAIL"))
	public void ReadCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
		this.dataTracker.set(GoatUtil.LEFT_HORN, nbt.getBoolean("HasLeftHorn"));
		this.dataTracker.set(GoatUtil.RIGHT_HORN, nbt.getBoolean("HasRightHorn"));
		this.dataTracker.set(GoatVariant.VARIANT, nbt.getInt("Variant"));
	}
	@Inject(method="onGrowUp", at = @At("TAIL"))
	protected void OnGrowUp(CallbackInfo ci) {
		if (this.isBaby()) GoatUtil.removeHorns(this);
		else GoatUtil.addHorns(this);
	}

	@Override
	public void onDeath(DamageSource source) {
		super.onDeath(source);
		GoatVariant variant = GoatVariant.get((GoatEntity)(Object)this);
		Item item;
		if (variant == GoatVariant.DARK) item = ModBase.FLEECE.get(DyeColor.BLACK).asItem();
		else item = ModBase.FLEECE.get(DyeColor.WHITE).asItem();
		this.dropItem(item);
	}

	@Override public BloodType GetDefaultBloodType() { return ModBase.GOAT_BLOOD_TYPE; }
}
