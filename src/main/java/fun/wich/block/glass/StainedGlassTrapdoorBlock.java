package fun.wich.block.glass;

import net.minecraft.block.Block;
import net.minecraft.block.Stainable;
import net.minecraft.util.DyeColor;

public class StainedGlassTrapdoorBlock extends AbstractGlassTrapdoorBlock implements Stainable {
	private final DyeColor color;
	public StainedGlassTrapdoorBlock(DyeColor color, Block block, Block slab) {
		super(block, slab);
		this.color = color;
	}
	@Override
	public DyeColor getColor() { return this.color; }
}
