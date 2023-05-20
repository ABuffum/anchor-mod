package fun.mousewich.damage;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;

public class ModEntityDamageSource extends EntityDamageSource {
	public ModEntityDamageSource(String name, Entity source) {
		super(name, source);
	}

	public static DamageSource leeching(Entity attacker) {
		return new ModEntityDamageSource("leeching", attacker).setBypassesArmor().setUsesMagic();
	}
	public static DamageSource sonicBoom(Entity attacker) {
		return new ModEntityDamageSource("sonic_boom", attacker).setBypassesArmor().setUsesMagic();//.setBypassesProtection();
	}
	public static DamageSource quills(Entity attacker) {
		return new ModEntityDamageSource("quillls", attacker).setBypassesArmor();
	}
}
