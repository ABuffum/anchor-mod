package fun.wich.mixins.item;

import fun.wich.item.ConditionalLoreItem;
import fun.wich.item.tool.ModShearsItem;
import fun.wich.origins.power.IncreaseDurabilityPower;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
	@Shadow public abstract Item getItem();

	@Inject(method="isOf", at = @At("HEAD"), cancellable = true)
	public void isOf(Item item, CallbackInfoReturnable<Boolean> cir) {
		if (item == Items.FISHING_ROD) {
			if (this.getItem() instanceof FishingRodItem) cir.setReturnValue(true);
		}
		else if (item == Items.SHEARS) {
			if (this.getItem() instanceof ModShearsItem) cir.setReturnValue(true);
		}
	}

	@Inject(method = "damage(ILnet/minecraft/entity/LivingEntity;Ljava/util/function/Consumer;)V", at = @At("HEAD"), cancellable = true)
	private <T extends LivingEntity> void IncreaseItemDurabilityWithPower(int amount, T entity, Consumer<T> breakCallback, CallbackInfo ci) {
		if (!entity.world.isClient && !(entity instanceof PlayerEntity player && player.isCreative())) {
			float factor = IncreaseDurabilityPower.GetFactor(entity, (ItemStack)(Object)this);
			if (factor > 0) {
				if (entity.world.random.nextFloat() < factor) ci.cancel();
			}
		}
	}

	@Inject(method="getTooltip", at=@At(value="INVOKE", target="Lnet/minecraft/item/ItemStack;hasNbt()Z", shift=At.Shift.BEFORE, ordinal=0), locals= LocalCapture.CAPTURE_FAILHARD)
	private void AppendConditionalLore(@Nullable PlayerEntity player, TooltipContext context, CallbackInfoReturnable<List<Text>> cir, List<Text> list) {
		if (getItem() instanceof ConditionalLoreItem loreItem) loreItem.ApplyConditionalLore((ItemStack)(Object)this, player, context, list);
	}
}
