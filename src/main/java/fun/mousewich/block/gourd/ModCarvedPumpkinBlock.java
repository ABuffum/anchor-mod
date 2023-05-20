package fun.mousewich.block.gourd;

import fun.mousewich.entity.variants.SnowGolemVariant;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class ModCarvedPumpkinBlock extends CarvedGourdBlock {
	@Nullable
	private BlockPattern snowGolemDispenserPattern;
	@Nullable
	private BlockPattern snowGolemPattern;

	private final Supplier<SnowGolemVariant> snowGolemVariant;

	public ModCarvedPumpkinBlock(Settings settings, Supplier<SnowGolemVariant> snowGolemVariant) {
		super(settings);
		this.snowGolemVariant = snowGolemVariant;
	}

	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		if (!oldState.isOf(state.getBlock())) this.trySpawnEntity(world, pos);
	}

	public boolean canDispense(WorldView world, BlockPos pos) {
		return this.getSnowGolemDispenserPattern().searchAround(world, pos) != null;
	}

	private void trySpawnEntity(World world, BlockPos pos) {
		BlockPattern.Result result = this.getSnowGolemPattern().searchAround(world, pos);
		if (result != null) {
			for(int k = 0; k < this.getSnowGolemPattern().getHeight(); ++k) {
				CachedBlockPosition cachedBlockPosition = result.translate(0, k, 0);
				world.setBlockState(cachedBlockPosition.getBlockPos(), Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS);
				world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, cachedBlockPosition.getBlockPos(), Block.getRawIdFromState(cachedBlockPosition.getBlockState()));
			}
			SnowGolemEntity golem = EntityType.SNOW_GOLEM.create(world);
			if (golem != null) {
				SnowGolemVariant.setVariant(golem, snowGolemVariant.get().ordinal());
				BlockPos blockPos = result.translate(0, 2, 0).getBlockPos();
				golem.refreshPositionAndAngles((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.05D, (double)blockPos.getZ() + 0.5D, 0.0F, 0.0F);
				world.spawnEntity(golem);
				for (ServerPlayerEntity player : world.getNonSpectatingEntities(ServerPlayerEntity.class, golem.getBoundingBox().expand(5.0D))) {
					Criteria.SUMMONED_ENTITY.trigger(player, golem);
				}
			}
			for(int m = 0; m < this.getSnowGolemPattern().getHeight(); ++m) {
				CachedBlockPosition cachedBlockPosition2 = result.translate(0, m, 0);
				world.updateNeighbors(cachedBlockPosition2.getBlockPos(), Blocks.AIR);
			}
		}
	}

	private BlockPattern getSnowGolemDispenserPattern() {
		if (this.snowGolemDispenserPattern == null) {
			this.snowGolemDispenserPattern = BlockPatternBuilder.start().aisle(" ", "#", "#").where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.SNOW_BLOCK))).build();
		}
		return this.snowGolemDispenserPattern;
	}

	private BlockPattern getSnowGolemPattern() {
		if (this.snowGolemPattern == null) {
			this.snowGolemPattern = BlockPatternBuilder.start().aisle("^", "#", "#").where('^', CachedBlockPosition.matchesBlockState(SnowGolemVariant.IS_GOLEM_HEAD_PREDICATE)).where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.SNOW_BLOCK))).build();
		}
		return this.snowGolemPattern;
	}
}