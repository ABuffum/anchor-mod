package haven.mixins.client.gui;

import haven.ModBase;
import haven.ModTags;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
	private static Map<Function<ItemStack, Boolean>, Pair<Identifier, Float>> OVERLAYS = new HashMap<>(Map.ofEntries(
		Overlay(ModTags.Items.CARVED_PUMPKINS, "minecraft:textures/misc/pumpkinblur.png", 1.0F),
		Overlay(ModBase.CARVED_MELON.getItem(), "haven:textures/misc/melonblur.png", 1.0F),
		Overlay(ModBase.TINTED_GOGGLES, "haven:textures/misc/tinted_glass.png", 1.0F)
	));
	@Shadow
	private void renderOverlay(Identifier texture, float opacity) { }
	@Shadow
	private MinecraftClient client;

	@Inject(method="render", at = @At(value = "INVOKE_ASSIGN", target="Lnet/minecraft/entity/player/PlayerInventory;getArmorStack(I)Lnet/minecraft/item/ItemStack;"))
	public void render(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
		ItemStack itemStack = client.player.getInventory().getArmorStack(3);
		for(Function<ItemStack, Boolean> key : OVERLAYS.keySet()) {
			if (key.apply(itemStack)) {
				Pair<Identifier, Float> value = OVERLAYS.get(key);
				renderOverlay(value.getLeft(), value.getRight());
				break;
			}
		}
	}

	private static Map.Entry<Function<ItemStack, Boolean>, Pair<Identifier, Float>> Overlay(Item item, String id, float opacity) {
		return Overlay((stack) -> stack.isOf(item), id, opacity);
	}
	private static Map.Entry<Function<ItemStack, Boolean>, Pair<Identifier, Float>> Overlay(Tag.Identified<Item> tag, String id, float opacity) {
		return Overlay((stack) -> stack.isIn(tag), id, opacity);
	}
	private static Map.Entry<Function<ItemStack, Boolean>, Pair<Identifier, Float>> Overlay(Function<ItemStack, Boolean> key, String id, float opacity) {
		return Map.entry(key, new Pair<>(new Identifier(id), opacity));
	}
}
