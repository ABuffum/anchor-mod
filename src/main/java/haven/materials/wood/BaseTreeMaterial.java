package haven.materials.wood;

import haven.blocks.basic.HavenLeavesBlock;
import haven.materials.providers.*;
import haven.util.HavenPair;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public abstract class BaseTreeMaterial extends WoodMaterial implements
		LogProvider, StrippedLogProvider, WoodProvider, StrippedWoodProvider, LeavesProvider {
	private final HavenPair log;
	public HavenPair getLog() { return log; }
	private final HavenPair stripped_log;
	public HavenPair getStrippedLog() { return stripped_log; }
	private final HavenPair wood;
	public HavenPair getWood() { return wood; }
	private final HavenPair stripped_wood;
	public HavenPair getStrippedWood() { return stripped_wood; }
	private final HavenPair leaves;
	public HavenPair getLeaves() { return leaves; }

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
		log = new HavenPair(new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(2.0F).sounds(BlockSoundGroup.WOOD)));
		stripped_log = new HavenPair(new PillarBlock(AbstractBlock.Settings.copy(log.BLOCK)));
		wood = new HavenPair(new PillarBlock(AbstractBlock.Settings.copy(log.BLOCK)));
		stripped_wood = new HavenPair(new PillarBlock(AbstractBlock.Settings.copy(log.BLOCK)));
		leaves = new HavenPair(new HavenLeavesBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2F).ticksRandomly().sounds(leafSounds).nonOpaque().allowsSpawning(BaseTreeMaterial::canSpawnOnLeaves).suffocates(BaseTreeMaterial::never).blockVision(BaseTreeMaterial::never)));
	}

	public boolean contains(Block block) {
		return block == log.BLOCK || block == stripped_log.BLOCK || block == wood.BLOCK || block == stripped_wood.BLOCK
				|| block == leaves.BLOCK || super.contains(block);
	}
	public boolean contains(Item item) {
		return item == log.ITEM || item == stripped_log.ITEM || item == wood.ITEM || item == stripped_wood.ITEM
				|| item == leaves.ITEM || super.contains(item);
	}
}
