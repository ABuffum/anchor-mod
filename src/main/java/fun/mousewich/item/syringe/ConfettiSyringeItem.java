package fun.mousewich.item.syringe;

import fun.mousewich.ModBase;
import fun.mousewich.ModConfig;
import fun.mousewich.damage.ModDamageSource;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.passive.sheep.RainbowSheepEntity;
import fun.mousewich.haven.HavenMod;
import fun.mousewich.sound.ModSoundEvents;
import fun.mousewich.util.ColorUtil;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class ConfettiSyringeItem extends BaseSyringeItem {
	public ConfettiSyringeItem() { super(); }
	@Override
	public void ApplyEffect(PlayerEntity user, ItemStack stack, LivingEntity entity) {
		BloodType bloodType = BloodType.Get(entity);
		if (ModConfig.REGISTER_HAVEN_MOD && bloodType == HavenMod.CONFETTI_BLOOD_TYPE) {
			if (user == entity) BloodSyringeItem.heal(entity, 4);
			else entity.damage(ModDamageSource.Injected("confetti_as_clown", user), 4);
		}
		//Transform sheep into rainbow sheep
		else if (entity instanceof SheepEntity sheep) {
			if (sheep instanceof RainbowSheepEntity) BloodSyringeItem.heal(entity, 1);
			else {
				if (!sheep.world.isClient()) {
					((ServerWorld)sheep.world).spawnParticles(ParticleTypes.EXPLOSION, sheep.getX(), sheep.getBodyY(0.5D), sheep.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
					sheep.discard();
					RainbowSheepEntity sheepEntity = ModBase.RAINBOW_SHEEP_ENTITY.create(sheep.world);
					if (sheepEntity != null) {
						sheepEntity.refreshPositionAndAngles(sheep.getX(), sheep.getY(), sheep.getZ(), sheep.getYaw(), sheep.getPitch());
						sheepEntity.setHealth(sheep.getHealth());
						sheepEntity.bodyYaw = sheep.bodyYaw;
						if (sheep.hasCustomName()) {
							sheepEntity.setCustomName(sheep.getCustomName());
							sheepEntity.setCustomNameVisible(sheep.isCustomNameVisible());
						}
						if (sheep.isPersistent()) sheepEntity.setPersistent();
						sheepEntity.setInvulnerable(sheep.isInvulnerable());
						sheep.world.spawnEntity(sheepEntity);
					}
					for(int i = 0; i <= sheep.getRandom().nextInt(3); ++i) {
						sheep.world.spawnEntity(new ItemEntity(sheep.world, sheep.getX(), sheep.getBodyY(1.0D), sheep.getZ(), new ItemStack(ColorUtil.GetWoolItem(sheep.getColor()))));
					}
				}
			}
		}
		else entity.damage(ModDamageSource.Injected("confetti", user), 1);
	}
	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		BlockPos pos = context.getBlockPos();
		World world = context.getWorld();
		Block block = context.getWorld().getBlockState(pos).getBlock();
		Block outBlock;
		Item outItem;
		if (ModConfig.REGISTER_HAVEN_MOD) {
			if (block == Blocks.PUMPKIN) {
				outBlock = HavenMod.SOLEIL_CARVED_PUMPKIN.asBlock();
				outItem = Items.PUMPKIN_SEEDS;
			}
			else if (block == Blocks.MELON) {
				outBlock = HavenMod.SOLEIL_CARVED_MELON.asBlock();
				outItem = Items.MELON_SEEDS;
			}
			else if (block == ModBase.WHITE_PUMPKIN.asBlock()) {
				outBlock = HavenMod.SOLEIL_CARVED_WHITE_PUMPKIN.asBlock();
				outItem = ModBase.WHITE_PUMPKIN_SEEDS;
			}
			else if (block == ModBase.ROTTEN_PUMPKIN.asBlock()) {
				outBlock = HavenMod.SOLEIL_CARVED_ROTTEN_PUMPKIN.asBlock();
				outItem = ModBase.ROTTEN_PUMPKIN_SEEDS;
			}
			else return ActionResult.PASS;
			//Convert a gourd into a soleil-carved gourd
			PlayerEntity player = context.getPlayer();
			ItemStack itemStack = context.getStack();
			if (!world.isClient && player != null) {
				Direction direction = context.getSide();
				Direction direction2 = direction.getAxis() == Direction.Axis.Y ? player.getHorizontalFacing().getOpposite() : direction;
				world.playSound(null, pos, ModSoundEvents.SYRINGE_INJECTED, SoundCategory.BLOCKS, 1F, 1F);
				world.setBlockState(pos, outBlock.getDefaultState().with(HorizontalFacingBlock.FACING, direction2), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
				ItemEntity itemEntity = new ItemEntity(world, pos.getX() + 0.5 + direction2.getOffsetX() * 0.65, pos.getY() + 0.1, pos.getZ() + 0.5 + direction2.getOffsetZ() * 0.65, new ItemStack(outItem, 4));
				itemEntity.setVelocity(0.05 * direction2.getOffsetX() + world.random.nextDouble() * 0.02, 0.05, 0.05 * direction2.getOffsetZ() + world.random.nextDouble() * 0.02);
				world.spawnEntity(itemEntity);
				if (!player.getAbilities().creativeMode) itemStack.decrement(1);
				world.emitGameEvent(player, GameEvent.SHEAR, pos);
			}
			return ActionResult.success(world.isClient);
		}
		return super.useOnBlock(context);
	}
}
