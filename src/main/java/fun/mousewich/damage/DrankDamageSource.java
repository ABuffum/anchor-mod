package fun.mousewich.damage;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class DrankDamageSource extends ModDamageSource {
	public DrankDamageSource(String name) { super(name); }

	public Text getDeathMessage(LivingEntity entity) {
		LivingEntity livingEntity = entity.getPrimeAdversary();
		String string = "death.drank." + this.name;
		String string2 = string + ".player";
		return livingEntity != null
				? new TranslatableText(string2, entity.getDisplayName(), livingEntity.getDisplayName())
				: new TranslatableText(string, entity.getDisplayName());
	}
	@Override
	public Entity getAttacker() { return null; }
}
