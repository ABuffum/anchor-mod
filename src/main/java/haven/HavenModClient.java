package haven;

import haven.boats.HavenBoatEntityRenderer;
import haven.boats.HavenBoatType;
import haven.entities.*;
import haven.materials.*;
import haven.particles.*;
import haven.rendering.*;
import haven.util.*;

import net.fabricmc.api.*;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.*;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.resource.*;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.*;
import net.minecraft.item.Items;
import net.minecraft.resource.*;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockRenderView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class HavenModClient implements ClientModInitializer {
	public static final EntityModelLayer FANCY_CHICKEN_ENTITY_MODEL_LAYER = new EntityModelLayer(HavenMod.ID("fancy_chicken"), "main");

	public static final EntityModelLayer BOAT_ENTITY_MODEL_LAYER = new EntityModelLayer(HavenMod.ID("boat"), "main");

	public static final Identifier PacketID = new Identifier(HavenMod.NAMESPACE, "spawn_packet");

	private static final List<Block> Cutout = new ArrayList(List.<Block>of(
		//More Torches
		HavenMod.BONE_TORCH.BLOCK, HavenMod.BONE_TORCH.WALL_BLOCK,
		HavenMod.BAMBOO_TORCH.BLOCK, HavenMod.BAMBOO_TORCH.WALL_BLOCK,
		HavenMod.DRIED_BAMBOO_TORCH.BLOCK, HavenMod.DRIED_BAMBOO_TORCH.WALL_BLOCK,
		//Metal Torches
		HavenMod.COPPER_TORCH.BLOCK, HavenMod.COPPER_TORCH.WALL_BLOCK,
		HavenMod.EXPOSED_COPPER_TORCH.BLOCK, HavenMod.EXPOSED_COPPER_TORCH.WALL_BLOCK,
		HavenMod.WEATHERED_COPPER_TORCH.BLOCK, HavenMod.WEATHERED_COPPER_TORCH.WALL_BLOCK,
		HavenMod.OXIDIZED_COPPER_TORCH.BLOCK, HavenMod.OXIDIZED_COPPER_TORCH.WALL_BLOCK,
		HavenMod.WAXED_COPPER_TORCH.BLOCK, HavenMod.WAXED_COPPER_TORCH.WALL_BLOCK,
		HavenMod.WAXED_EXPOSED_COPPER_TORCH.BLOCK, HavenMod.WAXED_EXPOSED_COPPER_TORCH.WALL_BLOCK,
		HavenMod.WAXED_WEATHERED_COPPER_TORCH.BLOCK, HavenMod.WAXED_WEATHERED_COPPER_TORCH.WALL_BLOCK,
		HavenMod.WAXED_OXIDIZED_COPPER_TORCH.BLOCK, HavenMod.WAXED_OXIDIZED_COPPER_TORCH.WALL_BLOCK,
		HavenMod.GOLD_TORCH.BLOCK, HavenMod.GOLD_TORCH.WALL_BLOCK,
		HavenMod.IRON_TORCH.BLOCK, HavenMod.IRON_TORCH.WALL_BLOCK,
		HavenMod.DARK_IRON_TORCH.BLOCK, HavenMod.DARK_IRON_TORCH.WALL_BLOCK,
		HavenMod.NETHERITE_TORCH.BLOCK, HavenMod.NETHERITE_TORCH.WALL_BLOCK,
		//Bamboo & Dried Bamboo Doors
		HavenMod.BAMBOO.DOOR.BLOCK, HavenMod.BAMBOO.TRAPDOOR.BLOCK,
		HavenMod.DRIED_BAMBOO.DOOR.BLOCK, HavenMod.DRIED_BAMBOO.TRAPDOOR.BLOCK,
		HavenMod.POTTED_DRIED_BAMBOO,
		//Coffee
		HavenMod.COFFEE_PLANT,
		//More Copper
		HavenMod.COPPER_LANTERN.BLOCK, HavenMod.EXPOSED_COPPER_LANTERN.BLOCK,
		HavenMod.WEATHERED_COPPER_LANTERN.BLOCK, HavenMod.OXIDIZED_COPPER_LANTERN.BLOCK,
		HavenMod.WAXED_COPPER_LANTERN.BLOCK, HavenMod.WAXED_EXPOSED_COPPER_LANTERN.BLOCK,
		HavenMod.WAXED_WEATHERED_COPPER_LANTERN.BLOCK, HavenMod.WAXED_OXIDIZED_COPPER_LANTERN.BLOCK,
		HavenMod.COPPER_SOUL_LANTERN.BLOCK, HavenMod.EXPOSED_COPPER_SOUL_LANTERN.BLOCK,
		HavenMod.WEATHERED_COPPER_SOUL_LANTERN.BLOCK, HavenMod.OXIDIZED_COPPER_SOUL_LANTERN.BLOCK,
		HavenMod.WAXED_COPPER_SOUL_LANTERN.BLOCK, HavenMod.WAXED_EXPOSED_COPPER_SOUL_LANTERN.BLOCK,
		HavenMod.WAXED_WEATHERED_COPPER_SOUL_LANTERN.BLOCK, HavenMod.WAXED_OXIDIZED_COPPER_SOUL_LANTERN.BLOCK,
		HavenMod.COPPER_CHAIN.BLOCK, HavenMod.EXPOSED_COPPER_CHAIN.BLOCK,
		HavenMod.WEATHERED_COPPER_CHAIN.BLOCK, HavenMod.OXIDIZED_COPPER_CHAIN.BLOCK,
		HavenMod.WAXED_COPPER_CHAIN.BLOCK, HavenMod.WAXED_EXPOSED_COPPER_CHAIN.BLOCK,
		HavenMod.WAXED_WEATHERED_COPPER_CHAIN.BLOCK, HavenMod.WAXED_OXIDIZED_COPPER_CHAIN.BLOCK,
		HavenMod.COPPER_BARS.BLOCK, HavenMod.EXPOSED_COPPER_BARS.BLOCK,
		HavenMod.WEATHERED_COPPER_BARS.BLOCK, HavenMod.OXIDIZED_COPPER_BARS.BLOCK,
		HavenMod.WAXED_COPPER_BARS.BLOCK, HavenMod.WAXED_EXPOSED_COPPER_BARS.BLOCK,
		HavenMod.WAXED_WEATHERED_COPPER_BARS.BLOCK, HavenMod.WAXED_OXIDIZED_COPPER_BARS.BLOCK,
		//More Gold
		HavenMod.GOLD_LANTERN.BLOCK, HavenMod.GOLD_SOUL_LANTERN.BLOCK, HavenMod.GOLD_CHAIN.BLOCK, HavenMod.GOLD_BARS.BLOCK,
		//More Iron
		HavenMod.IRON_LANTERN.BLOCK, HavenMod.IRON_SOUL_LANTERN.BLOCK, HavenMod.IRON_CHAIN.BLOCK, HavenMod.DARK_IRON_BARS.BLOCK,
		//More Netherite
		HavenMod.NETHERITE_LANTERN.BLOCK, HavenMod.NETHERITE_SOUL_LANTERN.BLOCK, HavenMod.NETHERITE_CHAIN.BLOCK, HavenMod.NETHERITE_BARS.BLOCK
	));

	private static final Block[] Translucent = {
		HavenMod.SUBSTITUTE_ANCHOR_BLOCK
	};

	static {
		for (HavenFlower flower : HavenMod.FLOWERS) {
			Cutout.add(flower.BLOCK);
			Cutout.add(flower.POTTED);
		}
		for (HavenPair flower : HavenMod.TALL_FLOWERS) {
			Cutout.add(flower.BLOCK);
		}
		for (HavenPair leaf : HavenMod.LEAVES) {
			Cutout.add(leaf.BLOCK);
		}
		for (WoodMaterial material : HavenMod.WOOD_MATERIALS) {
			if (material instanceof TreeMaterial treeMaterial) {
				Cutout.add(treeMaterial.SAPLING.BLOCK);
				Cutout.add(treeMaterial.SAPLING.POTTED);
			}
			if (material instanceof MangroveMaterial mangroveMaterial) {
				Cutout.add(mangroveMaterial.PROPAGULE.BLOCK);
				Cutout.add(mangroveMaterial.PROPAGULE.POTTED);
			}
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
		//Flame Particles
		ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
			registry.register(HavenMod.ID("particle/copper_flame"));
		}));
		ParticleFactoryRegistry.getInstance().register(HavenMod.COPPER_FLAME, FlameParticle.Factory::new);
		ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
			registry.register(HavenMod.ID("particle/gold_flame"));
		}));
		ParticleFactoryRegistry.getInstance().register(HavenMod.GOLD_FLAME, FlameParticle.Factory::new);
		ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
			registry.register(HavenMod.ID("particle/iron_flame"));
		}));
		ParticleFactoryRegistry.getInstance().register(HavenMod.IRON_FLAME, FlameParticle.Factory::new);
		ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
			registry.register(HavenMod.ID("particle/netherite_flame"));
		}));
		ParticleFactoryRegistry.getInstance().register(HavenMod.NETHERITE_FLAME, FlameParticle.Factory::new);
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
		//Angel Bat
		EntityRendererRegistry.register(HavenMod.ANGEL_BAT_ENTITY, BatEntityRenderer::new);
		//Chicken Variants
		EntityModelLayerRegistry.registerModelLayer(FANCY_CHICKEN_ENTITY_MODEL_LAYER, FancyChickenModel::getTexturedModelData);
		EntityRendererRegistry.register(HavenMod.FANCY_CHICKEN_ENTITY, FancyChickenEntityRenderer::new);
		//Cow Variants
		EntityRendererRegistry.register(HavenMod.MOOBLOOM_ENTITY, MoobloomEntityRenderer::new);
		EntityRendererRegistry.register(HavenMod.MOOBLOSSOM_ENTITY, MooblossomEntityRenderer::new);
		EntityRendererRegistry.register(HavenMod.MOOLIP_ENTITY, MoolipEntityRenderer::new);
		EntityRendererRegistry.register(HavenMod.ORANGE_MOOBLOSSOM_ENTITY, OrangeMooblossomEntityRenderer::new);
		EntityRendererRegistry.register(HavenMod.CRIMSON_MOOSHROOM_ENTITY, CrimsonMooshroomEntityRenderer::new);
		EntityRendererRegistry.register(HavenMod.WARPED_MOOSHROOM_ENTITY, WarpedMooshroomEntityRenderer::new);
		//Bottled Confetti
		EntityRendererRegistry.register(HavenMod.BOTTLED_CONFETTI_ENTITY, (context) -> new FlyingItemEntityRenderer(context));
		EntityRendererRegistry.register(HavenMod.DROPPED_CONFETTI_ENTITY, EmptyEntityRenderer::new);
		EntityRendererRegistry.register(HavenMod.CONFETTI_CLOUD_ENTITY, EmptyEntityRenderer::new);
		EntityRendererRegistry.register(HavenMod.DROPPED_DRAGON_BREATH_ENTITY, EmptyEntityRenderer::new);
		EntityRendererRegistry.register(HavenMod.DRAGON_BREATH_CLOUD_ENTITY, EmptyEntityRenderer::new);
		//Custom Boats
		EntityModelLayerRegistry.registerModelLayer(BOAT_ENTITY_MODEL_LAYER, BoatEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(HavenMod.BOAT_ENTITY, HavenBoatEntityRenderer::new);
		//1.19 Backport
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