package haven.items;

import haven.ModBase;

import haven.blocks.anchors.AnchorBlock;
import haven.blocks.anchors.BrokenAnchorBlock;
import haven.blocks.anchors.SubstituteAnchorBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AnchorCoreItem extends Item {
	public final int owner;
	public AnchorCoreItem(int owner) {
		super(ModBase.ItemSettings());
		this.owner = owner;
	}
	
	/**
	 * Called when an item is used on a block.
	 * 
	 * <p>This method is called on both the logical client and logical server, so take caution when using this method.
	 * The logical side can be checked using {@link net.minecraft.world.World#isClient() context.getWorld().isClient()}.
	 * 
	 * @return an action result that specifies if using the item on a block was successful.
	 * 
	 * @param context the usage context
	 */
	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		PlayerEntity player = context.getPlayer();
		BlockPos pos = context.getBlockPos();
		BlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		Boolean anchor = block instanceof AnchorBlock, substitute = block instanceof SubstituteAnchorBlock;
		if (anchor || substitute) {
			if (state.get(AnchorBlock.OWNER) == 0) {
		       	world.setBlockState(pos, state.with(AnchorBlock.OWNER, this.owner));
				player.getStackInHand(context.getHand()).decrement(1);
		       	return ActionResult.SUCCESS;
			}
		}
		else if (block instanceof RespawnAnchorBlock) {
			BlockState newState = ModBase.SUBSTITUTE_ANCHOR_BLOCK.getDefaultState();
			world.setBlockState(pos, newState.with(AnchorBlock.OWNER, this.owner));
			player.getStackInHand(context.getHand()).decrement(1);
			return ActionResult.SUCCESS;
		}
		return ActionResult.FAIL;
	}
}
