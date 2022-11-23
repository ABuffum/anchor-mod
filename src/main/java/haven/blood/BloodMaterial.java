package haven.blood;

import haven.ModBase;
import haven.blocks.basic.*;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.*;
import haven.containers.BlockContainer;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class BloodMaterial extends BaseMaterial implements
		FenceProvider, PaneProvider, SlabProvider, StairsProvider, WallProvider {
	private final BlockContainer fence;
	public BlockContainer getFence() { return fence; }
	private final BlockContainer pane;
	public BlockContainer getPane() { return pane; }
	private final BlockContainer slab;
	public BlockContainer getSlab() { return slab; }
	private final BlockContainer stairs;
	public BlockContainer getStairs() { return stairs; }
	private final BlockContainer wall;
	public BlockContainer getWall() { return wall; }
	public BloodMaterial(String name) {
		super(name, false);
		fence = new BlockContainer(new ModFenceBlock(ModBase.BLOOD_BLOCK.getBlock()), ItemSettings());
		pane = new BlockContainer(new ModPaneBlock(ModBase.BLOOD_BLOCK.getBlock()), ItemSettings());
		slab = new BlockContainer(new HavenSlabBlock(ModBase.BLOOD_BLOCK.getBlock()), ItemSettings());
		stairs = new BlockContainer(new HavenStairsBlock(ModBase.BLOOD_BLOCK.getBlock()), ItemSettings());
		wall = new BlockContainer(new HavenWallBlock(ModBase.BLOOD_BLOCK.getBlock()), ItemSettings());
	}

	@Override
	protected Item.Settings ItemSettings() {
		return ModBase.BloodItemSettings();
	}

	public boolean contains(Block block) {
		return block == fence.getBlock() || block == pane.getBlock() || block == slab.getBlock() || block == stairs.getBlock() || block == wall.getBlock() || super.contains(block);
	}
	public boolean contains(Item item) {
		return item == fence.getItem() || item == pane.getItem() || item == slab.getItem() || item == stairs.getItem() || item == wall.getItem() || super.contains(item);
	}
}
