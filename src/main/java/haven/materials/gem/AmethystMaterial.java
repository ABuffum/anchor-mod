package haven.materials.gem;

import haven.HavenMod;
import haven.blocks.basic.HavenSlabBlock;
import haven.blocks.basic.HavenStairsBlock;
import haven.blocks.basic.HavenWallBlock;
import haven.materials.base.ToolArmorHorseMaterial;
import haven.materials.providers.*;
import haven.util.HavenArmorMaterials;
import haven.util.HavenPair;
import haven.util.HavenToolMaterials;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;

public class AmethystMaterial extends ToolArmorHorseMaterial implements
		CrystalBlockProvider, CrystalSlabProvider, CrystalStairsProvider, CrystalWallProvider,
		BricksProvider, BrickSlabProvider, BrickStairsProvider, BrickWallProvider,
		SlabProvider, StairsProvider, WallProvider {
	private final HavenPair crystal_block;
	public HavenPair getCrystalBlock() { return crystal_block; }
	private final HavenPair crystal_slab;
	public HavenPair getCrystalSlab() { return crystal_slab; }
	private final HavenPair crystal_stairs;
	public HavenPair getCrystalStairs() { return crystal_stairs; }
	private final HavenPair crystal_wall;
	public HavenPair getCrystalWall() { return crystal_wall; }
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

	public AmethystMaterial() {
		super("amethyst", false, HavenToolMaterials.AMETHYST,
				5, -3, -3, 0, 1, -2.8F, 1.5F, -3, 3, -2.4F,
				HavenArmorMaterials.AMETHYST, 10);
		crystal_block = new HavenPair(new Block(FabricBlockSettings.of(Material.AMETHYST, MapColor.PURPLE).sounds(BlockSoundGroup.AMETHYST_CLUSTER).strength(1.5f, 1.5f).requiresTool().luminance(HavenMod.luminance(5))), ItemSettings());
		crystal_slab = new HavenPair(new HavenSlabBlock(crystal_block.BLOCK), ItemSettings());
		crystal_stairs = new HavenPair(new HavenStairsBlock(crystal_block.BLOCK), ItemSettings());
		crystal_wall = new HavenPair(new HavenWallBlock(crystal_block.BLOCK), ItemSettings());
		bricks = new HavenPair(new Block(FabricBlockSettings.of(Material.AMETHYST, MapColor.PURPLE).sounds(BlockSoundGroup.AMETHYST_BLOCK).strength(1.5f, 1.5f).requiresTool()), ItemSettings());
		brick_slab = new HavenPair(new HavenSlabBlock(bricks.BLOCK), ItemSettings());
		brick_stairs = new HavenPair(new HavenStairsBlock(bricks.BLOCK), ItemSettings());
		brick_wall = new HavenPair(new HavenWallBlock(bricks.BLOCK), ItemSettings());
		slab = new HavenPair(new HavenSlabBlock(Blocks.AMETHYST_BLOCK), ItemSettings());
		stairs = new HavenPair(new HavenStairsBlock(Blocks.AMETHYST_BLOCK), ItemSettings());
		wall = new HavenPair(new HavenWallBlock(Blocks.AMETHYST_BLOCK), ItemSettings());
	}

	public boolean contains(Block block) {
		return block == crystal_block.BLOCK || block == crystal_slab.BLOCK  || block == crystal_stairs.BLOCK || block == crystal_wall.BLOCK
				|| block == bricks.BLOCK || block == brick_slab.BLOCK || block == brick_stairs.BLOCK || block == brick_wall.BLOCK
				|| block == slab.BLOCK || block == stairs.BLOCK || block == wall.BLOCK || super.contains(block);
	}
	public boolean contains(Item item) {
		return item == crystal_block.ITEM || item == crystal_slab.ITEM  || item == crystal_stairs.ITEM || item == crystal_wall.ITEM
				|| item == bricks.ITEM || item == brick_slab.ITEM  || item == brick_stairs.ITEM || item == brick_wall.ITEM
				|| item == slab.ITEM || item == stairs.ITEM || item == wall.ITEM || super.contains(item);
	}
}
