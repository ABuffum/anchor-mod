package fun.wich.block;

import fun.wich.entity.vehicle.DispenserMinecartEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;

public class DispenserMinecartPointer implements BlockPointer {
	private final ServerWorld world;
	protected final DispenserMinecartEntity entity;
	public DispenserMinecartPointer(ServerWorld world, DispenserMinecartEntity entity) {
		this.world = world;
		this.entity = entity;
	}
	@Override
	public ServerWorld getWorld() { return this.world; }
	@Override
	public double getX() { return this.entity.getX() + 0.5; }
	@Override
	public double getY() { return this.entity.getY() + 0.5; }
	@Override
	public double getZ() { return this.entity.getZ() + 0.5; }
	@Override
	public BlockPos getPos() { return this.entity.getBlockPos(); }
	@Override
	public BlockState getBlockState() { return this.entity.getDefaultContainedBlock(); }
	@Override
	public <T extends BlockEntity> T getBlockEntity() { return null; }
	public DispenserMinecartEntity getEntity() { return this.entity; }
}
