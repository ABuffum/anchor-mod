package haven.effects;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

public class IrradiatedEffect extends StatusEffect {
    protected IrradiatedEffect(StatusEffectType type, int color) {
        super(StatusEffectType.HARMFUL, 0x00FF00);
    }
}
