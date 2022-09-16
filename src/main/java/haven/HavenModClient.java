package haven;

import haven.boats.HavenBoatEntityRenderer;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.*;
import haven.particles.*;
import haven.rendering.*;
import haven.rendering.block.AnchorBlockEntityRenderer;
import haven.rendering.block.SubstituteAnchorBlockEntityRenderer;
import haven.rendering.entities.*;
import haven.rendering.models.FancyChickenModel;
import haven.util.*;

import net.fabricmc.api.*;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.*;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.resource.*;
import net.fabricmc.fabric.mixin.object.builder.ModelPredicateProviderRegistrySpecificAccessor;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.*;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemStack;
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
		//Metal Torches
		HavenMod.COPPER_TORCH.BLOCK, HavenMod.COPPER_TORCH.WALL_BLOCK,
		HavenMod.EXPOSED_COPPER_TORCH.BLOCK, HavenMod.EXPOSED_COPPER_TORCH.WALL_BLOCK,
		HavenMod.WEATHERED_COPPER_TORCH.BLOCK, HavenMod.WEATHERED_COPPER_TORCH.WALL_BLOCK,
		HavenMod.OXIDIZED_COPPER_TORCH.BLOCK, HavenMod.OXIDIZED_COPPER_TORCH.WALL_BLOCK,
		HavenMod.WAXED_COPPER_TORCH.BLOCK, HavenMod.WAXED_COPPER_TORCH.WALL_BLOCK,
		HavenMod.WAXED_EXPOSED_COPPER_TORCH.BLOCK, HavenMod.WAXED_EXPOSED_COPPER_TORCH.WALL_BLOCK,
		HavenMod.WAXED_WEATHERED_COPPER_TORCH.BLOCK, HavenMod.WAXED_WEATHERED_COPPER_TORCH.WALL_BLOCK,
		HavenMod.WAXED_OXIDIZED_COPPER_TORCH.BLOCK, HavenMod.WAXED_OXIDIZED_COPPER_TORCH.WALL_BLOCK,
		HavenMod.COPPER_SOUL_TORCH.BLOCK, HavenMod.COPPER_SOUL_TORCH.WALL_BLOCK,
		HavenMod.EXPOSED_COPPER_SOUL_TORCH.BLOCK, HavenMod.EXPOSED_COPPER_SOUL_TORCH.WALL_BLOCK,
		HavenMod.WEATHERED_COPPER_SOUL_TORCH.BLOCK, HavenMod.WEATHERED_COPPER_SOUL_TORCH.WALL_BLOCK,
		HavenMod.OXIDIZED_COPPER_SOUL_TORCH.BLOCK, HavenMod.OXIDIZED_COPPER_SOUL_TORCH.WALL_BLOCK,
		HavenMod.WAXED_COPPER_SOUL_TORCH.BLOCK, HavenMod.WAXED_COPPER_SOUL_TORCH.WALL_BLOCK,
		HavenMod.WAXED_EXPOSED_COPPER_SOUL_TORCH.BLOCK, HavenMod.WAXED_EXPOSED_COPPER_SOUL_TORCH.WALL_BLOCK,
		HavenMod.WAXED_WEATHERED_COPPER_SOUL_TORCH.BLOCK, HavenMod.WAXED_WEATHERED_COPPER_SOUL_TORCH.WALL_BLOCK,
		HavenMod.WAXED_OXIDIZED_COPPER_SOUL_TORCH.BLOCK, HavenMod.WAXED_OXIDIZED_COPPER_SOUL_TORCH.WALL_BLOCK,
		//Bamboo & Dried Bamboo Doors
		HavenMod.BAMBOO_MATERIAL.getDoor().BLOCK, HavenMod.BAMBOO_MATERIAL.getTrapdoor().BLOCK,
		HavenMod.DRIED_BAMBOO_MATERIAL.getDoor().BLOCK, HavenMod.DRIED_BAMBOO_MATERIAL.getTrapdoor().BLOCK,
		HavenMod.POTTED_DRIED_BAMBOO,
		//Strawberries
		HavenMod.STRAWBERRY_BUSH,
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
		//More Iron
		HavenMod.DARK_IRON_MATERIAL.getDoor().BLOCK, HavenMod.DARK_IRON_MATERIAL.getTrapdoor().BLOCK,
		//Backport
		HavenMod.MANGROVE_ROOTS.BLOCK, HavenMod.MANGROVE_MATERIAL.getTrapdoor().BLOCK
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
		for (BaseMaterial material : HavenMod.MATERIALS) {
			if (material instanceof TorchProvider torchProvider) {
				HavenTorch torch = torchProvider.getTorch();
				Cutout.add(torch.BLOCK);
				Cutout.add(torch.WALL_BLOCK);
			}
			if (material instanceof SoulTorchProvider soulTorchProvider) {
				HavenTorch torch = soulTorchProvider.getSoulTorch();
				Cutout.add(torch.BLOCK);
				Cutout.add(torch.WALL_BLOCK);
			}
			if (material instanceof LanternProvider lantern) Cutout.add(lantern.getLantern().BLOCK);
			if (material instanceof SoulLanternProvider soulLantern) Cutout.add(soulLantern.getSoulLantern().BLOCK);
			if (material instanceof ChainProvider chain) Cutout.add(chain.getChain().BLOCK);
			if (material instanceof BarsProvider bars) Cutout.add(bars.getBars().BLOCK);
			if (material instanceof SaplingProvider saplingProvider) {
				HavenSapling sapling = saplingProvider.getSapling();
				Cutout.add(sapling.BLOCK);
				Cutout.add(sapling.POTTED);
			}
			//if (material instanceof PropaguleProvider propaguleProvider) {
			//	HavenPropagule propagule = propaguleProvider.getPropagule();
			//	Cutout.add(propagule.BLOCK);
			//	Cutout.add(propagule.POTTED);
			//}
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
		ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
			registry.register(HavenMod.ID("particle/thrown_tomato"));
		}));
		ParticleFactoryRegistry.getInstance().register(HavenMod.TOMATO_PARTICLE, TomatoParticle.Factory::new);
		//Server Blood
		setupFluidRendering(HavenMod.STILL_BLOOD_FLUID, HavenMod.FLOWING_BLOOD_FLUID, HavenMod.ID("blood"), 0xFF0000);
		BlockRenderLayerMap.INSTANCE.putFluids(translucent, HavenMod.STILL_BLOOD_FLUID, HavenMod.FLOWING_BLOOD_FLUID);
		//Angel Bat
		EntityRendererRegistry.register(HavenMod.ANGEL_BAT_ENTITY, BatEntityRenderer::new);
		//Melon Golem
		EntityRendererRegistry.register(HavenMod.MELON_SEED_PROJECTILE_ENTITY, (context) -> new FlyingItemEntityRenderer(context));
		EntityRendererRegistry.register(HavenMod.MELON_GOLEM_ENTITY, MelonGolemEntityRenderer::new);
		//Rainbow Wool
		EntityRendererRegistry.register(HavenMod.RAINBOW_SHEEP_ENTITY, RainbowSheepEntityRenderer::new);
		//Custom Beds
		for (HavenBed bed : HavenMod.BEDS) {
			ClientSpriteRegistryCallback.event(TexturedRenderLayers.BEDS_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
				registry.register(bed.GetTexture());
			}));
		}
		//Liquid Mud
		setupFluidRendering(HavenMod.STILL_MUD_FLUID, HavenMod.FLOWING_MUD_FLUID, HavenMod.ID("mud"), 0x472804);
		BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getSolid(), HavenMod.STILL_MUD_FLUID, HavenMod.FLOWING_MUD_FLUID);
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
		//Grappling Rod
		ModelPredicateProviderRegistrySpecificAccessor.callRegister(HavenMod.GRAPPLING_ROD, new Identifier("cast"), HavenModClient::castGrapplingRod);
		//
		receiveEntityPacket();
	}
	private static float castGrapplingRod(ItemStack stack, ClientWorld world, LivingEntity entity, int seed) {
		if (entity == null) {
			return 0.0F;
		} else {
			boolean bl = entity.getMainHandStack() == stack;
			boolean bl2 = entity.getOffHandStack() == stack;
			if (entity.getMainHandStack().getItem() instanceof FishingRodItem) {
				bl2 = false;
			}

			return (bl || bl2) && entity instanceof PlayerEntity && ((PlayerEntity)entity).fishHook != null ? 1.0F : 0.0F;
		}
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