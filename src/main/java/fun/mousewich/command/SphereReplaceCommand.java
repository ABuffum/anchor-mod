package fun.mousewich.command;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.BlockPredicateArgumentType;
import net.minecraft.command.argument.BlockStateArgument;
import net.minecraft.command.argument.BlockStateArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Clearable;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.function.Predicate;

public class SphereReplaceCommand {
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
		dispatcher.register(CommandManager.literal("spherefill")
				.requires(source -> source.hasPermissionLevel(2))
				.then(CommandManager.argument("position", BlockPosArgumentType.blockPos())
						.then(CommandManager.argument("radius", IntegerArgumentType.integer(1))
								.then((CommandManager.argument("block", BlockStateArgumentType.blockState())
										.executes(context -> execute(context, BlockPosArgumentType.getBlockPos(context, "position"), IntegerArgumentType.getInteger(context, "radius"), BlockStateArgumentType.getBlockState(context, "block"), null)))
										.then(CommandManager.argument("filter", BlockPredicateArgumentType.blockPredicate())
												.executes(context -> execute(context, BlockPosArgumentType.getBlockPos(context, "position"), IntegerArgumentType.getInteger(context, "radius"), BlockStateArgumentType.getBlockState(context, "block"), BlockPredicateArgumentType.getBlockPredicate(context, "filter"))))))));
	}

	private static int execute(CommandContext<ServerCommandSource> context, BlockPos position, int radius, BlockStateArgument block, Predicate<CachedBlockPosition> filter) {
		ServerWorld world = context.getSource().getWorld();
		return Fill(world, position, radius, block, filter);
	}

	public static int Fill(ServerWorld world, BlockPos position, int radius, BlockStateArgument block, Predicate<CachedBlockPosition> filter) {
		int X = position.getX(), Y = position.getY(), Z = position.getZ();
		BlockPos.Mutable mutable = new BlockPos.Mutable(X, Y, Z);
		int r = radius + 1;
		//Populate Graph
		int j = 0;
		ArrayList<BlockPos> list = Lists.newArrayList();
		for (int x = -r; x <= r; x++) {
			for (int y = -r; y <= r; y++) {
				for (int z = -r; z <= r; z++) {
					mutable.set(X + x, Y + y, Z + z);
					if (mutable.isWithinDistance(position, radius)) {
						BlockPos blockPos = mutable.toImmutable();
						if (filter != null && !filter.test(new CachedBlockPosition(world, blockPos, true)) || block == null) continue;
						BlockEntity blockEntity = world.getBlockEntity(blockPos);
						Clearable.clear(blockEntity);
						if (!block.setBlockState(world, blockPos, Block.NOTIFY_LISTENERS)) continue;
						list.add(blockPos.toImmutable());
						++j;
					}
				}
			}
		}
		for (BlockPos blockPos : list) {
			Block block2 = world.getBlockState(blockPos).getBlock();
			world.updateNeighbors(blockPos, block2);
		}
		return j;
	}
}
