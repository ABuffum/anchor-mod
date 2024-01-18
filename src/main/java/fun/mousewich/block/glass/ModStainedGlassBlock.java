package fun.mousewich.block.glass;

import fun.mousewich.block.BlockConvertible;
import fun.mousewich.util.dye.ModDyeColor;
import fun.mousewich.util.dye.ModStainable;
import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.Block;

public class ModStainedGlassBlock extends AbstractGlassBlock implements ModStainable {
	protected final ModDyeColor color;
	public ModStainedGlassBlock(BlockConvertible block, ModDyeColor color) { this(block.asBlock(), color); }
	public ModStainedGlassBlock(Block block, ModDyeColor color) { this(Settings.copy(block), color); }
	public ModStainedGlassBlock(Settings settings, ModDyeColor color) {
		super(settings);
		this.color = color;
	}
	@Override
	public ModDyeColor GetModColor() { return this.color; }
}
