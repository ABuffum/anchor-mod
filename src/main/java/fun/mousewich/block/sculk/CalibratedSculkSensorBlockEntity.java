package fun.mousewich.block.sculk;

import com.mojang.serialization.Dynamic;
import fun.mousewich.ModBase;
import fun.mousewich.entity.ModNbtKeys;
import fun.mousewich.event.ModVibrationListener;
import fun.mousewich.util.SculkUtil;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.event.BlockPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.listener.GameEventListener;

public class CalibratedSculkSensorBlockEntity extends BlockEntity implements ExtendedSculkEntity {
	private ModVibrationListener listener;
	private final ModVibrationListener.Callback listenerCallback;
	private int lastVibrationFrequency;
	public CalibratedSculkSensorBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(ModBase.CALIBRATED_SCULK_SENSOR_ENTITY, blockPos, blockState);
		this.listenerCallback = new VibrationListenerCallback(this);
		this.listener = new ModVibrationListener(new BlockPositionSource(this.pos), 8, this.listenerCallback);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		this.lastVibrationFrequency = nbt.getInt(ModNbtKeys.LAST_VIBRATION_FREQUENCY);
		if (nbt.contains(ModNbtKeys.LISTENER, NbtElement.COMPOUND_TYPE)) {
			ModVibrationListener.createCodec(this.listenerCallback).parse(new Dynamic<>(NbtOps.INSTANCE, nbt.getCompound(ModNbtKeys.LISTENER))).resultOrPartial(ModBase.LOGGER::error).ifPresent(listener -> {
				this.listener = listener;
			});
		}
	}

	@Override
	public NbtCompound writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		nbt.putInt(ModNbtKeys.LAST_VIBRATION_FREQUENCY, this.lastVibrationFrequency);
		ModVibrationListener.createCodec(this.listenerCallback).encodeStart(NbtOps.INSTANCE, this.listener).resultOrPartial(ModBase.LOGGER::error).ifPresent(listenerNbt -> nbt.put(ModNbtKeys.LISTENER, listenerNbt));
		return nbt;
	}

	public ModVibrationListener getEventListener() { return this.listener; }
	public int getLastVibrationFrequency() { return this.lastVibrationFrequency; }
	public void setLastVibrationFrequency(int lastVibrationFrequency) { this.lastVibrationFrequency = lastVibrationFrequency; }

	public static class VibrationListenerCallback implements ModVibrationListener.Callback {
		protected final CalibratedSculkSensorBlockEntity blockEntity;
		public VibrationListenerCallback(CalibratedSculkSensorBlockEntity sculkSensorBlockEntity) {
			this.blockEntity = sculkSensorBlockEntity;
		}
		@Override
		public boolean triggersAvoidCriterion() { return true; }

		@Override
		public boolean accepts(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, Entity entity, BlockPos pos2) {
			BlockPos blockPos = this.blockEntity.getPos();
			int i = this.method_49832(world, blockPos, this.blockEntity.getCachedState());
			if (i != 0 && SculkUtil.FREQUENCIES.getOrDefault(event, 0) != i) {
				return false;
			}
			if (pos.equals(this.blockEntity.getPos()) && (event == GameEvent.BLOCK_DESTROY || event == GameEvent.BLOCK_PLACE)) {
				return false;
			}
			return SculkUtil.isInactive(this.blockEntity.getCachedState());
		}

		@Override
		public void accept(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, Entity entity, Entity sourceEntity, float distance) {
			BlockState blockState = this.blockEntity.getCachedState();
			BlockPos blockPos = this.blockEntity.getPos();
			if (SculkSensorBlock.isInactive(blockState)) {
				this.blockEntity.setLastVibrationFrequency(SculkUtil.FREQUENCIES.getOrDefault(event, 0));
				int i = VibrationListenerCallback.method_49833(distance, listener.getRange());
				SculkUtil.setActive(entity, world, blockPos, blockState, i, this.blockEntity.getLastVibrationFrequency());
			}
		}
		@Override
		public void onListen() { this.blockEntity.markDirty(); }
		@Override
		public ModVibrationListener getModEventListener() { return null; }
		public static int method_49833(float f, int i) { return Math.max(1, 15 - MathHelper.floor(((double)f / (double)i) * 15.0)); }

		public int method_49805(World world, BlockPos blockPos, Direction direction) {
			return world.getBlockState(blockPos).getStrongRedstonePower(world, blockPos, direction);
		}
		public int method_49806(World world, BlockPos blockPos, Direction direction, boolean bl) {
			BlockState blockState = world.getBlockState(blockPos);
			if (bl) return AbstractRedstoneGateBlock.isRedstoneGate(blockState) ? method_49805(world, blockPos, direction) : 0;
			if (blockState.isOf(Blocks.REDSTONE_BLOCK)) return 15;
			if (blockState.isOf(Blocks.REDSTONE_WIRE)) return blockState.get(RedstoneWireBlock.POWER);
			if (blockState.emitsRedstonePower()) return method_49805(world, blockPos, direction);
			return 0;
		}
		private int method_49832(World world, BlockPos blockPos, BlockState blockState) {
			Direction direction = blockState.get(CalibratedSculkSensorBlock.FACING).getOpposite();
			return method_49806(world, blockPos.offset(direction), direction, false);
		}
	}
}