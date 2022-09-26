package haven.util;

import haven.HavenMod;
import haven.containers.BlockContainer;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.*;
import net.minecraft.block.Block;

import java.util.HashMap;
import java.util.Map;

public class StrippedBlockUtils {
	public static final Map<Block, Block> STRIPPED_BLOCKS = new HashMap<Block, Block>();

	static {
		for(BaseMaterial material : HavenMod.MATERIALS) {
			if (material instanceof StrippedBundleProvider bundle) STRIPPED_BLOCKS.put(bundle.getBundle().BLOCK, bundle.getStrippedBundle().BLOCK);
			if (material instanceof StrippedLogProvider log) STRIPPED_BLOCKS.put(log.getLog().BLOCK, log.getStrippedLog().BLOCK);
			if (material instanceof StrippedWoodProvider wood) STRIPPED_BLOCKS.put(wood.getWood().BLOCK, wood.getStrippedWood().BLOCK);
			if (material instanceof StrippedStemProvider stem) STRIPPED_BLOCKS.put(stem.getStem().BLOCK, stem.getStrippedStem().BLOCK);
			if (material instanceof StrippedHyphaeProvider hyphae) STRIPPED_BLOCKS.put(hyphae.getHyphae().BLOCK, hyphae.getStrippedHyphae().BLOCK);
		}
	}
}
