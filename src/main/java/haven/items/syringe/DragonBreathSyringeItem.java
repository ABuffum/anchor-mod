package haven.items.syringe;

import haven.ModBase;

public class DragonBreathSyringeItem extends BaseSyringeItem {
	public DragonBreathSyringeItem() {
		this(new Settings().group(ModBase.BLOOD_ITEM_GROUP).recipeRemainder(ModBase.DIRTY_SYRINGE).maxCount(1));
	}
	public DragonBreathSyringeItem(Settings settings) {
		super(settings);
	}
}
