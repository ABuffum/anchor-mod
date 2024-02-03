package fun.wich.util;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import fun.wich.container.BlockContainer;
import fun.wich.container.IBlockItemContainer;
import fun.wich.container.TorchContainer;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;

import net.minecraft.block.Oxidizable.OxidizationLevel;

import java.util.*;
import java.util.function.Supplier;

import static java.util.Map.entry;

public class OxidationScale<T> {
	protected final T unaffected;
	public T getUnaffected() { return unaffected; }
	protected final T exposed;
	public T getExposed() { return exposed; }
	protected final T weathered;
	public T getWeathered() { return weathered; }
	protected final T oxidized;
	public T getOxidized() { return oxidized; }

	public OxidationScale(T unaffected, T exposed, T weathered, T oxidized) {
		this.unaffected = unaffected;
		this.exposed = exposed;
		this.weathered = weathered;
		this.oxidized = oxidized;
	}


	public static final Set<OxidationScale<Block>> OXIDIZABLE_BLOCKS = new HashSet<>();

	public static final Set<OxidationScale<Item>> OXIDIZABLE_ITEMS = new HashSet<>();

	public static void Initialize() {
		Register(Blocks.COPPER_BLOCK, Blocks.EXPOSED_COPPER, Blocks.WEATHERED_COPPER, Blocks.OXIDIZED_COPPER);
		Register(Items.COPPER_BLOCK, Items.EXPOSED_COPPER, Items.WEATHERED_COPPER, Items.OXIDIZED_COPPER);
		Register(Blocks.CUT_COPPER, Blocks.EXPOSED_CUT_COPPER, Blocks.WEATHERED_CUT_COPPER, Blocks.OXIDIZED_CUT_COPPER);
		Register(Items.CUT_COPPER, Items.EXPOSED_CUT_COPPER, Items.WEATHERED_CUT_COPPER, Items.OXIDIZED_CUT_COPPER);
		Register(Blocks.CUT_COPPER_SLAB, Blocks.EXPOSED_CUT_COPPER_SLAB, Blocks.WEATHERED_CUT_COPPER_SLAB, Blocks.OXIDIZED_CUT_COPPER_SLAB);
		Register(Items.CUT_COPPER_SLAB, Items.EXPOSED_CUT_COPPER_SLAB, Items.WEATHERED_CUT_COPPER_SLAB, Items.OXIDIZED_CUT_COPPER_SLAB);
		Register(Blocks.CUT_COPPER_STAIRS, Blocks.EXPOSED_CUT_COPPER_STAIRS, Blocks.WEATHERED_CUT_COPPER_STAIRS, Blocks.OXIDIZED_CUT_COPPER_STAIRS);
		Register(Items.CUT_COPPER_STAIRS, Items.EXPOSED_CUT_COPPER_STAIRS, Items.WEATHERED_CUT_COPPER_STAIRS, Items.OXIDIZED_CUT_COPPER_STAIRS);
	}

	public static MapColor getMapColor(OxidizationLevel level) {
		if (level == OxidizationLevel.OXIDIZED) return MapColor.TEAL;
		else if (level == OxidizationLevel.WEATHERED) return MapColor.DARK_AQUA;
		else if (level == OxidizationLevel.EXPOSED) return MapColor.TERRACOTTA_LIGHT_GRAY;
		else return MapColor.ORANGE;
	}

	public interface BlockSettingsSupplier {
		AbstractBlock.Settings get(OxidizationLevel level);
	}

