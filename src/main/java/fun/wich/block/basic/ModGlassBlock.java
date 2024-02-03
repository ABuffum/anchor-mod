package fun.wich.block.basic;

import fun.wich.container.IBlockItemContainer;
import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.Block;

public class ModGlassBlock extends AbstractGlassBlock {
	public ModGlassBlock(Settings settings) { super(settings); }
	public ModGlassBlock(Block block) { this(Settings.copy(block)); }
	public ModGlassBlock(IBlockItemContainer block) { this(block.asBlock()); }
}
