package haven.rendering.entities.cow;

import haven.ModBase;
import haven.entities.passive.cow.RedMooblossomEntity;
import haven.rendering.features.cow.RedMooblossomFlowerFeatureRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class RedMooblossomEntityRenderer extends MobEntityRenderer<RedMooblossomEntity, CowEntityModel<RedMooblossomEntity>> {
	private static final Identifier TEXTURE = ModBase.ID("textures/entity/cow/red_mooblossom.png");

	public RedMooblossomEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new CowEntityModel(context.getPart(EntityModelLayers.MOOSHROOM)), 0.7F);
		this.addFeature(new RedMooblossomFlowerFeatureRenderer(this));
	}

	public Identifier getTexture(RedMooblossomEntity mooblossomEntity) {
		return TEXTURE;
	}
}
