package fun.mousewich.mixins;

import com.google.common.collect.Sets;
import fun.mousewich.entity.tnt.ModTntEntity;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.damage.DamageSource;
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
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

@Mixin(Explosion.class)
public abstract class ExplosionMixin {
	@Shadow @Final private List<BlockPos> affectedBlocks;
	@Shadow @Final private ExplosionBehavior behavior;
	@Shadow @Final private float power;
	@Shadow @Final private double x;
	@Shadow @Final private double y;
	@Shadow @Final private double z;
	@Shadow @Final private World world;
	@Shadow @Final private Entity entity;
	@Shadow @Final private Map<PlayerEntity, Vec3d> affectedPlayers;
	@Shadow public abstract DamageSource getDamageSource();

	@Inject(method="getCausingEntity", at = @At("HEAD"), cancellable = true)
	public void GetCausingEntity(CallbackInfoReturnable<LivingEntity> cir) {
		if (this.entity instanceof ModTntEntity tnt) cir.setReturnValue(tnt.getCausingEntity());
	}
	@Inject(method="collectBlocksAndDamageEntities", at = @At("HEAD"), cancellable = true)
	public void CollectBlocksAndDamageEntities(CallbackInfo ci) {
		//We only want to mess with explosions we're responsible for
		if (!(this.entity instanceof ModTntEntity tntEntity)) return;
		int l;
		int k;
		this.world.emitGameEvent(this.entity, GameEvent.EXPLODE, new BlockPos(this.x, this.y, this.z));
		HashSet<BlockPos> set = Sets.newHashSet();
		for (int j = 0; j < 16; ++j) {
			for (k = 0; k < 16; ++k) {
				block2: for (l = 0; l < 16; ++l) {
					if (j != 0 && j != 15 && k != 0 && k != 15 && l != 0 && l != 15) continue;
					double d = (float)j / 15.0f * 2.0f - 1.0f;
					double e = (float)k / 15.0f * 2.0f - 1.0f;
					double f = (float)l / 15.0f * 2.0f - 1.0f;
					double g = Math.sqrt(d * d + e * e + f * f);
					d /= g;
					e /= g;
					f /= g;
					double m = this.x;
					double n = this.y;
					double o = this.z;
					for (float h = this.power * (0.7f + this.world.random.nextFloat() * 0.6f); h > 0.0f; h -= 0.22500001f) {
						BlockPos blockPos = new BlockPos(m, n, o);
						BlockState blockState = this.world.getBlockState(blockPos);
						FluidState fluidState = this.world.getFluidState(blockPos);
						if (!this.world.isInBuildLimit(blockPos)) continue block2;
						Optional<Float> optional = this.behavior.getBlastResistance((Explosion)(Object)this, this.world, blockPos, blockState, fluidState);
						if (optional.isPresent()) h -= (optional.get() + 0.3f) * 0.3f;
						if (h > 0.0f && this.behavior.canDestroyBlock((Explosion)(Object)this, this.world, blockPos, blockState, h)) set.add(blockPos);
						m += d * (double)0.3f;
						n += e * (double)0.3f;
						o += f * (double)0.3f;
					}
				}
			}
		}
		if (tntEntity.shouldDestroyBlocks()) this.affectedBlocks.addAll(set);
		float q = this.power * 2.0f;
		k = MathHelper.floor(this.x - (double)q - 1.0);
		l = MathHelper.floor(this.x + (double)q + 1.0);
		int r = MathHelper.floor(this.y - (double)q - 1.0);
		int s = MathHelper.floor(this.y + (double)q + 1.0);
		int t = MathHelper.floor(this.z - (double)q - 1.0);
		int u = MathHelper.floor(this.z + (double)q + 1.0);
		List<Entity> list = this.world.getOtherEntities(this.entity, new Box(k, r, t, l, s, u));
		Vec3d vec3d = new Vec3d(this.x, this.y, this.z);
		for (Entity value : list) {
			if (value.isImmuneToExplosion()) continue;
			double x = value.getX();
			double y = value instanceof TntEntity ? value.getY() : value.getEyeY();
			double z = value.getZ();
			double aa = Math.sqrt((x - this.x) * x + (y - this.y) * y + (z - this.z) * z);
			double w = Math.sqrt(value.squaredDistanceTo(vec3d));
			if (w / q > 1.0 || aa == 0.0) continue;
			x /= aa;
			y /= aa;
			z /= aa;
			double ab = Explosion.getExposure(vec3d, value);
			double ac = (1.0 - w) * ab;
			float damageMultiplier = tntEntity.damageMultiplier();
			if (damageMultiplier > 0 && this.power > 0) {
				float damage = (int)((ac * ac + ac) / 2.0 * 7.0 * q + 1.0);
				this.entity.damage(this.getDamageSource(), damage * damageMultiplier);
			}
			float knockbackMultiplier = tntEntity.knockbackMultiplier();
			if (knockbackMultiplier != 0) {
				double ad = ac;
				if (this.entity instanceof LivingEntity) ad = ProtectionEnchantment.transformExplosionKnockback((LivingEntity) entity, ac);
				ad *= knockbackMultiplier;
				this.entity.setVelocity(this.entity.getVelocity().add(x * ad, y * ad, z * ad));
				if (this.entity instanceof PlayerEntity playerEntity) {
					if (!playerEntity.isSpectator() && (!playerEntity.isCreative() || !playerEntity.getAbilities().flying)) {
						this.affectedPlayers.put(playerEntity, new Vec3d(z * ac, aa * ac, ab * ac));
					}
				}
			}
		}
		ci.cancel();
	}
}
