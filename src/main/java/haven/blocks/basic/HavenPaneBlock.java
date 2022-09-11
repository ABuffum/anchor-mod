package haven.blocks.basic;

import net.minecraft.block.Block;
import net.minecraft.block.PaneBlock;

public class HavenPaneBlock extends PaneBlock {
	public HavenPaneBlock(Block block) {
		this(Settings.copy(block));
	}
	public HavenPaneBlock(Settings settings) {
		super(settings);
	}
}
