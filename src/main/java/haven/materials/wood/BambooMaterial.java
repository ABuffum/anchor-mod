package haven.materials.wood;

import haven.materials.providers.*;
import haven.containers.BlockContainer;
import haven.containers.TorchContainer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;

public class BambooMaterial extends BaseTreeMaterial implements StrippedBundleProvider {
	private final BlockContainer bundle;
	public BlockContainer getBundle() { return bundle; }
	private final BlockContainer stripped_bundle;
	public BlockContainer getStrippedBundle() { return stripped_bundle; }

	public BambooMaterial(String name, MapColor mapColor) {
		super(name, mapColor, true);
		bundle = new BlockContainer(new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(2.0F).sounds(BlockSoundGroup.BAMBOO)));
		stripped_bundle = new BlockContainer(new PillarBlock(AbstractBlock.Settings.copy(bundle.BLOCK)));
	}

	public boolean contains(Block block) {
		return bundle.contains(block) || stripped_bundle.contains(block) || super.contains(block);
	}
	public boolean contains(Item item) {
		return bundle.contains(item) || stripped_bundle.contains(item) || super.contains(item);
	}
}
