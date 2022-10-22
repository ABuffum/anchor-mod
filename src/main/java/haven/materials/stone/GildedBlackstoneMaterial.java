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

public class GildedBlackstoneMaterial extends BaseMaterial implements
		SlabProvider, StairsProvider, WallProvider,
		PolishedProvider, PolishedSlabProvider, PolishedStairsProvider, PolishedWallProvider,
		PolishedBricksProvider, PolishedBrickSlabProvider, PolishedBrickStairsProvider, PolishedBrickWallProvider {

	private final BlockContainer slab;
	public BlockContainer getSlab() { return slab; }
	private final BlockContainer stairs;
	public BlockContainer getStairs() { return stairs; }
	private final BlockContainer wall;
	public BlockContainer getWall() { return wall; }
	private final BlockContainer polished;
	public BlockContainer getPolished() { return polished; }
	private final BlockContainer polished_slab;
	public BlockContainer getPolishedSlab() { return polished_slab; }
	private final BlockContainer polished_stairs;
	public BlockContainer getPolishedStairs() { return polished_stairs; }
	private final BlockContainer polished_wall;
	public BlockContainer getPolishedWall() { return polished_wall; }
	private final BlockContainer polished_bricks;
	public BlockContainer getPolishedBricks() { return polished_bricks; }
	private final BlockContainer polished_brick_slab;
	public BlockContainer getPolishedBrickSlab() { return polished_brick_slab; }
	private final BlockContainer polished_brick_stairs;
	public BlockContainer getPolishedBrickStairs() { return polished_brick_stairs; }
	private final BlockContainer polished_brick_wall;
	public BlockContainer getPolishedBrickWall() { return polished_brick_wall; }

	public GildedBlackstoneMaterial() {
		super("gilded_blackstone", false);
		slab = new BlockContainer(new HavenSlabBlock(Blocks.GILDED_BLACKSTONE), ItemSettings());
		stairs = new BlockContainer(new HavenStairsBlock(Blocks.GILDED_BLACKSTONE), ItemSettings());
		wall = new BlockContainer(new HavenWallBlock(Blocks.GILDED_BLACKSTONE), ItemSettings());
		polished = new BlockContainer(new Block(AbstractBlock.Settings.copy(Blocks.GILDED_BLACKSTONE)), ItemSettings());
		polished_slab = new BlockContainer(new HavenSlabBlock(polished.BLOCK), ItemSettings());
		polished_stairs = new BlockContainer(new HavenStairsBlock(polished.BLOCK), ItemSettings());
		polished_wall = new BlockContainer(new HavenWallBlock(polished.BLOCK), ItemSettings());
		polished_bricks = new BlockContainer(new Block(AbstractBlock.Settings.copy(polished.BLOCK)), ItemSettings());
		polished_brick_slab = new BlockContainer(new HavenSlabBlock(polished_bricks.BLOCK), ItemSettings());
		polished_brick_stairs = new BlockContainer(new HavenStairsBlock(polished_bricks.BLOCK), ItemSettings());
		polished_brick_wall = new BlockContainer(new HavenWallBlock(polished_bricks.BLOCK), ItemSettings());
	}

	public boolean contains(Block block) {
		return slab.contains(block) || stairs.contains(block) || wall.contains(block)
				|| polished.contains(block) || polished_slab.contains(block) || polished_stairs.contains(block) || polished_wall.contains(block)
				|| polished_bricks.contains(block) || polished_brick_slab.contains(block) || polished_brick_stairs.contains(block) || polished_brick_wall.contains(block)
				|| super.contains(block);
	}
	public boolean contains(Item item) {
		return slab.contains(item) || stairs.contains(item) || wall.contains(item)
				|| polished.contains(item) || polished_slab.contains(item) || polished_stairs.contains(item) || polished_wall.contains(item)
				|| polished_bricks.contains(item) || polished_brick_slab.contains(item)  || polished_brick_stairs.contains(item) || polished_brick_wall.contains(item)
				|| super.contains(item);
	}
}
