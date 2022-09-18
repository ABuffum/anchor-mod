package haven.materials;

import haven.items.basic.HavenHorseArmorItem;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.*;
import haven.util.HavenArmorMaterials;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;

public class StuddedLeatherMaterial extends BaseMaterial implements
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

	public StuddedLeatherMaterial() {
		super("studded_leather", false);
		helmet = new ArmorItem(HavenArmorMaterials.STUDDED_LEATHER, EquipmentSlot.HEAD, ItemSettings());
		chestplate = new ArmorItem(HavenArmorMaterials.STUDDED_LEATHER, EquipmentSlot.CHEST, ItemSettings());
		leggings = new ArmorItem(HavenArmorMaterials.STUDDED_LEATHER, EquipmentSlot.LEGS, ItemSettings());
		boots = new ArmorItem(HavenArmorMaterials.STUDDED_LEATHER, EquipmentSlot.FEET, ItemSettings());
		horse_armor = new HavenHorseArmorItem(4, getName(), ItemSettings().maxCount(1));
	}

	public boolean contains(Item item) {
		return item == helmet || item == chestplate || item == leggings || item == boots || item == horse_armor || super.contains(item);
	}
}
