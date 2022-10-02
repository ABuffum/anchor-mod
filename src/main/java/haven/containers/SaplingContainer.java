package haven.containers;

import haven.blocks.basic.HavenSaplingBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.sound.BlockSoundGroup;

import java.util.function.Supplier;

public class SaplingContainer extends PottedBlockContainer {
	public static final AbstractBlock.Settings SETTINGS = AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS);

	public SaplingContainer(Supplier<SaplingGenerator> saplingGenerator) {
		this(saplingGenerator, SETTINGS);
	}
	public SaplingContainer(Supplier<SaplingGenerator> saplingGenerator, AbstractBlock.Settings settings) {
		super(new HavenSaplingBlock(saplingGenerator.get(), settings));
	}
}
