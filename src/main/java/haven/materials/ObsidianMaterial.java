package haven.materials;

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
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;

public class ObsidianMaterial extends ToolArmorHorseMaterial implements
		SlabProvider, StairsProvider, WallProvider {
	private final HavenPair slab;
	public HavenPair getSlab() { return slab; }
	private final HavenPair stairs;
	public HavenPair getStairs() { return stairs; }
	private final HavenPair wall;
	public HavenPair getWall() { return wall; }

	public ObsidianMaterial() {
		super("obsidian", false, HavenToolMaterials.OBSIDIAN,
				5, -3, -3, 0, 1, -2.8F, 1.5F, -3, 3, -2.4F,
				HavenArmorMaterials.OBSIDIAN, 10);
		slab = new HavenPair(new HavenSlabBlock(Blocks.OBSIDIAN), ItemSettings());
		stairs = new HavenPair(new HavenStairsBlock(Blocks.OBSIDIAN), ItemSettings());
		wall = new HavenPair(new HavenWallBlock(Blocks.OBSIDIAN), ItemSettings());
	}

	public boolean contains(Block block) {
		return block == slab.BLOCK || block == stairs.BLOCK || block == wall.BLOCK || super.contains(block);
	}
	public boolean contains(Item item) {
		return item == slab.ITEM || item == stairs.ITEM || item == wall.ITEM || super.contains(item);
	}
}
