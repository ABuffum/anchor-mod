package haven.materials.wood;

import haven.containers.BlockContainer;
import haven.containers.TorchContainer;
import haven.materials.providers.*;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;

public abstract class BaseFungusMaterial extends WoodMaterial implements
		TorchProvider, SoulTorchProvider, CampfireProvider, SoulCampfireProvider,
		StemProvider, StrippedStemProvider, HyphaeProvider, StrippedHyphaeProvider, WartBlockProvider {
	private final TorchContainer torch;
	public TorchContainer getTorch() { return torch; }
	private final TorchContainer soul_torch;
	public TorchContainer getSoulTorch() { return soul_torch; }
	private final BlockContainer stem;
	public BlockContainer getStem() { return stem; }
	private final BlockContainer stripped_stem;
	public BlockContainer getStrippedStem() { return stripped_stem; }
	private final BlockContainer hyphae;
	public BlockContainer getHyphae() { return hyphae; }
	private final BlockContainer stripped_hyphae;
	public BlockContainer getStrippedHyphae() { return stripped_hyphae; }
	private final BlockContainer wart;
	public BlockContainer getWartBlock() { return wart; }
	private final BlockContainer campfire;
	public BlockContainer getCampfire() { return campfire; }
	private final BlockContainer soul_campfire;
	public BlockContainer getSoulCampfire() { return soul_campfire; }

	public BaseFungusMaterial(String name, MapColor mapColor) {
		this(name, mapColor, false);
	}
	public BaseFungusMaterial(String name, MapColor mapColor, boolean isFlammable) {
		super(name, mapColor, isFlammable);
		torch = new TorchContainer(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(luminance(14)).sounds(BlockSoundGroup.WOOD), ParticleTypes.FLAME);
		soul_torch = new TorchContainer(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(luminance(10)).sounds(BlockSoundGroup.WOOD), ParticleTypes.SOUL_FIRE_FLAME);
		stem = new BlockContainer(new PillarBlock(AbstractBlock.Settings.of(Material.NETHER_WOOD, mapColor).strength(2.0F).sounds(BlockSoundGroup.NETHER_STEM)));
		stripped_stem = new BlockContainer(new PillarBlock(AbstractBlock.Settings.copy(stem.BLOCK)));
		hyphae = new BlockContainer(new PillarBlock(AbstractBlock.Settings.copy(stem.BLOCK)));
		stripped_hyphae = new BlockContainer(new PillarBlock(AbstractBlock.Settings.copy(stem.BLOCK)));
		wart = new BlockContainer(new Block(AbstractBlock.Settings.of(Material.SOLID_ORGANIC, mapColor).strength(1.0F).sounds(BlockSoundGroup.WART_BLOCK)));
		campfire = new BlockContainer(new CampfireBlock(true, 1, AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(2.0F).sounds(BlockSoundGroup.WOOD).luminance(createLightLevelFromLitBlockState(15)).nonOpaque()));
		soul_campfire = new BlockContainer(new CampfireBlock(false, 2, AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(2.0F).sounds(BlockSoundGroup.WOOD).luminance(createLightLevelFromLitBlockState(10)).nonOpaque()));
	}

	public boolean contains(Block block) {
		return stem.contains(block) || stripped_stem.contains(block) || hyphae.contains(block) || stripped_hyphae.contains(block)
				|| torch.contains(block) || soul_torch.contains(block) || campfire.contains(block) || soul_campfire.contains(block)
				|| wart.contains(block) || super.contains(block);
	}
	public boolean contains(Item item) {
		return stem.contains(item) || stripped_stem.contains(item) || hyphae.contains(item) || stripped_hyphae.contains(item)
				|| torch.contains(item) || soul_torch.contains(item) || campfire.contains(item) || soul_campfire.contains(item)
				|| wart.contains(item) || super.contains(item);
	}
}
