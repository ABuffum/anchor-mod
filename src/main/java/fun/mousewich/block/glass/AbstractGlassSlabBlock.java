package fun.mousewich.block.glass;

import fun.mousewich.block.TransparentSlabBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public abstract class AbstractGlassSlabBlock extends TransparentSlabBlock {
	protected final Block baseBlock;
	public Block getBaseBlock() { return baseBlock; }
	public AbstractGlassSlabBlock(Block block) {
		super(block);
		this.baseBlock = block;
	}
	@Override
	public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return VoxelShapes.empty();
	}
	@Override
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) { return 1.0f; }
	@Override
	public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) { return true; }
}
