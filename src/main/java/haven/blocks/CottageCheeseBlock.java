package haven.blocks;

import haven.HavenMod;
import haven.materials.providers.BucketProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CottageCheeseBlock extends Block {
	public CottageCheeseBlock(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		BucketProvider bucketProvider = BucketProvider.getProvider(player.getStackInHand(hand).getItem());
		if (bucketProvider != null) {
			ItemStack newStack = new ItemStack(bucketProvider.getCottageCheeseBucket());
			if (!player.getAbilities().creativeMode) {
				player.getStackInHand(hand).decrement(1);
				if (player.getStackInHand(hand).isEmpty()) player.setStackInHand(hand, newStack);
				else player.getInventory().insertStack(newStack);
			}
			else player.getInventory().insertStack(newStack);
			world.setBlockState(pos, Blocks.AIR.getDefaultState());
		}
		return ActionResult.PASS;
	}
}
