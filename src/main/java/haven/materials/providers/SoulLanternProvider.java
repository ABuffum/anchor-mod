package haven.materials.providers;

import haven.util.HavenPair;
import net.minecraft.block.Block;

public interface SoulLanternProvider {
	public HavenPair getSoulLantern();
	public Block getUnlitSoulLantern();
}
