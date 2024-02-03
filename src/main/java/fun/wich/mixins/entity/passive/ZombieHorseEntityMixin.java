package fun.wich.mixins.entity.passive;

import fun.wich.ModBase;
import fun.wich.entity.blood.BloodType;
import fun.wich.entity.blood.EntityWithBloodType;
import fun.wich.sound.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.ZombieHorseEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(ZombieHorseEntity.class)
public abstract class ZombieHorseEntityMixin extends HorseBaseEntity implements EntityWithBloodType {
	private static final UUID HORSE_ARMOR_BONUS_ID = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");
	protected ZombieHorseEntityMixin(EntityType<? extends HorseBaseEntity> entityType, World world) { super(entityType, world); }

	@Override
	protected void playJumpSound() { this.playSound(ModSoundEvents.ENTITY_ZOMBIE_HORSE_JUMP, 0.4f, 1.0f); }

	@Override
	public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
		int i;
		if (fallDistance > 1.0f) this.playSound(ModSoundEvents.ENTITY_ZOMBIE_HORSE_LAND, 0.4f, 1.0f);
		if ((i = this.computeFallDamage(fallDistance, damageMultiplier)) <= 0) return false;
		this.damage(damageSource, i);
		if (this.hasPassengers()) {
			for (Entity entity : this.getPassengersDeep()) entity.damage(damageSource, i);
		}
		this.playBlockFallSound();
		return true;
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		if (!this.items.getStack(1).isEmpty()) {
			nbt.put("ArmorItem", this.items.getStack(1).writeNbt(new NbtCompound()));
		}
	}
	public ItemStack getArmorType() {
		return this.getEquippedStack(EquipmentSlot.CHEST);
	}
	private void equipArmor(ItemStack stack) {
		this.equipStack(EquipmentSlot.CHEST, stack);
		this.setEquipmentDropChance(EquipmentSlot.CHEST, 0.0f);
	}
	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		ItemStack itemStack;
		super.readCustomDataFromNbt(nbt);
		if (nbt.contains("ArmorItem", 10) && !(itemStack = ItemStack.fromNbt(nbt.getCompound("ArmorItem"))).isEmpty() && this.isHorseArmor(itemStack)) {
			this.items.setStack(1, itemStack);
		}
		this.updateSaddle();
	}
	@Override
	protected void updateSaddle() {
		if (this.world.isClient) {
			return;
		}
		super.updateSaddle();
		this.setArmorTypeFromStack(this.items.getStack(1));
		this.setEquipmentDropChance(EquipmentSlot.CHEST, 0.0f);
	}
	private void setArmorTypeFromStack(ItemStack stack) {
		this.equipArmor(stack);
		if (!this.world.isClient) {
			int i;
			this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).removeModifier(HORSE_ARMOR_BONUS_ID);
			if (this.isHorseArmor(stack) && (i = ((HorseArmorItem)stack.getItem()).getBonus()) != 0) {
				this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).addTemporaryModifier(new EntityAttributeModifier(HORSE_ARMOR_BONUS_ID, "Horse armor bonus", (double)i, EntityAttributeModifier.Operation.ADDITION));
			}
		}
	}

	@Inject(method="interactMob", at=@At(value="INVOKE", target="Lnet/minecraft/item/ItemStack;useOnEntity(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;"), cancellable=true)
	private void EquipArmor(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (this.isHorseArmor(itemStack)) {
			this.openInventory(player);
			cir.setReturnValue(ActionResult.success(this.world.isClient));
		}
	}

	@Override
	public void onInventoryChanged(Inventory sender) {
		ItemStack itemStack = this.getArmorType();
		boolean bl = this.isSaddled();
		this.updateSaddle();
		if (this.age > 20 && !bl && this.isSaddled()) this.playSound(ModSoundEvents.ENTITY_ZOMBIE_HORSE_SADDLE, 0.5f, 1.0f);
		ItemStack itemStack2 = this.getArmorType();
		if (this.age > 20 && this.isHorseArmor(itemStack2) && itemStack != itemStack2) {
			this.playSound(SoundEvents.ENTITY_HORSE_ARMOR, 0.5f, 1.0f);
		}
	}
	@Override
	public boolean hasArmorSlot() { return true; }
	@Override
	public boolean isHorseArmor(ItemStack item) { return item.getItem() instanceof HorseArmorItem; }

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		if (state.getMaterial().isLiquid()) return;
		BlockState blockState = this.world.getBlockState(pos.up());
		BlockSoundGroup blockSoundGroup = state.getSoundGroup();
		if (blockState.isOf(Blocks.SNOW)) blockSoundGroup = blockState.getSoundGroup();
		SoundEvent sound = null;
		if (this.hasPassengers() && this.playExtraHorseSounds) {
			++this.soundTicks;
			if (this.soundTicks > 5 && this.soundTicks % 3 == 0) sound = ModSoundEvents.ENTITY_ZOMBIE_HORSE_GALLOP;
			else if (this.soundTicks <= 5) sound = ModSoundEvents.ENTITY_ZOMBIE_HORSE_STEP_WOOD;
		}
		else if (blockSoundGroup == BlockSoundGroup.WOOD) sound = ModSoundEvents.ENTITY_ZOMBIE_HORSE_STEP_WOOD;
		else sound = ModSoundEvents.ENTITY_ZOMBIE_HORSE_STEP;
		if (sound != null) this.playSound(sound, blockSoundGroup.getVolume() * 0.15f, blockSoundGroup.getPitch());
	}

	@Override public BloodType GetDefaultBloodType() { return ModBase.ZOMBIE_HORSE_BLOOD_TYPE; }
}
