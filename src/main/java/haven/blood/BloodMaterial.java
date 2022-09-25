package haven.blood;

import haven.HavenMod;
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
		fence = new BlockContainer(new HavenFenceBlock(HavenMod.BLOOD_BLOCK.BLOCK), ItemSettings());
		pane = new BlockContainer(new HavenPaneBlock(HavenMod.BLOOD_BLOCK.BLOCK), ItemSettings());
		slab = new BlockContainer(new HavenSlabBlock(HavenMod.BLOOD_BLOCK.BLOCK), ItemSettings());
		stairs = new BlockContainer(new HavenStairsBlock(HavenMod.BLOOD_BLOCK.BLOCK), ItemSettings());
		wall = new BlockContainer(new HavenWallBlock(HavenMod.BLOOD_BLOCK.BLOCK), ItemSettings());
	}

	@Override
	protected Item.Settings ItemSettings() {
		return HavenMod.BloodItemSettings();
	}

	public boolean contains(Block block) {
		return block == fence.BLOCK || block == pane.BLOCK || block == slab.BLOCK || block == stairs.BLOCK || block == wall.BLOCK || super.contains(block);
	}
	public boolean contains(Item item) {
		return item == fence.ITEM || item == pane.ITEM || item == slab.ITEM || item == stairs.ITEM || item == wall.ITEM || super.contains(item);
	}
}
