package haven.mixins.entities;

import haven.blocks.sculk.SculkShriekerWarningManager;
import haven.entities.hostile.warden.WardenEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method="takeShieldHit", at = @At("TAIL"))
	protected void TakeShieldHit(LivingEntity attacker, CallbackInfo ci) {
		super.takeShieldHit(attacker);
		if (attacker instanceof WardenEntity) ((PlayerEntity)(Object)this).disableShield(true);
	}

	@Inject(method="tick", at = @At("HEAD"))
	public void Tick(CallbackInfo ci) {
		if (!this.world.isClient) SculkShriekerWarningManager.getSculkShriekerWarningManager((PlayerEntity)(Object)(this)).tick();
	}
}
