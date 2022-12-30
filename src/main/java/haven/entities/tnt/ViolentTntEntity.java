package haven.entities.tnt;

import haven.ModBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

public class ViolentTntEntity extends ModTntEntity {
	public ViolentTntEntity(EntityType<? extends Entity> entityType, World world) {
		super(entityType, world);
	}
	public ViolentTntEntity(World world, double x, double y, double z, @Nullable LivingEntity igniter) {
		super(ModBase.VIOLENT_TNT_ENTITY, world, x, y, z, igniter);
	}

	@Override
	protected void explode() {
		Explosion explosion = this.world.createExplosion(this, this.getX(), this.getBodyY(0.0625D), this.getZ(), 6F, Explosion.DestructionType.BREAK);
		propagateExplosion(3);
	}
	@Override
	public boolean destroyBlocks() { return true; }
	@Override
	public boolean doDamage() { return true; }
	@Override
	public boolean doKnockback() { return false; }
}
