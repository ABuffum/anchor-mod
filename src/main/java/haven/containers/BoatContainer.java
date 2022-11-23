package haven.containers;

import haven.ModBase;
import haven.entities.vehicle.ModBoatType;
import haven.items.basic.ModBoatItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class BoatContainer implements IContainer {
	private final ModBoatType type;
	public ModBoatType getType() { return type; }
	private final ModBoatItem item;
	public ModBoatItem getItem() { return item; }

	public BoatContainer(String name, Block baseBlock, boolean floatsOnLava) {
		this(name, baseBlock, floatsOnLava, ModBase.ItemSettings().maxCount(1));
	}
	public BoatContainer(String name, Block baseBlock, boolean floatsOnLava, Item.Settings itemSettings) {
		type = ModBoatType.Register(new ModBoatType(baseBlock, name, () -> getItem(), floatsOnLava));
		item = new ModBoatItem(getType(), itemSettings);
	}

	@Override
	public boolean contains(Block block) { return false; }

	@Override
	public boolean contains(Item item) { return item == this.getItem(); }
}
