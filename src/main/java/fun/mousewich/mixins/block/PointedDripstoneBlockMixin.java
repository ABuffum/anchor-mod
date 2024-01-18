package fun.mousewich.mixins.block;

import fun.mousewich.entity.projectile.JavelinEntity;
import fun.mousewich.util.PointedDripstoneUtil;
import fun.mousewich.ModBase;
import net.minecraft.block.*;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.Random;

@Mixin(PointedDripstoneBlock.class)
public class PointedDripstoneBlockMixin extends Block implements LandingBlock, Waterloggable {
	public PointedDripstoneBlockMixin(Settings settings) { super(settings); }

	@Inject(method="dripTick", at = @At("HEAD"), cancellable = true)
	private static void DripTick(BlockState state, ServerWorld world, BlockPos pos, float dripChance, CallbackInfo ci) {
		float f;
		if (dripChance > 0.17578125f) {
			ci.cancel();
			return;
		}
		if (!PointedDripstoneUtil.isHeldByPointedDripstone(state, world, pos)) {
			ci.cancel();
			return;
		}
		Optional<PointedDripstoneUtil.DrippingFluid> optional = PointedDripstoneUtil.getFluid(world, pos, state);
		if (optional.isEmpty()) {
			ci.cancel();
			return;
		}
		Fluid fluid = optional.get().fluid;
		if (fluid == Fluids.WATER) f = 0.17578125f;
		else if (fluid == Fluids.LAVA) f = 0.05859375f;
		else {
			ci.cancel();
			return;
		}
		if (dripChance >= f) {
			ci.cancel();
			return;
		}
		BlockPos blockPos = PointedDripstoneUtil.getTipPos(state, world, pos, 11, false);
		if (blockPos == null) {
			ci.cancel();
			return;
		}
		if (optional.get().sourceState.isOf(ModBase.MUD.asBlock()) && fluid == Fluids.WATER) {
			BlockState blockState = Blocks.CLAY.getDefaultState();
			world.setBlockState(optional.get().pos, blockState);
			Block.pushEntitiesUpBeforeBlockChange(optional.get().sourceState, blockState, world, optional.get().pos);
			world.emitGameEvent(GameEvent.BLOCK_CHANGE, optional.get().pos);
			world.syncWorldEvent(WorldEvents.POINTED_DRIPSTONE_DRIPS, blockPos, 0);
			ci.cancel();
			return;
		}
		BlockPos blockPos2 = PointedDripstoneUtil.getCauldronPos(world, blockPos, fluid);
		if (blockPos2 == null) {
			ci.cancel();
			return;
		}
		world.syncWorldEvent(WorldEvents.POINTED_DRIPSTONE_DRIPS, blockPos, 0);
		BlockState blockState2 = world.getBlockState(blockPos2);
		world.getBlockTickScheduler().schedule(blockPos2, blockState2.getBlock(), 50 + blockPos.getY() - blockPos2.getY());
		ci.cancel();
	}

	@Inject(method="randomDisplayTick", at = @At("HEAD"), cancellable = true)
	public void RandomDisplayTick(BlockState state, World world, BlockPos pos, Random random, CallbackInfo ci) {
		if (!PointedDripstoneBlock.canDrip(state)) return;
		float f = random.nextFloat();
		if (f > 0.12f) return;
		PointedDripstoneUtil.getFluid(world, pos, state)
				.filter(fluid -> f < 0.02f || PointedDripstoneUtil.isFluidLiquid(fluid.fluid))
				.ifPresent(fluid -> PointedDripstoneUtil.createParticle(world, pos, state, fluid.fluid));
		ci.cancel();
	}

	@Inject(method="getDripFluid(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/fluid/Fluid;", at = @At("HEAD"), cancellable = true)
	private static void GetDripFluid(World world, BlockPos pos, CallbackInfoReturnable<Fluid> cir) {
		cir.setReturnValue(PointedDripstoneUtil.getFluid(world, pos, world.getBlockState(pos)).map(fluid -> fluid.fluid)
				.filter(PointedDripstoneUtil::isFluidLiquid).orElse(Fluids.EMPTY));
	}
	@Inject(method="getDripFluid(Lnet/minecraft/world/World;Lnet/minecraft/fluid/Fluid;)Lnet/minecraft/fluid/Fluid;", at = @At("HEAD"), cancellable = true)
	private static void GetDripFluid(World world, Fluid fluid, CallbackInfoReturnable<Fluid> cir) {
		cir.setReturnValue(PointedDripstoneUtil.getDripFluid(world, fluid));
	}

	@Inject(method="createParticle(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V", at = @At("HEAD"), cancellable = true)
	private static void CreateParticle(World world, BlockPos pos, BlockState state, CallbackInfo ci) {
		PointedDripstoneUtil.getFluid(world, pos, state).ifPresent(fluid -> PointedDripstoneUtil.createParticle(world, pos, state, fluid.fluid));
	}

	@Inject(method="createParticle(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/fluid/Fluid;)V", at = @At("HEAD"), cancellable = true)
	private static void CreateParticle(World world, BlockPos pos, BlockState state, Fluid fluid, CallbackInfo ci) {
		PointedDripstoneUtil.createParticle(world, pos, state, fluid);
		ci.cancel();
	}

	@Inject(method="onProjectileHit", at=@At("HEAD"), cancellable = true)
	public void OnJavelinHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile, CallbackInfo ci) {
		BlockPos blockPos = hit.getBlockPos();
		if (!world.isClient && projectile.canModifyAt(world, blockPos) && projectile instanceof JavelinEntity && projectile.getVelocity().length() > 0.6) {
			world.breakBlock(blockPos, true);
			ci.cancel();
		}
	}
}
