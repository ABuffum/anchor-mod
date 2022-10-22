package haven.items.throwable;

import haven.entities.projectiles.BottledConfettiEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BottledConfettiItem extends Item {
	public BottledConfettiItem(Settings settings) { super(settings); }

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SPLASH_POTION_THROW, SoundCategory.NEUTRAL, 0.5F, 1F);
		if (!world.isClient) {
			BottledConfettiEntity confettiEntity = new BottledConfettiEntity(world, user);
			confettiEntity.setItem(itemStack);
			confettiEntity.setProperties(user, user.getPitch(), user.getYaw(), 0F, 1.5F, 0F);
			world.spawnEntity(confettiEntity);
		}
		user.incrementStat(Stats.USED.getOrCreateStat(this));
		if (!user.getAbilities().creativeMode) itemStack.decrement(1);
		return TypedActionResult.success(itemStack, world.isClient());
	}
}