package fun.wich.item.consumable;

import fun.wich.util.ItemUtil;
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
import net.minecraft.world.event.GameEvent;

public abstract class BottledDrinkItem extends Item {
	public BottledDrinkItem(Settings settings) { super(settings); }

	public abstract void OnDrink(ItemStack stack, LivingEntity user);
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		PlayerEntity playerEntity = user instanceof PlayerEntity ? (PlayerEntity) user : null;
		if (playerEntity instanceof ServerPlayerEntity player) Criteria.CONSUME_ITEM.trigger(player, stack);
		if (!world.isClient) OnDrink(stack, user);
		if (playerEntity != null) playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
		world.emitGameEvent(user, GameEvent.DRINKING_FINISH, user.getCameraBlockPos());
		return ItemUtil.getConsumableRemainder(stack, user, Items.GLASS_BOTTLE);
	}

	@Override
	public int getMaxUseTime(ItemStack stack) { return 32; }
	@Override
	public UseAction getUseAction(ItemStack stack) { return UseAction.DRINK; }
	@Override
	public SoundEvent getDrinkSound() { return SoundEvents.ENTITY_GENERIC_DRINK; }
	@Override
	public SoundEvent getEatSound() { return SoundEvents.ENTITY_GENERIC_DRINK; }
	@Override
	public boolean hasRecipeRemainder() { return true; }
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) { return ItemUsage.consumeHeldItem(world, user, hand); }
}
