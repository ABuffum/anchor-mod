package haven.blocks.basic;

import net.minecraft.block.Block;
import net.minecraft.block.FenceBlock;

public class ModFenceBlock extends FenceBlock {
	public ModFenceBlock(Block block) {
		this(Settings.copy(block));
	}

	public ModFenceBlock(Settings settings) {
		super(settings);
	}
}
