package haven.materials.gem;

import haven.blocks.basic.HavenSlabBlock;
import haven.blocks.basic.HavenStairsBlock;
import haven.blocks.basic.HavenWallBlock;
import haven.materials.base.ToolArmorHorseMaterial;
import haven.materials.providers.*;
import haven.materials.HavenArmorMaterials;
import haven.containers.BlockContainer;
import haven.materials.HavenToolMaterials;
import net.minecraft.block.*;
import net.minecraft.item.Item;

public class QuartzMaterial extends ToolArmorHorseMaterial implements
		CrystalBlockProvider, CrystalSlabProvider, CrystalStairsProvider, CrystalWallProvider,
		SmoothWallProvider, WallProvider,
		BrickSlabProvider, BrickStairsProvider, BrickWallProvider {
	private final BlockContainer crystal_block;
	public BlockContainer getCrystalBlock() { return crystal_block; }
	private final BlockContainer crystal_slab;
	public BlockContainer getCrystalSlab() { return crystal_slab; }
	private final BlockContainer crystal_stairs;
	public BlockContainer getCrystalStairs() { return crystal_stairs; }
	private final BlockContainer crystal_wall;
	public BlockContainer getCrystalWall() { return crystal_wall; }
	private final BlockContainer smooth_wall;
	public BlockContainer getSmoothWall() { return smooth_wall; }
	private final BlockContainer wall;
	public BlockContainer getWall() { return wall; }
	private final BlockContainer brick_slab;
	public BlockContainer getBrickSlab() { return brick_slab; }
	private final BlockContainer brick_stairs;
	public BlockContainer getBrickStairs() { return brick_stairs; }
	private final BlockContainer brick_wall;
	public BlockContainer getBrickWall() { return brick_wall; }

	public QuartzMaterial() {
		super("quartz", false, HavenToolMaterials.QUARTZ,
				5, -3, -1, 0, 1, -2.8F, 1.5F, -3, 3, -2.4F,
				HavenArmorMaterials.QUARTZ, 8);
		crystal_block = new BlockContainer(new Block(AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK)), ItemSettings());
		crystal_slab = new BlockContainer(new HavenSlabBlock(crystal_block.BLOCK), ItemSettings());
		crystal_stairs = new BlockContainer(new HavenStairsBlock(crystal_block.BLOCK), ItemSettings());
		crystal_wall = new BlockContainer(new HavenWallBlock(crystal_block.BLOCK), ItemSettings());
		smooth_wall = new BlockContainer(new HavenWallBlock(Blocks.SMOOTH_QUARTZ));
		wall = new BlockContainer(new HavenWallBlock(Blocks.QUARTZ_BLOCK));
		brick_slab = new BlockContainer(new HavenSlabBlock(Blocks.QUARTZ_BRICKS));
		brick_stairs = new BlockContainer(new HavenStairsBlock(Blocks.QUARTZ_BRICKS));
		brick_wall = new BlockContainer(new HavenWallBlock(Blocks.QUARTZ_BRICKS));
	}

	public boolean contains(Block block) {
		return smooth_wall.contains(block) || wall.contains(block)
				|| brick_slab.contains(block) || brick_stairs.contains(block) || brick_wall.contains(block)
				|| super.contains(block);
	}
	public boolean contains(Item item) {
		return smooth_wall.contains(item) || wall.contains(item)
				|| brick_slab.contains(item) || brick_stairs.contains(item) || brick_wall.contains(item)
				|| super.contains(item);
	}
}
