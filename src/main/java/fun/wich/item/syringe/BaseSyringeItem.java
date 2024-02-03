package fun.wich.item.syringe;

import fun.wich.ModBase;
import fun.wich.ModFactory;
import fun.wich.entity.blood.BloodType;
import fun.wich.sound.ModSoundEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BaseSyringeItem extends Item {
	public interface SyringeEffect {
		void ApplyEffect(PlayerEntity user, ItemStack stack, LivingEntity entity);
	}
	private SyringeEffect applyEffect = null;
	public BaseSyringeItem() { this(ModFactory.ItemSettings().recipeRemainder(ModBase.DIRTY_SYRINGE).maxCount(1)); }
	public BaseSyringeItem(SyringeEffect applyEffect) {
		this();
		this.applyEffect = applyEffect;
	}
	public BaseSyringeItem(Settings settings) { super(settings); }

	public void ApplyEffect(PlayerEntity user, ItemStack stack) { ApplyEffect(user, stack, user); }
	public void ApplyEffect(PlayerEntity user, ItemStack stack, LivingEntity entity) {
		if (applyEffect != null) applyEffect.ApplyEffect(user, stack, entity);
	}
	public ItemStack GetReplacementSyringe(PlayerEntity user, LivingEntity entity) { return new ItemStack(ModBase.DIRTY_SYRINGE); }
	public void ReplaceSyringe(PlayerEntity user, Hand hand) { ReplaceSyringe(user, user, hand); }
	public void ReplaceSyringe(PlayerEntity user, LivingEntity entity, Hand hand) {
		ReplaceSyringe(user, hand, GetReplacementSyringe(user, entity));
	}
	public void ReplaceSyringe(PlayerEntity user, Hand hand, BloodType type) {
		ReplaceSyringe(user, hand, BloodSyringeItem.getForBloodType(type));
	}
	public void ReplaceSyringe(PlayerEntity user, Hand hand, Item replacement) {
		ReplaceSyringe(user, hand, new ItemStack(replacement == null ? ModBase.DIRTY_SYRINGE : replacement));
	}
	public void ReplaceSyringe(PlayerEntity user, Hand hand, ItemStack newStack) {
		if (!user.getAbilities().creativeMode) user.getStackInHand(hand).decrement(1);
		user.getInventory().insertStack(newStack);
	}

	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		if (!entity.blockedByShield(DamageSource.player(user))) {
			if (entity instanceof PlayerEntity playerEntity && playerEntity.getAbilities().creativeMode) return ActionResult.PASS;
			ApplyEffect(user, stack, entity);
			entity.playSound(ModSoundEvents.SYRINGE_INJECTED, 0.5F, 1.0F);
			ReplaceSyringe(user, entity, hand);
			user.getItemCooldownManager().set(user.getStackInHand(hand).getItem(), 100);
			return ActionResult.CONSUME;
		}
		return ActionResult.PASS;
	}
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (!user.isSneaking()) {
			ApplyEffect(user, user.getStackInHand(hand));
			user.playSound(ModSoundEvents.SYRINGE_INJECTED, 0.5F, 1.0F);
			ReplaceSyringe(user, hand);
			user.getItemCooldownManager().set(user.getStackInHand(hand).getItem(), 100);
			return TypedActionResult.consume(user.getStackInHand(hand));
		}
		return TypedActionResult.pass(user.getStackInHand(hand));
	}
}
