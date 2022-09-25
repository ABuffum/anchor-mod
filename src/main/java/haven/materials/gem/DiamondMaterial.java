package haven.materials.gem;

import haven.blocks.basic.HavenSlabBlock;
import haven.blocks.basic.HavenStairsBlock;
import haven.blocks.basic.HavenWallBlock;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.*;
import haven.containers.BlockContainer;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;

public class DiamondMaterial extends BaseMaterial implements
		SlabProvider, StairsProvider, WallProvider,
		BricksProvider, BrickSlabProvider, BrickStairsProvider, BrickWallProvider {
	private final BlockContainer bricks;
	public BlockContainer getBricks() { return bricks; }
	private final BlockContainer brick_slab;
	public BlockContainer getBrickSlab() { return brick_slab; }
	private final BlockContainer brick_stairs;
	public BlockContainer getBrickStairs() { return brick_stairs; }
	private final BlockContainer brick_wall;
	public BlockContainer getBrickWall() { return brick_wall; }
	private final BlockContainer slab;
	public BlockContainer getSlab() { return slab; }
	private final BlockContainer stairs;
	public BlockContainer getStairs() { return stairs; }
	private final BlockContainer wall;
	public BlockContainer getWall() { return wall; }
	public DiamondMaterial() {
		super("diamond", false);
		bricks = new BlockContainer(new Block(AbstractBlock.Settings.copy(Blocks.DIAMOND_BLOCK)));
		brick_slab = new BlockContainer(new HavenSlabBlock(bricks.BLOCK));
		brick_stairs = new BlockContainer(new HavenStairsBlock(bricks.BLOCK));
		brick_wall = new BlockContainer(new HavenWallBlock(bricks.BLOCK));
		slab = new BlockContainer(new HavenSlabBlock(Blocks.DIAMOND_BLOCK));
		stairs = new BlockContainer(new HavenStairsBlock(Blocks.DIAMOND_BLOCK));
		wall = new BlockContainer(new HavenWallBlock(Blocks.DIAMOND_BLOCK));
	}

	public boolean contains(Block block) {
		return bricks.contains(block) || brick_slab.contains(block) || brick_stairs.contains(block) || brick_wall.contains(block)
				|| slab.contains(block) || stairs.contains(block) || wall.contains(block)
				|| super.contains(block);
	}
	public boolean contains(Item item) {
		return bricks.contains(item) || brick_slab.contains(item) || brick_stairs.contains(item) || brick_wall.contains(item)
				|| slab.contains(item) || stairs.contains(item) || wall.contains(item)
				|| super.contains(item);
	}
}
