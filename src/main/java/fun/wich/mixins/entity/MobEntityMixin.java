package fun.wich.mixins.entity;

import fun.wich.event.ModGameEvent;
import fun.wich.item.syringe.BaseSyringeItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity {
	protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) { super(entityType, world); }

	@Shadow protected abstract ActionResult interactWithItem(PlayerEntity player, Hand hand);
	@Shadow protected abstract ActionResult interactMob(PlayerEntity player, Hand hand);


	@Inject(method="interact", at = @At(value="INVOKE", shift=At.Shift.AFTER, target="Lnet/minecraft/entity/mob/MobEntity;detachLeash(ZZ)V"))
	private void InjectLeashInteractEvent(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		this.emitGameEvent(ModGameEvent.ENTITY_INTERACT, player);
	}
	@Redirect(method="interact", at = @At(value="INVOKE", target="Lnet/minecraft/entity/LivingEntity;interact(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;"))
	private ActionResult InjectItemInteractEvent(LivingEntity instance, PlayerEntity player, Hand hand) {
		ActionResult actionResult = this.interactWithItem(player, hand);
		if (actionResult.isAccepted()) {
			this.emitGameEvent(ModGameEvent.ENTITY_INTERACT, player);
		}
		return actionResult;
	}
	@Redirect(method="interact", at = @At(value="INVOKE", target="Lnet/minecraft/entity/mob/MobEntity;interactMob(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;"))
	private ActionResult InjectMobInteractEvent(MobEntity instance, PlayerEntity player, Hand hand) {
		ActionResult actionResult = this.interactMob(player, hand);
		if (actionResult.isAccepted()) {
			this.emitGameEvent(ModGameEvent.ENTITY_INTERACT, player);
		}
		return actionResult;
	}
	@Inject(method="interactWithItem", at = @At("HEAD"), cancellable = true)
	private void InteractWithItem(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.getItem() instanceof BaseSyringeItem) {
			ActionResult result = itemStack.useOnEntity(player, this, hand);
			if (result.isAccepted()) cir.setReturnValue(result);
		}
	}
}
