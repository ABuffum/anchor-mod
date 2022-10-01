package haven.materials.base;

import haven.items.basic.*;
import haven.materials.providers.*;
import net.minecraft.item.*;

public abstract class BaseToolMaterial extends BaseMaterial implements
		AxeProvider, HoeProvider, PickaxeProvider, ShovelProvider, SwordProvider, KnifeProvider {
	private final Item axe;
	public Item getAxe() { return axe; }
	private final Item hoe;
	public Item getHoe() { return hoe; }
	private final Item pickaxe;
	public Item getPickaxe() { return pickaxe; }
	private final Item shovel;
	public Item getShovel() { return shovel; }
	private final Item sword;
	public Item getSword() { return sword; }
	private final Item knife;
	public Item getKnife() { return knife; }

	public BaseToolMaterial(String name, boolean flammable, ToolMaterial toolMaterial,
							float axeDmg, float axeSpd,
							int hoeDmg, float hoeSpd,
							int picDmg, float picSpd,
							float shvDmg, float shvSpd,
							int swdDmg, float swdSpd) {
		super(name, flammable);
		axe = new HavenAxeItem(toolMaterial, axeDmg, axeSpd, ItemSettings());
		hoe = new HavenHoeItem(toolMaterial, hoeDmg, hoeSpd, ItemSettings());
		pickaxe = new HavenPickaxeItem(toolMaterial, picDmg, picSpd, ItemSettings());
		shovel = new HavenShovelItem(toolMaterial, shvDmg, shvSpd, ItemSettings());
		sword = new HavenSwordItem(toolMaterial, swdDmg, swdSpd, ItemSettings());
		knife = new HavenKnifeItem(toolMaterial, ItemSettings());
	}

	public boolean contains(Item item) {
		return item == axe || item == hoe || item == pickaxe || item == shovel || item == sword || item == knife || super.contains(item);
	}
}
