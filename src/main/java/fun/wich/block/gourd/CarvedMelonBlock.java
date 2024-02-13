package fun.wich.block.gourd;

import fun.wich.entity.ModEntityType;
import fun.wich.entity.neutral.golem.MelonGolemEntity;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class CarvedMelonBlock extends CarvedGourdBlock {
	@Nullable
	private BlockPattern melonGolemDispenserPattern;
	@Nullable
	private BlockPattern golemPattern;

	public CarvedMelonBlock(Settings settings) { super(settings); }

	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		if (!oldState.isOf(state.getBlock())) this.trySpawnEntity(world, pos);
	}

	public boolean canDispense(WorldView world, BlockPos pos) {
		return this.getMelonGolemDispenserPattern().searchAround(world, pos) != null;
	}

	private void trySpawnEntity(World world, BlockPos pos) {
		BlockPattern.Result result = this.getGolemPattern().searchAround(world, pos);
		if (result != null) {
			for(int k = 0; k < this.getGolemPattern().getHeight(); ++k) {
				CachedBlockPosition cachedBlockPosition = result.translate(0, k, 0);
				world.setBlockState(cachedBlockPosition.getBlockPos(), Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS);
				world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, cachedBlockPosition.getBlockPos(), Block.getRawIdFromState(cachedBlockPosition.getBlockState()));
			}
			MelonGolemEntity golem = ModEntityType.MELON_GOLEM_ENTITY.create(world);
			if (golem != null) {
				BlockPos blockPos = result.translate(0, 2, 0).getBlockPos();
				golem.refreshPositionAndAngles((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.05D, (double)blockPos.getZ() + 0.5D, 0.0F, 0.0F);
				world.spawnEntity(golem);
				for (ServerPlayerEntity player : world.getNonSpectatingEntities(ServerPlayerEntity.class, golem.getBoundingBox().expand(5.0D))) {
					Criteria.SUMMONED_ENTITY.trigger(player, golem);
				}
			}
			for(int m = 0; m < this.getGolemPattern().getHeight(); ++m) {
				CachedBlockPosition cachedBlockPosition2 = result.translate(0, m, 0);
				world.updateNeighbors(cachedBlockPosition2.getBlockPos(), Blocks.AIR);
			}
		}
	}

	private BlockPattern getMelonGolemDispenserPattern() {
		if (this.melonGolemDispenserPattern == null) {
			this.melonGolemDispenserPattern = BlockPatternBuilder.start().aisle(" ", "#", "#").where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.SNOW_BLOCK))).build();
		}
		return this.melonGolemDispenserPattern;
	}

	private BlockPattern getGolemPattern() {
		if (this.golemPattern == null) {
			this.golemPattern = BlockPatternBuilder.start().aisle("^", "#", "#").where('^', CachedBlockPosition.matchesBlockState(state -> state.getBlock() instanceof CarvedMelonBlock)).where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.SNOW_BLOCK))).build();
		}
		return this.golemPattern;
	}
}