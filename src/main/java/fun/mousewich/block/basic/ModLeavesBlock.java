package fun.mousewich.block.basic;

import net.minecraft.block.Block;
import net.minecraft.block.LeavesBlock;

public class ModLeavesBlock extends LeavesBlock {
	public ModLeavesBlock(Block block) { this(Settings.copy(block)); }
	public ModLeavesBlock(Settings settings) { super(settings); }
}
