package fun.wich.mixins.entity.decoration;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemFrameEntity.class)
public abstract class ItemFrameEntityMixin extends AbstractDecorationEntity {
	protected ItemFrameEntityMixin(EntityType<? extends AbstractDecorationEntity> entityType, World world) { super(entityType, world); }

	@Inject(method="damage", at = @At(value="INVOKE", shift=At.Shift.AFTER, target="Lnet/minecraft/entity/decoration/ItemFrameEntity;dropHeldStack(Lnet/minecraft/entity/Entity;Z)V"))
	private void InjectBlockChangeOnDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		this.emitGameEvent(GameEvent.BLOCK_CHANGE, source.getAttacker());
	}
	@Inject(method="onBreak", at = @At("TAIL"))
	private void InjectBlockChangeOnBreak(Entity entity, CallbackInfo ci) {
		this.emitGameEvent(GameEvent.BLOCK_CHANGE, entity);
	}
	@Inject(method="interact", at = @At(value="INVOKE", shift=At.Shift.AFTER, target="Lnet/minecraft/entity/decoration/ItemFrameEntity;setHeldItemStack(Lnet/minecraft/item/ItemStack;)V"))
	private void InjectBlockChangeOnSet(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		this.emitGameEvent(GameEvent.BLOCK_CHANGE, player);
	}
	@Inject(method="interact", at = @At(value="INVOKE", shift=At.Shift.AFTER, target="Lnet/minecraft/entity/decoration/ItemFrameEntity;setRotation(I)V"))
	private void InjectBlockChangeOnRotate(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		this.emitGameEvent(GameEvent.BLOCK_CHANGE, player);
	}
}
