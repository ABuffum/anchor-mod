package haven.items;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ChorusJuiceItem extends BottledDrinkItem {

	public ChorusJuiceItem(Settings settings) {
		super(settings);
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		ItemStack itemStack = super.finishUsing(stack, world, user);
		if (!world.isClient) {
			double d = user.getX();
			double e = user.getY();
			double f = user.getZ();
			for(int i = 0; i < 16; ++i) {
				double g = user.getX() + (user.getRandom().nextDouble() - 0.5D) * 16.0D;
				double h = MathHelper.clamp(user.getY() + (double)(user.getRandom().nextInt(16) - 8), (double)world.getBottomY(), (double)(world.getBottomY() + ((ServerWorld)world).getLogicalHeight() - 1));
				double j = user.getZ() + (user.getRandom().nextDouble() - 0.5D) * 16.0D;
				if (user.hasVehicle()) user.stopRiding();
				if (user.teleport(g, h, j, true)) {
					SoundEvent soundEvent = user instanceof FoxEntity ? SoundEvents.ENTITY_FOX_TELEPORT : SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
					world.playSound((PlayerEntity)null, d, e, f, soundEvent, SoundCategory.PLAYERS, 1.0F, 1.0F);
					user.playSound(soundEvent, 1.0F, 1.0F);
					break;
				}
			}
			if (user instanceof PlayerEntity player) player.getItemCooldownManager().set(this, 20);
		}
		return itemStack;
	}
}
