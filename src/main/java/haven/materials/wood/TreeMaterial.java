package haven.materials.wood;

import haven.materials.providers.SaplingProvider;
import haven.containers.SaplingContainer;
import net.minecraft.block.*;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;

import java.util.function.Supplier;

public class TreeMaterial extends BaseTreeMaterial implements SaplingProvider {
	private final SaplingContainer sapling;
	public SaplingContainer getSapling() { return sapling; }

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
		super(name, mapColor, leafSounds, isFlammable);
		sapling = new SaplingContainer(saplingGenerator);
	}

	public boolean contains(Block block) {
		return sapling.contains(block) || super.contains(block);
	}
	public boolean contains(Item item) {
		return sapling.contains(item) || super.contains(item);
	}
}
