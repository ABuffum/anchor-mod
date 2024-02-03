package fun.wich.mixins.block.entity;

import fun.wich.block.JukeboxBlockExtension;
import fun.wich.entity.HopperTransferable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Clearable;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(JukeboxBlockEntity.class)
public abstract class JukeboxBlockEntityMixin extends BlockEntity implements Clearable, JukeboxBlockExtension, HopperTransferable {
	private int ticksThisSecond;
	private long tickCount;
	private long recordStartTick;
	private boolean playing;

	public JukeboxBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) { super(type, pos, state); }

	@Inject(method="readNbt", at=@At("TAIL"))
	public void ReadNbt(NbtCompound nbt, CallbackInfo ci) {
		this.playing = nbt.getBoolean("IsPlaying");
		this.recordStartTick = nbt.getLong("RecordStartTick");
		this.tickCount = nbt.getLong("TickCount");
	}

	@Inject(method="writeNbt", at=@At("TAIL"))
	protected void WriteNbt(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
		nbt.putBoolean("IsPlaying", this.playing);
		nbt.putLong("RecordStartTick", this.recordStartTick);
		nbt.putLong("TickCount", this.tickCount);
	}

	@Override
	public void startPlaying() {
		this.recordStartTick = this.tickCount;
		this.playing = true;
	}
	@Override
	public boolean isPlaying() { return this.playing; }
	@Override
	public void setPlaying(boolean value) { this.playing = value; }
	@Override
	public long getRecordStartTick() { return this.recordStartTick; }
	@Override
	public int getTicksThisSecond() { return this.ticksThisSecond; }
	@Override
	public void setTicksThisSecond(int value) { this.ticksThisSecond = value; }
	@Override
	public long getTickCount() { return this.tickCount; }
	@Override
	public void setTickCount(long value) { this.tickCount = value; }
	@Override
	public boolean canTransferTo(Inventory hopperInventory, int slot, ItemStack stack) {
		for (int i = 0; i < hopperInventory.size(); ++i) {
			ItemStack itemStack = hopperInventory.getStack(i);
			if (!itemStack.isEmpty()) continue;
			return true;
		}
		return false;
	}
}
