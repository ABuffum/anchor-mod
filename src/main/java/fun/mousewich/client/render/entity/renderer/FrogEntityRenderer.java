package fun.mousewich.client.render.entity.renderer;

import fun.mousewich.client.render.entity.ModEntityModelLayers;
import fun.mousewich.client.render.entity.model.FrogEntityModel;
import fun.mousewich.entity.passive.frog.FrogEntity;
import fun.mousewich.entity.variants.FrogVariant;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class FrogEntityRenderer extends MobEntityRenderer<FrogEntity, FrogEntityModel<FrogEntity>> {
    public FrogEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new FrogEntityModel<>(context.getPart(ModEntityModelLayers.FROG)), 0.3F);
    }
    public Identifier getTexture(FrogEntity frogEntity) {
        FrogVariant variant = frogEntity.getVariant();
        if (variant == null) variant = FrogVariant.TEMPERATE;
        return variant.texture;
    }
}
