package haven.util;

import haven.HavenMod;
import haven.materials.providers.LogProvider;
import net.minecraft.block.Block;

import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;

public class StrippedBlockUtils {
	public static final Map<Block, Block> STRIPPED_BLOCKS = new HashMap<Block, Block>();

	public static void Register(Block unstripped, Block stripped) {
		STRIPPED_BLOCKS.put(unstripped, stripped);
	}
}
