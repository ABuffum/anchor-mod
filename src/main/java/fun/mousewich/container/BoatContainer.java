package fun.mousewich.container;

import fun.mousewich.dispenser.ModBoatDispenserBehavior;
import fun.mousewich.entity.vehicle.ModBoatType;
import fun.mousewich.item.basic.ChestBoatItem;
import fun.mousewich.item.basic.ModBoatItem;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;

public class BoatContainer implements IContainer, ItemConvertible {
	protected final ModBoatType type;
	public ModBoatType getType() { return type; }
	protected final Item item;
	@Override
	public Item asItem() { return this.item; }
	protected final Item chest;
	public Item getChestBoat() { return this.chest; }

	public BoatContainer(String name, IBlockItemContainer baseBlock, boolean floatsOnLava, Item.Settings itemSettings) {
		this(name, baseBlock.asBlock(), floatsOnLava, itemSettings);
	}
	public BoatContainer(String name, Block baseBlock, boolean floatsOnLava, Item.Settings itemSettings) {
		type = ModBoatType.Register(new ModBoatType(baseBlock, name, this::asItem, this::getChestBoat, floatsOnLava));
		item = new ModBoatItem(type, itemSettings);
		chest = new ChestBoatItem(type, itemSettings);
	}

	@Override
	public boolean contains(Block block) { return false; }

	@Override
	public boolean contains(Item item) { return item == this.item; }

	public BoatContainer fuel(int fuelTime) {
		FuelRegistry.INSTANCE.add(this.item, fuelTime);
		return this;
	}
	public BoatContainer dispensable() {
		DispenserBlock.registerBehavior(this.item, new ModBoatDispenserBehavior(this.type));
		return this;
	}
}
