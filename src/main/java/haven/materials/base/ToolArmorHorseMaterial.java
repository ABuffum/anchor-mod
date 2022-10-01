package haven.materials.base;

import haven.items.basic.HavenHorseArmorItem;
import haven.materials.providers.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;

public abstract class ToolArmorHorseMaterial extends BaseToolMaterial implements
		HelmetProvider, ChestplateProvider, LeggingsProvider, BootsProvider, HorseArmorProvider {
	private final Item helmet;
	public Item getHelmet() { return helmet; }
	private final Item chestplate;
	public Item getChestplate() { return chestplate; }
	private final Item leggings;
	public Item getLeggings() { return leggings; }
	private final Item boots;
	public Item getBoots() { return boots; }
	private final Item horse_armor;
	public Item getHorseArmor() { return horse_armor; }

	public ToolArmorHorseMaterial(String name, boolean flammable, ToolMaterial toolMaterial,
								  float axeDmg, float axeSpd,
								  int hoeDmg, float hoeSpd,
								  int picDmg, float picSpd,
								  float shvDmg, float shvSpd,
								  int swdDmg, float swdSpd,
								  ArmorMaterial armorMaterial, int horseArmorBonus) {
		super(name, flammable, toolMaterial, axeDmg, axeSpd, hoeDmg, hoeSpd, picDmg, picSpd, shvDmg, shvSpd, swdDmg, swdSpd);
		helmet = new ArmorItem(armorMaterial, EquipmentSlot.HEAD, ItemSettings());
		chestplate = new ArmorItem(armorMaterial, EquipmentSlot.CHEST, ItemSettings());
		leggings = new ArmorItem(armorMaterial, EquipmentSlot.LEGS, ItemSettings());
		boots = new ArmorItem(armorMaterial, EquipmentSlot.FEET, ItemSettings());
		horse_armor = new HavenHorseArmorItem(horseArmorBonus, getName(), ItemSettings().maxCount(1));
	}

	public boolean contains(Item item) {
		return item == helmet || item == chestplate || item == leggings || item == boots || item == horse_armor
				|| super.contains(item);
	}
}
