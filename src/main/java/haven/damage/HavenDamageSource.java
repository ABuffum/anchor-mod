package haven.damage;

import haven.blood.BloodType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class HavenDamageSource extends DamageSource {
	public static final DamageSource DETERIORATION = new HavenDamageSource("deterioration").setUnblockable().setBypassesArmor();
	public static final DamageSource BLEEDING = new HavenDamageSource("bleeding").setUnblockable().setBypassesArmor();
	public static final DamageSource BONE_ROT = new HavenDamageSource("bone_rot").setUnblockable().setBypassesArmor();
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
	public static final DamageSource ICHOR = new HavenDamageSource("ichor").setUnblockable().setBypassesArmor();
	public static final DamageSource WITHERING = new HavenDamageSource("withering").setUnblockable().setBypassesArmor();
	public static InjectedDamageSource Injected(String type, LivingEntity source) {
		return new InjectedDamageSource(type, source).setUnblockable().setBypassesArmor();
	}
	public static InjectedBloodDamageSource Injected(LivingEntity source, BloodType type) {
		return new InjectedBloodDamageSource(source, type).setUnblockable().setBypassesArmor();
	}
	public static HavenDamageSource Drank(String name) {
		return new DrankDamageSource(name).setUnblockable().setBypassesArmor();
	}

	public HavenDamageSource(String name) {
		super(name);
	}

	@Override
	public Entity getAttacker() { return null; }
	@Override
	public HavenDamageSource setBypassesArmor() { super.setBypassesArmor(); return this; }
	@Override
	public HavenDamageSource setFallingBlock() { super.setFallingBlock(); return this; }
	@Override
	public HavenDamageSource setOutOfWorld() { super.setOutOfWorld(); return this; }
	@Override
	public HavenDamageSource setUnblockable() { super.setUnblockable(); return this; }
	@Override
	public HavenDamageSource setFire() { super.setFire(); return this; }
	@Override
	public HavenDamageSource setNeutral() { super.setNeutral(); return this; }
}
