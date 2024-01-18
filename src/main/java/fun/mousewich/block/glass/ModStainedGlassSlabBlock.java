package fun.mousewich.block.glass;

import fun.mousewich.block.BlockConvertible;
import fun.mousewich.util.dye.ModDyeColor;
import fun.mousewich.util.dye.ModStainable;
import net.minecraft.block.Block;

public class ModStainedGlassSlabBlock extends AbstractGlassSlabBlock implements ModStainable {
	protected final ModDyeColor color;
	public ModStainedGlassSlabBlock(BlockConvertible block, ModDyeColor color) { this(block.asBlock(), color); }
	public ModStainedGlassSlabBlock(Block block, ModDyeColor color) {
		super(block);
		this.color = color;
	}
	@Override
	public ModDyeColor GetModColor() { return this.color; }
}