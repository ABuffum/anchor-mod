package fun.wich.mixins.entity.hostile;

import fun.wich.ModBase;
import fun.wich.entity.ModNbtKeys;
import fun.wich.entity.Pouchable;
import fun.wich.entity.blood.BloodType;
import fun.wich.entity.blood.EntityWithBloodType;
import fun.wich.entity.passive.HedgehogEntity;
import fun.wich.registry.ModEntityRegistry;
import fun.wich.sound.ModSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.EndermiteEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndermiteEntity.class)
public abstract class EndermiteEntityMixin extends HostileEntity implements EntityWithBloodType, Pouchable {
	protected EndermiteEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "initGoals", at = @At("TAIL"))
	private void addGoals(CallbackInfo info) {
		Goal fleeGoal = new FleeEntityGoal<>(this, HedgehogEntity.class, 16.0F, 1.6D, 1.4D);
		this.goalSelector.add(3, fleeGoal);
	}

	@SuppressWarnings("WrongEntityDataParameterClass")
	private static final TrackedData<Boolean> FROM_POUCH = DataTracker.registerData(RabbitEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(FROM_POUCH, false);
	}
	@Override
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		return Pouchable.tryPouch(player, hand, this).orElse(super.interactMob(player, hand));
	}
	@Override
	public ItemStack getPouchItem() { return new ItemStack(ModEntityRegistry.ENDERMITE_POUCH); }
	@Override
	public boolean isFromPouch() { return this.dataTracker.get(FROM_POUCH); }
	@Override
	public void setFromPouch(boolean fromPouch) { this.dataTracker.set(FROM_POUCH, fromPouch); }
	@Override
	public void copyDataToStack(ItemStack stack) { Pouchable.copyDataToStack(this, stack); }
	@Override
	public void copyDataFromNbt(NbtCompound nbt) { Pouchable.copyDataFromNbt(this, nbt); }
	@Override
	public SoundEvent getPouchedSound() { return ModSoundEvents.ITEM_POUCH_FILL_ENDERMITE; }
	@Override
	public boolean cannotDespawn() { return super.cannotDespawn() || this.isFromPouch(); }
	@Override
	public boolean canImmediatelyDespawn(double distanceSquared) { return !this.isFromPouch() && !this.hasCustomName(); }
	@Inject(method="writeCustomDataToNbt", at=@At("TAIL"))
	public void WriteCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) { nbt.putBoolean(ModNbtKeys.FROM_POUCH, this.isFromPouch()); }
	@Inject(method="readCustomDataFromNbt", at=@At("TAIL"))
	public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) { this.setFromPouch(nbt.getBoolean(ModNbtKeys.FROM_POUCH)); }
	@Override public BloodType GetDefaultBloodType() { return ModBase.ENDERMITE_BLOOD_TYPE; }
}
