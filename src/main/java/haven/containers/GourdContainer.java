package haven.containers;

import haven.blocks.gourd.AttachedGourdStemBlock;
import haven.blocks.gourd.CarvableGourdBlock;
import haven.blocks.gourd.GourdStemBlock;
import haven.blocks.gourd.HavenGourdBlock;
import net.minecraft.block.*;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;

public class GourdContainer {
	private final BlockContainer gourd;
	public BlockContainer getGourd() { return gourd; }
	private final StemContainer stemContainer;
	public StemBlock getStem() { return stemContainer.getStem(); }
	public AttachedStemBlock getAttachedStem() { return stemContainer.getAttached(); }
	public Item getSeeds() { return stemContainer.getSeeds(); }

	public GourdContainer(AbstractBlock.Settings gourdSettings, AbstractBlock.Settings stemSettings, AbstractBlock.Settings attachedStemSettings, Item.Settings itemSettings) {
		gourd = new BlockContainer(new HavenGourdBlock(gourdSettings, () -> getStem(), () -> getAttachedStem()), itemSettings);
		stemContainer = new StemContainer((GourdBlock)gourd.BLOCK, stemSettings, attachedStemSettings, itemSettings);
	}

	public static class StemContainer {
		private final StemBlock stem;
		public StemBlock getStem() { return stem; }
		private final AttachedStemBlock attached;
		public AttachedStemBlock getAttached() { return attached; }
		private final Item seeds;
		public Item getSeeds() { return seeds; }

		public StemContainer(GourdBlock gourd, AbstractBlock.Settings stemSettings, AbstractBlock.Settings attachedSettings, Item.Settings itemSettings) {
			stem = new GourdStemBlock(gourd, () -> getSeeds(), stemSettings);
			attached = new AttachedGourdStemBlock(gourd, () -> getSeeds(), attachedSettings);
			seeds = new AliasedBlockItem(stem, itemSettings);
		}
	}
}
