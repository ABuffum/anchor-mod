package haven.blocks;

import java.util.Iterator;
import java.util.function.Predicate;

import haven.HavenMod;
import haven.entities.MelonGolemEntity;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.Wearable;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class CarvedMelonBlock extends HorizontalFacingBlock implements Wearable {
	public static final DirectionProperty FACING;
	@Nullable
	private BlockPattern melonGolemDispenserPattern;
	@Nullable
	private BlockPattern melonGolemPattern;
	private static final Predicate<BlockState> IS_GOLEM_HEAD_PREDICATE;

	public CarvedMelonBlock(AbstractBlock.Settings settings) {
		super(settings);
		this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH));
	}

	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		if (!oldState.isOf(state.getBlock())) {
			this.trySpawnEntity(world, pos);
		}
	}

	public boolean canDispense(WorldView world, BlockPos pos) {
		return this.getMelonGolemDispenserPattern().searchAround(world, pos) != null;
	}

	private void trySpawnEntity(World world, BlockPos pos) {
		BlockPattern.Result result = this.getMelonGolemPattern().searchAround(world, pos);
		int k;
		Iterator var6;
		ServerPlayerEntity serverPlayerEntity2;
		int m;
		if (result != null) {
			for(k = 0; k < this.getMelonGolemPattern().getHeight(); ++k) {
				CachedBlockPosition cachedBlockPosition = result.translate(0, k, 0);
				world.setBlockState(cachedBlockPosition.getBlockPos(), Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS);
				world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, cachedBlockPosition.getBlockPos(), Block.getRawIdFromState(cachedBlockPosition.getBlockState()));
			}
			MelonGolemEntity melonGolemEntity = (MelonGolemEntity)HavenMod.MELON_GOLEM_ENTITY.create(world);
			BlockPos blockPos = result.translate(0, 2, 0).getBlockPos();
			melonGolemEntity.refreshPositionAndAngles((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.05D, (double)blockPos.getZ() + 0.5D, 0.0F, 0.0F);
			world.spawnEntity(melonGolemEntity);
			var6 = world.getNonSpectatingEntities(ServerPlayerEntity.class, melonGolemEntity.getBoundingBox().expand(5.0D)).iterator();

			while(var6.hasNext()) {
				serverPlayerEntity2 = (ServerPlayerEntity)var6.next();
				Criteria.SUMMONED_ENTITY.trigger(serverPlayerEntity2, melonGolemEntity);
			}

			for(m = 0; m < this.getMelonGolemPattern().getHeight(); ++m) {
				CachedBlockPosition cachedBlockPosition2 = result.translate(0, m, 0);
				world.updateNeighbors(cachedBlockPosition2.getBlockPos(), Blocks.AIR);
			}
		}
	}

	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return (BlockState)this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING);
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
		FACING = HorizontalFacingBlock.FACING;
		IS_GOLEM_HEAD_PREDICATE = (state) -> {
			return state != null && (state.isOf(HavenMod.CARVED_MELON.BLOCK) || state.isOf(HavenMod.MELON_LANTERN.BLOCK));
		};
	}
}