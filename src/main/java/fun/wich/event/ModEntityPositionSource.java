package fun.wich.event;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.dynamic.DynamicSerializableUuid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.PositionSource;
import net.minecraft.world.event.PositionSourceType;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public class ModEntityPositionSource implements PositionSource {
	public static final Codec<ModEntityPositionSource> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			DynamicSerializableUuid.CODEC.fieldOf("source_entity").forGetter(ModEntityPositionSource::getUuid),
			Codec.FLOAT.fieldOf("y_offset").orElse(0.0f).forGetter(entityPositionSource -> entityPositionSource.yOffset))
			.apply(instance, (uUID, float_) -> new ModEntityPositionSource(Either.right(Either.left(uUID)), float_)));
	private Either<Entity, Either<UUID, Integer>> source;
	final float yOffset;
	public ModEntityPositionSource(Entity entity, float yOffset) { this(Either.left(entity), yOffset); }
	ModEntityPositionSource(Either<Entity, Either<UUID, Integer>> source, float yOffset) {
		this.source = source;
		this.yOffset = yOffset;
	}
	@Override
	public Optional<BlockPos> getPos(World world) { return GetPos(world).map(BlockPos::new); }
	public Optional<Vec3d> GetPos(World world) {
		if (this.source.left().isEmpty()) this.findEntityInWorld(world);
		return this.source.left().map(entity -> entity.getPos().add(0.0, this.yOffset, 0.0));
	}
	private void findEntityInWorld(World world) {
		this.source.map(Optional::of, either -> Optional.ofNullable(either.map(uuid -> {
			if (world instanceof ServerWorld serverWorld) return serverWorld.getEntity(uuid);
			else return null;
		}, world::getEntityById))).ifPresent(entity -> this.source = Either.left(entity));
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

	@Override
	public PositionSourceType<?> getType() { return ModPositionSourceTypes.MOD_ENTITY; }
	public static class Type implements PositionSourceType<ModEntityPositionSource> {
		@Override
		public ModEntityPositionSource readFromBuf(PacketByteBuf packetByteBuf) {
			return new ModEntityPositionSource(Either.right(Either.right(packetByteBuf.readVarInt())), packetByteBuf.readFloat());
		}
		@Override
		public void writeToBuf(PacketByteBuf packetByteBuf, ModEntityPositionSource entityPositionSource) {
			packetByteBuf.writeVarInt(entityPositionSource.getEntityId());
			packetByteBuf.writeFloat(entityPositionSource.yOffset);
		}
		@Override
		public Codec<ModEntityPositionSource> getCodec() { return CODEC; }
	}
}
