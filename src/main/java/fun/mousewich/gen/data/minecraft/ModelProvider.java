package fun.mousewich.gen.data.minecraft;

/*
 * Decompiled with CFR 0.1.1 (FabricMC 57d88659).
 */

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

import fun.mousewich.ModBase;
import fun.mousewich.gen.data.fabric.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.model.BlockStateModelGenerator;
import net.minecraft.data.client.model.BlockStateSupplier;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModelProvider implements DataProvider {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
	private final DataGenerator generator;

	public ModelProvider(DataGenerator generator) { this.generator = generator; }

	@Override
	public void run(DataCache cache) {
		Path path = this.generator.getOutput();
		HashMap<Block, BlockStateSupplier> map = Maps.newHashMap();
		Consumer<BlockStateSupplier> consumer = blockStateSupplier -> {
			Block block = blockStateSupplier.getBlock();
			BlockStateSupplier blockStateSupplier2 = map.put(block, blockStateSupplier);
			if (blockStateSupplier2 != null) {
				throw new IllegalStateException("Duplicate blockstate definition for " + block);
			}
		};
		HashMap<Identifier, Supplier<JsonElement>> map2 = Maps.newHashMap();
		HashSet<Item> set = Sets.newHashSet();
		BiConsumer<Identifier, Supplier<JsonElement>> biConsumer = (identifier, supplier) -> {
			Supplier<JsonElement> supplier2 = map2.put(identifier, supplier);
			if (supplier2 != null) {
				throw new IllegalStateException("Duplicate model definition for " + identifier);
			}
		};
		Consumer<Item> consumer2 = set::add;
		//Blockstate / Block Model Generation
		BlockStateModelGenerator bsmg = new BlockStateModelGenerator(consumer, biConsumer, consumer2);
		if (this instanceof FabricModelProvider fabricModelProvider) {
			fabricModelProvider.generateBlockStateModels(bsmg);
		}
		else bsmg.register(); // Fallback to the vanilla registration when not a fabric provider
		//Item Model Generation
		ItemModelGenerator img = new ItemModelGenerator(biConsumer);
		if (this instanceof FabricModelProvider fabricModelProvider) {
			fabricModelProvider.generateItemModels(img);
		}
		else img.register(); // Fallback to the vanilla registration when not a fabric provider
		List<Block> list = Registry.BLOCK.stream().filter(block -> !map.containsKey(block)).toList();
		if (!list.isEmpty()) {
			ModBase.LOGGER.error("Missing blockstate definitions for: " + list);
		}
		HashSet<Item> missingItemSet = Sets.newHashSet();
		Registry.BLOCK.forEach(block -> {
			Item item = Item.BLOCK_ITEMS.get(block);
			if (item != null) {
				if (set.contains(item)) return;
				else missingItemSet.add(item);
				/*Identifier identifier = ModelIds.getItemModelId(item);
				if (!map2.containsKey(identifier)) {
					map2.put(identifier, new SimpleModelSupplier(ModelIds.getBlockModelId(block)));
				}*/
			}
		});
		ModBase.LOGGER.error("Missing item model definitions for: " + missingItemSet.stream().map(item -> {
			Identifier identifier = Registry.ITEM.getId(item);
			if (identifier == Registry.ITEM.getDefaultId()) return item.toString();
			return identifier.toString();
		}).toList());
		this.writeJsons(cache, path, map, ModelProvider::getBlockStateJsonPath);
		this.writeJsons(cache, path, map2, ModelProvider::getModelJsonPath);
	}

	private <T> void writeJsons(DataCache cache, Path root, Map<T, ? extends Supplier<JsonElement>> jsons, BiFunction<Path, T, Path> locator) {
		jsons.forEach((object, supplier) -> {
			Path path2 = locator.apply(root, object);
			try {
				DataProvider.writeToPath(GSON, cache, supplier.get(), path2);
			}
			catch (Exception exception) {
				ModBase.LOGGER.error("Couldn't save {}", path2, exception);
			}
		});
	}

	private static Path getBlockStateJsonPath(Path root, Block block) {
		Identifier identifier = Registry.BLOCK.getId(block);
		return root.resolve("assets/" + identifier.getNamespace() + "/blockstates/" + identifier.getPath() + ".json");
	}

	private static Path getModelJsonPath(Path root, Identifier id) {
		return root.resolve("assets/" + id.getNamespace() + "/models/" + id.getPath() + ".json");
	}

	@Override
	public String getName() { return "Block State Definitions"; }
}

