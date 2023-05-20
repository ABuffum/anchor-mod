package fun.mousewich.block.dust;

import fun.mousewich.block.ModProperties;
import fun.mousewich.entity.ModNbtKeys;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.Objects;

public abstract class BrushableBlockEntity extends BlockEntity implements BrushableEntity {
	protected int brushesCount;
	protected long nextDustTime;
	protected long nextBrushTime;
	protected Direction hitDirection;
	protected ItemStack item = ItemStack.EMPTY;

	public BrushableBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}
	@Override
	public boolean brush(long worldTime, PlayerEntity player, Direction hitDirection) {
		if (this.hitDirection == null) this.hitDirection = hitDirection;
		this.nextDustTime = worldTime + 40L;
		if (worldTime < this.nextBrushTime || !(this.world instanceof ServerWorld)) return false;
		this.nextBrushTime = worldTime + 10L;
		this.generateItem(player);
		int i = this.getDustedLevel();
		if (++this.brushesCount >= 10) {
			this.finishBrushing(player);
			return true;
		}
		this.world.getBlockTickScheduler().schedule(this.getPos(), this.getCachedState().getBlock(), 40);
		int j = this.getDustedLevel();
		if (i != j) {
			BlockState blockState = this.getCachedState();
			BlockState blockState2 = blockState.with(ModProperties.DUSTED, j);
			this.world.setBlockState(this.getPos(), blockState2, Block.NOTIFY_ALL);
		}
		return false;
	}
	protected void finishBrushing(PlayerEntity player) {
		if (this.world == null || this.world.getServer() == null) return;
		this.spawnItem(player);
		BlockState blockState = this.getCachedState();
		this.world.syncWorldEvent(3008, this.getPos(), Block.getRawIdFromState(blockState));
		blockState = blockState.getBlock() instanceof Brushable brushable ? brushable.getBrushedState(blockState) : Blocks.AIR.getDefaultState();
		this.world.setBlockState(this.pos, blockState, Block.NOTIFY_ALL);
	}
	protected void spawnItem(PlayerEntity player) {
		if (this.world == null || this.world.getServer() == null) return;
		this.generateItem(player);
		if (!this.item.isEmpty()) {
			double d = EntityType.ITEM.getWidth();
			double e = 1.0 - d;
			double f = d / 2.0;
			Direction direction = Objects.requireNonNullElse(this.hitDirection, Direction.UP);
			BlockPos blockPos = this.pos.offset(direction, 1);
			double g = (double)blockPos.getX() + 0.5 * e + f;
			double h = (double)blockPos.getY() + 0.5 + (double)(EntityType.ITEM.getHeight() / 2.0f);
			double i = (double)blockPos.getZ() + 0.5 * e + f;
			ItemEntity itemEntity = new ItemEntity(this.world, g, h, i, this.item.split(this.world.random.nextInt(21) + 10));
			itemEntity.setVelocity(Vec3d.ZERO);
			this.world.spawnEntity(itemEntity);
			this.item = ItemStack.EMPTY;
		}
	}
	public abstract void generateItem(PlayerEntity player);

	public void scheduledTick() {
		if (this.world == null) return;
		if (this.brushesCount != 0 && this.world.getTime() >= this.nextDustTime) {
			int i = this.getDustedLevel();
			this.brushesCount = Math.max(0, this.brushesCount - 2);
			int j = this.getDustedLevel();
			if (i != j) this.world.setBlockState(this.getPos(), this.getCachedState().with(ModProperties.DUSTED, j), Block.NOTIFY_ALL);
			this.nextDustTime = this.world.getTime() + 4L;
		}
		if (this.brushesCount == 0) {
			this.hitDirection = null;
			this.nextDustTime = 0L;
			this.nextBrushTime = 0L;
		}
		else this.world.getBlockTickScheduler().schedule(this.getPos(), this.getCachedState().getBlock(), (int)(this.nextDustTime - this.world.getTime()));
	}
	@Override
	public NbtCompound toInitialChunkDataNbt() {
		NbtCompound nbtCompound = super.toInitialChunkDataNbt();
		if (this.hitDirection != null) nbtCompound.putInt(ModNbtKeys.HIT_DIRECTION, this.hitDirection.ordinal());
		nbtCompound.put(ModNbtKeys.ITEM, this.item.writeNbt(new NbtCompound()));
		return nbtCompound;
	}
	public BlockEntityUpdateS2CPacket toUpdatePacket() { return new BlockEntityUpdateS2CPacket(this.pos, BlockEntityUpdateS2CPacket.STRUCTURE, this.toInitialChunkDataNbt()); }
	@Override
	public void readNbt(NbtCompound nbt) {
		if (nbt.contains(ModNbtKeys.HIT_DIRECTION)) this.hitDirection = Direction.values()[nbt.getInt(ModNbtKeys.HIT_DIRECTION)];
	}

	@Override
	public int getDustedLevel() {
		if (this.brushesCount == 0) return 0;
		if (this.brushesCount < 3) return 1;
		if (this.brushesCount < 6) return 2;
		return 3;
	}
	@Override
	public Direction getHitDirection() { return this.hitDirection; }
	@Override
	public ItemStack getItem() { return this.item; }
}
