package fun.mousewich.mixins;

import fun.mousewich.entity.tnt.ModTntEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

@Mixin(Explosion.class)
public abstract class ExplosionMixin {
	@Shadow @Final private @Nullable Entity entity;
	@Shadow public abstract DamageSource getDamageSource();

	@Inject(method="getCausingEntity", at = @At("HEAD"), cancellable = true)
	public void GetCausingEntity(CallbackInfoReturnable<LivingEntity> cir) {
		if (this.entity instanceof ModTntEntity tnt) cir.setReturnValue(tnt.getCausingEntity());
	}

	@Redirect(method="affectWorld", at=@At(value="INVOKE", target="Lnet/minecraft/world/World;playSound(DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FFZ)V"))
	private void DontPlaySoundNoSound(World instance, double x, double y, double z, SoundEvent sound, SoundCategory category, float volume, float pitch, boolean useDistance) {
		if (this.entity instanceof ModTntEntity tntEntity) {
			if (!tntEntity.shouldMakeSound()) return;
		}
		instance.playSound(x, y, z, sound, category, volume, pitch, useDistance);
	}
	@Redirect(method="collectBlocksAndDamageEntities", at=@At(value="INVOKE", target="Lnet/minecraft/world/explosion/ExplosionBehavior;canDestroyBlock(Lnet/minecraft/world/explosion/Explosion;Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;F)Z"))
	private boolean DontAffectBlocksIfNoDestroy(ExplosionBehavior instance, Explosion explosion, BlockView world, BlockPos pos, BlockState state, float power) {
		if (this.entity instanceof ModTntEntity tntEntity) {
			if (!tntEntity.shouldDestroyBlocks()) return false;
		}
		return instance.canDestroyBlock(explosion, world, pos, state, power);
	}
	@Redirect(method="collectBlocksAndDamageEntities", at=@At(value="INVOKE", target="Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
	private boolean AdjustDamage(Entity instance, DamageSource source, float amount) {
		if (this.entity instanceof ModTntEntity tntEntity) {
			if (!tntEntity.shouldDamage()) return false;
			float damageMultiplier = tntEntity.damageMultiplier();
			if (damageMultiplier == 0) return false;
			amount *= damageMultiplier;
		}
		return instance.damage(source, amount);
	}
	@Redirect(method="collectBlocksAndDamageEntities", at=@At(value="INVOKE", target="Lnet/minecraft/entity/Entity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V"))
	private void DontPushIfNoKnockback(Entity instance, Vec3d velocity) {
		if (this.entity instanceof ModTntEntity tntEntity) {
			if (!tntEntity.shouldDoKnockback() || tntEntity.knockbackMultiplier() == 0) return;
		}
		instance.setVelocity(velocity);
	}
	@Redirect(method="collectBlocksAndDamageEntities", at=@At(value="INVOKE", target="Lnet/minecraft/util/math/Vec3d;add(DDD)Lnet/minecraft/util/math/Vec3d;"))
	private Vec3d AdjustKnockback(Vec3d instance, double x, double y, double z) {
		if (this.entity instanceof ModTntEntity tntEntity) {
			if (!tntEntity.shouldDoKnockback()) return instance;
			float knockbackMultiplier = tntEntity.knockbackMultiplier();
			if (knockbackMultiplier == 0) return instance;
			x *= knockbackMultiplier;
			y *= knockbackMultiplier;
			z *= knockbackMultiplier;
		}
		return instance.add(x, y, z);
	}
	@Redirect(method="collectBlocksAndDamageEntities", at=@At(value="INVOKE", target="Lnet/minecraft/entity/player/PlayerEntity;isSpectator()Z"))
	private boolean DontAffectPlayersIfNoKnockback(PlayerEntity instance) {
		if (this.entity instanceof ModTntEntity tntEntity) {
			if (!tntEntity.shouldDoKnockback() || tntEntity.knockbackMultiplier() == 0) return true;
		}
		return instance.isSpectator();
	}
}