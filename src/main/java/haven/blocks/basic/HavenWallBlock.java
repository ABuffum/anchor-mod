package haven.blocks.basic;

import net.minecraft.block.Block;
import net.minecraft.block.WallBlock;

public class HavenWallBlock extends WallBlock {
	public HavenWallBlock(Block block) {
		this(Settings.copy(block));
	}
	public HavenWallBlock(Settings settings) {
		super(settings);
	}
}
