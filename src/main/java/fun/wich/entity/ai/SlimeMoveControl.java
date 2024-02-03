package fun.wich.entity.ai;

import fun.wich.entity.hostile.slime.ModSlimeEntity;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.attribute.EntityAttributes;

public class SlimeMoveControl extends MoveControl {
	private float targetYaw;
	private int ticksUntilJump;
	private final ModSlimeEntity slime;
	private boolean jumpOften;

	public SlimeMoveControl(ModSlimeEntity slime) {
		super(slime);
		this.slime = slime;
		this.targetYaw = 180.0f * slime.getYaw() / (float)Math.PI;
	}

	public void look(float targetYaw, boolean jumpOften) {
		this.targetYaw = targetYaw;
		this.jumpOften = jumpOften;
	}

	public void move(double speed) {
		this.speed = speed;
		this.state = State.MOVE_TO;
	}

	@Override
	public void tick() {
		this.entity.setYaw(this.wrapDegrees(this.entity.getYaw(), this.targetYaw, 90.0f));
		this.entity.headYaw = this.entity.getYaw();
		this.entity.bodyYaw = this.entity.getYaw();
		if (this.state != State.MOVE_TO) {
			this.entity.setForwardSpeed(0.0f);
			return;
		}
		this.state = State.WAIT;
		if (this.entity.isOnGround()) {
			this.entity.setMovementSpeed((float)(this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
			if (this.ticksUntilJump-- <= 0) {
				this.ticksUntilJump = this.slime.getTicksUntilNextJump();
				if (this.jumpOften) this.ticksUntilJump /= 3;
				this.slime.getJumpControl().setActive();
				if (this.slime.makesJumpSound()) {
					this.slime.playSound(this.slime.getJumpSound(), this.slime.getSoundVolume(), this.slime.getJumpSoundPitch());
				}
			}
			else {
				this.slime.sidewaysSpeed = 0.0f;
				this.slime.forwardSpeed = 0.0f;
				this.entity.setMovementSpeed(0.0f);
			}
		}
		else {
			this.entity.setMovementSpeed((float)(this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
		}
	}
}