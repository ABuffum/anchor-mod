package haven.materials.wood;

import haven.HavenMod;
import haven.materials.providers.*;
import haven.util.HavenPair;
import haven.util.HavenTorch;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;

public class BambooMaterial extends WoodMaterial implements
		TorchProvider, LogProvider, StrippedLogProvider, BundleProvider, StrippedBundleProvider {
	private final HavenTorch torch;
	public HavenTorch getTorch() { return torch; }
	private final HavenPair bundle;
	public HavenPair getBundle() { return bundle; }
	private final HavenPair stripped_bundle;
	public HavenPair getStrippedBundle() { return stripped_bundle; }
	private final HavenPair log;
	public HavenPair getLog() { return log; }
	private final HavenPair stripped_log;
	public HavenPair getStrippedLog() { return stripped_log; }

	public BambooMaterial(String name, MapColor mapColor) {
		super(name, mapColor, true);
		torch = new HavenTorch(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(HavenMod.luminance(14)).sounds(BlockSoundGroup.BAMBOO), ParticleTypes.FLAME);
		bundle = new HavenPair(new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(2.0F).sounds(BlockSoundGroup.BAMBOO)));
		stripped_bundle = new HavenPair(new PillarBlock(AbstractBlock.Settings.copy(bundle.BLOCK)));
		log = new HavenPair(new PillarBlock(AbstractBlock.Settings.copy(bundle.BLOCK)));
		stripped_log = new HavenPair(new PillarBlock(AbstractBlock.Settings.copy(log.BLOCK)));
	}

	public boolean contains(Block block) {
		return block == torch.BLOCK || block == torch.WALL_BLOCK || block == bundle.BLOCK || block == stripped_bundle.BLOCK
				|| block == log.BLOCK || block == stripped_log.BLOCK || super.contains(block);
	}
	public boolean contains(Item item) {
		return item == torch.ITEM || item == bundle.ITEM || item == stripped_bundle.ITEM
				|| item == log.ITEM || item == stripped_log.ITEM || super.contains(item);
	}
}
