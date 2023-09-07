package fun.mousewich;

import fun.mousewich.client.render.block.model.PiglinHeadEntityModel;
import fun.mousewich.client.render.block.renderer.BrushableBlockEntityRenderer;
import fun.mousewich.client.render.block.renderer.PiglinHeadEntityRenderer;
import fun.mousewich.client.render.block.renderer.RagdollBlockEntityRenderer;
import fun.mousewich.client.render.entity.ModEntityModelLayers;
import fun.mousewich.client.render.entity.model.*;
import fun.mousewich.client.render.entity.renderer.*;
import fun.mousewich.client.render.entity.renderer.ModBoatEntityRenderer;
import fun.mousewich.client.render.entity.renderer.ModTntEntityRenderer;
import fun.mousewich.client.render.entity.renderer.chicken.FancyChickenEntityRenderer;
import fun.mousewich.client.render.entity.renderer.chicken.SlimeChickenEntityRenderer;
import fun.mousewich.client.render.entity.renderer.cow.*;
import fun.mousewich.client.render.entity.renderer.sheep.MossySheepEntityRenderer;
import fun.mousewich.client.render.entity.renderer.sheep.RainbowSheepEntityRenderer;
import fun.mousewich.client.render.entity.renderer.skeleton.MossySkeletonEntityRenderer;
import fun.mousewich.client.render.entity.renderer.skeleton.SlimySkeletonEntityRenderer;
import fun.mousewich.client.render.entity.renderer.skeleton.SunkenSkeletonEntityRenderer;
import fun.mousewich.client.render.entity.renderer.slime.PinkSlimeEntityRenderer;
import fun.mousewich.client.render.entity.renderer.slime.TropicalSlimeEntityRenderer;
import fun.mousewich.client.render.entity.renderer.spider.BoneSpiderEntityRenderer;
import fun.mousewich.client.render.entity.renderer.spider.JumpingSpiderEntityRenderer;
import fun.mousewich.client.render.entity.renderer.spider.SlimeSpiderEntityRenderer;
import fun.mousewich.client.render.entity.renderer.zombie.FrozenZombieEntityRenderer;
import fun.mousewich.client.render.entity.renderer.zombie.JungleZombieEntityRenderer;
import fun.mousewich.client.render.entity.renderer.zombie.SlimeZombieEntityRenderer;
import fun.mousewich.client.render.gui.Generic1x1ContainerScreen;
import fun.mousewich.client.render.gui.WoodcutterScreen;
import fun.mousewich.container.*;
import fun.mousewich.haven.HavenModClient;
import fun.mousewich.mixins.client.render.item.ModelPredicateProviderRegistryInvoker;
import fun.mousewich.particle.*;

import static fun.mousewich.ModBase.*;
import static fun.mousewich.registry.ModBambooRegistry.*;
import static fun.mousewich.registry.ModCopperRegistry.*;

import fun.mousewich.trim.TrimmingScreen;
import fun.mousewich.util.banners.ModBannerPattern;
import io.github.apace100.apoli.ApoliClient;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.particle.v1.FabricSpriteProvider;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.*;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.resource.*;
import net.fabricmc.fabric.mixin.object.builder.ModelPredicateProviderRegistrySpecificAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.StemBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.client.item.UnclampedModelPredicateProvider;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.SpiderEntityModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.*;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.resource.*;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.SignType;
import net.minecraft.util.math.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockRenderView;
import org.lwjgl.glfw.GLFW;

