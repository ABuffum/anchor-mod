package fun.mousewich.block.sculk;

import com.mojang.serialization.Dynamic;
import fun.mousewich.ModBase;
import fun.mousewich.ModGameRules;
import fun.mousewich.ModId;
import fun.mousewich.entity.ModNbtKeys;
import fun.mousewich.entity.hostile.warden.WardenEntity;
import fun.mousewich.event.ModGameEvent;
import fun.mousewich.event.ModVibrationListener;
import fun.mousewich.event.ModWorldEvents;
import fun.mousewich.gen.data.tag.ModGameEventTags;
import fun.mousewich.sound.ModSoundEvents;
import fun.mousewich.util.CollectionUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.event.BlockPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.listener.GameEventListener;

import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

public class SculkShriekerBlockEntity extends BlockEntity implements ModVibrationListener.Callback {
	private static final Map<Integer, SoundEvent> WARNING_SOUNDS = Map.ofEntries(
		Map.entry(1, ModSoundEvents.ENTITY_WARDEN_NEARBY_CLOSE),
		Map.entry(2, ModSoundEvents.ENTITY_WARDEN_NEARBY_CLOSER),
		Map.entry(3, ModSoundEvents.ENTITY_WARDEN_NEARBY_CLOSEST),
		Map.entry(4, ModSoundEvents.ENTITY_WARDEN_LISTENING_ANGRY)
	);
	private int warningLevel;
	private ModVibrationListener vibrationListener;

	public SculkShriekerBlockEntity(BlockPos pos, BlockState state) {
		super(ModBase.SCULK_SHRIEKER_ENTITY, pos, state);
		this.vibrationListener = new ModVibrationListener(new BlockPositionSource(this.pos), 8, this, null, 0.0f, 0);
	}

