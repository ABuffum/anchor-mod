package fun.mousewich.block;

import fun.mousewich.ModBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public interface ModVines {
	VoxelShape SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

	static ActionResult pick(Entity entity, BlockState state, World world, BlockPos pos, ItemStack berries) {
		ActionResult result = pick(state, world, pos, berries);
		if (result == ActionResult.SUCCESS || result == ActionResult.CONSUME) world.emitGameEvent(entity, GameEvent.BLOCK_CHANGE, pos);
		return result;
	}
	static ActionResult pick(BlockState state, World world, BlockPos pos, ItemStack berries) {
		if (state.get(Properties.BERRIES)) {
			Block.dropStack(world, pos, berries);
			float f = MathHelper.nextBetween(world.random, 0.8f, 1.2f);
			world.playSound(null, pos, SoundEvents.BLOCK_CAVE_VINES_PICK_BERRIES, SoundCategory.BLOCKS, 1.0f, f);
			world.setBlockState(pos, state.with(Properties.BERRIES, false), Block.NOTIFY_LISTENERS);
			return ActionResult.success(world.isClient);
		}
		return ActionResult.PASS;
	}

	static boolean hasBerries(BlockState state) { return state.contains(Properties.BERRIES) && state.get(Properties.BERRIES); }
}
