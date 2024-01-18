package fun.mousewich.mixins.item;

import fun.mousewich.ModId;
import fun.mousewich.util.dye.ModDyeColor;
import net.minecraft.item.FireworkStarItem;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FireworkStarItem.class)
public class FireworkStarItemMixin {
	@Inject(method="getColorText", at=@At("HEAD"), cancellable=true)
	private static void GetModColorText(int color, CallbackInfoReturnable<Text> cir) {
		ModDyeColor dyeColor = ModDyeColor.byFireworkColor(color);
		if (dyeColor == null || dyeColor.getId() == DyeColor.byId(dyeColor.getId()).getId()) return;
		cir.setReturnValue(new TranslatableText("item." + ModId.NAMESPACE + ".firework_star." + dyeColor.getName()));
	}
}
