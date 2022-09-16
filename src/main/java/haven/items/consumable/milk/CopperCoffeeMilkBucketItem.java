package haven.items.consumable.milk;

import haven.HavenMod;
import haven.util.BucketUtils;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;

public class CopperCoffeeMilkBucketItem extends WoodMilkBucketItem {

	public CopperCoffeeMilkBucketItem(Settings settings) {
		super(settings);
	}

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
			BucketUtils.milkClearStatusEffects(world, user);
			if (user instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity)user;
				player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 0));
				player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 200, 0));
			}
		}

		return stack.isEmpty() ? new ItemStack(HavenMod.COPPER_BUCKET) : stack;
	}
}