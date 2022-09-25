package haven.containers;

import haven.HavenMod;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class PottedBlockContainer {
	public final Block BLOCK;
	public final Item ITEM;
	public final Block POTTED;

	public static final Block.Settings SETTINGS = AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque();

	public PottedBlockContainer(Block block) {
		BLOCK = block;
		ITEM = new BlockItem(BLOCK, HavenMod.ItemSettings());
		POTTED = new FlowerPotBlock(BLOCK, SETTINGS);
	}

	public boolean contains(Block block) { return block == BLOCK || block == POTTED; }
	public boolean contains(Item item) { return item == ITEM; }
}
