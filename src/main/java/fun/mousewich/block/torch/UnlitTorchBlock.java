package fun.mousewich.block.torch;

import fun.mousewich.ModBase;
import fun.mousewich.ModFactory;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;

public class UnlitTorchBlock extends Block {
	protected static final VoxelShape BOUNDING_SHAPE = Block.createCuboidShape(6.0, 0.0, 6.0, 10.0, 10.0, 10.0);

	protected final TorchBlock lit;
	public TorchBlock getLitBlock() { return lit; }
	public UnlitTorchBlock(TorchBlock lit) {
		super(Settings.copy(lit).luminance(ModFactory.LUMINANCE_0));
		this.lit = lit;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return BOUNDING_SHAPE;
	}

	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		ItemStack itemStack = player.getStackInHand(hand);
		boolean flint, lava = false;
		if ((flint = itemStack.isOf(Items.FLINT_AND_STEEL)) || (lava = itemStack.isOf(ModBase.LAVA_BOTTLE)) || itemStack.isOf(Items.FIRE_CHARGE)) {
			SoundEvent sound = flint ? SoundEvents.ITEM_FLINTANDSTEEL_USE : SoundEvents.ITEM_FIRECHARGE_USE;
			world.playSound(player, pos, sound, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
			world.setBlockState(pos, lit.getDefaultState());
			world.emitGameEvent(player, GameEvent.BLOCK_PLACE, pos);
			if (flint) itemStack.damage(1, player, p -> p.sendToolBreakStatus(hand));
			else {
				itemStack.decrement(1);
				if (lava) player.giveItemStack(new ItemStack(ModBase.LAVA_BOTTLE.getRecipeRemainder()));
			}
			return ActionResult.SUCCESS;
		}
		return ActionResult.PASS;
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (direction == Direction.DOWN && !this.canPlaceAt(state, world, pos)) {
			return Blocks.AIR.getDefaultState();
		}
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return TorchBlock.sideCoversSmallSquare(world, pos.down(), Direction.UP);
	}

	@Override
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
		return lit.getPickStack(world, pos, state);
	}
}
