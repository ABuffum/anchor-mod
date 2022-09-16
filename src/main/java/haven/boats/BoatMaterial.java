package haven.boats;

import haven.boats.HavenBoat;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.BoatProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class BoatMaterial extends BaseMaterial implements BoatProvider {
	private final HavenBoat boat;
	public HavenBoat getBoat() { return boat; }

	public BoatMaterial(String name, boolean flammable, Block baseBlock) {
		super(name, flammable);
		boat = new HavenBoat(name, baseBlock, !isFlammable(), ItemSettings().maxCount(1));
	}

	public boolean contains(Item item) {
		return item == boat.ITEM || super.contains(item);
	}
}
