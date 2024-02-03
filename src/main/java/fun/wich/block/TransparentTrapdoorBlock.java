package fun.wich.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Direction;

public class TransparentTrapdoorBlock extends ThinTrapdoorBlock {
	protected final Block baseBlock;
	public Block getBaseBlock() { return baseBlock; }
	public TransparentTrapdoorBlock(Block block, SoundEvent openSound, SoundEvent closeSound) {
		super(Settings.copy(block), openSound, closeSound);
		this.baseBlock = block;
	}
	@Override
	public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
		if (stateFrom.isOf(baseBlock)) return true;
		if (stateFrom.isOf(this)) {
			boolean open = stateFrom.get(OPEN);
			if (!open) {
				if (!state.get(OPEN)) {
					BlockHalf from = stateFrom.get(HALF);
					if (from == state.get(HALF)) {
						if (direction.getAxis().isHorizontal()) return true;
					}
					else if (from == BlockHalf.BOTTOM) {
						if (direction == Direction.UP) return true;
					}
					else if (direction == Direction.DOWN) return true;
				}
			}
			else {
				if (!state.get(OPEN)) {
					if (stateFrom.get(FACING) == direction.getOpposite()) return true;
				}
			}
		}
		return super.isSideInvisible(state, stateFrom, direction);
	}
}
