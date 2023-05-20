package fun.mousewich.block.basic;

import fun.mousewich.block.BlockConvertible;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class ModFallingBlock extends FallingBlock {
	protected final int color;
	public ModFallingBlock(BlockConvertible block, int color) { this(block.asBlock(), color); }
	public ModFallingBlock(Block block, int color) { this(Settings.copy(block), color); }
	public ModFallingBlock(Settings settings, int color) {
		super(settings);
		this.color = color;
	}
	@Override
	public int getColor(BlockState state, BlockView world, BlockPos pos) { return color; }
}
