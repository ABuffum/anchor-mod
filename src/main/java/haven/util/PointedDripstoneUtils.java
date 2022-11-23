package haven.util;

import haven.ModBase;
import haven.mixins.blocks.AbstractCauldronBlockInvoker;
import haven.mixins.DimensionTypeAccessor;
import net.minecraft.block.*;
import net.minecraft.block.enums.Thickness;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.dimension.DimensionType;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class PointedDripstoneUtils {
	public static Optional<DrippingFluid> getFluid(World world, BlockPos pos2, BlockState state) {
		if (!isPointingDown(state)) {
			return Optional.empty();
		}
		return getSupportingPos(world, pos2, state, 11).map(pos -> {
			BlockPos blockPos = pos.up();
			BlockState blockState = world.getBlockState(blockPos);
			DimensionType dimension = world.getDimension();
			Fluid fluid = blockState.isOf(ModBase.MUD.getBlock()) && !((DimensionTypeAccessor)dimension).getUltrawarm() ? Fluids.WATER : world.getFluidState(blockPos).getFluid();
			return new DrippingFluid(blockPos, fluid, blockState);
		});
	}

	public static Fluid getDripFluid(World world, Fluid fluid) {
		if (fluid.matchesType(Fluids.EMPTY)) {
			DimensionType dimension = world.getDimension();
			return ((DimensionTypeAccessor)dimension).getUltrawarm() ? Fluids.LAVA : Fluids.WATER;
		}
		return fluid;
	}

	public static boolean isFluidLiquid(Fluid fluid) { return fluid == Fluids.LAVA || fluid == Fluids.WATER; }

	public static boolean isPointingDown(BlockState state) { return isPointedDripstoneFacingDirection(state, Direction.DOWN); }

	public static boolean isPointingUp(BlockState state) { return isPointedDripstoneFacingDirection(state, Direction.UP); }

	public static boolean isPointedDripstoneFacingDirection(BlockState state, Direction direction) {
		return state.isOf(Blocks.POINTED_DRIPSTONE) && state.get(PointedDripstoneBlock.VERTICAL_DIRECTION) == direction;
	}

	public static boolean isHeldByPointedDripstone(BlockState state, WorldView world, BlockPos pos) {
		return isPointingDown(state) && !world.getBlockState(pos.up()).isOf(Blocks.POINTED_DRIPSTONE);
	}

	public static boolean isTip(BlockState state, boolean allowMerged) {
		if (!state.isOf(Blocks.POINTED_DRIPSTONE)) {
			return false;
		}
		Thickness thickness = state.get(PointedDripstoneBlock.THICKNESS);
		return thickness == Thickness.TIP || allowMerged && thickness == Thickness.TIP_MERGE;
	}

	public static BlockPos getTipPos(BlockState state2, WorldAccess world, BlockPos pos2, int range, boolean allowMerged) {
		if (isTip(state2, allowMerged)) {
			return pos2;
		}
		Direction direction = state2.get(PointedDripstoneBlock.VERTICAL_DIRECTION);
		BiPredicate<BlockPos, BlockState> biPredicate = (pos, state) -> state.isOf(Blocks.POINTED_DRIPSTONE) && state.get(PointedDripstoneBlock.VERTICAL_DIRECTION) == direction;
		return searchInDirection(world, pos2, direction.getDirection(), biPredicate, state -> isTip(state, allowMerged), range).orElse(null);
	}

	public static Optional<BlockPos> getSupportingPos(World world, BlockPos pos2, BlockState state2, int range) {
		Direction direction = state2.get(PointedDripstoneBlock.VERTICAL_DIRECTION);
		BiPredicate<BlockPos, BlockState> biPredicate = (pos, state) -> state.isOf(Blocks.POINTED_DRIPSTONE) && state.get(PointedDripstoneBlock.VERTICAL_DIRECTION) == direction;
		return searchInDirection(world, pos2, direction.getOpposite().getDirection(), biPredicate, state -> !state.isOf(Blocks.POINTED_DRIPSTONE), range);
	}

	public static Optional<BlockPos> searchInDirection(WorldAccess world, BlockPos pos, Direction.AxisDirection direction, BiPredicate<BlockPos, BlockState> continuePredicate, Predicate<BlockState> stopPredicate, int range) {
		Direction direction2 = Direction.get(direction, Direction.Axis.Y);
		BlockPos.Mutable mutable = pos.mutableCopy();
		for (int i = 1; i < range; ++i) {
			mutable.move(direction2);
			BlockState blockState = world.getBlockState(mutable);
			if (stopPredicate.test(blockState)) {
				return Optional.of(mutable.toImmutable());
			}
			if (!world.isOutOfHeightLimit(mutable.getY()) && continuePredicate.test(mutable, blockState)) continue;
			return Optional.empty();
		}
		return Optional.empty();
	}

	public static void createParticle(World world, BlockPos pos, BlockState state, Fluid fluid) {
		Vec3d vec3d = state.getModelOffset(world, pos);
		double d = 0.0625;
		double e = (double)pos.getX() + 0.5 + vec3d.x;
		double f = (double)((float)(pos.getY() + 1) - 0.6875f) - 0.0625;
		double g = (double)pos.getZ() + 0.5 + vec3d.z;
		Fluid fluid2 = getDripFluid(world, fluid);
		DefaultParticleType particleEffect = fluid2.isIn(FluidTags.LAVA) ? ParticleTypes.DRIPPING_DRIPSTONE_LAVA : ParticleTypes.DRIPPING_DRIPSTONE_WATER;
		world.addParticle(particleEffect, e, f, g, 0.0, 0.0, 0.0);
	}

	public static BlockPos getCauldronPos(World world, BlockPos pos2, Fluid fluid) {
		Predicate<BlockState> predicate = state -> {
			Block block = state.getBlock();
			if (block instanceof AbstractCauldronBlock acb) {
				return ((AbstractCauldronBlockInvoker)acb).canBeFilledByDripstone(fluid);
			}
			return false;
		};
		BiPredicate<BlockPos, BlockState> biPredicate = (pos, state) -> canDripThrough(world, pos, state);
		return searchInDirection(world, pos2, Direction.DOWN.getDirection(), biPredicate, predicate, 11).orElse(null);
	}

	public record Emitter(@Nullable Entity sourceEntity, @Nullable BlockState affectedState) {
		public static Emitter of(@Nullable Entity sourceEntity) {
			return new Emitter(sourceEntity, null);
		}

		public static Emitter of(@Nullable BlockState affectedState) {
			return new Emitter(null, affectedState);
		}

		public static Emitter of(@Nullable Entity sourceEntity, @Nullable BlockState affectedState) {
			return new Emitter(sourceEntity, affectedState);
		}
	}

	private static final VoxelShape DRIP_COLLISION_SHAPE = Block.createCuboidShape(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);
	public static boolean canDripThrough(BlockView world, BlockPos pos, BlockState state) {
		if (state.isAir()) {
			return true;
		}
		if (state.isOpaqueFullCube(world, pos)) {
			return false;
		}
		if (!state.getFluidState().isEmpty()) {
			return false;
		}
		VoxelShape voxelShape = state.getCollisionShape(world, pos);
		return !VoxelShapes.matchesAnywhere(DRIP_COLLISION_SHAPE, voxelShape, BooleanBiFunction.AND);
	}

	public static class DrippingFluid {
		public final BlockPos pos;
		public final Fluid fluid;
		public final BlockState sourceState;
		public DrippingFluid(BlockPos pos, Fluid fluid, BlockState sourceState) {
			this.pos = pos;
			this.fluid = fluid;
			this.sourceState = sourceState;
		}
	}
}
