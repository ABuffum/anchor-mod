package haven.materials.wood;

import haven.HavenMod;
import haven.blocks.RowBlock;
import haven.containers.BlockContainer;
import haven.containers.TorchContainer;
import haven.materials.providers.*;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;

public class SugarCaneMaterial extends WoodMaterial implements
		BundleProvider, BaleProvider, RowProvider,
		TorchProvider, EnderTorchProvider, SoulTorchProvider, CampfireProvider, EnderCampfireProvider, SoulCampfireProvider {
	private final BlockContainer bundle;
	public BlockContainer getBundle() { return bundle; }
	private final BlockContainer bale;
	public BlockContainer getBale() { return bale; }
	private final BlockContainer row;
	public BlockContainer getRow() { return row; }
	private final TorchContainer torch;
	public TorchContainer getTorch() { return torch; }
	private final TorchContainer ender_torch;
	public TorchContainer getEnderTorch() { return ender_torch; }
	private final TorchContainer soul_torch;
	public TorchContainer getSoulTorch() { return soul_torch; }
	private final BlockContainer campfire;
	public BlockContainer getCampfire() { return campfire; }
	private final BlockContainer ender_campfire;
	public BlockContainer getEnderCampfire() { return ender_campfire; }
	private final BlockContainer soul_campfire;
	public BlockContainer getSoulCampfire() { return soul_campfire; }

	public SugarCaneMaterial(String name, MapColor mapColor) {
		super(name, mapColor, true);
		bundle = new BlockContainer(new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(2.0F).sounds(BlockSoundGroup.WOOD)));
		bale = new BlockContainer(new HayBlock(AbstractBlock.Settings.of(Material.SOLID_ORGANIC, mapColor).strength(0.5F).sounds(BlockSoundGroup.GRASS)));
		row = new BlockContainer(new RowBlock(AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(1.0F).sounds(BlockSoundGroup.WOOD)));
		torch = MakeTorch(14, BlockSoundGroup.GRASS, ParticleTypes.FLAME);
		ender_torch = MakeTorch(12, BlockSoundGroup.GRASS, HavenMod.ENDER_FIRE_FLAME_PARTICLE);
		soul_torch = MakeTorch(10, BlockSoundGroup.GRASS, ParticleTypes.SOUL_FIRE_FLAME);
		campfire = MakeCampfire(15, 1, mapColor);
		ender_campfire = MakeCampfire(13, 3, mapColor);
		soul_campfire = MakeCampfire(10, 2, mapColor);
	}

	public boolean contains(Block block) {
		return bundle.contains(block) || bale.contains(block)
				|| torch.contains(block) || ender_torch.contains(block) || soul_torch.contains(block)
				|| campfire.contains(block) || ender_campfire.contains(block) || soul_campfire.contains(block) || super.contains(block);
	}
	public boolean contains(Item item) {
		return bundle.contains(item) || bale.contains(item)
				|| torch.contains(item) || ender_torch.contains(item) || soul_torch.contains(item)
				|| campfire.contains(item) || ender_campfire.contains(item) || soul_campfire.contains(item) || super.contains(item);
	}
}
