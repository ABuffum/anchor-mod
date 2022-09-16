package haven.materials.base;

import com.nhoryzon.mc.farmersdelight.item.KnifeItem;
import haven.items.basic.HavenAxeItem;
import haven.items.basic.HavenHoeItem;
import haven.items.basic.HavenHorseArmorItem;
import haven.items.basic.HavenPickaxeItem;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;

public abstract class ToolArmorHorseMaterial extends BaseMaterial implements
		AxeProvider, HoeProvider, PickaxeProvider, ShovelProvider, SwordProvider, KnifeProvider,
		HelmetProvider, ChestplateProvider, LeggingsProvider, BootsProvider, HorseArmorProvider {
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
		super(name, flammable);
		axe = new HavenAxeItem(toolMaterial, axeDmg, axeSpd, ItemSettings());
		hoe = new HavenHoeItem(toolMaterial, hoeDmg, hoeSpd, ItemSettings());
		pickaxe = new HavenPickaxeItem(toolMaterial, picDmg, picSpd, ItemSettings());
		shovel = new ShovelItem(toolMaterial, shvDmg, shvSpd, ItemSettings());
		sword = new SwordItem(toolMaterial, swdDmg, swdSpd, ItemSettings());
		knife = new KnifeItem(toolMaterial, ItemSettings());
		helmet = new ArmorItem(armorMaterial, EquipmentSlot.HEAD, ItemSettings());
		chestplate = new ArmorItem(armorMaterial, EquipmentSlot.CHEST, ItemSettings());
		leggings = new ArmorItem(armorMaterial, EquipmentSlot.LEGS, ItemSettings());
		boots = new ArmorItem(armorMaterial, EquipmentSlot.FEET, ItemSettings());
		horse_armor = new HavenHorseArmorItem(horseArmorBonus, getName(), ItemSettings().maxCount(1));
	}

	public boolean contains(Item item) {
		return item == axe || item == hoe || item == pickaxe || item == shovel || item == sword || item == knife
				|| item == helmet || item == chestplate || item == leggings || item == boots || item == horse_armor
				|| super.contains(item);
	}
}
