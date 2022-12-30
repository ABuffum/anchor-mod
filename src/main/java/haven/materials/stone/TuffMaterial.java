package haven.materials.stone;

import haven.blocks.basic.ModSlabBlock;
import haven.blocks.basic.ModStairsBlock;
import haven.blocks.basic.ModWallBlock;
import haven.containers.BlockContainer;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.*;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;

public class TuffMaterial extends BaseMaterial implements
		SlabProvider, StairsProvider, WallProvider,
		SmoothProvider, SmoothSlabProvider, SmoothStairsProvider, SmoothWallProvider,
		BricksProvider, BrickSlabProvider, BrickStairsProvider, BrickWallProvider {

	private final BlockContainer slab;
	public BlockContainer getSlab() { return slab; }
	private final BlockContainer stairs;
	public BlockContainer getStairs() { return stairs; }
	private final BlockContainer wall;
	public BlockContainer getWall() { return wall; }
	private final BlockContainer smooth;
	public BlockContainer getSmooth() { return smooth; }
	private final BlockContainer smooth_slab;
	public BlockContainer getSmoothSlab() { return smooth_slab; }
	private final BlockContainer smooth_stairs;
	public BlockContainer getSmoothStairs() { return smooth_stairs; }
	private final BlockContainer smooth_wall;
	public BlockContainer getSmoothWall() { return smooth_wall; }
	private final BlockContainer bricks;
	public BlockContainer getBricks() { return bricks; }
	private final BlockContainer brick_slab;
	public BlockContainer getBrickSlab() { return brick_slab; }
	private final BlockContainer brick_stairs;
	public BlockContainer getBrickStairs() { return brick_stairs; }
	private final BlockContainer brick_wall;
	public BlockContainer getBrickWall() { return brick_wall; }

	public TuffMaterial() {
		super("tuff", false);
		slab = new BlockContainer(new ModSlabBlock(Blocks.TUFF), ItemSettings());
		stairs = new BlockContainer(new ModStairsBlock(Blocks.TUFF), ItemSettings());
		wall = new BlockContainer(new ModWallBlock(Blocks.TUFF), ItemSettings());
		smooth = new BlockContainer(new Block(AbstractBlock.Settings.copy(Blocks.TUFF)), ItemSettings());
		smooth_slab = new BlockContainer(new ModSlabBlock(smooth.getBlock()), ItemSettings());
		smooth_stairs = new BlockContainer(new ModStairsBlock(smooth.getBlock()), ItemSettings());
		smooth_wall = new BlockContainer(new ModWallBlock(smooth.getBlock()), ItemSettings());
		bricks = new BlockContainer(new Block(AbstractBlock.Settings.copy(Blocks.TUFF)), ItemSettings());
		brick_slab = new BlockContainer(new ModSlabBlock(bricks.getBlock()), ItemSettings());
		brick_stairs = new BlockContainer(new ModStairsBlock(bricks.getBlock()), ItemSettings());
		brick_wall = new BlockContainer(new ModWallBlock(bricks.getBlock()), ItemSettings());
	}

	public boolean contains(Block block) {
		return slab.contains(block) || stairs.contains(block) || wall.contains(block)
				|| smooth.contains(block) || smooth_slab.contains(block) || smooth_stairs.contains(block) || smooth_wall.contains(block)
				|| bricks.contains(block) || brick_slab.contains(block) || brick_stairs.contains(block) || brick_wall.contains(block)
				|| super.contains(block);
	}
	public boolean contains(Item item) {
		return slab.contains(item) || stairs.contains(item) || wall.contains(item)
				|| smooth.contains(item) || smooth_slab.contains(item) || smooth_stairs.contains(item) || smooth_wall.contains(item)
				|| bricks.contains(item) || brick_slab.contains(item)  || brick_stairs.contains(item) || brick_wall.contains(item)
				|| super.contains(item);
	}
}
