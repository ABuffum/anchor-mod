package fun.wich.container;

import fun.wich.gen.data.ModDatagen;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.TallBlockItem;
import net.minecraft.tag.Tag;

public class TallBlockContainer extends BlockContainer {
	public TallBlockContainer(Block block, Item.Settings settings) { super(block, new TallBlockItem(block, settings)); }
	public TallBlockContainer flammable(int burn, int spread) { return (TallBlockContainer)super.flammable(burn, spread); }
	public TallBlockContainer compostable(float chance) { return (TallBlockContainer)super.compostable(chance); }
	public TallBlockContainer dropSelf() { return (TallBlockContainer)super.dropSelf(); }
	public TallBlockContainer generatedItemModel() { return (TallBlockContainer)super.generatedItemModel(); }
	public TallBlockContainer blockTag(Tag.Identified<Block> tag) { ModDatagen.Cache.Tags.Register(tag, this.block); return this; }
	public TallBlockContainer itemTag(Tag.Identified<Item> tag) { ModDatagen.Cache.Tags.Register(tag, this.item); return this; }
}

