package haven.materials.gem;

import haven.blocks.basic.HavenSlabBlock;
import haven.blocks.basic.HavenStairsBlock;
import haven.blocks.basic.HavenWallBlock;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.*;
import haven.util.HavenPair;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;

public class DiamondMaterial extends BaseMaterial implements
		SlabProvider, StairsProvider, WallProvider,
		BricksProvider, BrickSlabProvider, BrickStairsProvider, BrickWallProvider {
	private final HavenPair bricks;
	public HavenPair getBricks() { return bricks; }
	private final HavenPair brick_slab;
	public HavenPair getBrickSlab() { return brick_slab; }
	private final HavenPair brick_stairs;
	public HavenPair getBrickStairs() { return brick_stairs; }
	private final HavenPair brick_wall;
	public HavenPair getBrickWall() { return brick_wall; }
	private final HavenPair slab;
	public HavenPair getSlab() { return slab; }
	private final HavenPair stairs;
	public HavenPair getStairs() { return stairs; }
	private final HavenPair wall;
	public HavenPair getWall() { return wall; }
	public DiamondMaterial() {
		super("diamond", false);
		bricks = new HavenPair(new Block(AbstractBlock.Settings.copy(Blocks.DIAMOND_BLOCK)));
		brick_slab = new HavenPair(new HavenSlabBlock(bricks.BLOCK));
		brick_stairs = new HavenPair(new HavenStairsBlock(bricks.BLOCK));
		brick_wall = new HavenPair(new HavenWallBlock(bricks.BLOCK));
		slab = new HavenPair(new HavenSlabBlock(Blocks.DIAMOND_BLOCK));
		stairs = new HavenPair(new HavenStairsBlock(Blocks.DIAMOND_BLOCK));
		wall = new HavenPair(new HavenWallBlock(Blocks.DIAMOND_BLOCK));
	}

	public boolean contains(Block block) {
		return block == bricks.BLOCK || block == brick_slab.BLOCK || block == brick_stairs.BLOCK || block == brick_wall.BLOCK
				|| block == slab.BLOCK || block == stairs.BLOCK || block == wall.BLOCK || super.contains(block);
	}
	public boolean contains(Item item) {
		return item == bricks.ITEM || item == brick_slab.ITEM || item == brick_stairs.ITEM || item == brick_wall.ITEM
				|| item == slab.ITEM || item == stairs.ITEM || item == wall.ITEM || super.contains(item);
	}
}
