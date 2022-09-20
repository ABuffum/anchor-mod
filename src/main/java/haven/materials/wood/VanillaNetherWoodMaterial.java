package haven.materials.wood;

import haven.boats.BoatMaterial;
import haven.boats.HavenBoat;
import haven.materials.providers.BoatProvider;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.item.Item;

public class VanillaNetherWoodMaterial extends VanillaWoodMaterial implements BoatProvider {
	private final HavenBoat boat;
	public HavenBoat getBoat() { return boat; }

	public VanillaNetherWoodMaterial(String name, MapColor mapColor, Block baseBlock, boolean isFlammable) {
		super(name, mapColor);
		if (!isFlammable) flammable = false;
		boat = new HavenBoat(name, baseBlock, !isFlammable(), ItemSettings().maxCount(1));
	}

	public boolean contains(Item item) {
		return item == boat.ITEM || super.contains(item);
	}
}
