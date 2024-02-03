package fun.wich.container;

import fun.wich.ModFactory;
import fun.wich.block.gourd.AttachedGourdStemBlock;
import fun.wich.block.gourd.GourdStemBlock;
import fun.wich.gen.data.ModDatagen;
import fun.wich.gen.data.loot.DropTable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AttachedStemBlock;
import net.minecraft.block.GourdBlock;
import net.minecraft.block.StemBlock;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;

public class GourdStemContainer {
	private final StemBlock stem;
	public StemBlock getStem() { return stem; }
	private final AttachedStemBlock attached;
	public AttachedStemBlock getAttached() { return attached; }
	private final Item seeds;
	public Item getSeeds() { return seeds; }

	public GourdStemContainer(GourdBlock gourd, AbstractBlock.Settings stemSettings, AbstractBlock.Settings attachedSettings) {
		stem = new GourdStemBlock(gourd, this::getSeeds, stemSettings);
		attached = new AttachedGourdStemBlock(gourd, this::getSeeds, attachedSettings);
		seeds = new AliasedBlockItem(stem, ModFactory.ItemSettings());
		ModDatagen.Cache.Drops.put(stem, DropTable.Stem(seeds));
		ModDatagen.Cache.Drops.put(attached, DropTable.Stem(seeds));
	}
}