	public static final Map<Block, Block> WAXED_BLOCKS = new HashMap<>(Map.ofEntries(
			entry(Blocks.COPPER_BLOCK, Blocks.WAXED_COPPER_BLOCK), entry(Blocks.EXPOSED_COPPER, Blocks.WAXED_EXPOSED_COPPER),
			entry(Blocks.WEATHERED_COPPER, Blocks.WAXED_WEATHERED_COPPER), entry(Blocks.OXIDIZED_COPPER, Blocks.WAXED_OXIDIZED_COPPER),
			entry(Blocks.CUT_COPPER, Blocks.WAXED_CUT_COPPER), entry(Blocks.EXPOSED_CUT_COPPER, Blocks.WAXED_EXPOSED_CUT_COPPER),
			entry(Blocks.WEATHERED_CUT_COPPER, Blocks.WAXED_WEATHERED_CUT_COPPER), entry(Blocks.OXIDIZED_CUT_COPPER, Blocks.WAXED_OXIDIZED_CUT_COPPER),
			entry(Blocks.CUT_COPPER_SLAB, Blocks.WAXED_CUT_COPPER_SLAB), entry(Blocks.EXPOSED_CUT_COPPER_SLAB, Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB),
			entry(Blocks.WEATHERED_CUT_COPPER_SLAB, Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB), entry(Blocks.OXIDIZED_CUT_COPPER_SLAB, Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB),
			entry(Blocks.CUT_COPPER_STAIRS, Blocks.WAXED_CUT_COPPER_STAIRS), entry(Blocks.EXPOSED_CUT_COPPER_STAIRS, Blocks.WAXED_EXPOSED_CUT_COPPER_STAIRS),
			entry(Blocks.WEATHERED_CUT_COPPER_STAIRS, Blocks.WAXED_WEATHERED_CUT_COPPER_STAIRS), entry(Blocks.OXIDIZED_CUT_COPPER_STAIRS, Blocks.WAXED_OXIDIZED_CUT_COPPER_STAIRS)
	));
	public static final Map<Item, Item> WAXED_ITEMS = new HashMap<>(Map.ofEntries(
			entry(Items.COPPER_BLOCK, Items.WAXED_COPPER), entry(Items.EXPOSED_COPPER, Items.WAXED_EXPOSED_COPPER),
			entry(Items.WEATHERED_COPPER, Items.WAXED_WEATHERED_COPPER), entry(Items.OXIDIZED_COPPER, Items.WAXED_OXIDIZED_COPPER),
			entry(Items.CUT_COPPER, Items.WAXED_CUT_COPPER), entry(Items.EXPOSED_CUT_COPPER, Items.WAXED_EXPOSED_CUT_COPPER),
			entry(Items.WEATHERED_CUT_COPPER, Items.WAXED_WEATHERED_CUT_COPPER), entry(Items.OXIDIZED_CUT_COPPER, Items.WAXED_OXIDIZED_CUT_COPPER),
			entry(Items.CUT_COPPER_SLAB, Items.WAXED_CUT_COPPER_SLAB), entry(Items.EXPOSED_CUT_COPPER_SLAB, Items.WAXED_EXPOSED_CUT_COPPER_SLAB),
			entry(Items.WEATHERED_CUT_COPPER_SLAB, Items.WAXED_WEATHERED_CUT_COPPER_SLAB), entry(Items.OXIDIZED_CUT_COPPER_SLAB, Items.WAXED_OXIDIZED_CUT_COPPER_SLAB),
			entry(Items.CUT_COPPER_STAIRS, Items.WAXED_CUT_COPPER_STAIRS), entry(Items.EXPOSED_CUT_COPPER_STAIRS, Items.WAXED_EXPOSED_CUT_COPPER_STAIRS),
			entry(Items.WEATHERED_CUT_COPPER_STAIRS, Items.WAXED_WEATHERED_CUT_COPPER_STAIRS), entry(Items.OXIDIZED_CUT_COPPER_STAIRS, Items.WAXED_OXIDIZED_CUT_COPPER_STAIRS)
	));
	public static void RegisterWaxed(BlockContainer unwaxed, BlockContainer waxed) {
		WAXED_BLOCKS.put(unwaxed.asBlock(), waxed.asBlock());
	}
	public static void RegisterWaxed(TorchContainer unwaxed, TorchContainer waxed) {
		WAXED_BLOCKS.put(unwaxed.asBlock(), waxed.asBlock());
		WAXED_BLOCKS.put(unwaxed.getWallBlock(), waxed.getWallBlock());
	}
	public static void RegisterWaxed(ItemConvertible unwaxed, ItemConvertible waxed) {
		WAXED_ITEMS.put(unwaxed.asItem(), waxed.asItem());
	}

	public static void Register(IBlockItemContainer unaffected, IBlockItemContainer exposed, IBlockItemContainer weathered, IBlockItemContainer oxidized) {
		Register(unaffected.asBlock(), exposed.asBlock(), weathered.asBlock(), oxidized.asBlock());
		Register(unaffected.asItem(), exposed.asItem(), weathered.asItem(), oxidized.asItem());
	}
	public static void Register(TorchContainer unaffected, TorchContainer exposed, TorchContainer weathered, TorchContainer oxidized) {
		Register(unaffected.asBlock(), exposed.asBlock(), weathered.asBlock(), oxidized.asBlock());
		Register(unaffected.getWallBlock(), exposed.getWallBlock(), weathered.getWallBlock(), oxidized.getWallBlock());
		Register(unaffected.asItem(), exposed.asItem(), weathered.asItem(), oxidized.asItem());
	}
	public static void Register(Block unaffected, Block exposed, Block weathered, Block oxidized) {
		OXIDIZABLE_BLOCKS.add(new OxidationScale<>(unaffected, exposed, weathered, oxidized));
	}
	public static void Register(Item unaffected, Item exposed, Item weathered, Item oxidized) {
		OXIDIZABLE_ITEMS.add(new OxidationScale<>(unaffected, exposed, weathered, oxidized));
	}

