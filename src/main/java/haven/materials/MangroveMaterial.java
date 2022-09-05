package haven.materials;

import haven.blocks.HavenLeavesBlock;
import haven.blocks.PropaguleBlock;
import haven.util.HavenPair;
import haven.util.HavenSapling;
import haven.util.PottedBlock;
import net.minecraft.block.*;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.entity.EntityType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import java.util.function.Supplier;

public class MangroveMaterial extends BaseTreeMaterial {
	public final PottedBlock PROPAGULE;

	public MangroveMaterial(String name, MapColor mapColor) {
		this(name, mapColor, true);
	}
	public MangroveMaterial(String name, MapColor mapColor, boolean isFlammable) {
		this(name, mapColor, BlockSoundGroup.GRASS, isFlammable);
	}
	public MangroveMaterial(String name, MapColor mapColor, BlockSoundGroup leafSounds) {
		this(name, mapColor, leafSounds, true);
	}
	public MangroveMaterial(String name, MapColor mapColor, BlockSoundGroup leafSounds, boolean isFlammable) {
		super(name, mapColor, leafSounds, isFlammable);
		PROPAGULE = new PottedBlock(new PropaguleBlock(AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)));
	}
}
