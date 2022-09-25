package haven.materials.wood;

import haven.materials.providers.*;
import haven.containers.BlockContainer;
import haven.containers.TorchContainer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;

public class BambooMaterial extends WoodMaterial implements
		TorchProvider, SoulTorchProvider, CampfireProvider, SoulCampfireProvider,
		LogProvider, StrippedLogProvider, WoodProvider, StrippedWoodProvider, BundleProvider, StrippedBundleProvider {
	private final TorchContainer torch;
	public TorchContainer getTorch() { return torch; }
	private final TorchContainer soul_torch;
	public TorchContainer getSoulTorch() { return soul_torch; }
	private final BlockContainer bundle;
	public BlockContainer getBundle() { return bundle; }
	private final BlockContainer stripped_bundle;
	public BlockContainer getStrippedBundle() { return stripped_bundle; }
	private final BlockContainer log;
	public BlockContainer getLog() { return log; }
	private final BlockContainer stripped_log;
	public BlockContainer getStrippedLog() { return stripped_log; }
	private final BlockContainer wood;
	public BlockContainer getWood() { return wood; }
	private final BlockContainer stripped_wood;
	public BlockContainer getStrippedWood() { return stripped_wood; }
	private final BlockContainer campfire;
	public BlockContainer getCampfire() { return campfire; }
	private final BlockContainer soul_campfire;
	public BlockContainer getSoulCampfire() { return soul_campfire; }

	public BambooMaterial(String name, MapColor mapColor) {
		super(name, mapColor, true);
		torch = new TorchContainer(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(luminance(14)).sounds(BlockSoundGroup.BAMBOO), ParticleTypes.FLAME);
		soul_torch = new TorchContainer(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(luminance(10)).sounds(BlockSoundGroup.BAMBOO), ParticleTypes.SOUL_FIRE_FLAME);
		bundle = new BlockContainer(new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(2.0F).sounds(BlockSoundGroup.BAMBOO)));
		stripped_bundle = new BlockContainer(new PillarBlock(AbstractBlock.Settings.copy(bundle.BLOCK)));
		log = new BlockContainer(new PillarBlock(AbstractBlock.Settings.copy(bundle.BLOCK)));
		stripped_log = new BlockContainer(new PillarBlock(AbstractBlock.Settings.copy(log.BLOCK)));
		wood = new BlockContainer(new PillarBlock(AbstractBlock.Settings.copy(bundle.BLOCK)));
		stripped_wood = new BlockContainer(new PillarBlock(AbstractBlock.Settings.copy(log.BLOCK)));
		campfire = new BlockContainer(new CampfireBlock(true, 1, AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(2.0F).sounds(BlockSoundGroup.BAMBOO).luminance(createLightLevelFromLitBlockState(15)).nonOpaque()));
		soul_campfire = new BlockContainer(new CampfireBlock(false, 2, AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(2.0F).sounds(BlockSoundGroup.BAMBOO).luminance(createLightLevelFromLitBlockState(10)).nonOpaque()));
	}

	public boolean contains(Block block) {
		return torch.contains(block) || soul_torch.contains(block)
				|| campfire.contains(block) || soul_campfire.contains(block)
				|| bundle.contains(block) || stripped_bundle.contains(block)
				|| log.contains(block) || stripped_log.contains(block)
				|| wood.contains(block) || stripped_wood.contains(block) || super.contains(block);
	}
	public boolean contains(Item item) {
		return torch.contains(item) || soul_torch.contains(item)
				|| campfire.contains(item) || soul_campfire.contains(item)
				|| bundle.contains(item) || stripped_bundle.contains(item)
				|| log.contains(item) || stripped_log.contains(item)
				|| wood.contains(item) || stripped_wood.contains(item) || super.contains(item);
	}
}
