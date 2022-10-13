package haven;

import haven.boats.HavenBoatEntityRenderer;
import haven.containers.*;
import haven.entities.EntitySpawnPacket;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.*;
import haven.particles.*;
import haven.rendering.block.AnchorBlockEntityRenderer;
import haven.rendering.block.SubstituteAnchorBlockEntityRenderer;
import haven.rendering.entities.*;
import haven.rendering.entities.cow.*;
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
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
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
import net.minecraft.nbt.NbtCompound;
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

	private static final List<Block> CutoutMipped = new ArrayList(List.<Block>of(
		//Hedges
		HavenMod.HEDGE_BLOCK.BLOCK,
		//Backport
		HavenMod.MANGROVE_ROOTS.BLOCK, HavenMod.MANGROVE_MATERIAL.getTrapdoor().BLOCK
	));

	private static final List<Block> Cutout = new ArrayList(List.<Block>of(
		HavenMod.BLUE_MUSHROOM_MATERIAL.getPotted().POTTED,
		//Gilded Fungus
		HavenMod.GILDED_MATERIAL.getFungus().POTTED, HavenMod.GILDED_MATERIAL.getTrapdoor().BLOCK,
		HavenMod.GILDED_ROOTS.BLOCK, HavenMod.GILDED_ROOTS.POTTED,
		//Bone Ladder
		HavenMod.BONE_MATERIAL.getLadder().BLOCK,
		//Lanterns
		HavenMod.UNLIT_LANTERN, HavenMod.UNLIT_SOUL_LANTERN, HavenMod.ENDER_LANTERN.BLOCK, HavenMod.UNLIT_ENDER_LANTERN,
		//Campfires
		HavenMod.ENDER_CAMPFIRE.BLOCK,
		//Underwater Torch
		HavenMod.UNDERWATER_TORCH.BLOCK, HavenMod.UNDERWATER_TORCH.WALL_BLOCK,
		HavenMod.UNDERWATER_TORCH.UNLIT.UNLIT, HavenMod.UNDERWATER_TORCH.UNLIT.UNLIT_WALL,
		//Bamboo & Dried Bamboo Doors
		HavenMod.BAMBOO_MATERIAL.getDoor().BLOCK, HavenMod.BAMBOO_MATERIAL.getTrapdoor().BLOCK,
		HavenMod.DRIED_BAMBOO_MATERIAL.getDoor().BLOCK, HavenMod.DRIED_BAMBOO_MATERIAL.getTrapdoor().BLOCK,
		HavenMod.POTTED_DRIED_BAMBOO,
		//Charred Wood
		HavenMod.CHARRED_MATERIAL.getDoor().BLOCK, HavenMod.CHARRED_MATERIAL.getTrapdoor().BLOCK,
		//Mushroom Wood
		HavenMod.BROWN_MUSHROOM_MATERIAL.getDoor().BLOCK,
		HavenMod.RED_MUSHROOM_MATERIAL.getDoor().BLOCK,
		HavenMod.MUSHROOM_STEM_MATERIAL.getDoor().BLOCK,
		HavenMod.BLUE_MUSHROOM_MATERIAL.getDoor().BLOCK,
		//White Pumpkin
		HavenMod.WHITE_PUMPKIN.getStem(), HavenMod.WHITE_PUMPKIN.getAttachedStem(),
		//Strawberries
		HavenMod.STRAWBERRY_BUSH,
		//Coffee
		HavenMod.COFFEE_PLANT,
		//More Iron
		HavenMod.DARK_IRON_MATERIAL.getDoor().BLOCK, HavenMod.DARK_IRON_MATERIAL.getTrapdoor().BLOCK
	));
	private static void setLayer(List<Block> target, PottedBlockContainer container) {
		target.add(container.BLOCK);
		target.add(container.POTTED);
	}
	private static void setLayer(List<Block> target, TorchContainer.Unlit unlit) {
		target.add(unlit.UNLIT);
		target.add(unlit.UNLIT_WALL);
	}
	private static void setLayer(List<Block> target, TorchContainer container) {
		target.add(container.BLOCK);
		target.add(container.WALL_BLOCK);
		setLayer(target, container.UNLIT);
	}
	private static void setLayer(List<Block> target, OxidizableTorchContainer container) {
		setLayer(target, container.getUnaffected());
		setLayer(target, container.getExposed());
		setLayer(target, container.getWeathered());
		setLayer(target, container.getOxidized());
		setLayer(target, container.getWaxedUnaffected());
		setLayer(target, container.getWaxedExposed());
		setLayer(target, container.getWaxedWeathered());
		setLayer(target, container.getWaxedOxidized());
	}
	private static void setLayer(List<Block> target, OxidizableLanternContainer container) {
		setLayer(target, (OxidizableBlockContainer)container);
		target.add(container.getUnlitUnaffected());
		target.add(container.getUnlitExposed());
		target.add(container.getUnlitWeathered());
		target.add(container.getUnlitOxidized());
		target.add(container.getUnlitWaxedUnaffected());
		target.add(container.getUnlitWaxedExposed());
		target.add(container.getUnlitWaxedWeathered());
		target.add(container.getUnlitWaxedOxidized());
	}
	private static void setLayer(List<Block> target, OxidizableBlockContainer container) {
		target.add(container.getUnaffected().BLOCK);
		target.add(container.getExposed().BLOCK);
		target.add(container.getWeathered().BLOCK);
		target.add(container.getOxidized().BLOCK);
		target.add(container.getWaxedUnaffected().BLOCK);
		target.add(container.getWaxedExposed().BLOCK);
		target.add(container.getWaxedWeathered().BLOCK);
		target.add(container.getWaxedOxidized().BLOCK);
	}

	private static final Block[] Translucent = {
		HavenMod.SUBSTITUTE_ANCHOR_BLOCK, HavenMod.TINTED_GLASS_PANE.BLOCK
	};

	static {
		setLayer(Cutout, HavenMod.UNLIT_TORCH);
		setLayer(Cutout, HavenMod.UNLIT_SOUL_TORCH);
		setLayer(Cutout, HavenMod.ENDER_TORCH);
		setLayer(Cutout, HavenMod.UNDERWATER_TORCH);
		for (FlowerContainer flower : HavenMod.FLOWERS) setLayer(Cutout, flower);
		for (BlockContainer flower : HavenMod.TALL_FLOWERS) Cutout.add(flower.BLOCK);
		for (BaseMaterial material : HavenMod.MATERIALS) {
			if (material instanceof TorchProvider torchProvider) setLayer(Cutout, torchProvider.getTorch());
			if (material instanceof OxidizableTorchProvider oxidizableTorch) setLayer(Cutout, oxidizableTorch.getOxidizableTorch());
			if (material instanceof SoulTorchProvider soulTorchProvider) setLayer(Cutout, soulTorchProvider.getSoulTorch());
			if (material instanceof OxidizableSoulTorchProvider oxidizableSoulTorch) setLayer(Cutout, oxidizableSoulTorch.getOxidizableSoulTorch());
			if (material instanceof EnderTorchProvider enderTorchProvider) setLayer(Cutout, enderTorchProvider.getEnderTorch());
			if (material instanceof OxidizableEnderTorchProvider oxidizableEnderTorch) setLayer(Cutout, oxidizableEnderTorch.getOxidizableEnderTorch());
			if (material instanceof LanternProvider lantern) {
				Cutout.add(lantern.getLantern().BLOCK);
				Cutout.add(lantern.getUnlitLantern());
			}
			if (material instanceof OxidizableLanternProvider oxidizableLantern) setLayer(Cutout, oxidizableLantern.getOxidizableLantern());
			if (material instanceof EnderLanternProvider enderLantern) {
				Cutout.add(enderLantern.getEnderLantern().BLOCK);
				Cutout.add(enderLantern.getUnlitEnderLantern());
			}
			if (material instanceof OxidizableEnderLanternProvider oxidizableEnderLantern) setLayer(Cutout, oxidizableEnderLantern.getOxidizableEnderLantern());
			if (material instanceof SoulLanternProvider soulLantern) {
				Cutout.add(soulLantern.getSoulLantern().BLOCK);
				Cutout.add(soulLantern.getUnlitSoulLantern());
			}
			if (material instanceof OxidizableSoulLanternProvider oxidizableSoulLantern) setLayer(Cutout, oxidizableSoulLantern.getOxidizableSoulLantern());
			if (material instanceof CampfireProvider campfire) Cutout.add(campfire.getCampfire().BLOCK);
			if (material instanceof SoulCampfireProvider soulCampfire) Cutout.add(soulCampfire.getSoulCampfire().BLOCK);
			if (material instanceof EnderCampfireProvider enderCampfire) Cutout.add(enderCampfire.getEnderCampfire().BLOCK);
			if (material instanceof ChainProvider chain) Cutout.add(chain.getChain().BLOCK);
			if (material instanceof OxidizableChainProvider oxidizableChain) setLayer(CutoutMipped, oxidizableChain.getOxidizableChain());
			if (material instanceof BarsProvider bars) CutoutMipped.add(bars.getBars().BLOCK);
			if (material instanceof OxidizableBarsProvider oxidizableBars) setLayer(CutoutMipped, oxidizableBars.getOxidizableBars());
			if (material instanceof LeavesProvider leaves) CutoutMipped.add(leaves.getLeaves().BLOCK);
			if (material instanceof SaplingProvider sapling) setLayer(Cutout, sapling.getSapling());
			//if (material instanceof PropaguleProvider propagule) setLayer(Cutout, propagule.getPropagule());
		}
	}

	@Override
	public void onInitializeClient() {
		BlockEntityRendererRegistry.register(HavenMod.ANCHOR_BLOCK_ENTITY, AnchorBlockEntityRenderer::new);

		BlockEntityRendererRegistry.register(HavenMod.SUBSTITUTE_ANCHOR_BLOCK_ENTITY, SubstituteAnchorBlockEntityRenderer::new);

		BlockRenderLayerMap inst = BlockRenderLayerMap.INSTANCE;
		RenderLayer cutoutMipped = RenderLayer.getCutoutMipped();
		for (Block block : CutoutMipped) inst.putBlock(block, cutoutMipped);
		RenderLayer cutout = RenderLayer.getCutout();
		for(Block block : Cutout) inst.putBlock(block, cutout);
		RenderLayer translucent = RenderLayer.getTranslucent();
		for(Block block : Translucent) inst.putBlock(block, translucent);
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
			registry.register(HavenMod.ID("particle/ender_fire_flame"));
		}));
		ParticleFactoryRegistry.getInstance().register(HavenMod.UNDERWATER_TORCH_GLOW, FlameParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(HavenMod.COPPER_FLAME_PARTICLE, FlameParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(HavenMod.GOLD_FLAME_PARTICLE, FlameParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(HavenMod.IRON_FLAME_PARTICLE, FlameParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(HavenMod.NETHERITE_FLAME_PARTICLE, FlameParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(HavenMod.ENDER_FIRE_FLAME_PARTICLE, FlameParticle.Factory::new);
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
		EntityRendererRegistry.register(HavenMod.MOONILLA_ENTITY, MoonillaEntityRenderer::new);
		//Flower Mooshrooms
		EntityRendererRegistry.register(HavenMod.MOOBLOOM_ENTITY, MoobloomEntityRenderer::new);
		EntityRendererRegistry.register(HavenMod.MOOLIP_ENTITY, MoolipEntityRenderer::new);
		//Mooblossoms
		EntityRendererRegistry.register(HavenMod.MOOBLOSSOM_ENTITY, MooblossomEntityRenderer::new);
		EntityRendererRegistry.register(HavenMod.ORANGE_MOOBLOSSOM_ENTITY, OrangeMooblossomEntityRenderer::new);
		EntityRendererRegistry.register(HavenMod.PINK_MOOBLOSSOM_ENTITY, PinkMooblossomEntityRenderer::new);
		EntityRendererRegistry.register(HavenMod.RED_MOOBLOSSOM_ENTITY, RedMooblossomEntityRenderer::new);
		EntityRendererRegistry.register(HavenMod.WHITE_MOOBLOSSOM_ENTITY, WhiteMooblossomEntityRenderer::new);
		//Blue Mooshroom
		EntityRendererRegistry.register(HavenMod.BLUE_MOOSHROOM_ENTITY, BlueMooshroomEntityRenderer::new);
		//Nether Mooshrooms
		EntityRendererRegistry.register(HavenMod.GILDED_MOOSHROOM_ENTITY, GildedMooshroomEntityRenderer::new);
		EntityRendererRegistry.register(HavenMod.CRIMSON_MOOSHROOM_ENTITY, CrimsonMooshroomEntityRenderer::new);
		EntityRendererRegistry.register(HavenMod.WARPED_MOOSHROOM_ENTITY, WarpedMooshroomEntityRenderer::new);
		//Bottled Confetti
		EntityRendererRegistry.register(HavenMod.BOTTLED_CONFETTI_ENTITY, FlyingItemEntityRenderer::new);
		EntityRendererRegistry.register(HavenMod.DROPPED_CONFETTI_ENTITY, EmptyEntityRenderer::new);
		EntityRendererRegistry.register(HavenMod.CONFETTI_CLOUD_ENTITY, EmptyEntityRenderer::new);
		EntityRendererRegistry.register(HavenMod.DROPPED_DRAGON_BREATH_ENTITY, EmptyEntityRenderer::new);
		EntityRendererRegistry.register(HavenMod.DRAGON_BREATH_CLOUD_ENTITY, EmptyEntityRenderer::new);
		//Custom Boats
		EntityModelLayerRegistry.registerModelLayer(BOAT_ENTITY_MODEL_LAYER, BoatEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(HavenMod.BOAT_ENTITY, HavenBoatEntityRenderer::new);
		//Grappling Rod
		ModelPredicateProviderRegistrySpecificAccessor.callRegister(HavenMod.GRAPPLING_ROD, new Identifier("cast"), HavenModClient::castGrapplingRod);
		//Entity Packets (not sure if I still need this but I might?)
		receiveEntityPacket();
	}

	public static void RegisterBlockColors(BlockColors blockColors) {
		//Gourd Stems
		blockColors.registerColorProvider((state, world, pos, tintIndex) -> 14731036,
				HavenMod.WHITE_PUMPKIN.getAttachedStem()
		);
		blockColors.registerColorProvider((state, world, pos, tintIndex) -> {
				int i = state.get(StemBlock.AGE);
				return (i * 32) << 16 | (255 - i * 8) << 8 | (i * 4);
			},
				HavenMod.WHITE_PUMPKIN.getStem()
		);
		//Generic Foliage
		blockColors.registerColorProvider((state, world, pos, tintIndex) ->
			world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor(),
				//Hedges
				HavenMod.HEDGE_BLOCK.BLOCK,
				//Mangrove Leaves
				HavenMod.MANGROVE_MATERIAL.getLeaves().BLOCK
		);
	}
	public static void RegisterItemColors(ItemColors itemColors) {
		//Generic Foliage
		itemColors.register((itemStack, i) -> 9619016,
				//Hedges
				HavenMod.HEDGE_BLOCK.ITEM,
				//Mangrove
				HavenMod.MANGROVE_MATERIAL.getLeaves().ITEM
		);
		//Fleece Armor
		itemColors.register((stack, tintIndex) -> {
			NbtCompound nbtCompound = stack.getSubNbt("display");
			return tintIndex > 0 ? -1 : nbtCompound != null && nbtCompound.contains("color", 99) ? nbtCompound.getInt("color") : 0xFFFFFF;
		}, HavenMod.FLEECE_MATERIAL.getHelmet(), HavenMod.FLEECE_MATERIAL.getChestplate(), HavenMod.FLEECE_MATERIAL.getLeggings(), HavenMod.FLEECE_MATERIAL.getBoots(), HavenMod.FLEECE_MATERIAL.getHorseArmor());
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