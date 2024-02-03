package fun.wich.block.flint;

import fun.wich.block.BlockConvertible;
import fun.wich.gen.data.tag.ModItemTags;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class FlintBlock extends Block {
	public FlintBlock(BlockConvertible block) { this(block.asBlock()); }
	public FlintBlock(Block block) { this(Settings.copy(block)); }
	public FlintBlock(Settings settings) {
		super(settings);
	}
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		ItemStack stack = player.getStackInHand(hand);
		if (stack.isIn(ModItemTags.LIGHTS_FLINT)) {
			Direction side = hit.getSide();
			BlockPos blockPos = pos.offset(side);
			if (AbstractFireBlock.canPlaceAt(world, blockPos, player.getHorizontalFacing())) {
				world.playSound(player, blockPos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0f, world.getRandom().nextFloat() * 0.4f + 0.8f);
				BlockState blockState = AbstractFireBlock.getState(world, blockPos);
				world.setBlockState(blockPos, blockState, Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
				world.emitGameEvent(player, GameEvent.BLOCK_PLACE, blockPos);
				if (player instanceof ServerPlayerEntity serverPlayer) {
					Criteria.PLACED_BLOCK.trigger(serverPlayer, blockPos, stack);
					stack.damage(1, player, p -> p.sendToolBreakStatus(hand));
				}
				return ActionResult.SUCCESS;
			}
		}
		return ActionResult.PASS;
	}
}
