package haven.containers;

import haven.HavenMod;
import haven.blocks.FlowerSeedBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import net.minecraft.block.PlantBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.sound.BlockSoundGroup;

public class FlowerSeedContainer extends BlockContainer {
	public FlowerSeedContainer(PlantBlock flower) {
		this(flower, AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS));
	}
	public FlowerSeedContainer(PlantBlock flower, AbstractBlock.Settings settings) {
		this(new FlowerSeedBlock(flower, settings));
	}
	private FlowerSeedContainer(FlowerSeedBlock seedBlock) {
		super(seedBlock, new BlockItem(seedBlock, FlowerContainer.SeedSettings()));
	}
}
