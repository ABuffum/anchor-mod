package haven.mixins;

import com.google.common.collect.Sets;
import haven.entities.SoftTntEntity;
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
		boolean soft = ent instanceof SoftTntEntity;
		World world = acc.getWorld();
		double X = acc.getX(), Y = acc.getY(), Z = acc.getZ();
		float power = acc.getPower();
		ExplosionBehavior behavior = acc.getBehavior();
		world.emitGameEvent(ent, GameEvent.EXPLODE, new BlockPos(X, Y, Z));
		Set<BlockPos> set = Sets.newHashSet();
		int k;
		int l;
		for(int j = 0; j < 16; ++j) {
			for(k = 0; k < 16; ++k) {
				for(l = 0; l < 16; ++l) {
					if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15) {
						double d = (double)((float)j / 15.0F * 2.0F - 1.0F);
						double e = (double)((float)k / 15.0F * 2.0F - 1.0F);
						double f = (double)((float)l / 15.0F * 2.0F - 1.0F);
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
							if (!world.isInBuildLimit(blockPos)) {
								break;
							}

							Optional<Float> optional = behavior.getBlastResistance(exp, world, blockPos, blockState, fluidState);
							if (optional.isPresent()) {
								h -= ((Float)optional.get() + 0.3F) * 0.3F;
							}

							if (h > 0.0F && behavior.canDestroyBlock(exp, world, blockPos, blockState, h)) {
								set.add(blockPos);
							}

							m += d * 0.30000001192092896D;
							n += e * 0.30000001192092896D;
							o += f * 0.30000001192092896D;
						}
					}
				}
			}
		}
		if (!soft) { //soft tnt can't affect blocks
			acc.getAffectedBlocks().addAll(set);
		}
		float q = power * 2.0F;
		k = MathHelper.floor(X - (double)q - 1.0D);
		l = MathHelper.floor(X + (double)q + 1.0D);
		int t = MathHelper.floor(Y - (double)q - 1.0D);
		int u = MathHelper.floor(Y + (double)q + 1.0D);
		int v = MathHelper.floor(Z - (double)q - 1.0D);
		int w = MathHelper.floor(Z + (double)q + 1.0D);
		List<Entity> list = world.getOtherEntities(ent, new Box((double)k, (double)t, (double)v, (double)l, (double)u, (double)w));
		Vec3d vec3d = new Vec3d(X, Y, Z);

		for(int x = 0; x < list.size(); ++x) {
			Entity entity = (Entity)list.get(x);
			if (!entity.isImmuneToExplosion()) {
				double y = Math.sqrt(entity.squaredDistanceTo(vec3d)) / (double)q;
				if (y <= 1.0D) {
					double z = entity.getX() - X;
					double aa = (entity instanceof TntEntity ? entity.getY() : entity.getEyeY()) - Y;
					double ab = entity.getZ() - Z;
					double ac = Math.sqrt(z * z + aa * aa + ab * ab);
					if (ac != 0.0D) {
						z /= ac;
						aa /= ac;
						ab /= ac;
						double ad = (double)exp.getExposure(vec3d, entity);
						double ae = (1.0D - y) * ad;
						if (!soft && power > 0) {
							entity.damage(exp.getDamageSource(), (float)((int)((ae * ae + ae) / 2.0D * 7.0D * (double)q + 1.0D)));
						}
						double af = ae;
						if (entity instanceof LivingEntity) {
							af = ProtectionEnchantment.transformExplosionKnockback((LivingEntity)entity, ae);
						}

						entity.setVelocity(entity.getVelocity().add(z * af, aa * af, ab * af));
						if (entity instanceof PlayerEntity) {
							PlayerEntity playerEntity = (PlayerEntity)entity;
							if (!playerEntity.isSpectator() && (!playerEntity.isCreative() || !playerEntity.getAbilities().flying)) {
								acc.getAffectedPlayers().put(playerEntity, new Vec3d(z * ae, aa * ae, ab * ae));
							}
						}
					}
				}
			}
		}
		ci.cancel();
	}
}
