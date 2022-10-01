package haven.containers;

import haven.HavenMod;
import haven.blocks.UnlitLanternBlock;
import haven.util.OxidationScale;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Oxidizable;
import net.minecraft.item.Item;

import java.util.function.Function;
import java.util.function.Supplier;

public class OxidizableLanternContainer extends OxidizableBlockContainer {
	private final Block unlit_unaffected;
	public Block getUnlitUnaffected() { return unlit_unaffected; }
	private final Block unlit_exposed;
	public Block getUnlitExposed() { return unlit_exposed; }
	private final Block unlit_weathered;
	public Block getUnlitWeathered() { return unlit_weathered; }
	private final Block unlit_oxidized;
	public Block getUnlitOxidized() { return unlit_oxidized; }
	private final Block unlit_waxed_unaffected;
	public Block getUnlitWaxedUnaffected() { return unlit_waxed_unaffected; }
	private final Block unlit_waxed_exposed;
	public Block getUnlitWaxedExposed() { return unlit_waxed_exposed; }
	private final Block unlit_waxed_weathered;
	public Block getUnlitWaxedWeathered() { return unlit_waxed_weathered; }
	private final Block unlit_waxed_oxidized;
	public Block getUnlitWaxedOxidized() { return unlit_waxed_oxidized; }

	public static interface OxidizedUnlitLanternSupplier {
		public Block get(Supplier<BlockContainer> pickStack, Oxidizable.OxidizationLevel level, AbstractBlock.Settings settings);
	}
	public static interface UnlitLanternSupplier {
		public Block get(Supplier<BlockContainer> pickStack, AbstractBlock.Settings settings);
	}

	public OxidizableLanternContainer(OxidizableBlockSupplier oxidizable, OxidizedUnlitLanternSupplier unlit, Function<AbstractBlock.Settings, Block> waxed, UnlitLanternSupplier waxedUnlit, OxidationScale.BlockSettingsSupplier settings) {
		super(oxidizable, waxed, settings);
		unlit_unaffected = unlit.get(this::getUnaffected, Oxidizable.OxidizationLevel.UNAFFECTED, HavenMod.UnlitLanternSettings());
		unlit_exposed = unlit.get(this::getExposed, Oxidizable.OxidizationLevel.EXPOSED, HavenMod.UnlitLanternSettings());
		unlit_weathered = unlit.get(this::getWeathered, Oxidizable.OxidizationLevel.WEATHERED, HavenMod.UnlitLanternSettings());
		unlit_oxidized= unlit.get(this::getOxidized, Oxidizable.OxidizationLevel.OXIDIZED, HavenMod.UnlitLanternSettings());

		unlit_waxed_unaffected = waxedUnlit.get(this::getWaxedUnaffected, HavenMod.UnlitLanternSettings());
		unlit_waxed_exposed = waxedUnlit.get(this::getWaxedExposed, HavenMod.UnlitLanternSettings());
		unlit_waxed_weathered = waxedUnlit.get(this::getWaxedWeathered, HavenMod.UnlitLanternSettings());
		unlit_waxed_oxidized= waxedUnlit.get(this::getWaxedOxidized, HavenMod.UnlitLanternSettings());
	}

	public OxidizableLanternContainer(OxidizableBlockSupplier oxidizable, OxidizedUnlitLanternSupplier unlit, Function<AbstractBlock.Settings, Block> waxed, UnlitLanternSupplier waxedUnlit, AbstractBlock.Settings settings) {
		super(oxidizable, waxed, settings);
		unlit_unaffected = unlit.get(this::getUnaffected, Oxidizable.OxidizationLevel.UNAFFECTED, HavenMod.UnlitLanternSettings());
		unlit_exposed = unlit.get(this::getExposed, Oxidizable.OxidizationLevel.EXPOSED, HavenMod.UnlitLanternSettings());
		unlit_weathered = unlit.get(this::getWeathered, Oxidizable.OxidizationLevel.WEATHERED, HavenMod.UnlitLanternSettings());
		unlit_oxidized= unlit.get(this::getOxidized, Oxidizable.OxidizationLevel.OXIDIZED, HavenMod.UnlitLanternSettings());

		unlit_waxed_unaffected = waxedUnlit.get(this::getWaxedUnaffected, HavenMod.UnlitLanternSettings());
		unlit_waxed_exposed = waxedUnlit.get(this::getWaxedExposed, HavenMod.UnlitLanternSettings());
		unlit_waxed_weathered = waxedUnlit.get(this::getWaxedWeathered, HavenMod.UnlitLanternSettings());
		unlit_waxed_oxidized= waxedUnlit.get(this::getWaxedOxidized, HavenMod.UnlitLanternSettings());
	}

	public boolean contains(Block block) {
		return block == unlit_unaffected || block == unlit_exposed || block == unlit_weathered || block == unlit_oxidized
				|| block == unlit_waxed_unaffected || block == unlit_waxed_exposed || block == unlit_waxed_weathered || block == unlit_waxed_oxidized
				|| super.contains(block);
	}
}
