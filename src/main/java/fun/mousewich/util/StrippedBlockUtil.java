package fun.mousewich.util;

import fun.mousewich.block.BlockConvertible;
import net.minecraft.block.Block;

import java.util.HashMap;
import java.util.Map;

public class StrippedBlockUtil {
	public static final Map<Block, Block> STRIPPED_BLOCKS = new HashMap<>();

	public static void Register(BlockConvertible block, BlockConvertible stripped) { Register(block.asBlock(), stripped.asBlock()); }
	public static void Register(Block block, BlockConvertible stripped) { Register(block, stripped.asBlock()); }
	public static void Register(BlockConvertible block, Block stripped) { Register(block.asBlock(), stripped); }
	public static void Register(Block block, Block stripped) {
		if (STRIPPED_BLOCKS.containsKey(block)) throw new IllegalArgumentException("Block already registered: " + block);
		STRIPPED_BLOCKS.put(block, stripped);
	}
}
