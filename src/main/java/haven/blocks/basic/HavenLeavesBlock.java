package haven.blocks.basic;

import net.minecraft.block.Block;
import net.minecraft.block.LeavesBlock;

public class HavenLeavesBlock extends LeavesBlock {
	public HavenLeavesBlock(Block block) {
		this(Settings.copy(block));
	}
	public HavenLeavesBlock(Settings settings) {
		super(settings);
	}
}
