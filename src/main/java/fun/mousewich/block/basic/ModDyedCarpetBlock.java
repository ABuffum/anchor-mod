package fun.mousewich.block.basic;

import net.minecraft.block.DyedCarpetBlock;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.util.DyeColor;

public class ModDyedCarpetBlock extends DyedCarpetBlock {
	/**
	 * @param dyeColor the color of this carpet when worn by a {@linkplain LlamaEntity llama}
	 * @param settings the block settings
	 */
	public ModDyedCarpetBlock(DyeColor dyeColor, Settings settings) { super(dyeColor, settings); }
}
