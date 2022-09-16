package haven.mixins.client.rendering;

import com.google.common.collect.Maps;
import haven.HavenMod;
import haven.util.HavenArmorMaterials;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(ArmorFeatureRenderer.class)
public class ArmorFeatureRendererMixin {
	private static final Map<String, Identifier> HAVENMOD_ARMOR_TEXTURE_CACHE = Maps.newHashMap();

	@Inject(method="getArmorTexture", at = @At("HEAD"), cancellable = true)
	private void GetArmorTexture(ArmorItem item, boolean legs, String overlay, CallbackInfoReturnable<Identifier> cir) {
		ArmorMaterial material = item.getMaterial();
		if (material instanceof HavenArmorMaterials) {
			String var10000 = item.getMaterial().getName();
			String string = "textures/models/armor/" + var10000 + "_layer_" + (legs ? 2 : 1) + (overlay == null ? "" : "_" + overlay) + ".png";
			cir.setReturnValue(HAVENMOD_ARMOR_TEXTURE_CACHE.computeIfAbsent(string, HavenMod::ID));
		}
	}
}
