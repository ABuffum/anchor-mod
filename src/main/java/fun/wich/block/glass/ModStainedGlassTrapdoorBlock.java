package fun.wich.block.glass;

import fun.wich.block.BlockConvertible;
import fun.wich.util.dye.ModDyeColor;
import fun.wich.util.dye.ModStainable;
import net.minecraft.block.Block;

public class ModStainedGlassTrapdoorBlock extends AbstractGlassTrapdoorBlock implements ModStainable {
	protected final ModDyeColor color;
	public ModStainedGlassTrapdoorBlock(BlockConvertible block, BlockConvertible slab, ModDyeColor color) { this(block.asBlock(), slab.asBlock(), color); }
	public ModStainedGlassTrapdoorBlock(Block block, Block slab, ModDyeColor color) {
		super(block, slab);
		this.color = color;
	}
	@Override
	public ModDyeColor GetModColor() { return this.color; }
}
