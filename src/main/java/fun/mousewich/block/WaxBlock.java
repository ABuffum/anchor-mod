package fun.mousewich.block;

import fun.mousewich.util.ItemUtil;
import fun.mousewich.util.OxidationScale;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

import java.util.Optional;

public class WaxBlock extends Block {
	public WaxBlock(BlockConvertible block) { this(block.asBlock()); }
	public WaxBlock(Block block) { this(Settings.copy(block)); }
	public WaxBlock(Settings settings) { super(settings); }

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		ItemStack stack = player.getStackInHand(hand);
		Item item = stack.getItem();
		Optional<Item> waxed = OxidationScale.getWaxed(item);
		if (waxed.isPresent()) {
			player.setStackInHand(hand, ItemUtil.swapItem(stack, waxed.get()));
			world.syncWorldEvent(player, WorldEvents.BLOCK_WAXED, pos, 0);
			return ActionResult.SUCCESS;
		}
		return ActionResult.PASS;
	}
}
