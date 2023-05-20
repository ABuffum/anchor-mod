package fun.mousewich.mixins.entity.hostile;

import fun.mousewich.ModBase;
import fun.mousewich.entity.FreezeConversionEntity;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZombieEntity.class)
public abstract class ZombieEntityMixin extends HostileEntity implements FreezeConversionEntity, EntityWithBloodType {
	@Shadow protected abstract void convertTo(EntityType<? extends ZombieEntity> entityType);
	private int inPowderSnowTime;
	private int ticksUntilSnowConversion;

	protected ZombieEntityMixin(EntityType<? extends HostileEntity> entityType, World world) { super(entityType, world); }
	@Inject(method="initDataTracker", at=@At("TAIL"))
	protected void InitDataTracker(CallbackInfo ci) { this.getDataTracker().startTracking(ZOMBIE_CONVERTING_IN_SNOW, false); }
	public boolean isConvertingInSnow() { return this.getDataTracker().get(ZOMBIE_CONVERTING_IN_SNOW); }
	@Override public boolean isShaking() { return this.isConvertingInSnow(); }
	@Override
	public boolean canFreeze() { return false; }

	@Inject(method="writeCustomDataToNbt", at=@At("TAIL"))
	public void WriteCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
		nbt.putInt("InPowderSnowTime", FreezeConversionEntity.InPowderSnow(this) ? this.inPowderSnowTime : -1);
		nbt.putInt("FrozenConversionTime", this.isConvertingInSnow() ? this.ticksUntilSnowConversion : -1);
	}

	@Inject(method="readCustomDataFromNbt", at=@At("TAIL"))
	public void ReadCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
		this.inPowderSnowTime = nbt.getInt("InPowderSnowTime");
		if (nbt.contains("FrozenConversionTime", 99) && nbt.getInt("FrozenConversionTime") > -1) {
			this.setTicksUntilSnowConversion(nbt.getInt("FrozenConversionTime"));
		}
	}

	private void setTicksUntilSnowConversion(int ticksUntilConversion) {
		this.ticksUntilSnowConversion = ticksUntilConversion;
		this.getDataTracker().set(ZOMBIE_CONVERTING_IN_SNOW, true);
	}

	@Inject(method="tick", at=@At("HEAD"))
	public void Tick(CallbackInfo ci) {
		if (!this.world.isClient && this.isAlive() && !this.isAiDisabled()) {
			if (this.isConvertingInSnow()) {
				--this.ticksUntilSnowConversion;
				if (this.ticksUntilSnowConversion < 0) this.convertInSnow();
			}
			else if (this.canConvertInSnow()) {
				if (FreezeConversionEntity.InPowderSnow(this)) {
					++this.inPowderSnowTime;
					if (this.inPowderSnowTime >= 140) this.setTicksUntilSnowConversion(300);
				}
				else this.inPowderSnowTime = -1;
			}
		}
	}

	protected void convertInSnow() {
		convertTo(ModBase.FROZEN_ZOMBIE_ENTITY);
		if (!this.isSilent()) {
			//this.world.syncWorldEvent(null, WorldEvents.ZOMBIE_CONVERTS_TO_DROWNED, this.getBlockPos(), 0);
			this.world.playSound(null, this.getBlockPos(), SoundEvents.ENTITY_ZOMBIE_CONVERTED_TO_DROWNED, SoundCategory.HOSTILE, 2.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f);
		}
	}

	@Override public BloodType GetDefaultBloodType() { return ModBase.ZOMBIE_BLOOD_TYPE; }
}
