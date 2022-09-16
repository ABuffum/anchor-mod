package haven.materials.base;

import haven.HavenMod;
import net.minecraft.block.*;
import net.minecraft.item.Item;

public abstract class BaseMaterial {
	private final String name;
	public String getName() { return name; }
	private boolean flammable = true;
	public boolean isFlammable() { return flammable; }
	public BaseMaterial(String name, boolean flammable) {
		this.name = name;
		this.flammable = flammable;
	}

	protected Item.Settings ItemSettings() {
		return HavenMod.ItemSettings();
	}

	public boolean contains(Block block) { return false; }
	public boolean contains(Item item) { return false; }
}
