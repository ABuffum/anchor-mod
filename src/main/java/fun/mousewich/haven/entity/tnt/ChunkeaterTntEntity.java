package fun.mousewich.haven.entity.tnt;

import fun.mousewich.entity.tnt.ModTntEntity;
import fun.mousewich.haven.HavenMod;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

public class ChunkeaterTntEntity extends ModTntEntity {
	public ChunkeaterTntEntity(EntityType<? extends Entity> entityType, World world) { super(entityType, world, HavenMod.CHUNKEATER_TNT.asBlock().getDefaultState()); }
	public ChunkeaterTntEntity(World world, double x, double y, double z, @Nullable LivingEntity igniter) {
		this(world, x, y, z, igniter, HavenMod.CHUNKEATER_TNT.asBlock().getDefaultState());
	}
	public ChunkeaterTntEntity(World world, double x, double y, double z, @Nullable LivingEntity igniter, BlockState state) {
		super(HavenMod.CHUNKEATER_TNT_ENTITY, world, x, y, z, igniter, state);
	}

	@Override
	protected void explode() {
		Explosion explosion = this.world.createExplosion(this, this.getX(), this.getBodyY(0.0625D), this.getZ(), 128F, Explosion.DestructionType.BREAK);
		propagateExplosion(16);
	}
	@Override
	public float damageMultiplier() { return 2; }
}
