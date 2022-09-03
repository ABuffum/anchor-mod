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
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockRenderView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

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
		//Server Blood
		setupFluidRendering(HavenMod.STILL_BLOOD_FLUID, HavenMod.FLOWING_BLOOD_FLUID, HavenMod.ID("blood"), 0xFF0000);
		BlockRenderLayerMap.INSTANCE.putFluids(translucent, HavenMod.STILL_BLOOD_FLUID, HavenMod.FLOWING_BLOOD_FLUID);
	}

	public static void setupFluidRendering(final Fluid still, final Fluid flowing, final Identifier textureFluidId, final int color) {
		final Identifier stillSpriteId = HavenMod.ID("block/" + textureFluidId.getPath() + "_still");
		final Identifier flowingSpriteId = HavenMod.ID("block/" + textureFluidId.getPath() + "_flow");

		// If they're not already present, add the sprites to the block atlas
		ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register((atlasTexture, registry) -> {
			registry.register(stillSpriteId);
			registry.register(flowingSpriteId);
		});

		final Identifier fluidId = Registry.FLUID.getId(still);
		final Identifier listenerId = new Identifier(fluidId.getNamespace(), fluidId.getPath() + "_reload_listener");

		final Sprite[] fluidSprites = {null, null};

		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
			@Override
			public void reload(ResourceManager manager) {
				final Function<Identifier, Sprite> atlas = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);
				fluidSprites[0] = atlas.apply(stillSpriteId);
				fluidSprites[1] = atlas.apply(flowingSpriteId);
			}

			@Override
			public Identifier getFabricId() {
				return listenerId;
			}
		});

		// The FluidRenderer gets the sprites and color from a FluidRenderHandler during rendering
		final FluidRenderHandler renderHandler = new FluidRenderHandler() {
			@Override
			public Sprite[] getFluidSprites(BlockRenderView view, BlockPos pos, FluidState state) {
				return fluidSprites;
			}

			@Override
			public int getFluidColor(BlockRenderView view, BlockPos pos, FluidState state) {
				return color;
			}
		};

		FluidRenderHandlerRegistry.INSTANCE.register(still, renderHandler);
		FluidRenderHandlerRegistry.INSTANCE.register(flowing, renderHandler);
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