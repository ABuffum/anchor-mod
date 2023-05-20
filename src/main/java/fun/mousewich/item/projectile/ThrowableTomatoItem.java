package fun.mousewich.item.projectile;

import fun.mousewich.entity.projectile.ThrownTomatoEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ThrowableTomatoItem extends Item {
	public ThrowableTomatoItem(Settings settings) { super(settings); }

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F);
		if (!world.isClient) {
			ThrownTomatoEntity projectile = new ThrownTomatoEntity(world, user);
			projectile.setItem(itemStack);
			projectile.setProperties(user, user.getPitch(), user.getYaw(), 0.0f, 1.5f, 1.0f);
			world.spawnEntity(projectile);
		}
		user.incrementStat(Stats.USED.getOrCreateStat(this));
		if (!user.getAbilities().creativeMode) itemStack.decrement(1);
		return TypedActionResult.success(itemStack, world.isClient());
	}
}