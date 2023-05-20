package fun.mousewich.block.glass;

import net.minecraft.block.Block;
import net.minecraft.block.Stainable;
import net.minecraft.util.DyeColor;

public class StainedGlassSlabBlock extends AbstractGlassSlabBlock implements Stainable {
	private final DyeColor color;
	public StainedGlassSlabBlock(DyeColor color, Block block) {
		super(block);
		this.color = color;
	}
	@Override
	public DyeColor getColor() { return this.color; }
}
