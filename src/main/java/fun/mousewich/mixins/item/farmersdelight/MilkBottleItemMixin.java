package fun.mousewich.mixins.item.farmersdelight;

import com.nhoryzon.mc.farmersdelight.item.MilkBottleItem;
import fun.mousewich.effect.ModStatusEffect;
import fun.mousewich.util.MilkUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Mixin(MilkBottleItem.class)
public class MilkBottleItemMixin {
	@Inject(method="affectConsumer", at = @At("HEAD"), cancellable = true)
	public void AffectConsumer(ItemStack stack, World world, LivingEntity user, CallbackInfo ci) {
		Collection<StatusEffect> activeStatusEffectList = user.getActiveStatusEffects().keySet()
				.stream().filter((x) -> !ModStatusEffect.MILK_IMMUNE_EFFECTS.contains(x)).toList();
		if (!activeStatusEffectList.isEmpty()) {
			Optional<StatusEffect> var10000 = activeStatusEffectList.stream().skip(world.getRandom().nextInt(activeStatusEffectList.size())).findFirst();
			Objects.requireNonNull(user);
			var10000.ifPresent(user::removeStatusEffect);
		}
		MilkUtil.CheckLactosIntolerance(world, user);
		ci.cancel();
	}
}
