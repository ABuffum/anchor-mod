package haven.materials.providers;

import haven.util.HavenPair;
import net.minecraft.block.Block;

public interface LanternProvider {
	public HavenPair getLantern();
	public Block getUnlitLantern();

	public default boolean containsLantern(Block block) {
		return block == getLantern().BLOCK || block == getUnlitLantern();
	}
}
