package haven.containers;

import haven.util.OxidationScale;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleEffect;

import static net.minecraft.block.Oxidizable.OxidizationLevel;

public class OxidizableTorchContainer {
	private final TorchContainer unaffected;
	public TorchContainer getUnaffected() { return unaffected; }
	private final TorchContainer exposed;
	public TorchContainer getExposed() { return exposed; }
	private final TorchContainer weathered;
	public TorchContainer getWeathered() { return weathered; }
	private final TorchContainer oxidized;
	public TorchContainer getOxidized() { return oxidized; }
	private final WaxedTorchContainer waxed_unaffected;
	public WaxedTorchContainer getWaxedUnaffected() { return waxed_unaffected; }
	private final WaxedTorchContainer waxed_exposed;
	public WaxedTorchContainer getWaxedExposed() { return waxed_exposed; }
	private final WaxedTorchContainer waxed_weathered;
	public WaxedTorchContainer getWaxedWeathered() { return waxed_weathered; }
	private final WaxedTorchContainer waxed_oxidized;
	public WaxedTorchContainer getWaxedOxidized() { return waxed_oxidized; }

	public OxidizableTorchContainer(ParticleEffect particle, OxidationScale.BlockSettingsSupplier settings) {
		unaffected = TorchContainer.Oxidizable(OxidizationLevel.UNAFFECTED, settings, particle);
		exposed = TorchContainer.Oxidizable(OxidizationLevel.EXPOSED, settings, particle);
		weathered = TorchContainer.Oxidizable(OxidizationLevel.WEATHERED, settings, particle);
		oxidized = TorchContainer.Oxidizable(OxidizationLevel.OXIDIZED, settings, particle);
		waxed_unaffected = new WaxedTorchContainer(unaffected, settings.get(OxidizationLevel.UNAFFECTED), particle);
		waxed_exposed = new WaxedTorchContainer(exposed, settings.get(OxidizationLevel.UNAFFECTED), particle);
		waxed_weathered = new WaxedTorchContainer(weathered, settings.get(OxidizationLevel.UNAFFECTED), particle);
		waxed_oxidized = new WaxedTorchContainer(oxidized, settings.get(OxidizationLevel.UNAFFECTED), particle);
	}

	public OxidizableTorchContainer(ParticleEffect particle, AbstractBlock.Settings settings) {
		unaffected = TorchContainer.Oxidizable(OxidizationLevel.UNAFFECTED, settings, particle);
		exposed = TorchContainer.Oxidizable(OxidizationLevel.EXPOSED, settings, particle);
		weathered = TorchContainer.Oxidizable(OxidizationLevel.WEATHERED, settings, particle);
		oxidized = TorchContainer.Oxidizable(OxidizationLevel.OXIDIZED, settings, particle);
		waxed_unaffected = new WaxedTorchContainer(unaffected, settings, particle);
		waxed_exposed = new WaxedTorchContainer(exposed, settings, particle);
		waxed_weathered = new WaxedTorchContainer(weathered, settings, particle);
		waxed_oxidized = new WaxedTorchContainer(oxidized, settings, particle);
	}

	public boolean contains(Block block) {
		return unaffected.contains(block) || exposed.contains(block) || weathered.contains(block) || oxidized.contains(block)
				|| waxed_unaffected.contains(block) || waxed_exposed.contains(block) || waxed_weathered.contains(block) || waxed_oxidized.contains(block);
	}
	public boolean contains(Item item) {
		return unaffected.contains(item) || exposed.contains(item) || weathered.contains(item) || oxidized.contains(item)
				|| waxed_unaffected.contains(item) || waxed_exposed.contains(item) || waxed_weathered.contains(item) || waxed_oxidized.contains(item);
	}
}
