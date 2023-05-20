package fun.mousewich.entity.hostile.spider;

import fun.mousewich.sound.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.JumpControl;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

public class JumpingSpiderEntity extends SpiderEntity {
	private int jumpTicks;
	private int jumpDuration;
	private boolean lastOnGround;
	private int ticksUntilJump;
	public JumpingSpiderEntity(EntityType<? extends JumpingSpiderEntity> entityType, World world) {
		super(entityType, world);
		this.jumpControl = new SpiderJumpControl(this);
		this.moveControl = new SpiderMoveControl(this);
		this.setSpeed(0.0);
	}
	public static DefaultAttributeContainer.Builder createJumpingSpiderAttributes() {
		return SpiderEntity.createSpiderAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0);
	}
	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt) { return entityData; }
	@Override
	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) { return 0.45f; }

	@Override
	protected float getJumpVelocity() {
		if (this.horizontalCollision || this.moveControl.isMoving() && this.moveControl.getTargetY() > this.getY() + 0.5) return 0.5f;
		Path path = this.navigation.getCurrentPath();
		if (path != null && !path.isFinished()) {
			Vec3d vec3d = path.getNodePosition(this);
			if (vec3d.y > this.getY() + 0.5) return 0.5f;
		}
		if (this.moveControl.getSpeed() <= 0.6) return 0.2f;
		return 0.3f;
	}
	@Override
	protected void jump() {
		super.jump();
		double d = this.moveControl.getSpeed();
		if (d > 0.0 && this.getVelocity().horizontalLengthSquared() < 0.01) {
			this.updateVelocity(0.1f, new Vec3d(0.0, 0.0, 1.0));
		}
		if (!this.world.isClient) this.world.sendEntityStatus(this, (byte)1);
	}

	public float getJumpProgress(float delta) {
		if (this.jumpDuration == 0) return 0.0f;
		return ((float)this.jumpTicks + delta) / (float)this.jumpDuration;
	}
	public void setSpeed(double speed) {
		this.getNavigation().setSpeed(speed);
		this.moveControl.moveTo(this.moveControl.getTargetX(), this.moveControl.getTargetY(), this.moveControl.getTargetZ(), speed);
	}
	@Override
	public void setJumping(boolean jumping) {
		super.setJumping(jumping);
		if (jumping) {
			this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f) * 0.8f);
		}
	}
	public void startJump() {
		this.setJumping(true);
		this.jumpDuration = 10;
		this.jumpTicks = 0;
	}
	@Override
	public void mobTick() {
		if (this.ticksUntilJump > 0) --this.ticksUntilJump;
		if (this.onGround) {
			if (!this.lastOnGround) {
				this.setJumping(false);
				this.scheduleJump();
			}
			SpiderJumpControl spiderJumpControl = (SpiderJumpControl)this.jumpControl;
			if (!spiderJumpControl.isActive()) {
				if (this.moveControl.isMoving() && this.ticksUntilJump == 0) {
					Path path = this.navigation.getCurrentPath();
					Vec3d vec3d = new Vec3d(this.moveControl.getTargetX(), this.moveControl.getTargetY(), this.moveControl.getTargetZ());
					if (path != null && !path.isFinished()) vec3d = path.getNodePosition(this);
					this.lookTowards(vec3d.x, vec3d.z);
					this.startJump();
				}
			}
			else if (!spiderJumpControl.canJump()) this.enableJump();
		}
		this.lastOnGround = this.onGround;
	}
	private void lookTowards(double x, double z) { this.setYaw((float)(MathHelper.atan2(z - this.getZ(), x - this.getX()) * 57.2957763671875) - 90.0f); }
	private void enableJump() { ((SpiderJumpControl)this.jumpControl).setCanJump(true); }
	private void disableJump() { ((SpiderJumpControl)this.jumpControl).setCanJump(false); }
	private void doScheduleJump() { this.ticksUntilJump = this.moveControl.getSpeed() < 2.2 ? 10 : 1; }
	private void scheduleJump() {
		this.doScheduleJump();
		this.disableJump();
	}
	@Override
	public void tickMovement() {
		super.tickMovement();
		if (this.jumpTicks != this.jumpDuration) ++this.jumpTicks;
		else if (this.jumpDuration != 0) {
			this.jumpTicks = 0;
			this.jumpDuration = 0;
			this.setJumping(false);
		}
	}
	@Override
	protected SoundEvent getAmbientSound() { return ModSoundEvents.ENTITY_JUMPING_SPIDER_AMBIENT; }
	@Override
	protected SoundEvent getHurtSound(DamageSource source) { return ModSoundEvents.ENTITY_JUMPING_SPIDER_HURT; }
	@Override
	protected SoundEvent getDeathSound() { return ModSoundEvents.ENTITY_JUMPING_SPIDER_DEATH; }
	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(ModSoundEvents.ENTITY_JUMPING_SPIDER_STEP, 0.15f, 1.0f);
	}
	protected SoundEvent getJumpSound() { return ModSoundEvents.ENTITY_JUMPING_SPIDER_JUMP; }
	@Override
	public void handleStatus(byte status) {
		if (status == 1) {
			this.spawnSprintingParticles();
			this.jumpDuration = 10;
			this.jumpTicks = 0;
		} else {
			super.handleStatus(status);
		}
	}

	public static class SpiderJumpControl extends JumpControl {
		private final JumpingSpiderEntity spider;
		private boolean canJump;
		public SpiderJumpControl(JumpingSpiderEntity spider) {
			super(spider);
			this.spider = spider;
		}
		public boolean isActive() { return this.active; }
		public boolean canJump() { return this.canJump; }
		public void setCanJump(boolean canJump) { this.canJump = canJump; }
		@Override
		public void tick() {
			if (this.active) {
				this.spider.startJump();
				this.active = false;
			}
		}
	}
	static class SpiderMoveControl extends MoveControl {
		private final JumpingSpiderEntity spider;
		private double spiderSpeed;
		public SpiderMoveControl(JumpingSpiderEntity owner) {
			super(owner);
			this.spider = owner;
		}
		@Override
		public void tick() {
			if (this.spider.onGround && !this.spider.jumping && !((SpiderJumpControl)this.spider.jumpControl).isActive()) this.spider.setSpeed(0.0);
			else if (this.isMoving()) this.spider.setSpeed(this.spiderSpeed);
			super.tick();
		}
		@Override
		public void moveTo(double x, double y, double z, double speed) {
			if (this.spider.isTouchingWater()) speed = 1.5;
			super.moveTo(x, y, z, speed);
			if (speed > 0.0) this.spiderSpeed = speed;
		}
	}
}
