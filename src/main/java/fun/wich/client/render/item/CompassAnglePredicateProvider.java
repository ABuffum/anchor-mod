package fun.wich.client.render.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.UnclampedModelPredicateProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.dynamic.GlobalPos;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

@Environment(value= EnvType.CLIENT)
public class CompassAnglePredicateProvider implements UnclampedModelPredicateProvider {
	private final AngleInterpolator aimedInterpolator = new AngleInterpolator();
	private final AngleInterpolator aimlessInterpolator = new AngleInterpolator();
	public final CompassTarget compassTarget;

	public CompassAnglePredicateProvider(CompassTarget compassTarget) { this.compassTarget = compassTarget; }

	@Override
	public float unclampedCall(ItemStack itemStack, ClientWorld clientWorld, LivingEntity livingEntity, int i) {
		Entity entity = livingEntity != null ? livingEntity : itemStack.getHolder();
		if (entity == null) return 0;
		if ((clientWorld = this.getClientWorld(entity, clientWorld)) == null) return 0;
		return this.getAngle(itemStack, clientWorld, i, entity);
	}

	private float getAngle(ItemStack stack, ClientWorld world, int seed, Entity entity) {
		GlobalPos globalPos = this.compassTarget.getPos(world, stack, entity);
		long l = world.getTime();
		if (!this.canPointTo(entity, globalPos)) return this.getAimlessAngle(seed, l);
		return this.getAngleTo(entity, l, globalPos.getPos());
	}

	private float getAimlessAngle(int seed, long time) {
		if (this.aimlessInterpolator.shouldUpdate(time)) this.aimlessInterpolator.update(time, Math.random());
		double d = this.aimlessInterpolator.value + (double)((float)this.scatter(seed) / 2.14748365E9f);
		return MathHelper.floorMod((float)d, 1.0f);
	}

	private float getAngleTo(Entity entity, long time, BlockPos pos) {
		double d = this.getAngleTo(entity, pos);
		double e = this.getBodyYaw(entity);
		if (entity instanceof PlayerEntity playerEntity && playerEntity.isMainPlayer()) {
			if (this.aimedInterpolator.shouldUpdate(time)) this.aimedInterpolator.update(time, 0.5 - (e - 0.25));
			return MathHelper.floorMod((float)(d + this.aimedInterpolator.value), 1.0f);
		}
		else return MathHelper.floorMod((float)(0.5 - (e - 0.25 - d)), 1);
	}

	private ClientWorld getClientWorld(Entity entity, ClientWorld world) {
		return world == null && entity.world instanceof ClientWorld clientWorld ? clientWorld : world;
	}

	private boolean canPointTo(Entity entity, GlobalPos pos) {
		Vec3d posVec = entity.getPos();
		Vec3i vec = new Vec3i(posVec.getX(), posVec.getY(), posVec.getZ());
		return pos != null && pos.getDimension() == entity.world.getRegistryKey() && pos.getPos().getSquaredDistance(vec) > 1.0E-5;
	}

	private double getAngleTo(Entity entity, BlockPos pos) {
		Vec3d vec3d = Vec3d.ofCenter(pos);
		return Math.atan2(vec3d.getZ() - entity.getZ(), vec3d.getX() - entity.getX()) / 6.2831854820251465;
	}

	private double getBodyYaw(Entity entity) { return MathHelper.floorMod(entity.getYaw() / 360.0f, 1.0); }

	/**
	 * Scatters a seed by integer overflow in multiplication onto the whole
	 * int domain.
	 */
	private int scatter(int seed) { return seed * 1327217883; }

	@Environment(value=EnvType.CLIENT)
	static class AngleInterpolator {
		double value;
		private double speed;
		private long lastUpdateTime;
		AngleInterpolator() { }
		boolean shouldUpdate(long time) { return this.lastUpdateTime != time; }
		void update(long time, double target) {
			this.lastUpdateTime = time;
			this.speed += (MathHelper.floorMod((target - this.value) + 0.5, 1.0) - 0.5) * 0.1;
			this.speed *= 0.8;
			this.value = MathHelper.floorMod(this.value + this.speed, 1.0);
		}
	}

	@Environment(value=EnvType.CLIENT)
	public interface CompassTarget { GlobalPos getPos(ClientWorld var1, ItemStack var2, Entity var3); }
}