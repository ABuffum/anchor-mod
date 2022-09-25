package haven.util;

import net.minecraft.block.Block;

import java.util.HashMap;
import java.util.Map;

public class StrippedBlockUtils {
	public static final Map<Block, Block> STRIPPED_BLOCKS = new HashMap<Block, Block>();

	public static void Register(Block unstripped, Block stripped) {
		STRIPPED_BLOCKS.put(unstripped, stripped);
	}
}
