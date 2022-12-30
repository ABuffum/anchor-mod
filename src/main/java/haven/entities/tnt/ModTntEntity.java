package haven.entities.tnt;

import haven.blocks.tnt.ModTntBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class ModTntEntity extends Entity {
	protected static final TrackedData<Integer> FUSE;
	protected static final int DEFAULT_FUSE = 80;
	@Nullable
	protected LivingEntity causingEntity;

	protected ModTntEntity(EntityType<? extends Entity> entityType, World world) {
		super(entityType, world);
		this.inanimate = true;
	}

	protected ModTntEntity(EntityType<? extends Entity> entityType, World world, double x, double y, double z, @Nullable LivingEntity igniter) {
		this(entityType, world);
		this.setPosition(x, y, z);
		double d = world.random.nextDouble() * 6.2831854820251465D;
		this.setVelocity(-Math.sin(d) * 0.02D, 0.20000000298023224D, -Math.cos(d) * 0.02D);
		this.setFuse(DEFAULT_FUSE);
		this.prevX = x;
		this.prevY = y;
		this.prevZ = z;
		this.causingEntity = igniter;
	}

	protected void initDataTracker() { this.dataTracker.startTracking(FUSE, DEFAULT_FUSE); }
	protected MoveEffect getMoveEffect() { return MoveEffect.NONE; }
	public boolean collides() { return !this.isRemoved(); }

	public void tick() {
		if (!this.hasNoGravity()) this.setVelocity(this.getVelocity().add(0.0D, -0.04D, 0.0D));
		this.move(MovementType.SELF, this.getVelocity());
		this.setVelocity(this.getVelocity().multiply(0.98D));
		if (this.onGround) this.setVelocity(this.getVelocity().multiply(0.7D, -0.5D, 0.7D));
		int i = this.getFuse() - 1;
		this.setFuse(i);
		if (i <= 0) {
			this.discard();
			if (!this.world.isClient) this.explode();
		}
		else {
			this.updateWaterState();
			if (this.world.isClient) {
				this.world.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5D, this.getZ(), 0.0D, 0.0D, 0.0D);
			}
		}
	}

	protected abstract void explode();
	public abstract boolean destroyBlocks();
	public abstract boolean doDamage();
	public abstract boolean doKnockback();
	public boolean makeSound() { return true; }

	protected void propagate(BlockPos pos) {
		Block block = world.getBlockState(pos).getBlock();
		if (block instanceof ModTntBlock modTntBlock) {
			modTntBlock.primeTnt(world, pos);
			world.removeBlock(pos, false);
		}
	}
	protected void propagateExplosion(int radius) {
		int X = (int)this.getX(), Y = (int)this.getY(), Z = (int)this.getZ();
		for (int z = Z - radius; z <= Z + radius; z++) {
			for (int y = Y - radius; y <= Y + radius; y++) {
				for (int x = X - radius; x <= X + radius; x++) {
					propagate(new BlockPos(x, y, z));
				}
			}
		}
	}

	protected void writeCustomDataToNbt(NbtCompound nbt) { nbt.putShort("Fuse", (short)this.getFuse()); }
	protected void readCustomDataFromNbt(NbtCompound nbt) { this.setFuse(nbt.getShort("Fuse")); }
	@Nullable
	public LivingEntity getCausingEntity() { return this.causingEntity; }
	protected float getEyeHeight(EntityPose pose, EntityDimensions dimensions) { return 0.15F; }
	public void setFuse(int fuse) { this.dataTracker.set(FUSE, fuse); }
	public int getFuse() { return this.dataTracker.get(FUSE); }
	public Packet<?> createSpawnPacket() { return new EntitySpawnS2CPacket(this); }

	static {
		FUSE = DataTracker.registerData(ModTntEntity.class, TrackedDataHandlerRegistry.INTEGER);
	}
}
