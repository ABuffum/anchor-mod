package haven.damage;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;

public class HavenEntityDamageSource extends EntityDamageSource {
	public HavenEntityDamageSource(String name, Entity source) {
		super(name, source);
	}

	public static DamageSource sonicBoom(Entity attacker) {
		return new HavenEntityDamageSource("sonic_boom", attacker).setBypassesArmor().setUsesMagic();//.setBypassesProtection();
	}
	public static DamageSource quills(Entity attacker) {
		return new HavenEntityDamageSource("quillls", attacker).setBypassesArmor();
	}
}
