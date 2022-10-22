package haven.blocks.sculk;

import haven.HavenMod;
import haven.events.VibrationListener;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.event.BlockPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.listener.GameEventListener;
public class HavenSculkSensorBlockEntity extends BlockEntity implements VibrationListener.Callback {
	private VibrationListener listener;
	private int lastVibrationFrequency;

	public HavenSculkSensorBlockEntity(BlockPos pos, BlockState state) {
		super(HavenMod.SCULK_SENSOR_ENTITY, pos, state);
		this.listener = new VibrationListener(new BlockPositionSource(this.pos), ((HavenSculkSensorBlock)state.getBlock()).getRange(), this, null, 0.0f, 0);
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

	public VibrationListener getEventListener() { return this.listener; }

	public int getLastVibrationFrequency() { return this.lastVibrationFrequency; }

	@Override
	public boolean accepts(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, Entity entity, BlockPos pos2) {
		if (this.isRemoved() || pos.equals(this.getPos()) && (event == GameEvent.BLOCK_DESTROY || event == GameEvent.BLOCK_PLACE)) return false;
		return HavenSculkSensorBlock.isInactive(this.getCachedState());
	}

	@Override
	public void accept(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, Entity entity, Entity sourceEntity, float distance) {
		BlockState blockState = this.getCachedState();
		if (HavenSculkSensorBlock.isInactive(blockState)) {
			this.lastVibrationFrequency = HavenSculkSensorBlock.FREQUENCIES.getInt(event);
			HavenSculkSensorBlock.setActive(entity, world, this.pos, blockState, getPower(distance, listener.getRange()));
		}
	}

	public void onListen() { this.markDirty(); }

	public static int getPower(float distance, int range) {
		double d = (double)distance / (double)range;
		return Math.max(1, 15 - MathHelper.floor(d * 15.0));
	}

	public void setLastVibrationFrequency(int lastVibrationFrequency) {
		this.lastVibrationFrequency = lastVibrationFrequency;
	}
}
