package fun.wich.client.render.entity.renderer;

import fun.wich.client.render.entity.ModEntityModelLayers;
import fun.wich.client.render.entity.model.FrogEntityModel;
import fun.wich.entity.passive.frog.FrogEntity;
import fun.wich.entity.variants.FrogVariant;
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
