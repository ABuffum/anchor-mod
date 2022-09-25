package haven.blocks.gourd;

import haven.HavenMod;
import haven.entities.passive.WhiteSnowGolemEntity;
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

import java.util.Iterator;
import java.util.function.Predicate;

public class CarvedWhitePumpkinBlock extends CarvedGourdBlock {
	@Nullable
	private BlockPattern whiteSnowGolemDispenserPattern;
	@Nullable
	private BlockPattern whiteSnowGolemPattern;
	private static final Predicate<BlockState> IS_GOLEM_HEAD_PREDICATE;

	public CarvedWhitePumpkinBlock(Settings settings) {
		super(settings);
	}

	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		if (!oldState.isOf(state.getBlock())) this.trySpawnEntity(world, pos);
	}

	public boolean canDispense(WorldView world, BlockPos pos) {
		return this.getWhiteSnowGolemDispenserPattern().searchAround(world, pos) != null;
	}

	private void trySpawnEntity(World world, BlockPos pos) {
		BlockPattern.Result result = this.getWhiteSnowGolemPattern().searchAround(world, pos);
		Iterator var6;
		ServerPlayerEntity serverPlayerEntity2;
		if (result != null) {
			for(int k = 0; k < this.getWhiteSnowGolemPattern().getHeight(); ++k) {
				CachedBlockPosition cachedBlockPosition = result.translate(0, k, 0);
				world.setBlockState(cachedBlockPosition.getBlockPos(), Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS);
				world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, cachedBlockPosition.getBlockPos(), Block.getRawIdFromState(cachedBlockPosition.getBlockState()));
			}
			WhiteSnowGolemEntity snowGolemEntity = (WhiteSnowGolemEntity)HavenMod.WHITE_SNOW_GOLEM_ENTITY.create(world);
			BlockPos blockPos = result.translate(0, 2, 0).getBlockPos();
			snowGolemEntity.refreshPositionAndAngles((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.05D, (double)blockPos.getZ() + 0.5D, 0.0F, 0.0F);
			world.spawnEntity(snowGolemEntity);
			var6 = world.getNonSpectatingEntities(ServerPlayerEntity.class, snowGolemEntity.getBoundingBox().expand(5.0D)).iterator();

			while(var6.hasNext()) {
				serverPlayerEntity2 = (ServerPlayerEntity)var6.next();
				Criteria.SUMMONED_ENTITY.trigger(serverPlayerEntity2, snowGolemEntity);
			}

			for(int m = 0; m < this.getWhiteSnowGolemPattern().getHeight(); ++m) {
				CachedBlockPosition cachedBlockPosition2 = result.translate(0, m, 0);
				world.updateNeighbors(cachedBlockPosition2.getBlockPos(), Blocks.AIR);
			}
		}
	}

	private BlockPattern getWhiteSnowGolemDispenserPattern() {
		if (this.whiteSnowGolemDispenserPattern == null) {
			this.whiteSnowGolemDispenserPattern = BlockPatternBuilder.start().aisle(" ", "#", "#").where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.SNOW_BLOCK))).build();
		}
		return this.whiteSnowGolemDispenserPattern;
	}

	private BlockPattern getWhiteSnowGolemPattern() {
		if (this.whiteSnowGolemPattern == null) {
			this.whiteSnowGolemPattern = BlockPatternBuilder.start().aisle("^", "#", "#").where('^', CachedBlockPosition.matchesBlockState(IS_GOLEM_HEAD_PREDICATE)).where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.SNOW_BLOCK))).build();
		}
		return this.whiteSnowGolemPattern;
	}

	static {
		IS_GOLEM_HEAD_PREDICATE = (state) -> state != null && (state.isOf(HavenMod.WHITE_PUMPKIN.getCarved().BLOCK) || state.isOf(HavenMod.WHITE_JACK_O_LANTERN.BLOCK));
	}
}