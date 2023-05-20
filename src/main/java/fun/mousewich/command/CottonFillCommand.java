package fun.mousewich.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.*;

public class CottonFillCommand {
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
		dispatcher.register(CommandManager.literal("cottonfill")
				.requires(source -> source.hasPermissionLevel(2))
				.then(CommandManager.argument("position", BlockPosArgumentType.blockPos())
						.then(CommandManager.argument("radius", IntegerArgumentType.integer(1))
								.executes(context -> execute(context, BlockPosArgumentType.getBlockPos(context, "position"), IntegerArgumentType.getInteger(context, "radius"))))));
	}

	private static int execute(CommandContext<ServerCommandSource> context, BlockPos position, int radius) {
		ServerWorld world = context.getSource().getWorld();
		return Fill(world, position, radius);
	}

	public static int Fill(ServerWorld world, BlockPos position, int radius) {
		int X = position.getX(), Y = position.getY(), Z = position.getZ();
		BlockPos.Mutable mutable = new BlockPos.Mutable(X, Y, Z);
		int modifiedBlocks = 0;
		int r = radius + 1;
		//Populate Graph
		HashSet<BlockPos> POSITIONS = new HashSet<>();
		for (int x = -r; x <= r; x++) {
			for (int y = -r; y <= r; y++) {
				for (int z = -r; z <= r; z++) {
					mutable.set(X + x, Y + y, Z + z);
					if (mutable.isWithinDistance(position, radius)) {
						POSITIONS.add(mutable.toImmutable());
					}
				}
			}
		}
		Map<BlockPos, Integer> DISTANCE = new HashMap<>();
		int manhattan = radius + 1;
		March(world, position, 0, manhattan, DISTANCE, POSITIONS);
		for (BlockPos pos : DISTANCE.keySet()) {
			if (TryReplace(world, pos, world.getBlockState(pos))) modifiedBlocks++;
		}
		return modifiedBlocks;
	}

	private static final Direction[] DIRECTIONS = Direction.values();
	private static void March(World world, BlockPos position, int traveled, int max, Map<BlockPos, Integer> DISTANCE, Set<BlockPos> POSITIONS) {
		if (!POSITIONS.contains(position)) return;
		if (traveled > max) return;
		if (!DISTANCE.containsKey(position))  DISTANCE.put(position, traveled);
		else {
			int distance = DISTANCE.get(position);
			if (distance <= traveled) return;
			else DISTANCE.put(position, traveled);
		}
		for (Direction direction : DIRECTIONS) {
			BlockPos pos = position.offset(direction);
			if (!POSITIONS.contains(pos)) continue;
			if(StateBlocks(world.getBlockState(pos))) continue;
			March(world, pos, traveled + 1, max, DISTANCE, POSITIONS);
		}
	}

	public static boolean StateBlocks(BlockState state) { return !state.isAir(); }

	public static boolean TryReplace(World world, BlockPos pos, BlockState state) {
		if (StateBlocks(state)) return false;
		world.setBlockState(pos, Blocks.COBWEB.getDefaultState(), Block.NOTIFY_ALL);
		world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos);
		return false;
	}
}
