package haven.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.Stainable;
import net.minecraft.util.DyeColor;

public class StainedGlassSlabBlock extends GlassSlabBlock implements Stainable {
	private final DyeColor color;
	public StainedGlassSlabBlock(DyeColor color, Block block) {
		super(Settings.copy(block));
		this.color = color;
	}
	public StainedGlassSlabBlock(DyeColor color, Settings settings) {
		super(settings);
		this.color = color;
	}

	@Override
	public DyeColor getColor() { return this.color; }
}
