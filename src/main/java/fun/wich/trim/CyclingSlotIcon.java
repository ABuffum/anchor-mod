package fun.wich.trim;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;

import java.util.List;

@Environment(value= EnvType.CLIENT)
public class CyclingSlotIcon {
	private final int slotId;
	private List<Identifier> textures = List.of();
	private int timer;
	private int currentIndex;
	public CyclingSlotIcon(int slotId) { this.slotId = slotId; }
	public void updateTexture(List<Identifier> textures) {
		if (!this.textures.equals(textures)) {
			this.textures = textures;
			this.currentIndex = 0;
		}
		if (!this.textures.isEmpty() && ++this.timer % 30 == 0) this.currentIndex = (this.currentIndex + 1) % this.textures.size();
	}
	public void render(ScreenHandler screenHandler, MatrixStack matrices, float delta, int x, int y) {
		Slot slot = screenHandler.getSlot(this.slotId);
		if (this.textures.isEmpty() || slot.hasStack()) return;
		boolean bl = this.textures.size() > 1 && this.timer >= 30;
		float f = bl ? Math.min((float)(this.timer % 30) + delta, 4.0f) / 4.0f : 1.0f;
		if (f < 1.0f) {
			int i = Math.floorMod(this.currentIndex - 1, this.textures.size());
			this.drawIcon(slot, this.textures.get(i), 1.0f - f, matrices, x, y);
		}
		this.drawIcon(slot, this.textures.get(this.currentIndex), f, matrices, x, y);
	}
	private void drawIcon(Slot slot, Identifier texture, float alpha, MatrixStack matrices, int x, int y) {
		Sprite sprite = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).apply(texture);
		RenderSystem.setShaderTexture(0, sprite.getAtlas().getId());
		DrawableHelper.drawSprite(matrices, x + slot.x, y + slot.y, 0, 16, 16, sprite);//, 1.0f, 1.0f, 1.0f, alpha);
		//drawSprite(matrices, x + slot.x, y + slot.y, 0, 16, 16, sprite, 1.0f, 1.0f, 1.0f, alpha);
	}
	public static void drawSprite(MatrixStack matrices, int x, int y, int z, int width, int height, Sprite sprite, float red, float green, float blue, float alpha) {
		drawTexturedQuad(matrices.peek().getModel(), x, x + width, y, y + height, z, sprite.getMinU(), sprite.getMaxU(), sprite.getMinV(), sprite.getMaxV(), red, green, blue, alpha);
	}
	private static void drawTexturedQuad(Matrix4f matrix, int x0, int x1, int y0, int y1, int z, float u0, float u1, float v0, float v1, float red, float green, float blue, float alpha) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
		bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);
		bufferBuilder.vertex(matrix, x0, y1, z).color(red, green, blue, alpha).texture(u0, v1).next();
		bufferBuilder.vertex(matrix, x1, y1, z).color(red, green, blue, alpha).texture(u1, v1).next();
		bufferBuilder.vertex(matrix, x1, y0, z).color(red, green, blue, alpha).texture(u1, v0).next();
		bufferBuilder.vertex(matrix, x0, y0, z).color(red, green, blue, alpha).texture(u0, v0).next();
		BufferRenderer.draw(bufferBuilder);
		bufferBuilder.end();
		RenderSystem.disableBlend();
	}
}
