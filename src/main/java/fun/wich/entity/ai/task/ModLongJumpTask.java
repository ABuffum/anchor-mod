package fun.wich.entity.ai.task;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import fun.wich.effect.ModStatusEffects;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.ai.brain.BlockPosLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.collection.Weighted;
import net.minecraft.util.collection.Weighting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ModLongJumpTask <E extends MobEntity> extends Task<E> {
	protected static final int MAX_COOLDOWN = 20;
	private static final int TARGET_RETAIN_TIME = 40;
	protected static final int PATHING_DISTANCE = 8;
	private static final int RUN_TIME = 200;
	private static final List<Integer> RAM_RANGES = Lists.newArrayList(65, 70, 75, 80);
	private final UniformIntProvider cooldownRange;
	protected final int verticalRange;
	protected final int horizontalRange;
	protected final float maxRange;
	protected List<Target> targets = Lists.newArrayList();
	protected Optional<Vec3d> lastPos = Optional.empty();
	protected Vec3d lastTarget;
	protected int cooldown;
	protected long targetTime;
	private Function<E, SoundEvent> entityToSound;
	private final Predicate<BlockState> jumpToPredicate;

	public ModLongJumpTask(UniformIntProvider cooldownRange, int verticalRange, int horizontalRange, float maxRange, Function<E, SoundEvent> entityToSound) {
		this(cooldownRange, verticalRange, horizontalRange, maxRange, entityToSound, state -> false);
	}

	public ModLongJumpTask(UniformIntProvider cooldownRange, int verticalRange, int horizontalRange, float maxRange, Function<E, SoundEvent> entityToSound, Predicate<BlockState> jumpToPredicate) {
		super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryModuleState.REGISTERED, MemoryModuleType.LONG_JUMP_COOLING_DOWN, MemoryModuleState.VALUE_ABSENT, MemoryModuleType.LONG_JUMP_MID_JUMP, MemoryModuleState.VALUE_ABSENT), RUN_TIME);
		this.cooldownRange = cooldownRange;
		this.verticalRange = verticalRange;
		this.horizontalRange = horizontalRange;
		this.maxRange = maxRange;
		this.entityToSound = entityToSound;
		this.jumpToPredicate = jumpToPredicate;
	}

	@Override
	protected boolean shouldRun(ServerWorld serverWorld, MobEntity mobEntity) {
		boolean bl = mobEntity.isOnGround()
				&& !mobEntity.isTouchingWater()
				&& !mobEntity.isInLava()
				&& !mobEntity.hasStatusEffect(ModStatusEffects.STICKY)
				&& !serverWorld.getBlockState(mobEntity.getBlockPos()).isOf(Blocks.HONEY_BLOCK);
		if (!bl) mobEntity.getBrain().remember(MemoryModuleType.LONG_JUMP_COOLING_DOWN, this.cooldownRange.get(serverWorld.random) / 2);
		return bl;
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld serverWorld, MobEntity mobEntity, long l) {
		boolean bl = this.lastPos.isPresent() && this.lastPos.get().equals(mobEntity.getPos()) && this.cooldown > 0 && !mobEntity.isInsideWaterOrBubbleColumn() && (this.lastTarget != null || !this.targets.isEmpty());
		if (!bl && mobEntity.getBrain().getOptionalMemory(MemoryModuleType.LONG_JUMP_MID_JUMP).isEmpty()) {
			mobEntity.getBrain().remember(MemoryModuleType.LONG_JUMP_COOLING_DOWN, this.cooldownRange.get(serverWorld.random) / 2);
			mobEntity.getBrain().forget(MemoryModuleType.LOOK_TARGET);
		}
		return bl;
	}

	@Override
	protected void run(ServerWorld serverWorld, E mobEntity, long l) {
		this.lastTarget = null;
		this.cooldown = MAX_COOLDOWN;
		this.lastPos = Optional.of(mobEntity.getPos());
		BlockPos blockPos = mobEntity.getBlockPos();
		int i = blockPos.getX();
		int j = blockPos.getY();
		int k = blockPos.getZ();
		this.targets = BlockPos.stream(i - this.horizontalRange, j - this.verticalRange, k - this.horizontalRange, i + this.horizontalRange, j + this.verticalRange, k + this.horizontalRange).filter(blockPos2 -> !blockPos2.equals(blockPos)).map(blockPos2 -> new Target(blockPos2.toImmutable(), MathHelper.ceil(blockPos.getSquaredDistance(blockPos2)))).collect(Collectors.toCollection(Lists::newArrayList));
	}

	@Override
	protected void keepRunning(ServerWorld serverWorld, E mobEntity, long l) {
		if (this.lastTarget != null) {
			if (l - this.targetTime >= TARGET_RETAIN_TIME) {
				mobEntity.setYaw(mobEntity.bodyYaw);
				mobEntity.setNoDrag(true);
				double d = this.lastTarget.length();
				double e = d + mobEntity.getJumpBoostVelocityModifier();
				mobEntity.setVelocity(this.lastTarget.multiply(e / d));
				mobEntity.getBrain().remember(MemoryModuleType.LONG_JUMP_MID_JUMP, true);
				serverWorld.playSoundFromEntity(null, (Entity)mobEntity, this.entityToSound.apply(mobEntity), SoundCategory.NEUTRAL, 1.0f, 1.0f);
			}
		}
		else {
			--this.cooldown;
			this.method_41342(serverWorld, mobEntity, l);
		}
	}

	protected void method_41342(ServerWorld serverWorld, E mobEntity, long l) {
		while (!this.targets.isEmpty()) {
			Vec3d vec3d2;
			BlockPos blockPos;
			Optional<Target> optional = this.getTarget(serverWorld);
			if (optional.isEmpty() || !this.canJumpTo(serverWorld, mobEntity, blockPos = optional.get().getPos()) || (vec3d2 = this.getRammingVelocity(mobEntity, Vec3d.ofCenter(blockPos))) == null) continue;
			mobEntity.getBrain().remember(MemoryModuleType.LOOK_TARGET, new BlockPosLookTarget(blockPos));
			EntityNavigation entityNavigation = mobEntity.getNavigation();
			Path path = entityNavigation.findPathTo(blockPos, 0, PATHING_DISTANCE);
			if (path != null && path.reachesTarget()) continue;
			this.lastTarget = vec3d2;
			this.targetTime = l;
			return;
		}
	}

	protected Optional<Target> getTarget(ServerWorld world) {
		Optional<Target> optional = Weighting.getRandom(world.random, this.targets);
		optional.ifPresent(this.targets::remove);
		return optional;
	}

	protected boolean canJumpTo(ServerWorld world, E entity, BlockPos pos) {
		BlockPos blockPos = entity.getBlockPos();
		int i = blockPos.getX();
		int j = blockPos.getZ();
		if (i == pos.getX() && j == pos.getZ()) {
			return false;
		}
		if (!entity.getNavigation().isValidPosition(pos) && !this.jumpToPredicate.test(world.getBlockState(pos.down()))) {
			return false;
		}
		return entity.getPathfindingPenalty(LandPathNodeMaker.getLandNodeType((entity).world, pos.mutableCopy())) == 0.0f;
	}
	protected Vec3d getRammingVelocity(MobEntity entity, Vec3d pos) {
		ArrayList<Integer> list = Lists.newArrayList(RAM_RANGES);
		Collections.shuffle(list);
		for (int i : list) {
			Vec3d vec3d = this.getRammingVelocity(entity, pos, i);
			if (vec3d == null) continue;
			return vec3d;
		}
		return null;
	}
	private Vec3d getRammingVelocity(MobEntity entity, Vec3d pos, int range) {
		Vec3d vec3d = entity.getPos();
		Vec3d vec3d2 = new Vec3d(pos.x - vec3d.x, 0.0, pos.z - vec3d.z).normalize().multiply(0.5);
		pos = pos.subtract(vec3d2);
		Vec3d vec3d3 = pos.subtract(vec3d);
		float f = (float)range * (float)Math.PI / 180.0f;
		double d = Math.atan2(vec3d3.z, vec3d3.x);
		double e = vec3d3.subtract(0.0, vec3d3.y, 0.0).lengthSquared();
		double g = Math.sqrt(e);
		double h = vec3d3.y;
		double i = Math.sin(2.0f * f);
		double k = Math.pow(Math.cos(f), 2.0);
		double l = Math.sin(f);
		double m = Math.cos(f);
		double n = Math.sin(d);
		double o = Math.cos(d);
		double p = e * 0.08 / (g * i - 2.0 * h * k);
		if (p < 0.0) return null;
		double q = Math.sqrt(p);
		if (q > (double)this.maxRange) return null;
		double r = q * m;
		double s = q * l;
		int t = MathHelper.ceil(g / r) * 2;
		double u = 0.0;
		Vec3d vec3d4 = null;
		for (int v = 0; v < t - 1; ++v) {
			double w = l / m * (u += g / (double)t) - Math.pow(u, 2.0) * 0.08 / (2.0 * p * Math.pow(m, 2.0));
			double x = u * o;
			double y = u * n;
			Vec3d vec3d5 = new Vec3d(vec3d.x + x, vec3d.y + w, vec3d.z + y);
			if (vec3d4 != null && !this.canReach(entity, vec3d4, vec3d5)) return null;
			vec3d4 = vec3d5;
		}
		return new Vec3d(r * o, s, r * n).multiply(0.95f);
	}
	private boolean canReach(MobEntity entity, Vec3d startPos, Vec3d endPos) {
		EntityDimensions entityDimensions = entity.getDimensions(EntityPose.LONG_JUMPING);
		Vec3d vec3d = endPos.subtract(startPos);
		double d = Math.min(entityDimensions.width, entityDimensions.height);
		int i = MathHelper.ceil(vec3d.length() / d);
		Vec3d vec3d2 = vec3d.normalize();
		Vec3d vec3d3 = startPos;
		for (int j = 0; j < i; ++j) {
			vec3d3 = j == i - 1 ? endPos : vec3d3.add(vec3d2.multiply(d * (double)0.9f));
			Box box = entityDimensions.getBoxAt(vec3d3);
			if (entity.world.isSpaceEmpty(entity, box)) continue;
			return false;
		}
		return true;
	}
	public static class Target extends Weighted.Absent {
		private final BlockPos pos;
		public Target(BlockPos pos, int weight) {
			super(weight);
			this.pos = pos;
		}
		public BlockPos getPos() { return this.pos; }
	}
}
