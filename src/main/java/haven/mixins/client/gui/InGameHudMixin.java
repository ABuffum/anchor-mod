package haven.mixins.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import haven.HavenMod;
import haven.HavenTags;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.StringVisitable;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
	private static final Identifier PUMPKIN_BLUR = new Identifier("textures/misc/pumpkinblur.png");
	private static final Identifier MELON_BLUR = HavenMod.ID("textures/misc/melonblur.png");
	@Shadow
	private void renderOverlay(Identifier texture, float opacity) { }
	@Shadow
	private MinecraftClient client;

	@Inject(method="render", at = @At(value = "INVOKE_ASSIGN", target="Lnet/minecraft/entity/player/PlayerInventory;getArmorStack(I)Lnet/minecraft/item/ItemStack;"))
	public void render(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
		ItemStack itemStack = client.player.getInventory().getArmorStack(3);
		if (itemStack.isIn(HavenTags.Items.CARVED_PUMPKINS)) renderOverlay(PUMPKIN_BLUR, 1.0F);
		else if (itemStack.isOf(HavenMod.CARVED_MELON.ITEM)) renderOverlay(MELON_BLUR, 1.0F);
	}
}
