package haven.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;

public class HavenSlabBlock extends SlabBlock {
	public HavenSlabBlock(Block block) {
		this(Settings.copy(block));
	}
	public HavenSlabBlock(Settings settings) {
		super(settings);
	}
}
