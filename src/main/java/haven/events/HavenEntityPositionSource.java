package haven.events;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.dynamic.DynamicSerializableUuid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.PositionSource;
import net.minecraft.world.event.PositionSourceType;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public class HavenEntityPositionSource implements PositionSource {
	public static final Codec<HavenEntityPositionSource> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			DynamicSerializableUuid.CODEC.fieldOf("source_entity").forGetter(HavenEntityPositionSource::getUuid),
			Codec.FLOAT.fieldOf("y_offset").orElse(0.0f).forGetter(entityPositionSource -> entityPositionSource.yOffset))
			.apply(instance, (uUID, float_) -> new HavenEntityPositionSource(Either.right(Either.left(uUID)), float_)));
	private Either<Entity, Either<UUID, Integer>> source;
	final float yOffset;

	public HavenEntityPositionSource(Entity entity, float yOffset) {
		this(Either.left(entity), yOffset);
	}

	HavenEntityPositionSource(Either<Entity, Either<UUID, Integer>> source, float yOffset) {
		this.source = source;
		this.yOffset = yOffset;
	}

	private static int getId(Either<Entity, Integer> source) {
		Optional<Entity> left = source.left();
		if (left.isPresent()) return left.get().getId();
		Optional<Integer> right = source.right();
		if (right.isPresent()) return right.get();
		throw new RuntimeException("Unable to get entityId from source");
	}

	@Override
	public Optional<BlockPos> getPos(World world) {
		if (this.source.left().isEmpty()) this.findEntityInWorld(world);
		return this.source.left().map(entity -> new BlockPos(entity.getPos().add(0.0, this.yOffset, 0.0)));
	}

	private void findEntityInWorld(World world) {
		this.source.map(Optional::of, either -> Optional.ofNullable(either.map(
				uuid -> world instanceof ServerWorld serverWorld ? serverWorld.getEntity((UUID)uuid) : null, world::getEntityById)
		)).ifPresent(entity -> this.source = Either.left(entity));
	}

	private UUID getUuid() {
		return this.source.map(Entity::getUuid, either -> either.map(Function.identity(), integer -> {
			throw new RuntimeException("Unable to get entityId from uuid");
		}));
	}

	int getEntityId() {
		return this.source.map(Entity::getId, either -> either.map(uUID -> {
			throw new IllegalStateException("Unable to get entityId from uuid");
		}, Function.identity()));
	}

	public static final PositionSourceType<HavenEntityPositionSource> HAVEN_ENTITY = PositionSourceType.register("haven_entity", new HavenEntityPositionSource.Type());

	@Override
	public PositionSourceType<?> getType() { return HAVEN_ENTITY; }

	public static class Type implements PositionSourceType<HavenEntityPositionSource> {
		@Override
		public HavenEntityPositionSource readFromBuf(PacketByteBuf packetByteBuf) {
			return new HavenEntityPositionSource(Either.right(Either.right(packetByteBuf.readVarInt())), packetByteBuf.readFloat());
		}

		@Override
		public void writeToBuf(PacketByteBuf packetByteBuf, HavenEntityPositionSource entityPositionSource) {
			packetByteBuf.writeVarInt(entityPositionSource.getEntityId());
			packetByteBuf.writeFloat(entityPositionSource.yOffset);
		}

		@Override
		public Codec<HavenEntityPositionSource> getCodec() { return CODEC; }
	}
}