import java.util.*;
import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class ModClient implements ClientModInitializer {
	private static void InitializeCutoutMipped() {
		RenderLayer layer = RenderLayer.getCutoutMipped();
		//Hedges
		setLayer(layer, HEDGE_BLOCK);
		//Minecraft Dungeons
		setLayer(layer, LIGHT_GREEN_ACACIA_LEAVES, RED_ACACIA_LEAVES, YELLOW_ACACIA_LEAVES);
		setLayer(layer, LIGHT_GREEN_BIRCH_LEAVES, RED_BIRCH_LEAVES, YELLOW_BIRCH_LEAVES, WHITE_BIRCH_LEAVES);
		setLayer(layer, BLUE_GREEN_OAK_LEAVES, LIGHT_GREEN_OAK_LEAVES, RED_OAK_LEAVES, YELLOW_OAK_LEAVES, WHITE_OAK_LEAVES);
		setLayer(layer, WHITE_SPRUCE_LEAVES, SWEET_BERRY_LEAVES);
		//Backport
		setLayer(layer, MANGROVE_LEAVES, MANGROVE_ROOTS, MANGROVE_TRAPDOOR, CHERRY_LEAVES, CHERRY_TRAPDOOR, PINK_PETALS);
		//Leaves
		setLayer(layer, CASSIA_LEAVES, FLOWERING_CASSIA_LEAVES, PINK_DOGWOOD_LEAVES, PALE_DOGWOOD_LEAVES, WHITE_DOGWOOD_LEAVES, GRAPE_LEAVES);
		//Iron
		setLayer(layer, IRON_CHAIN);
		//Dark Iron
		setLayer(layer, DARK_IRON_BARS);
		//Gold
		setLayer(layer, GOLD_BARS, GOLD_CHAIN);
		//Copper
		setLayer(layer, COPPER_CHAIN, EXPOSED_COPPER_CHAIN, WEATHERED_COPPER_CHAIN, OXIDIZED_COPPER_CHAIN);
		setLayer(layer, WAXED_COPPER_CHAIN, WAXED_EXPOSED_COPPER_CHAIN, WAXED_WEATHERED_COPPER_CHAIN, WAXED_OXIDIZED_COPPER_CHAIN);
		setLayer(layer, COPPER_BARS, EXPOSED_COPPER_BARS, WEATHERED_COPPER_BARS, OXIDIZED_COPPER_BARS);
		setLayer(layer, WAXED_COPPER_BARS, WAXED_EXPOSED_COPPER_BARS, WAXED_WEATHERED_COPPER_BARS, WAXED_OXIDIZED_COPPER_BARS);
		//Netherite
		setLayer(layer, NETHERITE_BARS, NETHERITE_CHAIN);
	}
	private static void InitializeCutout() {
		RenderLayer layer = RenderLayer.getCutout();
		//############################
		setLayer(layer, SCULK_SENSOR);
		//############################
		//Lanterns
		setLayer(layer, UNLIT_LANTERN, UNLIT_SOUL_LANTERN, ENDER_LANTERN.asBlock());
		//Unlit Torches
		setLayer(layer, UNLIT_TORCH, UNLIT_SOUL_TORCH);
		//Underwater Torch
		setLayer(layer, UNDERWATER_TORCH);
		//Prismarine
		setLayer(layer, PRISMARINE_TORCH, PRISMARINE_SOUL_TORCH, PRISMARINE_ENDER_TORCH, UNDERWATER_PRISMARINE_TORCH);
		//Blaze
		setLayer(layer, BLAZE_TORCH, BLAZE_SOUL_TORCH, BLAZE_ENDER_TORCH, UNDERWATER_BLAZE_TORCH);
		//Ender Fire
		setLayer(layer, ENDER_TORCH);
		setLayer(layer, ENDER_CAMPFIRE);
		//Wood Torches
		setLayer(layer, ACACIA_TORCH, ACACIA_SOUL_TORCH, ACACIA_ENDER_TORCH, UNDERWATER_ACACIA_TORCH);
		setLayer(layer, BIRCH_TORCH, BIRCH_SOUL_TORCH, BIRCH_ENDER_TORCH, UNDERWATER_BIRCH_TORCH);
		setLayer(layer, DARK_OAK_TORCH, DARK_OAK_SOUL_TORCH, DARK_OAK_ENDER_TORCH, UNDERWATER_DARK_OAK_TORCH);
		setLayer(layer, ACACIA_TORCH, JUNGLE_SOUL_TORCH, JUNGLE_ENDER_TORCH, UNDERWATER_JUNGLE_TORCH);
		setLayer(layer, SPRUCE_TORCH, SPRUCE_SOUL_TORCH, SPRUCE_ENDER_TORCH, UNDERWATER_SPRUCE_TORCH);
		setLayer(layer, CRIMSON_TORCH, CRIMSON_SOUL_TORCH, CRIMSON_ENDER_TORCH, UNDERWATER_CRIMSON_TORCH);
		setLayer(layer, WARPED_TORCH, WARPED_SOUL_TORCH, WARPED_ENDER_TORCH, UNDERWATER_WARPED_TORCH);
		setLayer(layer, GILDED_TORCH, GILDED_SOUL_TORCH, GILDED_ENDER_TORCH, UNDERWATER_GILDED_TORCH);
		setLayer(layer, BAMBOO_TORCH, BAMBOO_SOUL_TORCH, BAMBOO_ENDER_TORCH, UNDERWATER_BAMBOO_TORCH);
		setLayer(layer, DRIED_BAMBOO_TORCH, DRIED_BAMBOO_SOUL_TORCH, DRIED_BAMBOO_ENDER_TORCH, UNDERWATER_DRIED_BAMBOO_TORCH);
		setLayer(layer, CHERRY_TORCH, CHERRY_SOUL_TORCH, CHERRY_ENDER_TORCH, UNDERWATER_CHERRY_TORCH);
		setLayer(layer, MANGROVE_TORCH, MANGROVE_SOUL_TORCH, MANGROVE_ENDER_TORCH, UNDERWATER_MANGROVE_TORCH);
		setLayer(layer, CASSIA_TORCH, CASSIA_SOUL_TORCH, CASSIA_ENDER_TORCH, UNDERWATER_CASSIA_TORCH);
		setLayer(layer, CHARRED_TORCH, CHARRED_SOUL_TORCH, CHARRED_ENDER_TORCH, UNDERWATER_CHARRED_TORCH);
		setLayer(layer, DOGWOOD_TORCH, DOGWOOD_SOUL_TORCH, DOGWOOD_ENDER_TORCH, UNDERWATER_DOGWOOD_TORCH);
		setLayer(layer, SUGAR_CANE_TORCH, SUGAR_CANE_SOUL_TORCH, SUGAR_CANE_ENDER_TORCH, UNDERWATER_SUGAR_CANE_TORCH);
		setLayer(layer, BLUE_MUSHROOM_TORCH, BLUE_MUSHROOM_SOUL_TORCH, BLUE_MUSHROOM_ENDER_TORCH, UNDERWATER_BLUE_MUSHROOM_TORCH);
		setLayer(layer, BROWN_MUSHROOM_TORCH, BROWN_MUSHROOM_SOUL_TORCH, BROWN_MUSHROOM_ENDER_TORCH, UNDERWATER_BROWN_MUSHROOM_TORCH);
		setLayer(layer, RED_MUSHROOM_TORCH, RED_MUSHROOM_SOUL_TORCH, RED_MUSHROOM_ENDER_TORCH, UNDERWATER_RED_MUSHROOM_TORCH);
		setLayer(layer, MUSHROOM_STEM_TORCH, MUSHROOM_STEM_SOUL_TORCH, MUSHROOM_STEM_ENDER_TORCH, UNDERWATER_MUSHROOM_STEM_TORCH);
		//Campfires
		setLayer(layer, ACACIA_CAMPFIRE, ACACIA_SOUL_CAMPFIRE, ACACIA_ENDER_CAMPFIRE);
		setLayer(layer, BIRCH_CAMPFIRE, BIRCH_SOUL_CAMPFIRE, BIRCH_ENDER_CAMPFIRE);
		setLayer(layer, DARK_OAK_CAMPFIRE, DARK_OAK_SOUL_CAMPFIRE, DARK_OAK_ENDER_CAMPFIRE);
		setLayer(layer, ACACIA_CAMPFIRE, JUNGLE_SOUL_CAMPFIRE, JUNGLE_ENDER_CAMPFIRE);
		setLayer(layer, SPRUCE_CAMPFIRE, SPRUCE_SOUL_CAMPFIRE, SPRUCE_ENDER_CAMPFIRE);
		setLayer(layer, BAMBOO_CAMPFIRE, BAMBOO_SOUL_CAMPFIRE, BAMBOO_ENDER_CAMPFIRE);
		setLayer(layer, DRIED_BAMBOO_CAMPFIRE, DRIED_BAMBOO_SOUL_CAMPFIRE, DRIED_BAMBOO_ENDER_CAMPFIRE);
		setLayer(layer, CHERRY_CAMPFIRE, CHERRY_SOUL_CAMPFIRE, CHERRY_ENDER_CAMPFIRE);
		setLayer(layer, MANGROVE_CAMPFIRE, MANGROVE_SOUL_CAMPFIRE, MANGROVE_ENDER_CAMPFIRE);
		setLayer(layer, CASSIA_CAMPFIRE, CASSIA_SOUL_CAMPFIRE, CASSIA_ENDER_CAMPFIRE);
		setLayer(layer, CHARRED_CAMPFIRE, CHARRED_SOUL_CAMPFIRE, CHARRED_ENDER_CAMPFIRE);
		setLayer(layer, DOGWOOD_CAMPFIRE, DOGWOOD_SOUL_CAMPFIRE, DOGWOOD_ENDER_CAMPFIRE);
		setLayer(layer, HAY_TORCH, HAY_SOUL_TORCH, HAY_ENDER_TORCH, UNDERWATER_HAY_TORCH);
		setLayer(layer, SUGAR_CANE_CAMPFIRE, SUGAR_CANE_SOUL_CAMPFIRE, SUGAR_CANE_ENDER_CAMPFIRE);
		setLayer(layer, CRIMSON_CAMPFIRE, CRIMSON_SOUL_CAMPFIRE, CRIMSON_ENDER_CAMPFIRE);
		setLayer(layer, WARPED_CAMPFIRE, WARPED_SOUL_CAMPFIRE, WARPED_ENDER_CAMPFIRE);
		setLayer(layer, GILDED_CAMPFIRE, GILDED_SOUL_CAMPFIRE, GILDED_ENDER_CAMPFIRE);
		setLayer(layer, BLUE_MUSHROOM_CAMPFIRE, BLUE_MUSHROOM_SOUL_CAMPFIRE, BLUE_MUSHROOM_ENDER_CAMPFIRE);
		setLayer(layer, BROWN_MUSHROOM_CAMPFIRE, BROWN_MUSHROOM_SOUL_CAMPFIRE, BROWN_MUSHROOM_ENDER_CAMPFIRE);
		setLayer(layer, RED_MUSHROOM_CAMPFIRE, RED_MUSHROOM_SOUL_CAMPFIRE, RED_MUSHROOM_ENDER_CAMPFIRE);
		setLayer(layer, MUSHROOM_STEM_CAMPFIRE, MUSHROOM_STEM_SOUL_CAMPFIRE, MUSHROOM_STEM_ENDER_CAMPFIRE);
		//Gourds
		setLayer(layer, WHITE_PUMPKIN_STEM, ATTACHED_WHITE_PUMPKIN_STEM);
		//Iron
		setLayer(layer, IRON_TORCH, IRON_SOUL_TORCH, IRON_ENDER_TORCH, UNDERWATER_IRON_TORCH);
		setLayer(layer, IRON_LANTERN, IRON_SOUL_LANTERN, IRON_ENDER_LANTERN);
		//Dark Iron
		setLayer(layer, DARK_IRON_TORCH, DARK_IRON_SOUL_TORCH, DARK_IRON_ENDER_TORCH, UNDERWATER_DARK_IRON_TORCH);
		//Gold
		setLayer(layer, GOLD_TORCH, GOLD_SOUL_TORCH, GOLD_ENDER_TORCH, UNDERWATER_GOLD_TORCH);
		setLayer(layer, GOLD_LANTERN, GOLD_SOUL_LANTERN, GOLD_ENDER_LANTERN);
		//Copper
		setLayer(layer, COPPER_TORCH, EXPOSED_COPPER_TORCH, WEATHERED_COPPER_TORCH, OXIDIZED_COPPER_TORCH);
		setLayer(layer, WAXED_COPPER_TORCH, WAXED_EXPOSED_COPPER_TORCH, WAXED_WEATHERED_COPPER_TORCH, WAXED_OXIDIZED_COPPER_TORCH);
		setLayer(layer, COPPER_SOUL_TORCH, EXPOSED_COPPER_SOUL_TORCH, WEATHERED_COPPER_SOUL_TORCH, OXIDIZED_COPPER_SOUL_TORCH);
		setLayer(layer, WAXED_COPPER_SOUL_TORCH, WAXED_EXPOSED_COPPER_SOUL_TORCH, WAXED_WEATHERED_COPPER_SOUL_TORCH, WAXED_OXIDIZED_COPPER_SOUL_TORCH);
		setLayer(layer, COPPER_ENDER_TORCH, EXPOSED_COPPER_ENDER_TORCH, WEATHERED_COPPER_ENDER_TORCH, OXIDIZED_COPPER_ENDER_TORCH);
		setLayer(layer, WAXED_COPPER_ENDER_TORCH, WAXED_EXPOSED_COPPER_ENDER_TORCH, WAXED_WEATHERED_COPPER_ENDER_TORCH, WAXED_OXIDIZED_COPPER_ENDER_TORCH);
		setLayer(layer, UNDERWATER_COPPER_TORCH, EXPOSED_UNDERWATER_COPPER_TORCH, WEATHERED_UNDERWATER_COPPER_TORCH, OXIDIZED_UNDERWATER_COPPER_TORCH);
		setLayer(layer, WAXED_UNDERWATER_COPPER_TORCH, WAXED_EXPOSED_UNDERWATER_COPPER_TORCH, WAXED_WEATHERED_UNDERWATER_COPPER_TORCH, WAXED_OXIDIZED_UNDERWATER_COPPER_TORCH);
		setLayer(layer, COPPER_LANTERN, EXPOSED_COPPER_LANTERN, WEATHERED_COPPER_LANTERN, OXIDIZED_COPPER_LANTERN);
		setLayer(layer, WAXED_COPPER_LANTERN, WAXED_EXPOSED_COPPER_LANTERN, WAXED_WEATHERED_COPPER_LANTERN, WAXED_OXIDIZED_COPPER_LANTERN);
		setLayer(layer, COPPER_SOUL_LANTERN, EXPOSED_COPPER_SOUL_LANTERN, WEATHERED_COPPER_SOUL_LANTERN, OXIDIZED_COPPER_SOUL_LANTERN);
		setLayer(layer, WAXED_COPPER_SOUL_LANTERN, WAXED_EXPOSED_COPPER_SOUL_LANTERN, WAXED_WEATHERED_COPPER_SOUL_LANTERN, WAXED_OXIDIZED_COPPER_SOUL_LANTERN);
		setLayer(layer, COPPER_ENDER_LANTERN, EXPOSED_COPPER_ENDER_LANTERN, WEATHERED_COPPER_ENDER_LANTERN, OXIDIZED_COPPER_ENDER_LANTERN);
		setLayer(layer, WAXED_COPPER_ENDER_LANTERN, WAXED_EXPOSED_COPPER_ENDER_LANTERN, WAXED_WEATHERED_COPPER_ENDER_LANTERN, WAXED_OXIDIZED_COPPER_ENDER_LANTERN);
		//Netherite
		setLayer(layer, NETHERITE_TORCH, NETHERITE_SOUL_TORCH, NETHERITE_ENDER_TORCH, UNDERWATER_NETHERITE_TORCH);
		setLayer(layer, NETHERITE_LANTERN, NETHERITE_SOUL_LANTERN, NETHERITE_ENDER_LANTERN);
		//Extended Bone
		setLayer(layer, BONE_TORCH, BONE_SOUL_TORCH, BONE_ENDER_TORCH, UNDERWATER_BONE_TORCH);
		//Cherry
		setLayer(layer, PINK_PETALS, CHERRY_DOOR);
		//Mushroom Wood
		setLayer(layer, BLUE_MUSHROOM_DOOR, BROWN_MUSHROOM_DOOR, RED_MUSHROOM_DOOR, MUSHROOM_STEM_DOOR);
		//Vines
		setLayer(layer, GRAPE_VINES, GRAPE_VINES_PLANT);
		//Bushes
		setLayer(layer, STRAWBERRY_BUSH);
		setLayer(layer, COFFEE_PLANT);
		//Saplings
		setLayer(layer, MANGROVE_PROPAGULE, CHERRY_SAPLING, CASSIA_SAPLING, DOGWOOD_SAPLING, GRAPE_SAPLING);
		//Metal Doors/Trapdoors
		setLayer(layer, DARK_IRON_DOOR, DARK_IRON_TRAPDOOR);
		setLayer(layer, GOLD_DOOR, GOLD_TRAPDOOR, NETHERITE_DOOR, NETHERITE_TRAPDOOR);
		setLayer(layer, COPPER_TRAPDOOR, EXPOSED_COPPER_TRAPDOOR, WEATHERED_COPPER_TRAPDOOR, OXIDIZED_COPPER_TRAPDOOR);
		setLayer(layer, WAXED_COPPER_TRAPDOOR, WAXED_EXPOSED_COPPER_TRAPDOOR, WAXED_WEATHERED_COPPER_TRAPDOOR, WAXED_OXIDIZED_COPPER_TRAPDOOR);
		//Torchflower
		setLayer(layer, TORCHFLOWER_CROP.asBlock(), TORCHFLOWER.asBlock(), TORCHFLOWER.getPottedBlock());
		//Pitcher Plant
		setLayer(layer, PITCHER_CROP.asBlock(), PITCHER_PLANT.asBlock());
		//Extended Bamboo
		setLayer(layer, BAMBOO_DOOR, BAMBOO_TRAPDOOR);
		setLayer(layer, DRIED_BAMBOO);
		setLayer(layer, DRIED_BAMBOO_DOOR, DRIED_BAMBOO_TRAPDOOR);
		//Echo
		setLayer(layer, ECHO_CLUSTER, LARGE_ECHO_BUD, MEDIUM_ECHO_BUD, SMALL_ECHO_BUD);
		setLayer(layer, SCULK_VEIN, SCULK_SHRIEKER, CALIBRATED_SCULK_SENSOR);
		//Woodcutter
		setLayer(layer, MANGROVE_WOODCUTTER, BAMBOO_WOODCUTTER, DRIED_BAMBOO_WOODCUTTER, CHERRY_WOODCUTTER);
		setLayer(layer, ACACIA_WOODCUTTER, BIRCH_WOODCUTTER, DARK_OAK_WOODCUTTER, JUNGLE_WOODCUTTER);
		setLayer(layer, WOODCUTTER, SPRUCE_WOODCUTTER, CRIMSON_WOODCUTTER, WARPED_WOODCUTTER, GILDED_WOODCUTTER);
		setLayer(layer, CASSIA_WOODCUTTER, CHARRED_WOODCUTTER, DOGWOOD_WOODCUTTER, HAY_WOODCUTTER, SUGAR_CANE_WOODCUTTER);
		setLayer(layer, BLUE_MUSHROOM_WOODCUTTER, BROWN_MUSHROOM_WOODCUTTER, RED_MUSHROOM_WOODCUTTER, MUSHROOM_STEM_WOODCUTTER);
		//Flowers
		setLayer(layer, AMARANTH, BLUE_ROSE_BUSH, TALL_ALLIUM, TALL_PINK_ALLIUM, TALL_VANILLA);
		setLayer(layer, BUTTERCUP, PINK_DAISY, ROSE, BLUE_ROSE, MAGENTA_TULIP, MARIGOLD, INDIGO_ORCHID, MAGENTA_ORCHID);
		setLayer(layer, ORANGE_ORCHID, PURPLE_ORCHID, RED_ORCHID, WHITE_ORCHID, YELLOW_ORCHID, PINK_ALLIUM, LAVENDER);
		setLayer(layer, HYDRANGEA, PAEONIA, ASTER, VANILLA_FLOWER);
		//Flower Parts
		setLayer(layer, AMARANTH_PARTS, VANILLA_PARTS);
		setLayer(layer, BUTTERCUP_PARTS, PINK_DAISY_PARTS, ROSE_PARTS, BLUE_ROSE_PARTS, MAGENTA_TULIP_PARTS, MARIGOLD_PARTS, INDIGO_ORCHID_PARTS, MAGENTA_ORCHID_PARTS);
		setLayer(layer, ORANGE_ORCHID_PARTS, PURPLE_ORCHID_PARTS, RED_ORCHID_PARTS, WHITE_ORCHID_PARTS, YELLOW_ORCHID_PARTS, PINK_ALLIUM_PARTS, LAVENDER_PARTS);
		setLayer(layer, HYDRANGEA_PARTS, PAEONIA_PARTS, ASTER_PARTS);
		//Flower Parts (Vanilla)
		setLayer(layer, ALLIUM_PARTS, AZURE_BLUET_PARTS, BLUE_ORCHID_PARTS, CORNFLOWER_PARTS, DANDELION_PARTS);
		setLayer(layer, LILAC_PARTS, LILY_OF_THE_VALLEY_PARTS, ORANGE_TULIP_PARTS, OXEYE_DAISY_PARTS, PEONY_PARTS, PINK_TULIP_PARTS);
		setLayer(layer, POPPY_PARTS, RED_TULIP_PARTS, SUNFLOWER_PARTS, WHITE_TULIP_PARTS, WITHER_ROSE_PARTS);
		//Gilded
		setLayer(layer, GILDED_TRAPDOOR);
		//Plushies
		setLayer(layer, BAT_PLUSHIE, CAMEL_PLUSHIE, SADDLED_CAMEL_PLUSHIE);
		//Misc
		setLayer(layer, GUNPOWDER_FUSE);
		setLayer(layer, BLUE_MUSHROOM, MYCELIUM_ROOTS);
		setLayer(layer, DEATH_CAP_MUSHROOM, BLUE_NETHERSHROOM);
		setLayer(layer, GILDED_FUNGUS, GILDED_ROOTS);
		setLayer(layer, WARPED_WART, GILDED_WART);
	}
	private static void InitializeTranslucent() {
		RenderLayer layer = RenderLayer.getTranslucent();
		setLayer(layer, EMPTY_LANTERN);
		setLayer(layer, BEIGE_STAINED_GLASS, BEIGE_STAINED_GLASS_PANE, BEIGE_STAINED_GLASS_SLAB, BEIGE_STAINED_GLASS_TRAPDOOR);
		setLayer(layer, BURGUNDY_STAINED_GLASS, BURGUNDY_STAINED_GLASS_PANE, BURGUNDY_STAINED_GLASS_SLAB, BURGUNDY_STAINED_GLASS_TRAPDOOR);
		setLayer(layer, LAVENDER_STAINED_GLASS, LAVENDER_STAINED_GLASS_PANE, LAVENDER_STAINED_GLASS_SLAB, LAVENDER_STAINED_GLASS_TRAPDOOR);
		setLayer(layer, MINT_STAINED_GLASS, MINT_STAINED_GLASS_PANE, MINT_STAINED_GLASS_SLAB, MINT_STAINED_GLASS_TRAPDOOR);
		setLayer(layer, GLASS_SLAB, GLASS_TRAPDOOR, TINTED_GLASS_PANE, TINTED_GLASS_SLAB, TINTED_GLASS_TRAPDOOR);
		setLayer(layer, RUBY_GLASS, RUBY_GLASS_PANE, RUBY_GLASS_SLAB, RUBY_GLASS_TRAPDOOR);
		for(BlockContainer container : STAINED_GLASS_SLABS.values()) setLayer(layer, container);
		for(BlockContainer container : STAINED_GLASS_TRAPDOORS.values()) setLayer(layer, container);
		//Slime
		setLayer(layer, BLUE_SLIME_BLOCK, PINK_SLIME_BLOCK);
		//Lanterns
		setLayer(layer, SLIME_LANTERN, TROPICAL_SLIME_LANTERN, PINK_SLIME_LANTERN, MAGMA_CUBE_LANTERN);
		//Plushies
		setLayer(layer, SLIME_PLUSHIE, TROPICAL_SLIME_PLUSHIE, PINK_SLIME_PLUSHIE);
	}

	public static void setLayer(RenderLayer layer, Block block) { BlockRenderLayerMap.INSTANCE.putBlock(block, layer); }
	public static void setLayer(RenderLayer layer, Block... blocks) { BlockRenderLayerMap.INSTANCE.putBlocks(layer, blocks); }
	public static void setLayer(RenderLayer layer, BlockContainer container) { setLayer(layer, container.asBlock()); }
	public static void setLayer(RenderLayer layer, BlockContainer... containers) {
		setLayer(layer, Arrays.stream(containers).map(BlockContainer::asBlock).toArray(Block[]::new));
	}
	public static void setLayer(RenderLayer layer, PottedBlockContainer... containers) {
		setLayer(layer, Arrays.stream(containers).map(PottedBlockContainer::asBlock).toArray(Block[]::new));
		setLayer(layer, Arrays.stream(containers).map(PottedBlockContainer::getPottedBlock).toArray(Block[]::new));
	}
	public static void setLayer(RenderLayer layer, TorchContainer... containers) {
		for(TorchContainer container : containers) {
			BlockRenderLayerMap.INSTANCE.putBlocks(layer, container.asBlock(), container.getWallBlock());
		}
	}
	public static void setLayer(RenderLayer layer, UnlitTorchContainer... containers) {
		for(UnlitTorchContainer container : containers) {
			BlockRenderLayerMap.INSTANCE.putBlocks(layer, container.asBlock(), container.getWallBlock());
		}
	}

	@Override
	public void onInitializeClient() {
		//Render Layers
		InitializeCutoutMipped();
		InitializeCutout();
		InitializeTranslucent();
		//Mob Heads
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.PIGLIN_HEAD, PiglinHeadEntityModel::getTexturedModelData);
		BlockEntityRendererRegistry.register(PIGLIN_HEAD_BLOCK_ENTITY, PiglinHeadEntityRenderer::new);
		//Ragdolls
		BlockEntityRendererRegistry.register(RAGDOLL_BLOCK_ENTITY, RagdollBlockEntityRenderer::new);
		//Brushable Block Entities
		BlockEntityRendererRegistry.register(SUSPICIOUS_BLOCK_ENTITY, BrushableBlockEntityRenderer::new);
		BlockEntityRendererRegistry.register(SANDY_BLOCK_ENTITY, BrushableBlockEntityRenderer::new);
		//Frog & Tadpole
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.FROG, FrogEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(FROG_ENTITY, FrogEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.TADPOLE, TadpoleEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(TADPOLE_ENTITY, TadpoleEntityRenderer::new);
		//Warden
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.WARDEN, fun.mousewich.client.render.entity.model.WardenEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(WARDEN_ENTITY, WardenEntityRenderer::new);
		//Camel
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.CAMEL, CamelEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(CAMEL_ENTITY, CamelEntityRenderer::new);
		//Sniffer
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.SNIFFER, SnifferEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(SNIFFER_ENTITY, SnifferEntityRenderer::new);
		//Bone Spider
		EntityRendererRegistry.register(BONE_SHARD_PROJECTILE_ENTITY, FlyingItemEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.BONE_SPIDER, BoneSpiderEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(BONE_SPIDER_ENTITY, BoneSpiderEntityRenderer::new);
		//Jumping Spider
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.JUMPING_SPIDER, SpiderEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(JUMPING_SPIDER_ENTITY, JumpingSpiderEntityRenderer::new);
		//Slime Spider
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.SLIME_SPIDER, SlimeSpiderEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.SLIME_SPIDER_OUTER, SlimeSpiderEntityModel::getOuterTexturedModelData);
		EntityRendererRegistry.register(SLIME_SPIDER_ENTITY, SlimeSpiderEntityRenderer::new);
		//Hedgehog Entity
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.HEDGEHOG, fun.mousewich.client.render.entity.model.HedgehogEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(HEDGEHOG_ENTITY, fun.mousewich.client.render.entity.renderer.HedgehogEntityRenderer::new);
		//Raccoons
		EntityRendererRegistry.register(RACCOON_ENTITY, RaccoonEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.RACCOON, RaccoonEntityModel::getTexturedModelData);
		//Red Pandas
		EntityRendererRegistry.register(RED_PANDA_ENTITY, RedPandaEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.RED_PANDA, RedPandaEntityModel::getTexturedModelData);
		//Phantoms
		EntityRendererRegistry.register(RED_PHANTOM_ENTITY, RedPhantomEntityRenderer::new);
		//Piranha Entity
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.PIRANHA, PiranhaEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(PIRANHA_ENTITY, PiranhaEntityRenderer::new);
		//Golems
		EntityRendererRegistry.register(MELON_SEED_PROJECTILE_ENTITY, FlyingItemEntityRenderer::new);
		EntityRendererRegistry.register(MELON_GOLEM_ENTITY, fun.mousewich.client.render.entity.renderer.MelonGolemEntityRenderer::new);
		//Chicken Variants
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.FANCY_CHICKEN, FancyChickenEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(FANCY_CHICKEN_ENTITY, FancyChickenEntityRenderer::new);
		//Cow Variants
		EntityRendererRegistry.register(BLUE_MOOSHROOM_ENTITY, BlueMooshroomEntityRenderer::new);
		EntityRendererRegistry.register(NETHER_MOOSHROOM_ENTITY, NetherMooshroomEntityRenderer::new);
		//Flower Cows
		EntityRendererRegistry.register(MOOBLOOM_ENTITY, MoobloomEntityRenderer::new);
		EntityRendererRegistry.register(MOOLIP_ENTITY, MoolipEntityRenderer::new);
		EntityRendererRegistry.register(MOOBLOSSOM_ENTITY, MooblossomEntityRenderer::new);
		//Sheep Variants
		EntityRendererRegistry.register(MOSSY_SHEEP_ENTITY, MossySheepEntityRenderer::new);
		EntityRendererRegistry.register(RAINBOW_SHEEP_ENTITY, RainbowSheepEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.RAINBOW_SHEEP, RainbowSheepEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.RAINBOW_SHEEP_FUR, RainbowSheepWoolEntityModel::getTexturedModelData);
		//Tropical Slime
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.TROPICAL_SLIME, TropicalSlimeEntityModel::getInnerTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.TROPICAL_SLIME_OUTER, TropicalSlimeEntityModel::getOuterTexturedModelData);
		EntityRendererRegistry.register(TROPICAL_SLIME_ENTITY, TropicalSlimeEntityRenderer::new);
		//Pink Slime
		EntityRendererRegistry.register(PINK_SLIME_BALL_ENTITY, FlyingItemEntityRenderer::new);
		EntityRendererRegistry.register(PINK_SLIME_ENTITY, PinkSlimeEntityRenderer::new);
		//Other Slime Mobs
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.SLIME_CHICKEN, SlimeChickenEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.SLIME_CHICKEN_OUTER, SlimeChickenEntityModel::getOuterTexturedModelData);
		EntityRendererRegistry.register(SLIME_CHICKEN_ENTITY, SlimeChickenEntityRenderer::new);

		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.SLIME_COW, SlimeCowEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.SLIME_COW_OUTER, SlimeCowEntityModel::getOuterTexturedModelData);
		EntityRendererRegistry.register(SLIME_COW_ENTITY, SlimeCowEntityRenderer::new);

		//Slime Creeper
		EntityRendererRegistry.register(SLIME_CREEPER_ENTITY, SlimeCreeperEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.SLIME_CREEPER, SlimeCreeperEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.SLIME_CREEPER_OUTER, () -> SlimeCreeperEntityModel.getOuterTexturedModelData(Dilation.NONE));
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.SLIME_CREEPER_ARMOR, () -> SlimeCreeperEntityModel.getOuterTexturedModelData(new Dilation(2.0f)));
		//Mossy Skeleton
		EntityRendererRegistry.register(MOSSY_SKELETON_ENTITY, MossySkeletonEntityRenderer::new);
		//Slimy Skeleton
		EntityRendererRegistry.register(SLIMY_SKELETON_ENTITY, SlimySkeletonEntityRenderer::new);
		//Sunken Skeleton
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.SUNKEN_SKELETON, SunkenSkeletonEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.SUNKEN_SKELETON_INNER_ARMOR, SunkenSkeletonEntityModel::getArmorModelData);
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.SUNKEN_SKELETON_OUTER_ARMOR, SunkenSkeletonEntityModel::getArmorModelData);
		EntityRendererRegistry.register(SUNKEN_SKELETON_ENTITY, SunkenSkeletonEntityRenderer::new);
		//Layered Zombie
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.LAYERED_ZOMBIE, LayeredZombieEntityModel::getInnerModelData);
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.LAYERED_ZOMBIE_INNER_ARMOR, LayeredZombieEntityModel::getArmorModelData);
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.LAYERED_ZOMBIE_OUTER_ARMOR, LayeredZombieEntityModel::getArmorModelData);
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.LAYERED_ZOMBIE_OUTER, LayeredZombieEntityModel::getOuterModelData);
		//Slime Zombie
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.SLIME_ZOMBIE, SlimeZombieEntityModel::getInnerModelData);
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.SLIME_ZOMBIE_INNER_ARMOR, SlimeZombieEntityModel::getArmorModelData);
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.SLIME_ZOMBIE_OUTER_ARMOR, SlimeZombieEntityModel::getArmorModelData);
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.SLIME_ZOMBIE_OUTER, SlimeZombieEntityModel::getOuterModelData);
		EntityRendererRegistry.register(SLIME_ZOMBIE_ENTITY, SlimeZombieEntityRenderer::new);
		//Frozen Zombie
		EntityRendererRegistry.register(SLOWING_SNOWBALL_ENTITY, FlyingItemEntityRenderer::new);
		EntityRendererRegistry.register(FROZEN_ZOMBIE_ENTITY, FrozenZombieEntityRenderer::new);
		//Jungle Zombie
		EntityRendererRegistry.register(JUNGLE_ZOMBIE_ENTITY, JungleZombieEntityRenderer::new);
		//Purple Eye of Ender
		EntityRendererRegistry.register(PURPLE_EYE_OF_ENDER_ENTITY, context -> new FlyingItemEntityRenderer<>(context, 1.0f, true));
		//Hanging Signs
		TexturedModelData hangingSignModel = HangingSignModel.getTexturedModelData();
		Set<SignType> signTypes = new HashSet<>(List.of(SignType.OAK, SignType.SPRUCE, SignType.BIRCH, SignType.ACACIA, SignType.JUNGLE, SignType.DARK_OAK, SignType.CRIMSON, SignType.WARPED));
		signTypes.addAll(SIGN_TYPES);
		signTypes.addAll(HANGING_SIGN_SUBTYPES.keySet());
		for (SignType type : signTypes) {
			EntityModelLayerRegistry.registerModelLayer(HangingSignModel.createSignLayer(type), () -> hangingSignModel);
		}
		//Custom Boats
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.BOAT, BoatEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.RAFT, RaftEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(MOD_BOAT_ENTITY, ModBoatEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.CHEST_BOAT, ChestBoatEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.CHEST_RAFT, ChestRaftEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(CHEST_BOAT_ENTITY, ChestBoatEntityRenderer::new);
		EntityRendererRegistry.register(MOD_CHEST_BOAT_ENTITY, ChestBoatEntityRenderer::new);
		//Banners
		ClientSpriteRegistryCallback.event(TexturedRenderLayers.BANNER_PATTERNS_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
			for (ModBannerPattern pattern : ModBannerPattern.values()) registry.register(pattern.getSpriteId(true));
		}));
		ClientSpriteRegistryCallback.event(TexturedRenderLayers.SHIELD_PATTERNS_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
			for (ModBannerPattern pattern : ModBannerPattern.values()) registry.register(pattern.getSpriteId(false));
		}));
		//Particles
		ParticleFactoryRegistry PARTICLES = ParticleFactoryRegistry.getInstance();
		//Egg Crack
		PARTICLES.register(ModParticleTypes.EGG_CRACK, ModSuspendParticle.EggCrackParticleFactory::new);
		//Slime
		PARTICLES.register(ModParticleTypes.ITEM_BLUE_SLIME, new ModCrackParticle.BlueSlimeballFactory());
		PARTICLES.register(ModParticleTypes.ITEM_PINK_SLIME, new ModCrackParticle.PinkSlimeballFactory());
		//Throwable Tomatoes
		EntityRendererRegistry.register(THROWABLE_TOMATO_ENTITY, FlyingItemEntityRenderer::new);
		PARTICLES.register(ModParticleTypes.TOMATO, TomatoParticle.Factory::new);
		//Bottled Confetti & Dragon Breath
		EntityRendererRegistry.register(BOTTLED_CONFETTI_ENTITY, FlyingItemEntityRenderer::new);
		EntityRendererRegistry.register(DROPPED_CONFETTI_ENTITY, EmptyEntityRenderer::new);
		EntityRendererRegistry.register(CONFETTI_CLOUD_ENTITY, EmptyEntityRenderer::new);
		EntityRendererRegistry.register(DROPPED_DRAGON_BREATH_ENTITY, EmptyEntityRenderer::new);
		EntityRendererRegistry.register(DRAGON_BREATH_CLOUD_ENTITY, EmptyEntityRenderer::new);
		//Bottled Lightning
		EntityRendererRegistry.register(BOTTLED_LIGHTNING_ENTITY, FlyingItemEntityRenderer::new);
		//Grappling Rod
		ModelPredicateProviderRegistrySpecificAccessor.callRegister(GRAPPLING_ROD, new Identifier("cast"), ModClient::castGrapplingRod);
		//Bleeing Obsidian
		PARTICLES.register(ModParticleTypes.LANDING_OBSIDIAN_BLOOD, ModBlockLeakParticle.LandingObsidianBloodFactory::new);
		PARTICLES.register(ModParticleTypes.FALLING_OBSIDIAN_BLOOD, ModBlockLeakParticle.FallingObsidianBloodFactory::new);
		PARTICLES.register(ModParticleTypes.DRIPPING_OBSIDIAN_BLOOD, ModBlockLeakParticle.DrippingObsidianBloodFactory::new);
		//Torches
		ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
			//Thrown Items
			registry.register(ID("particle/thrown_tomato"));
			//Torches
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
			//Mud
			registry.register(ID("particle/mud_bubble"));
			registry.register(ID("particle/mud_splash_0"));
			registry.register(ID("particle/mud_splash_1"));
			registry.register(ID("particle/mud_splash_2"));
			registry.register(ID("particle/mud_splash_3"));
			//Blood
			registry.register(ID("particle/blood_bubble"));
			registry.register(ID("particle/blood_splash_0"));
			registry.register(ID("particle/blood_splash_1"));
			registry.register(ID("particle/blood_splash_2"));
			registry.register(ID("particle/blood_splash_3"));
		}));
		PARTICLES.register(ModParticleTypes.UNDERWATER_TORCH_GLOW, FlameParticle.Factory::new);
		PARTICLES.register(ModParticleTypes.PRISMARINE_FLAME, FlameParticle.Factory::new);
		PARTICLES.register(ModParticleTypes.COPPER_FLAME, FlameParticle.Factory::new);
		PARTICLES.register(ModParticleTypes.GOLD_FLAME, FlameParticle.Factory::new);
		PARTICLES.register(ModParticleTypes.IRON_FLAME, FlameParticle.Factory::new);
		PARTICLES.register(ModParticleTypes.NETHERITE_FLAME, FlameParticle.Factory::new);
		PARTICLES.register(ModParticleTypes.ENDER_FIRE_FLAME, FlameParticle.Factory::new);
		PARTICLES.register(ModParticleTypes.SMALL_SOUL_FLAME, FlameParticle.SmallFactory::new);
		PARTICLES.register(ModParticleTypes.SMALL_ENDER_FLAME, FlameParticle.SmallFactory::new);
		PARTICLES.register(ModParticleTypes.SMALL_NETHERITE_FLAME, FlameParticle.SmallFactory::new);
		//Cherry Leaves
		PARTICLES.register(ModParticleTypes.CHERRY_LEAVES, CherryLeavesParticle.Factory::new);
		//Sculk
		PARTICLES.register(ModParticleTypes.SCULK_SOUL, ModSoulParticle.SculkSoulFactory::new);
		PARTICLES.register(ModParticleTypes.SCULK_CHARGE, SculkChargeParticle.Factory::new);
		PARTICLES.register(ModParticleTypes.SCULK_CHARGE_POP, SculkChargePopParticle.Factory::new);
		PARTICLES.register(ModParticleTypes.SHRIEK, ShriekParticle.Factory::new);
		PARTICLES.register(ModParticleTypes.SONIC_BOOM, SonicBoomParticle.Factory::new);
		//Liquid Mud (Particle)
		PARTICLES.register(ModParticleTypes.MUD_BUBBLE, WaterBubbleParticle.Factory::new);
		PARTICLES.register(ModParticleTypes.MUD_SPLASH, WaterSplashParticle.SplashFactory::new);
		PARTICLES.register(ModParticleTypes.DRIPPING_MUD, ModBlockLeakParticle.DrippingMudFactory::new);
		PARTICLES.register(ModParticleTypes.FALLING_MUD, ModBlockLeakParticle.FallingMudFactory::new);
		PARTICLES.register(ModParticleTypes.FALLING_DRIPSTONE_MUD, ModBlockLeakParticle.FallingDripstoneMudFactory::new);
		setupFluidRendering(STILL_MUD_FLUID, FLOWING_MUD_FLUID, ID("mud"), 0x472804);
		BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getSolid(), STILL_MUD_FLUID, FLOWING_MUD_FLUID);
		//Blood Fluid
		PARTICLES.register(ModParticleTypes.BLOOD_BUBBLE, WaterBubbleParticle.Factory::new);
		PARTICLES.register(ModParticleTypes.BLOOD_SPLASH, WaterSplashParticle.SplashFactory::new);
		PARTICLES.register(ModParticleTypes.DRIPPING_BLOOD, ModBlockLeakParticle.DrippingBloodFactory::new);
		PARTICLES.register(ModParticleTypes.FALLING_BLOOD, ModBlockLeakParticle.FallingBloodFactory::new);
		PARTICLES.register(ModParticleTypes.FALLING_DRIPSTONE_BLOOD, ModBlockLeakParticle.FallingDripstoneBloodFactory::new);
		setupFluidRendering(STILL_BLOOD_FLUID, FLOWING_BLOOD_FLUID, ID("blood"), 0xFF0000);
		BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), STILL_BLOOD_FLUID, FLOWING_BLOOD_FLUID);
		//Custom Beds
		ClientSpriteRegistryCallback.event(TexturedRenderLayers.BEDS_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
			registry.register(GLOW_LICHEN_BED.GetTexture());
			registry.register(MOSS_BED.GetTexture());
			registry.register(RAINBOW_BED.GetTexture());
		}));
		//Generic 1x1
		ScreenRegistry.register(GENERIC_1X1_SCREEN_HANDLER, Generic1x1ContainerScreen::new);
		//Woodcutter
		ScreenRegistry.register(WOODCUTTER_SCREEN_HANDLER, WoodcutterScreen::new);
		//Trimming Table
		ScreenRegistry.register(TRIMMING_SCREEN_HANDLER, TrimmingScreen::new);
		//Projectiles
		for (ArrowContainer container : ArrowContainer.ARROW_CONTAINERS) {
			EntityRendererRegistry.register(container.getEntityType(), ModArrowEntityRenderer::new);
		}
		//Horn Items
		Identifier TOOTING = new Identifier("tooting");
		UnclampedModelPredicateProvider HORN_PREDICATE = (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0f : 0.0f;
		ModelPredicateProviderRegistryInvoker.Register(ModBase.GOAT_HORN, TOOTING, HORN_PREDICATE);
		ModelPredicateProviderRegistryInvoker.Register(ModBase.WIND_HORN, TOOTING, HORN_PREDICATE);
		//Powder Kegs
		EntityRendererRegistry.register(POWDER_KEG_ENTITY, ModTntEntityRenderer::new);
		//Keybinds
		KeyBinding useTertiaryActivePowerKeybind = new KeyBinding("key." + NAMESPACE + ".tertiary_active", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "category." + NAMESPACE);
		ApoliClient.registerPowerKeybinding("key." + NAMESPACE + ".tertiary_active", useTertiaryActivePowerKeybind);
		ApoliClient.registerPowerKeybinding("tertiary", useTertiaryActivePowerKeybind);
		KeyBindingHelper.registerKeyBinding(useTertiaryActivePowerKeybind);

		if (ModConfig.REGISTER_HAVEN_MOD) {
			HavenModClient.InitializeClient();
		}
	}
	private <T extends ParticleEffect> void registerBlockLeakFactory(ParticleFactoryRegistry registry, ParticleType<T> type, ParticleFactory.BlockLeakParticleFactory<T> factory) {
		registry.register(type, (FabricSpriteProvider spriteProvider) -> (particleType, world, x, y, z, velocityX, velocityY, velocityZ) -> {
			SpriteBillboardParticle spriteBillboardParticle = factory.createParticle(particleType, world, x, y, z, velocityX, velocityY, velocityZ);
			if (spriteBillboardParticle != null) spriteBillboardParticle.setSprite(spriteProvider);
			return spriteBillboardParticle;
		});
	}
	private <T extends ParticleEffect> void registerSpriteParticleFactory(ParticleFactoryRegistry registry, ParticleType<T> type, ParticleFactory.SpriteParticleFactory<T> factory) {
		registry.register(type, (FabricSpriteProvider spriteProvider) -> (particleType, world, x, y, z, velocityX, velocityY, velocityZ) -> {
			SpriteBillboardParticle spriteBillboardParticle = factory.createParticle(particleType, world, x, y, z, spriteProvider);
			if (spriteBillboardParticle != null) spriteBillboardParticle.setSprite(spriteProvider);
			return spriteBillboardParticle;
		});
	}
	@Environment(value=EnvType.CLIENT)
	public interface ParticleFactory<T extends ParticleEffect> {
		Particle createParticle(T var1, ClientWorld clientWorld, double x, double y, double z, double var9, double var11, double var13);
		@Environment(value=EnvType.CLIENT)
		interface BlockLeakParticleFactory<T extends ParticleEffect> {
			SpriteBillboardParticle createParticle(T var1, ClientWorld clientWorld, double x, double y, double z, double var9, double var11, double var13);
		}
		@Environment(value=EnvType.CLIENT)
		interface SpriteParticleFactory<T extends ParticleEffect> {
			SpriteBillboardParticle createParticle(T var1, ClientWorld clientWorld, double x, double y, double z, SpriteProvider spriteProvider);
		}
	}

	public static void RegisterBlockColors(BlockColors blockColors) {
		//Generic Foliage
		blockColors.registerColorProvider((s, w, p, i) -> w != null && p != null ? BiomeColors.getFoliageColor(w, p) : FoliageColors.getDefaultColor(),
				//Hedges
				HEDGE_BLOCK.asBlock(),
				//Mangrove Leaves
				MANGROVE_LEAVES.asBlock()
		);
		//Pink Petals
		blockColors.registerColorProvider((state, world, pos, tintIndex) -> {
			if (tintIndex != 0) {
				if (world == null || pos == null) return GrassColors.getColor(0.5, 1.0);
				return BiomeColors.getGrassColor(world, pos);
			}
			return -1;
		}, PINK_PETALS.asBlock());
		//Gourds
		blockColors.registerColorProvider((state, world, pos, tintIndex) -> 14731036, ATTACHED_WHITE_PUMPKIN_STEM);
		blockColors.registerColorProvider((state, world, pos, tintIndex) -> {
			int i = state.get(StemBlock.AGE);
			return (i * 32) << 16 | (255 - i * 8) << 8 | (i * 4);
		}, WHITE_PUMPKIN_STEM);

		if (ModConfig.REGISTER_HAVEN_MOD) HavenModClient.RegisterBlockColors(blockColors);
	}

	public static void RegisterItemColors(ItemColors itemColors) {
		//Generic Foliage
		itemColors.register((itemStack, i) -> 9619016,
				//Hedges
				HEDGE_BLOCK,
				//Mangrove
				MANGROVE_LEAVES
		);
		//Fleece Armor
		itemColors.register((stack, tintIndex) -> {
			NbtCompound nbtCompound = stack.getSubNbt("display");
			return tintIndex > 0 ? -1 : nbtCompound != null && nbtCompound.contains("color", 99) ? nbtCompound.getInt("color") : 0xFFFFFF;
		}, FLEECE_HELMET, FLEECE_CHESTPLATE, FLEECE_LEGGINGS, FLEECE_BOOTS, FLEECE_HORSE_ARMOR);
		//Summoning Arrows
		for (Map.Entry<Item, Pair<Integer, Integer>> entry : ArrowContainer.SUMMONING_ARROW_COLORS.entrySet()) {
			Pair<Integer, Integer> colors = entry.getValue();
			itemColors.register((stack, tintIndex) -> tintIndex == 0 ? colors.getRight() : tintIndex == 1 ? colors.getLeft() : -1, entry.getKey());
		}

		if (ModConfig.REGISTER_HAVEN_MOD) HavenModClient.RegisterItemColors(itemColors);
	}

	private static float castGrapplingRod(ItemStack stack, ClientWorld world, LivingEntity entity, int seed) {
		if (entity == null) return 0;
		else {
			boolean bl = entity.getMainHandStack() == stack;
			boolean bl2 = entity.getOffHandStack() == stack;
			if (entity.getMainHandStack().getItem() instanceof FishingRodItem) bl2 = false;
			return (bl || bl2) && entity instanceof PlayerEntity && ((PlayerEntity)entity).fishHook != null ? 1 : 0;
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
		final Sprite[] fluidSprites = { null, null };
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
			@Override
			public void reload(ResourceManager manager) {
				final Function<Identifier, Sprite> atlas = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);
				fluidSprites[0] = atlas.apply(stillSpriteId);
				fluidSprites[1] = atlas.apply(flowingSpriteId);
			}
			@Override public Identifier getFabricId() { return listenerId; }
		});
		// The FluidRenderer gets the sprites and color from a FluidRenderHandler during rendering
		final FluidRenderHandler renderHandler = new FluidRenderHandler() {
			@Override public Sprite[] getFluidSprites(BlockRenderView view, BlockPos pos, FluidState state) { return fluidSprites; }
			@Override public int getFluidColor(BlockRenderView view, BlockPos pos, FluidState state) { return color; }
		};
		FluidRenderHandlerRegistry.INSTANCE.register(still, renderHandler);
		FluidRenderHandlerRegistry.INSTANCE.register(flowing, renderHandler);
	}
}