package fun.mousewich.mixins.client.render.item;

import fun.mousewich.ModBase;
import fun.mousewich.client.render.item.CompassAnglePredicateProvider;
import fun.mousewich.entity.LastDeathPositionStoring;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.item.UnclampedModelPredicateProvider;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelPredicateProviderRegistry.class)
public abstract class ModelPredicateProviderRegistryMixin {
	@Shadow
	private static void register(Item item, Identifier id, UnclampedModelPredicateProvider provider) { }

	@Inject(method="<clinit>", at = @At("TAIL"))
	private static void RegisterRecoveryCompass(CallbackInfo ci) {
		register(ModBase.RECOVERY_COMPASS, new Identifier("angle"), new CompassAnglePredicateProvider((world, stack, entity) -> {
			if (entity instanceof LastDeathPositionStoring deathStoring) return deathStoring.getLastDeathPos().orElse(null);
			return null;
		}));
	}
}
