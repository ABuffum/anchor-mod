package fun.wich.container;

import fun.wich.gen.data.ModDatagen;
import fun.wich.gen.data.loot.DropTable;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;

public class WallBlockContainer implements IWallBlockItemContainer {
	protected final Block block;
	public Block asBlock() { return block; }
	protected final Block wallBlock;
	public Block getWallBlock() { return wallBlock; }
	protected final Item item;
	public Item asItem() { return item; }

	public WallBlockContainer(Block block, Block wallBlock, Item item) {
		this.block = block;
		this.item = item;
		this.wallBlock = wallBlock;
	}

	public boolean contains(Block block) { return block == this.block || block == this.wallBlock; }
	public boolean contains(Item item) { return item == this.item; }

	public WallBlockContainer flammable(int burn, int spread) {
		FlammableBlockRegistry.getDefaultInstance().add(this.block, burn, spread);
		FlammableBlockRegistry.getDefaultInstance().add(this.wallBlock, burn, spread);
		return this;
	}
	public WallBlockContainer fuel(int fuelTime) {
		FuelRegistry.INSTANCE.add(this.item, fuelTime);
		return this;
	}
	public WallBlockContainer compostable(float chance) {
		CompostingChanceRegistry.INSTANCE.add(this.item, chance);
		return this;
	}
	public WallBlockContainer dispenser(DispenserBehavior behavior) {
		DispenserBlock.registerBehavior(this.item, behavior);
		return this;
	}

	public WallBlockContainer blockTag(Tag.Identified<Block> tag) {
		ModDatagen.Cache.Tags.Register(tag, this.block);
		ModDatagen.Cache.Tags.Register(tag, this.wallBlock);
		return this;
	}
	public WallBlockContainer itemTag(Tag.Identified<Item> tag) { ModDatagen.Cache.Tags.Register(tag, this.item); return this; }

	public WallBlockContainer dropSelf() {
		ModDatagen.Cache.Drops.put(this.block, DropTable.Drops(this.item));
		ModDatagen.Cache.Drops.put(this.wallBlock, DropTable.Drops(this.item));
		return this;
	}
}
