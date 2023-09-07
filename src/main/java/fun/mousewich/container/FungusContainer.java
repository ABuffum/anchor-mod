package fun.mousewich.container;

import fun.mousewich.block.basic.ModFungusBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.tag.Tag;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.HugeFungusFeatureConfig;

import java.util.function.Supplier;

public class FungusContainer extends PottedBlockContainer {
	public static AbstractBlock.Settings Settings(MapColor mapColor) {
		return AbstractBlock.Settings.of(Material.PLANT, mapColor).breakInstantly().noCollision().sounds(BlockSoundGroup.FUNGUS);
	}

	public FungusContainer(Supplier<ConfiguredFeature<HugeFungusFeatureConfig, ?>> fungusGenerator, MapColor mapColor) {
		this(fungusGenerator, Settings(mapColor));
	}
	public FungusContainer(Supplier<ConfiguredFeature<HugeFungusFeatureConfig, ?>> fungusGenerator, AbstractBlock.Settings settings) {
		super(new ModFungusBlock(settings, fungusGenerator));
	}
	//Tags
	public FungusContainer blockTag(Tag.Identified<Block> tag) { return (FungusContainer)super.blockTag(tag); }
	public FungusContainer itemTag(Tag.Identified<Item> tag) { return (FungusContainer)super.itemTag(tag); }
}
