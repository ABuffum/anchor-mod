package haven;

import haven.entities.*;
import haven.materials.WoodMaterial;
import haven.particles.*;

import haven.rendering.AnchorBlockEntityRenderer;
import haven.rendering.EntitySpawnPacket;
import haven.rendering.SubstituteAnchorBlockEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Environment(EnvType.CLIENT)
public class HavenModClient implements ClientModInitializer {

	public static final Identifier PacketID = new Identifier(HavenMod.NAMESPACE, "spawn_packet");

	private static final List<Block> Cutout = new ArrayList(List.of(
			//Bone Torch
			HavenMod.BONE_TORCH_BLOCK, HavenMod.BONE_WALL_TORCH_BLOCK,
			//Other Flowers
			HavenMod.MARIGOLD_BLOCK, HavenMod.POTTED_MARIGOLD,
			HavenMod.PINK_ALLIUM_BLOCK, HavenMod.POTTED_PINK_ALLIUM,
			//Cherry Trees
			HavenMod.PALE_CHERRY_LEAVES.BLOCK, HavenMod.PINK_CHERRY_LEAVES.BLOCK,
			HavenMod.WHITE_CHERRY_LEAVES.BLOCK,
			//Cassia Trees & Cinnamon
			HavenMod.FLOWERING_CASSIA_LEAVES.BLOCK,
			//Coffee
			HavenMod.COFFEE_PLANT
	));

	private static final Block[] Translucent = {
			HavenMod.SUBSTITUTE_ANCHOR_BLOCK
	};

	static {
		for(WoodMaterial material : HavenMod.WOOD_MATERIALS) {
			Cutout.add(material.SAPLING.BLOCK);
			Cutout.add(material.SAPLING.POTTED);
			Cutout.add(material.LEAVES.BLOCK);
		}
	}

	@Override
	public void onInitializeClient() {
		BlockEntityRendererRegistry.register(HavenMod.ANCHOR_BLOCK_ENTITY, AnchorBlockEntityRenderer::new);

		BlockEntityRendererRegistry.register(HavenMod.SUBSTITUTE_ANCHOR_BLOCK_ENTITY, SubstituteAnchorBlockEntityRenderer::new);

		BlockRenderLayerMap inst = BlockRenderLayerMap.INSTANCE;
		RenderLayer cutout = RenderLayer.getCutout();
		for(Block block : Cutout) {
			inst.putBlock(block, cutout);
		}
		RenderLayer translucent = RenderLayer.getTranslucent();
		for(Block block : Translucent) {
			inst.putBlock(block, translucent);
		}

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

		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.MARIGOLD_BLOCK, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.POTTED_MARIGOLD, RenderLayer.getCutout());

		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.PINK_ALLIUM_BLOCK, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HavenMod.POTTED_PINK_ALLIUM, RenderLayer.getCutout());
		//Soft TNT
		EntityRendererRegistry.register(HavenMod.SOFT_TNT_ENTITY, SoftTntEntityRenderer::new);
		//Throwable Tomatoes
		EntityRendererRegistry.register(HavenMod.THROWABLE_TOMATO_ENTITY, (context) -> new FlyingItemEntityRenderer(context));
		receiveEntityPacket();
		ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
			registry.register(new Identifier(HavenMod.NAMESPACE, "particle/thrown_tomato"));
		}));
		ParticleFactoryRegistry.getInstance().register(HavenMod.TOMATO_PARTICLE, TomatoParticle.Factory::new);
	}

	public void receiveEntityPacket() {
		ClientSidePacketRegistry.INSTANCE.register(PacketID, (ctx, byteBuf) -> {
			EntityType<?> et = Registry.ENTITY_TYPE.get(byteBuf.readVarInt());
			UUID uuid = byteBuf.readUuid();
			int entityId = byteBuf.readVarInt();
			Vec3d pos = EntitySpawnPacket.PacketBufUtil.readVec3d(byteBuf);
			float pitch = EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);
			float yaw = EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);
			ctx.getTaskQueue().execute(() -> {
				if (MinecraftClient.getInstance().world == null)
					throw new IllegalStateException("Tried to spawn entity in a null world!");
				Entity e = et.create(MinecraftClient.getInstance().world);
				if (e == null)
					throw new IllegalStateException("Failed to create instance of entity \"" + Registry.ENTITY_TYPE.getId(et) + "\"!");
				e.updateTrackedPosition(pos);
				e.setPos(pos.x, pos.y, pos.z);
				e.setPitch(pitch);
				e.setYaw(yaw);
				e.setId(entityId);
				e.setUuid(uuid);
				MinecraftClient.getInstance().world.addEntity(entityId, e);
			});
		});
	}
}