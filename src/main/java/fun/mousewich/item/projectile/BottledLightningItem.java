package fun.mousewich.item.projectile;

import fun.mousewich.entity.projectile.BottledConfettiEntity;
import fun.mousewich.entity.projectile.BottledLightningEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BottledLightningItem extends Item {
	public BottledLightningItem(Settings settings) { super(settings); }

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SPLASH_POTION_THROW, SoundCategory.NEUTRAL, 0.5F, 1F);
		if (!world.isClient) {
			BottledLightningEntity projectile = new BottledLightningEntity(world, user);
			projectile.setItem(itemStack);
			projectile.setProperties(user, user.getPitch(), user.getYaw(), 0F, 1.5F, 0F);
			world.spawnEntity(projectile);
		}
		user.incrementStat(Stats.USED.getOrCreateStat(this));
		if (!user.getAbilities().creativeMode) itemStack.decrement(1);
		return TypedActionResult.success(itemStack, world.isClient());
	}
}