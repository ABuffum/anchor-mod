package haven.items.consumable;

import haven.blood.BloodType;
import haven.damage.HavenDamageSource;
import haven.util.ItemUtils;
import haven.util.WaterBottleUtil;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class CurseBreakerPotionItem extends Item {
	private final CurseBreakerPotionEffect effect;
	public CurseBreakerPotionItem(Settings settings, CurseBreakerPotionEffect effect) {
		super(settings);
		this.effect = effect;
	}

	public boolean hasGlint(ItemStack stack) { return true; }

	public interface CurseBreakerPotionEffect {
		public void apply(ItemStack stack, World world, LivingEntity user);
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		PlayerEntity playerEntity = user instanceof PlayerEntity ? (PlayerEntity) user : null;
		if (playerEntity instanceof ServerPlayerEntity player) Criteria.CONSUME_ITEM.trigger(player, stack);
		if (!world.isClient) {
			if (BloodType.Get(user).IsWaterVulnerable()) user.damage(HavenDamageSource.DRANK_WATER, 2);
			effect.apply(stack, world, user);
		}
		if (playerEntity != null) playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
		world.emitGameEvent(user, GameEvent.DRINKING_FINISH, user.getCameraBlockPos());

		return ItemUtils.getConsumableRemainder(stack, user, Items.GLASS_BOTTLE);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		return WaterBottleUtil.useOnBlock(context);
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

