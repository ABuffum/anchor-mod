package haven.items.basic;

import haven.ModBase;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class HavenHorseArmorItem extends HorseArmorItem {
	private static final String ENTITY_TEXTURE_PREFIX = "textures/entity/horse/";
	private final String entityTexture;

	public HavenHorseArmorItem(int bonus, String name, Item.Settings settings) {
		super(bonus, name, settings);
		this.entityTexture = "textures/entity/horse/armor/horse_armor_" + name + ".png";
	}

	@Override
	public Identifier getEntityTexture() {
		return ModBase.ID(this.entityTexture);
	}
}
