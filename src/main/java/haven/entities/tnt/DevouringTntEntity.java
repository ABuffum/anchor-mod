package haven.entities.tnt;

import haven.ModBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

public class DevouringTntEntity extends ModTntEntity {
	public DevouringTntEntity(EntityType<? extends Entity> entityType, World world) {
		super(entityType, world);
	}

	public DevouringTntEntity(World world, double x, double y, double z, @Nullable LivingEntity igniter) {
		super(ModBase.DEVOURING_TNT_ENTITY, world, x, y, z, igniter);
	}

	@Override
	protected void explode() {
		Explosion explosion = this.world.createExplosion(this, this.getX(), this.getBodyY(0.0625D), this.getZ(), 4F, Explosion.DestructionType.DESTROY);
		propagateExplosion(5);
	}
	@Override
	public boolean destroyBlocks() { return true; }
	@Override
	public boolean doDamage() { return false; }
	@Override
	public boolean doKnockback() { return true; }
}
