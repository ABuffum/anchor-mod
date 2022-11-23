package haven.rendering.entities.cow;

import haven.ModBase;
import haven.entities.passive.cow.CowfeeEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

public class CowfeeEntityRenderer extends MobEntityRenderer<CowfeeEntity, CowEntityModel<CowfeeEntity>> {
	private static final Identifier TEXTURE = ModBase.ID("textures/entity/cow/cowfee.png");

	public CowfeeEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new CowEntityModel(context.getPart(EntityModelLayers.COW)), 0.7F);
	}

	public Identifier getTexture(CowfeeEntity cowEntity) {
		return TEXTURE;
	}
}
