package haven.boats;

import haven.HavenMod;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class HavenBoat {
	public final HavenBoatType TYPE;
	public final HavenBoatItem ITEM;

	public HavenBoat(String name, Block baseBlock, boolean floatsOnLava) {
		this(name, baseBlock, floatsOnLava, HavenMod.ItemSettings().maxCount(1));
	}
	public HavenBoat(String name, Block baseBlock, boolean floatsOnLava, Item.Settings itemSettings) {
		TYPE = HavenBoatType.Register(new HavenBoatType(baseBlock, name, () -> getItem(), floatsOnLava));
		ITEM = new HavenBoatItem(TYPE, itemSettings);
	}
	private HavenBoatItem getItem() { return ITEM; }
}
