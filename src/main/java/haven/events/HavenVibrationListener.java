package haven.events;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import haven.HavenTags;
import haven.util.CodecUtils;
import haven.util.VectorUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.particle.VibrationParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.GameEventTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Util;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.dynamic.DynamicSerializableUuid;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockStateRaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.PositionSource;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class HavenVibrationListener implements GameEventListener {
	protected final PositionSource positionSource;
	protected final int range;
	protected final Callback callback;
	@Nullable
	protected Vibration vibration;
	protected float distance;
	protected int delay;

	public static Codec<HavenVibrationListener> createCodec(Callback callback) {
		return RecordCodecBuilder.create(instance -> instance.group(
				PositionSource.CODEC.fieldOf("source").forGetter(listener -> listener.positionSource),
				Codecs.NONNEGATIVE_INT.fieldOf("range").forGetter(listener -> listener.range),
				Vibration.CODEC.optionalFieldOf("event").forGetter(listener -> Optional.ofNullable(listener.vibration)),
				Codec.floatRange(0.0f, Float.MAX_VALUE).fieldOf("event_distance").orElse(0.0f).forGetter(listener -> listener.distance),
				Codecs.NONNEGATIVE_INT.fieldOf("event_delay").orElse(0).forGetter(listener -> listener.delay))
				.apply(instance, (positionSource, range, vibration, distance, delay) ->
						new HavenVibrationListener((PositionSource)positionSource, (int)range, (Callback)callback, vibration.orElse(null), (float)distance, (int)delay)));
	}

	public HavenVibrationListener(PositionSource positionSource, int range, Callback callback, @Nullable Vibration vibration, float distance, int delay) {
		this.positionSource = positionSource;
		this.range = range;
		this.callback = callback;
		this.vibration = vibration;
		this.distance = distance;
		this.delay = delay;
	}

	public void tick(World world) {
		if (world instanceof ServerWorld serverWorld) {
			if (this.vibration != null) {
				--this.delay;
				if (this.delay <= 0) {
					this.delay = 0;
					this.callback.accept(serverWorld, this, new BlockPos(this.vibration.pos), this.vibration.gameEvent, this.vibration.getEntity(serverWorld).orElse(null), this.vibration.getOwner(serverWorld).orElse(null), this.distance);
					this.vibration = null;
				}
			}
		}
	}

	@Override
	public PositionSource getPositionSource() { return this.positionSource; }

	@Override
	public int getRange() { return this.range; }

	@Override
	public boolean listen(World world, GameEvent event, @Nullable Entity entity, BlockPos pos) {
		if (this.vibration != null) return false;
		if (!this.callback.canAccept(event, world, entity, pos)) return false;
		Optional<BlockPos> optional = this.positionSource.getPos(world);
		if (optional.isEmpty()) return false;
		Vec3d vec3d = Vec3d.ofCenter(pos);
		BlockPos pos2 = optional.get();
		Vec3d vec3d2 = Vec3d.ofCenter(pos2);
		if (world instanceof ServerWorld) {
			if (!this.callback.accepts((ServerWorld)world, this, new BlockPos(vec3d), event, entity, pos)) return false;
		}
		if (HavenVibrationListener.isOccluded(world, vec3d, vec3d2)) return false;
		if (world instanceof ServerWorld) this.listen((ServerWorld)world, event, vec3d, vec3d2, entity);
		return true;
	}

	private void listen(ServerWorld world, GameEvent gameEvent, Vec3d start, Vec3d end, Entity entity) {
		this.distance = (float)start.distanceTo(end);
		this.vibration = new Vibration(gameEvent, this.distance, start, entity);
		this.delay = MathHelper.floor(this.distance);
		world.spawnParticles(new VibrationParticleEffect(new net.minecraft.world.Vibration(new BlockPos(start), this.positionSource, this.delay)), start.getX(), start.getY(), start.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
		this.callback.onListen();
	}

	private static boolean isOccluded(World world, Vec3d start, Vec3d end) {
		Vec3d vec3d = new Vec3d((double)MathHelper.floor(start.x) + 0.5, (double)MathHelper.floor(start.y) + 0.5, (double)MathHelper.floor(start.z) + 0.5);
		Vec3d vec3d2 = new Vec3d((double)MathHelper.floor(end.x) + 0.5, (double)MathHelper.floor(end.y) + 0.5, (double)MathHelper.floor(end.z) + 0.5);
		for (Direction direction : Direction.values()) {
			Vec3d vec3d3 = VectorUtils.withBias(vec3d, direction, 1.0E-5f);
			if (world.raycast(new BlockStateRaycastContext(vec3d3, vec3d2, state -> state.isIn(BlockTags.OCCLUDES_VIBRATION_SIGNALS))).getType() == HitResult.Type.BLOCK) continue;
			return false;
		}
		return true;
	}

	public static interface Callback {
		default public Tag.Identified<GameEvent> getTag() { return GameEventTags.VIBRATIONS; }
		default public boolean triggersAvoidCriterion() { return false; }

		default public boolean canAccept(GameEvent gameEvent, World world, Entity entity, BlockPos pos) {
			if (!this.getTag().contains(gameEvent)) return false;
			if (entity != null) {
				if (entity.isSpectator()) return false;
				if (entity.bypassesSteppingEffects() && GameEventTags.IGNORE_VIBRATIONS_SNEAKING.contains(gameEvent)) {
					System.out.println("2");
					if (this.triggersAvoidCriterion() && entity instanceof ServerPlayerEntity serverPlayerEntity) {
						System.out.println("3");
						//Criteria.AVOID_VIBRATION.trigger(serverPlayerEntity); //TODO? I don't know what this does
					}
					return false;
				}
				if (entity.occludeVibrationSignals()) return false;
			}
			//TODO: Dampens Vibrations - I don't think this works correctly
			BlockState state = world.getBlockState(pos);
			if (state != null) return !state.isIn(HavenTags.Blocks.DAMPENS_VIBRATIONS);
			return true;
		}

		public boolean accepts(ServerWorld var1, GameEventListener var2, BlockPos var3, GameEvent var4, Entity entity, BlockPos pos);

		public void accept(ServerWorld var1, GameEventListener var2, BlockPos var3, GameEvent var4, @Nullable Entity var5, @Nullable Entity var6, float var7);

		default public void onListen() { }
	}

	public static final Codec<Vec3d> VEC3D_CODEC = Codec.DOUBLE.listOf().comapFlatMap(list2 -> Util.toArray(list2, 3).map(list -> new Vec3d((Double)list.get(0), (Double)list.get(1), (Double)list.get(2))), vec3d -> List.of(Double.valueOf(vec3d.getX()), Double.valueOf(vec3d.getY()), Double.valueOf(vec3d.getZ())));



	public record Vibration(GameEvent gameEvent, float distance, Vec3d pos, @Nullable UUID uuid, @Nullable UUID projectileOwnerUuid, @Nullable Entity entity) {
		public static final Codec<Vibration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				CodecUtils.getCodec(Registry.GAME_EVENT).fieldOf("game_event").forGetter(vibration -> vibration.gameEvent),
				Codec.floatRange(0.0f, Float.MAX_VALUE).fieldOf("distance").forGetter(vibration -> vibration.distance),
				VEC3D_CODEC.fieldOf("pos").forGetter(vibration -> vibration.pos),
				DynamicSerializableUuid.CODEC.optionalFieldOf("source").forGetter(vibration -> Optional.ofNullable(vibration.uuid())),
				DynamicSerializableUuid.CODEC.optionalFieldOf("projectile_owner")
						.forGetter(vibration -> Optional.ofNullable(vibration.projectileOwnerUuid())))
				.apply(instance, (event, distance, pos, uuid, projectileOwnerUuid) -> new Vibration((GameEvent)event, (float)distance, (Vec3d)pos, uuid.orElse(null), projectileOwnerUuid.orElse(null))));

		public Vibration(GameEvent gameEvent, float distance, Vec3d pos, @Nullable UUID uuid, @Nullable UUID projectileOwnerUuid) {
			this(gameEvent, distance, pos, uuid, projectileOwnerUuid, null);
		}

		public Vibration(GameEvent gameEvent, float distance, Vec3d pos, @Nullable Entity entity) {
			this(gameEvent, distance, pos, entity == null ? null : entity.getUuid(), Vibration.getOwnerUuid(entity), entity);
		}

		@Nullable
		private static UUID getOwnerUuid(@Nullable Entity entity) {
			ProjectileEntity projectileEntity;
			if (entity instanceof ProjectileEntity && (projectileEntity = (ProjectileEntity)entity).getOwner() != null) return projectileEntity.getOwner().getUuid();
			return null;
		}

		public Optional<Entity> getEntity(ServerWorld world) {
			return Optional.ofNullable(this.entity).or(() -> Optional.ofNullable(this.uuid).map(world::getEntity));
		}

		public Optional<Entity> getOwner(ServerWorld world) {
			return this.getEntity(world).filter(entity -> entity instanceof ProjectileEntity).map(entity -> (ProjectileEntity)entity).map(ProjectileEntity::getOwner).or(() -> Optional.ofNullable(this.projectileOwnerUuid).map(world::getEntity));
		}
	}
}
