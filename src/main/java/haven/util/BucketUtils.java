package haven.util;

import haven.HavenMod;
import haven.mixins.entities.LivingEntityAccessor;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Iterator;

public class BucketUtils {
	public static boolean milkClearStatusEffects(World world, LivingEntity entity) {
		if (world.isClient) return false;
		Iterator<StatusEffectInstance> iterator = entity.getActiveStatusEffects().values().iterator();
		boolean bl;
		LivingEntityAccessor lea = (LivingEntityAccessor)entity;
		for(bl = false; iterator.hasNext(); bl = true) {
			StatusEffectInstance effect = (StatusEffectInstance)iterator.next();
			if (!HavenMod.MILK_IMMUNE_EFFECTS.contains(effect.getEffectType())) {
				lea.OnStatusEffectRemoved(effect);
			}
			iterator.remove();
		}
		return bl;
	}

	public static ActionResult fillCauldron(World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack, BlockState state, SoundEvent soundEvent, Item itemOut) {
		if (!world.isClient) {
			Item item = stack.getItem();
			player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(itemOut)));
			player.incrementStat(Stats.FILL_CAULDRON);
			player.incrementStat(Stats.USED.getOrCreateStat(item));
			world.setBlockState(pos, state);
			world.playSound((PlayerEntity)null, pos, soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
			world.emitGameEvent((Entity)null, GameEvent.FLUID_PLACE, pos);
		}

		return ActionResult.success(world.isClient);
	}
}
