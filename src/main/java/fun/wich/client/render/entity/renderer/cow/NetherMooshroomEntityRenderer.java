package fun.wich.client.render.entity.renderer.cow;

import fun.wich.client.render.entity.renderer.feature.cow.NetherMooshroomMushroomFeatureRenderer;
import fun.wich.entity.passive.cow.NetherMooshroomEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class NetherMooshroomEntityRenderer extends MobEntityRenderer<NetherMooshroomEntity, CowEntityModel<NetherMooshroomEntity>> {
	public NetherMooshroomEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new CowEntityModel<>(context.getPart(EntityModelLayers.MOOSHROOM)), 0.7F);
		this.addFeature(new NetherMooshroomMushroomFeatureRenderer<>(this));
	}
	public Identifier getTexture(NetherMooshroomEntity entity) { return entity.getVariant().texture; }
}