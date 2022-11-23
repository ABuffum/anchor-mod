package haven.materials.wood;

import haven.ModBase;
import haven.containers.BlockContainer;
import haven.containers.TorchContainer;
import haven.materials.providers.*;
import haven.sounds.ModBlockSoundGroups;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;

public class BaseFungusMaterial extends WoodMaterial implements
		TorchProvider, EnderTorchProvider, SoulTorchProvider, CampfireProvider, EnderCampfireProvider, SoulCampfireProvider,
		StrippedStemProvider, StrippedHyphaeProvider, WartBlockProvider {
	private final TorchContainer torch;
	public TorchContainer getTorch() { return torch; }
	private final TorchContainer ender_torch;
	public TorchContainer getEnderTorch() { return ender_torch; }
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
	private final BlockContainer ender_campfire;
	public BlockContainer getEnderCampfire() { return ender_campfire; }
	private final BlockContainer soul_campfire;
	public BlockContainer getSoulCampfire() { return soul_campfire; }

	public BaseFungusMaterial(String name, MapColor mapColor) {
		this(name, mapColor, false);
	}
	public BaseFungusMaterial(String name, MapColor mapColor, boolean isFlammable) {
		super(name, mapColor, isFlammable, ModBlockSoundGroups.NETHER_WOOD);
		torch = MakeTorch(ModBase.LUMINANCE_14, ModBlockSoundGroups.NETHER_WOOD, ParticleTypes.FLAME);
		ender_torch = MakeTorch(ModBase.LUMINANCE_12, ModBlockSoundGroups.NETHER_WOOD, ModBase.ENDER_FIRE_FLAME_PARTICLE);
		soul_torch = MakeTorch(ModBase.LUMINANCE_10, ModBlockSoundGroups.NETHER_WOOD, ParticleTypes.SOUL_FIRE_FLAME);
		stem = new BlockContainer(new PillarBlock(AbstractBlock.Settings.of(Material.NETHER_WOOD, mapColor).strength(2.0F).sounds(BlockSoundGroup.NETHER_STEM)), ItemSettings());
		stripped_stem = new BlockContainer(new PillarBlock(AbstractBlock.Settings.copy(stem.getBlock())), ItemSettings());
		hyphae = new BlockContainer(new PillarBlock(AbstractBlock.Settings.copy(stem.getBlock())), ItemSettings());
		stripped_hyphae = new BlockContainer(new PillarBlock(AbstractBlock.Settings.copy(stem.getBlock())), ItemSettings());
		wart = new BlockContainer(new Block(AbstractBlock.Settings.of(Material.SOLID_ORGANIC, mapColor).strength(1.0F).sounds(BlockSoundGroup.WART_BLOCK)), ItemSettings());
		campfire = MakeCampfire(15, 1, mapColor, BlockSoundGroup.NETHER_STEM, true);
		ender_campfire = MakeCampfire(13, 3, mapColor, BlockSoundGroup.NETHER_STEM, false);
		soul_campfire = MakeCampfire(10, 2, mapColor, BlockSoundGroup.NETHER_STEM, false);
	}

	public boolean contains(Block block) {
		return stem.contains(block) || stripped_stem.contains(block) || hyphae.contains(block) || stripped_hyphae.contains(block)
				|| torch.contains(block) || ender_torch.contains(block) || soul_torch.contains(block)
				|| campfire.contains(block) || ender_campfire.contains(block) || soul_campfire.contains(block)
				|| wart.contains(block) || super.contains(block);
	}
	public boolean contains(Item item) {
		return stem.contains(item) || stripped_stem.contains(item) || hyphae.contains(item) || stripped_hyphae.contains(item)
				|| torch.contains(item) || ender_torch.contains(item) || soul_torch.contains(item)
				|| campfire.contains(item) || ender_campfire.contains(item) || soul_campfire.contains(item)
				|| wart.contains(item) || super.contains(item);
	}
}
