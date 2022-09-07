package haven.damage;

import net.minecraft.entity.damage.DamageSource;

public class HavenDamageSource extends DamageSource {
	public static final DamageSource DETERIORATION = new HavenDamageSource("deterioration").setUnblockable().setBypassesArmor();
	//Syringe
	public static final DamageSource SYRINGE_BLOOD_LOSS = new HavenDamageSource("syringe_blood_loss").setBypassesArmor();
	public static final DamageSource ICHOR = new HavenDamageSource("ichor").setUnblockable().setBypassesArmor();
	public static final DamageSource INCOMPATIBLE_BLOOD = new HavenDamageSource("incompatible_blood").setBypassesArmor();
	public static final DamageSource INJECTED_WATER = new HavenDamageSource("injected_water").setUnblockable().setBypassesArmor();

	public HavenDamageSource(String name) {
		super(name);
	}

	public HavenDamageSource setBypassesArmor() { return (HavenDamageSource)super.setBypassesArmor(); }
	public HavenDamageSource setFallingBlock() { return (HavenDamageSource)super.setFallingBlock(); }
	public HavenDamageSource setOutOfWorld() { return (HavenDamageSource)super.setOutOfWorld(); }
	public HavenDamageSource setUnblockable() { return (HavenDamageSource)super.setUnblockable(); }
	public HavenDamageSource setFire() { return (HavenDamageSource)super.setFire(); }
	public HavenDamageSource setNeutral() { return (HavenDamageSource)super.setNeutral(); }
}
