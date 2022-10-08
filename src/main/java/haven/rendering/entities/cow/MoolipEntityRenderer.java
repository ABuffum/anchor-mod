package haven.rendering.entities.cow;

import haven.HavenMod;
import haven.entities.passive.cow.MoolipEntity;
import haven.rendering.features.MoolipFlowerFeatureRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class MoolipEntityRenderer extends MobEntityRenderer<MoolipEntity, CowEntityModel<MoolipEntity>> {
	private static final Identifier TEXTURE = HavenMod.ID("textures/entity/cow/moolip.png");

	public MoolipEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new CowEntityModel(context.getPart(EntityModelLayers.MOOSHROOM)), 0.7F);
		this.addFeature(new MoolipFlowerFeatureRenderer(this));
	}

	public Identifier getTexture(MoolipEntity moolipEntity) {
		return TEXTURE;
	}
}
