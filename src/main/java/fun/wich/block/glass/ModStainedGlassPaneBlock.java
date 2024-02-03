package fun.wich.block.glass;

import fun.wich.util.dye.ModDyeColor;
import fun.wich.util.dye.ModStainable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.PaneBlock;

public class ModStainedGlassPaneBlock extends PaneBlock implements ModStainable {
	protected final ModDyeColor color;
	public ModStainedGlassPaneBlock(AbstractBlock.Settings settings, ModDyeColor color) {
		super(settings);
		this.color = color;
		this.setDefaultState(this.stateManager.getDefaultState().with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(WATERLOGGED, false));
	}
	@Override
	public ModDyeColor GetModColor() { return this.color; }
}