	public ModVibrationListener getVibrationListener() { return this.vibrationListener; }
	public ModVibrationListener getModEventListener() { return this.vibrationListener; }

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		if (nbt.contains(ModNbtKeys.WARNING_LEVEL, NbtElement.NUMBER_TYPE)) this.warningLevel = nbt.getInt(ModNbtKeys.WARNING_LEVEL);
		if (nbt.contains(ModNbtKeys.LISTENER, NbtElement.COMPOUND_TYPE)) {
			ModVibrationListener.createCodec(this).parse(new Dynamic<>(NbtOps.INSTANCE, nbt.getCompound(ModNbtKeys.LISTENER))).resultOrPartial(ModId.LOGGER::error).ifPresent(vibrationListener -> this.vibrationListener = vibrationListener);
		}
	}

	@Override
	public NbtCompound writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		nbt.putInt(ModNbtKeys.WARNING_LEVEL, this.warningLevel);
		ModVibrationListener.createCodec(this).encodeStart(NbtOps.INSTANCE, this.vibrationListener).resultOrPartial(ModId.LOGGER::error).ifPresent(nbtElement -> nbt.put(ModNbtKeys.LISTENER, nbtElement));
		return nbt;
	}

	public Tag.Identified<GameEvent> getTag() { return ModGameEventTags.SHRIEKER_CAN_LISTEN; }

	@Override
	public boolean accepts(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, Entity entity, BlockPos pos2) {
		return !this.isRemoved() && !this.getCachedState().get(SculkShriekerBlock.SHRIEKING) && SculkShriekerBlockEntity.findResponsiblePlayerFromEntity(entity) != null;
	}

	public static ServerPlayerEntity findResponsiblePlayerFromEntity(Entity entity) {
		if (entity instanceof ServerPlayerEntity serverPlayerEntity1) return serverPlayerEntity1;
		if (entity != null && entity.getPrimaryPassenger() instanceof ServerPlayerEntity serverPlayerEntity2) return serverPlayerEntity2;
		if (entity instanceof ProjectileEntity projectileEntity && projectileEntity.getOwner() instanceof ServerPlayerEntity serverPlayerEntity3) return serverPlayerEntity3;
		if (entity instanceof ItemEntity itemEntity && CollectionUtil.map(itemEntity.getThrower(), itemEntity.world::getPlayerByUuid) instanceof ServerPlayerEntity serverPlayerEntity4) return serverPlayerEntity4;
		return null;
	}

	@Override
	public void accept(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, Entity entity, Entity sourceEntity, float distance) {
		this.shriek(world, SculkShriekerBlockEntity.findResponsiblePlayerFromEntity(sourceEntity != null ? sourceEntity : entity));
	}

	public void shriek(ServerWorld world, ServerPlayerEntity player) {
		if (player == null) return;
		BlockState blockState = this.getCachedState();
		if (blockState.get(SculkShriekerBlock.SHRIEKING)) return;
		this.warningLevel = 0;
		if (this.canWarn(world) && !this.trySyncWarningLevel(world, player)) return;
		this.shriek(world, (Entity)player);
	}

	private boolean trySyncWarningLevel(ServerWorld world, ServerPlayerEntity player) {
		OptionalInt optionalInt = SculkShriekerWarningManager.warnNearbyPlayers(world, this.getPos(), player);
		optionalInt.ifPresent(warningLevel -> this.warningLevel = warningLevel);
		return optionalInt.isPresent();
	}

	private void shriek(ServerWorld world, Entity entity) {
		BlockPos blockPos = this.getPos();
		BlockState blockState = this.getCachedState();
		world.setBlockState(blockPos, blockState.with(SculkShriekerBlock.SHRIEKING, true), Block.NOTIFY_LISTENERS);
		world.getBlockTickScheduler().schedule(blockPos, blockState.getBlock(), 90);
		world.syncWorldEvent(ModWorldEvents.SCULK_SHRIEKS, blockPos, 0);
		world.emitGameEvent(entity, ModGameEvent.SHRIEK, blockPos);
	}

	private boolean canWarn(ServerWorld world) {
		return this.getCachedState().get(SculkShriekerBlock.CAN_SUMMON) && world.getDifficulty() != Difficulty.PEACEFUL && world.getGameRules().getBoolean(ModGameRules.DO_WARDEN_SPAWNING);
	}

	public void warn(ServerWorld world) {
		if (this.canWarn(world) && this.warningLevel > 0) {
			if (!this.trySpawnWarden(world)) this.playWarningSound();
			WardenEntity.addDarknessToClosePlayers(world, Vec3d.ofCenter(this.getPos()), null, 40);
		}
	}

	private void playWarningSound() {
		SoundEvent soundEvent = WARNING_SOUNDS.get(this.warningLevel);
		if (soundEvent != null && this.world != null) {
			BlockPos blockPos = this.getPos();
			int i = blockPos.getX() + MathHelper.nextBetween(this.world.random, -10, 10);
			int j = blockPos.getY() + MathHelper.nextBetween(this.world.random, -10, 10);
			int k = blockPos.getZ() + MathHelper.nextBetween(this.world.random, -10, 10);
			this.world.playSound(null, i, j, k, soundEvent, SoundCategory.HOSTILE, 5.0f, 1.0f);
		}
	}

	public void onListen() { this.markDirty(); }

	private boolean trySpawnWarden(ServerWorld world) {
		if (this.warningLevel < 4) return false;
		return trySpawnWardenAt(world, this.getPos()).isPresent();
	}
	public static Optional<WardenEntity> trySpawnWardenAt(ServerWorld world, BlockPos pos) {
		BlockPos.Mutable mutable = pos.mutableCopy();
		for (int i = 0; i < 20; ++i) {
			WardenEntity warden;
			int j = MathHelper.nextBetween(world.random, -5, 5);
			int k = MathHelper.nextBetween(world.random, -5, 5);
			mutable.set(pos, j, 6, k);
			if (!world.getWorldBorder().contains(mutable)) continue;
			if (!findWardenSpawnPos(world, mutable)) continue;
			warden = ModBase.WARDEN_ENTITY.create(world, null, null, null, mutable, SpawnReason.TRIGGERED, false, false);
			if (warden == null) continue;
			if (warden.canSpawn(world, SpawnReason.TRIGGERED) && warden.canSpawn(world)) {
				world.spawnEntityAndPassengers(warden);
				return Optional.of(warden);
			}
			warden.discard();
		}
		return Optional.empty();
	}

	private static boolean findWardenSpawnPos(ServerWorld world, BlockPos.Mutable pos) {
		BlockPos.Mutable mutable = new BlockPos.Mutable().set(pos);
		BlockState blockState = world.getBlockState(mutable);
		for (int i = 6; i >= -6; --i) {
			pos.move(Direction.DOWN);
			mutable.set(pos, Direction.UP);
			BlockState blockState2 = world.getBlockState(pos);
			if (blockState.getCollisionShape(world, pos).isEmpty() && Block.isFaceFullSquare(blockState2.getCollisionShape(world, pos), Direction.UP)) {
				pos.move(Direction.UP);
				return true;
			}
			blockState = blockState2;
		}
		return false;
	}
}