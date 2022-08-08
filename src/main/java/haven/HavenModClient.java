package haven;

import haven.HavenMod;

import haven.anchors.AnchorBlockEntityRenderer;
import haven.entities.SoftTntEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@Environment(EnvType.CLIENT)
public class HavenModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
    	BlockEntityRendererRegistry.register(HavenMod.ANCHOR_BLOCK_ENTITY, AnchorBlockEntityRenderer::new);
		//Bone Torch
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.BONE_TORCH_BLOCK, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.BONE_WALL_TORCH_BLOCK, RenderLayer.getCutout());
		//Carnations
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.BLACK_CARNATION_BLOCK, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.POTTED_BLACK_CARNATION, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.BLUE_CARNATION_BLOCK, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.POTTED_BLUE_CARNATION, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.BROWN_CARNATION_BLOCK, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.POTTED_BROWN_CARNATION, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.CYAN_CARNATION_BLOCK, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.POTTED_CYAN_CARNATION, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.GRAY_CARNATION_BLOCK, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.POTTED_GRAY_CARNATION, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.GREEN_CARNATION_BLOCK, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.POTTED_GREEN_CARNATION, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.LIGHT_BLUE_CARNATION_BLOCK, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.POTTED_LIGHT_BLUE_CARNATION, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.LIGHT_GRAY_CARNATION_BLOCK, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.POTTED_LIGHT_GRAY_CARNATION, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.LIME_CARNATION_BLOCK, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.POTTED_LIME_CARNATION, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.MAGENTA_CARNATION_BLOCK, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.POTTED_MAGENTA_CARNATION, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.ORANGE_CARNATION_BLOCK, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.POTTED_ORANGE_CARNATION, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.PINK_CARNATION_BLOCK, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.POTTED_PINK_CARNATION, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.PURPLE_CARNATION_BLOCK, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.POTTED_PURPLE_CARNATION, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.RED_CARNATION_BLOCK, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.POTTED_RED_CARNATION, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.WHITE_CARNATION_BLOCK, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.POTTED_WHITE_CARNATION, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.YELLOW_CARNATION_BLOCK, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.POTTED_YELLOW_CARNATION, RenderLayer.getCutout());
		//Soft TNT
		EntityRendererRegistry.register(HavenMod.SOFT_TNT_ENTITY, SoftTntEntityRenderer::new);
		//Cassia Trees & Cinnamon
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.CASSIA_SAPLING_BLOCK, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.POTTED_CASSIA_SAPLING_BLOCK, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.CASSIA_LEAVES_BLOCK, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.FLOWERING_CASSIA_LEAVES_BLOCK, RenderLayer.getCutout());
    }
}