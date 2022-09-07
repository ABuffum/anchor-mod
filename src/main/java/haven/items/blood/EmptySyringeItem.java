package haven.items.blood;

import haven.util.BloodType;
import haven.damage.HavenDamageSource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

public class EmptySyringeItem extends BaseSyringeItem {
	public EmptySyringeItem(Settings settings) { super(settings); }

	@Override
	public void ApplyEffect(PlayerEntity user, LivingEntity entity) {
		entity.damage(HavenDamageSource.SYRINGE_BLOOD_LOSS,1);
	}

	@Override
	public Item GetReplacementSyringe(PlayerEntity user, LivingEntity entity) {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType != BloodType.NONE) return BloodType.GetSyringe(bloodType);
		else return super.GetReplacementSyringe(user, entity);
	}
}
