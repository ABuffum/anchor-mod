package haven.materials.metal;

import haven.materials.base.ToolArmorHorseMaterial;
import haven.materials.providers.*;
import haven.util.HavenArmorMaterials;
import haven.util.HavenToolMaterials;
import net.minecraft.item.*;

public class CopperMaterial extends ToolArmorHorseMaterial implements
		NuggetProvider, ShearsProvider {
	private final Item nugget;
	public Item getNugget() { return nugget; }
	private final Item shears;
	public Item getShears() { return shears; }

	public CopperMaterial() {
		super("copper", false, HavenToolMaterials.COPPER,
				6, -3, -1, -2, 1, -2.8F, 1.5F, -3, 3, -2.4F,
				HavenArmorMaterials.COPPER, 6);
		nugget = new Item(ItemSettings());
		shears = new ShearsItem(ItemSettings().maxDamage(238));
	}

	public boolean contains(Item item) {
		return item == nugget || item == shears || super.contains(item);
	}
}
