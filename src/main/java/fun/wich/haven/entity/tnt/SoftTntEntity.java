package fun.wich.haven.entity.tnt;

import fun.wich.entity.tnt.ModTntEntity;
import fun.wich.haven.HavenMod;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

public class SoftTntEntity extends ModTntEntity {
	public SoftTntEntity(EntityType<? extends Entity> entityType, World world) { super(entityType, world, HavenMod.SOFT_TNT.asBlock().getDefaultState()); }
	public SoftTntEntity(World world, double x, double y, double z, @Nullable LivingEntity igniter) {
		this(world, x, y, z, igniter, HavenMod.SOFT_TNT.asBlock().getDefaultState());
	}
	public SoftTntEntity(World world, double x, double y, double z, @Nullable LivingEntity igniter, BlockState state) {
		super(HavenMod.SOFT_TNT_ENTITY, world, x, y, z, igniter, state);
	}

	@Override
	protected void explode() {
		Explosion explosion = this.world.createExplosion(this, this.getX(), this.getBodyY(0.0625D), this.getZ(), -4F, Explosion.DestructionType.NONE);
		propagateExplosion(3);
	}
	@Override
	public boolean shouldDestroyBlocks() { return false; }
	@Override
	public boolean shouldDamage() { return false; }
	@Override
	public float knockbackMultiplier() { return 1.5F; }
}
