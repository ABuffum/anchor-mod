package fun.mousewich.mixins.item.farmersdelight;

import com.nhoryzon.mc.farmersdelight.item.HotCocoaItem;
import fun.mousewich.effect.ModStatusEffect;
import fun.mousewich.util.MilkUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Mixin(HotCocoaItem.class)
public class HotCocoaItemMixin {
	@Inject(method="affectConsumer", at = @At("HEAD"), cancellable = true)
	public void AffectConsumer(ItemStack stack, World world, LivingEntity user, CallbackInfo ci) {
		Set<StatusEffect> compatibleEffectList = user.getStatusEffects()
				.stream().map(StatusEffectInstance::getEffectType)
				.filter(effectType -> effectType.getType() == StatusEffectType.HARMFUL)
				.filter(effectType -> !ModStatusEffect.MILK_IMMUNE_EFFECTS.contains(effectType)).collect(Collectors.toSet());
		if (!compatibleEffectList.isEmpty()) {
			Optional<StatusEffect> var10000 = compatibleEffectList.stream().skip(world.getRandom().nextInt(compatibleEffectList.size())).findFirst();
			Objects.requireNonNull(user);
			var10000.ifPresent(user::removeStatusEffect);
		}
		MilkUtil.CheckLactosIntolerance(world, user);
		ci.cancel();
	}
}
