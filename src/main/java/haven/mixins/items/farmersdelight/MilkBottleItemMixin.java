package haven.mixins.items.farmersdelight;

import com.nhoryzon.mc.farmersdelight.item.MilkBottleItem;
import haven.ModBase;
import haven.effects.BoneRotEffect;
import haven.util.MilkUtils;
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
				.stream().filter((x) -> !ModBase.MILK_IMMUNE_EFFECTS.contains(x)).<StatusEffect>toList();
		if (!activeStatusEffectList.isEmpty()) {
			Optional<StatusEffect> var10000 = activeStatusEffectList.stream().skip((long)world.getRandom().nextInt(activeStatusEffectList.size())).findFirst();
			Objects.requireNonNull(user);
			var10000.ifPresent(user::removeStatusEffect);
		}
		BoneRotEffect.reduce(world, user);
		MilkUtils.CheckLactosIntolerance(world, user);
		ci.cancel();
	}
}
