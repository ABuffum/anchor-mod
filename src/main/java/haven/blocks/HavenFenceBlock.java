package haven.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.FenceBlock;

public class HavenFenceBlock extends FenceBlock {
	public HavenFenceBlock(Block block) {
		this(Settings.copy(block));
	}

	public HavenFenceBlock(Settings settings) {
		super(settings);
	}
}
