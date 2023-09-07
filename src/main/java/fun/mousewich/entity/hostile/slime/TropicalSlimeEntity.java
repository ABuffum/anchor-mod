package fun.mousewich.entity.hostile.slime;

import fun.mousewich.ModBase;
import fun.mousewich.entity.ai.SlimeMoveControl;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import fun.mousewich.particle.ModParticleTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.passive.TropicalFishEntity;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.Util;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class TropicalSlimeEntity extends ModSlimeEntity implements EntityWithBloodType {
	public double prevTick = 0;
	public int frame = 0;
	public TropicalSlimeEntity(EntityType<? extends SlimeEntity> entityType, World world) {
		super(entityType, world);
		this.moveControl = new SlimeMoveControl(this);
	}

	@Override
	protected void initGoals() {
		super.initGoals();
		this.goalSelector.remove(this.swimmingGoal);
	}

	@Override
	public boolean canBreatheInWater() { return true; }
	@Override
	public boolean isPushedByFluids() { return false; }

	@Override
	public boolean canSpawn(WorldView world) { return world.intersectsEntities(this); }
	@Override
	protected ParticleEffect getParticles() { return ModParticleTypes.ITEM_BLUE_SLIME; }

	public boolean damage(DamageSource source, float amount) {
		if (this.isInvulnerableTo(source)) return false;
		else {
			Entity entity = source.getAttacker();
			if (entity != null) {
				if (!source.isProjectile()) { //only melee
					if (!source.isExplosive()) { //ignore explosions
						if (!source.isMagic()) { //no magic >:(
							entity.setAir(this.getNextAirOnLand(this.getAir()));
						}
					}
				}
			}
			return super.damage(source, amount);
		}
	}
	@Override
	public void onDeath(DamageSource source) {
		super.onDeath(source);
		if (this.getSize() <= 1) {
			int i = random.nextInt(10);
			if (i == 0) {
				if (!this.world.isClient) {
					TropicalFishEntity fish = EntityType.TROPICAL_FISH.create(this.world);
					if (fish != null) {
						int j, k, l;
						if ((double)this.random.nextFloat() < 0.9) {
							int m = Util.getRandom(TropicalFishEntity.COMMON_VARIANTS, this.random);
							i = m & 0xFF;
							j = (m & 0xFF00) >> 8;
							k = (m & 0xFF0000) >> 16;
							l = (m & 0xFF000000) >> 24;
						} else {
							i = this.random.nextInt(2);
							j = this.random.nextInt(6);
							k = this.random.nextInt(15);
							l = this.random.nextInt(15);
						}
						fish.setVariant(i | j << 8 | k << 16 | l << 24);
						fish.setPosition(getPos());
						world.spawnEntity(fish);
					}
				}
			}
			else if (i < 3) dropItem(Items.TROPICAL_FISH);
		}
	}

	@Override public BloodType GetDefaultBloodType() { return ModBase.BLUE_SLIME_BLOOD_TYPE; }
}
