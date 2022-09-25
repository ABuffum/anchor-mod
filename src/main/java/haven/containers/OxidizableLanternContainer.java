package haven.containers;

import haven.HavenMod;
import haven.util.OxidationScale;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Oxidizable;
import net.minecraft.item.Item;

import java.util.function.Function;

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
		public Block get(Oxidizable.OxidizationLevel level, AbstractBlock.Settings settings);
	}
	public static interface UnlitLanternSupplier {
		public Block get(AbstractBlock.Settings settings);
	}

	public OxidizableLanternContainer(OxidizableBlockSupplier oxidizable, Function<AbstractBlock.Settings, Block> waxed, OxidationScale.BlockSettingsSupplier settings) {
		super(oxidizable, waxed, settings);
		unlit_unaffected = oxidizable.get(Oxidizable.OxidizationLevel.UNAFFECTED, HavenMod.UnlitLanternSettings().dropsLike(getUnaffected().BLOCK));
		unlit_exposed = oxidizable.get(Oxidizable.OxidizationLevel.EXPOSED, HavenMod.UnlitLanternSettings().dropsLike(getExposed().BLOCK));
		unlit_weathered = oxidizable.get(Oxidizable.OxidizationLevel.WEATHERED, HavenMod.UnlitLanternSettings().dropsLike(getWeathered().BLOCK));
		unlit_oxidized= oxidizable.get(Oxidizable.OxidizationLevel.OXIDIZED, HavenMod.UnlitLanternSettings().dropsLike(getOxidized().BLOCK));

		unlit_waxed_unaffected = waxed.apply(HavenMod.UnlitLanternSettings().dropsLike(getWaxedUnaffected().BLOCK));
		unlit_waxed_exposed = waxed.apply(HavenMod.UnlitLanternSettings().dropsLike(getWaxedExposed().BLOCK));
		unlit_waxed_weathered = waxed.apply(HavenMod.UnlitLanternSettings().dropsLike(getWaxedWeathered().BLOCK));
		unlit_waxed_oxidized= waxed.apply(HavenMod.UnlitLanternSettings().dropsLike(getWaxedOxidized().BLOCK));
	}

	public OxidizableLanternContainer(OxidizableBlockSupplier oxidizable, Function<AbstractBlock.Settings, Block> waxed, AbstractBlock.Settings settings) {
		super(oxidizable, waxed, settings);
		unlit_unaffected = oxidizable.get(Oxidizable.OxidizationLevel.UNAFFECTED, HavenMod.UnlitLanternSettings().dropsLike(getUnaffected().BLOCK));
		unlit_exposed = oxidizable.get(Oxidizable.OxidizationLevel.EXPOSED, HavenMod.UnlitLanternSettings().dropsLike(getExposed().BLOCK));
		unlit_weathered = oxidizable.get(Oxidizable.OxidizationLevel.WEATHERED, HavenMod.UnlitLanternSettings().dropsLike(getWeathered().BLOCK));
		unlit_oxidized= oxidizable.get(Oxidizable.OxidizationLevel.OXIDIZED, HavenMod.UnlitLanternSettings().dropsLike(getOxidized().BLOCK));

		unlit_waxed_unaffected = waxed.apply(HavenMod.UnlitLanternSettings().dropsLike(getWaxedUnaffected().BLOCK));
		unlit_waxed_exposed = waxed.apply(HavenMod.UnlitLanternSettings().dropsLike(getWaxedExposed().BLOCK));
		unlit_waxed_weathered = waxed.apply(HavenMod.UnlitLanternSettings().dropsLike(getWaxedWeathered().BLOCK));
		unlit_waxed_oxidized= waxed.apply(HavenMod.UnlitLanternSettings().dropsLike(getWaxedOxidized().BLOCK));
	}

	public boolean contains(Block block) {
		return block == unlit_unaffected || block == unlit_exposed || block == unlit_weathered || block == unlit_oxidized
				|| block == unlit_waxed_unaffected || block == unlit_waxed_exposed || block == unlit_waxed_weathered || block == unlit_waxed_oxidized
				|| super.contains(block);
	}
}
