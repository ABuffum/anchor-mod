package haven.anchors;

import haven.HavenMod;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AnchorCoreItem extends Item {
	public final int owner;
	public AnchorCoreItem(int owner) {
		super(HavenMod.ITEM_SETTINGS);
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
		PlayerEntity player =	context.getPlayer();
		//if (!world.isClient) {
			BlockPos pos = context.getBlockPos();
			BlockState state = world.getBlockState(pos);
			Block block = state.getBlock();
			if (block instanceof AnchorBlock) {
				int owner = state.get(AnchorBlock.OWNER);
				if (owner == 0) {
		        	world.setBlockState(pos, state.with(AnchorBlock.OWNER, this.owner));
		        	Hand hand = context.getHand();
					ItemStack itemStack = player.getStackInHand(hand);
					itemStack.decrement(1);
		        	return ActionResult.SUCCESS;
					//return ActionResult.PASS;
				}
			}
		//}
		//else player.sendMessage(Text.of("is client"), true);
		return ActionResult.FAIL;
	}
}
