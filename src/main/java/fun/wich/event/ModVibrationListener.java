package fun.wich.event;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fun.wich.advancement.ModCriteria;
import fun.wich.gen.data.tag.ModBlockTags;
import fun.wich.origins.power.PowersUtil;
import fun.wich.origins.power.SoftStepsPower;
import fun.wich.util.CodecUtil;
import fun.wich.util.SculkUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.particle.VibrationParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.GameEventTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Util;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.dynamic.DynamicSerializableUuid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.PositionSource;
import net.minecraft.world.event.listener.GameEventListener;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ModVibrationListener implements GameEventListener {
	protected final PositionSource positionSource;
	protected final int range;
	protected final Callback callback;
	protected Vibration vibration;
	protected float distance;
	protected int delay;
	public static Codec<ModVibrationListener> createCodec(Callback callback) {
		return RecordCodecBuilder.create(instance -> instance.group(
				PositionSource.CODEC.fieldOf("source").forGetter(listener -> listener.positionSource),
				Codecs.NONNEGATIVE_INT.fieldOf("range").forGetter(listener -> listener.range),
				Vibration.CODEC.optionalFieldOf("event").forGetter(listener -> Optional.ofNullable(listener.vibration)),
				Codec.floatRange(0.0f, Float.MAX_VALUE).fieldOf("event_distance").orElse(0.0f).forGetter(listener -> listener.distance),
				Codecs.NONNEGATIVE_INT.fieldOf("event_delay").orElse(0).forGetter(listener -> listener.delay))
				.apply(instance, (positionSource, range, vibration, distance, delay) ->
						new ModVibrationListener(positionSource, range, callback, vibration.orElse(null), distance, delay)));
	}
	public ModVibrationListener(PositionSource positionSource, int range, Callback callback) {
		this(positionSource, range, callback, null, 0, 0);
	}
	public ModVibrationListener(PositionSource positionSource, int range, Callback callback, Vibration vibration, float distance, int delay) {
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
	public boolean listen(World world, GameEvent event, Entity entity, BlockPos pos) {
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
		if (SculkUtil.isOccluded(world, vec3d, vec3d2)) return false;
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
	public interface Callback {
		default Tag.Identified<GameEvent> getTag() { return GameEventTags.VIBRATIONS; }
		default boolean triggersAvoidCriterion() { return false; }

		default boolean canAccept(GameEvent gameEvent, World world, Entity entity, BlockPos pos) {
			if (!this.getTag().contains(gameEvent)) return false;
			if (entity != null) {
				if (entity.isSpectator()) return false;
				if (entity.bypassesSteppingEffects() && GameEventTags.IGNORE_VIBRATIONS_SNEAKING.contains(gameEvent)) {
					if (this.triggersAvoidCriterion() && entity instanceof ServerPlayerEntity serverPlayerEntity) {
						ModCriteria.AVOID_VIBRATION.trigger(serverPlayerEntity);
					}
					return false;
				}
				if (entity.occludeVibrationSignals()) return false;
				if (PowersUtil.Active(entity, SoftStepsPower.class)) return false;
			}
			BlockState state = world.getBlockState(pos);
			if (state != null) return !state.isIn(ModBlockTags.DAMPENS_VIBRATIONS);
			return true;
		}
		boolean accepts(ServerWorld var1, GameEventListener var2, BlockPos var3, GameEvent var4, Entity entity, BlockPos pos);
		void accept(ServerWorld var1, GameEventListener var2, BlockPos var3, GameEvent var4, Entity var5, Entity var6, float var7);
		default void onListen() { }
		ModVibrationListener getModEventListener();
	}
	public static final Codec<Vec3d> VEC3D_CODEC = Codec.DOUBLE.listOf().comapFlatMap(list2 -> Util.toArray(list2, 3).map(list -> new Vec3d(list.get(0), list.get(1), list.get(2))), vec3d -> List.of(vec3d.getX(), vec3d.getY(), vec3d.getZ()));
	public record Vibration(GameEvent gameEvent, float distance, Vec3d pos, UUID uuid, UUID projectileOwnerUuid, Entity entity) {
		public static final Codec<Vibration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				CodecUtil.getCodec(Registry.GAME_EVENT).fieldOf("game_event")
						.forGetter(Vibration::gameEvent),
				Codec.floatRange(0.0f, Float.MAX_VALUE).fieldOf("distance")
						.forGetter(Vibration::distance),
				VEC3D_CODEC.fieldOf("pos").forGetter(Vibration::pos),
				DynamicSerializableUuid.CODEC.optionalFieldOf("source")
						.forGetter(vibration -> Optional.ofNullable(vibration.uuid())),
				DynamicSerializableUuid.CODEC.optionalFieldOf("projectile_owner")
						.forGetter(vibration -> Optional.ofNullable(vibration.projectileOwnerUuid())))
				.apply(instance, (event, distance, pos, uuid, projectileOwnerUuid) -> new Vibration(event, distance, pos, uuid.orElse(null), projectileOwnerUuid.orElse(null))));
		public Vibration(GameEvent gameEvent, float distance, Vec3d pos, UUID uuid, UUID projectileOwnerUuid) {
			this(gameEvent, distance, pos, uuid, projectileOwnerUuid, null);
		}
		public Vibration(GameEvent gameEvent, float distance, Vec3d pos, Entity entity) {
			this(gameEvent, distance, pos, entity == null ? null : entity.getUuid(), Vibration.getOwnerUuid(entity), entity);
		}
		private static UUID getOwnerUuid(Entity entity) {
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

	public static final GameEvent[] field_43323 = new GameEvent[]{
			ModGameEvent.RESONATE_1, ModGameEvent.RESONATE_2, ModGameEvent.RESONATE_3, ModGameEvent.RESONATE_4,
			ModGameEvent.RESONATE_5, ModGameEvent.RESONATE_6, ModGameEvent.RESONATE_7, ModGameEvent.RESONATE_8,
			ModGameEvent.RESONATE_9, ModGameEvent.RESONATE_10, ModGameEvent.RESONATE_11, ModGameEvent.RESONATE_12,
			ModGameEvent.RESONATE_13, ModGameEvent.RESONATE_14, ModGameEvent.RESONATE_15
	};
	public static GameEvent method_49881(int i) { return field_43323[i - 1]; }
}
