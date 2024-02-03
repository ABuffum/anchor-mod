package fun.wich.block.basic;

import fun.wich.block.BlockConvertible;
import net.minecraft.block.Block;
import net.minecraft.block.PaneBlock;

public class ModPaneBlock extends PaneBlock {
	public ModPaneBlock(BlockConvertible block) { this(block.asBlock()); }
	public ModPaneBlock(Block block) { this(Settings.copy(block)); }
	public ModPaneBlock(Settings settings) { super(settings); }
}
