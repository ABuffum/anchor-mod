package haven.materials.gem;

import haven.blocks.basic.HavenSlabBlock;
import haven.blocks.basic.HavenStairsBlock;
import haven.blocks.basic.HavenWallBlock;
import haven.materials.base.BaseMaterial;
import haven.materials.base.ToolArmorHorseMaterial;
import haven.materials.providers.*;
import haven.util.HavenArmorMaterials;
import haven.util.HavenPair;
import haven.util.HavenToolMaterials;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;

public class EmeraldMaterial extends ToolArmorHorseMaterial implements
		BricksProvider, BrickSlabProvider, BrickStairsProvider, BrickWallProvider,
		CutProvider, CutSlabProvider, CutStairsProvider, CutWallProvider {
	private final HavenPair bricks;
	public HavenPair getBricks() { return bricks; }
	private final HavenPair brick_slab;
	public HavenPair getBrickSlab() { return brick_slab; }
	private final HavenPair brick_stairs;
	public HavenPair getBrickStairs() { return brick_stairs; }
	private final HavenPair brick_wall;
	public HavenPair getBrickWall() { return brick_wall; }
	private final HavenPair cut;
	public HavenPair getCut() { return cut; }
	private final HavenPair cut_slab;
	public HavenPair getCutSlab() { return cut_slab; }
	private final HavenPair cut_stairs;
	public HavenPair getCutStairs() { return cut_stairs; }
	private final HavenPair cut_wall;
	public HavenPair getCutWall() { return cut_wall; }

	public EmeraldMaterial() {
		super("emerald", false, HavenToolMaterials.EMERALD,
				5, -3, -2, 0, 1, -2.8F, 1.5F, -3, 3, -2.4F,
				HavenArmorMaterials.EMERALD, 9);
		bricks = new HavenPair(new Block(AbstractBlock.Settings.copy(Blocks.EMERALD_BLOCK)), ItemSettings());
		brick_slab = new HavenPair(new HavenSlabBlock(bricks.BLOCK), ItemSettings());
		brick_stairs = new HavenPair(new HavenStairsBlock(bricks.BLOCK), ItemSettings());
		brick_wall = new HavenPair(new HavenWallBlock(bricks.BLOCK), ItemSettings());
		cut = new HavenPair(new Block(AbstractBlock.Settings.copy(Blocks.EMERALD_BLOCK)), ItemSettings());
		cut_slab = new HavenPair(new HavenSlabBlock(cut.BLOCK), ItemSettings());
		cut_stairs = new HavenPair(new HavenStairsBlock(cut.BLOCK), ItemSettings());
		cut_wall = new HavenPair(new HavenWallBlock(cut.BLOCK), ItemSettings());
	}

	public boolean contains(Block block) {
		return block == bricks.BLOCK || block == brick_slab.BLOCK || block == brick_stairs.BLOCK || block == brick_wall.BLOCK
				|| block == cut.BLOCK || block == cut_slab.BLOCK || block == cut_stairs.BLOCK || block == cut_wall.BLOCK || super.contains(block);
	}
	public boolean contains(Item item) {
		return item == bricks.ITEM || item == brick_slab.ITEM || item == brick_stairs.ITEM || item == brick_wall.ITEM
				|| item == cut.ITEM || item == cut_slab.ITEM || item == cut_stairs.ITEM || item == cut_wall.ITEM || super.contains(item);
	}
}
