package fun.mousewich.entity;

import fun.mousewich.ModBase;
import fun.mousewich.damage.ModDamageSource;
import fun.mousewich.sound.ModSoundEvents;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class IceChunkEntity extends Entity {
	public int timeFalling;
	protected LivingEntity owner;

	public IceChunkEntity(EntityType<?> type, World world) {
		super(type, world);
	}
	public IceChunkEntity(World world, double x, double y, double z) {
		super(ModBase.ICE_CHUNK_ENTITY, world);
		this.inanimate = true;
		this.setPosition(x, y, z);
		this.setVelocity(Vec3d.ZERO);
		this.prevX = x;
		this.prevY = y;
		this.prevZ = z;
	}

	public static boolean isOpen(World world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		return state.isAir() || state.getFluidState().getFluid() == Fluids.WATER;
	}

	public static boolean canSpawn(World world, Vec3d pos) {
		BlockPos.Mutable mutable = new BlockPos.Mutable(pos.getX(), pos.getY(), pos.getZ());
		double X = pos.getX(), Y = pos.getY(), Z = pos.getZ();
		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				for (int y = 0; y <= 1; y++) {
					mutable.set(X + (0.51 * x), y + (0.51 * y), Z + (0.51 * z));
					if (!isOpen(world, mutable)) return false;
				}
			}
		}
		return true;
	}

	public LivingEntity getOwner() { return this.owner; }
	public void setOwner(LivingEntity owner) { this.owner = owner; }
	@Override
	public boolean isAttackable() { return false; }
	@Override
	protected Entity.MoveEffect getMoveEffect() { return Entity.MoveEffect.NONE; }
	@Override
	protected void initDataTracker() { }
	@Override
	public boolean collides() { return !this.isRemoved(); }

	@Override
	public void tick() {
		++this.timeFalling;
		if (!this.hasNoGravity()) {
			this.setVelocity(this.getVelocity().add(0.0, -0.04, 0.0));
		}
		this.move(MovementType.SELF, this.getVelocity());
		if (!this.world.isClient) {
			BlockPos blockPos = this.getBlockPos();
			if (this.onGround) {
				BlockState blockState = this.world.getBlockState(blockPos);
				this.setVelocity(this.getVelocity().multiply(0.7, -0.5, 0.7));
				if (!blockState.isOf(Blocks.MOVING_PISTON)) {
					this.discard();
				}
			}
			else if (!(this.world.isClient || (this.timeFalling <= 100 || blockPos.getY() > this.world.getBottomY() && blockPos.getY() <= this.world.getTopY()) && this.timeFalling <= 600)) {
				this.discard();
			}
		}
		this.setVelocity(this.getVelocity().multiply(0.98));
	}

	@Override
	public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
		Predicate<Entity> predicate;
		int i = MathHelper.ceil(fallDistance - 1.0f);
		if (i < 0) return false;
		predicate = entity -> !entity.isSpectator() && entity != this.owner;
		this.playSound(this.getFallSound(i), 1.0f, 1.0f);
		float f = Math.min(MathHelper.floor((float)i * 4), 40);
		this.world.getOtherEntities(this, this.getBoundingBox(), predicate).forEach(entity -> entity.damage(ModDamageSource.ICE_CHUNK, f));
		return false;
	}
	private SoundEvent getFallSound(int distance) {
		return distance > 6 ? ModSoundEvents.ENTITY_ICEOLOGER_ICE_FALL_BIG : ModSoundEvents.ENTITY_ICEOLOGER_ICE_FALL_SMALL;
	}

	@Override
	protected void writeCustomDataToNbt(NbtCompound nbt) { nbt.putInt(ModNbtKeys.TIME, this.timeFalling); }
	@Override
	protected void readCustomDataFromNbt(NbtCompound nbt) { this.timeFalling = nbt.getInt(ModNbtKeys.TIME); }
	@Override
	public boolean doesRenderOnFire() { return false; }
	@Override
	public boolean entityDataRequiresOperator() { return true; }
	@Override
	public Packet<?> createSpawnPacket() { return new EntitySpawnS2CPacket(this, 0); }

	@Override
	public void onSpawnPacket(EntitySpawnS2CPacket packet) {
		super.onSpawnPacket(packet);
		this.inanimate = true;
		double d = packet.getX();
		double e = packet.getY();
		double f = packet.getZ();
		this.setPosition(d, e, f);
	}
}
