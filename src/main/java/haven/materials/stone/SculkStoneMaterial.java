package haven.materials.stone;

import haven.blocks.basic.HavenSlabBlock;
import haven.blocks.basic.HavenStairsBlock;
import haven.blocks.basic.HavenWallBlock;
import haven.containers.BlockContainer;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.*;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;

public class SculkStoneMaterial extends BaseMaterial implements
		Provider, SlabProvider, StairsProvider, WallProvider,
		BricksProvider, BrickSlabProvider, BrickStairsProvider, BrickWallProvider {
	private final BlockContainer provider;
	public BlockContainer get() { return provider; }
	private final BlockContainer slab;
	public BlockContainer getSlab() { return slab; }
	private final BlockContainer stairs;
	public BlockContainer getStairs() { return stairs; }
	private final BlockContainer wall;
	public BlockContainer getWall() { return wall; }
	private final BlockContainer bricks;
	public BlockContainer getBricks() { return bricks; }
	private final BlockContainer brick_slab;
	public BlockContainer getBrickSlab() { return brick_slab; }
	private final BlockContainer brick_stairs;
	public BlockContainer getBrickStairs() { return brick_stairs; }
	private final BlockContainer brick_wall;
	public BlockContainer getBrickWall() { return brick_wall; }

	public SculkStoneMaterial() {
		super("sculk_stone", false);
		provider = new BlockContainer(new Block(AbstractBlock.Settings.of(Material.STONE, MapColor.BLACK).requiresTool().strength(2.5F, 6.0F)), ItemSettings());
		slab = new BlockContainer(new HavenSlabBlock(provider.BLOCK), ItemSettings());
		stairs = new BlockContainer(new HavenStairsBlock(provider.BLOCK), ItemSettings());
		wall = new BlockContainer(new HavenWallBlock(provider.BLOCK), ItemSettings());
		bricks = new BlockContainer(new Block(FabricBlockSettings.copy(provider.BLOCK)), ItemSettings());
		brick_slab = new BlockContainer(new HavenSlabBlock(bricks.BLOCK), ItemSettings());
		brick_stairs = new BlockContainer(new HavenStairsBlock(bricks.BLOCK), ItemSettings());
		brick_wall = new BlockContainer(new HavenWallBlock(bricks.BLOCK), ItemSettings());
	}

	public boolean contains(Block block) {
		return provider.contains(block) || slab.contains(block) || stairs.contains(block) || wall.contains(block)
				|| bricks.contains(block) || brick_slab.contains(block) || brick_stairs.contains(block) || brick_wall.contains(block)
				|| super.contains(block);
	}
	public boolean contains(Item item) {
		return provider.contains(item) || slab.contains(item) || stairs.contains(item) || wall.contains(item)
				|| bricks.contains(item) || brick_slab.contains(item) || brick_stairs.contains(item) || brick_wall.contains(item)
				|| super.contains(item);
	}
}
