package fun.wich.mixins.entity.hostile;

import fun.wich.ModBase;
import fun.wich.entity.WaterConversionEntity;
import fun.wich.entity.hostile.skeleton.SunkenSkeletonEntity;
import fun.wich.entity.variants.SunkenSkeletonVariant;
import fun.wich.sound.ModSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractSkeletonEntity.class)
public abstract class AbstractSkeletonEntityMixin extends HostileEntity implements RangedAttackMob, WaterConversionEntity {
	private int inWaterTime;
	private int ticksUntilWaterConversion;
	protected AbstractSkeletonEntityMixin(EntityType<? extends HostileEntity> entityType, World world) { super(entityType, world); }
	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.getDataTracker().startTracking(SKELETON_CONVERTING_IN_WATER, false);
	}
	@Override
	public boolean isConvertingInWater() { return this.getDataTracker().get(SKELETON_CONVERTING_IN_WATER); }
	@Override
	public boolean canConvertInWater() { return true; }

	@Inject(method="isShaking", at=@At("HEAD"), cancellable = true)
	public void IsShaking(CallbackInfoReturnable<Boolean> cir) { if (this.isConvertingInWater()) cir.setReturnValue(true); }

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		nbt.putInt("InWaterTime", this.isTouchingWater() ? this.inWaterTime : -1);
		nbt.putInt("WaterConversionTime", this.isConvertingInWater() ? this.ticksUntilWaterConversion : -1);
	}

	@Inject(method="readCustomDataFromNbt", at=@At("TAIL"))
	public void ReadCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
		this.inWaterTime = nbt.getInt("InWaterTime");
		if (nbt.contains("WaterConversionTime", 99) && nbt.getInt("WaterConversionTime") > -1) {
			this.setTicksUntilWaterConversion(nbt.getInt("WaterConversionTime"));
		}
	}

	private void setTicksUntilWaterConversion(int ticksUntilConversion) {
		this.ticksUntilWaterConversion = ticksUntilConversion;
		this.getDataTracker().set(SKELETON_CONVERTING_IN_WATER, true);
	}

	@Override
	public void tick() {
		if (!this.world.isClient && this.isAlive() && !this.isAiDisabled()) {
			if (this.isConvertingInWater()) {
				--this.ticksUntilWaterConversion;
				if (this.ticksUntilWaterConversion < 0) this.convertInWater();
			}
			else if (this.canConvertInWater()) {
				BlockPos pos = this.getBlockPos();
				if (this.isSubmergedIn(FluidTags.WATER) && !this.world.getBiome(pos).isCold(pos)) {
					++this.inWaterTime;
					if (this.inWaterTime >= 140) this.setTicksUntilWaterConversion(300);
				}
				else this.inWaterTime = -1;
			}
		}
		super.tick();
	}

	public void convertInWater() {
		SunkenSkeletonEntity entity = convertTo(ModBase.SUNKEN_SKELETON_ENTITY, true);
		if (entity != null) entity.setVariant(random.nextInt(SunkenSkeletonVariant.values().length));
		if (!this.isSilent()) {
			this.world.playSound(null, this.getBlockPos(), ModSoundEvents.ENTITY_SKELETON_CONVERTED_TO_SUNKEN_SKELETON, SoundCategory.HOSTILE, 2.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f);
		}
	}
}
