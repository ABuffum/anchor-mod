package haven.materials;

import haven.blocks.CryingObsidianSlabBlock;
import haven.blocks.CryingObsidianStairsBlock;
import haven.blocks.CryingObsidianWallBlock;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.SlabProvider;
import haven.materials.providers.StairsProvider;
import haven.materials.providers.WallProvider;
import haven.util.HavenPair;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.particle.DefaultParticleType;

public class CryingObsidianMaterial extends BaseMaterial implements
		SlabProvider, StairsProvider, WallProvider {
	private final HavenPair slab;
	public HavenPair getSlab() { return slab; }
	private final HavenPair stairs;
	public HavenPair getStairs() { return stairs; }
	private final HavenPair wall;
	public HavenPair getWall() { return wall; }

	public CryingObsidianMaterial(String name, DefaultParticleType particle) {
		super(name, false);
		slab = new HavenPair(new CryingObsidianSlabBlock(Blocks.OBSIDIAN, particle), ItemSettings());
		stairs = new HavenPair(new CryingObsidianStairsBlock(Blocks.OBSIDIAN, particle), ItemSettings());
		wall = new HavenPair(new CryingObsidianWallBlock(Blocks.OBSIDIAN, particle), ItemSettings());
	}

	public boolean contains(Block block) {
		return block == slab.BLOCK || block == stairs.BLOCK || block == wall.BLOCK || super.contains(block);
	}
	public boolean contains(Item item) {
		return item == slab.ITEM || item == stairs.ITEM || item == wall.ITEM || super.contains(item);
	}
}
