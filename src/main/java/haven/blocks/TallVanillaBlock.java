package haven.blocks;

import haven.HavenMod;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
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

import java.util.function.Consumer;

public class TallVanillaBlock extends TallFlowerBlock {
	public TallVanillaBlock(Settings settings) {
		super(settings);
	}

	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(Items.SHEARS)) {
			boolean upper = state.get(TallFlowerBlock.HALF) == DoubleBlockHalf.UPPER;
			if (upper) {
				world.setBlockState(pos.down(), HavenMod.VANILLA_FLOWER.BLOCK.getDefaultState(), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
				world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL);
			}
			else {
				world.setBlockState(pos, HavenMod.VANILLA_FLOWER.BLOCK.getDefaultState(), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
				world.setBlockState(pos.up(), Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL);
			}
			dropStack(world, pos, new ItemStack(HavenMod.VANILLA, world.random.nextInt(2) + 1));
			itemStack.damage(1, (LivingEntity)player, (Consumer)((playerx) -> ((LivingEntity)playerx).sendToolBreakStatus(hand)));
			world.emitGameEvent(player, GameEvent.SHEAR, pos);
			player.incrementStat(Stats.USED.getOrCreateStat(Items.SHEARS));
			return ActionResult.success(world.isClient);
		}
		return super.onUse(state, world, pos, player, hand, hit);
	}
}
