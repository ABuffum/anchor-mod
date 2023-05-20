package fun.mousewich.item;

import fun.mousewich.block.archaeology.SuspiciousSandBlock;
import fun.mousewich.block.archaeology.SuspiciousSandBlockEntity;
import fun.mousewich.sound.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.Random;

public class BrushItem extends Item {
	public BrushItem(Settings settings) { super(settings); }
	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		PlayerEntity playerEntity = context.getPlayer();
		if (playerEntity != null) playerEntity.setCurrentHand(context.getHand());
		return ActionResult.CONSUME;
	}
	@Override
	public int getMaxUseTime(ItemStack stack) { return 225; }
	@Override
	public void usageTick(World world, LivingEntity user2, ItemStack stack, int remainingUseTicks) {
		if (remainingUseTicks < 0 || !(user2 instanceof PlayerEntity playerEntity)) {
			user2.stopUsingItem();
			return;
		}
		BlockHitResult blockHitResult = Item.raycast(world, playerEntity, RaycastContext.FluidHandling.NONE);
		BlockPos blockPos = blockHitResult.getBlockPos();
		if (blockHitResult.getType() == HitResult.Type.MISS) {
			user2.stopUsingItem();
			return;
		}
		int i = this.getMaxUseTime(stack) - remainingUseTicks + 1;
		if (i == 1 || i % 10 == 0) {
			SoundEvent soundEvent;
			BlockState blockState = world.getBlockState(blockPos);
			this.addDustParticles(world, blockHitResult, blockState, user2.getRotationVec(0.0f));
			if (blockState.getBlock() instanceof SuspiciousSandBlock suspiciousSandBlock) soundEvent = suspiciousSandBlock.getBrushSound();
			else soundEvent = ModSoundEvents.ITEM_BRUSH_BRUSHING_GENERIC;
			world.playSound(playerEntity, blockPos, soundEvent, SoundCategory.PLAYERS, 1, 1);
			if (!world.isClient() && world.getBlockEntity(blockPos) instanceof SuspiciousSandBlockEntity sus && sus.brush(world.getTime(), playerEntity, blockHitResult.getSide())) {
				stack.damage(1, user2, user -> user.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
			}
		}
	}

	public void addDustParticles(World world, BlockHitResult hitResult, BlockState state, Vec3d userRotation) {
		int i = world.getRandom().nextInt(3) + 8;
		BlockStateParticleEffect blockStateParticleEffect = new BlockStateParticleEffect(ParticleTypes.BLOCK, state);
		Direction direction = hitResult.getSide();
		DustParticlesOffset dustParticlesOffset = DustParticlesOffset.fromSide(userRotation, direction);
		Vec3d vec3d = hitResult.getPos();
		for (int j = 0; j < i; ++j) {
			world.addParticle(blockStateParticleEffect, vec3d.x - (double)(direction == Direction.WEST ? 1.0E-6f : 0.0f), vec3d.y, vec3d.z - (double)(direction == Direction.NORTH ? 1.0E-6f : 0.0f), dustParticlesOffset.xd() * 3.0 * world.getRandom().nextDouble(), 0.0, dustParticlesOffset.zd() * 3.0 * world.getRandom().nextDouble());
		}
	}

	record DustParticlesOffset(double xd, double yd, double zd) {
		public static DustParticlesOffset fromSide(Vec3d userRotation, Direction side) {
			return switch (side) {
				case DOWN -> new DustParticlesOffset(-userRotation.getX(), 0.0, userRotation.getZ());
				case UP -> new DustParticlesOffset(userRotation.getZ(), 0.0, -userRotation.getX());
				case NORTH -> new DustParticlesOffset(1.0, 0.0, -0.1);
				case SOUTH -> new DustParticlesOffset(-1.0, 0.0, 0.1);
				case WEST -> new DustParticlesOffset(-0.1, 0.0, -1.0);
				case EAST -> new DustParticlesOffset(0.1, 0.0, 1.0);
			};
		}
	}
}
