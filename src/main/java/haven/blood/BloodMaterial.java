package haven.blood;

import haven.HavenMod;
import haven.blocks.basic.*;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.*;
import haven.util.HavenPair;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class BloodMaterial extends BaseMaterial implements
		FenceProvider, PaneProvider, SlabProvider, StairsProvider, WallProvider {
	private final HavenPair fence;
	public HavenPair getFence() { return fence; }
	private final HavenPair pane;
	public HavenPair getPane() { return pane; }
	private final HavenPair slab;
	public HavenPair getSlab() { return slab; }
	private final HavenPair stairs;
	public HavenPair getStairs() { return stairs; }
	private final HavenPair wall;
	public HavenPair getWall() { return wall; }
	public BloodMaterial(String name) {
		super(name, false);
		fence = new HavenPair(new HavenFenceBlock(HavenMod.BLOOD_BLOCK.BLOCK), ItemSettings());
		pane = new HavenPair(new HavenPaneBlock(HavenMod.BLOOD_BLOCK.BLOCK), ItemSettings());
		slab = new HavenPair(new HavenSlabBlock(HavenMod.BLOOD_BLOCK.BLOCK), ItemSettings());
		stairs = new HavenPair(new HavenStairsBlock(HavenMod.BLOOD_BLOCK.BLOCK), ItemSettings());
		wall = new HavenPair(new HavenWallBlock(HavenMod.BLOOD_BLOCK.BLOCK), ItemSettings());
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
