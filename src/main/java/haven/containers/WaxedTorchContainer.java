package haven.containers;

import net.minecraft.block.AbstractBlock;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleEffect;

public class WaxedTorchContainer extends TorchContainer {
	public final TorchContainer UNWAXED;

	public WaxedTorchContainer(TorchContainer unwaxed, AbstractBlock.Settings settings, ParticleEffect particle) {
		super(settings, particle);
		UNWAXED = unwaxed;
	}

	public WaxedTorchContainer(TorchContainer unwaxed, AbstractBlock.Settings blockSettings, ParticleEffect particle, Item.Settings itemSettings) {
		super(blockSettings, particle, itemSettings);
		UNWAXED = unwaxed;
	}
}
