package fun.mousewich.mixins.entity.passive;

import fun.mousewich.ModBase;
import fun.mousewich.entity.Pouchable;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import fun.mousewich.sound.ModSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RabbitEntity.class)
public abstract class RabbitEntityMixin extends AnimalEntity implements Pouchable, EntityWithBloodType {
	@Shadow public abstract int getRabbitType();
	@Shadow int moreCarrotTicks;
	@Shadow public abstract void setRabbitType(int rabbitType);

	protected RabbitEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
	}


	@SuppressWarnings("WrongEntityDataParameterClass")
	private static final TrackedData<Boolean> FROM_POUCH = DataTracker.registerData(RabbitEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	@Inject(method="initDataTracker", at=@At("TAIL"))
	protected void addPouchToDataTracker(CallbackInfo ci) {
		this.dataTracker.startTracking(FROM_POUCH, false);
	}
	@Override
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		return Pouchable.tryPouch(player, hand, this).orElse(super.interactMob(player, hand));
	}
	@Override
	public ItemStack getPouchItem() { return new ItemStack(ModBase.RABBIT_POUCH); }
	@Override
	public boolean isFromPouch() { return this.dataTracker.get(FROM_POUCH); }
	@Override
	public void setFromPouch(boolean fromPouch) { this.dataTracker.set(FROM_POUCH, fromPouch); }
	@Override
	public void copyDataToStack(ItemStack stack) {
		Pouchable.copyDataToStack(this, stack);
		NbtCompound nbt = stack.getOrCreateNbt();
		nbt.putInt("RabbitType", this.getRabbitType());
		nbt.putInt("MoreCarrotTicks", this.moreCarrotTicks);
	}
	@Override
	public void copyDataFromNbt(NbtCompound nbt) {
		Pouchable.copyDataFromNbt(this, nbt);
		this.setRabbitType(nbt.getInt("RabbitType"));
		this.moreCarrotTicks = nbt.getInt("MoreCarrotTicks");
	}
	@Override
	public SoundEvent getPouchedSound() { return ModSoundEvents.ITEM_POUCH_FILL_RABBIT; }
	@Override
	public boolean cannotDespawn() { return super.cannotDespawn() || this.isFromPouch(); }
	@Override
	public boolean canImmediatelyDespawn(double distanceSquared) { return !this.isFromPouch() && !this.hasCustomName(); }
	@Inject(method="writeCustomDataToNbt", at=@At("TAIL"))
	public void WriteCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) { nbt.putBoolean("FromPouch", this.isFromPouch()); }
	@Inject(method="readCustomDataFromNbt", at=@At("TAIL"))
	public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) { this.setFromPouch(nbt.getBoolean("FromPouch")); }

	@Override public BloodType GetDefaultBloodType() { return ModBase.RABBIT_BLOOD_TYPE; }
}
