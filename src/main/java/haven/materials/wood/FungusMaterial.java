package haven.materials.wood;

import haven.containers.FungusContainer;
import haven.materials.providers.FungusProvider;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.item.Item;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.HugeFungusFeatureConfig;

import java.util.function.Supplier;

public class FungusMaterial extends BaseFungusMaterial implements FungusProvider {
	private final FungusContainer fungus;
	public FungusContainer getFungus() { return fungus; }

	public FungusMaterial(String name, MapColor mapColor, Supplier<ConfiguredFeature<HugeFungusFeatureConfig, ?>> fungusGenerator) {
		this(name, mapColor, fungusGenerator, false);
	}
	public FungusMaterial(String name, MapColor mapColor, Supplier<ConfiguredFeature<HugeFungusFeatureConfig, ?>> fungusGenerator, boolean isFlammable) {
		super(name, mapColor, isFlammable);
		fungus = new FungusContainer(fungusGenerator, mapColor);
	}

	public boolean contains(Block block) {
		return fungus.contains(block) || super.contains(block);
	}
	public boolean contains(Item item) {
		return fungus.contains(item) || super.contains(item);
	}
}
