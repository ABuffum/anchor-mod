package fun.wich.haven.block;

import fun.wich.haven.HavenMod;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class AmberEyeEndPortalFrameBlock extends Block {
	public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
	protected static final VoxelShape FRAME_SHAPE = Block.createCuboidShape(0., 0, 0, 16, 13, 16);
	protected static final VoxelShape EYE_SHAPE = Block.createCuboidShape(4, 13, 4, 12, 16, 12);
	protected static final VoxelShape FRAME_WITH_EYE_SHAPE = VoxelShapes.union(FRAME_SHAPE, EYE_SHAPE);
	public AmberEyeEndPortalFrameBlock(AbstractBlock.Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
	}
	public boolean hasSidedTransparency(BlockState state) { return true; }
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) { return FRAME_WITH_EYE_SHAPE; }
	public BlockState getPlacementState(ItemPlacementContext ctx) { return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite()); }
	public boolean hasComparatorOutput(BlockState state) { return true; }
	public int getComparatorOutput(BlockState state, World world, BlockPos pos) { return 15; }
	public BlockState rotate(BlockState state, BlockRotation rotation) { return state.with(FACING, rotation.rotate(state.get(FACING))); }
	public BlockState mirror(BlockState state, BlockMirror mirror) { return state.rotate(mirror.getRotation(state.get(FACING))); }
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(FACING); }
	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) { return false; }
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		dropStack(world, pos, HavenMod.AMBER_EYE.getDefaultStack());
		world.setBlockState(pos, Blocks.END_PORTAL_FRAME.getDefaultState()
				.with(EndPortalFrameBlock.FACING, state.get(FACING))
				.with(EndPortalFrameBlock.EYE, false));
		return ActionResult.PASS;
	}
}