package haven.util;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import haven.HavenMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

import java.util.Optional;
import java.util.function.Supplier;

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
			for(OxidationScale scale : HavenMod.OXIDIZABLE_BLOCKS) {
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
			for(Block unwaxed : HavenMod.WAXED_BLOCKS.keySet()) {
				map.put(unwaxed, HavenMod.WAXED_BLOCKS.get(unwaxed));
			}
			return map;
		});
	}
	public static Supplier<BiMap<Block, Block>> WaxedToUnwaxedBlocks() {
		return Suppliers.memoize(() -> {
			return ((BiMap)UnwaxedToWaxedBlocks().get()).inverse();
		});
	}

	public static final OxidationScale COPPER_BLOCK = new OxidationScale(Blocks.COPPER_BLOCK, Blocks.EXPOSED_COPPER, Blocks.WEATHERED_COPPER, Blocks.OXIDIZED_COPPER);
	public static final OxidationScale CUT_COPPER = new OxidationScale(Blocks.CUT_COPPER, Blocks.EXPOSED_CUT_COPPER, Blocks.WEATHERED_CUT_COPPER, Blocks.OXIDIZED_CUT_COPPER);
	public static final OxidationScale CUT_COPPER_SLAB = new OxidationScale(Blocks.CUT_COPPER_SLAB, Blocks.EXPOSED_CUT_COPPER_SLAB, Blocks.WEATHERED_CUT_COPPER_SLAB, Blocks.OXIDIZED_CUT_COPPER_SLAB);
	public static final OxidationScale CUT_COPPER_STAIRS = new OxidationScale(Blocks.CUT_COPPER_STAIRS, Blocks.EXPOSED_CUT_COPPER_STAIRS, Blocks.WEATHERED_CUT_COPPER_STAIRS, Blocks.OXIDIZED_CUT_COPPER_STAIRS);
}
