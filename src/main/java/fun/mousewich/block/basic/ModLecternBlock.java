package fun.mousewich.block.basic;

import fun.mousewich.block.BlockConvertible;
import net.minecraft.block.Block;
import net.minecraft.block.LecternBlock;

import java.util.HashSet;
import java.util.Set;

public class ModLecternBlock extends LecternBlock {
	public static final Set<Block> LECTERNS = new HashSet<>();

	public ModLecternBlock(BlockConvertible block) { this(block.asBlock()); }
	public ModLecternBlock(Block block) { this(Settings.copy(block)); }
	public ModLecternBlock(Settings settings) {
		super(settings);
		LECTERNS.add(this);
	}
}
