package haven.anchors;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class AnchorsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
    	BlockEntityRendererRegistry.register(Anchors.ANCHOR_BLOCK_ENTITY, AnchorBlockEntityRenderer::new);
    }
}