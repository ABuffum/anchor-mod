package haven.rendering.entities.cow;

import haven.ModBase;
import haven.entities.passive.cow.MooblossomEntity;
import haven.rendering.features.cow.MooblossomFlowerFeatureRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class MooblossomEntityRenderer extends MobEntityRenderer<MooblossomEntity, CowEntityModel<MooblossomEntity>> {
	private static final Identifier TEXTURE = ModBase.ID("textures/entity/cow/mooblossom.png");

	public MooblossomEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new CowEntityModel(context.getPart(EntityModelLayers.MOOSHROOM)), 0.7F);
		this.addFeature(new MooblossomFlowerFeatureRenderer(this));
	}

	public Identifier getTexture(MooblossomEntity mooblossomEntity) {
		return TEXTURE;
	}
}
