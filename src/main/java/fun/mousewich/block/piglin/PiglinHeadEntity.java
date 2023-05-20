package fun.mousewich.block.piglin;

import fun.mousewich.ModBase;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PiglinHeadEntity extends BlockEntity {
	private int poweredTicks;
	private boolean powered;
	private boolean zombified;
	public boolean isZombified() { return this.zombified; }
	public void setZombified(boolean zombified) { this.zombified = zombified; }
	public PiglinHeadEntity(BlockPos pos, BlockState state) { super(ModBase.PIGLIN_HEAD_BLOCK_ENTITY, pos, state); }
	public static void tick(World world, BlockPos pos, BlockState state, PiglinHeadEntity blockEntity) {
		if (world.isReceivingRedstonePower(pos)) {
			blockEntity.powered = true;
			++blockEntity.poweredTicks;
		}
		else blockEntity.powered = false;
	}
	public float getPoweredTicks(float tickDelta) {
		if (this.powered) return this.poweredTicks + tickDelta;
		return this.poweredTicks;
	}
}
