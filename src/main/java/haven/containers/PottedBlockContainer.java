package haven.containers;

import haven.ModBase;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class PottedBlockContainer implements IBlockItemContainer {
	private final Block block;
	public Block getBlock() { return block; }
	private final Item item;
	public Item getItem() { return item; }
	private final Block potted;
	public Block getPottedBlock() { return getPotted(); }

	public static final Block.Settings SETTINGS = AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque();

	public PottedBlockContainer(Block block) {
		this(block, ModBase.ItemSettings());
	}

	public PottedBlockContainer(Block block, Item.Settings itemSettings) {
		this.block = block;
		item = new BlockItem(this.getBlock(), itemSettings);
		potted = new FlowerPotBlock(this.getBlock(), SETTINGS);
	}

	public boolean contains(Block block) { return block == this.getBlock() || block == getPotted(); }
	public boolean contains(Item item) { return item == this.getItem(); }

	public Block getPotted() {
		return potted;
	}
}
