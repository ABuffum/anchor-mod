package haven.blood;

import haven.HavenMod;
import haven.damage.HavenDamageSource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

public class BloodSyringeItem extends BaseSyringeItem {
	public final BloodType bloodType;
	public BloodSyringeItem(BloodType bloodType) {
		this(bloodType, new Item.Settings().group(HavenMod.BLOOD_ITEM_GROUP).recipeRemainder(HavenMod.DIRTY_SYRINGE).maxCount(1));
	}
	public BloodSyringeItem(BloodType bloodType, Settings settings) {
		super(settings);
		this.bloodType = bloodType;
	}

	@Override
	public void ApplyEffect(PlayerEntity user, LivingEntity entity) {
		BloodType entityType = BloodType.Get(entity);
		if (bloodType == entityType) entity.heal(1);
		else entity.damage(HavenDamageSource.INCOMPATIBLE_BLOOD, 1);
	}
}
