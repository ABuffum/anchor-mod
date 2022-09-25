package haven.materials.providers;

import haven.containers.BlockContainer;
import net.minecraft.block.Block;

public interface SoulLanternProvider {
	public BlockContainer getSoulLantern();
	public Block getUnlitSoulLantern();

	public default boolean containsSoulLantern(Block block) {
		return block == getSoulLantern().BLOCK || block == getUnlitSoulLantern();
	}
}
