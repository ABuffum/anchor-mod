package fun.wich.client.render.entity.renderer.skeleton;

import fun.wich.client.render.entity.ModEntityModelLayers;
import fun.wich.client.render.entity.model.SunkenSkeletonEntityModel;
import fun.wich.entity.hostile.skeleton.SunkenSkeletonEntity;
import fun.wich.entity.variants.SunkenSkeletonVariant;
import fun.wich.gen.data.tag.ModItemTags;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public class SunkenSkeletonEntityRenderer extends BipedEntityRenderer<SunkenSkeletonEntity, SunkenSkeletonEntityModel> {
	public SunkenSkeletonEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new SunkenSkeletonEntityModel(ctx.getPart(ModEntityModelLayers.SUNKEN_SKELETON)), 0.5f);
		this.addFeature(new ArmorFeatureRenderer<>(this,
				new SunkenSkeletonEntityModel(ctx.getPart(ModEntityModelLayers.SUNKEN_SKELETON_INNER_ARMOR)),
				new SunkenSkeletonEntityModel(ctx.getPart(ModEntityModelLayers.SUNKEN_SKELETON_OUTER_ARMOR))));
	}
	@Override
	public Identifier getTexture(SunkenSkeletonEntity entity) { return SunkenSkeletonVariant.get(entity).texture; }
	@Override
	protected boolean isShaking(SunkenSkeletonEntity entity) { return entity.isShaking(); }
	@Override
	public void render(SunkenSkeletonEntity entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		ItemStack stack = entity.hasStackEquipped(EquipmentSlot.HEAD) ? entity.getEquippedStack(EquipmentSlot.HEAD) : ItemStack.EMPTY;
		boolean noHelmet = stack.isEmpty() || !(stack.isIn(ModItemTags.HELMETS) || stack.isIn(ModItemTags.CARVED_GOURDS));
		for (ModelPart part : this.model.fans) part.visible = noHelmet;
		super.render(entity, f, g, matrixStack, vertexConsumerProvider, i);
	}
}
