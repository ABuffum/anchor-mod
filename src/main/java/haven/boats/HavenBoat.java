package haven.boats;

import haven.HavenMod;
import haven.materials.WoodMaterial;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class HavenBoat {
	private static final Item.Settings ITEM_SETTINGS = new Item.Settings().maxCount(1).group(HavenMod.ITEM_GROUP);

	public final HavenBoatType TYPE;
	public final HavenBoatItem ITEM;

	public HavenBoat(String name, Block baseBlock) {
		TYPE = HavenBoatType.Register(new HavenBoatType(baseBlock, name, () -> getItem()));
		ITEM = new HavenBoatItem(TYPE, ITEM_SETTINGS);
	}
	private HavenBoatItem getItem() { return ITEM; }
}
