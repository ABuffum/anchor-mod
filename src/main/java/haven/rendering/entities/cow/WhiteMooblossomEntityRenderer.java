package haven.rendering.entities.cow;

import haven.ModBase;
import haven.entities.passive.cow.WhiteMooblossomEntity;
import haven.rendering.features.cow.WhiteMooblossomFlowerFeatureRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class WhiteMooblossomEntityRenderer extends MobEntityRenderer<WhiteMooblossomEntity, CowEntityModel<WhiteMooblossomEntity>> {
	private static final Identifier TEXTURE = ModBase.ID("textures/entity/cow/white_mooblossom.png");

	public WhiteMooblossomEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new CowEntityModel(context.getPart(EntityModelLayers.MOOSHROOM)), 0.7F);
		this.addFeature(new WhiteMooblossomFlowerFeatureRenderer(this));
	}

	public Identifier getTexture(WhiteMooblossomEntity mooblossomEntity) {
		return TEXTURE;
	}
}
