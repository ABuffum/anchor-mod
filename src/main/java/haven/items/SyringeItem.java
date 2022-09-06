package haven.items;

import haven.HavenMod;
import haven.sounds.HavenSoundEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class SyringeItem extends Item {
	public final StatusEffectInstance statusEffect;

	public SyringeItem(StatusEffectInstance statusEffect) {
		this(statusEffect, HavenMod.ITEM_SETTINGS);
	}
	public SyringeItem(StatusEffectInstance statusEffect, Settings settings) {
		super(settings);
		this.statusEffect = statusEffect;
	}

	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		entity.addStatusEffect(new StatusEffectInstance(statusEffect), user);
		entity.playSound(HavenSoundEvents.SYRINGE_INJECTED, 0.5F, 1.0F);
		user.getStackInHand(hand).decrement(1);
		return ActionResult.CONSUME;
	}
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		user.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 600, 1), user);
		user.getStackInHand(hand).decrement(1);
		return TypedActionResult.pass(user.getStackInHand(hand));
	}
}
