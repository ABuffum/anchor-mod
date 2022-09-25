package haven.materials;

import haven.blocks.CryingObsidianSlabBlock;
import haven.blocks.CryingObsidianStairsBlock;
import haven.blocks.CryingObsidianWallBlock;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.SlabProvider;
import haven.materials.providers.StairsProvider;
import haven.materials.providers.WallProvider;
import haven.containers.BlockContainer;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.particle.DefaultParticleType;

public class CryingObsidianMaterial extends BaseMaterial implements
		SlabProvider, StairsProvider, WallProvider {
	private final BlockContainer slab;
	public BlockContainer getSlab() { return slab; }
	private final BlockContainer stairs;
	public BlockContainer getStairs() { return stairs; }
	private final BlockContainer wall;
	public BlockContainer getWall() { return wall; }

	public CryingObsidianMaterial(String name, DefaultParticleType particle) {
		super(name, false);
		slab = new BlockContainer(new CryingObsidianSlabBlock(Blocks.OBSIDIAN, particle), ItemSettings());
		stairs = new BlockContainer(new CryingObsidianStairsBlock(Blocks.OBSIDIAN, particle), ItemSettings());
		wall = new BlockContainer(new CryingObsidianWallBlock(Blocks.OBSIDIAN, particle), ItemSettings());
	}

	public boolean contains(Block block) {
		return slab.contains(block) || stairs.contains(block) || wall.contains(block) || super.contains(block);
	}
	public boolean contains(Item item) {
		return slab.contains(item) || stairs.contains(item) || wall.contains(item) || super.contains(item);
	}
}
