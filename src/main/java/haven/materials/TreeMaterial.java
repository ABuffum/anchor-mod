package haven.materials;

import haven.blocks.*;
import haven.boats.HavenBoat;
import haven.util.HavenPair;
import haven.util.HavenSapling;
import haven.util.HavenSign;
import net.minecraft.block.*;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.entity.EntityType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import java.util.function.Supplier;

public class TreeMaterial extends WoodMaterial {
	private final String name;
	public String getName() { return name; }

	public final boolean isFlammable;

	public final HavenPair LOG, STRIPPED_LOG;
	public final HavenPair WOOD, STRIPPED_WOOD;
	public final HavenPair LEAVES;
	public final HavenSapling SAPLING;

	public TreeMaterial(String name, MapColor mapColor, Supplier<SaplingGenerator> saplingGenerator) {
		this(name, mapColor, saplingGenerator, true);
	}
	public TreeMaterial(String name, MapColor mapColor, Supplier<SaplingGenerator> saplingGenerator, boolean isFlammable) {
		this(name, mapColor, BlockSoundGroup.GRASS, saplingGenerator, isFlammable);
	}
	public TreeMaterial(String name, MapColor mapColor, BlockSoundGroup leafSounds, Supplier<SaplingGenerator> saplingGenerator) {
		this(name, mapColor, leafSounds, saplingGenerator, true);
	}
	public TreeMaterial(String name, MapColor mapColor, BlockSoundGroup leafSounds, Supplier<SaplingGenerator> saplingGenerator, boolean isFlammable) {
		super(name, mapColor, isFlammable);
		this.name = name;
		this.isFlammable = isFlammable;
		LOG = new HavenPair(new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(2.0F).sounds(BlockSoundGroup.WOOD)));
		STRIPPED_LOG = new HavenPair(new PillarBlock(AbstractBlock.Settings.copy(LOG.BLOCK)));
		WOOD = new HavenPair(new PillarBlock(AbstractBlock.Settings.copy(LOG.BLOCK)));
		STRIPPED_WOOD = new HavenPair(new PillarBlock(AbstractBlock.Settings.copy(LOG.BLOCK)));
		LEAVES = new HavenPair(new HavenLeavesBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2F).ticksRandomly().sounds(leafSounds).nonOpaque().allowsSpawning(TreeMaterial::canSpawnOnLeaves).suffocates(TreeMaterial::never).blockVision(TreeMaterial::never)));
		SAPLING = new HavenSapling(saplingGenerator);
	}

	private static boolean canSpawnOnLeaves(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) { return type == EntityType.OCELOT || type == EntityType.PARROT; }
	private static boolean never(BlockState state, BlockView world, BlockPos pos) { return false; }
}
