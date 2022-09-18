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
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;

public class QuartzMaterial extends ToolArmorHorseMaterial implements
		CrystalBlockProvider, CrystalSlabProvider, CrystalStairsProvider, CrystalWallProvider,
		SmoothWallProvider, WallProvider,
		BrickSlabProvider, BrickStairsProvider, BrickWallProvider {
	private final HavenPair crystal_block;
	public HavenPair getCrystalBlock() { return crystal_block; }
	private final HavenPair crystal_slab;
	public HavenPair getCrystalSlab() { return crystal_slab; }
	private final HavenPair crystal_stairs;
	public HavenPair getCrystalStairs() { return crystal_stairs; }
	private final HavenPair crystal_wall;
	public HavenPair getCrystalWall() { return crystal_wall; }
	private final HavenPair smooth_wall;
	public HavenPair getSmoothWall() { return smooth_wall; }
	private final HavenPair wall;
	public HavenPair getWall() { return wall; }
	private final HavenPair brick_slab;
	public HavenPair getBrickSlab() { return brick_slab; }
	private final HavenPair brick_stairs;
	public HavenPair getBrickStairs() { return brick_stairs; }
	private final HavenPair brick_wall;
	public HavenPair getBrickWall() { return brick_wall; }

	public QuartzMaterial() {
		super("quartz", false, HavenToolMaterials.QUARTZ,
				5, -3, -1, 0, 1, -2.8F, 1.5F, -3, 3, -2.4F,
				HavenArmorMaterials.QUARTZ, 8);
		crystal_block = new HavenPair(new Block(AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK)), ItemSettings());
		crystal_slab = new HavenPair(new HavenSlabBlock(crystal_block.BLOCK), ItemSettings());
		crystal_stairs = new HavenPair(new HavenStairsBlock(crystal_block.BLOCK), ItemSettings());
		crystal_wall = new HavenPair(new HavenWallBlock(crystal_block.BLOCK), ItemSettings());
		smooth_wall = new HavenPair(new HavenWallBlock(Blocks.SMOOTH_QUARTZ));
		wall = new HavenPair(new HavenWallBlock(Blocks.QUARTZ_BLOCK));
		brick_slab = new HavenPair(new HavenSlabBlock(Blocks.QUARTZ_BRICKS));
		brick_stairs = new HavenPair(new HavenStairsBlock(Blocks.QUARTZ_BRICKS));
		brick_wall = new HavenPair(new HavenWallBlock(Blocks.QUARTZ_BRICKS));
	}

	public boolean contains(Block block) {
		return block == smooth_wall.BLOCK || block == wall.BLOCK
				|| block == brick_slab.BLOCK || block == brick_stairs.BLOCK || block == brick_wall.BLOCK
				|| super.contains(block);
	}
	public boolean contains(Item item) {
		return item == smooth_wall.ITEM || item == wall.ITEM
				|| item == brick_slab.ITEM || item == brick_stairs.ITEM || item == brick_wall.ITEM
				|| super.contains(item);
	}
}
