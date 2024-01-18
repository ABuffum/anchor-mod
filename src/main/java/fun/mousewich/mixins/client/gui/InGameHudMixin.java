package fun.mousewich.mixins.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.mousewich.ModId;
import fun.mousewich.entity.passive.camel.CamelEntity;
import fun.mousewich.gen.data.tag.ModItemTags;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.JumpingMount;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import org.spongepowered.asm.mixin.Final;
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
	private static final Map<Function<ItemStack, Boolean>, Pair<Identifier, Float>> OVERLAYS = new HashMap<>(Map.ofEntries(
			Overlay(ModItemTags.CARVED_PUMPKINS, new Identifier("textures/misc/pumpkinblur.png"), 1.0F),
			Overlay(ModItemTags.CARVED_MELONS, ModId.ID("textures/misc/melonblur.png"), 1.0F)
	));
	@Shadow
	private void renderOverlay(Identifier texture, float opacity) { }
	@Final
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

	private static JumpingMount getJumpingMount(PlayerEntity player) {
		Entity entity = player.getVehicle();
		return entity instanceof JumpingMount jumpingMount && jumpingMount.canJump() ? jumpingMount : null;
	}

	@Shadow
	private int scaledHeight;

	@Inject(method="renderMountJumpBar", at = @At("HEAD"), cancellable = true)
	public void RenderMountJumpBar(MatrixStack matrices, int x, CallbackInfo ci) {
		if (this.client.player.getVehicle() instanceof CamelEntity camel) {
			this.client.getProfiler().push("jumpBar");
			RenderSystem.setShaderTexture(0, DrawableHelper.GUI_ICONS_TEXTURE);
			int j = (int)(this.client.player.getMountJumpStrength() * 183.0f);
			int k = this.scaledHeight - 32 + 3;
			InGameHud igh = (InGameHud)(Object)this;
			igh.drawTexture(matrices, x, k, 0, 84, 182, 5);
			if (camel.getJumpCooldown() > 0) igh.drawTexture(matrices, x, k, 0, 74, 182, 5);
			else if (j > 0) igh.drawTexture(matrices, x, k, 0, 89, j, 5);
			this.client.getProfiler().pop();
			ci.cancel();
		}
	}

	private static Map.Entry<Function<ItemStack, Boolean>, Pair<Identifier, Float>> Overlay(Item item, Identifier id, float opacity) {
		return Overlay((stack) -> stack.isOf(item), id, opacity);
	}
	private static Map.Entry<Function<ItemStack, Boolean>, Pair<Identifier, Float>> Overlay(Tag.Identified<Item> tag, Identifier id, float opacity) {
		return Overlay((stack) -> stack.isIn(tag), id, opacity);
	}
	private static Map.Entry<Function<ItemStack, Boolean>, Pair<Identifier, Float>> Overlay(Function<ItemStack, Boolean> key, Identifier id, float opacity) {
		return Map.entry(key, new Pair<>(id, opacity));
	}
}
