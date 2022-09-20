package haven.materials.providers;

import haven.util.HavenPair;
import net.minecraft.block.Block;

public interface LanternProvider {
	public HavenPair getLantern();
	public Block getUnlitLantern();
}
