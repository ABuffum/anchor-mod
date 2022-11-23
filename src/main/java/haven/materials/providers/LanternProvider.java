package haven.materials.providers;

import haven.containers.BlockContainer;
import net.minecraft.block.Block;

public interface LanternProvider {
	public BlockContainer getLantern();
	public Block getUnlitLantern();

	public default boolean containsLantern(Block block) {
		return block == getLantern().getBlock() || block == getUnlitLantern();
	}
}
