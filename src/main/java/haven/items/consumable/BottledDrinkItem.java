package haven.items.consumable;

import haven.util.ItemUtils;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class BottledDrinkItem extends Item {
	private static final int MAX_USE_TIME = 32;

	private final SoundEvent drinkSound;

	public BottledDrinkItem(Item.Settings settings) {
		this(SoundEvents.ITEM_BOTTLE_EMPTY, settings);
	}
	public BottledDrinkItem(SoundEvent drinkSound, Item.Settings settings) {
		super(settings);
		this.drinkSound = drinkSound;
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		super.finishUsing(stack, world, user);
		if (user instanceof ServerPlayerEntity) {
			ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)user;
			Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
			serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
		}

		return ItemUtils.getConsumableRemainder(stack, user, Items.GLASS_BOTTLE);
	}

	public int getMaxUseTime(ItemStack stack) {
		return MAX_USE_TIME;
	}

	public UseAction getUseAction(ItemStack stack) {
		return UseAction.DRINK;
	}

	public SoundEvent getDrinkSound() {
		return drinkSound;
	}

	public SoundEvent getEatSound() {
		return drinkSound;
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		return ItemUsage.consumeHeldItem(world, user, hand);
	}
}
