package haven.containers;

import haven.ModBase;
import haven.blocks.basic.ModBedBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class BedContainer extends BlockContainer {
	public BedContainer(String name) {
		this(name, ModBase.ItemSettings().maxCount(1));
	}
	public BedContainer(String name, Item.Settings itemSettings) {
		this(name, AbstractBlock.Settings.copy(Blocks.WHITE_BED), itemSettings);
	}
	public BedContainer(String name, AbstractBlock.Settings blockSettings, Item.Settings itemSettings) {
		super(new ModBedBlock(name, blockSettings), itemSettings);
	}

	public Identifier GetTexture() {
		return ((ModBedBlock) getBlock()).GetTexture();
	}
}
