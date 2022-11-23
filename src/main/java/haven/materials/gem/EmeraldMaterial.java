package haven.materials.gem;

import haven.blocks.basic.HavenSlabBlock;
import haven.blocks.basic.HavenStairsBlock;
import haven.blocks.basic.HavenWallBlock;
import haven.materials.base.ToolArmorHorseMaterial;
import haven.materials.providers.*;
import haven.materials.HavenArmorMaterials;
import haven.containers.BlockContainer;
import haven.materials.HavenToolMaterials;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;

public class EmeraldMaterial extends ToolArmorHorseMaterial implements
		BricksProvider, BrickSlabProvider, BrickStairsProvider, BrickWallProvider,
		CutProvider, CutSlabProvider, CutStairsProvider, CutWallProvider {
	private final BlockContainer bricks;
	public BlockContainer getBricks() { return bricks; }
	private final BlockContainer brick_slab;
	public BlockContainer getBrickSlab() { return brick_slab; }
	private final BlockContainer brick_stairs;
	public BlockContainer getBrickStairs() { return brick_stairs; }
	private final BlockContainer brick_wall;
	public BlockContainer getBrickWall() { return brick_wall; }
	private final BlockContainer cut;
	public BlockContainer getCut() { return cut; }
	private final BlockContainer cut_slab;
	public BlockContainer getCutSlab() { return cut_slab; }
	private final BlockContainer cut_stairs;
	public BlockContainer getCutStairs() { return cut_stairs; }
	private final BlockContainer cut_wall;
	public BlockContainer getCutWall() { return cut_wall; }

	public EmeraldMaterial() {
		super("emerald", false, HavenToolMaterials.EMERALD,
				5, -3, -2, 0, 1, -2.8F, 1.5F, -3, 3, -2.4F,
				HavenArmorMaterials.EMERALD, 9);
		bricks = new BlockContainer(new Block(AbstractBlock.Settings.copy(Blocks.EMERALD_BLOCK)), ItemSettings());
		brick_slab = new BlockContainer(new HavenSlabBlock(bricks.getBlock()), ItemSettings());
		brick_stairs = new BlockContainer(new HavenStairsBlock(bricks.getBlock()), ItemSettings());
		brick_wall = new BlockContainer(new HavenWallBlock(bricks.getBlock()), ItemSettings());
		cut = new BlockContainer(new Block(AbstractBlock.Settings.copy(Blocks.EMERALD_BLOCK)), ItemSettings());
		cut_slab = new BlockContainer(new HavenSlabBlock(cut.getBlock()), ItemSettings());
		cut_stairs = new BlockContainer(new HavenStairsBlock(cut.getBlock()), ItemSettings());
		cut_wall = new BlockContainer(new HavenWallBlock(cut.getBlock()), ItemSettings());
	}

	public boolean contains(Block block) {
		return bricks.contains(block) || brick_slab.contains(block) || brick_stairs.contains(block) || brick_wall.contains(block)
				|| cut.contains(block) || cut_slab.contains(block) || cut_stairs.contains(block) || cut_wall.contains(block)
				|| super.contains(block);
	}
	public boolean contains(Item item) {
		return bricks.contains(item) || brick_slab.contains(item) || brick_stairs.contains(item) || brick_wall.contains(item)
				|| cut.contains(item) || cut_slab.contains(item) || cut_stairs.contains(item) || cut_wall.contains(item)
				|| super.contains(item);
	}
}
