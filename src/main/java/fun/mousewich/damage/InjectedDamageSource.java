package fun.mousewich.damage;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class InjectedDamageSource extends EntityDamageSource {
	public InjectedDamageSource(String name, Entity source) { super(name, source); }
	public Text getDeathMessage(LivingEntity entity) {
		String string = "death.injected." + this.name;
		String string2 = string + ".player";
		LivingEntity livingEntity = (source == entity) ? null : source instanceof LivingEntity ? (LivingEntity)source : null;
		return livingEntity != null
				? new TranslatableText(string2, entity.getDisplayName(), livingEntity.getDisplayName())
				: new TranslatableText(string, entity.getDisplayName());
	}
	@Override
	public Entity getAttacker() { return null; }
	@Override
	public InjectedDamageSource setFire() { super.setFire(); return this; }
	@Override
	public InjectedDamageSource setUnblockable() { super.setUnblockable(); return this; }
	@Override
	public InjectedDamageSource setBypassesArmor() { super.setBypassesArmor(); return this; }
}
