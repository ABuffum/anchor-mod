package haven.materials.wood;

import haven.materials.providers.*;
import haven.util.HavenPair;
import haven.util.HavenTorch;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;

public class BambooMaterial extends WoodMaterial implements
		TorchProvider, SoulTorchProvider,
		LogProvider, StrippedLogProvider, WoodProvider, StrippedWoodProvider, BundleProvider, StrippedBundleProvider {
	private final HavenTorch torch;
	public HavenTorch getTorch() { return torch; }
	private final HavenTorch soul_torch;
	public HavenTorch getSoulTorch() { return soul_torch; }
	private final HavenPair bundle;
	public HavenPair getBundle() { return bundle; }
	private final HavenPair stripped_bundle;
	public HavenPair getStrippedBundle() { return stripped_bundle; }
	private final HavenPair log;
	public HavenPair getLog() { return log; }
	private final HavenPair stripped_log;
	public HavenPair getStrippedLog() { return stripped_log; }
	private final HavenPair wood;
	public HavenPair getWood() { return wood; }
	private final HavenPair stripped_wood;
	public HavenPair getStrippedWood() { return stripped_wood; }

	public BambooMaterial(String name, MapColor mapColor) {
		super(name, mapColor, true);
		torch = new HavenTorch(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(luminance(14)).sounds(BlockSoundGroup.BAMBOO), ParticleTypes.FLAME);
		soul_torch = new HavenTorch(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(luminance(10)).sounds(BlockSoundGroup.BAMBOO), ParticleTypes.SOUL_FIRE_FLAME);
		bundle = new HavenPair(new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(2.0F).sounds(BlockSoundGroup.BAMBOO)));
		stripped_bundle = new HavenPair(new PillarBlock(AbstractBlock.Settings.copy(bundle.BLOCK)));
		log = new HavenPair(new PillarBlock(AbstractBlock.Settings.copy(bundle.BLOCK)));
		stripped_log = new HavenPair(new PillarBlock(AbstractBlock.Settings.copy(log.BLOCK)));
		wood = new HavenPair(new PillarBlock(AbstractBlock.Settings.copy(bundle.BLOCK)));
		stripped_wood = new HavenPair(new PillarBlock(AbstractBlock.Settings.copy(log.BLOCK)));
	}

	public boolean contains(Block block) {
		return torch.contains(block) || soul_torch.contains(block)
				|| block == bundle.BLOCK || block == stripped_bundle.BLOCK
				|| block == log.BLOCK || block == stripped_log.BLOCK
				|| block == wood.BLOCK || block == stripped_wood.BLOCK || super.contains(block);
	}
	public boolean contains(Item item) {
		return torch.contains(item) || soul_torch.contains(item)
				|| item == bundle.ITEM || item == stripped_bundle.ITEM
				|| item == log.ITEM || item == stripped_log.ITEM
				|| item == wood.ITEM || item == stripped_wood.ITEM || super.contains(item);
	}
}
