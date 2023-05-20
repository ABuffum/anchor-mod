package fun.mousewich.container;

import fun.mousewich.block.FlowerSeedBlock;
import fun.mousewich.gen.data.ModDatagen;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class FlowerPartContainer extends BlockContainer {
	private final Item petals;
	public Item petalsItem() { return this.petals; }

	public FlowerPartContainer(Block flower, Block.Settings blockSettings, Item.Settings itemSettings) {
		this(new FlowerSeedBlock(flower, blockSettings), itemSettings);
	}
	private FlowerPartContainer(FlowerSeedBlock block, Item.Settings settings) {
		super(block, new BlockItem(block, settings));
		this.petals = new Item(settings);
	}

	@Override
	public boolean contains(Item item) {
		return item == petals || super.contains(item);
	}

	public FlowerPartContainer compostable(float seedChance, float petalChance) {
		super.compostable(seedChance);
		CompostingChanceRegistry.INSTANCE.add(this.petals, petalChance);
		return this;
	}
	public FlowerPartContainer dropSelf() { return (FlowerPartContainer)super.dropSelf(); }

	public FlowerPartContainer flowerPartModel() { ModDatagen.Cache.Models.FLOWER_PART.add(this); return this; }
}
