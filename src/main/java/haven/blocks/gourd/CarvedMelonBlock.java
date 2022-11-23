package haven.blocks.gourd;

import java.util.Iterator;
import java.util.function.Predicate;

import haven.ModBase;
import haven.entities.passive.MelonGolemEntity;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
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
	private BlockPattern melonGolemPattern;
	private static final Predicate<BlockState> IS_GOLEM_HEAD_PREDICATE;

	public CarvedMelonBlock(AbstractBlock.Settings settings) {
		super(settings);
	}

	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		if (!oldState.isOf(state.getBlock())) this.trySpawnEntity(world, pos);
	}

	public boolean canDispense(WorldView world, BlockPos pos) {
		return this.getMelonGolemDispenserPattern().searchAround(world, pos) != null;
	}

	private void trySpawnEntity(World world, BlockPos pos) {
		BlockPattern.Result result = this.getMelonGolemPattern().searchAround(world, pos);
		Iterator var6;
		ServerPlayerEntity serverPlayerEntity2;
		if (result != null) {
			for(int k = 0; k < this.getMelonGolemPattern().getHeight(); ++k) {
				CachedBlockPosition cachedBlockPosition = result.translate(0, k, 0);
				world.setBlockState(cachedBlockPosition.getBlockPos(), Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS);
				world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, cachedBlockPosition.getBlockPos(), Block.getRawIdFromState(cachedBlockPosition.getBlockState()));
			}
			MelonGolemEntity melonGolemEntity = (MelonGolemEntity) ModBase.MELON_GOLEM_ENTITY.create(world);
			BlockPos blockPos = result.translate(0, 2, 0).getBlockPos();
			melonGolemEntity.refreshPositionAndAngles((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.05D, (double)blockPos.getZ() + 0.5D, 0.0F, 0.0F);
			world.spawnEntity(melonGolemEntity);
			var6 = world.getNonSpectatingEntities(ServerPlayerEntity.class, melonGolemEntity.getBoundingBox().expand(5.0D)).iterator();

			while(var6.hasNext()) {
				serverPlayerEntity2 = (ServerPlayerEntity)var6.next();
				Criteria.SUMMONED_ENTITY.trigger(serverPlayerEntity2, melonGolemEntity);
			}

			for(int m = 0; m < this.getMelonGolemPattern().getHeight(); ++m) {
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

	private BlockPattern getMelonGolemPattern() {
		if (this.melonGolemPattern == null) {
			this.melonGolemPattern = BlockPatternBuilder.start().aisle("^", "#", "#").where('^', CachedBlockPosition.matchesBlockState(IS_GOLEM_HEAD_PREDICATE)).where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.SNOW_BLOCK))).build();
		}
		return this.melonGolemPattern;
	}

	static {
		IS_GOLEM_HEAD_PREDICATE = (state) -> state != null && (state.isOf(ModBase.CARVED_MELON.getBlock()) || state.isOf(ModBase.MELON_LANTERN.getBlock()));
	}
}