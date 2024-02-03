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

public class SharpTntEntity extends ModTntEntity {
	public SharpTntEntity(EntityType<? extends Entity> entityType, World world) { super(entityType, world, HavenMod.SHARP_TNT.asBlock().getDefaultState()); }
	public SharpTntEntity(World world, double x, double y, double z, @Nullable LivingEntity igniter) {
		this(world, x, y, z, igniter, HavenMod.SHARP_TNT.asBlock().getDefaultState());
	}
	public SharpTntEntity(World world, double x, double y, double z, @Nullable LivingEntity igniter, BlockState state) {
		super(HavenMod.SHARP_TNT_ENTITY, world, x, y, z, igniter, state);
	}

	@Override
	protected void explode() {
		Explosion explosion = this.world.createExplosion(this, this.getX(), this.getBodyY(0.0625D), this.getZ(), 4F, Explosion.DestructionType.NONE);
		propagateExplosion(3);
	}
	@Override
	public boolean shouldDestroyBlocks() { return false; }
	@Override
	public boolean shouldDoKnockback() { return false; }
	@Override
	public float damageMultiplier() { return 3; }
}
