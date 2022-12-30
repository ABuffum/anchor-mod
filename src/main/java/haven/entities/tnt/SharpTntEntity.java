package haven.entities.tnt;

import haven.ModBase;
import net.minecraft.entity.*;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

public class SharpTntEntity extends ModTntEntity {
	public SharpTntEntity(EntityType<? extends Entity> entityType, World world) {
		super(entityType, world);
	}
	public SharpTntEntity(World world, double x, double y, double z, @Nullable LivingEntity igniter) {
		super(ModBase.SHARP_TNT_ENTITY, world, x, y, z, igniter);
	}

	@Override
	protected void explode() {
		Explosion explosion = this.world.createExplosion(this, this.getX(), this.getBodyY(0.0625D), this.getZ(), 4F, Explosion.DestructionType.NONE);
		propagateExplosion(3);
	}
	@Override
	public boolean destroyBlocks() { return false; }
	@Override
	public boolean doDamage() { return true; }
	@Override
	public boolean doKnockback() { return false; }
}
