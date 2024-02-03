package fun.wich.haven.item;

import fun.wich.haven.HavenMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.EndPortalFrameBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AmberEyeItem extends Item {
	public AmberEyeItem(Settings settings) { super(settings); }

	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos blockPos = context.getBlockPos();
		BlockState blockState = world.getBlockState(blockPos);
		if (blockState.isOf(Blocks.END_PORTAL_FRAME) && !(Boolean)blockState.get(EndPortalFrameBlock.EYE)) {
			if (world.isClient) return ActionResult.SUCCESS;
			else {
				BlockState blockState2 = HavenMod.AMBER_EYE_END_PORTAL_FRAME.getDefaultState()
						.with(EndPortalFrameBlock.FACING, blockState.get(EndPortalFrameBlock.FACING));
				world.playSound(null, blockPos, SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
				Block.pushEntitiesUpBeforeBlockChange(blockState, blockState2, world, blockPos);
				world.setBlockState(blockPos, blockState2, Block.NOTIFY_LISTENERS);
				world.updateComparators(blockPos, HavenMod.AMBER_EYE_END_PORTAL_FRAME);
				context.getStack().decrement(1);
				return ActionResult.CONSUME;
			}
		}
		else return ActionResult.PASS;
	}
}
