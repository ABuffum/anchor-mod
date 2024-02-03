package fun.wich.block.basic;

import fun.wich.block.BlockConvertible;
import net.minecraft.block.Block;
import net.minecraft.block.CraftingTableBlock;

public class ModCraftingTableBlock extends CraftingTableBlock {
	public ModCraftingTableBlock(BlockConvertible block) { this(block.asBlock()); }
	public ModCraftingTableBlock(Block block) { this(Settings.copy(block)); }
	public ModCraftingTableBlock(Settings settings) { super(settings); }
}
