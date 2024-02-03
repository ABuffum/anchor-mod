package fun.wich.entity;

import fun.wich.ModBase;
import fun.wich.sound.ModSoundEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.Optional;

public interface Pouchable {
	boolean isFromPouch();
	void setFromPouch(boolean fromPouch);
	void copyDataToStack(ItemStack stack);
	void copyDataFromNbt(NbtCompound nbt);
	ItemStack getPouchItem();
	default SoundEvent getPouchedSound() { return ModSoundEvents.ITEM_POUCH_FILL; }
	@Deprecated
	static void copyDataToStack(MobEntity entity, ItemStack stack) {
		NbtCompound nbt = stack.getOrCreateNbt();
		if (entity.hasCustomName()) stack.setCustomName(entity.getCustomName());
		if (entity.isAiDisabled()) nbt.putBoolean("NoAI", entity.isAiDisabled());
		if (entity.isSilent()) nbt.putBoolean("Silent", entity.isSilent());
		if (entity.hasNoGravity()) nbt.putBoolean("NoGravity", entity.hasNoGravity());
		if (entity.isGlowingLocal()) nbt.putBoolean("Glowing", entity.isGlowingLocal());
		if (entity.isInvulnerable()) nbt.putBoolean("Invulnerable", entity.isInvulnerable());
		nbt.putFloat("Health", entity.getHealth());
	}
	@Deprecated
	static void copyDataFromNbt(MobEntity entity, NbtCompound nbt) {
		if (nbt.contains("NoAI")) entity.setAiDisabled(nbt.getBoolean("NoAI"));
		if (nbt.contains("Silent")) entity.setSilent(nbt.getBoolean("Silent"));
		if (nbt.contains("NoGravity")) entity.setNoGravity(nbt.getBoolean("NoGravity"));
		if (nbt.contains("Glowing")) entity.setGlowing(nbt.getBoolean("Glowing"));
		if (nbt.contains("Invulnerable")) entity.setInvulnerable(nbt.getBoolean("Invulnerable"));
		if (nbt.contains("Health", 99)) entity.setHealth(nbt.getFloat("Health"));
	}
	static <T extends LivingEntity> Optional<ActionResult> tryPouch(PlayerEntity player, Hand hand, T entity) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.getItem() == ModBase.POUCH && entity.isAlive()) {
			entity.playSound(((Pouchable)entity).getPouchedSound(), 1.0f, 1.0f);
			ItemStack itemStack2 = ((Pouchable)entity).getPouchItem();
			((Pouchable)entity).copyDataToStack(itemStack2);
			ItemStack itemStack3 = ItemUsage.exchangeStack(itemStack, player, itemStack2, false);
			player.setStackInHand(hand, itemStack3);
			World world = entity.world;
			entity.discard();
			return Optional.of(ActionResult.success(world.isClient));
		}
		return Optional.empty();
	}
}
