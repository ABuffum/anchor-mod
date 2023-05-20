package fun.mousewich.client.render.entity.renderer;

import fun.mousewich.client.render.entity.model.ModVexEntityModel;
import fun.mousewich.client.render.entity.renderer.feature.HeldItemFeatureRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

@Environment(value= EnvType.CLIENT)
public class ModVexEntityRenderer extends MobEntityRenderer<VexEntity, ModVexEntityModel> {
	private static final Identifier TEXTURE = new Identifier("textures/entity/illager/vex.png");
	private static final Identifier CHARGING_TEXTURE = new Identifier("textures/entity/illager/vex_charging.png");

	public ModVexEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new ModVexEntityModel(context.getPart(EntityModelLayers.VEX)), 0.3f);
		this.addFeature(new HeldItemFeatureRenderer<>(this, MinecraftClient.getInstance().getHeldItemRenderer()));
	}
	@Override
	protected int getBlockLight(VexEntity vexEntity, BlockPos blockPos) { return 15; }
	@Override
	public Identifier getTexture(VexEntity vexEntity) {
		if (vexEntity.isCharging()) return CHARGING_TEXTURE;
		return TEXTURE;
	}
}
