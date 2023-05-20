package fun.mousewich.util;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import fun.mousewich.block.BlockConvertible;
import fun.mousewich.sound.IdentifiedSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class CrackedBlocks {
	private static final BiMap<Block, Block> CRACKED_BLOCKS = HashBiMap.create();

	public static BlockState GetCracked(BlockState state) {
		Block block = state.getBlock();
		if (CRACKED_BLOCKS.containsKey(block)) {
			return CRACKED_BLOCKS.get(block).getDefaultState();
		}
		return null;
	}
	public static BlockState GetUncracked(BlockState state) {
		Block block = state.getBlock();
		if (CRACKED_BLOCKS.containsValue(block)) {
			return CRACKED_BLOCKS.inverse().get(block).getDefaultState();
		}
		return null;
	}

	public static boolean Crack(World world, BlockPos pos) {
		BlockState uncracked = world.getBlockState(pos);
		BlockState cracked = GetCracked(uncracked);
		if (cracked != null) {
			world.setBlockState(pos, cracked, Block.NOTIFY_ALL);
			world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos);
			world.playSound(null, pos, IdentifiedSounds.getBreakSound(uncracked), SoundCategory.BLOCKS, 1f, 1f);
			return true;
		}
		return false;
	}

	public static void Register(BlockConvertible uncracked, BlockConvertible cracked) { Register(uncracked.asBlock(), cracked.asBlock()); }
	public static void Register(Block uncracked, BlockConvertible cracked) { Register(uncracked, cracked.asBlock()); }
	public static void Register(BlockConvertible uncracked, Block cracked) { Register(uncracked.asBlock(), cracked); }
	public static void Register(Block uncracked, Block cracked) { CRACKED_BLOCKS.put(uncracked, cracked); }

	public static void Initialize() {
		Register(Blocks.STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS);
		Register(Blocks.INFESTED_STONE_BRICKS, Blocks.INFESTED_CRACKED_STONE_BRICKS);
		Register(Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS);
		Register(Blocks.NETHER_BRICKS, Blocks.CRACKED_NETHER_BRICKS);
		Register(Blocks.DEEPSLATE_BRICKS, Blocks.CRACKED_DEEPSLATE_BRICKS);
		Register(Blocks.DEEPSLATE_TILES, Blocks.CRACKED_DEEPSLATE_TILES);
	}
}
