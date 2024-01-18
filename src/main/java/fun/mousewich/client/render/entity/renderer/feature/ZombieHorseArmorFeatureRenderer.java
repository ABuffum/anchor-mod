package fun.mousewich.client.render.entity.renderer.feature;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.item.DyeableHorseArmorItem;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.ItemStack;

@Environment(value= EnvType.CLIENT)
public class ZombieHorseArmorFeatureRenderer extends FeatureRenderer<HorseBaseEntity, HorseEntityModel<HorseBaseEntity>> {
	private final HorseEntityModel<HorseBaseEntity> model;

	public ZombieHorseArmorFeatureRenderer(FeatureRendererContext<HorseBaseEntity, HorseEntityModel<HorseBaseEntity>> context, EntityModelLoader loader) {
		super(context);
		this.model = new HorseEntityModel<>(loader.getModelPart(EntityModelLayers.HORSE_ARMOR));
	}

	@Override
	public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, HorseBaseEntity horseEntity, float f, float g, float h, float j, float k, float l) {
		float p;
		float o;
		float n;
		ItemStack itemStack = horseEntity.getEquippedStack(EquipmentSlot.CHEST);
		if (!(itemStack.getItem() instanceof HorseArmorItem horseArmorItem)) return;
		this.getContextModel().copyStateTo(this.model);
		this.model.animateModel(horseEntity, f, g, h);
		this.model.setAngles(horseEntity, f, g, j, k, l);
		if (horseArmorItem instanceof DyeableHorseArmorItem) {
			int m = ((DyeableHorseArmorItem)horseArmorItem).getColor(itemStack);
			n = (float)(m >> 16 & 0xFF) / 255.0f;
			o = (float)(m >> 8 & 0xFF) / 255.0f;
			p = (float)(m & 0xFF) / 255.0f;
		} else {
			n = 1.0f;
			o = 1.0f;
			p = 1.0f;
		}
		VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(horseArmorItem.getEntityTexture()));
		this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, n, o, p, 1.0f);
	}
}
