package fun.mousewich.item.armor;

import fun.mousewich.ModId;
import fun.mousewich.dispenser.HorseArmorDispenserBehavior;
import fun.mousewich.material.ModArmorMaterials;
import net.minecraft.block.DispenserBlock;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.util.Identifier;

public class ModHorseArmorItem extends HorseArmorItem {
	private final Identifier entityTexture;

	public ModHorseArmorItem(ModArmorMaterials material, Settings settings) {
		this(material.getHorseProtectionAmount(), material.getName(), settings);
	}
	public ModHorseArmorItem(int bonus, String name, Settings settings) {
		super(bonus, name, settings);
		this.entityTexture = ModId.ID("textures/entity/horse/armor/horse_armor_" + name + ".png");
	}
	public ModHorseArmorItem dispenseSilently() {
		DispenserBlock.registerBehavior(this, new HorseArmorDispenserBehavior()::dispenseSilently);
		return this;
	}
	@Override
	public Identifier getEntityTexture() { return this.entityTexture; }
}
