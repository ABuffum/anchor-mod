package haven.blocks.basic;

import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;

public class ModSlabBlock extends SlabBlock {
	public ModSlabBlock(Block block) {
		this(Settings.copy(block));
	}
	public ModSlabBlock(Settings settings) {
		super(settings);
	}
}
