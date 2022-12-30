package haven.entities.tnt;

import haven.ModBase;
import net.minecraft.entity.*;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

public class SoftTntEntity extends ModTntEntity {
	public SoftTntEntity(EntityType<? extends Entity> entityType, World world) {
		super(entityType, world);
	}

	public SoftTntEntity(World world, double x, double y, double z, @Nullable LivingEntity igniter) {
		super(ModBase.SOFT_TNT_ENTITY, world, x, y, z, igniter);
	}

	@Override
	protected void explode() {
		Explosion explosion = this.world.createExplosion(this, this.getX(), this.getBodyY(0.0625D), this.getZ(), -3F, Explosion.DestructionType.NONE);
		propagateExplosion(3);
	}
	@Override
	public boolean destroyBlocks() { return false; }
	@Override
	public boolean doDamage() { return false; }
	@Override
	public boolean doKnockback() { return true; }
}
