package haven.items.consumable.milk;

import com.nhoryzon.mc.farmersdelight.registry.ItemsRegistry;
import haven.util.ItemUtils;
import haven.util.MilkUtils;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class MilkBottleItem extends Item {
	private static final int MAX_USE_TIME = 32;

	public MilkBottleItem(Settings settings) {
		super(settings);
	}

	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		if (user instanceof ServerPlayerEntity) {
			ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)user;
			Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
			serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
		}
		if (!world.isClient) MilkUtils.ApplyMilk(world, user);

		return ItemUtils.getConsumableRemainder(stack, user, Items.GLASS_BOTTLE);
	}

	public int getMaxUseTime(ItemStack stack) { return MAX_USE_TIME; }

	public UseAction getUseAction(ItemStack stack) { return UseAction.DRINK; }

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		return ItemUsage.consumeHeldItem(world, user, hand);
	}
}
