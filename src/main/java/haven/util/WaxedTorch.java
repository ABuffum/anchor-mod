package haven.util;

import net.minecraft.block.AbstractBlock;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleEffect;

public class WaxedTorch extends HavenTorch {
	public final HavenTorch UNWAXED;

	public WaxedTorch(HavenTorch unwaxed, AbstractBlock.Settings settings, ParticleEffect particle) {
		super(settings, particle);
		UNWAXED = unwaxed;
	}

	public WaxedTorch(HavenTorch unwaxed, AbstractBlock.Settings blockSettings, ParticleEffect particle, Item.Settings itemSettings) {
		super(blockSettings, particle, itemSettings);
		UNWAXED = unwaxed;
	}
}
