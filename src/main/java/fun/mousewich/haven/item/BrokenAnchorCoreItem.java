package fun.mousewich.haven.item;

import fun.mousewich.ModFactory;
import fun.mousewich.haven.block.anchor.AnchorBlock;
import fun.mousewich.haven.block.anchor.BrokenAnchorBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BrokenAnchorCoreItem extends Item {
	public final int owner;
	public BrokenAnchorCoreItem(int owner) {
		super(ModFactory.ItemSettings());
		this.owner = owner;
	}
	
	/**
	 * Called when an item is used on a block.
	 * 
	 * <p>This method is called on both the logical client and logical server, so take caution when using this method.
	 * The logical side can be checked using {@link World#isClient() context.getWorld().isClient()}.
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
		if (block instanceof BrokenAnchorBlock) {
			if (state.get(AnchorBlock.OWNER) == 0) {
		       	world.setBlockState(pos, state.with(AnchorBlock.OWNER, this.owner));
				player.getStackInHand(context.getHand()).decrement(1);
		       	return ActionResult.SUCCESS;
			}
		}
		return ActionResult.FAIL;
	}
}
