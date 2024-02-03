package fun.wich.item.consumable;

import fun.wich.util.ItemUtil;
import fun.wich.util.MilkUtil;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;

public class CoffeeMilkBowlItem extends MilkBowlItem {
	public CoffeeMilkBowlItem(Settings settings) { super(settings); }
	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		if (user instanceof ServerPlayerEntity serverPlayerEntity) {
			Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
			serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
		}
		if (!world.isClient) MilkUtil.ApplyMilk(world, user, true);
		return ItemUtil.getConsumableRemainder(stack, user, Items.BOWL);
	}
}