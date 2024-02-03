package fun.wich.item;

import fun.wich.ModBase;
import fun.wich.block.sculk.SculkBlock;
import fun.wich.block.sculk.SculkTurfBlock;
import fun.wich.sound.ModSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Random;

public class ChumItem extends Item {
	public ChumItem(Settings settings) { super(settings); }

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos pos = context.getBlockPos();
		BlockState blockState = world.getBlockState(pos);
		Block block = blockState.getBlock();
		PlayerEntity player = context.getPlayer();
		//Squelch out some sculk
		if (block == ModBase.SCULK.asBlock() || block instanceof SculkTurfBlock) {
			if (block instanceof SculkTurfBlock) {
				if (context.getSide() != Direction.UP) return ActionResult.PASS;
			}
			Random random = world.getRandom();
			if (random.nextFloat() < 0.05f) {
				int selection = random.nextInt(3);
				ItemStack stack = new ItemStack(ModBase.SCULK_VEIN);
				if (selection == 0) stack = new ItemStack(ModBase.SCULK);
				world.spawnEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, stack));
			}
			world.playSound(null, pos, ModSoundEvents.ITEM_CHUM_USED, SoundCategory.BLOCKS, 1, 1);
			if (player != null && !player.getAbilities().creativeMode) context.getStack().decrement(1);
			return ActionResult.SUCCESS;
		}
		//Try spreading sculk turf
		else if (world.getBlockState(pos.up()).isTranslucent(world, pos)) {
			SculkTurfBlock turf = SculkTurfBlock.getSculkTurf(block);
			if (turf != null) {
				for (BlockPos blockPos : BlockPos.iterate(pos.add(-1, -1, -1), pos.add(1, 1, 1))) {
					Block b = world.getBlockState(blockPos).getBlock();
					if (b instanceof SculkTurfBlock || b instanceof SculkBlock) {
						world.setBlockState(pos, turf.getDefaultState(), Block.NOTIFY_ALL);
						world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
						world.playSound(null, pos, ModSoundEvents.ITEM_CHUM_USED, SoundCategory.BLOCKS, 1, 1);
						if (player != null && !player.getAbilities().creativeMode) context.getStack().decrement(1);
						return ActionResult.SUCCESS;
					}
				}
			}
		}
		return ActionResult.PASS;
	}
}
