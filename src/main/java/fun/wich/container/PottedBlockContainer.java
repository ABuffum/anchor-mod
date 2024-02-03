package fun.wich.container;

import fun.wich.ModFactory;
import fun.wich.gen.data.ModDatagen;
import fun.wich.gen.data.loot.DropTable;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;

public class PottedBlockContainer implements IBlockItemContainer {
	private final Block block;
	public Block asBlock() { return block; }
	private final Item item;
	public Item asItem() { return item; }
	private final Block potted;
	public Block getPottedBlock() { return potted; }

	public PottedBlockContainer(Block block) { this(block, ModFactory.ItemSettings()); }
	public PottedBlockContainer(Block block, Item.Settings itemSettings) {
		this.block = block;
		item = new BlockItem(this.block, itemSettings);
		potted = new FlowerPotBlock(this.block, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
		ModDatagen.Cache.Tags.Register(BlockTags.FLOWER_POTS, this.potted);
	}

	public boolean contains(Block block) { return block == this.block || block == this.potted; }
	public boolean contains(Item item) { return item == this.item; }
	public PottedBlockContainer flammable(int burn, int spread) {
		FlammableBlockRegistry.getDefaultInstance().add(this.asBlock(), burn, spread);
		return this;
	}
	public PottedBlockContainer fuel(int fuelTime) {
		FuelRegistry.INSTANCE.add(this.asItem(), fuelTime);
		return this;
	}
	//Drops
	public PottedBlockContainer compostable(float chance) {
		CompostingChanceRegistry.INSTANCE.add(this.asItem(), chance);
		return this;
	}
	public PottedBlockContainer requireSilkTouch() {
		ModDatagen.Cache.Drops.put(this.asBlock(), DropTable.WithSilkTouch(this.asItem()));
		ModDatagen.Cache.Drops.put(this.getPottedBlock(), DropTable.Potted(this.asItem()));
		return this;
	}
	public PottedBlockContainer drops(DropTable drops) {
		ModDatagen.Cache.Drops.put(this.asBlock(), drops);
		return this;
	}
	public PottedBlockContainer dropSelf() {
		drops(DropTable.Drops(this.asItem()));
		return dropPotted();
	}
	public PottedBlockContainer dropPotted() {
		ModDatagen.Cache.Drops.put(this.getPottedBlock(), DropTable.Potted(this.asItem()));
		return this;
	}
	//Tags
	public PottedBlockContainer blockTag(Tag.Identified<Block> tag) { ModDatagen.Cache.Tags.Register(tag, this.block); return this; }
	public PottedBlockContainer itemTag(Tag.Identified<Item> tag) { ModDatagen.Cache.Tags.Register(tag, this.item); return this; }

	public PottedBlockContainer pottedModel() { ModDatagen.Cache.Models.POTTED.add(this); return this; }
}
