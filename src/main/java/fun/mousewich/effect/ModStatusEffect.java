package fun.mousewich.effect;

import net.minecraft.entity.effect.*;

import java.util.HashSet;
import java.util.Set;

public class ModStatusEffect extends StatusEffect {
	public static Set<StatusEffect> MILK_IMMUNE_EFFECTS = new HashSet<>();
	public ModStatusEffect(StatusEffectType category, int color) { super(category, color); }
	public ModStatusEffect milkImmune() { MILK_IMMUNE_EFFECTS.add(this); return this; }
}
