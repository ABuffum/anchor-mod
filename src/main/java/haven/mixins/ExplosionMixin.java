package haven.mixins;

import com.google.common.collect.Sets;
import haven.entities.tnt.*;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Mixin(Explosion.class)
public class ExplosionMixin {
	@Inject(method="collectBlocksAndDamageEntities", at = @At("HEAD"), cancellable = true)
	public void blocksAndDamageEntities(CallbackInfo ci) {
		Explosion exp = (Explosion)(Object)this;
		ExplosionAccessor acc = (ExplosionAccessor)exp;
		Entity ent = acc.getEntity();
		//We only want to mess with explosions we're responsible for
		if (!(ent instanceof ModTntEntity tntEntity)) return;
		World world = acc.getWorld();
		double X = acc.getX(), Y = acc.getY(), Z = acc.getZ();
		float power = acc.getPower();
		ExplosionBehavior behavior = acc.getBehavior();
		world.emitGameEvent(tntEntity, GameEvent.EXPLODE, new BlockPos(X, Y, Z));
		Set<BlockPos> set = Sets.newHashSet();
		int k;
		int l;
		for(int j = 0; j < 16; ++j) {
			for(k = 0; k < 16; ++k) {
				for(l = 0; l < 16; ++l) {
					if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15) {
						double d = j / 15.0 * 2.0 - 1.0;
						double e = k / 15.0 * 2.0 - 1.0;
						double f = l / 15.0 * 2.0 - 1.0;
						double g = Math.sqrt(d * d + e * e + f * f);
						d /= g;
						e /= g;
						f /= g;
						float h = power * (0.7F + world.random.nextFloat() * 0.6F);
						double m = X;
						double n = Y;
						double o = Z;

						for(float var21 = 0.3F; h > 0.0F; h -= 0.22500001F) {
							BlockPos blockPos = new BlockPos(m, n, o);
							BlockState blockState = world.getBlockState(blockPos);
							FluidState fluidState = world.getFluidState(blockPos);
							if (!world.isInBuildLimit(blockPos)) break;
							Optional<Float> optional = behavior.getBlastResistance(exp, world, blockPos, blockState, fluidState);
							if (optional.isPresent()) h -= (optional.get() + 0.3F) * 0.3F;
							if (h > 0.0F && behavior.canDestroyBlock(exp, world, blockPos, blockState, h)) set.add(blockPos);
							m += d * 0.30000001192092896D;
							n += e * 0.30000001192092896D;
							o += f * 0.30000001192092896D;
						}
					}
				}
			}
		}
		if (tntEntity.destroyBlocks()) acc.getAffectedBlocks().addAll(set);
		float q = power * 2.0F;
		k = MathHelper.floor(X - q - 1.0D);
		l = MathHelper.floor(X + q + 1.0D);
		int t = MathHelper.floor(Y - q - 1.0D);
		int u = MathHelper.floor(Y + q + 1.0D);
		int v = MathHelper.floor(Z - q - 1.0D);
		int w = MathHelper.floor(Z + q + 1.0D);
		Vec3d vec3d = new Vec3d(X, Y, Z);
		for (Entity entity : world.getOtherEntities(ent, new Box(k, t, v, l, u, w))) {
			if (!entity.isImmuneToExplosion()) {
				double y = Math.sqrt(entity.squaredDistanceTo(vec3d)) / (double) q;
				if (y <= 1.0D) {
					double z = entity.getX() - X;
					double aa = (entity instanceof TntEntity ? entity.getY() : entity.getEyeY()) - Y;
					double ab = entity.getZ() - Z;
					double ac = Math.sqrt(z * z + aa * aa + ab * ab);
					if (ac != 0.0D) {
						z /= ac;
						aa /= ac;
						ab /= ac;
						double ae = (1.0D - y) * Explosion.getExposure(vec3d, entity);
						if (tntEntity.doDamage() && power > 0) {
							float damage = (int)((ae * ae + ae) / 2.0 * 7.0 * q + 1.0);
							if (tntEntity instanceof ChunkeaterTntEntity) damage *= 2;
							if (tntEntity instanceof SharpTntEntity) damage *= 3;
							entity.damage(exp.getDamageSource(), damage);
						}
						if (tntEntity.doKnockback()) {
							double af = ae;
							if (entity instanceof LivingEntity) af = ProtectionEnchantment.transformExplosionKnockback((LivingEntity) entity, ae);
							if (entity instanceof DevouringTntEntity) af *= -1;
							entity.setVelocity(entity.getVelocity().add(z * af, aa * af, ab * af));
							if (entity instanceof PlayerEntity playerEntity) {
								if (!playerEntity.isSpectator() && (!playerEntity.isCreative() || !playerEntity.getAbilities().flying)) {
									acc.getAffectedPlayers().put(playerEntity, new Vec3d(z * ae, aa * ae, ab * ae));
								}
							}
						}
					}
				}
			}
		}
		ci.cancel();
	}
}
