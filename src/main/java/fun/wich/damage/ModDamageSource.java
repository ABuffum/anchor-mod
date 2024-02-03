package fun.wich.damage;

import fun.wich.entity.blood.BloodType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

public class ModDamageSource extends DamageSource {
	public static final DamageSource DETERIORATION = new ModDamageSource("deterioration").setUnblockable().setBypassesArmor();
	public static final DamageSource BLEEDING = new ModDamageSource("bleeding").setUnblockable().setBypassesArmor();
	public static final DamageSource BONE_ROT = new ModDamageSource("bone_rot").setUnblockable().setBypassesArmor();
	public static final DamageSource DRANK_ICHOR = Drank("ichor");
	public static final DamageSource DRANK_ICHOR_AS_ANEMIC = Drank("ichor_as_anemic");
	public static final DamageSource DRANK_ICHOR_AS_VAMPIRE = Drank("ichor_as_vampire");
	public static final DamageSource DRANK_LAVA = Drank("lava").setFire();
	public static final DamageSource DRANK_MAGMA_CREAM = Drank("magma_cream").setFire();
	public static final DamageSource DRANK_MILK = Drank("milk");
	public static final DamageSource DRANK_MUD = Drank("mud");
	public static final DamageSource DRANK_SLIME = Drank("slime");
	public static final DamageSource DRANK_SLUDGE = Drank("sludge");
	public static final DamageSource DRANK_SUGAR_WATER = Drank("sugar_water");
	public static final DamageSource DRANK_WATER = Drank("water");
	public static final DamageSource SUFFOCATION = new ModDamageSource("suffocate").setUnblockable().setBypassesArmor();
	public static final DamageSource ICE_CHUNK = new ModDamageSource("ice_chunk").setFallingBlock();
	public static final DamageSource ICHOR = new ModDamageSource("ichor").setUnblockable().setBypassesArmor();
	public static final DamageSource WITHERING = new ModDamageSource("withering").setUnblockable().setBypassesArmor();
	public static final DamageSource DIE_INSTANTLY = new ModDamageSource("die_instantly").setUnblockable().setBypassesArmor();
	public static InjectedDamageSource Injected(String type, LivingEntity source) {
		return new InjectedDamageSource(type, source).setUnblockable().setBypassesArmor();
	}
	public static InjectedBloodDamageSource Injected(LivingEntity source, BloodType type) {
		return new InjectedBloodDamageSource(source, type).setUnblockable().setBypassesArmor();
	}
	public static ModDamageSource Drank(String name) {
		return new DrankDamageSource(name).setUnblockable().setBypassesArmor();
	}

	public ModDamageSource(String name) { super(name); }

	@Override
	public Entity getAttacker() { return null; }
	@Override
	public ModDamageSource setBypassesArmor() { super.setBypassesArmor(); return this; }
	@Override
	public ModDamageSource setFallingBlock() { super.setFallingBlock(); return this; }
	@Override
	public ModDamageSource setOutOfWorld() { super.setOutOfWorld(); return this; }
	@Override
	public ModDamageSource setUnblockable() { super.setUnblockable(); return this; }
	@Override
	public ModDamageSource setFire() { super.setFire(); return this; }
	@Override
	public ModDamageSource setNeutral() { super.setNeutral(); return this; }
}
