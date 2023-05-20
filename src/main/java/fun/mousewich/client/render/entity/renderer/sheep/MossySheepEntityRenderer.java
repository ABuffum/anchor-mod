package fun.mousewich.client.render.entity.renderer.sheep;

import fun.mousewich.ModBase;
import fun.mousewich.client.render.entity.renderer.feature.MossySheepMossFeatureRenderer;
import fun.mousewich.entity.passive.sheep.MossySheepEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.SheepEntityModel;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class MossySheepEntityRenderer extends MobEntityRenderer<MossySheepEntity, SheepEntityModel<MossySheepEntity>> {
	public static final Identifier TEXTURE = ModBase.ID("textures/entity/sheep/mossy_sheep.png");
	public MossySheepEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new SheepEntityModel<>(context.getPart(EntityModelLayers.SHEEP)), 0.7F);
		this.addFeature(new MossySheepMossFeatureRenderer(this, context.getModelLoader()));
	}
	@Override
	public Identifier getTexture(MossySheepEntity entity) { return TEXTURE; }
}
