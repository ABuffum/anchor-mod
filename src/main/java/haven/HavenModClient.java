package haven;

import haven.entities.*;
import haven.materials.WoodMaterial;
import haven.particles.*;

import haven.rendering.AnchorBlockEntityRenderer;
import haven.rendering.EntitySpawnPacket;
import haven.rendering.SubstituteAnchorBlockEntityRenderer;
import haven.util.HavenFlower;
import haven.util.HavenPair;
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
			HavenMod.BONE_TORCH.BLOCK, HavenMod.BONE_TORCH.WALL_BLOCK,
			//Other Flowers
			HavenMod.MARIGOLD.BLOCK, HavenMod.MARIGOLD.POTTED,
			HavenMod.PINK_ALLIUM.BLOCK, HavenMod.PINK_ALLIUM.POTTED,
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
		for (HavenFlower flower : HavenMod.FLOWERS) {
			Cutout.add(flower.BLOCK);
			Cutout.add(flower.POTTED);
		}
		for (HavenPair leaf : HavenMod.LEAVES) {
			Cutout.add(leaf.BLOCK);
		}
		for (WoodMaterial material : HavenMod.WOOD_MATERIALS) {
			Cutout.add(material.SAPLING.BLOCK);
			Cutout.add(material.SAPLING.POTTED);
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