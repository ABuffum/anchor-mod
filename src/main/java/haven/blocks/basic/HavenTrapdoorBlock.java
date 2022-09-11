package haven.blocks.basic;

import net.minecraft.block.Block;
import net.minecraft.block.TrapdoorBlock;

public class HavenTrapdoorBlock extends TrapdoorBlock {
	public HavenTrapdoorBlock(Block block) {
		this(Settings.copy(block));
	}
	public HavenTrapdoorBlock(Settings settings) {
		super(settings);
	}
}
