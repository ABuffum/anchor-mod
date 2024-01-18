package fun.mousewich.mixins.entity.passive;

import fun.mousewich.ModBase;
import fun.mousewich.entity.ModNbtKeys;
import fun.mousewich.entity.Pouchable;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import fun.mousewich.entity.passive.chicken.FancyChickenEntity;
import fun.mousewich.entity.passive.chicken.SlimeChickenEntity;
import fun.mousewich.gen.data.tag.ModItemTags;
import fun.mousewich.sound.ModSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChickenEntity.class)
public abstract class ChickenEntityMixin extends AnimalEntity implements Pouchable, EntityWithBloodType {
	@Shadow public boolean jockey;
	@Shadow public int eggLayTime;

	protected ChickenEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) { super(entityType, world); }

	@Inject(method="initGoals", at = @At("HEAD"))
	protected void initGoals(CallbackInfo ci) {
		if (getClass().equals(ChickenEntity.class) || getClass().equals(ChickenEntityMixin.class)) {
			this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D, ChickenEntity.class));
			this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D, FancyChickenEntity.class));
			this.goalSelector.add(3, new TemptGoal(this, 1.0D, Ingredient.ofStacks(ModItemTags.SEEDS.values().stream().map(ItemStack::new)), false));
		}
	}

	@Inject(method="isBreedingItem", at = @At("HEAD"), cancellable = true)
	public void isBreedingItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		if (stack.isIn(ModItemTags.SEEDS)) cir.setReturnValue(true);
	}

	@SuppressWarnings("WrongEntityDataParameterClass")
	private static final TrackedData<Boolean> FROM_POUCH = DataTracker.registerData(ChickenEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
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
	public ItemStack getPouchItem() { return new ItemStack(ModBase.CHICKEN_POUCH); }
	@Override
	public boolean isFromPouch() { return this.dataTracker.get(FROM_POUCH); }
	@Override
	public void setFromPouch(boolean fromPouch) { this.dataTracker.set(FROM_POUCH, fromPouch); }
	@Override
	public void copyDataToStack(ItemStack stack) {
		Pouchable.copyDataToStack(this, stack);
		NbtCompound nbt = stack.getOrCreateNbt();
		nbt.putBoolean(ModNbtKeys.IS_CHICKEN_JOCKEY, this.jockey);
		nbt.putInt(ModNbtKeys.EGG_LAY_TIME, this.eggLayTime);
	}
	@Override
	public void copyDataFromNbt(NbtCompound nbt) {
		Pouchable.copyDataFromNbt(this, nbt);
		this.jockey = nbt.getBoolean(ModNbtKeys.IS_CHICKEN_JOCKEY);
		if (nbt.contains(ModNbtKeys.EGG_LAY_TIME)) this.eggLayTime = nbt.getInt(ModNbtKeys.EGG_LAY_TIME);
	}
	@Override
	public SoundEvent getPouchedSound() {
		if (hasCustomName()) {
			String name = getCustomName().getString();
			if (name.equals("Greg")) return ModSoundEvents.ITEM_POUCH_FILL_CHICKEN_GREG;
		}
		return ModSoundEvents.ITEM_POUCH_FILL_CHICKEN;
	}
	@Override
	public boolean cannotDespawn() { return super.cannotDespawn() || this.isFromPouch(); }

	@Inject(method="canImmediatelyDespawn", at=@At("HEAD"), cancellable = true)
	private void dontDespawnIfFromPouch(double distanceSquared, CallbackInfoReturnable<Boolean> cir) {
		if (this.isFromPouch()) cir.setReturnValue(false);
	}
	@Inject(method="writeCustomDataToNbt", at=@At("TAIL"))
	public void WriteCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) { nbt.putBoolean(ModNbtKeys.FROM_POUCH, this.isFromPouch()); }
	@Inject(method="readCustomDataFromNbt", at=@At("TAIL"))
	public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) { this.setFromPouch(nbt.getBoolean(ModNbtKeys.FROM_POUCH)); }

	@Redirect(method="tickMovement", at=@At(value="INVOKE", target="Lnet/minecraft/entity/passive/ChickenEntity;dropItem(Lnet/minecraft/item/ItemConvertible;)Lnet/minecraft/entity/ItemEntity;"))
	public ItemEntity LayDifferentEggs(ChickenEntity instance, ItemConvertible itemConvertible) {
		if (instance instanceof SlimeChickenEntity) return instance.dropItem(Items.SLIME_BALL);
		return instance.dropItem(itemConvertible);
	}

	@Override public BloodType GetDefaultBloodType() { return ModBase.CHICKEN_BLOOD_TYPE; }

	@Override
	public boolean canBreedWith(AnimalEntity other) {
		if (other == this) return false;
		Class<?> otherClass = other.getClass();
		if (otherClass != this.getClass()
				&& otherClass != ChickenEntity.class
				&& otherClass != FancyChickenEntity.class
		) return false;
		else return this.isInLove() && other.isInLove();
	}
}
