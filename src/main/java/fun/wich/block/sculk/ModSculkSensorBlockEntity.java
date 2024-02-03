package fun.wich.block.sculk;

import fun.wich.event.ModVibrationListener;
import fun.wich.ModBase;
import fun.wich.util.SculkUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.SculkSensorBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.event.BlockPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.listener.GameEventListener;

public class ModSculkSensorBlockEntity extends BlockEntity implements ModVibrationListener.Callback {
	private final ModVibrationListener listener;
	private int lastVibrationFrequency;

	public ModSculkSensorBlockEntity(BlockPos pos, BlockState state) {
		super(ModBase.SCULK_SENSOR_ENTITY, pos, state);
		this.listener = new ModVibrationListener(new BlockPositionSource(this.pos), ((SculkSensorBlock)state.getBlock()).getRange(), this, null, 0.0f, 0);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		this.lastVibrationFrequency = nbt.getInt("last_vibration_frequency");
	}

	@Override
	public NbtCompound writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		nbt.putInt("last_vibration_frequency", this.lastVibrationFrequency);
		return nbt;
	}

	public ModVibrationListener getEventListener() { return this.listener; }

	public int getLastVibrationFrequency() { return this.lastVibrationFrequency; }

	@Override
	public boolean accepts(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, Entity entity, BlockPos pos2) {
		if (this.isRemoved() || pos.equals(this.getPos()) && (event == GameEvent.BLOCK_DESTROY || event == GameEvent.BLOCK_PLACE)) return false;
		return SculkUtil.isInactive(this.getCachedState());
	}

	@Override
	public void accept(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, Entity entity, Entity sourceEntity, float distance) {
		BlockState blockState = this.getCachedState();
		if (SculkUtil.isInactive(blockState)) {
			this.lastVibrationFrequency = SculkUtil.FREQUENCIES.getInt(event);
			SculkUtil.setActive(entity, world, this.pos, blockState, SculkUtil.getPower(distance, listener.getRange()));
		}
	}

	public void onListen() { this.markDirty(); }

	@Override
	public ModVibrationListener getModEventListener() { return listener; }

	public static int getPower(float distance, int range) {
		return Math.max(1, 15 - MathHelper.floor(15.0 * distance / range));
	}

	public void setLastVibrationFrequency(int lastVibrationFrequency) {
		this.lastVibrationFrequency = lastVibrationFrequency;
	}
}
