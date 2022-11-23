package haven.util;

import haven.ModBase;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.*;
import net.minecraft.block.Block;

import java.util.HashMap;
import java.util.Map;

public class StrippedBlockUtils {
	public static final Map<Block, Block> STRIPPED_BLOCKS = new HashMap<Block, Block>();

	static {
		for(BaseMaterial material : ModBase.MATERIALS) {
			if (material instanceof StrippedBundleProvider bundle) STRIPPED_BLOCKS.put(bundle.getBundle().getBlock(), bundle.getStrippedBundle().getBlock());
			if (material instanceof StrippedLogProvider log) STRIPPED_BLOCKS.put(log.getLog().getBlock(), log.getStrippedLog().getBlock());
			if (material instanceof StrippedWoodProvider wood) STRIPPED_BLOCKS.put(wood.getWood().getBlock(), wood.getStrippedWood().getBlock());
			if (material instanceof StrippedStemProvider stem) STRIPPED_BLOCKS.put(stem.getStem().getBlock(), stem.getStrippedStem().getBlock());
			if (material instanceof StrippedHyphaeProvider hyphae) STRIPPED_BLOCKS.put(hyphae.getHyphae().getBlock(), hyphae.getStrippedHyphae().getBlock());
		}
	}
}
