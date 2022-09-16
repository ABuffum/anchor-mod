package haven.util;

import haven.HavenMod;
import haven.blocks.HavenBedBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class HavenBed extends HavenPair {
	public HavenBed(String name) {
		this(name, HavenMod.ItemSettings().maxCount(1));
	}
	public HavenBed(String name, Item.Settings itemSettings) {
		this(name, AbstractBlock.Settings.copy(Blocks.WHITE_BED), itemSettings);
	}
	public HavenBed(String name, AbstractBlock.Settings blockSettings, Item.Settings itemSettings) {
		super(new HavenBedBlock(name, blockSettings), itemSettings);
	}

	public Identifier GetTexture() {
		return ((HavenBedBlock)BLOCK).GetTexture();
	}
}
