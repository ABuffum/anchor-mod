package fun.mousewich.block;

import fun.mousewich.block.basic.ModSlabBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.SlabType;
import net.minecraft.util.math.Direction;

public class TransparentSlabBlock extends ModSlabBlock {
	protected final Block baseBlock;
	public Block getBaseBlock() { return baseBlock; }
	public TransparentSlabBlock(Block block) {
		super(block);
		this.baseBlock = block;
	}
	@Override
	public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
		if (stateFrom.isOf(baseBlock)) return true;
		if (stateFrom.isOf(this)) {
			SlabType from = stateFrom.get(TYPE);
			if (from == SlabType.DOUBLE) return true;
			else if (from == state.get(TYPE)) {
				if (direction.getAxis().isHorizontal()) return true;
			}
			else if (from == SlabType.BOTTOM) {
				if (direction == Direction.UP) return true;
			}
			else if (direction == Direction.DOWN) return true;
		}
		return super.isSideInvisible(state, stateFrom, direction);
	}
}
