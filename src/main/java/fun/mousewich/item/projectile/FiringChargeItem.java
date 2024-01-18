package fun.mousewich.item.projectile;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FiringChargeItem extends Item {
	public FiringChargeItem(Settings settings) { super(settings); }

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.NEUTRAL, 2.0f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
		if (!world.isClient) {
			SnowballEntity snowball = new SnowballEntity(world, user);
			snowball.setProperties(user, user.getPitch(), user.getYaw(), 0.0f, 1.5f, 1.0f);
			Vec3d velocity = snowball.getVelocity();
			SmallFireballEntity smallFireballEntity = new SmallFireballEntity(world, user, velocity.x, velocity.y, velocity.z);
			smallFireballEntity.setItem(itemStack);
			smallFireballEntity.setPos(user.getX(), (user.getY() + user.getEyeY()) / 2, user.getZ());
			world.spawnEntity(smallFireballEntity);
		}
		user.incrementStat(Stats.USED.getOrCreateStat(this));
		if (!user.getAbilities().creativeMode) {
			itemStack.decrement(1);
		}
		return TypedActionResult.success(itemStack, world.isClient());
	}
}