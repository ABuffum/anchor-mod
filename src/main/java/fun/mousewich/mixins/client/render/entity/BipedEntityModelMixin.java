package fun.mousewich.mixins.client.render.entity;

import fun.mousewich.item.BrushItem;
import fun.mousewich.item.horn.HornItem;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelMixin<T extends LivingEntity> extends AnimalModel<T> implements ModelWithArms, ModelWithHead {
	@Shadow @Final public ModelPart rightArm;
	@Shadow @Final public ModelPart head;
	@Shadow @Final public ModelPart leftArm;

	@Inject(method="positionRightArm", at=@At("HEAD"), cancellable=true)
	private void RepositionRightArm(T entity, CallbackInfo ci) {
		if (entity instanceof PlayerEntity player && player.getItemUseTimeLeft() > 0) {
			Hand hand = player.getMainArm() == Arm.RIGHT ? Hand.MAIN_HAND : Hand.OFF_HAND;
			ItemStack stack = player.getStackInHand(hand);
			if (!stack.isEmpty()) {
				Item item = stack.getItem();
				if (item instanceof HornItem) {
					this.rightArm.pitch = MathHelper.clamp(this.head.pitch, -1.2f, 1.2f) - 1.4835298f;
					this.rightArm.yaw = this.head.yaw - 0.5235988f;
					ci.cancel();
				}
				else if (item instanceof BrushItem) {
					this.rightArm.pitch = this.rightArm.pitch * 0.5f - 0.62831855f;
					this.rightArm.yaw = 0.0f;
					ci.cancel();
				}
			}
		}
	}
	@Inject(method="positionLeftArm", at=@At("HEAD"), cancellable=true)
	private void RepositionLeftArm(T entity, CallbackInfo ci) {
		if (entity instanceof PlayerEntity player && player.getItemUseTimeLeft() > 0) {
			Hand hand = player.getMainArm() == Arm.LEFT ? Hand.MAIN_HAND : Hand.OFF_HAND;
			ItemStack stack = player.getStackInHand(hand);
			if (!stack.isEmpty()) {
				Item item = stack.getItem();
				if (item instanceof HornItem) {
					this.leftArm.pitch = MathHelper.clamp(this.head.pitch, -1.2f, 1.2f) - 1.4835298f;
					this.leftArm.yaw = this.head.yaw + 0.5235988f;
					ci.cancel();
				}
				else if (item instanceof BrushItem) {
					this.leftArm.pitch = this.leftArm.pitch * 0.5f - 0.62831855f;
					this.leftArm.yaw = 0.0f;
					ci.cancel();
				}
			}
		}
	}
}
