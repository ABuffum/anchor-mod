package haven.materials.providers;

import haven.containers.BlockContainer;
import net.minecraft.block.Block;

public interface EnderLanternProvider {
	public BlockContainer getEnderLantern();
	public Block getUnlitEnderLantern();

	public default boolean containsEnderLantern(Block block) {
		return block == getEnderLantern().getBlock() || block == getUnlitEnderLantern();
	}
}
