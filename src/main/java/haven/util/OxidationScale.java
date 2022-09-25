package haven.util;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import haven.containers.OxidizableBlockContainer;
import haven.containers.BlockContainer;
import haven.containers.OxidizableTorchContainer;
import haven.containers.TorchContainer;
import net.minecraft.block.*;

import java.util.*;
import java.util.function.Supplier;

import static java.util.Map.entry;

public class OxidationScale {
	public final Block UNAFFECTED;
	public final Block EXPOSED;
	public final Block WEATHERED;
	public final Block OXIDIZED;

	public OxidationScale(Block unaffected, Block exposed, Block weathered, Block oxidized) {
		UNAFFECTED = unaffected;
		EXPOSED = exposed;
		WEATHERED = weathered;
		OXIDIZED = oxidized;
	}


	public static final Set<OxidationScale> OXIDIZABLE_BLOCKS = new HashSet<OxidationScale>();

	public static final OxidationScale COPPER_BLOCK = Register(Blocks.COPPER_BLOCK, Blocks.EXPOSED_COPPER, Blocks.WEATHERED_COPPER, Blocks.OXIDIZED_COPPER);
	public static final OxidationScale CUT_COPPER = Register(Blocks.CUT_COPPER, Blocks.EXPOSED_CUT_COPPER, Blocks.WEATHERED_CUT_COPPER, Blocks.OXIDIZED_CUT_COPPER);
	public static final OxidationScale CUT_COPPER_SLAB = Register(Blocks.CUT_COPPER_SLAB, Blocks.EXPOSED_CUT_COPPER_SLAB, Blocks.WEATHERED_CUT_COPPER_SLAB, Blocks.OXIDIZED_CUT_COPPER_SLAB);
	public static final OxidationScale CUT_COPPER_STAIRS = Register(Blocks.CUT_COPPER_STAIRS, Blocks.EXPOSED_CUT_COPPER_STAIRS, Blocks.WEATHERED_CUT_COPPER_STAIRS, Blocks.OXIDIZED_CUT_COPPER_STAIRS);

	public static MapColor getMapColor(Oxidizable.OxidizationLevel level) {
		if (level == Oxidizable.OxidizationLevel.OXIDIZED) return MapColor.TEAL;
		else if (level == Oxidizable.OxidizationLevel.WEATHERED) return MapColor.DARK_AQUA;
		else if (level == Oxidizable.OxidizationLevel.EXPOSED) return MapColor.TERRACOTTA_LIGHT_GRAY;
		else return MapColor.ORANGE;
	}

	public static interface BlockSettingsSupplier {
		public AbstractBlock.Settings get(Oxidizable.OxidizationLevel level);
	}

	public static final Map<Block, Block> WAXED_BLOCKS = new HashMap<Block, Block>(Map.<Block, Block>ofEntries(
			entry(Blocks.COPPER_BLOCK, Blocks.WAXED_COPPER_BLOCK), entry(Blocks.EXPOSED_COPPER, Blocks.WAXED_EXPOSED_COPPER),
			entry(Blocks.WEATHERED_COPPER, Blocks.WAXED_WEATHERED_COPPER), entry(Blocks.OXIDIZED_COPPER, Blocks.WAXED_OXIDIZED_COPPER),
			entry(Blocks.CUT_COPPER, Blocks.WAXED_CUT_COPPER), entry(Blocks.EXPOSED_CUT_COPPER, Blocks.WAXED_EXPOSED_CUT_COPPER),
			entry(Blocks.WEATHERED_CUT_COPPER, Blocks.WAXED_WEATHERED_CUT_COPPER), entry(Blocks.OXIDIZED_CUT_COPPER, Blocks.WAXED_OXIDIZED_CUT_COPPER),
			entry(Blocks.CUT_COPPER_SLAB, Blocks.WAXED_CUT_COPPER_SLAB), entry(Blocks.EXPOSED_CUT_COPPER_SLAB, Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB),
			entry(Blocks.WEATHERED_CUT_COPPER_SLAB, Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB), entry(Blocks.OXIDIZED_CUT_COPPER_SLAB, Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB),
			entry(Blocks.CUT_COPPER_STAIRS, Blocks.WAXED_CUT_COPPER_STAIRS), entry(Blocks.EXPOSED_CUT_COPPER_STAIRS, Blocks.WAXED_EXPOSED_CUT_COPPER_STAIRS),
			entry(Blocks.WEATHERED_CUT_COPPER_STAIRS, Blocks.WAXED_WEATHERED_CUT_COPPER_STAIRS), entry(Blocks.OXIDIZED_CUT_COPPER_STAIRS, Blocks.WAXED_OXIDIZED_CUT_COPPER_STAIRS)
	));

