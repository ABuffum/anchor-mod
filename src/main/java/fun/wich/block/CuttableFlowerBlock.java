package fun.wich.block;

import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.function.Function;
import java.util.function.Supplier;

public class CuttableFlowerBlock extends TallFlowerBlock {
	private final Supplier<FlowerBlock> shortBlock;
	public FlowerBlock getShortBlock() { return shortBlock.get(); }

	private final Function<World, ItemStack> alsoDrop;

	public CuttableFlowerBlock(Settings settings, Supplier<FlowerBlock> shortBlock, Function<World, ItemStack> alsoDrop) {
		super(settings);
		this.shortBlock = shortBlock;
		this.alsoDrop = alsoDrop;
	}

	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(Items.SHEARS)) {
			if (state.get(TallFlowerBlock.HALF) == DoubleBlockHalf.UPPER) {
				world.setBlockState(pos.down(), getShortBlock().getDefaultState(), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
				world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL);
				world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
			}
			else {
				world.setBlockState(pos, getShortBlock().getDefaultState(), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
				world.setBlockState(pos.up(), Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL);
				world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
			}
			dropStack(world, pos, new ItemStack(getShortBlock().asItem()));
			if (alsoDrop != null) dropStack(world, pos, alsoDrop.apply(world));
			itemStack.damage(1, player, p -> p.sendToolBreakStatus(hand));
			world.emitGameEvent(player, GameEvent.SHEAR, pos);
			player.incrementStat(Stats.USED.getOrCreateStat(Items.SHEARS));
			return ActionResult.success(world.isClient);
		}
		return super.onUse(state, world, pos, player, hand, hit);
	}
}
