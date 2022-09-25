package haven.materials;

import haven.blocks.basic.HavenSlabBlock;
import haven.blocks.basic.HavenStairsBlock;
import haven.blocks.basic.HavenWallBlock;
import haven.materials.base.ToolArmorHorseMaterial;
import haven.materials.providers.*;
import haven.containers.BlockContainer;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;

public class ObsidianMaterial extends ToolArmorHorseMaterial implements
		SlabProvider, StairsProvider, WallProvider {
	private final BlockContainer slab;
	public BlockContainer getSlab() { return slab; }
	private final BlockContainer stairs;
	public BlockContainer getStairs() { return stairs; }
	private final BlockContainer wall;
	public BlockContainer getWall() { return wall; }

	public ObsidianMaterial() {
		super("obsidian", false, HavenToolMaterials.OBSIDIAN,
				5, -3, -3, 0, 1, -2.8F, 1.5F, -3, 3, -2.4F,
				HavenArmorMaterials.OBSIDIAN, 10);
		slab = new BlockContainer(new HavenSlabBlock(Blocks.OBSIDIAN), ItemSettings());
		stairs = new BlockContainer(new HavenStairsBlock(Blocks.OBSIDIAN), ItemSettings());
		wall = new BlockContainer(new HavenWallBlock(Blocks.OBSIDIAN), ItemSettings());
	}

	public boolean contains(Block block) {
		return slab.contains(block) || stairs.contains(block) || wall.contains(block) || super.contains(block);
	}
	public boolean contains(Item item) {
		return slab.contains(item) || stairs.contains(item) || wall.contains(item) || super.contains(item);
	}
}
