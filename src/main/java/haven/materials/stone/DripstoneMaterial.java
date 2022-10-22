package haven.materials.stone;

import haven.blocks.basic.HavenSlabBlock;
import haven.blocks.basic.HavenStairsBlock;
import haven.blocks.basic.HavenWallBlock;
import haven.containers.BlockContainer;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.*;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;

public class DripstoneMaterial extends BaseMaterial implements
		SmoothProvider, SmoothSlabProvider, SmoothStairsProvider, SmoothWallProvider,
		BricksProvider, BrickSlabProvider, BrickStairsProvider, BrickWallProvider {

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

	public DripstoneMaterial() {
		super("dripstone", false);
		smooth = new BlockContainer(new Block(AbstractBlock.Settings.copy(Blocks.DRIPSTONE_BLOCK)), ItemSettings());
		smooth_slab = new BlockContainer(new HavenSlabBlock(smooth.BLOCK), ItemSettings());
		smooth_stairs = new BlockContainer(new HavenStairsBlock(smooth.BLOCK), ItemSettings());
		smooth_wall = new BlockContainer(new HavenWallBlock(smooth.BLOCK), ItemSettings());
		bricks = new BlockContainer(new Block(AbstractBlock.Settings.copy(Blocks.DRIPSTONE_BLOCK)), ItemSettings());
		brick_slab = new BlockContainer(new HavenSlabBlock(bricks.BLOCK), ItemSettings());
		brick_stairs = new BlockContainer(new HavenStairsBlock(bricks.BLOCK), ItemSettings());
		brick_wall = new BlockContainer(new HavenWallBlock(bricks.BLOCK), ItemSettings());
	}

	public boolean contains(Block block) {
		return smooth.contains(block) || smooth_slab.contains(block) || smooth_stairs.contains(block) || smooth_wall.contains(block)
				|| bricks.contains(block) || brick_slab.contains(block) || brick_stairs.contains(block) || brick_wall.contains(block)
				|| super.contains(block);
	}
	public boolean contains(Item item) {
		return smooth.contains(item) || smooth_slab.contains(item) || smooth_stairs.contains(item) || smooth_wall.contains(item)
				|| bricks.contains(item) || brick_slab.contains(item)  || brick_stairs.contains(item) || brick_wall.contains(item)
				|| super.contains(item);
	}
}
