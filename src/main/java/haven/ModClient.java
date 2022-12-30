package haven;

import haven.rendering.block.*;
import haven.rendering.entities.ModBoatEntityRenderer;
import haven.containers.*;
import haven.entities.EntitySpawnPacket;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.*;
import haven.particles.*;
import haven.rendering.entities.*;
import haven.rendering.entities.cow.*;
import haven.rendering.entities.sheep.MossySheepEntityRenderer;
import haven.rendering.entities.sheep.RainbowSheepEntityRenderer;
import haven.rendering.entities.tnt.*;
import haven.rendering.models.*;

import static haven.ModBase.*;

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
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockRenderView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class ModClient implements ClientModInitializer {
	public static final EntityModelLayer FANCY_CHICKEN_ENTITY_MODEL_LAYER = new EntityModelLayer(ID("fancy_chicken"), "main");
	public static final EntityModelLayer MODEL_WARDEN_LAYER = new EntityModelLayer(ID("warden"), "main");
	public static final EntityModelLayer HEDGEHOG_ENTITY_MODEL_LAYER = new EntityModelLayer(ID("hedgehog"), "main");
	public static final EntityModelLayer RED_PANDA_ENTITY_MODEL_LAYER = new EntityModelLayer(ID("red_panda"), "main");
	public static final EntityModelLayer RACCOON_ENTITY_MODEL_LAYER = new EntityModelLayer(ID("raccoon"), "main");

	public static final EntityModelLayer BOAT_ENTITY_MODEL_LAYER = new EntityModelLayer(ID("boat"), "main");

	public static final Identifier PacketID = ID("spawn_packet");

	private static final List<Block> CutoutMipped = new ArrayList(List.<Block>of(
		//Hedges
			HEDGE_BLOCK.getBlock(),
		//Backport
			MANGROVE_ROOTS.getBlock(), MANGROVE_MATERIAL.getTrapdoor().getBlock()
	));

	private static final List<Block> Cutout = new ArrayList(List.<Block>of(
			BLUE_MUSHROOM_MATERIAL.getPotted().getPotted(),
		//Gilded Fungus
			GILDED_MATERIAL.getFungus().getPotted(), GILDED_MATERIAL.getTrapdoor().getBlock(),
			GILDED_ROOTS.getBlock(), GILDED_ROOTS.getPotted(),
		//Lanterns
		UNLIT_LANTERN, UNLIT_SOUL_LANTERN, ENDER_LANTERN.getBlock(), UNLIT_ENDER_LANTERN,
		//Campfires
			ENDER_CAMPFIRE.getBlock(),
		//Underwater Torch
			UNDERWATER_TORCH.getBlock(), UNDERWATER_TORCH.getWallBlock(),
		UNDERWATER_TORCH.UNLIT.UNLIT, UNDERWATER_TORCH.UNLIT.UNLIT_WALL,
		//Bamboo & Dried Bamboo Doors
			BAMBOO_MATERIAL.getDoor().getBlock(), BAMBOO_MATERIAL.getTrapdoor().getBlock(),
			DRIED_BAMBOO_MATERIAL.getDoor().getBlock(), DRIED_BAMBOO_MATERIAL.getTrapdoor().getBlock(),
		POTTED_DRIED_BAMBOO,
		//New Bamboo
			VANILLA_BAMBOO_MATERIAL.getDoor().getBlock(), VANILLA_BAMBOO_MATERIAL.getTrapdoor().getBlock(),
		//Charred Wood
			CHARRED_MATERIAL.getDoor().getBlock(), CHARRED_MATERIAL.getTrapdoor().getBlock(),
		//Mushroom Wood
			BROWN_MUSHROOM_MATERIAL.getDoor().getBlock(),
			RED_MUSHROOM_MATERIAL.getDoor().getBlock(),
			MUSHROOM_STEM_MATERIAL.getDoor().getBlock(),
			BLUE_MUSHROOM_MATERIAL.getDoor().getBlock(),
		//White Pumpkin
		WHITE_PUMPKIN.getStem(), WHITE_PUMPKIN.getAttachedStem(),
		//Strawberries
		STRAWBERRY_BUSH,
		//Coffee
		COFFEE_PLANT,
		//More Iron
			DARK_IRON_MATERIAL.getDoor().getBlock(), DARK_IRON_MATERIAL.getTrapdoor().getBlock(),
		//Echo
			ECHO_CLUSTER.getBlock(), LARGE_ECHO_BUD.getBlock(), MEDIUM_ECHO_BUD.getBlock(), SMALL_ECHO_BUD.getBlock(),
		//Sculk
			SCULK_SENSOR.getBlock(), SCULK_VEIN.getBlock(), SCULK_SHRIEKER.getBlock(),
		//Plushie
			BAT_PLUSHIE.getBlock(), ANGEL_BAT_PLUSHIE.getBlock(),
		//DECORATION-ONLY BLOCKS
			DECORATIVE_VINE.getBlock(), DECORATIVE_SUGAR_CANE.getBlock(), DECORATIVE_CACTUS.getBlock()
	));
	private static void setLayer(List<Block> target, FlowerContainer container) {
		setLayer(target, (PottedBlockContainer)container);
		target.add(container.SEEDS.getBlock());
	}
	private static void setLayer(List<Block> target, PottedBlockContainer container) {
		target.add(container.getBlock());
		target.add(container.getPotted());
	}
	private static void setLayer(List<Block> target, TorchContainer.Unlit unlit) {
		target.add(unlit.UNLIT);
		target.add(unlit.UNLIT_WALL);
	}
	private static void setLayer(List<Block> target, TorchContainer container) {
		target.add(container.getBlock());
		target.add(container.getWallBlock());
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
		target.add(container.getUnaffected().getBlock());
		target.add(container.getExposed().getBlock());
		target.add(container.getWeathered().getBlock());
		target.add(container.getOxidized().getBlock());
		target.add(container.getWaxedUnaffected().getBlock());
		target.add(container.getWaxedExposed().getBlock());
		target.add(container.getWaxedWeathered().getBlock());
		target.add(container.getWaxedOxidized().getBlock());
	}

	private static final Block[] Translucent = {
		SUBSTITUTE_ANCHOR_BLOCK,
		GLASS_SLAB.getBlock(), TINTED_GLASS_PANE.getBlock(), TINTED_GLASS_SLAB.getBlock()
	};

	static {
		setLayer(Cutout, UNLIT_TORCH);
		setLayer(Cutout, UNLIT_SOUL_TORCH);
		setLayer(Cutout, ENDER_TORCH);
		setLayer(Cutout, UNDERWATER_TORCH);
		for (FlowerContainer flower : Set.of(
			BUTTERCUP, PINK_DAISY, ROSE, BLUE_ROSE, MAGENTA_TULIP, MARIGOLD, INDIGO_ORCHID, MAGENTA_ORCHID,
			PURPLE_ORCHID, WHITE_ORCHID, YELLOW_ORCHID, PINK_ALLIUM, LAVENDER, HYDRANGEA, PAEONIA, ASTER,
			VANILLA_FLOWER
		)) setLayer(Cutout, flower);
		for (DyeColor color : COLORS) setLayer(Cutout, CARNATIONS.get(color));
		for (BlockContainer flower : Set.<BlockContainer>of(
			AMARANTH, BLUE_ROSE_BUSH, TALL_ALLIUM, TALL_PINK_ALLIUM, TALL_VANILLA
		)) Cutout.add(flower.getBlock());
		for (FlowerSeedContainer seed : Set.of(
			AMARANTH_SEEDS, ALLIUM_SEEDS, AZURE_BLUET_SEEDS, BLUE_ORCHID_SEEDS, CORNFLOWER_SEEDS, DANDELION_SEEDS,
			LILAC_SEEDS, LILY_OF_THE_VALLEY_SEEDS, ORANGE_TULIP_SEEDS, OXEYE_DAISY_SEEDS, PEONY_SEEDS, PINK_TULIP_SEEDS,
			POPPY_SEEDS, RED_TULIP_SEEDS, SUNFLOWER_SEEDS, WHITE_TULIP_SEEDS, WITHER_ROSE_SEEDS
		)) Cutout.add(seed.getBlock());
		for (BaseMaterial material : MATERIALS) {
			if (material instanceof TorchProvider torchProvider) setLayer(Cutout, torchProvider.getTorch());
			if (material instanceof OxidizableTorchProvider oxidizableTorch) setLayer(Cutout, oxidizableTorch.getOxidizableTorch());
			if (material instanceof SoulTorchProvider soulTorchProvider) setLayer(Cutout, soulTorchProvider.getSoulTorch());
			if (material instanceof OxidizableSoulTorchProvider oxidizableSoulTorch) setLayer(Cutout, oxidizableSoulTorch.getOxidizableSoulTorch());
			if (material instanceof EnderTorchProvider enderTorchProvider) setLayer(Cutout, enderTorchProvider.getEnderTorch());
			if (material instanceof OxidizableEnderTorchProvider oxidizableEnderTorch) setLayer(Cutout, oxidizableEnderTorch.getOxidizableEnderTorch());
			if (material instanceof LanternProvider lantern) {
				Cutout.add(lantern.getLantern().getBlock());
				Cutout.add(lantern.getUnlitLantern());
			}
			if (material instanceof OxidizableLanternProvider oxidizableLantern) setLayer(Cutout, oxidizableLantern.getOxidizableLantern());
			if (material instanceof EnderLanternProvider enderLantern) {
				Cutout.add(enderLantern.getEnderLantern().getBlock());
				Cutout.add(enderLantern.getUnlitEnderLantern());
			}
			if (material instanceof OxidizableEnderLanternProvider oxidizableEnderLantern) setLayer(Cutout, oxidizableEnderLantern.getOxidizableEnderLantern());
			if (material instanceof SoulLanternProvider soulLantern) {
				Cutout.add(soulLantern.getSoulLantern().getBlock());
				Cutout.add(soulLantern.getUnlitSoulLantern());
			}
			if (material instanceof OxidizableSoulLanternProvider oxidizableSoulLantern) setLayer(Cutout, oxidizableSoulLantern.getOxidizableSoulLantern());
			if (material instanceof CampfireProvider campfire) Cutout.add(campfire.getCampfire().getBlock());
			if (material instanceof SoulCampfireProvider soulCampfire) Cutout.add(soulCampfire.getSoulCampfire().getBlock());
			if (material instanceof EnderCampfireProvider enderCampfire) Cutout.add(enderCampfire.getEnderCampfire().getBlock());
			if (material instanceof WoodcutterProvider woodcutterProvider) Cutout.add(woodcutterProvider.getWoodcutter().getBlock());
			if (material instanceof ChainProvider chain) Cutout.add(chain.getChain().getBlock());
			if (material instanceof OxidizableChainProvider oxidizableChain) setLayer(CutoutMipped, oxidizableChain.getOxidizableChain());
			if (material instanceof BarsProvider bars) CutoutMipped.add(bars.getBars().getBlock());
			if (material instanceof OxidizableBarsProvider oxidizableBars) setLayer(CutoutMipped, oxidizableBars.getOxidizableBars());
			if (material instanceof LeavesProvider leaves) CutoutMipped.add(leaves.getLeaves().getBlock());
			if (material instanceof SaplingProvider sapling) setLayer(Cutout, sapling.getSapling());
			if (material instanceof PropaguleProvider propagule) setLayer(Cutout, propagule.getPropagule());
		}
	}

	@Override
	public void onInitializeClient() {
		BlockEntityRendererRegistry.register(ANCHOR_BLOCK_ENTITY, AnchorBlockEntityRenderer::new);

		BlockEntityRendererRegistry.register(BROKEN_ANCHOR_BLOCK_ENTITY, BrokenAnchorBlockEntityRenderer::new);

		BlockEntityRendererRegistry.register(SUBSTITUTE_ANCHOR_BLOCK_ENTITY, SubstituteAnchorBlockEntityRenderer::new);

		BlockRenderLayerMap inst = BlockRenderLayerMap.INSTANCE;
		RenderLayer cutoutMipped = RenderLayer.getCutoutMipped();
		for (Block block : CutoutMipped) inst.putBlock(block, cutoutMipped);
		RenderLayer cutout = RenderLayer.getCutout();
		for(Block block : Cutout) inst.putBlock(block, cutout);
		RenderLayer translucent = RenderLayer.getTranslucent();
		for(Block block : Translucent) inst.putBlock(block, translucent);
		for (DyeColor color : COLORS) inst.putBlock(STAINED_GLASS_SLABS.get(color).getBlock(), translucent);
		ParticleFactoryRegistry PARTICLES = ParticleFactoryRegistry.getInstance();
		//Bleeding Obsidian
		PARTICLES.register(LANDING_OBSIDIAN_BLOOD, HavenBlockLeakParticle.LandingObsidianBloodFactory::new);
		PARTICLES.register(FALLING_OBSIDIAN_BLOOD, HavenBlockLeakParticle.FallingObsidianBloodFactory::new);
		PARTICLES.register(DRIPPING_OBSIDIAN_BLOOD, HavenBlockLeakParticle.DrippingObsidianBloodFactory::new);
		//Blood
		ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
			registry.register(ID("particle/blood_bubble"));
			registry.register(ID("particle/blood_splash_0"));
			registry.register(ID("particle/blood_splash_1"));
			registry.register(ID("particle/blood_splash_2"));
			registry.register(ID("particle/blood_splash_3"));
		}));
		PARTICLES.register(BLOOD_BUBBLE, WaterBubbleParticle.Factory::new);
		PARTICLES.register(BLOOD_SPLASH, WaterSplashParticle.SplashFactory::new);
		PARTICLES.register(DRIPPING_BLOOD, HavenBlockLeakParticle.DrippingBloodFactory::new);
		PARTICLES.register(FALLING_BLOOD, HavenBlockLeakParticle.FallingBloodFactory::new);
		PARTICLES.register(FALLING_DRIPSTONE_BLOOD, HavenBlockLeakParticle.FallingDripstoneBloodFactory::new);
		setupFluidRendering(STILL_BLOOD_FLUID, FLOWING_BLOOD_FLUID, ID("blood"), 0xFF0000);
		BlockRenderLayerMap.INSTANCE.putFluids(translucent, STILL_BLOOD_FLUID, FLOWING_BLOOD_FLUID);
		//Mud
		ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
			registry.register(ID("particle/mud_bubble"));
			registry.register(ID("particle/mud_splash_0"));
			registry.register(ID("particle/mud_splash_1"));
			registry.register(ID("particle/mud_splash_2"));
			registry.register(ID("particle/mud_splash_3"));
		}));
		PARTICLES.register(MUD_BUBBLE, WaterBubbleParticle.Factory::new);
		PARTICLES.register(MUD_SPLASH, WaterSplashParticle.SplashFactory::new);
		PARTICLES.register(DRIPPING_MUD, HavenBlockLeakParticle.DrippingMudFactory::new);
		PARTICLES.register(FALLING_MUD, HavenBlockLeakParticle.FallingMudFactory::new);
		PARTICLES.register(FALLING_DRIPSTONE_MUD, HavenBlockLeakParticle.FallingDripstoneMudFactory::new);
		setupFluidRendering(STILL_MUD_FLUID, FLOWING_MUD_FLUID, ID("mud"), 0x472804);
		BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getSolid(), STILL_MUD_FLUID, FLOWING_MUD_FLUID);
		//Torches
		ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
			registry.register(ID("particle/glow_0"));
			registry.register(ID("particle/glow_1"));
			registry.register(ID("particle/glow_2"));
			registry.register(ID("particle/glow_3"));
			registry.register(ID("particle/glow_4"));
			registry.register(ID("particle/glow_5"));
			registry.register(ID("particle/glow_6"));
			registry.register(ID("particle/glow_7"));
			registry.register(ID("particle/copper_flame"));
			registry.register(ID("particle/gold_flame"));
			registry.register(ID("particle/iron_flame"));
			registry.register(ID("particle/netherite_flame"));
			registry.register(ID("particle/ender_fire_flame"));
		}));
		PARTICLES.register(UNDERWATER_TORCH_GLOW, FlameParticle.Factory::new);
		PARTICLES.register(COPPER_FLAME_PARTICLE, FlameParticle.Factory::new);
		PARTICLES.register(GOLD_FLAME_PARTICLE, FlameParticle.Factory::new);
		PARTICLES.register(IRON_FLAME_PARTICLE, FlameParticle.Factory::new);
		PARTICLES.register(NETHERITE_FLAME_PARTICLE, FlameParticle.Factory::new);
		PARTICLES.register(ENDER_FIRE_FLAME_PARTICLE, FlameParticle.Factory::new);
		PARTICLES.register(SMALL_SOUL_FLAME_PARTICLE, FlameParticle.SmallFactory::new);
		PARTICLES.register(SMALL_ENDER_FLAME_PARTICLE, FlameParticle.SmallFactory::new);
		//Sculk
		PARTICLES.register(SCULK_SOUL_PARTICLE, HavenSoulParticle.SculkSoulFactory::new);
		PARTICLES.register(SCULK_CHARGE_PARTICLE, SculkChargeParticle.Factory::new);
		PARTICLES.register(SCULK_CHARGE_POP_PARTICLE, SculkChargePopParticle.Factory::new);
		PARTICLES.register(SHRIEK_PARTICLE, ShriekParticle.Factory::new);
		PARTICLES.register(SONIC_BOOM_PARTICLE, SonicBoomParticle.Factory::new);
		EntityRendererRegistry.register(WARDEN_ENTITY, WardenEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(MODEL_WARDEN_LAYER, WardenEntityModel::getTexturedModelData);
		//Deepest Sleep
		PARTICLES.register(VECTOR_ARROW_PARTICLE, VectorParticle.ArrowFactory::new);
		EntityRendererRegistry.register(CHUNKEATER_TNT_ENTITY, ChunkeaterTntEntityRenderer::new);
		//Miasma
		EntityRendererRegistry.register(CATALYZING_TNT_ENTITY, CatalyzingTntEntityRenderer::new);
		//Alcatraz
		EntityRendererRegistry.register(SHARP_TNT_ENTITY, SharpTntEntityRenderer::new);
		//Hezekiah
		EntityRendererRegistry.register(DEVOURING_TNT_ENTITY, DevouringTntEntityRenderer::new);
		//Digger Krozhul
		EntityRendererRegistry.register(VIOLENT_TNT_ENTITY, ViolentTntEntityRenderer::new);
		//Soleil
		EntityRendererRegistry.register(SOFT_TNT_ENTITY, SoftTntEntityRenderer::new);
		//Throwable Tomatoes
		EntityRendererRegistry.register(THROWABLE_TOMATO_ENTITY, (context) -> new FlyingItemEntityRenderer(context));
		ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
			registry.register(ID("particle/thrown_tomato"));
		}));
		ParticleFactoryRegistry.getInstance().register(TOMATO_PARTICLE, TomatoParticle.Factory::new);
		//Angel Bat
		EntityRendererRegistry.register(ANGEL_BAT_ENTITY, BatEntityRenderer::new);
		//Melon Golem
		EntityRendererRegistry.register(MELON_SEED_PROJECTILE_ENTITY, (context) -> new FlyingItemEntityRenderer(context));
		EntityRendererRegistry.register(MELON_GOLEM_ENTITY, MelonGolemEntityRenderer::new);
		//White Pumpkin / White Pumpkin Snow Golem
		EntityRendererRegistry.register(WHITE_SNOW_GOLEM_ENTITY, WhiteSnowGolemEntityRenderer::new);
		//Sheep Variants
		EntityRendererRegistry.register(RAINBOW_SHEEP_ENTITY, RainbowSheepEntityRenderer::new);
		EntityRendererRegistry.register(MOSSY_SHEEP_ENTITY, MossySheepEntityRenderer::new);
		//Custom Beds
		ClientSpriteRegistryCallback.event(TexturedRenderLayers.BEDS_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
			for (BedContainer bed : BEDS) registry.register(bed.GetTexture());
		}));
		//Chicken Variants
		EntityModelLayerRegistry.registerModelLayer(FANCY_CHICKEN_ENTITY_MODEL_LAYER, FancyChickenModel::getTexturedModelData);
		EntityRendererRegistry.register(FANCY_CHICKEN_ENTITY, FancyChickenEntityRenderer::new);
		//Milk Cows
		EntityRendererRegistry.register(COWCOA_ENTITY, CowcoaEntityRenderer::new);
		EntityRendererRegistry.register(COWFEE_ENTITY, CowfeeEntityRenderer::new);
		EntityRendererRegistry.register(STRAWBOVINE_ENTITY, StrawbovineEntityRenderer::new);
		EntityRendererRegistry.register(MOONILLA_ENTITY, MoonillaEntityRenderer::new);
		//Flower Mooshrooms
		EntityRendererRegistry.register(MOOBLOOM_ENTITY, MoobloomEntityRenderer::new);
		EntityRendererRegistry.register(MOOLIP_ENTITY, MoolipEntityRenderer::new);
		//Mooblossoms
		EntityRendererRegistry.register(MOOBLOSSOM_ENTITY, MooblossomEntityRenderer::new);
		EntityRendererRegistry.register(ORANGE_MOOBLOSSOM_ENTITY, OrangeMooblossomEntityRenderer::new);
		EntityRendererRegistry.register(PINK_MOOBLOSSOM_ENTITY, PinkMooblossomEntityRenderer::new);
		EntityRendererRegistry.register(RED_MOOBLOSSOM_ENTITY, RedMooblossomEntityRenderer::new);
		EntityRendererRegistry.register(WHITE_MOOBLOSSOM_ENTITY, WhiteMooblossomEntityRenderer::new);
		//Blue Mooshroom
		EntityRendererRegistry.register(BLUE_MOOSHROOM_ENTITY, BlueMooshroomEntityRenderer::new);
		//Nether Mooshrooms
		EntityRendererRegistry.register(GILDED_MOOSHROOM_ENTITY, GildedMooshroomEntityRenderer::new);
		EntityRendererRegistry.register(CRIMSON_MOOSHROOM_ENTITY, CrimsonMooshroomEntityRenderer::new);
		EntityRendererRegistry.register(WARPED_MOOSHROOM_ENTITY, WarpedMooshroomEntityRenderer::new);
		//Bottled Confetti
		EntityRendererRegistry.register(BOTTLED_CONFETTI_ENTITY, FlyingItemEntityRenderer::new);
		EntityRendererRegistry.register(DROPPED_CONFETTI_ENTITY, EmptyEntityRenderer::new);
		EntityRendererRegistry.register(CONFETTI_CLOUD_ENTITY, EmptyEntityRenderer::new);
		//Dragon Breath
		EntityRendererRegistry.register(DROPPED_DRAGON_BREATH_ENTITY, EmptyEntityRenderer::new);
		EntityRendererRegistry.register(DRAGON_BREATH_CLOUD_ENTITY, EmptyEntityRenderer::new);
		//Bottled Lightning
		EntityRendererRegistry.register(BOTTLED_LIGHTNING_ENTITY, FlyingItemEntityRenderer::new);
		//Custom Boats
		EntityModelLayerRegistry.registerModelLayer(BOAT_ENTITY_MODEL_LAYER, BoatEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(BOAT_ENTITY, ModBoatEntityRenderer::new);
		//Grappling Rod
		ModelPredicateProviderRegistrySpecificAccessor.callRegister(GRAPPLING_ROD, new Identifier("cast"), ModClient::castGrapplingRod);

		//Raccoons
		EntityRendererRegistry.register(RACCOON_ENTITY, RaccoonEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(RACCOON_ENTITY_MODEL_LAYER, RaccoonEntityModel::getTexturedModelData);
		//Red Pandas
		EntityRendererRegistry.register(RED_PANDA_ENTITY, RedPandaEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(RED_PANDA_ENTITY_MODEL_LAYER, RedPandaEntityModel::getTexturedModelData);
		//Hedgehogs
		EntityRendererRegistry.register(HEDGEHOG_ENTITY, HedgehogEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(HEDGEHOG_ENTITY_MODEL_LAYER, HedgehogEntityModel::getTexturedModelData);

		//Entity Packets (not sure if I still need this but I might?)
		receiveEntityPacket();
	}

	public static void RegisterBlockColors(BlockColors blockColors) {
		//Gourd Stems
		blockColors.registerColorProvider((state, world, pos, tintIndex) -> 14731036,
				WHITE_PUMPKIN.getAttachedStem()
		);
		blockColors.registerColorProvider((state, world, pos, tintIndex) -> {
				int i = state.get(StemBlock.AGE);
				return (i * 32) << 16 | (255 - i * 8) << 8 | (i * 4);
			},
				WHITE_PUMPKIN.getStem()
		);
		//Generic Foliage
		blockColors.registerColorProvider((state, world, pos, tintIndex) ->
			world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor(),
				//Hedges
				HEDGE_BLOCK.getBlock(),
				//Mangrove Leaves
				MANGROVE_MATERIAL.getLeaves().getBlock()
		);
		//DECORATION-ONLY BLOCKS
		blockColors.registerColorProvider((state, world, pos, tintIndex) -> {
			if (world == null || pos == null) return FoliageColors.getDefaultColor();
			return BiomeColors.getFoliageColor(world, pos);
		}, DECORATIVE_VINE.getBlock());
	}
	public static void RegisterItemColors(ItemColors itemColors) {
		//Generic Foliage
		itemColors.register((itemStack, i) -> 9619016,
				//Hedges
				HEDGE_BLOCK.getItem(),
				//Mangrove
				MANGROVE_MATERIAL.getLeaves().getItem()
		);
		//Fleece Armor
		itemColors.register((stack, tintIndex) -> {
			NbtCompound nbtCompound = stack.getSubNbt("display");
			return tintIndex > 0 ? -1 : nbtCompound != null && nbtCompound.contains("color", 99) ? nbtCompound.getInt("color") : 0xFFFFFF;
		}, FLEECE_MATERIAL.getHelmet(), FLEECE_MATERIAL.getChestplate(), FLEECE_MATERIAL.getLeggings(), FLEECE_MATERIAL.getBoots(), FLEECE_MATERIAL.getHorseArmor());
		//DECORATION-ONLY BLOCKS
		itemColors.register((stack, tintIndex) -> FoliageColors.getDefaultColor(),
				DECORATIVE_VINE.getItem());
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
		final Identifier stillSpriteId = ID("block/" + textureFluidId.getPath() + "_still");
		final Identifier flowingSpriteId = ID("block/" + textureFluidId.getPath() + "_flow");

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