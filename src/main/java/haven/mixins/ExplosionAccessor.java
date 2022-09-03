package haven.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;

@Mixin(Explosion.class)
public interface ExplosionAccessor {
	@Accessor("world")
	public abstract World getWorld();
	@Accessor("entity")
	public abstract Entity getEntity();
	@Accessor("x")
	public abstract double getX();
	@Accessor("y")
	public abstract double getY();
	@Accessor("z")
	public abstract double getZ();
	@Accessor("power")
	public abstract float getPower();
	@Accessor("behavior")
	public abstract ExplosionBehavior getBehavior();
	@Accessor("affectedBlocks")
	public abstract List<BlockPos> getAffectedBlocks();
	@Accessor("affectedPlayers")
	public abstract Map<PlayerEntity, Vec3d> getAffectedPlayers();
}
