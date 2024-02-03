package fun.wich.container;

import fun.wich.ModFactory;
import fun.wich.block.BlockConvertible;
import fun.wich.gen.data.ModDatagen;
import fun.wich.gen.data.loot.DropTable;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Pair;

public class BlockContainer implements IBlockItemContainer {
	protected final Block block;

	public Block asBlock() { return block; }
	protected final Item item;
	public Item asItem() { return item; }

	public BlockContainer(Block block) { this(block, ModFactory.ItemSettings()); }
	public BlockContainer(Block block, Item.Settings settings) { this(block, new BlockItem(block,settings)); }
	public BlockContainer(Block block, Item item) {
		this.block = block;
		this.item = item;
	}

	public boolean contains(Block block) { return block == this.block; }
	public boolean contains(Item item) { return item == this.item; }

	public BlockContainer flammable(int burn, int spread) {
		FlammableBlockRegistry.getDefaultInstance().add(this.block, burn, spread);
		return this;
	}
	public BlockContainer fuel(int fuelTime) {
		FuelRegistry.INSTANCE.add(this.item, fuelTime);
		return this;
	}
	public BlockContainer compostable(float chance) {
		CompostingChanceRegistry.INSTANCE.add(this.item, chance);
		return this;
	}
	public BlockContainer dispenser(DispenserBehavior behavior) {
		DispenserBlock.registerBehavior(this.item, behavior);
		return this;
	}

	//Tags
	public BlockContainer blockTag(Tag.Identified<Block> tag) { ModDatagen.Cache.Tags.Register(tag, this.block); return this; }
	@SafeVarargs
	public final BlockContainer blockTag(Tag.Identified<Block>... tags) { for (Tag.Identified<Block> tag : tags) blockTag(tag); return this; }
	public BlockContainer itemTag(Tag.Identified<Item> tag) { ModDatagen.Cache.Tags.Register(tag, this.item); return this; }
	@SafeVarargs
	public final BlockContainer itemTag(Tag.Identified<Item>... tags) { for (Tag.Identified<Item> tag : tags) itemTag(tag); return this; }

	//<editor-fold desc="Models">
	public BlockContainer generatedItemModel() { ModDatagen.Cache.Models.GENERATED.add(this.item); return this; }

	public BlockContainer cubeAllModel() { ModDatagen.Cache.Models.CUBE_ALL.add(this); return this; }

	public BlockContainer buttonModel(BlockConvertible base) { return buttonModel(base.asBlock()); }
	public BlockContainer buttonModel(Block base) { ModDatagen.Cache.Models.BUTTON.add(new Pair<>(this, base)); return this; }

	public BlockContainer fenceModel(BlockConvertible base) { return fenceModel(base.asBlock()); }
	public BlockContainer fenceModel(Block base) { ModDatagen.Cache.Models.FENCE.add(new Pair<>(this, base)); return this; }

	public BlockContainer fenceGateModel(BlockConvertible base) { return fenceGateModel(base.asBlock()); }
	public BlockContainer fenceGateModel(Block base) { ModDatagen.Cache.Models.FENCE_GATE.add(new Pair<>(this, base)); return this; }

	public BlockContainer paneModel(BlockConvertible base) { return paneModel(base.asBlock()); }
	public BlockContainer paneModel(Block base) { ModDatagen.Cache.Models.PANE.add(new Pair<>(this, base)); return this; }

	public BlockContainer slabModel(BlockConvertible base) { return slabModel(base.asBlock()); }
	public BlockContainer slabModel(Block base) { ModDatagen.Cache.Models.SLAB.add(new Pair<>(this, base)); return this; }

	public BlockContainer stairsModel(BlockConvertible base) { return stairsModel(base.asBlock()); }
	public BlockContainer stairsModel(Block base) { ModDatagen.Cache.Models.STAIRS.add(new Pair<>(this, base)); return this; }

	public BlockContainer trapdoorModel(boolean orientable) { ModDatagen.Cache.Models.TRAPDOOR.add(new Pair<>(this, orientable)); return this; }

	public BlockContainer wallModel(BlockConvertible base) { return wallModel(base.asBlock()); }
	public BlockContainer wallModel(Block base) { ModDatagen.Cache.Models.WALL.add(new Pair<>(this, base)); return this; }

	public BlockContainer weightedPressurePlateModel(BlockConvertible base) { return weightedPressurePlateModel(base.asBlock()); }
	public BlockContainer weightedPressurePlateModel(Block base) { ModDatagen.Cache.Models.WEIGHTED_PRESSURE_PLATE.add(new Pair<>(this, base)); return this; }

	//Niche
	public BlockContainer bambooFenceModel() { ModDatagen.Cache.Models.BAMBOO_FENCE.add(this); return this; }
	public BlockContainer bambooFenceGateModel() { ModDatagen.Cache.Models.BAMBOO_FENCE_GATE.add(this); return this; }
	//</editor-fold>

	public BlockContainer drops(DropTable dropTable) {
		if (dropTable == null) return dropSelf();
		ModDatagen.Cache.Drops.put(this.block, dropTable);
		return this;
	}
	public BlockContainer drops(Item item) { return drops(DropTable.Drops(item)); }
	public BlockContainer dropSelf() { return drops(this.item); }
	public BlockContainer dropSlabs() { return drops(DropTable.SLAB); }
	public BlockContainer requireSilkTouch() { return drops(DropTable.WithSilkTouch(this.item)); }
	public BlockContainer requireSilkTouchOrDrop(Item item) { return drops(DropTable.SilkTouchOrElse(item)); }
}
