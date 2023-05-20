package fun.mousewich.mixins;

import net.minecraft.entity.effect.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(StatusEffects.class)
public class StatusEffectsMixin {
	@ModifyConstant(method="<clinit>", constant=@Constant(intValue = 3484199))
	private static int ReassignWitherColor(int constant) { return 7561558; }
	@ModifyConstant(method="<clinit>", constant=@Constant(intValue = 16773073))
	private static int ReassignSlowFallingColor(int constant) { return 15978425; }
}