	public static void Register(OxidizableTorchContainer container) {
		Register(container.getUnaffected(), container.getExposed(), container.getWeathered(), container.getOxidized());
	}
	public static OxidationScale Register(OxidizableBlockContainer container) {
		return Register(container.getUnaffected().BLOCK, container.getExposed().BLOCK, container.getWeathered().BLOCK, container.getOxidized().BLOCK);
	}
	public static OxidationScale Register(BlockContainer unaffected, BlockContainer exposed, BlockContainer weathered, BlockContainer oxidized) {
		return Register(unaffected.BLOCK, exposed.BLOCK, weathered.BLOCK, oxidized.BLOCK);
	}
	public static void Register(TorchContainer unaffected, TorchContainer exposed, TorchContainer weathered, TorchContainer oxidized) {
		Register(unaffected.BLOCK, exposed.BLOCK, weathered.BLOCK, oxidized.BLOCK);
		Register(unaffected.WALL_BLOCK, exposed.WALL_BLOCK, weathered.WALL_BLOCK, oxidized.WALL_BLOCK);
		Register(unaffected.UNLIT.UNLIT, exposed.UNLIT.UNLIT, weathered.UNLIT.UNLIT, oxidized.UNLIT.UNLIT);
		Register(unaffected.UNLIT.UNLIT_WALL, exposed.UNLIT.UNLIT_WALL, weathered.UNLIT.UNLIT_WALL, oxidized.UNLIT.UNLIT_WALL);
	}
	public static OxidationScale Register(Block unaffected, Block exposed, Block weathered, Block oxidized) {
		return Register(new OxidationScale(unaffected, exposed, weathered, oxidized));
	}

	public static OxidationScale Register(OxidationScale scale) {
		if (scale != null) OXIDIZABLE_BLOCKS.add(scale);
		return scale;
	}

	public static Optional<Block> getDecreasedOxidationBlock(Block block) {
		return Optional.ofNullable((Block)((BiMap)OxidationLevelDecreases().get()).get(block));
	}

	public static Block getUnaffectedOxidationBlock(Block block) {
		Block block2 = block;

		for(Block block3 = (Block)((BiMap)OxidationLevelDecreases().get()).get(block); block3 != null; block3 = (Block)((BiMap)OxidationLevelDecreases().get()).get(block3)) {
			block2 = block3;
		}

		return block2;
	}

	public static Optional<BlockState> getDecreasedOxidationState(BlockState state) {
		return getDecreasedOxidationBlock(state.getBlock()).map((block) -> {
			return block.getStateWithProperties(state);
		});
	}

	public static Optional<Block> getIncreasedOxidationBlock(Block block) {
		return Optional.ofNullable((Block)((BiMap)OxidationLevelIncreases().get()).get(block));
	}

	public static BlockState getUnaffectedOxidationState(BlockState state) {
		return getUnaffectedOxidationBlock(state.getBlock()).getStateWithProperties(state);
	}

	private static Supplier<BiMap<Block, Block>> OxidationLevelIncreases() {
		return Suppliers.memoize(() -> {
			BiMap<Block, Block> map = HashBiMap.create();
			for(OxidationScale scale : OXIDIZABLE_BLOCKS) {
				map.put(scale.UNAFFECTED, scale.EXPOSED);
				map.put(scale.EXPOSED, scale.WEATHERED);
				map.put(scale.WEATHERED, scale.OXIDIZED);
			}
			return map;
		});
	}
	private static Supplier<BiMap<Block, Block>> OxidationLevelDecreases() {
		return Suppliers.memoize(() -> {
			return ((BiMap)OxidationLevelIncreases().get()).inverse();
		});
	}

	public static Optional<BlockState> getWaxedState(BlockState state) {
		return Optional.ofNullable((Block)((BiMap)UnwaxedToWaxedBlocks().get()).get(state.getBlock())).map((block) -> {
			return block.getStateWithProperties(state);
		});
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
		return Suppliers.memoize(() -> {
			return ((BiMap)UnwaxedToWaxedBlocks().get()).inverse();
		});
	}
}
