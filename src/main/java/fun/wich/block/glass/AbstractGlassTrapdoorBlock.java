package fun.wich.block.glass;

import fun.wich.block.TransparentTrapdoorBlock;
import fun.wich.sound.ModSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public abstract class AbstractGlassTrapdoorBlock extends TransparentTrapdoorBlock {
	protected final Block baseSlab;
	public AbstractGlassTrapdoorBlock(Block block, Block slab) {
		super(block, ModSoundEvents.BLOCK_GLASS_TRAPDOOR_OPEN, ModSoundEvents.BLOCK_GLASS_TRAPDOOR_CLOSE);
		this.baseSlab = slab;
	}

	@Override
	public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return VoxelShapes.empty();
	}
	@Override
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) { return 1.0f; }
	@Override
	public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) { return true; }
	@Override
	public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
		if (stateFrom.isOf(baseSlab)) {
			SlabType type = stateFrom.get(Properties.SLAB_TYPE);
			if (type == SlabType.DOUBLE) return true;
			else if (type == SlabType.TOP) {
				if (!state.get(OPEN)) {
					if (state.get(HALF) == BlockHalf.TOP && direction.getAxis().isHorizontal()) return true;
				}
			}
			else if (!state.get(OPEN)) {
				if (state.get(HALF) == BlockHalf.BOTTOM && direction.getAxis().isHorizontal()) return true;
			}
		}
		return super.isSideInvisible(state, stateFrom, direction);
	}
}
