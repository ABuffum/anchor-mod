package fun.mousewich.client.render.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.mousewich.ModBase;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public class Generic1x1ContainerScreen extends HandledScreen<Generic1x1ContainerScreenHandler> {
	private static final Identifier TEXTURE = ModBase.ID("textures/gui/container/single_slot.png");

	public Generic1x1ContainerScreen(Generic1x1ContainerScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}
	@Override
	protected void init() {
		super.init();
		this.titleX = (this.backgroundWidth - this.textRenderer.getWidth(this.title)) / 2;
	}
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices);
		super.render(matrices, mouseX, mouseY, delta);
		this.drawMouseoverTooltip(matrices, mouseX, mouseY);
	}
	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		RenderSystem.setShaderTexture(0, TEXTURE);
		int i = (this.width - this.backgroundWidth) / 2;
		int j = (this.height - this.backgroundHeight) / 2;
		this.drawTexture(matrices, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
	}
}
