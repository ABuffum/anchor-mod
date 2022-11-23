package haven.mixins.items.farmersdelight;

import com.nhoryzon.mc.farmersdelight.item.HotCocoaItem;
import haven.ModBase;
import haven.effects.BoneRotEffect;
import haven.util.MilkUtils;
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
				.stream().filter((x) -> x.getEffectType().getType() == StatusEffectType.HARMFUL)
				.filter((x) -> !ModBase.MILK_IMMUNE_EFFECTS.contains(x))
				.map(StatusEffectInstance::getEffectType).collect(Collectors.toSet());
		if (!compatibleEffectList.isEmpty()) {
			Optional<StatusEffect> var10000 = compatibleEffectList.stream().skip((long)world.getRandom().nextInt(compatibleEffectList.size())).findFirst();
			Objects.requireNonNull(user);
			var10000.ifPresent(user::removeStatusEffect);
		}
		BoneRotEffect.reduce(world, user);
		MilkUtils.CheckLactosIntolerance(world, user);
		ci.cancel();
	}
}
