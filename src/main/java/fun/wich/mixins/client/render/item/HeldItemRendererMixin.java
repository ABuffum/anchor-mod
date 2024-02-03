package fun.wich.mixins.client.render.item;

import fun.wich.item.BrushItem;
import fun.wich.util.math.RotationAxis;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
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

@Mixin(HeldItemRenderer.class)
public abstract class HeldItemRendererMixin {
	@Shadow protected abstract void applyEquipOffset(MatrixStack matrices, Arm arm, float equipProgress);
	@Shadow @Final private MinecraftClient client;
	@Shadow protected abstract void applySwingOffset(MatrixStack matrices, Arm arm, float swingProgress);
	@Shadow public abstract void renderItem(LivingEntity entity, ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light);

	@Inject(method="renderFirstPersonItem", at=@At("HEAD"), cancellable = true)
	private void RenderFirstPersonItemWithSpecialPose(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack stack, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
		if (!stack.isEmpty() && stack.getItem() instanceof BrushItem) {
			if (player.isUsingSpyglass()) return;
			Arm arm = hand == Hand.MAIN_HAND ? player.getMainArm() : player.getMainArm().getOpposite();
			matrices.push();
			boolean bl2 = arm == Arm.RIGHT;
			if (player.isUsingItem() && player.getItemUseTimeLeft() > 0 && player.getActiveHand() == hand) {
				this.applyBrushTransformation(matrices, tickDelta, arm, stack, equipProgress);
			}
			else if (player.isUsingRiptide()) {
				this.applyEquipOffset(matrices, arm, equipProgress);
				int l = bl2 ? 1 : -1;
				matrices.translate((float)l * -0.4f, 0.8f, 0.3f);
				matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)l * 65.0f));
				matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)l * -85.0f));
			}
			else {
				float n = -0.4f * MathHelper.sin(MathHelper.sqrt(swingProgress) * (float)Math.PI);
				float m = 0.2f * MathHelper.sin(MathHelper.sqrt(swingProgress) * ((float)Math.PI * 2));
				float f = -0.2f * MathHelper.sin(swingProgress * (float)Math.PI);
				int o = bl2 ? 1 : -1;
				matrices.translate((float)o * n, m, f);
				this.applyEquipOffset(matrices, arm, equipProgress);
				this.applySwingOffset(matrices, arm, swingProgress);
			}
			this.renderItem(player, stack, bl2 ? ModelTransformation.Mode.FIRST_PERSON_RIGHT_HAND : ModelTransformation.Mode.FIRST_PERSON_LEFT_HAND, !bl2, matrices, vertexConsumers, light);
			matrices.pop();
			ci.cancel();
		}
	}

	private void applyBrushTransformation(MatrixStack matrices, float tickDelta, Arm arm, ItemStack stack, float equipProgress) {
		this.applyEquipOffset(matrices, arm, equipProgress);
		float f = (float)this.client.player.getItemUseTimeLeft() - tickDelta + 1.0f;
		float g = 1.0f - f / (float)stack.getMaxUseTime();
		float m = -15.0f + 75.0f * MathHelper.cos(g * 45.0f * (float)Math.PI);
		if (arm != Arm.RIGHT) {
			matrices.translate(0.1, 0.83, 0.35);
			matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-80.0f));
			matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90.0f));
			matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(m));
			matrices.translate(-0.3, 0.22, 0.35);
		}
		else {
			matrices.translate(-0.25, 0.22, 0.35);
			matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-80.0f));
			matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0f));
			matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(0.0f));
			matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(m));
		}
	}
}
