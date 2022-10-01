package haven.blocks.basic;

import net.minecraft.block.DyedCarpetBlock;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.util.DyeColor;

public class HavenDyedCarpetBlock extends DyedCarpetBlock {
	/**
	 * @param dyeColor the color of this carpet when worn by a {@linkplain LlamaEntity llama}
	 * @param settings
	 */
	public HavenDyedCarpetBlock(DyeColor dyeColor, Settings settings) {
		super(dyeColor, settings);
	}
}
