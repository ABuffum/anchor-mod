package fun.wich.gen.data.model;

import fun.wich.ModConfig;
import fun.wich.ModId;
import fun.wich.block.BlockConvertible;
import fun.wich.block.ModProperties;
import fun.wich.block.plushie.PlushieBlock;
import fun.wich.container.*;
import fun.wich.gen.data.ModDatagen;
import fun.wich.gen.data.fabric.FabricModelProvider;
import fun.wich.haven.HavenMod;
import fun.wich.util.ColorUtil;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.SlabType;
import net.minecraft.block.enums.WireConnection;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.model.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import static fun.wich.ModBase.*;
import static fun.wich.ModBase.FACETING_TABLE;
import static fun.wich.registry.ModBambooRegistry.*;
import static fun.wich.registry.ModCopperRegistry.*;

public class ModelGenerator extends FabricModelProvider {
	public ModelGenerator(FabricDataGenerator dataGenerator) { super(dataGenerator); }
	public static Identifier prefixPath(Identifier id, String prefix) { return new Identifier(id.getNamespace(), prefix + id.getPath()); }
	public static Identifier postfixPath(Identifier id, String postfix) { return new Identifier(id.getNamespace(), id.getPath() + postfix); }
	public static Identifier getBlockModelId(Block block) { return getBlockModelId("", block); }
	public static Identifier getBlockModelId(String prefix, Block block) { return prefixPath(Registry.BLOCK.getId(block), "block/" + prefix); }
	public static Identifier getBlockModelId(String prefix, Block block, String postfix) { return postfixPath(getBlockModelId(prefix, block), postfix); }
	public static Identifier getItemModelId(Item item) { return prefixPath(Registry.ITEM.getId(item), "item/"); }
	public static void cubeAllModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) { parentedItem(bsmg, bsmg::registerSimpleCubeAll, container); }
	public static void copiedSimpleBlockstate(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer copy) { copiedSimpleBlockstate(bsmg, container, copy.asBlock()); }
	public static void copiedSimpleBlockstate(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block copyBlock) {
		copiedSimpleBlockstate(bsmg, container.asBlock(), copyBlock);
		parentedItem(bsmg, container.asItem(), copyBlock);
	}
	public static void copiedSimpleBlockstate(BlockStateModelGenerator bsmg, Block block, Block copyBlock) { copiedSimpleBlockstate(bsmg, block, getBlockModelId(copyBlock)); }
	public static void copiedSimpleBlockstate(BlockStateModelGenerator bsmg, Block block, Identifier id) {
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, id));
	}

	private static void explicitModelCommon(BlockStateModelGenerator bsmg, Block block) { bsmg.registerSimpleState(block); }
	private static void explicitModelCommon(BlockStateModelGenerator bsmg, IBlockItemContainer container) { explicitModelCommon(bsmg, container.asBlock()); }
	public static void explicitBlockParentedItem(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		explicitModelCommon(bsmg, container);
		parentedItem(bsmg, container);
	}
	public static void explicitBlockGeneratedItem(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		explicitModelCommon(bsmg, container);
		generatedItemModel(bsmg, container);
	}
	public static void singletonModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, TexturedModel.Factory factory) {
		parentedItem(bsmg, (b) -> bsmg.registerSingleton(b, factory), container);
	}
	private static final Identifier TEMPLATE_SKULL = ModId.ID("minecraft:item/template_skull");
	private static final Identifier TEMPLATE_SPAWN_EGG = ModId.ID("minecraft:item/template_spawn_egg");
	private static final Identifier TEMPLATE_SUMMONING_ARROW = ModId.ID("item/template_summoning_arrow");
	public static void parentedItem(BlockStateModelGenerator bsmg, IBlockItemContainer container) { parentedItem(bsmg, container.asItem(), container.asBlock()); }
	public static void parentedItem(BlockStateModelGenerator bsmg, Item item, Block block) { bsmg.registerParentedItemModel(item, getBlockModelId(block)); }
	public static void parentedItem(BlockStateModelGenerator bsmg, Item item, Identifier parent) { bsmg.registerParentedItemModel(item, parent); }
	public static void parentedItem(BlockStateModelGenerator bsmg, Item item, Item parent) { parentedItem(bsmg, item, getItemModelId(parent)); }
	public static void parentedItem(BlockStateModelGenerator bsmg, Item item, ItemConvertible parent) { parentedItem(bsmg, item, parent.asItem()); }
	public static void spawnEggModel(BlockStateModelGenerator bsmg, Item item) { parentedItem(bsmg, item, TEMPLATE_SPAWN_EGG); }
	public static void skullModel(BlockStateModelGenerator bsmg, Item item) { parentedItem(bsmg, item, TEMPLATE_SKULL); }
	public interface ModelFunc { void apply(Block block); }
	public static void parentedItem(BlockStateModelGenerator bsmg, ModelFunc blockModel, IBlockItemContainer container) { parentedItem(bsmg, blockModel, container.asBlock(), container.asItem()); }
	public static void parentedItem(BlockStateModelGenerator bsmg, ModelFunc blockModel, Block block, Item item) {
		blockModel.apply(block);
		parentedItem(bsmg, item, block);
	}
	public static void handheldItemModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) { handheldItemModel(bsmg, container.asItem(), container.asBlock()); }
	public static void handheldItemModel(BlockStateModelGenerator bsmg, Item item, Block block) {
		Models.HANDHELD.upload(ModelIds.getItemModelId(item), Texture.layer0(block), bsmg.modelCollector);
	}
	public static void handheldItemModel(BlockStateModelGenerator bsmg, Item item) {
		Models.HANDHELD.upload(ModelIds.getItemModelId(item), Texture.layer0(item), bsmg.modelCollector);
	}
	public static void generatedItemModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) { generatedItemModel(bsmg, container.asItem(), container.asBlock()); }
	public static void generatedItemModel(BlockStateModelGenerator bsmg, Item item, Block block) {
		Models.GENERATED.upload(ModelIds.getItemModelId(item), Texture.layer0(block), bsmg.modelCollector);
	}
	public static void generatedItemModel(BlockStateModelGenerator bsmg, Item item) {
		Models.GENERATED.upload(ModelIds.getItemModelId(item), Texture.layer0(item), bsmg.modelCollector);
	}
	public static void generatedItemModel(BlockStateModelGenerator bsmg, Item item, Identifier identifier) {
		Models.GENERATED.upload(ModelIds.getItemModelId(item), Texture.layer0(identifier), bsmg.modelCollector);
	}
	public static void generatedItemModels(BlockStateModelGenerator bsmg, ItemConvertible... items) {
		for (ItemConvertible item : items) generatedItemModel(bsmg, item.asItem());
	}
	public static void logModel(BlockStateModelGenerator bsmg, IBlockItemContainer log, IBlockItemContainer wood) { makeLogModel(bsmg, log, null, wood); }
	public static void stemModel(BlockStateModelGenerator bsmg, IBlockItemContainer stem, IBlockItemContainer wood) { makeLogModel(bsmg, null, stem, wood); }
	public static void makeLogModel(BlockStateModelGenerator bsmg, IBlockItemContainer log, IBlockItemContainer stem, IBlockItemContainer wood) {
		Texture textures;
		if (log != null) {
			Block logBlock = log.asBlock();
			textures = Texture.sideAndEndForTop(logBlock);
			Identifier identifier = Models.CUBE_COLUMN.upload(logBlock, textures, bsmg.modelCollector);
			Identifier identifier2 = Models.CUBE_COLUMN_HORIZONTAL.upload(logBlock, textures, bsmg.modelCollector);
			bsmg.blockStateCollector.accept(BlockStateModelGenerator.createAxisRotatedBlockState(logBlock, identifier, identifier2));
			parentedItem(bsmg, log.asItem(), logBlock);
		}
		else {
			Block stemBlock = stem.asBlock();
			textures = Texture.sideAndEndForTop(stemBlock);
			Identifier identifier = Models.CUBE_COLUMN.upload(stemBlock, textures, bsmg.modelCollector);
			bsmg.blockStateCollector.accept(BlockStateModelGenerator.createAxisRotatedBlockState(stemBlock, identifier));
			parentedItem(bsmg, stem.asItem(), stemBlock);
		}
		Block woodBlock = wood.asBlock();
		Texture textureMap = textures.copyAndAdd(TextureKey.END, textures.getTexture(TextureKey.SIDE));
		Identifier identifier3 = Models.CUBE_COLUMN.upload(woodBlock, textureMap, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createAxisRotatedBlockState(woodBlock, identifier3));
		parentedItem(bsmg, wood.asItem(), woodBlock);
	}
	public static void horizontalPillarModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		parentedItem(bsmg, (b) -> bsmg.registerAxisRotated(b, TexturedModel.CUBE_COLUMN, TexturedModel.CUBE_COLUMN_HORIZONTAL), container);
	}
	public static void pillarModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		Texture textures = Texture.of(TextureKey.SIDE, Texture.getId(block)).put(TextureKey.END, Texture.getSubId(block, "_top"));
		Identifier model = Models.CUBE_COLUMN.upload(block, textures, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createAxisRotatedBlockState(block, model));
		parentedItem(bsmg, container.asItem(), block);
	}
	public static void stonePillarModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		Identifier modelId = getBlockModelId(block);
		Texture textures = Texture.of(TextureKey.SIDE, modelId).put(TextureKey.END, Texture.getSubId(block, "_top"));
		Identifier model = Models.CUBE_COLUMN.upload(block, textures, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createBlockStateWithTwoModelAndRandomInversion(block, modelId, model).coordinate(BlockStateModelGenerator.createAxisRotatedVariantMap()));
		parentedItem(bsmg, container.asItem(), block);
	}
	public static void topBottomSidesPillarModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block top, Block bottom) { topBottomSidesPillarModel(bsmg, container, top, Texture.getId(bottom)); }
	public static void topBottomSidesPillarModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block top, Identifier bottom) {
		Block block = container.asBlock();
		Identifier modelId = getBlockModelId(block);
		Texture textureMap = new Texture()
				.put(TextureKey.BOTTOM, bottom)
				.put(TextureKey.TOP, Texture.getId(top))
				.put(TextureKey.SIDE, Texture.getSubId(block, "_side"));
		Identifier model = Models.CUBE_BOTTOM_TOP.upload(block, textureMap, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createBlockStateWithTwoModelAndRandomInversion(block, modelId, model).coordinate(BlockStateModelGenerator.createAxisRotatedVariantMap()));
		parentedItem(bsmg, container.asItem(), block);
	}
	public static void facingTopBottomSidesPillarModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Identifier top, Identifier bottom) {
		Block block = container.asBlock();
		Texture textureMap = new Texture()
				.put(TextureKey.BOTTOM, bottom)
				.put(TextureKey.TOP, top)
				.put(TextureKey.SIDE, Texture.getSubId(block, "_side"));
		Identifier model = Models.CUBE_BOTTOM_TOP.upload(block, textureMap, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(block)
				.coordinate(BlockStateVariantMap.create(Properties.FACING)
						.register(Direction.DOWN, BlockStateVariant.create().put(VariantSettings.MODEL, model)
								.put(VariantSettings.X, VariantSettings.Rotation.R180))
						.register(Direction.UP, BlockStateVariant.create().put(VariantSettings.MODEL, model))
						.register(Direction.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, model)
								.put(VariantSettings.X, VariantSettings.Rotation.R90))
						.register(Direction.EAST, BlockStateVariant.create().put(VariantSettings.MODEL, model)
								.put(VariantSettings.X, VariantSettings.Rotation.R90)
								.put(VariantSettings.Y, VariantSettings.Rotation.R90))
						.register(Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.MODEL, model)
								.put(VariantSettings.X, VariantSettings.Rotation.R90)
								.put(VariantSettings.Y, VariantSettings.Rotation.R180))
						.register(Direction.WEST, BlockStateVariant.create().put(VariantSettings.MODEL, model)
								.put(VariantSettings.X, VariantSettings.Rotation.R90)
								.put(VariantSettings.Y, VariantSettings.Rotation.R270))));
		parentedItem(bsmg, container.asItem(), model);
	}
	public static void frogspawnModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		bsmg.registerItemModel(block);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, ModelIds.getBlockModelId(block)));
	}
	public static void tallFlowerModel(BlockStateModelGenerator bsmg, TallBlockContainer flower) {
		Block doubleBlock = flower.asBlock();
		bsmg.registerItemModel(doubleBlock, "_top");
		Identifier identifier = bsmg.createSubModel(doubleBlock, "_top", Models.CROSS, Texture::cross);
		Identifier identifier2 = bsmg.createSubModel(doubleBlock, "_bottom", Models.CROSS, Texture::cross);
		bsmg.registerDoubleBlock(doubleBlock, identifier, identifier2);
	}
	public static void tallFlowerModel(BlockStateModelGenerator bsmg, TallBlockContainer flower, Block topModel, Identifier bottomModel) {
		generatedItemModel(bsmg, flower.asItem(), topModel);
		Block doubleBlock = flower.asBlock();
		Identifier identifier = Models.CROSS.upload(doubleBlock, "_top", Texture.cross(topModel), bsmg.modelCollector);
		Identifier identifier2 = Models.CROSS.upload(doubleBlock, "_bottom", Texture.cross(bottomModel), bsmg.modelCollector);
		bsmg.registerDoubleBlock(doubleBlock, identifier, identifier2);
	}
	public static void amethystModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		bsmg.registerAmethyst(block);
		generatedItemModel(bsmg, container.asItem(), block);
	}
	public static void flowerPartModels(BlockStateModelGenerator bsmg, Identifier seedsItem, Identifier seedsBlock, FlowerPartContainer... containers) {
		Models.GENERATED.upload(seedsItem, Texture.layer0(seedsItem), bsmg.modelCollector);
		Identifier blockIdentifier = Models.CROSS.upload(seedsBlock, Texture.cross(seedsBlock), bsmg.modelCollector);
		for (FlowerPartContainer container : containers) {
			flowerPartModelCommon(bsmg, container, blockIdentifier);
			Item item = container.asItem();
			if (!getItemModelId(item).equals(seedsItem)) parentedItem(bsmg, container.asItem(), seedsItem);
		}
	}
	public static void flowerPartModelCommon(BlockStateModelGenerator bsmg, FlowerPartContainer container, Identifier identifier) {
		Block block = container.asBlock();
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, identifier));
		generatedItemModel(bsmg, container.petalsItem());
	}
	public static void flowerPartModelCommon(BlockStateModelGenerator bsmg, FlowerPartContainer container) {
		Block block = container.asBlock();
		Identifier identifier = Models.CROSS.upload(block, Texture.cross(block), bsmg.modelCollector);
		flowerPartModelCommon(bsmg, container, identifier);
	}
	public static void flowerPartModel(BlockStateModelGenerator bsmg, FlowerPartContainer container) {
		flowerPartModelCommon(bsmg, container);
		generatedItemModel(bsmg, container.asItem());
	}
	public static void crossModel(BlockStateModelGenerator bsmg, Block block) { crossModel(bsmg, Texture.cross(block), block); }
	public static void crossModel(BlockStateModelGenerator bsmg, Texture cross, Block block) {
		bsmg.registerItemModel(block);
		Identifier identifier = Models.CROSS.upload(block, cross, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, identifier));
	}
	public static void potModel(BlockStateModelGenerator bsmg, PottedBlockContainer container) { potModel(bsmg, Texture.plant(container.asBlock()), container.getPottedBlock()); }
	public static void potModel(BlockStateModelGenerator bsmg, Texture pottedCross, Block potted) {
		Identifier identifier2 = Models.FLOWER_POT_CROSS.upload(potted, pottedCross, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(potted, identifier2));
	}
	private static void pottedModelCommon(BlockStateModelGenerator bsmg, Texture cross, Texture pottedCross, PottedBlockContainer container) {
		crossModel(bsmg, cross, container.asBlock());
		potModel(bsmg, pottedCross, container.getPottedBlock());
	}
	public static void pottedModel(BlockStateModelGenerator bsmg, PottedBlockContainer container) {
		Block block = container.asBlock();
		pottedModelCommon(bsmg, Texture.cross(block), Texture.plant(block), container);
	}
	public static void rootsModel(BlockStateModelGenerator bsmg, PottedBlockContainer container) {
		Block block = container.asBlock();
		pottedModelCommon(bsmg, Texture.cross(block), Texture.plant(Texture.getSubId(block, "_pot")), container);
	}
	public static void paneModel(BlockStateModelGenerator bsmg, IBlockItemContainer pane, Block baseBlock) {
		Block paneBlock = pane.asBlock();
		Texture textureMap = Texture.paneAndTopForEdge(baseBlock, paneBlock);
		Identifier identifier = Models.TEMPLATE_GLASS_PANE_POST.upload(paneBlock, textureMap, bsmg.modelCollector);
		Identifier identifier2 = Models.TEMPLATE_GLASS_PANE_SIDE.upload(paneBlock, textureMap, bsmg.modelCollector);
		Identifier identifier3 = Models.TEMPLATE_GLASS_PANE_SIDE_ALT.upload(paneBlock, textureMap, bsmg.modelCollector);
		Identifier identifier4 = Models.TEMPLATE_GLASS_PANE_NOSIDE.upload(paneBlock, textureMap, bsmg.modelCollector);
		Identifier identifier5 = Models.TEMPLATE_GLASS_PANE_NOSIDE_ALT.upload(paneBlock, textureMap, bsmg.modelCollector);
		Models.GENERATED.upload(getItemModelId(pane.asItem()), Texture.layer0(baseBlock), bsmg.modelCollector);
		bsmg.blockStateCollector.accept(MultipartBlockStateSupplier.create(paneBlock).with(BlockStateVariant.create().put(VariantSettings.MODEL, identifier)).with(When.create().set(Properties.NORTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier2)).with(When.create().set(Properties.EAST, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier2).put(VariantSettings.Y, VariantSettings.Rotation.R90)).with(When.create().set(Properties.SOUTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier3)).with(When.create().set(Properties.WEST, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier3).put(VariantSettings.Y, VariantSettings.Rotation.R90)).with(When.create().set(Properties.NORTH, false), BlockStateVariant.create().put(VariantSettings.MODEL, identifier4)).with(When.create().set(Properties.EAST, false), BlockStateVariant.create().put(VariantSettings.MODEL, identifier5)).with(When.create().set(Properties.SOUTH, false), BlockStateVariant.create().put(VariantSettings.MODEL, identifier5).put(VariantSettings.Y, VariantSettings.Rotation.R90)).with(When.create().set(Properties.WEST, false), BlockStateVariant.create().put(VariantSettings.MODEL, identifier4).put(VariantSettings.Y, VariantSettings.Rotation.R270)));
	}
	private static void slabModelCommon(BlockStateModelGenerator bsmg, IBlockItemContainer container, Identifier slab, Identifier slabTop, Block baseBlock) { slabModelCommon(bsmg, container, slab, slabTop, getBlockModelId(baseBlock)); }
	private static void slabModelCommon(BlockStateModelGenerator bsmg, IBlockItemContainer container, Identifier slab, Identifier slabTop, Identifier baseBlock) {
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSlabBlockState(container.asBlock(), slab, slabTop, baseBlock));
		parentedItem(bsmg, container.asItem(), slab);
	}
	public static void slabModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer baseBlock) { slabModel(bsmg, container, baseBlock.asBlock()); }
	public static void slabModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block baseBlock, Texture textures) {
		Block block = container.asBlock();
		Identifier identifier = Models.SLAB.upload(block, textures, bsmg.modelCollector);
		Identifier identifier2 = Models.SLAB_TOP.upload(block, textures, bsmg.modelCollector);
		slabModelCommon(bsmg, container, identifier, identifier2, baseBlock);
	}
	public static void slabModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block baseBlock) { slabModel(bsmg, container, baseBlock, Texture.all(baseBlock)); }

	public static void copiedSlabModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer copy, IBlockItemContainer baseBlock) { copiedSlabModel(bsmg, container, copy, baseBlock.asBlock()); }
	public static void copiedSlabModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer copy, Block baseBlock) {
		Block copyBlock = copy.asBlock();
		Identifier identifier = Texture.getId(copyBlock);
		Identifier identifier2 = Texture.getSubId(copyBlock, "_top");
		slabModelCommon(bsmg, container, identifier, identifier2, baseBlock);
	}

	public static void logSlabModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block log) {
		topSideSlabModel(bsmg, container, log, Texture.getSubId(log, "_top"), Texture.getId(log));
	}
	public static void topSideSlabModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, BlockConvertible base) { topSideSlabModel(bsmg, container, base.asBlock()); }
	public static void topSideSlabModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block base) {
		topSideSlabModel(bsmg, container, base, Texture.getSubId(base, "_top"), Texture.getSubId(base, "_side"));
	}
	public static void topSideSlabModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block base, Identifier top, Identifier side) {
		slabModel(bsmg, container, base, new Texture().put(TextureKey.SIDE, side).put(TextureKey.TOP, top).put(TextureKey.BOTTOM, top));
	}
	private void glassSlabModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer baseBlock) { glassSlabModel(bsmg, container, baseBlock.asBlock()); }
	private void glassSlabModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block baseBlock) {
		Block block = container.asBlock();
		Texture textures = Texture.sideEnd(Texture.getSubId(block, "_side"), Texture.getId(baseBlock));
		Identifier identifier = Models.SLAB.upload(block, textures, bsmg.modelCollector);
		Identifier identifier2 = Models.SLAB_TOP.upload(block, textures, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSlabBlockState(block, identifier, identifier2, getBlockModelId(baseBlock)));
		parentedItem(bsmg, container.asItem(), identifier);
	}
	public static void stairsModelCommon(BlockStateModelGenerator bsmg, IBlockItemContainer container, Identifier inner, Identifier stairs, Identifier outer) {
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createStairsBlockState(container.asBlock(), inner, stairs, outer));
		parentedItem(bsmg, container.asItem(), stairs);
	}
	public static void stairsModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block baseBlock) {
		Block block = container.asBlock();
		Texture textures = Texture.all(baseBlock);
		Identifier inner = Models.INNER_STAIRS.upload(block, textures, bsmg.modelCollector);
		Identifier stairs = Models.STAIRS.upload(block, textures, bsmg.modelCollector);
		Identifier outer = Models.OUTER_STAIRS.upload(block, textures, bsmg.modelCollector);
		stairsModelCommon(bsmg, container, inner, stairs, outer);
	}
	public static void copiedStairsModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer copy) {
		Block copyBlock = copy.asBlock();
		Identifier inner = Texture.getSubId(copyBlock, "_inner");
		Identifier stairs = Texture.getId(copyBlock);
		Identifier outer = Texture.getSubId(copyBlock, "_outer");
		stairsModelCommon(bsmg, container, inner, stairs, outer);
	}
	public static void wallModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer baseBlock) { wallModel(bsmg, container, baseBlock.asBlock()); }
	public static void wallModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block baseBlock) { wallModel(bsmg, container, Texture.all(baseBlock)); }
	public static void wallModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Identifier texture) { wallModel(bsmg, container, Texture.all(texture)); }
	public static void wallModelCommon(BlockStateModelGenerator bsmg, IBlockItemContainer container, Identifier post, Identifier side, Identifier sideTall, Identifier inventory) {
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createWallBlockState(container.asBlock(), post, side, sideTall));
		parentedItem(bsmg, container.asItem(), inventory);
	}
	public static void wallModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Texture textures) {
		Block block = container.asBlock();
		Identifier post = Models.TEMPLATE_WALL_POST.upload(block, textures, bsmg.modelCollector);
		Identifier side = Models.TEMPLATE_WALL_SIDE.upload(block, textures, bsmg.modelCollector);
		Identifier sideTall = Models.TEMPLATE_WALL_SIDE_TALL.upload(block, textures, bsmg.modelCollector);
		Identifier inventory = Models.WALL_INVENTORY.upload(block, textures, bsmg.modelCollector);
		wallModelCommon(bsmg, container, post, side, sideTall, inventory);
	}
	public static void copiedWallModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer copy) {
		Identifier id = getBlockModelId(copy.asBlock());
		Identifier post = postfixPath(id, "_post");
		Identifier side = postfixPath(id, "_side");
		Identifier sideTall = postfixPath(id, "_side_tall");
		Identifier inventory = postfixPath(id, "_inventory");
		wallModelCommon(bsmg, container, post, side, sideTall, inventory);
	}
	public static void mushroomBlockModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) { bsmg.registerMushroomBlock(container.asBlock()); }
	public static void doorModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		Texture textureMap = Texture.topBottom(block);
		Identifier identifier = Models.DOOR_BOTTOM.upload(block, textureMap, bsmg.modelCollector);
		Identifier identifier2 = Models.DOOR_BOTTOM_RH.upload(block, textureMap, bsmg.modelCollector);
		Identifier identifier3 = Models.DOOR_TOP.upload(block, textureMap, bsmg.modelCollector);
		Identifier identifier4 = Models.DOOR_TOP_RH.upload(block, textureMap, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createDoorBlockState(block, identifier, identifier2, identifier3, identifier4));
		generatedItemModel(bsmg, container.asItem());
	}
	public static void copiedDoorModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer copy) {
		Block copyBlock = copy.asBlock();
		Identifier identifier = Texture.getSubId(copyBlock, "_bottom");
		Identifier identifier2 = Texture.getSubId(copyBlock, "_bottom_hinge");
		Identifier identifier3 = Texture.getSubId(copyBlock, "_top");
		Identifier identifier4 = Texture.getSubId(copyBlock, "_top_hinge");
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createDoorBlockState(container.asBlock(), identifier, identifier2, identifier3, identifier4));
		parentedItem(bsmg, container.asItem(), getItemModelId(copy.asItem()));
	}
	public static void trapdoorModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, boolean orientable) {
		Block block = container.asBlock();
		Texture textureMap = Texture.texture(block);
		Identifier identifier2;
		if (orientable) {
			Identifier identifier = Models.TEMPLATE_ORIENTABLE_TRAPDOOR_TOP.upload(block, textureMap, bsmg.modelCollector);
			identifier2 = Models.TEMPLATE_ORIENTABLE_TRAPDOOR_BOTTOM.upload(block, textureMap, bsmg.modelCollector);
			Identifier identifier3 = Models.TEMPLATE_ORIENTABLE_TRAPDOOR_OPEN.upload(block, textureMap, bsmg.modelCollector);
			bsmg.blockStateCollector.accept(BlockStateModelGenerator.createOrientableTrapdoorBlockState(block, identifier, identifier2, identifier3));
		}
		else {
			Identifier identifier = Models.TEMPLATE_TRAPDOOR_TOP.upload(block, textureMap, bsmg.modelCollector);
			identifier2 = Models.TEMPLATE_TRAPDOOR_BOTTOM.upload(block, textureMap, bsmg.modelCollector);
			Identifier identifier3 = Models.TEMPLATE_TRAPDOOR_OPEN.upload(block, textureMap, bsmg.modelCollector);
			bsmg.blockStateCollector.accept(BlockStateModelGenerator.createTrapdoorBlockState(block, identifier, identifier2, identifier3));
		}
		parentedItem(bsmg, container.asItem(), identifier2);
	}
	public static void thinTrapdoorModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		Texture textureMap = Texture.texture(block);
		Identifier identifier = ModModels.TEMPLATE_THIN_TRAPDOOR_TOP.upload(block, textureMap, bsmg.modelCollector);
		Identifier identifier2 = ModModels.TEMPLATE_THIN_TRAPDOOR_BOTTOM.upload(block, textureMap, bsmg.modelCollector);
		Identifier identifier3 = ModModels.TEMPLATE_THIN_TRAPDOOR_OPEN.upload(block, textureMap, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createTrapdoorBlockState(block, identifier, identifier2, identifier3));
		parentedItem(bsmg, container.asItem(), identifier2);
	}
	public static void copiedTrapdoorModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer copy, boolean orientable) { copiedTrapdoorModel(bsmg, container, copy.asBlock(), orientable); }
	public static void copiedTrapdoorModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block copy, boolean orientable) {
		Block block = container.asBlock();
		Identifier identifier = Texture.getSubId(copy, "_top");
		Identifier identifier2 = Texture.getSubId(copy, "_bottom");
		Identifier identifier3 = Texture.getSubId(copy, "_open");
		if (orientable) {
			bsmg.blockStateCollector.accept(BlockStateModelGenerator.createOrientableTrapdoorBlockState(block, identifier, identifier2, identifier3));
		}
		else {
			bsmg.blockStateCollector.accept(BlockStateModelGenerator.createTrapdoorBlockState(block, identifier, identifier2, identifier3));
		}
		parentedItem(bsmg, container.asItem(), identifier2);
	}
	public static void glassTrapdoorModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer top, IBlockItemContainer side) { glassTrapdoorModel(bsmg, container, top.asBlock(), side); }
	public static void glassTrapdoorModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block top, IBlockItemContainer side) { glassTrapdoorModel(bsmg, container, top, side.asBlock()); }
	public static void glassTrapdoorModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block top, Block side) {
		Block block = container.asBlock();
		Texture textureMap = new Texture().put(TextureKey.TOP, Texture.getId(top)).put(TextureKey.SIDE, postfixPath(Texture.getId(side), "_top"));
		Identifier identifier = ModModels.TEMPLATE_GLASS_TRAPDOOR_TOP.upload(block, textureMap, bsmg.modelCollector);
		Identifier identifier2 = ModModels.TEMPLATE_GLASS_TRAPDOOR_BOTTOM.upload(block, textureMap, bsmg.modelCollector);
		Identifier identifier3 = ModModels.TEMPLATE_GLASS_TRAPDOOR_OPEN.upload(block, textureMap, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createTrapdoorBlockState(block, identifier, identifier2, identifier3));
		parentedItem(bsmg, container.asItem(), identifier2);
	}
	public static void buttonModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Identifier unpressed, Identifier pressed, Identifier inventory) {
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createButtonBlockState(container.asBlock(), unpressed, pressed));
		parentedItem(bsmg, container.asItem(), inventory);
	}
	public static void buttonModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer baseBlock) { buttonModel(bsmg, container, baseBlock.asBlock()); }
	public static void buttonModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block baseBlock) {
		Block block = container.asBlock();
		Texture textures = Texture.all(baseBlock);
		Identifier unpressed = Models.BUTTON.upload(block, textures, bsmg.modelCollector);
		Identifier pressed = Models.BUTTON_PRESSED.upload(block, textures, bsmg.modelCollector);
		Identifier inventory = Models.BUTTON_INVENTORY.upload(block, textures, bsmg.modelCollector);
		buttonModel(bsmg, container, unpressed, pressed, inventory);
	}
	public static void copiedButtonModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer copy) {
		Identifier unpressed = getBlockModelId(copy.asBlock());
		Identifier pressed = postfixPath(unpressed, "_pressed");
		Identifier inventory = postfixPath(unpressed, "_inventory");
		buttonModel(bsmg, container, unpressed, pressed, inventory);
	}

	public static void bambooFenceModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		Texture textures = new Texture().put(TextureKey.TEXTURE, Texture.getId(block)).put(TextureKey.PARTICLE, Texture.getSubId(block, "_particle"));
		Identifier identifier = ModModels.CUSTOM_FENCE_POST.upload(block, textures, bsmg.modelCollector);
		Identifier identifier2 = ModModels.CUSTOM_FENCE_SIDE_NORTH.upload(block, textures, bsmg.modelCollector);
		Identifier identifier3 = ModModels.CUSTOM_FENCE_SIDE_EAST.upload(block, textures, bsmg.modelCollector);
		Identifier identifier4 = ModModels.CUSTOM_FENCE_SIDE_SOUTH.upload(block, textures, bsmg.modelCollector);
		Identifier identifier5 = ModModels.CUSTOM_FENCE_SIDE_WEST.upload(block, textures, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(MultipartBlockStateSupplier.create(block)
				.with(BlockStateVariant.create()
						.put(VariantSettings.MODEL, identifier))
				.with(When.create().set(Properties.NORTH, true), BlockStateVariant.create()
						.put(VariantSettings.MODEL, identifier2)
						.put(VariantSettings.UVLOCK, true))
				.with(When.create().set(Properties.EAST, true), BlockStateVariant.create()
						.put(VariantSettings.MODEL, identifier3)
						.put(VariantSettings.UVLOCK, true))
				.with(When.create().set(Properties.SOUTH, true), BlockStateVariant.create()
						.put(VariantSettings.MODEL, identifier4)
						.put(VariantSettings.UVLOCK, true))
				.with(When.create().set(Properties.WEST, true), BlockStateVariant.create()
						.put(VariantSettings.MODEL, identifier5)
						.put(VariantSettings.UVLOCK, true)));
		parentedItem(bsmg, container.asItem(), ModModels.CUSTOM_FENCE_INVENTORY.upload(block, textures, bsmg.modelCollector));
	}

	public static void bambooFenceGateModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		Texture textures = new Texture().put(TextureKey.TEXTURE, Texture.getId(block)).put(TextureKey.PARTICLE, Texture.getSubId(block, "_particle"));
		Identifier identifier = ModModels.TEMPLATE_CUSTOM_FENCE_GATE_OPEN.upload(block, textures, bsmg.modelCollector);
		Identifier identifier2 = ModModels.TEMPLATE_CUSTOM_FENCE_GATE.upload(block, textures, bsmg.modelCollector);
		Identifier identifier3 = ModModels.TEMPLATE_CUSTOM_FENCE_GATE_WALL_OPEN.upload(block, textures, bsmg.modelCollector);
		Identifier identifier4 = ModModels.TEMPLATE_CUSTOM_FENCE_GATE_WALL.upload(block, textures, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createFenceGateBlockState(block, identifier, identifier2, identifier3, identifier4));
		parentedItem(bsmg, container.asItem(), identifier2);
	}

	public static void fenceModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer baseBlock) { fenceModel(bsmg, container, baseBlock.asBlock()); }
	public static void fenceModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block baseBlock) {
		Block block = container.asBlock();
		Texture textures = Texture.all(baseBlock);
		Identifier identifier = Models.FENCE_POST.upload(block, textures, bsmg.modelCollector);
		Identifier identifier2 = Models.FENCE_SIDE.upload(block, textures, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createFenceBlockState(block, identifier, identifier2));
		parentedItem(bsmg, container.asItem(), Models.FENCE_INVENTORY.upload(block, textures, bsmg.modelCollector));
	}
	public static void fenceGateModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer baseBlock) { fenceGateModel(bsmg, container, baseBlock.asBlock()); }
	public static void fenceGateModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block baseBlock) {
		Block block = container.asBlock();
		Texture textures = Texture.all(baseBlock);
		Identifier identifier = Models.TEMPLATE_FENCE_GATE_OPEN.upload(block, textures, bsmg.modelCollector);
		Identifier identifier2 = Models.TEMPLATE_FENCE_GATE.upload(block, textures, bsmg.modelCollector);
		Identifier identifier3 = Models.TEMPLATE_FENCE_GATE_WALL_OPEN.upload(block, textures, bsmg.modelCollector);
		Identifier identifier4 = Models.TEMPLATE_FENCE_GATE_WALL.upload(block, textures, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createFenceGateBlockState(block, identifier, identifier2, identifier3, identifier4));
		parentedItem(bsmg, container.asItem(), identifier2);
	}
	public static void pressurePlateModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block baseBlock) {
		Block block = container.asBlock();
		Texture textures = Texture.all(baseBlock);
		Identifier identifier = Models.PRESSURE_PLATE_UP.upload(block, textures, bsmg.modelCollector);
		Identifier identifier2 = Models.PRESSURE_PLATE_DOWN.upload(block, textures, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createPressurePlateBlockState(block, identifier, identifier2));
		parentedItem(bsmg, container.asItem(), identifier);
	}
	public static void weightedPressurePlateModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Identifier up, Identifier down) {
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(container.asBlock()).coordinate(BlockStateModelGenerator.createValueFencedModelMap(Properties.POWER, 1, down, up)));
		parentedItem(bsmg, container.asItem(), up);
	}
	public static void weightedPressurePlateModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block baseBlock) {
		Block block = container.asBlock();
		Texture textures = Texture.all(baseBlock);
		Identifier up = Models.PRESSURE_PLATE_UP.upload(block, textures, bsmg.modelCollector);
		Identifier down = Models.PRESSURE_PLATE_DOWN.upload(block, textures, bsmg.modelCollector);
		weightedPressurePlateModel(bsmg, container, up, down);
	}
	public static void copiedWeightedPressurePlateModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer useModels) {
		Identifier up = ModelIds.getBlockModelId(useModels.asBlock());
		Identifier down = postfixPath(ModelIds.getBlockModelId(useModels.asBlock()), "_down");
		weightedPressurePlateModel(bsmg, container, up, down);
	}
	public static void signModel(BlockStateModelGenerator bsmg, WallBlockContainer container, Block baseBlock) {
		Block block = container.asBlock(), wallBlock = container.getWallBlock();
		Identifier identifier = Models.PARTICLE.upload(block, Texture.all(baseBlock), bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, identifier));
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(wallBlock, identifier));
		bsmg.excludeFromSimpleItemModelGeneration(wallBlock);
		generatedItemModel(bsmg, container.asItem());
	}

	public static void hangingSignModel(BlockStateModelGenerator bsmg, Block particle, WallBlockContainer hangingSign) {
		Texture textureMap = Texture.particle(particle);
		Identifier identifier = Models.PARTICLE.upload(hangingSign.asBlock(), textureMap, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(hangingSign.asBlock(), identifier));
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(hangingSign.getWallBlock(), identifier));
		bsmg.excludeFromSimpleItemModelGeneration(hangingSign.getWallBlock());
		generatedItemModel(bsmg, hangingSign.asItem());
	}

	private static void statueModelCommon(BlockStateModelGenerator bsmg, Identifier model, Block block, Item item) {
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(block)
				.coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_FACING)
						.register(Direction.EAST, BlockStateVariant.create()
								.put(VariantSettings.MODEL, model).put(VariantSettings.Y, VariantSettings.Rotation.R90))
						.register(Direction.SOUTH, BlockStateVariant.create()
								.put(VariantSettings.MODEL, model).put(VariantSettings.Y, VariantSettings.Rotation.R180))
						.register(Direction.WEST, BlockStateVariant.create()
								.put(VariantSettings.MODEL, model).put(VariantSettings.Y, VariantSettings.Rotation.R270))
						.register(Direction.NORTH, BlockStateVariant.create()
								.put(VariantSettings.MODEL, model))));
		parentedItem(bsmg, item, model);
	}
	private static void explicitStatueModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		statueModelCommon(bsmg, getBlockModelId(block), block, container.asItem());
	}
	private static void plushieModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		if (!(container.asBlock() instanceof PlushieBlock block)) throw new IllegalArgumentException("Block must be of type PlushieBlock");
		Identifier model = block.getModel().upload(block, new Texture().put(TextureKey.ALL, Texture.getId(block))
				.put(TextureKey.PARTICLE, Texture.getId(block)), bsmg.modelCollector);
		statueModelCommon(bsmg, model, block, container.asItem());
	}

	private static void torchModelCommon(BlockStateModelGenerator bsmg, TorchContainer container, Identifier lit, Identifier litWall, Identifier unlit, Identifier unlitWall, Model template, Model templateWall, ItemConvertible parentItem) {
		Block block = container.asBlock();
		Texture textures = Texture.torch(block), unlitTextures = new Texture().put(TextureKey.TORCH, getBlockModelId("unlit_", block));
		Identifier litModel = lit != null ? lit : template.upload(block, textures, bsmg.modelCollector);
		Identifier unlitModel = unlit != null ? unlit : template.upload(getBlockModelId("unlit_", block), unlitTextures, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(block).coordinate(BlockStateModelGenerator.createBooleanModelMap(Properties.LIT, litModel, unlitModel)));
		Block wallBlock = container.getWallBlock();
		litModel = litWall != null ? litWall : templateWall.upload(wallBlock, textures, bsmg.modelCollector);
		unlitModel = unlitWall != null ? unlitWall : templateWall.upload(getBlockModelId("unlit_", wallBlock), unlitTextures, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(wallBlock)
				.coordinate(BlockStateVariantMap.create(Properties.LIT, Properties.HORIZONTAL_FACING)
						.register(true, Direction.EAST, BlockStateVariant.create()
								.put(VariantSettings.MODEL, litModel))
						.register(true, Direction.SOUTH, BlockStateVariant.create()
								.put(VariantSettings.MODEL, litModel).put(VariantSettings.Y, VariantSettings.Rotation.R90))
						.register(true, Direction.WEST, BlockStateVariant.create()
								.put(VariantSettings.MODEL, litModel).put(VariantSettings.Y, VariantSettings.Rotation.R180))
						.register(true, Direction.NORTH, BlockStateVariant.create()
								.put(VariantSettings.MODEL, litModel).put(VariantSettings.Y, VariantSettings.Rotation.R270))
						.register(false, Direction.EAST, BlockStateVariant.create()
								.put(VariantSettings.MODEL, unlitModel))
						.register(false, Direction.SOUTH, BlockStateVariant.create()
								.put(VariantSettings.MODEL, unlitModel).put(VariantSettings.Y, VariantSettings.Rotation.R90))
						.register(false, Direction.WEST, BlockStateVariant.create()
								.put(VariantSettings.MODEL, unlitModel).put(VariantSettings.Y, VariantSettings.Rotation.R180))
						.register(false, Direction.NORTH, BlockStateVariant.create()
								.put(VariantSettings.MODEL, unlitModel).put(VariantSettings.Y, VariantSettings.Rotation.R270))));
		bsmg.excludeFromSimpleItemModelGeneration(wallBlock);
		if (parentItem == null) generatedItemModel(bsmg, container.asItem(), block);
		else parentedItem(bsmg, container.asItem(), parentItem);
	}
	private static void torchModelCommon(BlockStateModelGenerator bsmg, TorchContainer container, Identifier unlit, Identifier unlitWall, Model template, Model templateWall) {
		torchModelCommon(bsmg, container, null,  null, unlit, unlitWall, template, templateWall, null);
	}
	private static void torchModelCommon(BlockStateModelGenerator bsmg, TorchContainer container, Identifier unlit, Identifier unlitWall) {
		torchModelCommon(bsmg, container, unlit, unlitWall, Models.TEMPLATE_TORCH, Models.TEMPLATE_TORCH_WALL);
	}
	public static void torchModel(BlockStateModelGenerator bsmg, TorchContainer container) { torchModelCommon(bsmg, container, null, null); }
	public static void torchModel(BlockStateModelGenerator bsmg, TorchContainer container, UnlitTorchContainer unlit) {
		torchModelCommon(bsmg, container, getBlockModelId(unlit.asBlock()), getBlockModelId(unlit.getWallBlock()));
	}
	public static void torchModel(BlockStateModelGenerator bsmg, TorchContainer container, TorchContainer base) {
		torchModelCommon(bsmg, container, getBlockModelId("unlit_", base.asBlock()), getBlockModelId("unlit_", base.getWallBlock()));
	}
	public static void copiedTorchModel(BlockStateModelGenerator bsmg, TorchContainer container, TorchContainer looksLike) {
		torchModelCommon(bsmg, container, getBlockModelId(looksLike.asBlock()), getBlockModelId(looksLike.getWallBlock()), getBlockModelId("unlit_", looksLike.asBlock()), getBlockModelId("unlit_", looksLike.getWallBlock()), Models.TEMPLATE_TORCH, Models.TEMPLATE_TORCH_WALL, looksLike);
	}
	private static void unlitTorchModelCommon(BlockStateModelGenerator bsmg, Block block, Block wallBlock, Identifier identifier, Identifier identifier2) {
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, identifier));
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(wallBlock, BlockStateVariant.create().put(VariantSettings.MODEL, identifier2)).coordinate(BlockStateModelGenerator.createEastDefaultHorizontalRotationStates()));
	}
	private static void unlitTorchModel(BlockStateModelGenerator bsmg, UnlitTorchContainer container) {
		Block block = container.asBlock(), wallBlock = container.getWallBlock();
		Texture textures = Texture.torch(block);
		Identifier identifier = Models.TEMPLATE_TORCH.upload(block, textures, bsmg.modelCollector);
		Identifier identifier2 = Models.TEMPLATE_TORCH_WALL.upload(wallBlock, textures, bsmg.modelCollector);
		unlitTorchModelCommon(bsmg, block, wallBlock, identifier, identifier2);
	}

	private static void boneTorchModelCommon(BlockStateModelGenerator bsmg, TorchContainer container, Identifier unlit, Identifier unlitWall) {
		torchModelCommon(bsmg, container, unlit, unlitWall, ModModels.TEMPLATE_BONE_TORCH, ModModels.TEMPLATE_BONE_TORCH_WALL);
	}
	public static void boneTorchModel(BlockStateModelGenerator bsmg, TorchContainer container) { boneTorchModelCommon(bsmg, container, null, null); }
	public static void boneTorchModel(BlockStateModelGenerator bsmg, TorchContainer container, TorchContainer base) {
		boneTorchModelCommon(bsmg, container, getBlockModelId("unlit_", base.asBlock()), getBlockModelId("unlit_", base.getWallBlock()));
	}

	public static void thickTorchModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Identifier unlitModel = getBlockModelId("unlit_", container.asBlock());
		unlitModel = ModModels.TEMPLATE_THICK_TORCH.upload(unlitModel, Texture.all(unlitModel), bsmg.modelCollector);
		thickTorchModel(bsmg, container, unlitModel);
	}
	public static void thickTorchModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Identifier unlitModel) {
		Block block = container.asBlock();
		Identifier litModel = ModModels.TEMPLATE_THICK_TORCH.upload(block, Texture.all(block), bsmg.modelCollector);
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(block).coordinate(BlockStateModelGenerator.createBooleanModelMap(Properties.LIT, litModel, unlitModel)));
		generatedItemModel(bsmg, container.asItem());
	}
	public static void postModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		Identifier model = ModModels.TEMPLATE_POST.upload(block, Texture.all(block), bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, model));
		parentedItem(bsmg, container.asItem(), block);
	}

	public static void lanternModelCommon(BlockStateModelGenerator bsmg, IBlockItemContainer container, Identifier lit, Identifier litHanging, Identifier unlit, Identifier unlitHanging) {
		Block block = container.asBlock();
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(block)
				.coordinate(BlockStateVariantMap.create(Properties.LIT, Properties.HANGING)
						.register(true, true, BlockStateVariant.create().put(VariantSettings.MODEL, litHanging))
						.register(true, false, BlockStateVariant.create().put(VariantSettings.MODEL, lit))
						.register(false, true, BlockStateVariant.create().put(VariantSettings.MODEL, unlitHanging))
						.register(false, false, BlockStateVariant.create().put(VariantSettings.MODEL, unlit))));
	}
	public static Texture unlitLanternTexture(Block block) {
		return new Texture().put(TextureKey.LANTERN, getBlockModelId("unlit_", block));
	}
	public static void lanternModelCommon(BlockStateModelGenerator bsmg, IBlockItemContainer container, Identifier unlit) {
		Block block = container.asBlock();
		Identifier litModel = TexturedModel.TEMPLATE_LANTERN.upload(block, bsmg.modelCollector);
		Identifier litHanging = TexturedModel.TEMPLATE_HANGING_LANTERN.upload(block, bsmg.modelCollector);
		Identifier unlitModel = unlit != null ? unlit : Models.TEMPLATE_LANTERN.upload(getBlockModelId("unlit_", block), unlitLanternTexture(block), bsmg.modelCollector);
		Identifier unlitHanging = unlit != null ? postfixPath(unlit, "_hanging") : Models.TEMPLATE_HANGING_LANTERN.upload(getBlockModelId("unlit_", block, "_hanging"), unlitLanternTexture(block), bsmg.modelCollector);
		lanternModelCommon(bsmg, container, litModel, litHanging, unlitModel, unlitHanging);
		generatedItemModel(bsmg, container.asItem());
	}
	public static void lanternModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) { lanternModelCommon(bsmg, container, null); }
	public static void lanternModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer base) { lanternModel(bsmg, container, base.asBlock()); }
	public static void lanternModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block base) {
		Identifier id = getBlockModelId(base);
		if (!id.getPath().startsWith("block/unlit_")) id = getBlockModelId("unlit_", base);
		lanternModelCommon(bsmg, container, id);
	}
	public static void copiedLanternModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer copy) { copiedLanternModel(bsmg, container, copy, copy); }
	public static void copiedLanternModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer copy, IBlockItemContainer unlit) {
		Identifier litModel = getBlockModelId(copy.asBlock());
		Identifier litHanging = postfixPath(litModel, "_hanging");
		Identifier unlitModel = getBlockModelId("unlit_", unlit.asBlock());
		Identifier unlitHanging = postfixPath(unlitModel, "_hanging");
		lanternModelCommon(bsmg, container, litModel, litHanging, unlitModel, unlitHanging);
		parentedItem(bsmg, container.asItem(), copy);
	}
	private static void unlitLanternModelCommon(BlockStateModelGenerator bsmg, Block block, Identifier identifier, Identifier identifier2) {
		bsmg.excludeFromSimpleItemModelGeneration(block);
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(block).coordinate(BlockStateModelGenerator.createBooleanModelMap(Properties.HANGING, identifier2, identifier)));
	}
	public static void unlitLanternModel(BlockStateModelGenerator bsmg, Block block) {
		Identifier identifier = TexturedModel.TEMPLATE_LANTERN.upload(block, bsmg.modelCollector);
		Identifier identifier2 = TexturedModel.TEMPLATE_HANGING_LANTERN.upload(block, bsmg.modelCollector);
		unlitLanternModelCommon(bsmg, block, identifier, identifier2);
	}
	public static void unlitLanternModel(BlockStateModelGenerator bsmg, Block block, Block copyModel) {
		Identifier identifier = getBlockModelId(copyModel);
		unlitLanternModelCommon(bsmg, block, identifier, postfixPath(identifier, "_hanging"));
	}
	public static void explicitLanternModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		Identifier identifier = getBlockModelId(block);
		unlitLanternModelCommon(bsmg, block, identifier, postfixPath(identifier, "_hanging"));
		generatedItemModel(bsmg, container.asItem());
	}
	private static void slimeLanternModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Model model) {
		Block block = container.asBlock();
		Identifier identifier = model.upload(block, Texture.texture(block), bsmg.modelCollector);
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(block, BlockStateVariant.create().put(VariantSettings.MODEL, identifier)).coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
		parentedItem(bsmg, container.asItem(), block);
	}
	private static final Identifier CAMPFIRE_FIRE = ModId.ID("minecraft:block/campfire_fire");
	private static final Identifier SOUL_CAMPFIRE_FIRE = ModId.ID("minecraft:block/soul_campfire_fire");
	private static final Identifier ENDER_CAMPFIRE_FIRE = ModId.ID("block/ender_campfire_fire");
	private static void campfireModelCommon(BlockStateModelGenerator bsmg, IBlockItemContainer container, Identifier log, Identifier off, int fire) {
		Block block = container.asBlock();
		Texture textureMap = new Texture()
				.put(ModTextureKeys.LOG, log)
				.put(TextureKey.LIT_LOG, Texture.getSubId(block, "_log_lit"));
		textureMap.put(TextureKey.FIRE, switch (fire) {
			case 1 -> SOUL_CAMPFIRE_FIRE;
			case 2 -> ENDER_CAMPFIRE_FIRE;
			default -> CAMPFIRE_FIRE;
		});
		Identifier on = ModModels.TEMPLATE_CAMPFIRE.upload(block, textureMap, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(block).coordinate(BlockStateModelGenerator.createBooleanModelMap(Properties.LIT, on, off)).coordinate(BlockStateModelGenerator.createSouthDefaultHorizontalRotationStates()));
		generatedItemModel(bsmg, container.asItem());
	}
	public static void campfireModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, int fire) {
		Block block = container.asBlock();
		Texture textures = new Texture().put(ModTextureKeys.LOG, Texture.getSubId(block, "_log"));
		Identifier off = ModModels.TEMPLATE_CAMPFIRE_OFF.upload(block, textures, bsmg.modelCollector);
		campfireModelCommon(bsmg, container, Texture.getSubId(block, "_log"), off, fire);
	}
	public static void campfireModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block base, int fire) {
		campfireModelCommon(bsmg, container, Texture.getSubId(base, "_log"), Texture.getSubId(base, "_off"), fire);
	}
	public static void candleModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block cake) {
		Block candle = container.asBlock();
		generatedItemModel(bsmg, container.asItem());
		Texture textureMap = Texture.all(Texture.getId(candle));
		Texture textureMap2 = Texture.all(Texture.getSubId(candle, "_lit"));
		Identifier identifier = Models.TEMPLATE_CANDLE.upload(candle, "_one_candle", textureMap, bsmg.modelCollector);
		Identifier identifier2 = Models.TEMPLATE_TWO_CANDLES.upload(candle, "_two_candles", textureMap, bsmg.modelCollector);
		Identifier identifier3 = Models.TEMPLATE_THREE_CANDLES.upload(candle, "_three_candles", textureMap, bsmg.modelCollector);
		Identifier identifier4 = Models.TEMPLATE_FOUR_CANDLES.upload(candle, "_four_candles", textureMap, bsmg.modelCollector);
		Identifier identifier5 = Models.TEMPLATE_CANDLE.upload(candle, "_one_candle_lit", textureMap2, bsmg.modelCollector);
		Identifier identifier6 = Models.TEMPLATE_TWO_CANDLES.upload(candle, "_two_candles_lit", textureMap2, bsmg.modelCollector);
		Identifier identifier7 = Models.TEMPLATE_THREE_CANDLES.upload(candle, "_three_candles_lit", textureMap2, bsmg.modelCollector);
		Identifier identifier8 = Models.TEMPLATE_FOUR_CANDLES.upload(candle, "_four_candles_lit", textureMap2, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(candle)
				.coordinate(BlockStateVariantMap.create(Properties.CANDLES, Properties.LIT)
						.register(1, false, BlockStateVariant.create().put(VariantSettings.MODEL, identifier))
						.register(2, false, BlockStateVariant.create().put(VariantSettings.MODEL, identifier2))
						.register(3, false, BlockStateVariant.create().put(VariantSettings.MODEL, identifier3))
						.register(4, false, BlockStateVariant.create().put(VariantSettings.MODEL, identifier4))
						.register(1, true, BlockStateVariant.create().put(VariantSettings.MODEL, identifier5))
						.register(2, true, BlockStateVariant.create().put(VariantSettings.MODEL, identifier6))
						.register(3, true, BlockStateVariant.create().put(VariantSettings.MODEL, identifier7))
						.register(4, true, BlockStateVariant.create().put(VariantSettings.MODEL, identifier8))));
		Identifier identifier9 = Models.TEMPLATE_CAKE_WITH_CANDLE.upload(cake, Texture.candleCake(candle, false), bsmg.modelCollector);
		Identifier identifier10 = Models.TEMPLATE_CAKE_WITH_CANDLE.upload(cake, "_lit", Texture.candleCake(candle, true), bsmg.modelCollector);
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(cake).coordinate(BlockStateModelGenerator.createBooleanModelMap(Properties.LIT, identifier10, identifier9)));
	}
	public static Texture cakeTexture(Block cake, boolean slice) {
		Texture textureMap = new Texture()
				.put(TextureKey.PARTICLE, Texture.getSubId(cake, "_side"))
				.put(TextureKey.BOTTOM, Texture.getSubId(cake, "_bottom"))
				.put(TextureKey.TOP, Texture.getSubId(cake, "_top"))
				.put(TextureKey.SIDE, Texture.getSubId(cake, "_side"));
		if (slice) textureMap.put(TextureKey.INSIDE, Texture.getSubId(cake, "_inner"));
		return textureMap;
	}
	private static Texture candleCakeTexture(Block cake, Block candle, boolean lit) {
		return cakeTexture(cake, false).put(TextureKey.CANDLE, Texture.getSubId(candle, lit ? "_lit" : ""));
	}
	public static void candleCakeModel(BlockStateModelGenerator bsmg, Block candle, Block cake, Block candleCake) {
		Identifier identifier9 = Models.TEMPLATE_CAKE_WITH_CANDLE.upload(candleCake, candleCakeTexture(cake, candle, false), bsmg.modelCollector);
		Identifier identifier10 = Models.TEMPLATE_CAKE_WITH_CANDLE.upload(candleCake, "_lit", candleCakeTexture(cake, candle, true), bsmg.modelCollector);
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(candleCake).coordinate(BlockStateModelGenerator.createBooleanModelMap(Properties.LIT, identifier10, identifier9)));
	}
	private static void registerCake(BlockStateModelGenerator bsmg, Block cake, Item item) {
		bsmg.registerItemModel(item);
		Texture full = cakeTexture(cake, false), slices = cakeTexture(cake, true);
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(cake)
				.coordinate(BlockStateVariantMap.create(Properties.BITES)
						.register(0, BlockStateVariant.create()
								.put(VariantSettings.MODEL, ModModels.TEMPLATE_CAKE.upload(cake, full, bsmg.modelCollector)))
						.register(1, BlockStateVariant.create()
								.put(VariantSettings.MODEL, ModModels.TEMPLATE_CAKE_SLICE1.upload(cake, slices, bsmg.modelCollector)))
						.register(2, BlockStateVariant.create()
								.put(VariantSettings.MODEL, ModModels.TEMPLATE_CAKE_SLICE2.upload(cake, slices, bsmg.modelCollector)))
						.register(3, BlockStateVariant.create()
								.put(VariantSettings.MODEL, ModModels.TEMPLATE_CAKE_SLICE3.upload(cake, slices, bsmg.modelCollector)))
						.register(4, BlockStateVariant.create()
								.put(VariantSettings.MODEL, ModModels.TEMPLATE_CAKE_SLICE4.upload(cake, slices, bsmg.modelCollector)))
						.register(5, BlockStateVariant.create()
								.put(VariantSettings.MODEL, ModModels.TEMPLATE_CAKE_SLICE5.upload(cake, slices, bsmg.modelCollector)))
						.register(6, BlockStateVariant.create()
								.put(VariantSettings.MODEL, ModModels.TEMPLATE_CAKE_SLICE6.upload(cake, slices, bsmg.modelCollector)))));
	}
	public static void cakeModels(BlockStateModelGenerator bsmg, CakeContainer container) {
		Block cake = container.CAKE.asBlock();
		registerCake(bsmg, cake, container.CAKE.asItem());
		candleCakeModel(bsmg, Blocks.CANDLE, cake, container.CANDLE_CAKE);
		candleCakeModel(bsmg, SOUL_CANDLE.asBlock(), cake, container.SOUL_CANDLE_CAKE);
		candleCakeModel(bsmg, ENDER_CANDLE.asBlock(), cake, container.ENDER_CANDLE_CAKE);
		candleCakeModel(bsmg, NETHERRACK_CANDLE.asBlock(), cake, container.NETHERRACK_CANDLE_CAKE);
		for (DyeColor color : DyeColor.values()) {
			candleCakeModel(bsmg, ColorUtil.GetCandleBlock(color), cake, container.CANDLE_CAKES.get(color));
		}
		candleCakeModel(bsmg, BEIGE_CANDLE.asBlock(), cake, container.BEIGE_CANDLE_CAKE);
		candleCakeModel(bsmg, BURGUNDY_CANDLE.asBlock(), cake, container.BURGUNDY_CANDLE_CAKE);
		candleCakeModel(bsmg, LAVENDER_CANDLE.asBlock(), cake, container.LAVENDER_CANDLE_CAKE);
		candleCakeModel(bsmg, MINT_CANDLE.asBlock(), cake, container.MINT_CANDLE_CAKE);
	}
	private static void barsModelCommon(BlockStateModelGenerator bsmg, Block block, Identifier ends, Identifier post, Identifier cap, Identifier capAlt, Identifier side, Identifier sideAlt) {
		bsmg.blockStateCollector.accept(MultipartBlockStateSupplier.create(block)
				.with(BlockStateVariant.create().put(VariantSettings.MODEL, ends))
				.with(When.create().set(Properties.NORTH, false).set(Properties.EAST, false).set(Properties.SOUTH, false).set(Properties.WEST, false),
						BlockStateVariant.create().put(VariantSettings.MODEL, post))
				.with(When.create().set(Properties.NORTH, true).set(Properties.EAST, false).set(Properties.SOUTH, false).set(Properties.WEST, false),
						BlockStateVariant.create().put(VariantSettings.MODEL, cap))
				.with(When.create().set(Properties.NORTH, false).set(Properties.EAST, true).set(Properties.SOUTH, false).set(Properties.WEST, false),
						BlockStateVariant.create().put(VariantSettings.MODEL, cap).put(VariantSettings.Y, VariantSettings.Rotation.R90))
				.with(When.create().set(Properties.NORTH, false).set(Properties.EAST, false).set(Properties.SOUTH, true).set(Properties.WEST, false),
						BlockStateVariant.create().put(VariantSettings.MODEL, capAlt))
				.with(When.create().set(Properties.NORTH, false).set(Properties.EAST, false).set(Properties.SOUTH, false).set(Properties.WEST, true),
						BlockStateVariant.create().put(VariantSettings.MODEL, capAlt).put(VariantSettings.Y, VariantSettings.Rotation.R90))
				.with(When.create().set(Properties.NORTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, side))
				.with(When.create().set(Properties.EAST, true), BlockStateVariant.create().put(VariantSettings.MODEL, side).put(VariantSettings.Y, VariantSettings.Rotation.R90))
				.with(When.create().set(Properties.SOUTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, sideAlt)).with(When.create().set(Properties.WEST, true),
						BlockStateVariant.create().put(VariantSettings.MODEL, sideAlt).put(VariantSettings.Y, VariantSettings.Rotation.R90)));
	}
	public static void barsModelBlockOnly(BlockStateModelGenerator bsmg, Block block) {
		Identifier texture = Texture.getId(block);
		Texture textureMap = new Texture().put(TextureKey.PARTICLE, texture).put(ModTextureKeys.BARS, texture).put(TextureKey.EDGE, texture);
		Identifier ends = ModModels.TEMPLATE_BARS_POST_ENDS.upload(block, textureMap, bsmg.modelCollector);
		Identifier post = ModModels.TEMPLATE_BARS_POST.upload(block, textureMap, bsmg.modelCollector);
		Identifier cap = ModModels.TEMPLATE_BARS_CAP.upload(block, textureMap, bsmg.modelCollector);
		Identifier capAlt = ModModels.TEMPLATE_BARS_CAP_ALT.upload(block, textureMap, bsmg.modelCollector);
		Identifier side = ModModels.TEMPLATE_BARS_SIDE.upload(block, textureMap, bsmg.modelCollector);
		Identifier sideAlt = ModModels.TEMPLATE_BARS_SIDE_ALT.upload(block, textureMap, bsmg.modelCollector);
		barsModelCommon(bsmg, block, ends, post, cap, capAlt, side, sideAlt);
	}
	public static void barsModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		barsModelBlockOnly(bsmg, block);
		generatedItemModel(bsmg, container.asItem(), block);
	}
	public static void copiedBarsModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer copy) {
		Identifier copyId = getBlockModelId(copy.asBlock());
		Identifier ends = postfixPath(copyId, "_post_ends");
		Identifier post = postfixPath(copyId, "_post");
		Identifier cap = postfixPath(copyId, "_cap");
		Identifier capAlt = postfixPath(copyId, "_cap_alt");
		Identifier side = postfixPath(copyId, "_side");
		Identifier sideAlt = postfixPath(copyId, "_side_alt");
		barsModelCommon(bsmg, container.asBlock(), ends, post, cap, capAlt, side, sideAlt);
		parentedItem(bsmg, container.asItem(), copy);
	}
	public static void sculkTurfModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block bottom) { topBottomSidesModel(bsmg, container, SCULK.asBlock(), bottom); }
	public static void sculkTurfModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Identifier bottom) { topBottomSidesModel(bsmg, container, SCULK.asBlock(), bottom); }
	public static void topBottomSidesModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block top, Block bottom) { topBottomSidesModel(bsmg, container, top, Texture.getId(bottom)); }
	public static void topBottomSidesModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block top, Identifier bottom) {
		Block block = container.asBlock();
		Texture textureMap = new Texture()
				.put(TextureKey.BOTTOM, bottom)
				.put(TextureKey.TOP, Texture.getId(top))
				.put(TextureKey.SIDE, Texture.getSubId(block, "_side"));
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, Models.CUBE_BOTTOM_TOP.upload(block, textureMap, bsmg.modelCollector)));
		parentedItem(bsmg, container.asItem(), block);
	}
	public static void topBottomSidesModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		Texture textureMap = new Texture()
				.put(TextureKey.BOTTOM, Texture.getSubId(block, "_bottom"))
				.put(TextureKey.TOP, Texture.getSubId(block, "_top"))
				.put(TextureKey.SIDE, Texture.getSubId(block, "_side"));
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, Models.CUBE_BOTTOM_TOP.upload(block, textureMap, bsmg.modelCollector)));
		parentedItem(bsmg, container.asItem(), block);
	}
	public static Texture uniqueSidesTexture(Identifier base, String particle, String up, String down, String north, String south, String east, String west) {
		return new Texture()
				.put(TextureKey.PARTICLE, postfixPath(base, particle))
				.put(TextureKey.UP, postfixPath(base, up))
				.put(TextureKey.DOWN, postfixPath(base, down))
				.put(TextureKey.NORTH, postfixPath(base, north))
				.put(TextureKey.SOUTH, postfixPath(base, south))
				.put(TextureKey.EAST, postfixPath(base, east))
				.put(TextureKey.WEST, postfixPath(base, west));
	}
	public static void chainModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Identifier model) {
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(container.asBlock(),
						BlockStateVariant.create().put(VariantSettings.MODEL, model))
				.coordinate(BlockStateModelGenerator.createAxisRotatedVariantMap()));
		generatedItemModel(bsmg, container.asItem());
	}
	public static void chainModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		chainModel(bsmg, container, ModModels.TEMPLATE_CHAIN.upload(block, Texture.all(block), bsmg.modelCollector));
	}
	public static void copiedChainModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer copy) {
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(container.asBlock(),
						BlockStateVariant.create().put(VariantSettings.MODEL, getBlockModelId(copy.asBlock())))
				.coordinate(BlockStateModelGenerator.createAxisRotatedVariantMap()));
		parentedItem(bsmg, container.asItem(), copy);
	}
	public static void heavyChainModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		chainModel(bsmg, container, ModModels.TEMPLATE_HEAVY_CHAIN.upload(block, Texture.all(block), bsmg.modelCollector));
	}
	private static void metalPillarModelCommon(BlockStateModelGenerator bsmg, IBlockItemContainer container, Identifier pillar, Identifier horizontal) {
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createAxisRotatedBlockState(container.asBlock(), pillar, horizontal));
		parentedItem(bsmg, container.asItem(), pillar);
	}
	public static void metalPillarModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer nonPillar) { metalPillarModel(bsmg, container,  nonPillar.asBlock()); }
	public static void metalPillarModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block nonPillar) {
		Block block = container.asBlock();
		Identifier end = Texture.getId(nonPillar), side = Texture.getId(block);
		Identifier pillar = Models.CUBE_COLUMN.upload(block, Texture.sideEnd(side, end), bsmg.modelCollector);
		Identifier horizontal = Models.CUBE_COLUMN_HORIZONTAL.upload(block, Texture.sideEnd(postfixPath(side, "_horizontal"), end), bsmg.modelCollector);
		metalPillarModelCommon(bsmg, container, pillar, horizontal);
	}
	public static void copiedMetalPillarModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer copy) {
		Block copyBlock = copy.asBlock();
		Identifier pillar = Texture.getId(copyBlock);
		Identifier horizontal = Texture.getSubId(copyBlock, "_horizontal");
		metalPillarModelCommon(bsmg, container, pillar, horizontal);
	}
	public static void uprightPillarModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createAxisRotatedBlockState(block, Models.CUBE_COLUMN.upload(block, Texture.sideEnd(block), bsmg.modelCollector)));
		parentedItem(bsmg, container.asItem(), block);
	}
	public static void ladderModelCommon(BlockStateModelGenerator bsmg, Block block, Item item, Identifier model) {
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(block, BlockStateVariant.create().put(VariantSettings.MODEL, model))
				.coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
		if (item != null) generatedItemModel(bsmg, item, block);
		else bsmg.excludeFromSimpleItemModelGeneration(block);
	}
	public static void ladderModelCommon(BlockStateModelGenerator bsmg, Block block, Item item, Model template) {
		ladderModelCommon(bsmg, block, item, template.upload(block, Texture.texture(block), bsmg.modelCollector));
	}
	public static void ladderModel(BlockStateModelGenerator bsmg, Block block, Item item) {
		ladderModelCommon(bsmg, block, item, ModModels.TEMPLATE_LADDER);
	}
	public static void ladderModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		ladderModelCommon(bsmg, container.asBlock(), container.asItem(), ModModels.TEMPLATE_LADDER);
	}
	public static void boneLadderModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		ladderModelCommon(bsmg, block, container.asItem(), getBlockModelId(block));
	}

	public static void horizontalFacingSlabModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer slabDouble, Texture textures) { horizontalFacingSlabModel(bsmg, container, slabDouble.asBlock(), textures); }
	public static void horizontalFacingSlabModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block slabDouble, Texture textures) { horizontalFacingSlabModel(bsmg, container, getBlockModelId(slabDouble), textures); }
	public static void horizontalFacingSlabModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Identifier slabDouble, Texture textures) {
		Block block = container.asBlock();
		Identifier slab = Models.SLAB.upload(block, textures, bsmg.modelCollector);
		Identifier slabTop = Models.SLAB_TOP.upload(block, textures, bsmg.modelCollector);
		horizontalFacingSlabModel(bsmg, container, slab, slabTop, slabDouble);
	}
	public static void horizontalFacingSlabModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Identifier slab, Identifier slabTop, Identifier slabDouble) {
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier
				.create(container.asBlock())
				.coordinate(BlockStateVariantMap
						.create(Properties.SLAB_TYPE, Properties.HORIZONTAL_FACING)
						.register(SlabType.BOTTOM, Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.MODEL, slab))
						.register(SlabType.TOP, Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.MODEL, slabTop))
						.register(SlabType.DOUBLE, Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.MODEL, slabDouble))
						.register(SlabType.BOTTOM, Direction.WEST, BlockStateVariant.create().put(VariantSettings.MODEL, slab).put(VariantSettings.Y, VariantSettings.Rotation.R90))
						.register(SlabType.TOP, Direction.WEST, BlockStateVariant.create().put(VariantSettings.MODEL, slabTop).put(VariantSettings.Y, VariantSettings.Rotation.R90))
						.register(SlabType.DOUBLE, Direction.WEST, BlockStateVariant.create().put(VariantSettings.MODEL, slabDouble).put(VariantSettings.Y, VariantSettings.Rotation.R90))
						.register(SlabType.BOTTOM, Direction.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, slab).put(VariantSettings.Y, VariantSettings.Rotation.R180))
						.register(SlabType.TOP, Direction.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, slabTop).put(VariantSettings.Y, VariantSettings.Rotation.R180))
						.register(SlabType.DOUBLE, Direction.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, slabDouble).put(VariantSettings.Y, VariantSettings.Rotation.R180))
						.register(SlabType.BOTTOM, Direction.EAST, BlockStateVariant.create().put(VariantSettings.MODEL, slab).put(VariantSettings.Y, VariantSettings.Rotation.R270))
						.register(SlabType.TOP, Direction.EAST, BlockStateVariant.create().put(VariantSettings.MODEL, slabTop).put(VariantSettings.Y, VariantSettings.Rotation.R270))
						.register(SlabType.DOUBLE, Direction.EAST, BlockStateVariant.create().put(VariantSettings.MODEL, slabDouble).put(VariantSettings.Y, VariantSettings.Rotation.R270))
				));
		parentedItem(bsmg, container.asItem(), slab);
	}

	public static void barkSlabModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block log) {
		Identifier texture = Texture.getId(log);
		Texture textures = new Texture().put(TextureKey.SIDE, texture).put(TextureKey.TOP, texture).put(TextureKey.BOTTOM, texture);
		horizontalFacingSlabModel(bsmg, container, log, textures);
	}

	public static void glazedTerracottaModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		Identifier identifier = TexturedModel.TEMPLATE_GLAZED_TERRACOTTA.upload(block, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(block, BlockStateVariant.create().put(VariantSettings.MODEL, identifier)).coordinate(BlockStateModelGenerator.createSouthDefaultHorizontalRotationStates()));
		parentedItem(bsmg, container.asItem(), identifier);
	}
	public static void glazedTerracottaSlabModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer base) { glazedTerracottaSlabModel(bsmg, container, base.asBlock()); }
	public static void glazedTerracottaSlabModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block base) {
		Block block = container.asBlock();
		Texture textures = Texture.pattern(base);
		Identifier slab = ModModels.TEMPLATE_GLAZED_TERRACOTTA_SLAB.upload(block, textures, bsmg.modelCollector);
		Identifier slabTop = ModModels.TEMPLATE_GLAZED_TERRACOTTA_SLAB_TOP.upload(block, textures, bsmg.modelCollector);
		horizontalFacingSlabModel(bsmg, container, slab, slabTop, getBlockModelId(base));
	}

	public static void rainbowBlockModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		Identifier base = getBlockModelId(block);
		String particle = "_particle", p = "_p", r = "_r", hpr = "_horiz_p_r", hrp = "_horiz_r_p", vpr = "_vert_p_r", vrp = "_vert_r_p";
		Identifier up = Models.CUBE.upload(block, "_up", uniqueSidesTexture(base, particle, r, p, vrp, vrp, vrp, vrp), bsmg.modelCollector);
		Identifier down = Models.CUBE.upload(block, "_down", uniqueSidesTexture(base, particle, p, r, vpr, vpr, vpr, vpr), bsmg.modelCollector);
		Identifier north = Models.CUBE.upload(block, "_north", uniqueSidesTexture(base, particle, vpr, vrp, p, r, hrp, hpr), bsmg.modelCollector);
		Identifier south = Models.CUBE.upload(block, "_south", uniqueSidesTexture(base, particle, hrp, hrp, hpr, hrp, p, r), bsmg.modelCollector);
		Identifier east = Models.CUBE.upload(block, "_east", uniqueSidesTexture(base, particle, vrp, vpr, r, p, hpr, hrp), bsmg.modelCollector);
		Identifier west = Models.CUBE.upload(block, "_west", uniqueSidesTexture(base, particle, hpr, hpr, hrp, hpr, r, p), bsmg.modelCollector);
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(block)
				.coordinate(BlockStateVariantMap.create(Properties.FACING)
						.register(Direction.DOWN, BlockStateVariant.create().put(VariantSettings.MODEL, up))
						.register(Direction.UP, BlockStateVariant.create().put(VariantSettings.MODEL, down))
						.register(Direction.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, north))
						.register(Direction.EAST, BlockStateVariant.create().put(VariantSettings.MODEL, south))
						.register(Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.MODEL, east))
						.register(Direction.WEST, BlockStateVariant.create().put(VariantSettings.MODEL, west))));
		parentedItem(bsmg, container.asItem(), north);
	}
	public static void rainbowSlabModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block baseBlock) {
		Block block = container.asBlock();
		Identifier base = getBlockModelId(baseBlock);
		String particle = "_particle", p = "_p", r = "_r", hpr = "_horiz_p_r", hrp = "_horiz_r_p", vpr = "_vert_p_r", vrp = "_vert_r_p";
		Texture n = uniqueSidesTexture(base, particle, vpr, vrp, p, r, hrp, hpr);
		Identifier north = ModModels.TEMPLATE_MULTI_TEXTURE_SLAB.upload(block, "_north", n, bsmg.modelCollector);
		Identifier north_top = ModModels.TEMPLATE_MULTI_TEXTURE_SLAB_TOP.upload(block, "_north", n, bsmg.modelCollector);
		Texture s = uniqueSidesTexture(base, particle, hrp, hrp, hpr, hrp, p, r);
		Identifier south = ModModels.TEMPLATE_MULTI_TEXTURE_SLAB.upload(block, "_south", s, bsmg.modelCollector);
		Identifier south_top = ModModels.TEMPLATE_MULTI_TEXTURE_SLAB_TOP.upload(block, "_south", s, bsmg.modelCollector);
		Texture e = uniqueSidesTexture(base, particle, vrp, vpr, r, p, hpr, hrp);
		Identifier east = ModModels.TEMPLATE_MULTI_TEXTURE_SLAB.upload(block, "_east", e, bsmg.modelCollector);
		Identifier east_top = ModModels.TEMPLATE_MULTI_TEXTURE_SLAB_TOP.upload(block, "_east", e, bsmg.modelCollector);
		Texture w = uniqueSidesTexture(base, particle, hpr, hpr, hrp, hpr, r, p);
		Identifier west = ModModels.TEMPLATE_MULTI_TEXTURE_SLAB.upload(block, "_west", w, bsmg.modelCollector);
		Identifier west_top = ModModels.TEMPLATE_MULTI_TEXTURE_SLAB_TOP.upload(block, "_west", w, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(block)
				.coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_FACING, Properties.SLAB_TYPE)
						.register(Direction.NORTH, SlabType.BOTTOM, BlockStateVariant.create().put(VariantSettings.MODEL, north))
						.register(Direction.EAST, SlabType.BOTTOM, BlockStateVariant.create().put(VariantSettings.MODEL, south))
						.register(Direction.SOUTH, SlabType.BOTTOM, BlockStateVariant.create().put(VariantSettings.MODEL, east))
						.register(Direction.WEST, SlabType.BOTTOM, BlockStateVariant.create().put(VariantSettings.MODEL, west))
						.register(Direction.NORTH, SlabType.DOUBLE, BlockStateVariant.create().put(VariantSettings.MODEL, postfixPath(base, "_east")))
						.register(Direction.EAST, SlabType.DOUBLE, BlockStateVariant.create().put(VariantSettings.MODEL, postfixPath(base, "_south")))
						.register(Direction.SOUTH, SlabType.DOUBLE, BlockStateVariant.create().put(VariantSettings.MODEL, postfixPath(base, "_east")))
						.register(Direction.WEST, SlabType.DOUBLE, BlockStateVariant.create().put(VariantSettings.MODEL, postfixPath(base, "_west")))
						.register(Direction.NORTH, SlabType.TOP, BlockStateVariant.create().put(VariantSettings.MODEL, north_top))
						.register(Direction.EAST, SlabType.TOP, BlockStateVariant.create().put(VariantSettings.MODEL, south_top))
						.register(Direction.SOUTH, SlabType.TOP, BlockStateVariant.create().put(VariantSettings.MODEL, east_top))
						.register(Direction.WEST, SlabType.TOP, BlockStateVariant.create().put(VariantSettings.MODEL, west_top))
				));
		parentedItem(bsmg, container.asItem(), north);
	}
	public static void rainbowCarpetModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block baseBlock) {
		Block block = container.asBlock();
		Identifier base = getBlockModelId(baseBlock);
		String particle = "_particle", p = "_p", r = "_r", hpr = "_horiz_p_r", hrp = "_horiz_r_p", vpr = "_vert_p_r", vrp = "_vert_r_p";
		Identifier north = ModModels.TEMPLATE_MULTI_TEXTURE_CARPET.upload(block, "_north", uniqueSidesTexture(base, particle, vpr, vrp, p, r, hrp, hpr), bsmg.modelCollector);
		Identifier south = ModModels.TEMPLATE_MULTI_TEXTURE_CARPET.upload(block, "_south", uniqueSidesTexture(base, particle, hrp, hrp, hpr, hrp, p, r), bsmg.modelCollector);
		Identifier east = ModModels.TEMPLATE_MULTI_TEXTURE_CARPET.upload(block, "_east", uniqueSidesTexture(base, particle, vrp, vpr, r, p, hpr, hrp), bsmg.modelCollector);
		Identifier west = ModModels.TEMPLATE_MULTI_TEXTURE_CARPET.upload(block, "_west", uniqueSidesTexture(base, particle, hpr, hpr, hrp, hpr, r, p), bsmg.modelCollector);
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(block)
				.coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_FACING)
						.register(Direction.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, north))
						.register(Direction.EAST, BlockStateVariant.create().put(VariantSettings.MODEL, south))
						.register(Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.MODEL, east))
						.register(Direction.WEST, BlockStateVariant.create().put(VariantSettings.MODEL, west))));
		parentedItem(bsmg, container.asItem(), north);
	}
	public static void bedModel(BlockStateModelGenerator bsmg, BedContainer bed, Block particle) {
		bedModel(bsmg, bed, Texture.getId(particle));
	}
	public static void bedModel(BlockStateModelGenerator bsmg, BedContainer bed, Identifier particle) {
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(bed.asBlock(), ModId.ID("minecraft:block/bed")));
		Models.TEMPLATE_BED.upload(getItemModelId(bed.asItem()), new Texture().put(TextureKey.PARTICLE, particle), bsmg.modelCollector);
	}
	public static void mudBricksModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		Texture map = Texture.all(block);
		Identifier identifier = Models.CUBE_ALL.upload(block, map, bsmg.modelCollector);
		Identifier identifier2 = ModModels.CUBE_NORTH_WEST_MIRRORED_ALL.upload(block, map, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, identifier2));
		parentedItem(bsmg, container.asItem(), identifier);
	}
	public static void woodcutterModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block planks) {
		Block block = container.asBlock();
		Texture textureMap = new Texture().put(ModTextureKeys.PLANKS, Texture.getId(planks));
		Identifier identifier = ModModels.TEMPLATE_WOODCUTTER.upload(block, textureMap, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(block, BlockStateVariant.create().put(VariantSettings.MODEL, identifier)).coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
		parentedItem(bsmg, container.asItem(), identifier);
	}

	private static void beehiveModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		Texture textureMap = Texture.sideFrontEnd(block).inherit(TextureKey.SIDE, TextureKey.PARTICLE);
		Texture textureMap2 = textureMap.copyAndAdd(TextureKey.FRONT, Texture.getSubId(block, "_front_honey"));
		Identifier identifier = Models.ORIENTABLE_WITH_BOTTOM.upload(block, textureMap, bsmg.modelCollector);
		Identifier identifier2 = Models.ORIENTABLE_WITH_BOTTOM.upload(block, "_honey", textureMap2, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(block)
				.coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates())
				.coordinate(BlockStateModelGenerator.createValueFencedModelMap(Properties.HONEY_LEVEL, 5, identifier2, identifier)));
		parentedItem(bsmg, container.asItem(), identifier);
	}
	private static void barrelModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		Identifier identifier = Texture.getSubId(block, "_top_open");
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(block)
				.coordinate(bsmg.createUpDefaultFacingVariantMap())
				.coordinate(BlockStateVariantMap.create(Properties.OPEN)
						.register(false, BlockStateVariant.create()
								.put(VariantSettings.MODEL, TexturedModel.CUBE_BOTTOM_TOP.upload(block, bsmg.modelCollector)))
						.register(true, BlockStateVariant.create()
								.put(VariantSettings.MODEL, TexturedModel.CUBE_BOTTOM_TOP.get(block)
										.texture(textureMap -> textureMap.put(TextureKey.TOP, identifier))
										.upload(block, "_open", bsmg.modelCollector)))));
		parentedItem(bsmg, container.asItem(), block);
	}
	private static void powderKegModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block barrel) {
		Block block = container.asBlock();
		Texture map = new Texture()
				.put(TextureKey.BOTTOM, Texture.getSubId(barrel, "_bottom"))
				.put(TextureKey.SIDE, Texture.getSubId(block, "_side"))
				.put(TextureKey.TOP, Texture.getSubId(block, "_top"));
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(block,
						BlockStateVariant.create().put(VariantSettings.MODEL, Models.CUBE_BOTTOM_TOP.upload(block, map, bsmg.modelCollector)))
				.coordinate(bsmg.createUpDefaultFacingVariantMap()));
		parentedItem(bsmg, container.asItem(), block);
	}
	public final BlockStateVariantMap createUpDefaultFacingVariantMap() {
		return BlockStateVariantMap.create(Properties.FACING).register(Direction.DOWN, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R180)).register(Direction.UP, BlockStateVariant.create()).register(Direction.NORTH, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R90)).register(Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R180)).register(Direction.WEST, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R270)).register(Direction.EAST, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R90));
	}
	private static void lecternModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block planks) {
		Block block = container.asBlock();
		Texture map = Texture.of(TextureKey.BOTTOM, Texture.getId(planks))
				.put(ModTextureKeys.BASE, Texture.getSubId(block, "_base"))
				.put(TextureKey.FRONT, Texture.getSubId(block, "_front"))
				.put(ModTextureKeys.SIDES, Texture.getSubId(block, "_sides"))
				.put(TextureKey.TOP, Texture.getSubId(block, "_top"));
		Identifier identifier = ModModels.TEMPLATE_LECTERN.upload(block, map, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(block,
						BlockStateVariant.create().put(VariantSettings.MODEL, identifier))
				.coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
		parentedItem(bsmg, container.asItem(), identifier);
	}
	public static void bookshelfModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block planks) {
		Block block = container.asBlock();
		Texture textureMap = Texture.sideEnd(Texture.getId(block), Texture.getId(planks));
		Identifier identifier = Models.CUBE_COLUMN.upload(block, textureMap, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, identifier));
		parentedItem(bsmg, container.asItem(), identifier);
	}

	record ChiseledBookshelfModelCacheKey(Model template, String modelSuffix) { }
	private static final Map<ChiseledBookshelfModelCacheKey, Identifier> CHISELED_BOOKSHELF_MODEL_CACHE = new HashMap<>();
	private static void chiseledBookshelfModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		Identifier identifier = ModelIds.getBlockModelId(block);
		MultipartBlockStateSupplier multipartBlockStateSupplier = MultipartBlockStateSupplier.create(block);
		Map.of(Direction.NORTH, VariantSettings.Rotation.R0, Direction.EAST, VariantSettings.Rotation.R90, Direction.SOUTH, VariantSettings.Rotation.R180, Direction.WEST, VariantSettings.Rotation.R270).forEach((direction, rotation) -> {
			When.PropertyCondition propertyCondition = When.create().set(Properties.HORIZONTAL_FACING, direction);
			multipartBlockStateSupplier.with(propertyCondition, BlockStateVariant.create()
					.put(VariantSettings.MODEL, identifier)
					.put(VariantSettings.Y, rotation)
					.put(VariantSettings.UVLOCK, true));
			supplyChiseledBookshelfModels(bsmg, block, multipartBlockStateSupplier, propertyCondition, rotation);
		});
		bsmg.blockStateCollector.accept(multipartBlockStateSupplier);
		Identifier inventory = ModModels.TEMPLATE_CHISELED_BOOKSHELF_INVENTORY.upload(block, new Texture()
				.put(TextureKey.TOP, Texture.getSubId(block, "_top"))
				.put(TextureKey.FRONT, Texture.getSubId(block, "_empty"))
				.put(TextureKey.SIDE, Texture.getSubId(block, "_side")), bsmg.modelCollector);
		bsmg.registerParentedItemModel(container.asItem(), inventory);
		CHISELED_BOOKSHELF_MODEL_CACHE.clear();
	}
	private static void supplyChiseledBookshelfModels(BlockStateModelGenerator bsmg, Block block, MultipartBlockStateSupplier blockStateSupplier, When.PropertyCondition facingCondition, VariantSettings.Rotation rotation) {
		Map.of(ModProperties.SLOT_0_OCCUPIED, ModModels.TEMPLATE_CHISELED_BOOKSHELF_SLOT_TOP_LEFT,
				ModProperties.SLOT_1_OCCUPIED, ModModels.TEMPLATE_CHISELED_BOOKSHELF_SLOT_TOP_MID,
				ModProperties.SLOT_2_OCCUPIED, ModModels.TEMPLATE_CHISELED_BOOKSHELF_SLOT_TOP_RIGHT,
				ModProperties.SLOT_3_OCCUPIED, ModModels.TEMPLATE_CHISELED_BOOKSHELF_SLOT_BOTTOM_LEFT,
				ModProperties.SLOT_4_OCCUPIED, ModModels.TEMPLATE_CHISELED_BOOKSHELF_SLOT_BOTTOM_MID,
				ModProperties.SLOT_5_OCCUPIED, ModModels.TEMPLATE_CHISELED_BOOKSHELF_SLOT_BOTTOM_RIGHT).forEach((property, model) -> {
			supplyChiseledBookshelfModel(bsmg, block, blockStateSupplier, facingCondition, rotation, property, model, true);
			supplyChiseledBookshelfModel(bsmg, block, blockStateSupplier, facingCondition, rotation, property, model, false);
		});
	}
	private static void supplyChiseledBookshelfModel(BlockStateModelGenerator bsmg, Block block, MultipartBlockStateSupplier blockStateSupplier, When.PropertyCondition facingCondition, VariantSettings.Rotation rotation, BooleanProperty property, Model model, boolean occupied) {
		String string = occupied ? "_occupied" : "_empty";
		Texture textureMap = new Texture().put(TextureKey.TEXTURE, Texture.getSubId(block, string));
		ChiseledBookshelfModelCacheKey chiseledBookshelfModelCacheKey = new ChiseledBookshelfModelCacheKey(model, string);
		Identifier identifier = CHISELED_BOOKSHELF_MODEL_CACHE.computeIfAbsent(chiseledBookshelfModelCacheKey, key -> model.upload(block, string, textureMap, bsmg.modelCollector));
		blockStateSupplier.with(When.allOf(facingCondition, When.create().set(property, occupied)), BlockStateVariant.create().put(VariantSettings.MODEL, identifier).put(VariantSettings.Y, rotation));
	}

	public static void registerCubeWithCustomTextures(BlockStateModelGenerator bsmg, Block block, Block otherTextureSource, BiFunction<Block, Block, Texture> texturesFactory) {
		Texture textureMap = texturesFactory.apply(block, otherTextureSource);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, Models.CUBE.upload(block, textureMap, bsmg.modelCollector)));
	}

	private static void craftingTableModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block planks) {
		Block block = container.asBlock();
		registerCubeWithCustomTextures(bsmg, block, planks, Texture::frontSideWithCustomBottom);
		parentedItem(bsmg, container.asItem(), block);
	}

	private static void frontSidePumpkinModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		Identifier top = Texture.getSubId(block, "_top");
		Identifier front = Texture.getSubId(block, "_front");
		Identifier side = Texture.getSubId(block, "_side");
		Texture textureMap = new Texture().put(TextureKey.PARTICLE, front)
				.put(TextureKey.DOWN, top).put(TextureKey.UP, top)
				.put(TextureKey.NORTH, front).put(TextureKey.SOUTH, front)
				.put(TextureKey.EAST, side).put(TextureKey.WEST, side);
		Identifier id = Models.CUBE.upload(block, textureMap, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, id));
		parentedItem(bsmg, container.asItem(), block);
	}
	private static void pumpkinModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		Identifier id = Models.CUBE_BOTTOM_TOP.upload(block, Texture.sideEnd(block), bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, id));
		parentedItem(bsmg, container.asItem(), block);
	}
	private static void carvedPumpkinModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer pumpkin) { carvedPumpkinModel(bsmg, container, pumpkin.asBlock()); }
	private static void carvedPumpkinModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block pumpkin) {
		Block block = container.asBlock();
		Texture textureMap = Texture.sideEnd(pumpkin);
		bsmg.registerNorthDefaultHorizontalRotatable(block, textureMap);
		parentedItem(bsmg, container.asItem(), block);
	}
	private static void parentedGourdStemModels(BlockStateModelGenerator bsmg, Block stemBlock, Block attachedStemBlock, Block baseStem, Block baseAttached) {
		Texture textureMap = Texture.stem(baseStem);
		Texture textureMap2 = Texture.stemAndUpper(baseStem, baseAttached);
		Identifier identifier = Models.STEM_FRUIT.upload(attachedStemBlock, textureMap2, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(attachedStemBlock, BlockStateVariant.create().put(VariantSettings.MODEL, identifier)).coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_FACING).register(Direction.WEST, BlockStateVariant.create()).register(Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R270)).register(Direction.NORTH, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R90)).register(Direction.EAST, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R180))));
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(stemBlock).coordinate(BlockStateVariantMap.create(Properties.AGE_7).register(integer -> BlockStateVariant.create().put(VariantSettings.MODEL, Models.STEM_GROWTH_STAGES[integer].upload(stemBlock, textureMap, bsmg.modelCollector)))));
	}

	private static void vinesModel(BlockStateModelGenerator bsmg, Block vines, Block plant) {
		Identifier identifier = bsmg.createSubModel(vines, "", Models.CROSS, Texture::cross);
		Identifier identifier2 = bsmg.createSubModel(vines, "_berried", Models.CROSS, Texture::cross);
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(vines).coordinate(BlockStateModelGenerator.createBooleanModelMap(Properties.BERRIES, identifier2, identifier)));
		Identifier identifier3 = bsmg.createSubModel(plant, "", Models.CROSS, Texture::cross);
		Identifier identifier4 = bsmg.createSubModel(plant, "_berried", Models.CROSS, Texture::cross);
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(plant).coordinate(BlockStateModelGenerator.createBooleanModelMap(Properties.BERRIES, identifier4, identifier3)));
	}

	private static void berryLeavesModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		Identifier stage0 = Models.LEAVES.upload(block, "_stage0", Texture.all(Texture.getSubId(block, "_stage0")), bsmg.modelCollector);
		Identifier stage1 = Models.LEAVES.upload(block, "_stage1", Texture.all(Texture.getSubId(block, "_stage1")), bsmg.modelCollector);
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(block).coordinate(BlockStateModelGenerator.createBooleanModelMap(Properties.BERRIES, stage1, stage0)));
		parentedItem(bsmg, container.asItem(), stage1);
	}

	private void suspiciousSandModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(block).coordinate(BlockStateVariantMap.create(ModProperties.DUSTED).register(integer -> {
			String string = "_" + integer;
			Identifier identifier = Texture.getSubId(block, string);
			return BlockStateVariant.create().put(VariantSettings.MODEL, Models.CUBE_ALL.upload(block, string, new Texture().put(TextureKey.ALL, identifier), bsmg.modelCollector));
		})));
		bsmg.registerParentedItemModel(container.asItem(), Texture.getSubId(block, "_0"));
	}

	private static void simpleHammerModel(BlockStateModelGenerator bsmg, ItemConvertible hammer) {
		Identifier identifier = getItemModelId(hammer.asItem());
		ModModels.TEMPLATE_SIMPLE_HAMMER.upload(identifier, Texture.texture(identifier), bsmg.modelCollector);
	}

	private List<BlockStateVariant> randomHorizontalRotations(Block block, String suffix) {
		return List.of(BlockStateModelGenerator.createModelVariantWithRandomHorizontalRotations(ModelIds.getBlockSubModelId(block, suffix)));
	}

	private void count4MushroomModel(BlockStateModelGenerator bsmg, PottedBlockContainer container, List<BlockStateVariant> v1, List<BlockStateVariant> v2, List<BlockStateVariant> v3, List<BlockStateVariant> v4) {
		Block block = container.asBlock();
		Item item = container.asItem();
		List<BlockStateVariant> V1 = new ArrayList<>(v1);
		V1.addAll(randomHorizontalRotations(block, "_1"));
		List<BlockStateVariant> V2 = new ArrayList<>(v2);
		V2.addAll(randomHorizontalRotations(block, "_2"));
		List<BlockStateVariant> V3 = new ArrayList<>(v3);
		V3.addAll(randomHorizontalRotations(block, "_3"));
		List<BlockStateVariant> V4 = new ArrayList<>(v4);
		V4.addAll(randomHorizontalRotations(block, "_4"));
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(block)
				.coordinate(BlockStateVariantMap.create(ModProperties.COUNT_4)
						.register(1, V1)
						.register(2, V2)
						.register(3, V3)
						.register(4, V4)));
		potModel(bsmg, Texture.plant(Texture.getId(item)), container.getPottedBlock());
		generatedItemModel(bsmg, item);
	}
	private void count4MushroomModel(BlockStateModelGenerator bsmg, PottedBlockContainer container, boolean alt) {
		Block block = container.asBlock();
		if (alt) {
			count4MushroomModel(bsmg, container,
					randomHorizontalRotations(block, "_1_alt"),
					randomHorizontalRotations(block, "_2_alt"),
					randomHorizontalRotations(block, "_3_alt"),
					randomHorizontalRotations(block, "_4_alt"));
		}
		else count4MushroomModel(bsmg, container, List.of(), List.of(), List.of(), List.of());
	}

	public final void registerWoolAndCarpet(BlockStateModelGenerator bsmg, Block wool, Block carpet) {
		bsmg.registerSimpleCubeAll(wool);
		Identifier identifier = TexturedModel.CARPET.get(wool).upload(carpet, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(carpet, identifier));
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator bsmg) {
		//Datagen Cache - Items
		for (Item item : ModDatagen.Cache.Models.GENERATED) {
			Identifier itemId = Registry.ITEM.getId(item);
			if (itemId == Registry.ITEM.getDefaultId()) {
				ModId.LOGGER.error(item.getClass().getName());
			}
			generatedItemModel(bsmg, item);
		}
		ModDatagen.Cache.Models.GENERATED.clear();
		for (Item item : ModDatagen.Cache.Models.HAMMER) simpleHammerModel(bsmg, item);
		ModDatagen.Cache.Models.HAMMER.clear();
		for (Item item : ModDatagen.Cache.Models.HANDHELD) handheldItemModel(bsmg, item);
		ModDatagen.Cache.Models.HANDHELD.clear();
		for (Pair<Item, Item> pair : ModDatagen.Cache.Models.PARENTED) parentedItem(bsmg, pair.getLeft(), pair.getRight());
		for (Item item : ModDatagen.Cache.Models.SPAWN_EGG) spawnEggModel(bsmg, item);
		ModDatagen.Cache.Models.SPAWN_EGG.clear();
		//Datagen Cache - Blocks (standalone)
		for (IBlockItemContainer container : ModDatagen.Cache.Models.BAMBOO_FENCE) bambooFenceModel(bsmg, container);
		ModDatagen.Cache.Models.BAMBOO_FENCE.clear();
		for (IBlockItemContainer container : ModDatagen.Cache.Models.BAMBOO_FENCE_GATE) bambooFenceGateModel(bsmg, container);
		ModDatagen.Cache.Models.BAMBOO_FENCE_GATE.clear();
		for (IBlockItemContainer container : ModDatagen.Cache.Models.BARREL) barrelModel(bsmg, container);
		ModDatagen.Cache.Models.BARREL.clear();
		for (IBlockItemContainer container : ModDatagen.Cache.Models.BARS) barsModel(bsmg, container);
		ModDatagen.Cache.Models.BARS.clear();
		for (IBlockItemContainer container : ModDatagen.Cache.Models.BEEHIVE) beehiveModel(bsmg, container);
		ModDatagen.Cache.Models.BEEHIVE.clear();
		for (IBlockItemContainer container : ModDatagen.Cache.Models.CAMPFIRE) campfireModel(bsmg, container, 0);
		ModDatagen.Cache.Models.CAMPFIRE.clear();
		for (IBlockItemContainer container : ModDatagen.Cache.Models.CHAIN) chainModel(bsmg, container);
		ModDatagen.Cache.Models.CHAIN.clear();
		for (IBlockItemContainer container : ModDatagen.Cache.Models.CHISELED_BOOKSHELF) chiseledBookshelfModel(bsmg, container);
		ModDatagen.Cache.Models.CHISELED_BOOKSHELF.clear();
		for (IBlockItemContainer container : ModDatagen.Cache.Models.CUBE_ALL) cubeAllModel(bsmg, container);
		ModDatagen.Cache.Models.CUBE_ALL.clear();
		for (IBlockItemContainer container : ModDatagen.Cache.Models.DOOR) doorModel(bsmg, container);
		ModDatagen.Cache.Models.DOOR.clear();
		for (IBlockItemContainer container : ModDatagen.Cache.Models.HEAVY_CHAIN) heavyChainModel(bsmg, container);
		ModDatagen.Cache.Models.HEAVY_CHAIN.clear();
		for (IBlockItemContainer container : ModDatagen.Cache.Models.LADDER) ladderModel(bsmg, container);
		ModDatagen.Cache.Models.LADDER.clear();
		for (IBlockItemContainer container : ModDatagen.Cache.Models.LEAVES) singletonModel(bsmg, container, TexturedModel.LEAVES);
		ModDatagen.Cache.Models.LEAVES.clear();
		for (IBlockItemContainer container : ModDatagen.Cache.Models.PLUSHIE) plushieModel(bsmg, container);
		ModDatagen.Cache.Models.PLUSHIE.clear();
		for (PottedBlockContainer container : ModDatagen.Cache.Models.POTTED) pottedModel(bsmg, container);
		ModDatagen.Cache.Models.POTTED.clear();
		for (IBlockItemContainer container : ModDatagen.Cache.Models.THIN_TRAPDOOR) thinTrapdoorModel(bsmg, container);
		ModDatagen.Cache.Models.THIN_TRAPDOOR.clear();
		for (TorchContainer container : ModDatagen.Cache.Models.TORCH) torchModel(bsmg, container);
		ModDatagen.Cache.Models.TORCH.clear();
		for (Pair<IBlockItemContainer, Boolean> pair : ModDatagen.Cache.Models.TRAPDOOR) trapdoorModel(bsmg, pair.getLeft(), pair.getRight());
		ModDatagen.Cache.Models.TRAPDOOR.clear();
		//Datagen Cache - Blocks (with base)
		for (Pair<IBlockItemContainer, Block> pair : ModDatagen.Cache.Models.BARK_SLAB) barkSlabModel(bsmg, pair.getLeft(), pair.getRight());
		ModDatagen.Cache.Models.BARK_SLAB.clear();
		for (Pair<IBlockItemContainer, Block> pair : ModDatagen.Cache.Models.BOOKSHELF) bookshelfModel(bsmg, pair.getLeft(), pair.getRight());
		ModDatagen.Cache.Models.BOOKSHELF.clear();
		for (Pair<IBlockItemContainer, Block> pair : ModDatagen.Cache.Models.BUTTON) buttonModel(bsmg, pair.getLeft(), pair.getRight());
		ModDatagen.Cache.Models.BUTTON.clear();
		for (Pair<IBlockItemContainer, Block> pair : ModDatagen.Cache.Models.CAMPFIRE_SOUL) campfireModel(bsmg, pair.getLeft(), pair.getRight(), 1);
		ModDatagen.Cache.Models.CAMPFIRE_SOUL.clear();
		for (Pair<IBlockItemContainer, Block> pair : ModDatagen.Cache.Models.CAMPFIRE_ENDER) campfireModel(bsmg, pair.getLeft(), pair.getRight(), 2);
		ModDatagen.Cache.Models.CAMPFIRE_ENDER.clear();
		for (Pair<IBlockItemContainer, Block> pair : ModDatagen.Cache.Models.CRAFTING_TABLE) craftingTableModel(bsmg, pair.getLeft(), pair.getRight());
		ModDatagen.Cache.Models.CRAFTING_TABLE.clear();
		for (Pair<IBlockItemContainer, Block> pair : ModDatagen.Cache.Models.FENCE) fenceModel(bsmg, pair.getLeft(), pair.getRight());
		ModDatagen.Cache.Models.FENCE.clear();
		for (Pair<IBlockItemContainer, Block> pair : ModDatagen.Cache.Models.FENCE_GATE) fenceGateModel(bsmg, pair.getLeft(), pair.getRight());
		ModDatagen.Cache.Models.FENCE_GATE.clear();
		for (Pair<WallBlockContainer, Block> pair : ModDatagen.Cache.Models.HANGING_SIGN) hangingSignModel(bsmg, pair.getRight(), pair.getLeft());
		ModDatagen.Cache.Models.HANGING_SIGN.clear();
		for (Pair<IBlockItemContainer, Block> pair : ModDatagen.Cache.Models.LECTERN) lecternModel(bsmg, pair.getLeft(), pair.getRight());
		ModDatagen.Cache.Models.LECTERN.clear();
		for (Pair<IBlockItemContainer, Block> pair : ModDatagen.Cache.Models.LOG_SLAB) logSlabModel(bsmg, pair.getLeft(), pair.getRight());
		ModDatagen.Cache.Models.LOG_SLAB.clear();
		for (Pair<IBlockItemContainer, Block> pair : ModDatagen.Cache.Models.PANE) paneModel(bsmg, pair.getLeft(), pair.getRight());
		ModDatagen.Cache.Models.PANE.clear();
		for (Pair<IBlockItemContainer, Block> pair : ModDatagen.Cache.Models.POWDER_KEG) powderKegModel(bsmg, pair.getLeft(), pair.getRight());
		ModDatagen.Cache.Models.POWDER_KEG.clear();
		for (Pair<IBlockItemContainer, Block> pair : ModDatagen.Cache.Models.PRESSURE_PLATE) pressurePlateModel(bsmg, pair.getLeft(), pair.getRight());
		ModDatagen.Cache.Models.PRESSURE_PLATE.clear();
		for (Pair<WallBlockContainer, Block> pair : ModDatagen.Cache.Models.SIGN) signModel(bsmg, pair.getLeft(), pair.getRight());
		ModDatagen.Cache.Models.SIGN.clear();
		for (Pair<IBlockItemContainer, Block> pair : ModDatagen.Cache.Models.SLAB) slabModel(bsmg, pair.getLeft(), pair.getRight());
		ModDatagen.Cache.Models.SLAB.clear();
		for (Pair<IBlockItemContainer, Block> pair : ModDatagen.Cache.Models.STAIRS) stairsModel(bsmg, pair.getLeft(), pair.getRight());
		ModDatagen.Cache.Models.STAIRS.clear();
		for (Pair<TorchContainer, TorchContainer> pair : ModDatagen.Cache.Models.TORCH_CHILD) torchModel(bsmg, pair.getLeft(), pair.getRight());
		ModDatagen.Cache.Models.TORCH_CHILD.clear();
		for (Pair<IBlockItemContainer, Block> pair : ModDatagen.Cache.Models.WALL) wallModel(bsmg, pair.getLeft(), pair.getRight());
		ModDatagen.Cache.Models.WALL.clear();
		for (Pair<IBlockItemContainer, Block> pair : ModDatagen.Cache.Models.WEIGHTED_PRESSURE_PLATE) weightedPressurePlateModel(bsmg, pair.getLeft(), pair.getRight());
		ModDatagen.Cache.Models.WEIGHTED_PRESSURE_PLATE.clear();
		for (Pair<IBlockItemContainer, Block> pair : ModDatagen.Cache.Models.WOODCUTTER) woodcutterModel(bsmg, pair.getLeft(), pair.getRight());
		ModDatagen.Cache.Models.WOODCUTTER.clear();
		//Datagen Cache - Misc Containers
		for (FlowerPartContainer container : ModDatagen.Cache.Models.FLOWER_PART) flowerPartModel(bsmg, container);
		ModDatagen.Cache.Models.FLOWER_PART.clear();

		//Overrides
		ladderModel(bsmg, Blocks.LADDER, null);
		barsModelBlockOnly(bsmg, Blocks.IRON_BARS);

		thickTorchModel(bsmg, TIKI_TORCH);
		thickTorchModel(bsmg, TIKI_SOUL_TORCH, getBlockModelId("unlit_", TIKI_TORCH.asBlock()));
		thickTorchModel(bsmg, TIKI_ENDER_TORCH, getBlockModelId("unlit_", TIKI_TORCH.asBlock()));
		postModel(bsmg, TIKI_TORCH_POST);

		//Light Sources
		unlitTorchModel(bsmg, UNLIT_TORCH);
		unlitTorchModelCommon(bsmg, UNLIT_SOUL_TORCH.asBlock(), UNLIT_SOUL_TORCH.getWallBlock(), getBlockModelId(UNLIT_TORCH.asBlock()), getBlockModelId(UNLIT_TORCH.getWallBlock()));
		torchModel(bsmg, UNDERWATER_TORCH, UNLIT_TORCH);
		//Ender Fire
		torchModel(bsmg, ENDER_TORCH, UNLIT_TORCH);
		unlitLanternModel(bsmg, UNLIT_LANTERN);
		unlitLanternModel(bsmg, UNLIT_SOUL_LANTERN, UNLIT_LANTERN);
		explicitLanternModel(bsmg, EMPTY_LANTERN);
		lanternModel(bsmg, ENDER_LANTERN, UNLIT_LANTERN);
		//<editor-fold desc="Candles & Cakes">
		candleModel(bsmg, SOUL_CANDLE, SOUL_CANDLE_CAKE);
		candleModel(bsmg, ENDER_CANDLE, ENDER_CANDLE_CAKE);
		candleModel(bsmg, NETHERRACK_CANDLE, NETHERRACK_CANDLE_CAKE);
		candleModel(bsmg, BEIGE_CANDLE, BEIGE_CANDLE_CAKE);
		candleModel(bsmg, BURGUNDY_CANDLE, BURGUNDY_CANDLE_CAKE);
		candleModel(bsmg, LAVENDER_CANDLE, LAVENDER_CANDLE_CAKE);
		candleModel(bsmg, MINT_CANDLE, MINT_CANDLE_CAKE);
		cakeModels(bsmg, CARROT_CAKE);
		cakeModels(bsmg, CHOCOLATE_CAKE);
		cakeModels(bsmg, CHORUS_CAKE);
		cakeModels(bsmg, COFFEE_CAKE);
		cakeModels(bsmg, CONFETTI_CAKE);
		cakeModels(bsmg, STRAWBERRY_CAKE);
		cakeModels(bsmg, VANILLA_CAKE);
		//</editor-fold>
		//<editor-fold desc="Sandstone">
		wallModel(bsmg, SMOOTH_SANDSTONE_WALL, Texture.getSubId(Blocks.SANDSTONE, "_top"));
		wallModel(bsmg, SMOOTH_RED_SANDSTONE_WALL, Texture.getSubId(Blocks.RED_SANDSTONE, "_top"));
		//</editor-fold>
		//<editor-fold desc="Arrows">
		for (Item arrow : ArrowContainer.SUMMONING_ARROWS.values()) {
			if (!RAINBOW_SHEEP_SUMMONING_ARROW.contains(arrow)) {
				parentedItem(bsmg, arrow, TEMPLATE_SUMMONING_ARROW);
			}
		}
		//</editor-fold>
		//<editor-fold desc="Mod Dye Colors">
		//Beige
		registerWoolAndCarpet(bsmg, BEIGE_WOOL.asBlock(), BEIGE_CARPET.asBlock());
		parentedItem(bsmg, BEIGE_WOOL);
		parentedItem(bsmg, BEIGE_CARPET);
		registerWoolAndCarpet(bsmg, BEIGE_FLEECE.asBlock(), BEIGE_FLEECE_CARPET.asBlock());
		parentedItem(bsmg, BEIGE_FLEECE);
		parentedItem(bsmg, BEIGE_FLEECE_CARPET);
		glassSlabModel(bsmg, BEIGE_STAINED_GLASS_SLAB, BEIGE_STAINED_GLASS);
		glassTrapdoorModel(bsmg, BEIGE_STAINED_GLASS_TRAPDOOR, BEIGE_STAINED_GLASS, BEIGE_STAINED_GLASS_PANE);
		glazedTerracottaModel(bsmg, BEIGE_GLAZED_TERRACOTTA);
		glazedTerracottaSlabModel(bsmg, BEIGE_GLAZED_TERRACOTTA_SLAB,  BEIGE_GLAZED_TERRACOTTA);
		//Burgundy
		registerWoolAndCarpet(bsmg, BURGUNDY_WOOL.asBlock(), BURGUNDY_CARPET.asBlock());
		parentedItem(bsmg, BURGUNDY_WOOL);
		parentedItem(bsmg, BURGUNDY_CARPET);
		registerWoolAndCarpet(bsmg, BURGUNDY_FLEECE.asBlock(), BURGUNDY_FLEECE_CARPET.asBlock());
		parentedItem(bsmg, BURGUNDY_FLEECE);
		parentedItem(bsmg, BURGUNDY_FLEECE_CARPET);
		glassSlabModel(bsmg, BURGUNDY_STAINED_GLASS_SLAB, BURGUNDY_STAINED_GLASS);
		glassTrapdoorModel(bsmg, BURGUNDY_STAINED_GLASS_TRAPDOOR, BURGUNDY_STAINED_GLASS, BURGUNDY_STAINED_GLASS_PANE);
		glazedTerracottaModel(bsmg, BURGUNDY_GLAZED_TERRACOTTA);
		glazedTerracottaSlabModel(bsmg, BURGUNDY_GLAZED_TERRACOTTA_SLAB,  BURGUNDY_GLAZED_TERRACOTTA);
		//Lavender
		registerWoolAndCarpet(bsmg, LAVENDER_WOOL.asBlock(), LAVENDER_CARPET.asBlock());
		parentedItem(bsmg, LAVENDER_WOOL);
		parentedItem(bsmg, LAVENDER_CARPET);
		registerWoolAndCarpet(bsmg, LAVENDER_FLEECE.asBlock(), LAVENDER_FLEECE_CARPET.asBlock());
		parentedItem(bsmg, LAVENDER_FLEECE);
		parentedItem(bsmg, LAVENDER_FLEECE_CARPET);
		glassSlabModel(bsmg, LAVENDER_STAINED_GLASS_SLAB, LAVENDER_STAINED_GLASS);
		glassTrapdoorModel(bsmg, LAVENDER_STAINED_GLASS_TRAPDOOR, LAVENDER_STAINED_GLASS, LAVENDER_STAINED_GLASS_PANE);
		glazedTerracottaModel(bsmg, LAVENDER_GLAZED_TERRACOTTA);
		glazedTerracottaSlabModel(bsmg, LAVENDER_GLAZED_TERRACOTTA_SLAB,  LAVENDER_GLAZED_TERRACOTTA);
		//Mint
		registerWoolAndCarpet(bsmg, MINT_WOOL.asBlock(), MINT_CARPET.asBlock());
		parentedItem(bsmg, MINT_WOOL);
		parentedItem(bsmg, MINT_CARPET);
		registerWoolAndCarpet(bsmg, MINT_FLEECE.asBlock(), MINT_FLEECE_CARPET.asBlock());
		parentedItem(bsmg, MINT_FLEECE);
		parentedItem(bsmg, MINT_FLEECE_CARPET);
		glassSlabModel(bsmg, MINT_STAINED_GLASS_SLAB, MINT_STAINED_GLASS);
		glassTrapdoorModel(bsmg, MINT_STAINED_GLASS_TRAPDOOR, MINT_STAINED_GLASS, MINT_STAINED_GLASS_PANE);
		glazedTerracottaModel(bsmg, MINT_GLAZED_TERRACOTTA);
		glazedTerracottaSlabModel(bsmg, MINT_GLAZED_TERRACOTTA_SLAB,  MINT_GLAZED_TERRACOTTA);
		//</editor-fold>
		//<editor-fold desc="Glass">
		glassSlabModel(bsmg, TINTED_GLASS_SLAB, Blocks.TINTED_GLASS);
		glassTrapdoorModel(bsmg, TINTED_GLASS_TRAPDOOR, Blocks.TINTED_GLASS, TINTED_GLASS_PANE);
		glassSlabModel(bsmg, RUBY_GLASS_SLAB, RUBY_GLASS);
		glassTrapdoorModel(bsmg, RUBY_GLASS_TRAPDOOR, RUBY_GLASS, RUBY_GLASS_PANE);
		glassSlabModel(bsmg, SAPPHIRE_GLASS_SLAB, SAPPHIRE_GLASS);
		glassTrapdoorModel(bsmg, SAPPHIRE_GLASS_TRAPDOOR, SAPPHIRE_GLASS, SAPPHIRE_GLASS_PANE);
		glassSlabModel(bsmg, GLASS_SLAB, Blocks.GLASS);
		glassTrapdoorModel(bsmg, GLASS_TRAPDOOR, Blocks.GLASS, Blocks.GLASS_PANE);
		for(DyeColor color : DyeColor.values()){
			glassSlabModel(bsmg, STAINED_GLASS_SLABS.get(color), ColorUtil.GetStainedGlassBlock(color));
			glassTrapdoorModel(bsmg, STAINED_GLASS_TRAPDOORS.get(color), ColorUtil.GetStainedGlassBlock(color), ColorUtil.GetStainedGlassPaneBlock(color));
		}
		//</editor-fold>
		//<editor-fold desc="Quartz">
		wallModel(bsmg, SMOOTH_QUARTZ_WALL, new Identifier("block/quartz_block_bottom"));
		wallModel(bsmg, QUARTZ_WALL, new Identifier("block/quartz_block_side"));
		//</editor-fold>
		//<editor-fold desc="Iron">
		lanternModel(bsmg, IRON_LANTERN);
		lanternModel(bsmg, IRON_SOUL_LANTERN, IRON_LANTERN);
		lanternModel(bsmg, IRON_ENDER_LANTERN, IRON_LANTERN);
		metalPillarModel(bsmg, CUT_IRON_PILLAR, CUT_IRON);
		//</editor-fold>
		metalPillarModel(bsmg, CUT_DARK_IRON_PILLAR, CUT_DARK_IRON);
		//<editor-fold desc="Gold">
		lanternModel(bsmg, GOLD_LANTERN);
		lanternModel(bsmg, GOLD_SOUL_LANTERN, GOLD_LANTERN);
		lanternModel(bsmg, GOLD_ENDER_LANTERN, GOLD_LANTERN);
		metalPillarModel(bsmg, CUT_GOLD_PILLAR, CUT_GOLD);
		//</editor-fold>
		//<editor-fold desc="Copper">
		copiedTorchModel(bsmg, WAXED_COPPER_TORCH, COPPER_TORCH);
		copiedTorchModel(bsmg, WAXED_EXPOSED_COPPER_TORCH, EXPOSED_COPPER_TORCH);
		copiedTorchModel(bsmg, WAXED_WEATHERED_COPPER_TORCH, WEATHERED_COPPER_TORCH);
		copiedTorchModel(bsmg, WAXED_OXIDIZED_COPPER_TORCH, OXIDIZED_COPPER_TORCH);
		copiedTorchModel(bsmg, WAXED_COPPER_SOUL_TORCH, COPPER_TORCH);
		copiedTorchModel(bsmg, WAXED_EXPOSED_COPPER_SOUL_TORCH, EXPOSED_COPPER_TORCH);
		copiedTorchModel(bsmg, WAXED_WEATHERED_COPPER_SOUL_TORCH, WEATHERED_COPPER_TORCH);
		copiedTorchModel(bsmg, WAXED_OXIDIZED_COPPER_SOUL_TORCH, OXIDIZED_COPPER_TORCH);
		copiedTorchModel(bsmg, WAXED_COPPER_ENDER_TORCH, COPPER_TORCH);
		copiedTorchModel(bsmg, WAXED_EXPOSED_COPPER_ENDER_TORCH, EXPOSED_COPPER_TORCH);
		copiedTorchModel(bsmg, WAXED_WEATHERED_COPPER_ENDER_TORCH, WEATHERED_COPPER_TORCH);
		copiedTorchModel(bsmg, WAXED_OXIDIZED_COPPER_ENDER_TORCH, OXIDIZED_COPPER_TORCH);
		copiedTorchModel(bsmg, WAXED_UNDERWATER_COPPER_TORCH, COPPER_TORCH);
		copiedTorchModel(bsmg, WAXED_EXPOSED_UNDERWATER_COPPER_TORCH, EXPOSED_COPPER_TORCH);
		copiedTorchModel(bsmg, WAXED_WEATHERED_UNDERWATER_COPPER_TORCH, WEATHERED_COPPER_TORCH);
		copiedTorchModel(bsmg, WAXED_OXIDIZED_UNDERWATER_COPPER_TORCH, OXIDIZED_COPPER_TORCH);
		lanternModel(bsmg, COPPER_LANTERN);
		lanternModel(bsmg, EXPOSED_COPPER_LANTERN);
		lanternModel(bsmg, WEATHERED_COPPER_LANTERN);
		lanternModel(bsmg, OXIDIZED_COPPER_LANTERN);
		copiedLanternModel(bsmg, WAXED_COPPER_LANTERN, COPPER_LANTERN);
		copiedLanternModel(bsmg, WAXED_EXPOSED_COPPER_LANTERN, EXPOSED_COPPER_LANTERN);
		copiedLanternModel(bsmg, WAXED_WEATHERED_COPPER_LANTERN, WEATHERED_COPPER_LANTERN);
		copiedLanternModel(bsmg, WAXED_OXIDIZED_COPPER_LANTERN, OXIDIZED_COPPER_LANTERN);
		lanternModel(bsmg, COPPER_SOUL_LANTERN, COPPER_LANTERN);
		lanternModel(bsmg, EXPOSED_COPPER_SOUL_LANTERN, EXPOSED_COPPER_LANTERN);
		lanternModel(bsmg, WEATHERED_COPPER_SOUL_LANTERN, WEATHERED_COPPER_LANTERN);
		lanternModel(bsmg, OXIDIZED_COPPER_SOUL_LANTERN, OXIDIZED_COPPER_LANTERN);
		copiedLanternModel(bsmg, WAXED_COPPER_SOUL_LANTERN, COPPER_SOUL_LANTERN, COPPER_LANTERN);
		copiedLanternModel(bsmg, WAXED_EXPOSED_COPPER_SOUL_LANTERN, EXPOSED_COPPER_SOUL_LANTERN, EXPOSED_COPPER_LANTERN);
		copiedLanternModel(bsmg, WAXED_WEATHERED_COPPER_SOUL_LANTERN, WEATHERED_COPPER_SOUL_LANTERN, WEATHERED_COPPER_LANTERN);
		copiedLanternModel(bsmg, WAXED_OXIDIZED_COPPER_SOUL_LANTERN, OXIDIZED_COPPER_SOUL_LANTERN, OXIDIZED_COPPER_LANTERN);
		lanternModel(bsmg, COPPER_ENDER_LANTERN, COPPER_LANTERN);
		lanternModel(bsmg, EXPOSED_COPPER_ENDER_LANTERN, EXPOSED_COPPER_LANTERN);
		lanternModel(bsmg, WEATHERED_COPPER_ENDER_LANTERN, WEATHERED_COPPER_LANTERN);
		lanternModel(bsmg, OXIDIZED_COPPER_ENDER_LANTERN, OXIDIZED_COPPER_LANTERN);
		copiedLanternModel(bsmg, WAXED_COPPER_ENDER_LANTERN, COPPER_ENDER_LANTERN, COPPER_LANTERN);
		copiedLanternModel(bsmg, WAXED_EXPOSED_COPPER_ENDER_LANTERN, EXPOSED_COPPER_ENDER_LANTERN, EXPOSED_COPPER_LANTERN);
		copiedLanternModel(bsmg, WAXED_WEATHERED_COPPER_ENDER_LANTERN, WEATHERED_COPPER_ENDER_LANTERN, WEATHERED_COPPER_LANTERN);
		copiedLanternModel(bsmg, WAXED_OXIDIZED_COPPER_ENDER_LANTERN, OXIDIZED_COPPER_ENDER_LANTERN, OXIDIZED_COPPER_LANTERN);
		copiedButtonModel(bsmg, WAXED_COPPER_BUTTON, COPPER_BUTTON);
		copiedButtonModel(bsmg, WAXED_EXPOSED_COPPER_BUTTON, EXPOSED_COPPER_BUTTON);
		copiedButtonModel(bsmg, WAXED_WEATHERED_COPPER_BUTTON, WEATHERED_COPPER_BUTTON);
		copiedButtonModel(bsmg, WAXED_OXIDIZED_COPPER_BUTTON, OXIDIZED_COPPER_BUTTON);
		copiedChainModel(bsmg, WAXED_COPPER_CHAIN, COPPER_CHAIN);
		copiedChainModel(bsmg, WAXED_EXPOSED_COPPER_CHAIN, EXPOSED_COPPER_CHAIN);
		copiedChainModel(bsmg, WAXED_WEATHERED_COPPER_CHAIN, WEATHERED_COPPER_CHAIN);
		copiedChainModel(bsmg, WAXED_OXIDIZED_COPPER_CHAIN, OXIDIZED_COPPER_CHAIN);
		copiedChainModel(bsmg, WAXED_HEAVY_COPPER_CHAIN, HEAVY_COPPER_CHAIN);
		copiedChainModel(bsmg, WAXED_EXPOSED_HEAVY_COPPER_CHAIN, EXPOSED_HEAVY_COPPER_CHAIN);
		copiedChainModel(bsmg, WAXED_WEATHERED_HEAVY_COPPER_CHAIN, WEATHERED_HEAVY_COPPER_CHAIN);
		copiedChainModel(bsmg, WAXED_OXIDIZED_HEAVY_COPPER_CHAIN, OXIDIZED_HEAVY_COPPER_CHAIN);
		copiedBarsModel(bsmg, WAXED_COPPER_BARS, COPPER_BARS);
		copiedBarsModel(bsmg, WAXED_EXPOSED_COPPER_BARS, EXPOSED_COPPER_BARS);
		copiedBarsModel(bsmg, WAXED_WEATHERED_COPPER_BARS, WEATHERED_COPPER_BARS);
		copiedBarsModel(bsmg, WAXED_OXIDIZED_COPPER_BARS, OXIDIZED_COPPER_BARS);

		metalPillarModel(bsmg, CUT_COPPER_PILLAR, Blocks.CUT_COPPER);
		metalPillarModel(bsmg, EXPOSED_CUT_COPPER_PILLAR, Blocks.EXPOSED_CUT_COPPER);
		metalPillarModel(bsmg, WEATHERED_CUT_COPPER_PILLAR, Blocks.WEATHERED_CUT_COPPER);
		metalPillarModel(bsmg, OXIDIZED_CUT_COPPER_PILLAR, Blocks.OXIDIZED_CUT_COPPER);
		copiedMetalPillarModel(bsmg, WAXED_CUT_COPPER_PILLAR, CUT_COPPER_PILLAR);
		copiedMetalPillarModel(bsmg, WAXED_EXPOSED_CUT_COPPER_PILLAR, EXPOSED_CUT_COPPER_PILLAR);
		copiedMetalPillarModel(bsmg, WAXED_WEATHERED_CUT_COPPER_PILLAR, WEATHERED_CUT_COPPER_PILLAR);
		copiedMetalPillarModel(bsmg, WAXED_OXIDIZED_CUT_COPPER_PILLAR, OXIDIZED_CUT_COPPER_PILLAR);

		copiedStairsModel(bsmg, WAXED_COPPER_STAIRS, COPPER_STAIRS);
		copiedStairsModel(bsmg, WAXED_EXPOSED_COPPER_STAIRS, EXPOSED_COPPER_STAIRS);
		copiedStairsModel(bsmg, WAXED_WEATHERED_COPPER_STAIRS, WEATHERED_COPPER_STAIRS);
		copiedStairsModel(bsmg, WAXED_OXIDIZED_COPPER_STAIRS, OXIDIZED_COPPER_STAIRS);
		copiedSlabModel(bsmg, WAXED_COPPER_SLAB, COPPER_SLAB, Blocks.COPPER_BLOCK);
		copiedSlabModel(bsmg, WAXED_EXPOSED_COPPER_SLAB, EXPOSED_COPPER_SLAB, Blocks.EXPOSED_COPPER);
		copiedSlabModel(bsmg, WAXED_WEATHERED_COPPER_SLAB, WEATHERED_COPPER_SLAB, Blocks.WEATHERED_COPPER);
		copiedSlabModel(bsmg, WAXED_OXIDIZED_COPPER_SLAB, OXIDIZED_COPPER_SLAB, Blocks.OXIDIZED_COPPER);
		copiedWallModel(bsmg, WAXED_COPPER_WALL, COPPER_WALL);
		copiedWallModel(bsmg, WAXED_EXPOSED_COPPER_WALL, EXPOSED_COPPER_WALL);
		copiedWallModel(bsmg, WAXED_WEATHERED_COPPER_WALL, WEATHERED_COPPER_WALL);
		copiedWallModel(bsmg, WAXED_OXIDIZED_COPPER_WALL, OXIDIZED_COPPER_WALL);

		copiedSimpleBlockstate(bsmg, WAXED_COPPER_GRATE, COPPER_GRATE);
		copiedSimpleBlockstate(bsmg, WAXED_EXPOSED_COPPER_GRATE, EXPOSED_COPPER_GRATE);
		copiedSimpleBlockstate(bsmg, WAXED_WEATHERED_COPPER_GRATE, WEATHERED_COPPER_GRATE);
		copiedSimpleBlockstate(bsmg, WAXED_OXIDIZED_COPPER_GRATE, OXIDIZED_COPPER_GRATE);

		copiedSimpleBlockstate(bsmg, WAXED_CHISELED_COPPER, CHISELED_COPPER);
		copiedSimpleBlockstate(bsmg, WAXED_EXPOSED_CHISELED_COPPER, EXPOSED_CHISELED_COPPER);
		copiedSimpleBlockstate(bsmg, WAXED_WEATHERED_CHISELED_COPPER, WEATHERED_CHISELED_COPPER);
		copiedSimpleBlockstate(bsmg, WAXED_OXIDIZED_CHISELED_COPPER, OXIDIZED_CHISELED_COPPER);

		copiedDoorModel(bsmg, WAXED_COPPER_DOOR, COPPER_DOOR);
		copiedDoorModel(bsmg, WAXED_EXPOSED_COPPER_DOOR, EXPOSED_COPPER_DOOR);
		copiedDoorModel(bsmg, WAXED_WEATHERED_COPPER_DOOR, WEATHERED_COPPER_DOOR);
		copiedDoorModel(bsmg, WAXED_OXIDIZED_COPPER_DOOR, OXIDIZED_COPPER_DOOR);

		copiedTrapdoorModel(bsmg, WAXED_COPPER_TRAPDOOR, COPPER_TRAPDOOR, false);
		copiedTrapdoorModel(bsmg, WAXED_EXPOSED_COPPER_TRAPDOOR, EXPOSED_COPPER_TRAPDOOR, false);
		copiedTrapdoorModel(bsmg, WAXED_WEATHERED_COPPER_TRAPDOOR, WEATHERED_COPPER_TRAPDOOR, false);
		copiedTrapdoorModel(bsmg, WAXED_OXIDIZED_COPPER_TRAPDOOR, OXIDIZED_COPPER_TRAPDOOR, false);

		copiedSimpleBlockstate(bsmg, WAXED_COPPER_BRICKS, COPPER_BRICKS);
		copiedSimpleBlockstate(bsmg, WAXED_EXPOSED_COPPER_BRICKS, EXPOSED_COPPER_BRICKS);
		copiedSimpleBlockstate(bsmg, WAXED_WEATHERED_COPPER_BRICKS, WEATHERED_COPPER_BRICKS);
		copiedSimpleBlockstate(bsmg, WAXED_OXIDIZED_COPPER_BRICKS, OXIDIZED_COPPER_BRICKS);
		copiedStairsModel(bsmg, WAXED_COPPER_BRICK_STAIRS, COPPER_BRICK_STAIRS);
		copiedStairsModel(bsmg, WAXED_EXPOSED_COPPER_BRICK_STAIRS, EXPOSED_COPPER_BRICK_STAIRS);
		copiedStairsModel(bsmg, WAXED_WEATHERED_COPPER_BRICK_STAIRS, WEATHERED_COPPER_BRICK_STAIRS);
		copiedStairsModel(bsmg, WAXED_OXIDIZED_COPPER_BRICK_STAIRS, OXIDIZED_COPPER_BRICK_STAIRS);
		copiedSlabModel(bsmg, WAXED_COPPER_BRICK_SLAB, COPPER_BRICK_SLAB, COPPER_BRICKS);
		copiedSlabModel(bsmg, WAXED_EXPOSED_COPPER_BRICK_SLAB, EXPOSED_COPPER_BRICK_SLAB, EXPOSED_COPPER_BRICKS);
		copiedSlabModel(bsmg, WAXED_WEATHERED_COPPER_BRICK_SLAB, WEATHERED_COPPER_BRICK_SLAB, WEATHERED_COPPER_BRICKS);
		copiedSlabModel(bsmg, WAXED_OXIDIZED_COPPER_BRICK_SLAB, OXIDIZED_COPPER_BRICK_SLAB, OXIDIZED_COPPER_BRICKS);
		copiedWallModel(bsmg, WAXED_COPPER_BRICK_WALL, COPPER_BRICK_WALL);
		copiedWallModel(bsmg, WAXED_EXPOSED_COPPER_BRICK_WALL, EXPOSED_COPPER_BRICK_WALL);
		copiedWallModel(bsmg, WAXED_WEATHERED_COPPER_BRICK_WALL, WEATHERED_COPPER_BRICK_WALL);
		copiedWallModel(bsmg, WAXED_OXIDIZED_COPPER_BRICK_WALL, OXIDIZED_COPPER_BRICK_WALL);
		copiedWeightedPressurePlateModel(bsmg, WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE, MEDIUM_WEIGHTED_PRESSURE_PLATE);
		copiedWeightedPressurePlateModel(bsmg, WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE, EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		copiedWeightedPressurePlateModel(bsmg, WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE, WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		copiedWeightedPressurePlateModel(bsmg, WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE, OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		//</editor-fold>
		//<editor-fold desc="Netherite">
		lanternModel(bsmg, NETHERITE_LANTERN);
		lanternModel(bsmg, NETHERITE_SOUL_LANTERN, NETHERITE_LANTERN);
		lanternModel(bsmg, NETHERITE_ENDER_LANTERN, NETHERITE_LANTERN);
		metalPillarModel(bsmg, CUT_NETHERITE_PILLAR, CUT_NETHERITE);
		//</editor-fold>
		//<editor-fold desc="Bone">
		boneTorchModel(bsmg, BONE_TORCH);
		boneTorchModel(bsmg, BONE_SOUL_TORCH, BONE_TORCH);
		boneTorchModel(bsmg, BONE_ENDER_TORCH, BONE_TORCH);
		boneTorchModel(bsmg, UNDERWATER_BONE_TORCH, BONE_TORCH);
		boneLadderModel(bsmg, BONE_LADDER);
		topSideSlabModel(bsmg, BONE_SLAB, Blocks.BONE_BLOCK);
		parentedItem(bsmg, BONE_ROW.asItem(), Texture.getSubId(BONE_ROW.asBlock(), "_inventory"));
		//</editor-fold>
		//<editor-fold desc="Mushrooms">
		count4MushroomModel(bsmg, DEATH_CAP_MUSHROOM, false);
		count4MushroomModel(bsmg, BLUE_NETHERSHROOM, true);
		//Blue Mooshrooms
		mushroomBlockModel(bsmg, BLUE_MUSHROOM_BLOCK);
		explicitBlockGeneratedItem(bsmg, BLUE_MUSHROOM);
		potModel(bsmg, BLUE_MUSHROOM);
		//</editor-fold>
		//<editor-fold desc="Gourds">
		carvedPumpkinModel(bsmg, SOUL_JACK_O_LANTERN, Blocks.PUMPKIN);
		carvedPumpkinModel(bsmg, ENDER_JACK_O_LANTERN, Blocks.PUMPKIN);
		frontSidePumpkinModel(bsmg, BURNT_PUMPKIN);
		pumpkinModel(bsmg, WHITE_PUMPKIN);
		carvedPumpkinModel(bsmg, CARVED_WHITE_PUMPKIN, WHITE_PUMPKIN);
		carvedPumpkinModel(bsmg, WHITE_JACK_O_LANTERN, WHITE_PUMPKIN);
		carvedPumpkinModel(bsmg, WHITE_SOUL_JACK_O_LANTERN, WHITE_PUMPKIN);
		carvedPumpkinModel(bsmg, WHITE_ENDER_JACK_O_LANTERN, WHITE_PUMPKIN);
		parentedGourdStemModels(bsmg, WHITE_PUMPKIN_STEM, ATTACHED_WHITE_PUMPKIN_STEM, Blocks.PUMPKIN_STEM, Blocks.ATTACHED_PUMPKIN_STEM);
		pumpkinModel(bsmg, ROTTEN_PUMPKIN);
		carvedPumpkinModel(bsmg, CARVED_ROTTEN_PUMPKIN, ROTTEN_PUMPKIN);
		carvedPumpkinModel(bsmg, ROTTEN_JACK_O_LANTERN, ROTTEN_PUMPKIN);
		carvedPumpkinModel(bsmg, ROTTEN_SOUL_JACK_O_LANTERN, ROTTEN_PUMPKIN);
		carvedPumpkinModel(bsmg, ROTTEN_ENDER_JACK_O_LANTERN, ROTTEN_PUMPKIN);
		carvedPumpkinModel(bsmg, CARVED_MELON, Blocks.MELON);
		carvedPumpkinModel(bsmg, MELON_LANTERN, Blocks.MELON);
		carvedPumpkinModel(bsmg, SOUL_MELON_LANTERN, Blocks.MELON);
		carvedPumpkinModel(bsmg, ENDER_MELON_LANTERN, Blocks.MELON);
		//</editor-fold>
		//<editor-fold desc="Gunpowder">
		bsmg.blockStateCollector.accept(MultipartBlockStateSupplier.create(GUNPOWDER_FUSE.asBlock()).with(
						When.anyOf(
								When.create()
										.set(Properties.NORTH_WIRE_CONNECTION, WireConnection.NONE)
										.set(Properties.EAST_WIRE_CONNECTION, WireConnection.NONE)
										.set(Properties.SOUTH_WIRE_CONNECTION, WireConnection.NONE)
										.set(Properties.WEST_WIRE_CONNECTION, WireConnection.NONE),
								When.create()
										.set(Properties.NORTH_WIRE_CONNECTION, WireConnection.SIDE, WireConnection.UP)
										.set(Properties.EAST_WIRE_CONNECTION, WireConnection.SIDE, WireConnection.UP),
								When.create()
										.set(Properties.EAST_WIRE_CONNECTION, WireConnection.SIDE, WireConnection.UP)
										.set(Properties.SOUTH_WIRE_CONNECTION, WireConnection.SIDE, WireConnection.UP),
								When.create().set(Properties.SOUTH_WIRE_CONNECTION, WireConnection.SIDE, WireConnection.UP)
										.set(Properties.WEST_WIRE_CONNECTION, WireConnection.SIDE, WireConnection.UP),
								When.create().set(Properties.WEST_WIRE_CONNECTION, WireConnection.SIDE, WireConnection.UP)
										.set(Properties.NORTH_WIRE_CONNECTION, WireConnection.SIDE, WireConnection.UP)),
						BlockStateVariant.create().put(VariantSettings.MODEL, ModId.ID("block/gunpowder_fuse_dot")))
				.with(When.create().set(Properties.NORTH_WIRE_CONNECTION, WireConnection.SIDE, WireConnection.UP),
						BlockStateVariant.create().put(VariantSettings.MODEL, ModId.ID("block/gunpowder_fuse_side0")))
				.with(When.create().set(Properties.SOUTH_WIRE_CONNECTION, WireConnection.SIDE, WireConnection.UP),
						BlockStateVariant.create().put(VariantSettings.MODEL, ModId.ID("block/gunpowder_fuse_side_alt0")))
				.with(When.create().set(Properties.EAST_WIRE_CONNECTION, WireConnection.SIDE, WireConnection.UP),
						BlockStateVariant.create()
								.put(VariantSettings.MODEL, ModId.ID("block/gunpowder_fuse_side_alt1"))
								.put(VariantSettings.Y, VariantSettings.Rotation.R270))
				.with(When.create().set(Properties.WEST_WIRE_CONNECTION, WireConnection.SIDE, WireConnection.UP),
						BlockStateVariant.create()
								.put(VariantSettings.MODEL, ModId.ID("block/gunpowder_fuse_side1"))
								.put(VariantSettings.Y, VariantSettings.Rotation.R270))
				.with(When.create().set(Properties.NORTH_WIRE_CONNECTION, WireConnection.UP),
						BlockStateVariant.create().put(VariantSettings.MODEL, ModId.ID("block/gunpowder_fuse_up")))
				.with(When.create().set(Properties.EAST_WIRE_CONNECTION, WireConnection.UP),
						BlockStateVariant.create().put(VariantSettings.MODEL, ModId.ID("block/gunpowder_fuse_up"))
								.put(VariantSettings.Y, VariantSettings.Rotation.R90))
				.with(When.create().set(Properties.SOUTH_WIRE_CONNECTION, WireConnection.UP),
						BlockStateVariant.create().put(VariantSettings.MODEL, ModId.ID("block/gunpowder_fuse_up"))
								.put(VariantSettings.Y, VariantSettings.Rotation.R180))
				.with(When.create().set(Properties.WEST_WIRE_CONNECTION, WireConnection.UP),
						BlockStateVariant.create().put(VariantSettings.MODEL, ModId.ID("block/gunpowder_fuse_up"))
								.put(VariantSettings.Y, VariantSettings.Rotation.R270)));
		//</editor-fold>
		//Suspicious Sand
		suspiciousSandModel(bsmg, SUSPICIOUS_SAND);
		suspiciousSandModel(bsmg, SUSPICIOUS_GRAVEL);
		//<editor-fold desc="Infested Blocks">
		copiedSimpleBlockstate(bsmg, INFESTED_MOSSY_CHISELED_STONE_BRICKS, MOSSY_CHISELED_STONE_BRICKS);
		copiedSimpleBlockstate(bsmg, INFESTED_COBBLED_DEEPSLATE, Blocks.COBBLED_DEEPSLATE);
		copiedSimpleBlockstate(bsmg, INFESTED_MOSSY_COBBLED_DEEPSLATE, MOSSY_COBBLED_DEEPSLATE);
		copiedSimpleBlockstate(bsmg, INFESTED_ANDESITE, Blocks.ANDESITE);
		copiedSimpleBlockstate(bsmg, INFESTED_ANDESITE_BRICKS, ANDESITE_BRICKS);
		copiedSimpleBlockstate(bsmg, INFESTED_CHISELED_ANDESITE_BRICKS, CHISELED_ANDESITE_BRICKS);
		copiedSimpleBlockstate(bsmg, INFESTED_DIORITE, Blocks.DIORITE);
		copiedSimpleBlockstate(bsmg, INFESTED_DIORITE_BRICKS, DIORITE_BRICKS);
		copiedSimpleBlockstate(bsmg, INFESTED_CHISELED_DIORITE_BRICKS, CHISELED_DIORITE_BRICKS);
		copiedSimpleBlockstate(bsmg, INFESTED_GRANITE, Blocks.GRANITE);
		copiedSimpleBlockstate(bsmg, INFESTED_GRANITE_BRICKS, GRANITE_BRICKS);
		copiedSimpleBlockstate(bsmg, INFESTED_CHISELED_GRANITE_BRICKS, CHISELED_GRANITE_BRICKS);
		copiedSimpleBlockstate(bsmg, INFESTED_TUFF, Blocks.TUFF);
		//</editor-fold>
		//Misc
		parentedItem(bsmg, JUICER);
		singletonModel(bsmg, LIGHT_BLUE_TARGET, TexturedModel.CUBE_COLUMN);
		mudBricksModel(bsmg, FLINT_BRICKS);
		explicitBlockParentedItem(bsmg, HEDGE_BLOCK);
		rootsModel(bsmg, MYCELIUM_ROOTS);
		pillarModel(bsmg, SUGAR_CANE_BALE);
		horizontalPillarModel(bsmg, SUGAR_CANE_BLOCK);
		topSideSlabModel(bsmg, SUGAR_CANE_BLOCK_SLAB, SUGAR_CANE_BLOCK);
		parentedItem(bsmg, SUGAR_CANE_ROW.asItem(), Texture.getSubId(SUGAR_CANE_ROW.asBlock(), "_inventory"));
		singletonModel(bsmg, CHISELED_TUFF, TexturedModel.END_FOR_TOP_CUBE_COLUMN);
		singletonModel(bsmg, CHISELED_TUFF_BRICKS, TexturedModel.END_FOR_TOP_CUBE_COLUMN);
		generatedItemModel(bsmg, KILL_POTION, ModId.ID("item/pink_potion"));
		generatedItemModel(bsmg, DISTILLED_WATER_BOTTLE, ModId.ID("item/water_bottle"));
		glazedTerracottaModel(bsmg, GLAZED_TERRACOTTA);
		glazedTerracottaSlabModel(bsmg, GLAZED_TERRACOTTA_SLAB, GLAZED_TERRACOTTA);
		for (DyeColor color : DyeColor.values()) glazedTerracottaSlabModel(bsmg, GLAZED_TERRACOTTA_SLABS.get(color), ColorUtil.GetGlazedTerracottaBlock(color));
		berryLeavesModel(bsmg, SWEET_BERRY_LEAVES);
		berryLeavesModel(bsmg, GRAPE_LEAVES);
		vinesModel(bsmg, GRAPE_VINES, GRAPE_VINES_PLANT);
		//Slime
		explicitBlockParentedItem(bsmg, BLUE_SLIME_BLOCK);
		explicitBlockParentedItem(bsmg, PINK_SLIME_BLOCK);
		slimeLanternModel(bsmg, MAGMA_CUBE_LANTERN, ModModels.TEMPLATE_MAGMA_CUBE_LANTERN);
		slimeLanternModel(bsmg, SLIME_LANTERN, ModModels.TEMPLATE_SLIME_LANTERN);
		slimeLanternModel(bsmg, TROPICAL_SLIME_LANTERN, ModModels.TEMPLATE_SLIME_LANTERN);
		slimeLanternModel(bsmg, PINK_SLIME_LANTERN, ModModels.TEMPLATE_SLIME_LANTERN);
		//<editor-fold desc="Wool">
		rainbowBlockModel(bsmg, RAINBOW_WOOL);
		rainbowSlabModel(bsmg, RAINBOW_WOOL_SLAB, RAINBOW_WOOL.asBlock());
		rainbowCarpetModel(bsmg, RAINBOW_CARPET, RAINBOW_WOOL.asBlock());
		bedModel(bsmg, RAINBOW_BED, ModId.ID("block/rainbow_wool_particle"));
		//</editor-fold>
		//<editor-fold desc="Fleece">
		for (DyeColor color : DyeColor.values()) {
			BlockContainer fleece = FLEECE.get(color), carpet = FLEECE_CARPETS.get(color);
			Block fleeceBlock = fleece.asBlock(), carpetBlock = carpet.asBlock();
			registerWoolAndCarpet(bsmg, fleeceBlock, carpetBlock);
			parentedItem(bsmg, fleece.asItem(), fleeceBlock);
			parentedItem(bsmg, carpet.asItem(), carpetBlock);
		}
		rainbowBlockModel(bsmg, RAINBOW_FLEECE);
		rainbowSlabModel(bsmg, RAINBOW_FLEECE_SLAB, RAINBOW_FLEECE.asBlock());
		rainbowCarpetModel(bsmg, RAINBOW_FLEECE_CARPET, RAINBOW_FLEECE.asBlock());
		//</editor-fold>
		//<editor-fold desc="Glow Lichen">
		registerWoolAndCarpet(bsmg, GLOW_LICHEN_BLOCK.asBlock(), GLOW_LICHEN_CARPET.asBlock());
		parentedItem(bsmg, GLOW_LICHEN_BLOCK);
		parentedItem(bsmg, GLOW_LICHEN_CARPET);
		bedModel(bsmg, GLOW_LICHEN_BED, GLOW_LICHEN_BLOCK.asBlock());
		//</editor-fold>
		bedModel(bsmg, MOSS_BED, Blocks.MOSS_BLOCK);
		//Sculk & Deep Dark
		singletonModel(bsmg, SCULK_CATALYST, TexturedModel.CUBE_BOTTOM_TOP);
		parentedItem(bsmg, SCULK_SHRIEKER);
		generatedItemModel(bsmg, SCULK_VEIN);
		parentedItem(bsmg, CALIBRATED_SCULK_SENSOR.asItem(), Texture.getSubId(CALIBRATED_SCULK_SENSOR.asBlock(), "_inactive"));
		singletonModel(bsmg, REINFORCED_DEEPSLATE, TexturedModel.CUBE_BOTTOM_TOP);
		stonePillarModel(bsmg, SHALE);
		stonePillarModel(bsmg, END_STONE_PILLAR);
		stonePillarModel(bsmg, END_SHALE);
		//<editor-fold desc="End Rock">
		stonePillarModel(bsmg, END_ROCK);
		facingTopBottomSidesPillarModel(bsmg, END_ROCK_SHALE, Texture.getSubId(END_ROCK.asBlock(), "_top"), Texture.getSubId(END_SHALE.asBlock(), "_top"));
		facingTopBottomSidesPillarModel(bsmg, END_SHALE_ROCK, Texture.getSubId(END_SHALE.asBlock(), "_top"), Texture.getSubId(END_ROCK.asBlock(), "_top"));
		//</editor-fold>
		//<editor-fold desc="Echo">
		amethystModel(bsmg, ECHO_CLUSTER);
		amethystModel(bsmg, LARGE_ECHO_BUD);
		amethystModel(bsmg, MEDIUM_ECHO_BUD);
		amethystModel(bsmg, SMALL_ECHO_BUD);
		//</editor-fold>
		mudBricksModel(bsmg, SCULK_STONE_BRICKS);
		//<editor-fold desc="Sculk Turf">
		sculkTurfModel(bsmg, ANDESITE_SCULK_TURF, Blocks.ANDESITE);
		sculkTurfModel(bsmg, BASALT_SCULK_TURF, Texture.getSubId(Blocks.BASALT, "_top"));
		sculkTurfModel(bsmg, BLACKSTONE_SCULK_TURF, Texture.getSubId(Blocks.BLACKSTONE, "_top"));
		sculkTurfModel(bsmg, CALCITE_SCULK_TURF, Blocks.CALCITE);
		sculkTurfModel(bsmg, DEEPSLATE_SCULK_TURF, Texture.getSubId(Blocks.DEEPSLATE, "_top"));
		sculkTurfModel(bsmg, DIORITE_SCULK_TURF, Blocks.DIORITE);
		sculkTurfModel(bsmg, DRIPSTONE_SCULK_TURF, Blocks.DRIPSTONE_BLOCK);
		sculkTurfModel(bsmg, END_SHALE_SCULK_TURF, Texture.getSubId(END_SHALE.asBlock(), "_top"));
		sculkTurfModel(bsmg, END_STONE_SCULK_TURF, Blocks.END_STONE);
		sculkTurfModel(bsmg, END_ROCK_SCULK_TURF, Texture.getSubId(END_ROCK.asBlock(), "_top"));
		sculkTurfModel(bsmg, END_ROCK_SHALE_SCULK_TURF, Texture.getSubId(END_SHALE.asBlock(), "_top"));
		sculkTurfModel(bsmg, END_SHALE_ROCK_SCULK_TURF, Texture.getSubId(END_ROCK.asBlock(), "_top"));
		sculkTurfModel(bsmg, GRANITE_SCULK_TURF, Blocks.GRANITE);
		sculkTurfModel(bsmg, NETHERRACK_SCULK_TURF, Blocks.NETHERRACK);
		sculkTurfModel(bsmg, RED_SANDSTONE_SCULK_TURF, Texture.getSubId(Blocks.RED_SANDSTONE, "_bottom"));
		sculkTurfModel(bsmg, SANDSTONE_SCULK_TURF, Texture.getSubId(Blocks.SANDSTONE, "_bottom"));
		sculkTurfModel(bsmg, SHALE_SCULK_TURF, Texture.getSubId(SHALE.asBlock(), "_top"));
		sculkTurfModel(bsmg, SMOOTH_BASALT_SCULK_TURF, Blocks.SMOOTH_BASALT);
		sculkTurfModel(bsmg, STONE_SCULK_TURF, Blocks.STONE);
		sculkTurfModel(bsmg, TUFF_SCULK_TURF, Blocks.TUFF);
		//</editor-fold>
		topSideSlabModel(bsmg, POLISHED_BASALT_SLAB, Blocks.POLISHED_BASALT);
		//<editor-fold desc="Charred Wood">
		logModel(bsmg, CHARRED_LOG, CHARRED_WOOD);
		logModel(bsmg, STRIPPED_CHARRED_LOG, STRIPPED_CHARRED_WOOD);
		//</editor-fold>
		mudBricksModel(bsmg, MUD_BRICKS);
		explicitModelCommon(bsmg, MUD_FLUID_BLOCK);
		explicitModelCommon(bsmg, BLOOD_FLUID_BLOCK);
		//Milk cauldrons
		explicitModelCommon(bsmg, MILK_CAULDRON);
		explicitModelCommon(bsmg, COTTAGE_CHEESE_CAULDRON);
		explicitModelCommon(bsmg, CHEESE_CAULDRON);
		//Mooblossom
		crossModel(bsmg, MOOBLOSSOM_MAGENTA_TULIP);
		crossModel(bsmg, MOOBLOSSOM_RED_TULIP);
		crossModel(bsmg, MOOBLOSSOM_ORANGE_TULIP);
		crossModel(bsmg, MOOBLOSSOM_WHITE_TULIP);
		crossModel(bsmg, MOOBLOSSOM_PINK_TULIP);
		//<editor-fold desc="Mangrove">
		logModel(bsmg, MANGROVE_LOG, MANGROVE_WOOD);
		logModel(bsmg, STRIPPED_MANGROVE_LOG, STRIPPED_MANGROVE_WOOD);
		explicitBlockParentedItem(bsmg, MANGROVE_ROOTS);
		uprightPillarModel(bsmg, MUDDY_MANGROVE_ROOTS);
		bsmg.registerSimpleState(MANGROVE_PROPAGULE.getPottedBlock());
		generatedItemModel(bsmg, MANGROVE_PROPAGULE.asItem());
		//</editor-fold>
		//<editor-fold desc="Cherry">
		logModel(bsmg, CHERRY_LOG, CHERRY_WOOD);
		logModel(bsmg, STRIPPED_CHERRY_LOG, STRIPPED_CHERRY_WOOD);
		//</editor-fold>
		//Pink Petals
		generatedItemModel(bsmg, PINK_PETALS);
		//<editor-fold desc="Cassia">
		logModel(bsmg, CASSIA_LOG, CASSIA_WOOD);
		logModel(bsmg, STRIPPED_CASSIA_LOG, STRIPPED_CASSIA_WOOD);
		//</editor-fold>
		//<editor-fold desc="Dogwood">
		logModel(bsmg, DOGWOOD_LOG, DOGWOOD_WOOD);
		logModel(bsmg, STRIPPED_DOGWOOD_LOG, STRIPPED_DOGWOOD_WOOD);
		//</editor-fold>
		stemModel(bsmg, GILDED_STEM, GILDED_HYPHAE);
		stemModel(bsmg, STRIPPED_GILDED_STEM, STRIPPED_GILDED_HYPHAE);
		rootsModel(bsmg, GILDED_ROOTS);
		topBottomSidesModel(bsmg, GILDED_NYLIUM, GILDED_NYLIUM.asBlock(), Blocks.NETHERRACK);
		explicitBlockGeneratedItem(bsmg, GILDED_FUNGUS);
		potModel(bsmg, GILDED_FUNGUS);
		bsmg.registerCrop(WARPED_WART.asBlock(), Properties.AGE_3, 0, 1, 1, 2);
		bsmg.registerCrop(GILDED_WART.asBlock(), Properties.AGE_3, 0, 1, 1, 2);
		//Torchflower
		pottedModel(bsmg, TORCHFLOWER);
		//<editor-fold desc="Bamboo">
		parentedItem(bsmg, BAMBOO_BLOCK);
		parentedItem(bsmg, STRIPPED_BAMBOO_BLOCK);
		logModel(bsmg, BAMBOO_LOG, BAMBOO_WOOD);
		logModel(bsmg, STRIPPED_BAMBOO_LOG, STRIPPED_BAMBOO_WOOD);
		//Extended
		parentedItem(bsmg, BAMBOO_ROW.asItem(), Texture.getSubId(BAMBOO_ROW.asBlock(), "_inventory"));
		//Dried Bamboo
		explicitModelCommon(bsmg, DRIED_BAMBOO.getPottedBlock());
		handheldItemModel(bsmg, DRIED_BAMBOO.asItem());
		parentedItem(bsmg, DRIED_BAMBOO_BLOCK);
		parentedItem(bsmg, STRIPPED_DRIED_BAMBOO_BLOCK);
		logModel(bsmg, DRIED_BAMBOO_LOG, DRIED_BAMBOO_WOOD);
		logModel(bsmg, STRIPPED_DRIED_BAMBOO_LOG, STRIPPED_DRIED_BAMBOO_WOOD);
		parentedItem(bsmg, DRIED_BAMBOO_ROW.asItem(), Texture.getSubId(DRIED_BAMBOO_ROW.asBlock(), "_inventory"));
		//</editor-fold>
		//<editor-fold desc="Frogs">
		horizontalPillarModel(bsmg, OCHRE_FROGLIGHT);
		horizontalPillarModel(bsmg, VERDANT_FROGLIGHT);
		horizontalPillarModel(bsmg, PEARLESCENT_FROGLIGHT);
		frogspawnModel(bsmg, FROGSPAWN);
		//</editor-fold>
		//<editor-fold desc="Tall Flowers">
		tallFlowerModel(bsmg, AMARANTH);
		tallFlowerModel(bsmg, BLUE_ROSE_BUSH);
		tallFlowerModel(bsmg, TALL_ALLIUM, Blocks.ALLIUM, ModId.ID("block/allium_stalk"));
		tallFlowerModel(bsmg, TALL_PINK_ALLIUM, PINK_ALLIUM.asBlock(), ModId.ID("block/allium_stalk"));
		tallFlowerModel(bsmg, TALL_VANILLA);
		//</editor-fold>
		//<editor-fold desc="Flower Parts">
		flowerPartModels(bsmg, ModId.ID("item/daisy_seeds"), ModId.ID("block/daisy_seeds"), PINK_DAISY_PARTS, OXEYE_DAISY_PARTS);
		flowerPartModels(bsmg, ModId.ID("item/rose_seeds"), ModId.ID("block/rose_seeds"), ROSE_PARTS, BLUE_ROSE_PARTS, WITHER_ROSE_PARTS);
		flowerPartModels(bsmg, ModId.ID("item/allium_seeds"), ModId.ID("block/allium_seeds"), PINK_ALLIUM_PARTS, ALLIUM_PARTS);
		flowerPartModels(bsmg, ModId.ID("item/orchid_seeds"), ModId.ID("block/orchid_seeds"), INDIGO_ORCHID_PARTS, MAGENTA_ORCHID_PARTS,
				ORANGE_ORCHID_PARTS, PURPLE_ORCHID_PARTS, RED_ORCHID_PARTS, WHITE_ORCHID_PARTS, YELLOW_ORCHID_PARTS, BLUE_ORCHID_PARTS);
		flowerPartModels(bsmg, ModId.ID("item/tulip_seeds"), ModId.ID("block/tulip_seeds"), MAGENTA_TULIP_PARTS, ORANGE_TULIP_PARTS,
				PINK_TULIP_PARTS, RED_TULIP_PARTS, WHITE_TULIP_PARTS);
		flowerPartModels(bsmg, ModId.ID("item/peony_seeds"), ModId.ID("block/peony_seeds"), PAEONIA_PARTS, PEONY_PARTS);
		flowerPartModels(bsmg, ModId.ID("item/lily_seeds"), ModId.ID("block/lily_seeds"), LILY_OF_THE_VALLEY_PARTS);
		//</editor-fold>

		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(FACETING_TABLE.asBlock(), BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockModelId(FACETING_TABLE.asBlock()))).coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
		parentedItem(bsmg, FACETING_TABLE);

		//Statues
		explicitStatueModel(bsmg, CREEPER_ANATOMY_STATUE);
		//Skulls
		skullModel(bsmg, PIGLIN_HEAD.asItem());
		skullModel(bsmg, ZOMBIFIED_PIGLIN_HEAD.asItem());
		//Ragdoll
		skullModel(bsmg, RAGDOLL.asItem());
		explicitModelCommon(bsmg, RAGDOLL);
		//Recovery Compass
		for (int i = 0; i < 32; i++) {
			String suffix = i < 10 ? "_0" : "_";
			Identifier identifier = Texture.getSubId(RECOVERY_COMPASS, suffix + i);
			Models.GENERATED.upload(identifier, Texture.layer0(identifier), bsmg.modelCollector);
		}

		if (ModConfig.REGISTER_HAVEN_MOD) {
			//<editor-fold desc="Anchors">
			explicitModelCommon(bsmg, HavenMod.ANCHOR);
			explicitModelCommon(bsmg, HavenMod.BROKEN_ANCHOR);
			explicitModelCommon(bsmg, HavenMod.SUBSTITUTE_ANCHOR_BLOCK);
			generatedItemModels(bsmg, HavenMod.ANCHOR, HavenMod.BROKEN_ANCHOR);
			for (Item item : HavenMod.ANCHOR_CORES.values()) {
				Identifier id = ModId.ID("entity/anchor/" + Registry.ITEM.getId(item).getPath());
				Models.CUBE_ALL.upload(getItemModelId(item), Texture.all(id), bsmg.modelCollector);
			}
			//</editor-fold>
			flowerPartModels(bsmg, ModId.ID("item/carnation_seeds"), ModId.ID("block/carnation_seeds"), HavenMod.CARNATION_PARTS.values().toArray(FlowerPartContainer[]::new));
			//TNT
			topBottomSidesModel(bsmg, HavenMod.SHARP_TNT);
			topBottomSidesModel(bsmg, HavenMod.CHUNKEATER_TNT);
			topBottomSidesModel(bsmg, HavenMod.VIOLENT_TNT);
			topBottomSidesModel(bsmg, HavenMod.DEVOURING_TNT);
			topBottomSidesModel(bsmg, HavenMod.CATALYZING_TNT);
			topBottomSidesModel(bsmg, HavenMod.SOFT_TNT);
			//<editor-fold desc="Soleil Gourds">
			carvedPumpkinModel(bsmg, HavenMod.SOLEIL_CARVED_PUMPKIN, Blocks.PUMPKIN);
			carvedPumpkinModel(bsmg, HavenMod.SOLEIL_JACK_O_LANTERN, Blocks.PUMPKIN);
			carvedPumpkinModel(bsmg, HavenMod.SOLEIL_SOUL_JACK_O_LANTERN, Blocks.PUMPKIN);
			carvedPumpkinModel(bsmg, HavenMod.SOLEIL_ENDER_JACK_O_LANTERN, Blocks.PUMPKIN);
			carvedPumpkinModel(bsmg, HavenMod.SOLEIL_CARVED_WHITE_PUMPKIN, WHITE_PUMPKIN);
			carvedPumpkinModel(bsmg, HavenMod.SOLEIL_WHITE_JACK_O_LANTERN, WHITE_PUMPKIN);
			carvedPumpkinModel(bsmg, HavenMod.SOLEIL_WHITE_SOUL_JACK_O_LANTERN, WHITE_PUMPKIN);
			carvedPumpkinModel(bsmg, HavenMod.SOLEIL_WHITE_ENDER_JACK_O_LANTERN, WHITE_PUMPKIN);
			carvedPumpkinModel(bsmg, HavenMod.SOLEIL_CARVED_ROTTEN_PUMPKIN, ROTTEN_PUMPKIN);
			carvedPumpkinModel(bsmg, HavenMod.SOLEIL_ROTTEN_JACK_O_LANTERN, ROTTEN_PUMPKIN);
			carvedPumpkinModel(bsmg, HavenMod.SOLEIL_ROTTEN_SOUL_JACK_O_LANTERN, ROTTEN_PUMPKIN);
			carvedPumpkinModel(bsmg, HavenMod.SOLEIL_ROTTEN_ENDER_JACK_O_LANTERN, ROTTEN_PUMPKIN);
			carvedPumpkinModel(bsmg, HavenMod.SOLEIL_CARVED_MELON, Blocks.MELON);
			carvedPumpkinModel(bsmg, HavenMod.SOLEIL_MELON_LANTERN, Blocks.MELON);
			carvedPumpkinModel(bsmg, HavenMod.SOLEIL_SOUL_MELON_LANTERN, Blocks.MELON);
			carvedPumpkinModel(bsmg, HavenMod.SOLEIL_ENDER_MELON_LANTERN, Blocks.MELON);
			//</editor-fold>
			bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(HavenMod.BROKEN_STARS.asBlock(), BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockModelId(HavenMod.BROKEN_STARS.asBlock()))).coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
			parentedItem(bsmg, HavenMod.BROKEN_STARS);
		}
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) { }
}