	public static Block getUnaffectedBlock(Block block) {
		Block block2 = block;
		for(Block block3 = BlockLevelDecreases().get().get(block); block3 != null; block3 = BlockLevelDecreases().get().get(block3)) {
			block2 = block3;
		}
		return block2;
	}
	public static Optional<Block> getIncreasedBlock(Block block) {
		return Optional.ofNullable(BlockLevelIncreases().get().get(block));
	}
	public static Optional<Block> getDecreasedBlock(Block block) {
		return Optional.ofNullable(BlockLevelDecreases().get().get(block));
	}
	public static Optional<BlockState> getDecreasedState(BlockState state) {
		return getDecreasedBlock(state.getBlock()).map((block) -> block.getStateWithProperties(state));
	}
	public static BlockState getUnaffectedState(BlockState state) {
		return getUnaffectedBlock(state.getBlock()).getStateWithProperties(state);
	}

	public static Item getUnaffectedItem(Item item) {
		Item item2 = item;
		for(Item item3 = ItemLevelDecreases().get().get(item); item3 != null; item3 = ItemLevelDecreases().get().get(item3)) {
			item2 = item3;
		}
		return item2;
	}
	public static Optional<Item> getIncreasedItem(Item item) {
		return Optional.ofNullable(ItemLevelIncreases().get().get(item));
	}
	public static Optional<Item> getDecreasedItem(Item item) {
		return Optional.ofNullable(ItemLevelDecreases().get().get(item));
	}
	
	private static Supplier<BiMap<Block, Block>> BlockLevelIncreases() {
		return Suppliers.memoize(() -> {
			BiMap<Block, Block> map = HashBiMap.create();
			for(OxidationScale<Block> scale : OXIDIZABLE_BLOCKS) {
				map.put(scale.unaffected, scale.exposed);
				map.put(scale.exposed, scale.weathered);
				map.put(scale.weathered, scale.oxidized);
			}
			return map;
		});
	}
	private static Supplier<BiMap<Block, Block>> BlockLevelDecreases() {
		return Suppliers.memoize(() -> BlockLevelIncreases().get().inverse());
	}
	private static Supplier<BiMap<Item, Item>> ItemLevelIncreases() {
		return Suppliers.memoize(() -> {
			BiMap<Item, Item> map = HashBiMap.create();
			for(OxidationScale<Item> scale : OXIDIZABLE_ITEMS) {
				map.put(scale.unaffected, scale.exposed);
				map.put(scale.exposed, scale.weathered);
				map.put(scale.weathered, scale.oxidized);
			}
			return map;
		});
	}
	private static Supplier<BiMap<Item, Item>> ItemLevelDecreases() {
		return Suppliers.memoize(() -> ItemLevelIncreases().get().inverse());
	}

	public static Optional<BlockState> getWaxedState(BlockState state) {
		return Optional.ofNullable(UnwaxedToWaxedBlocks().get().get(state.getBlock())).map((block) -> block.getStateWithProperties(state));
	}
	public static Optional<Item> getWaxed(Item item) {
		return Optional.ofNullable(UnwaxedToWaxedItems().get().get(item));
	}

	private static Supplier<BiMap<Block, Block>> UnwaxedToWaxedBlocks() {
		return Suppliers.memoize(() -> {
			BiMap<Block, Block> map = HashBiMap.create();
			for(Block unwaxed : WAXED_BLOCKS.keySet()) {
				map.put(unwaxed, WAXED_BLOCKS.get(unwaxed));
			}
			return map;
		});
	}
	public static Supplier<BiMap<Block, Block>> WaxedToUnwaxedBlocks() {
		return Suppliers.memoize(() -> UnwaxedToWaxedBlocks().get().inverse());
	}
	private static Supplier<BiMap<Item, Item>> UnwaxedToWaxedItems() {
		return Suppliers.memoize(() -> {
			BiMap<Item, Item> map = HashBiMap.create();
			for(Item unwaxed : WAXED_ITEMS.keySet()) {
				map.put(unwaxed, WAXED_ITEMS.get(unwaxed));
			}
			return map;
		});
	}
	public static Supplier<BiMap<Item, Item>> WaxedToUnwaxedItems() {
		return Suppliers.memoize(() -> UnwaxedToWaxedItems().get().inverse());
	}
}
