package haven;

import haven.boats.HavenBoatEntityRenderer;
import haven.containers.*;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.*;
import haven.particles.*;
import haven.rendering.*;
import haven.rendering.block.AnchorBlockEntityRenderer;
import haven.rendering.block.SubstituteAnchorBlockEntityRenderer;
import haven.rendering.entities.*;
import haven.rendering.models.FancyChickenModel;

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
import net.minecraft.block.StemBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.particle.*;
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
		//Gilded Fungus
		HavenMod.GILDED_ROOTS.BLOCK, HavenMod.GILDED_ROOTS.POTTED, HavenMod.GILDED_MATERIAL.getTrapdoor().BLOCK,
		//Unlit Lanterns
		HavenMod.UNLIT_LANTERN, HavenMod.UNLIT_SOUL_LANTERN,
		//Underwater Torch
		HavenMod.UNDERWATER_TORCH.BLOCK, HavenMod.UNDERWATER_TORCH.WALL_BLOCK,
		HavenMod.UNDERWATER_TORCH.UNLIT.UNLIT, HavenMod.UNDERWATER_TORCH.UNLIT.UNLIT_WALL,
		//Bamboo & Dried Bamboo Doors
		HavenMod.BAMBOO_MATERIAL.getDoor().BLOCK, HavenMod.BAMBOO_MATERIAL.getTrapdoor().BLOCK,
		HavenMod.DRIED_BAMBOO_MATERIAL.getDoor().BLOCK, HavenMod.DRIED_BAMBOO_MATERIAL.getTrapdoor().BLOCK,
		HavenMod.POTTED_DRIED_BAMBOO,
		//White Pumpkin
		HavenMod.WHITE_PUMPKIN.getStem(), HavenMod.WHITE_PUMPKIN.getAttachedStem(),
		//Strawberries
		HavenMod.STRAWBERRY_BUSH,
		//Coffee
		HavenMod.COFFEE_PLANT,
		//More Iron
		HavenMod.DARK_IRON_MATERIAL.getDoor().BLOCK, HavenMod.DARK_IRON_MATERIAL.getTrapdoor().BLOCK,
		//Backport
		HavenMod.MANGROVE_ROOTS.BLOCK, HavenMod.MANGROVE_MATERIAL.getTrapdoor().BLOCK
	));
	private static void setCutout(PottedBlockContainer container) {
		Cutout.add(container.BLOCK);
		Cutout.add(container.POTTED);
	}
	private static void setCutout(TorchContainer.Unlit unlit) {
		Cutout.add(unlit.UNLIT);
		Cutout.add(unlit.UNLIT_WALL);
	}
	private static void setCutout(TorchContainer container) {
		Cutout.add(container.BLOCK);
		Cutout.add(container.WALL_BLOCK);
		setCutout(container.UNLIT);
	}
	private static void setCutout(OxidizableTorchContainer container) {
		setCutout(container.getUnaffected());
		setCutout(container.getExposed());
		setCutout(container.getWeathered());
		setCutout(container.getOxidized());
		setCutout(container.getWaxedUnaffected());
		setCutout(container.getWaxedExposed());
		setCutout(container.getWaxedWeathered());
		setCutout(container.getWaxedOxidized());
	}
	private static void setCutout(OxidizableLanternContainer container) {
		setCutout((OxidizableBlockContainer)container);
		Cutout.add(container.getUnlitUnaffected());
		Cutout.add(container.getUnlitExposed());
		Cutout.add(container.getUnlitWeathered());
		Cutout.add(container.getUnlitOxidized());
		Cutout.add(container.getUnlitWaxedUnaffected());
		Cutout.add(container.getUnlitWaxedExposed());
		Cutout.add(container.getUnlitWaxedWeathered());
		Cutout.add(container.getUnlitWaxedOxidized());
	}
	private static void setCutout(OxidizableBlockContainer container) {
		Cutout.add(container.getUnaffected().BLOCK);
		Cutout.add(container.getExposed().BLOCK);
		Cutout.add(container.getWeathered().BLOCK);
		Cutout.add(container.getOxidized().BLOCK);
		Cutout.add(container.getWaxedUnaffected().BLOCK);
		Cutout.add(container.getWaxedExposed().BLOCK);
		Cutout.add(container.getWaxedWeathered().BLOCK);
		Cutout.add(container.getWaxedOxidized().BLOCK);
	}

	private static final Block[] Translucent = {
		HavenMod.SUBSTITUTE_ANCHOR_BLOCK, HavenMod.TINTED_GLASS_PANE.BLOCK
	};

	static {
		setCutout(HavenMod.UNLIT_TORCH);
		setCutout(HavenMod.UNLIT_SOUL_TORCH);
		setCutout(HavenMod.UNDERWATER_TORCH);
		for (FlowerContainer flower : HavenMod.FLOWERS) setCutout(flower);
		for (BlockContainer flower : HavenMod.TALL_FLOWERS) Cutout.add(flower.BLOCK);
		for (BaseMaterial material : HavenMod.MATERIALS) {
			if (material instanceof TorchProvider torchProvider) setCutout(torchProvider.getTorch());
			if (material instanceof OxidizableTorchProvider oxidizableTorch) setCutout(oxidizableTorch.getOxidizableTorch());
			if (material instanceof SoulTorchProvider soulTorchProvider) setCutout(soulTorchProvider.getSoulTorch());
			if (material instanceof OxidizableSoulTorchProvider oxidizableSoulTorch) setCutout(oxidizableSoulTorch.getOxidizableSoulTorch());
			if (material instanceof LanternProvider lantern) {
				Cutout.add(lantern.getLantern().BLOCK);
				Cutout.add(lantern.getUnlitLantern());
			}
			if (material instanceof OxidizableLanternProvider oxidizableLantern) setCutout(oxidizableLantern.getOxidizableLantern());
			if (material instanceof SoulLanternProvider soulLantern) {
				Cutout.add(soulLantern.getSoulLantern().BLOCK);
				Cutout.add(soulLantern.getUnlitSoulLantern());
			}
			if (material instanceof OxidizableSoulLanternProvider oxidizableSoulLantern) setCutout(oxidizableSoulLantern.getOxidizableSoulLantern());
			if (material instanceof CampfireProvider campfire) Cutout.add(campfire.getCampfire().BLOCK);
			if (material instanceof SoulCampfireProvider soulCampfire) Cutout.add(soulCampfire.getSoulCampfire().BLOCK);
			if (material instanceof ChainProvider chain) Cutout.add(chain.getChain().BLOCK);
			if (material instanceof OxidizableChainProvider oxidizableChain) setCutout(oxidizableChain.getOxidizableChain());
			if (material instanceof BarsProvider bars) Cutout.add(bars.getBars().BLOCK);
			if (material instanceof OxidizableBarsProvider oxidizableBars) setCutout(oxidizableBars.getOxidizableBars());
			if (material instanceof LeavesProvider leaves) Cutout.add(leaves.getLeaves().BLOCK);
			if (material instanceof SaplingProvider sapling) setCutout(sapling.getSapling());
			if (material instanceof FungusProvider fungus) setCutout(fungus.getFungus());
			//if (material instanceof PropaguleProvider propagule) setCutout(propagule.getPropagule());
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
		//Bleeding Obsidian
		ParticleFactoryRegistry.getInstance().register(HavenMod.LANDING_OBSIDIAN_BLOOD, HavenBlockLeakParticle.LandingObsidianBloodFactory::new);
		ParticleFactoryRegistry.getInstance().register(HavenMod.FALLING_OBSIDIAN_BLOOD, HavenBlockLeakParticle.FallingObsidianBloodFactory::new);
		ParticleFactoryRegistry.getInstance().register(HavenMod.DRIPPING_OBSIDIAN_BLOOD, HavenBlockLeakParticle.DrippingObsidianBloodFactory::new);
		//Blood
		ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
			registry.register(HavenMod.ID("particle/blood_bubble"));
			registry.register(HavenMod.ID("particle/blood_splash_0"));
			registry.register(HavenMod.ID("particle/blood_splash_1"));
			registry.register(HavenMod.ID("particle/blood_splash_2"));
			registry.register(HavenMod.ID("particle/blood_splash_3"));
		}));
		ParticleFactoryRegistry.getInstance().register(HavenMod.BLOOD_BUBBLE, WaterBubbleParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(HavenMod.BLOOD_SPLASH, WaterSplashParticle.SplashFactory::new);
		ParticleFactoryRegistry.getInstance().register(HavenMod.DRIPPING_BLOOD, HavenBlockLeakParticle.DrippingBloodFactory::new);
		ParticleFactoryRegistry.getInstance().register(HavenMod.FALLING_BLOOD, HavenBlockLeakParticle.FallingBloodFactory::new);
		ParticleFactoryRegistry.getInstance().register(HavenMod.FALLING_DRIPSTONE_BLOOD, HavenBlockLeakParticle.FallingDripstoneBloodFactory::new);
		setupFluidRendering(HavenMod.STILL_BLOOD_FLUID, HavenMod.FLOWING_BLOOD_FLUID, HavenMod.ID("blood"), 0xFF0000);
		BlockRenderLayerMap.INSTANCE.putFluids(translucent, HavenMod.STILL_BLOOD_FLUID, HavenMod.FLOWING_BLOOD_FLUID);
		//Mud
		ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
			registry.register(HavenMod.ID("particle/mud_bubble"));
			registry.register(HavenMod.ID("particle/mud_splash_0"));
			registry.register(HavenMod.ID("particle/mud_splash_1"));
			registry.register(HavenMod.ID("particle/mud_splash_2"));
			registry.register(HavenMod.ID("particle/mud_splash_3"));
		}));
		ParticleFactoryRegistry.getInstance().register(HavenMod.MUD_BUBBLE, WaterBubbleParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(HavenMod.MUD_SPLASH, WaterSplashParticle.SplashFactory::new);
		ParticleFactoryRegistry.getInstance().register(HavenMod.DRIPPING_MUD, HavenBlockLeakParticle.DrippingMudFactory::new);
		ParticleFactoryRegistry.getInstance().register(HavenMod.FALLING_MUD, HavenBlockLeakParticle.FallingMudFactory::new);
		ParticleFactoryRegistry.getInstance().register(HavenMod.FALLING_DRIPSTONE_MUD, HavenBlockLeakParticle.FallingDripstoneMudFactory::new);
		setupFluidRendering(HavenMod.STILL_MUD_FLUID, HavenMod.FLOWING_MUD_FLUID, HavenMod.ID("mud"), 0x472804);
		BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getSolid(), HavenMod.STILL_MUD_FLUID, HavenMod.FLOWING_MUD_FLUID);
		//Torches
		ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
			registry.register(HavenMod.ID("particle/glow_0"));
			registry.register(HavenMod.ID("particle/glow_1"));
			registry.register(HavenMod.ID("particle/glow_2"));
			registry.register(HavenMod.ID("particle/glow_3"));
			registry.register(HavenMod.ID("particle/glow_4"));
			registry.register(HavenMod.ID("particle/glow_5"));
			registry.register(HavenMod.ID("particle/glow_6"));
			registry.register(HavenMod.ID("particle/glow_7"));
			registry.register(HavenMod.ID("particle/copper_flame"));
			registry.register(HavenMod.ID("particle/gold_flame"));
			registry.register(HavenMod.ID("particle/iron_flame"));
			registry.register(HavenMod.ID("particle/netherite_flame"));
		}));
		ParticleFactoryRegistry.getInstance().register(HavenMod.UNDERWATER_TORCH_GLOW, FlameParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(HavenMod.COPPER_FLAME_PARTICLE, FlameParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(HavenMod.GOLD_FLAME_PARTICLE, FlameParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(HavenMod.IRON_FLAME_PARTICLE, FlameParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(HavenMod.NETHERITE_FLAME_PARTICLE, FlameParticle.Factory::new);
		//Soft TNT
		EntityRendererRegistry.register(HavenMod.SOFT_TNT_ENTITY, SoftTntEntityRenderer::new);
		//Throwable Tomatoes
		EntityRendererRegistry.register(HavenMod.THROWABLE_TOMATO_ENTITY, (context) -> new FlyingItemEntityRenderer(context));
		ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
			registry.register(HavenMod.ID("particle/thrown_tomato"));
		}));
		ParticleFactoryRegistry.getInstance().register(HavenMod.TOMATO_PARTICLE, TomatoParticle.Factory::new);
		//Angel Bat
		EntityRendererRegistry.register(HavenMod.ANGEL_BAT_ENTITY, BatEntityRenderer::new);
		//Melon Golem
		EntityRendererRegistry.register(HavenMod.MELON_SEED_PROJECTILE_ENTITY, (context) -> new FlyingItemEntityRenderer(context));
		EntityRendererRegistry.register(HavenMod.MELON_GOLEM_ENTITY, MelonGolemEntityRenderer::new);
		//White Pumpkin / White Pumpkin Snow Golem
		EntityRendererRegistry.register(HavenMod.WHITE_SNOW_GOLEM_ENTITY, WhiteSnowGolemEntityRenderer::new);
		//Rainbow Wool
		EntityRendererRegistry.register(HavenMod.RAINBOW_SHEEP_ENTITY, RainbowSheepEntityRenderer::new);
		//Custom Beds
		ClientSpriteRegistryCallback.event(TexturedRenderLayers.BEDS_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
			for (BedContainer bed : HavenMod.BEDS) registry.register(bed.GetTexture());
		}));
		//Chicken Variants
		EntityModelLayerRegistry.registerModelLayer(FANCY_CHICKEN_ENTITY_MODEL_LAYER, FancyChickenModel::getTexturedModelData);
		EntityRendererRegistry.register(HavenMod.FANCY_CHICKEN_ENTITY, FancyChickenEntityRenderer::new);
		//Milk Cows
		EntityRendererRegistry.register(HavenMod.COWCOA_ENTITY, CowcoaEntityRenderer::new);
		EntityRendererRegistry.register(HavenMod.COWFEE_ENTITY, CowfeeEntityRenderer::new);
		EntityRendererRegistry.register(HavenMod.STRAWBOVINE_ENTITY, StrawbovineEntityRenderer::new);
		//Flower Mooshrooms
		EntityRendererRegistry.register(HavenMod.MOOBLOOM_ENTITY, MoobloomEntityRenderer::new);
		EntityRendererRegistry.register(HavenMod.MOOLIP_ENTITY, MoolipEntityRenderer::new);
		//Mooblossoms
		EntityRendererRegistry.register(HavenMod.MOOBLOSSOM_ENTITY, MooblossomEntityRenderer::new);
		EntityRendererRegistry.register(HavenMod.ORANGE_MOOBLOSSOM_ENTITY, OrangeMooblossomEntityRenderer::new);
		EntityRendererRegistry.register(HavenMod.PINK_MOOBLOSSOM_ENTITY, PinkMooblossomEntityRenderer::new);
		EntityRendererRegistry.register(HavenMod.RED_MOOBLOSSOM_ENTITY, RedMooblossomEntityRenderer::new);
		EntityRendererRegistry.register(HavenMod.WHITE_MOOBLOSSOM_ENTITY, WhiteMooblossomEntityRenderer::new);
		//Nether Mooshrooms
		EntityRendererRegistry.register(HavenMod.GILDED_MOOSHROOM_ENTITY, GildedMooshroomEntityRenderer::new);
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

	public static void RegisterBlockColors(BlockColors blockColors) {
		//White Pumpkin Stems
		blockColors.registerColorProvider((state, world, pos, tintIndex) -> 14731036, HavenMod.WHITE_PUMPKIN.getAttachedStem());
		blockColors.registerColorProvider((state, world, pos, tintIndex) -> {
			int i = state.get(StemBlock.AGE);
			return (i * 32) << 16 | (255 - i * 8) << 8 | (i * 4);
		}, HavenMod.WHITE_PUMPKIN.getStem());
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