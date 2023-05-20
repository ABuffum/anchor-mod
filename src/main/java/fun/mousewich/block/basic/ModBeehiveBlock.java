package fun.mousewich.block.basic;

import fun.mousewich.block.BlockConvertible;
import fun.mousewich.container.BlockContainer;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.Block;

import java.util.HashSet;
import java.util.Set;

public class ModBeehiveBlock extends BeehiveBlock {
	public static final Set<Block> BEEHIVES = new HashSet<>();

	public ModBeehiveBlock(BlockConvertible block) { this(block.asBlock()); }
	public ModBeehiveBlock(Block block) { this(Settings.copy(block)); }
	public ModBeehiveBlock(Settings settings) {
		super(settings);
		BEEHIVES.add(this);
	}
}
