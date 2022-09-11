package haven.util;

import haven.blocks.complex.HavenSaplingBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.sound.BlockSoundGroup;

import java.util.function.Supplier;

public class HavenSapling extends PottedBlock {
	public static final Block.Settings SETTINGS = FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS);

	public HavenSapling(Supplier<SaplingGenerator> saplingGenerator) {
		this(saplingGenerator, SETTINGS);
	}
	public HavenSapling(Supplier<SaplingGenerator> saplingGenerator, AbstractBlock.Settings settings) {
		super(new HavenSaplingBlock(saplingGenerator.get(), settings));
	}
}
