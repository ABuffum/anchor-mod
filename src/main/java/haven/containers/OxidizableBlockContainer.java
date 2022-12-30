package haven.containers;

import haven.util.OxidationScale;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import static net.minecraft.block.Oxidizable.OxidizationLevel;

import java.util.function.Function;

public class OxidizableBlockContainer {
	private final BlockContainer unaffected;
	public BlockContainer getUnaffected() { return unaffected; }
	private final BlockContainer exposed;
	public BlockContainer getExposed() { return exposed; }
	private final BlockContainer weathered;
	public BlockContainer getWeathered() { return weathered; }
	private final BlockContainer oxidized;
	public BlockContainer getOxidized() { return oxidized; }
	private final WaxedBlockContainer waxed_unaffected;
	public WaxedBlockContainer getWaxedUnaffected() { return waxed_unaffected; }
	private final WaxedBlockContainer waxed_exposed;
	public WaxedBlockContainer getWaxedExposed() { return waxed_exposed; }
	private final WaxedBlockContainer waxed_weathered;
	public WaxedBlockContainer getWaxedWeathered() { return waxed_weathered; }
	private final WaxedBlockContainer waxed_oxidized;
	public WaxedBlockContainer getWaxedOxidized() { return waxed_oxidized; }

	public static interface OxidizableBlockSupplier {
		public Block get(OxidizationLevel level, AbstractBlock.Settings settings);
	}

	public OxidizableBlockContainer(OxidizableBlockSupplier oxidizable, Function<AbstractBlock.Settings, Block> waxed, OxidationScale.BlockSettingsSupplier settings) {
		unaffected = new BlockContainer(oxidizable.get(OxidizationLevel.UNAFFECTED, settings.get(OxidizationLevel.UNAFFECTED)));
		exposed = new BlockContainer(oxidizable.get(OxidizationLevel.EXPOSED, settings.get(OxidizationLevel.EXPOSED)));
		weathered = new BlockContainer(oxidizable.get(OxidizationLevel.WEATHERED, settings.get(OxidizationLevel.WEATHERED)));
		oxidized = new BlockContainer(oxidizable.get(OxidizationLevel.OXIDIZED, settings.get(OxidizationLevel.OXIDIZED)));
		waxed_unaffected = new WaxedBlockContainer(unaffected, waxed.apply(settings.get(OxidizationLevel.UNAFFECTED)));
		waxed_exposed = new WaxedBlockContainer(exposed, waxed.apply(settings.get(OxidizationLevel.EXPOSED)));
		waxed_weathered = new WaxedBlockContainer(weathered, waxed.apply(settings.get(OxidizationLevel.WEATHERED)));
		waxed_oxidized = new WaxedBlockContainer(oxidized, waxed.apply(settings.get(OxidizationLevel.OXIDIZED)));
	}

	public OxidizableBlockContainer(OxidizableBlockSupplier oxidizable, Function<AbstractBlock.Settings, Block> waxed, AbstractBlock.Settings settings) {
		unaffected = new BlockContainer(oxidizable.get(OxidizationLevel.UNAFFECTED, settings));
		exposed = new BlockContainer(oxidizable.get(OxidizationLevel.EXPOSED, settings));
		weathered = new BlockContainer(oxidizable.get(OxidizationLevel.WEATHERED, settings));
		oxidized = new BlockContainer(oxidizable.get(OxidizationLevel.OXIDIZED, settings));
		waxed_unaffected = new WaxedBlockContainer(unaffected, waxed.apply(settings));
		waxed_exposed = new WaxedBlockContainer(exposed, waxed.apply(settings));
		waxed_weathered = new WaxedBlockContainer(weathered, waxed.apply(settings));
		waxed_oxidized = new WaxedBlockContainer(oxidized, waxed.apply(settings));
	}

	private OxidizableBlockContainer(BlockContainer u, BlockContainer e, BlockContainer w, BlockContainer o, Block wu, Block we, Block ww, Block wo) {
		unaffected = u;
		exposed = e;
		weathered = w;
		oxidized = o;
		waxed_unaffected = new WaxedBlockContainer(u, wu);
		waxed_exposed = new WaxedBlockContainer(e, we);
		waxed_weathered = new WaxedBlockContainer(w, ww);
		waxed_oxidized = new WaxedBlockContainer(o, wo);
	}

	public static OxidizableBlockContainer Stairs(Function<OxidizationLevel, Block> baseBlock, Function<OxidizationLevel, Block> waxed) {
		BlockContainer u = new BlockContainer(baseBlock.apply(OxidizationLevel.UNAFFECTED));
		BlockContainer e = new BlockContainer(baseBlock.apply(OxidizationLevel.EXPOSED));
		BlockContainer w = new BlockContainer(baseBlock.apply(OxidizationLevel.WEATHERED));
		BlockContainer o = new BlockContainer(baseBlock.apply(OxidizationLevel.OXIDIZED));
		return new OxidizableBlockContainer(u, e, w, o,
				waxed.apply(OxidizationLevel.UNAFFECTED),
				waxed.apply(OxidizationLevel.EXPOSED),
				waxed.apply(OxidizationLevel.WEATHERED),
				waxed.apply(OxidizationLevel.OXIDIZED));
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
