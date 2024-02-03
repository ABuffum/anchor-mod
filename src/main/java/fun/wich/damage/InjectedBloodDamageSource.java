package fun.wich.damage;

import fun.wich.entity.blood.BloodType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class InjectedBloodDamageSource extends EntityDamageSource {
	private final BloodType bloodType;
	public InjectedBloodDamageSource(Entity source, BloodType type) {
		super("default", source);
		bloodType = type;
	}
	public Text getDeathMessage(LivingEntity entity) {
		String string = "death.injected.default";
		String string2 = string + ".player";
		LivingEntity livingEntity = (source == entity) ? null : source instanceof LivingEntity ? (LivingEntity)source : null;
		return livingEntity != null
				? new TranslatableText(string2, entity.getDisplayName(), livingEntity.getDisplayName(), Text.of(bloodType.getName()))
				: new TranslatableText(string, entity.getDisplayName(), Text.of(bloodType.getName()));
	}
	@Override
	public Entity getAttacker() { return null; }
	@Override
	public InjectedBloodDamageSource setFire() { super.setFire(); return this; }
	@Override
	public InjectedBloodDamageSource setUnblockable() { super.setUnblockable(); return this; }
	@Override
	public InjectedBloodDamageSource setBypassesArmor() { super.setBypassesArmor(); return this; }
}
