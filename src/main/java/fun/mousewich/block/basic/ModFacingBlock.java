package fun.mousewich.block.basic;

import fun.mousewich.block.BlockConvertible;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.Direction;

public class ModFacingBlock extends Block {
	public ModFacingBlock(BlockConvertible block) { this(block.asBlock()); }
	public ModFacingBlock(Block block) { this(Settings.copy(block)); }
	public ModFacingBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(Properties.FACING, Direction.NORTH));
	}
	public BlockRenderType getRenderType(BlockState state) { return BlockRenderType.MODEL; }
	public BlockState rotate(BlockState state, BlockRotation rotation) { return state.with(Properties.FACING, rotation.rotate(state.get(Properties.FACING))); }
	public BlockState mirror(BlockState state, BlockMirror mirror) { return state.rotate(mirror.getRotation(state.get(Properties.FACING))); }
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(Properties.FACING); }
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(Properties.FACING, ctx.getPlayerLookDirection().getOpposite());
	}
}
