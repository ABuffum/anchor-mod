package fun.wich.item.consumable;

import fun.wich.util.ItemUtil;
import fun.wich.util.MilkUtil;
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

public class MilkBowlItem extends Item {
	public MilkBowlItem(Settings settings) { super(settings); }
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		if (user instanceof ServerPlayerEntity serverPlayerEntity) {
			Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
			serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
		}
		if (!world.isClient) MilkUtil.ApplyMilk(world, user);
		return ItemUtil.getConsumableRemainder(stack, user, Items.BOWL);
	}
	public int getMaxUseTime(ItemStack stack) { return 32; }
	public UseAction getUseAction(ItemStack stack) { return UseAction.DRINK; }
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) { return ItemUsage.consumeHeldItem(world, user, hand); }
}
