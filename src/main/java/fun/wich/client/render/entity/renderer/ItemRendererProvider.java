package fun.wich.client.render.entity.renderer;

import net.minecraft.client.render.item.ItemRenderer;

public interface ItemRendererProvider {
	ItemRenderer getItemRenderer();
	void setItemRenderer(ItemRenderer renderer);
}
