package haven.items.blood;

import haven.HavenMod;
import haven.sounds.HavenSoundEvents;
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
	public BaseSyringeItem() {
		this(new Item.Settings().group(HavenMod.BLOOD_ITEM_GROUP).recipeRemainder(HavenMod.DIRTY_SYRINGE).maxCount(1));
	}
	public BaseSyringeItem(Settings settings) {
		super(settings);
	}

	public void ApplyEffect(PlayerEntity user) { ApplyEffect(user, user); }
	public void ApplyEffect(PlayerEntity user, LivingEntity entity) { }
	public Item GetReplacementSyringe(PlayerEntity user, LivingEntity entity) {
		return HavenMod.DIRTY_SYRINGE;
	}
	public void ReplaceSyringe(PlayerEntity user, Hand hand) { ReplaceSyringe(user, user, hand); }
	public void ReplaceSyringe(PlayerEntity user, LivingEntity entity, Hand hand) {
		ItemStack newStack = new ItemStack(GetReplacementSyringe(user, entity));
		if (!user.getAbilities().creativeMode) {
			user.getStackInHand(hand).decrement(1);
			if (user.getStackInHand(hand).isEmpty()) user.setStackInHand(hand, newStack);
			else user.getInventory().insertStack(newStack);
		}
		else user.getInventory().insertStack(newStack);
	}

	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		if (!entity.blockedByShield(DamageSource.player(user))) {
			if (entity instanceof PlayerEntity playerEntity && playerEntity.getAbilities().creativeMode) return ActionResult.PASS;
			ApplyEffect(user, entity);
			entity.playSound(HavenSoundEvents.SYRINGE_INJECTED, 0.5F, 1.0F);
			ReplaceSyringe(user, entity, hand);
			return ActionResult.CONSUME;
		}
		return ActionResult.PASS;
	}
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (!user.isSneaking()) {
			ApplyEffect(user);
			user.playSound(HavenSoundEvents.SYRINGE_INJECTED, 0.5F, 1.0F);
			ReplaceSyringe(user, hand);
			return TypedActionResult.consume(user.getStackInHand(hand));
		}
		return TypedActionResult.pass(user.getStackInHand(hand));
	}
}
