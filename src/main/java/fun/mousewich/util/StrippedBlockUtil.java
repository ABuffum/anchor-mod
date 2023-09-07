package fun.mousewich.util;

import fun.mousewich.block.BlockConvertible;
import net.minecraft.block.Block;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class StrippedBlockUtil {
	public static final Map<Block, Block> STRIPPED_BLOCKS = new HashMap<>();
	public interface OnStripFunc { void execute(ItemUsageContext context); }
	public static final Map<Block, OnStripFunc> ON_STRIP = new HashMap<>();

	public static void Register(BlockConvertible block, BlockConvertible stripped) { Register(block, stripped, null); }
	public static void Register(BlockConvertible block, BlockConvertible stripped, OnStripFunc onStrip) { Register(block.asBlock(), stripped.asBlock(), onStrip); }
	public static void Register(Block block, BlockConvertible stripped) { Register(block, stripped, null); }
	public static void Register(Block block, BlockConvertible stripped, OnStripFunc onStrip) { Register(block, stripped.asBlock(), onStrip); }
	public static void Register(BlockConvertible block, Block stripped) { Register(block, stripped, null); }
	public static void Register(BlockConvertible block, Block stripped, OnStripFunc onStrip) { Register(block.asBlock(), stripped, onStrip); }
	public static void Register(Block block, Block stripped) { Register(block, stripped, null); }
	public static void Register(Block block, Block stripped, OnStripFunc onStrip) {
		if (STRIPPED_BLOCKS.containsKey(block)) throw new IllegalArgumentException("Block already registered in stripped blocks map: " + block);
		STRIPPED_BLOCKS.put(block, stripped);
		if (onStrip == null) return;
		if (ON_STRIP.containsKey(stripped)) throw new IllegalArgumentException("Block already registered in on-strip map: " + block);
		ON_STRIP.put(stripped, onStrip);
	}

	public static OnStripFunc DropStack(ItemStack stack) {
		return (context) -> {
			World world = context.getWorld();
			BlockPos pos = context.getBlockPos();
			world.spawnEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, stack));
		};
	}
}
