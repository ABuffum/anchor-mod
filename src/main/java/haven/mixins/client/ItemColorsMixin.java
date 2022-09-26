package haven.mixins.client;

import haven.HavenModClient;
import net.minecraft.client.color.item.ItemColors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemColors.class)
public class ItemColorsMixin {
	@Inject(method="create", at = @At("RETURN"))
	private static void create(CallbackInfoReturnable<ItemColors> cir) {
		HavenModClient.RegisterItemColors(cir.getReturnValue());
	}
}
