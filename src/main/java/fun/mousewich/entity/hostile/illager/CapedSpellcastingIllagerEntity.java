package fun.mousewich.entity.hostile.illager;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SpellcastingIllagerEntity;
import net.minecraft.world.World;

public abstract class CapedSpellcastingIllagerEntity extends SpellcastingIllagerEntity {
	public float prevStrideDistance;
	public float strideDistance;
	public double prevCapeX;
	public double prevCapeY;
	public double prevCapeZ;
	public double capeX;
	public double capeY;
	public double capeZ;

	protected CapedSpellcastingIllagerEntity(EntityType<? extends SpellcastingIllagerEntity> entityType, World world) {
		super(entityType, world);
	}

	public static DefaultAttributeContainer.Builder createSpellcasterAttributes() {
		return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 12.0).add(EntityAttributes.GENERIC_MAX_HEALTH, 24.0);
	}

	//Cape logic
	@Override
	public void tick() {
		this.updateCapeAngles();
		super.tick();
	}
	@Override
	public void tickRiding() {
		super.tickRiding();
		this.prevStrideDistance = this.strideDistance;
		this.strideDistance = 0.0f;
	}
	@Override
	public void tickMovement() {
		this.prevStrideDistance = this.strideDistance;
		super.tickMovement();
		float f = !this.onGround || this.isDead() || this.isSwimming() ? 0.0f : Math.min(0.1f, (float)this.getVelocity().horizontalLength());
		this.strideDistance += (f - this.strideDistance) * 0.4f;
	}
	protected void updateCapeAngles() {
		this.prevCapeX = this.capeX;
		this.prevCapeY = this.capeY;
		this.prevCapeZ = this.capeZ;
		double d = this.getX() - this.capeX;
		double e = this.getY() - this.capeY;
		double f = this.getZ() - this.capeZ;
		if (d > 10) this.prevCapeX = this.capeX = this.getX();
		if (f > 10) this.prevCapeZ = this.capeZ = this.getZ();
		if (e > 10) this.prevCapeY = this.capeY = this.getY();
		if (d < -10) this.prevCapeX = this.capeX = this.getX();
		if (f < -10) this.prevCapeZ = this.capeZ = this.getZ();
		if (e < -10) this.prevCapeY = this.capeY = this.getY();
		this.capeX += d * 0.25;
		this.capeZ += f * 0.25;
		this.capeY += e * 0.25;
	}
}
