package fun.mousewich.haven;

import fun.mousewich.client.render.entity.renderer.ModTntEntityRenderer;
import fun.mousewich.container.FlowerPartContainer;
import fun.mousewich.container.PottedBlockContainer;
import fun.mousewich.haven.client.render.block.renderer.AnchorBlockEntityRenderer;
import fun.mousewich.haven.client.render.block.renderer.BrokenAnchorBlockEntityRenderer;
import fun.mousewich.haven.client.render.block.renderer.SubstituteAnchorBlockEntityRenderer;
import fun.mousewich.haven.particle.VectorParticle;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.BatEntityRenderer;

import static fun.mousewich.ModClient.setLayer;
import static fun.mousewich.haven.HavenMod.*;

public class HavenModClient {
	public static void InitializeCutout() {
		RenderLayer layer = RenderLayer.getCutout();
		setLayer(layer, ANGEL_BAT_PLUSHIE);
		for (PottedBlockContainer container : CARNATIONS.values()) setLayer(layer, container);
		for (FlowerPartContainer container : CARNATION_PARTS.values()) setLayer(layer, container);
		//DECORATION-ONLY BLOCKS
		setLayer(layer, DECORATIVE_VINE, DECORATIVE_SUGAR_CANE, DECORATIVE_CACTUS,
				DECORATIVE_ACACIA_SAPLING, DECORATIVE_BIRCH_SAPLING,
				DECORATIVE_DARK_OAK_SAPLING, DECORATIVE_JUNGLE_SAPLING,
				DECORATIVE_OAK_SAPLING, DECORATIVE_SPRUCE_SAPLING,
				DECORATIVE_CHERRY_SAPLING,
				DECORATIVE_CASSIA_SAPLING, DECORATIVE_DOGWOOD_SAPLING,
				DECORATIVE_BEETROOTS, DECORATIVE_CARROTS,
				DECORATIVE_POTATOES, DECORATIVE_WHEAT,
				DECORATIVE_CABBAGES, DECORATIVE_ONIONS);
	}
	public static void InitializeTranslucent() {
		RenderLayer layer = RenderLayer.getTranslucent();
		setLayer(layer, SUBSTITUTE_ANCHOR_BLOCK);
	}

	public static void InitializeClient() {
		InitializeCutout();
		InitializeTranslucent();
		//Particles
		ParticleFactoryRegistry PARTICLES = ParticleFactoryRegistry.getInstance();
		//Anchor Entities
		BlockEntityRendererRegistry.register(ANCHOR_BLOCK_ENTITY, AnchorBlockEntityRenderer::new);
		BlockEntityRendererRegistry.register(BROKEN_ANCHOR_BLOCK_ENTITY, BrokenAnchorBlockEntityRenderer::new);
		BlockEntityRendererRegistry.register(SUBSTITUTE_ANCHOR_BLOCK_ENTITY, SubstituteAnchorBlockEntityRenderer::new);
		//Angel Bat
		EntityRendererRegistry.register(ANGEL_BAT_ENTITY, BatEntityRenderer::new);
		//Deepest Sleep
		PARTICLES.register(VECTOR_ARROW_PARTICLE, VectorParticle.ArrowFactory::new);
		EntityRendererRegistry.register(CHUNKEATER_TNT_ENTITY, fun.mousewich.client.render.entity.renderer.ModTntEntityRenderer::new);
		//Miasma
		EntityRendererRegistry.register(CATALYZING_TNT_ENTITY, fun.mousewich.client.render.entity.renderer.ModTntEntityRenderer::new);
		//Alcatraz
		EntityRendererRegistry.register(SHARP_TNT_ENTITY, fun.mousewich.client.render.entity.renderer.ModTntEntityRenderer::new);
		//Hezekiah
		EntityRendererRegistry.register(DEVOURING_TNT_ENTITY, fun.mousewich.client.render.entity.renderer.ModTntEntityRenderer::new);
		//Digger Krozhul
		EntityRendererRegistry.register(VIOLENT_TNT_ENTITY, fun.mousewich.client.render.entity.renderer.ModTntEntityRenderer::new);
		//Soleil
		EntityRendererRegistry.register(SOFT_TNT_ENTITY, ModTntEntityRenderer::new);
	}

	public static void RegisterBlockColors(BlockColors blockColors) {
		//DECORATION-ONLY BLOCKS
		blockColors.registerColorProvider((state, world, pos, tintIndex) -> {
			if (world == null || pos == null) return FoliageColors.getDefaultColor();
			return BiomeColors.getFoliageColor(world, pos);
		}, HavenMod.DECORATIVE_VINE.asBlock());
		blockColors.registerColorProvider((state, world, pos, tintIndex) -> {
			if (world == null || pos == null) return -1;
			return BiomeColors.getGrassColor(world, pos);
		}, Blocks.SUGAR_CANE, DECORATIVE_SUGAR_CANE.asBlock());
	}
	public static void RegisterItemColors(ItemColors itemColors) {
		//DECORATION-ONLY BLOCKS
		itemColors.register((stack, tintIndex) -> FoliageColors.getDefaultColor(),
				DECORATIVE_VINE.asItem());
	}
}
