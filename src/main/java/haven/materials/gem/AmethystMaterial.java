package haven.materials.gem;

import haven.ModBase;
import haven.blocks.basic.ModSlabBlock;
import haven.blocks.basic.ModStairsBlock;
import haven.blocks.basic.ModWallBlock;
import haven.materials.base.ToolArmorHorseMaterial;
import haven.materials.providers.*;
import haven.materials.HavenArmorMaterials;
import haven.containers.BlockContainer;
import haven.materials.HavenToolMaterials;
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
	private final BlockContainer crystal_block;
	public BlockContainer getCrystalBlock() { return crystal_block; }
	private final BlockContainer crystal_slab;
	public BlockContainer getCrystalSlab() { return crystal_slab; }
	private final BlockContainer crystal_stairs;
	public BlockContainer getCrystalStairs() { return crystal_stairs; }
	private final BlockContainer crystal_wall;
	public BlockContainer getCrystalWall() { return crystal_wall; }
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

	public AmethystMaterial() {
		super("amethyst", false, HavenToolMaterials.AMETHYST,
				5, -3, -3, 0, 1, -2.8F, 1.5F, -3, 3, -2.4F,
				HavenArmorMaterials.AMETHYST, 10);
		crystal_block = new BlockContainer(new Block(FabricBlockSettings.of(Material.AMETHYST, MapColor.PURPLE).sounds(BlockSoundGroup.AMETHYST_CLUSTER).strength(1.5f, 1.5f).requiresTool().luminance(ModBase.LUMINANCE_5)), ItemSettings());
		crystal_slab = new BlockContainer(new ModSlabBlock(crystal_block.getBlock()), ItemSettings());
		crystal_stairs = new BlockContainer(new ModStairsBlock(crystal_block.getBlock()), ItemSettings());
		crystal_wall = new BlockContainer(new ModWallBlock(crystal_block.getBlock()), ItemSettings());
		bricks = new BlockContainer(new Block(FabricBlockSettings.of(Material.AMETHYST, MapColor.PURPLE).sounds(BlockSoundGroup.AMETHYST_BLOCK).strength(1.5f, 1.5f).requiresTool()), ItemSettings());
		brick_slab = new BlockContainer(new ModSlabBlock(bricks.getBlock()), ItemSettings());
		brick_stairs = new BlockContainer(new ModStairsBlock(bricks.getBlock()), ItemSettings());
		brick_wall = new BlockContainer(new ModWallBlock(bricks.getBlock()), ItemSettings());
		slab = new BlockContainer(new ModSlabBlock(Blocks.AMETHYST_BLOCK), ItemSettings());
		stairs = new BlockContainer(new ModStairsBlock(Blocks.AMETHYST_BLOCK), ItemSettings());
		wall = new BlockContainer(new ModWallBlock(Blocks.AMETHYST_BLOCK), ItemSettings());
	}

	public boolean contains(Block block) {
		return crystal_block.contains(block) || crystal_slab.contains(block) || crystal_stairs.contains(block) || crystal_wall.contains(block)
				|| bricks.contains(block) || brick_slab.contains(block) || brick_stairs.contains(block) || brick_wall.contains(block)
				|| slab.contains(block) || stairs.contains(block) || wall.contains(block)
				|| super.contains(block);
	}
	public boolean contains(Item item) {
		return crystal_block.contains(item) || crystal_slab.contains(item) || crystal_stairs.contains(item) || crystal_wall.contains(item)
				|| bricks.contains(item) || brick_slab.contains(item) || brick_stairs.contains(item) || brick_wall.contains(item)
				|| slab.contains(item) || stairs.contains(item) || wall.contains(item)
				|| super.contains(item);
	}
}
