package haven.materials;

import haven.blocks.HavenLeavesBlock;
import haven.util.HavenPair;
import haven.util.HavenSapling;
import net.minecraft.block.*;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.entity.EntityType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import java.util.function.Supplier;

public class BaseTreeMaterial extends WoodMaterial {
	public final HavenPair LOG, STRIPPED_LOG;
	public final HavenPair WOOD, STRIPPED_WOOD;
	public final HavenPair LEAVES;

	public BaseTreeMaterial(String name, MapColor mapColor) {
		this(name, mapColor, true);
	}
	public BaseTreeMaterial(String name, MapColor mapColor, boolean isFlammable) {
		this(name, mapColor, BlockSoundGroup.GRASS, isFlammable);
	}
	public BaseTreeMaterial(String name, MapColor mapColor, BlockSoundGroup leafSounds) {
		this(name, mapColor, leafSounds, true);
	}
	public BaseTreeMaterial(String name, MapColor mapColor, BlockSoundGroup leafSounds, boolean isFlammable) {
		super(name, mapColor, isFlammable);
		LOG = new HavenPair(new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(2.0F).sounds(BlockSoundGroup.WOOD)));
		STRIPPED_LOG = new HavenPair(new PillarBlock(AbstractBlock.Settings.copy(LOG.BLOCK)));
		WOOD = new HavenPair(new PillarBlock(AbstractBlock.Settings.copy(LOG.BLOCK)));
		STRIPPED_WOOD = new HavenPair(new PillarBlock(AbstractBlock.Settings.copy(LOG.BLOCK)));
		LEAVES = new HavenPair(new HavenLeavesBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2F).ticksRandomly().sounds(leafSounds).nonOpaque().allowsSpawning(BaseTreeMaterial::canSpawnOnLeaves).suffocates(BaseTreeMaterial::never).blockVision(BaseTreeMaterial::never)));
	}

	public static boolean canSpawnOnLeaves(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) { return type == EntityType.OCELOT || type == EntityType.PARROT; }
	public static boolean never(BlockState state, BlockView world, BlockPos pos) { return false; }
}
