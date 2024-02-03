package fun.wich.entity.cloud;

import net.minecraft.block.Blocks;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;

public class ConfettiCloudEntity extends AreaEffectCloudEntity {
	private static final ParticleEffect PARTICLE_EFFECT = new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.AIR.getDefaultState());
	public ConfettiCloudEntity(EntityType<? extends ConfettiCloudEntity> entityType, World world) {
		super(entityType, world);
		setRadius(1.0F);
		setInvisible(true);
		setParticleType(PARTICLE_EFFECT);
		setDuration(60);
		setSilent(true);
	}
	public ConfettiCloudEntity(World world, double x, double y, double z) {
		super(world, x, y, z);
		setRadius(1.0F);
		setInvisible(true);
		setParticleType(PARTICLE_EFFECT);
		setDuration(60);
		setSilent(true);
	}

	@Override
	public void tick() {
		super.tick();
		boolean bl = this.isWaiting();
		float f = this.getRadius();
		if (this.world.isClient) {
			if (bl && this.random.nextBoolean()) return;
		}
		if (this.age >= this.getWaitTime() + this.getDuration()) {
			this.discard();
			return;
		}
		boolean bl2 = this.age < this.getWaitTime();
		if (bl != bl2) this.setWaiting(bl2);
		if (bl2) return;
		if (this.getRadiusGrowth() != 0.0F) {
			f += this.getRadiusGrowth();
			if (f < 0.5F) {
				this.discard();
				return;
			}
			this.setRadius(f);
		}
	}
}
