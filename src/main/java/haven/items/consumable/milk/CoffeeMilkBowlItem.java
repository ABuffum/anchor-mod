package haven.items.consumable.milk;

import haven.util.BucketUtils;
import haven.util.MilkUtils;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;

public class CoffeeMilkBowlItem extends MilkBowlItem {
	public CoffeeMilkBowlItem(Settings settings) {
		super(settings);
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		if (user instanceof ServerPlayerEntity) {
			ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)user;
			Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
			serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
		}

		if (user instanceof PlayerEntity && !((PlayerEntity)user).getAbilities().creativeMode) {
			stack.decrement(1);
		}

		if (!world.isClient) {
			MilkUtils.ClearStatusEffects(world, user);
			if (user instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity)user;
				player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 0));
				player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 200, 0));
			}
			MilkUtils.CheckLactosIntolerance(world, user);
		}

		return stack.isEmpty() ? new ItemStack(Items.BOWL) : stack;
	}
}