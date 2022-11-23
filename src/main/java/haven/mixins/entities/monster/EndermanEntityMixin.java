package haven.mixins.entities.monster;

import haven.ModTags;
import haven.events.HavenGameEvent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndermanEntity.class)
public abstract class EndermanEntityMixin extends HostileEntity implements Angerable {
	protected EndermanEntityMixin(EntityType<? extends HostileEntity> entityType, World world) { super(entityType, world); }

	@Inject(method="isPlayerStaring", at = @At("HEAD"), cancellable = true)
	void IsPlayerStaring(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
		ItemStack itemStack = player.getInventory().armor.get(3);
		if (itemStack.isIn(ModTags.Items.CARVED_PUMPKINS)) cir.setReturnValue(false);
	}

	@Inject(method="teleportTo(DDD)Z", at = @At(value="INVOKE", target="Lnet/minecraft/entity/mob/EndermanEntity;isSilent()Z"))
	private void TeleportTo(double x, double y, double z, CallbackInfoReturnable<Boolean> cir) {
		this.world.emitGameEvent(this, HavenGameEvent.TELEPORT, getBlockPos());
	}
}
