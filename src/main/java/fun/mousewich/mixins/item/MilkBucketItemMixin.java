package fun.mousewich.mixins.item;

import fun.mousewich.util.MilkUtil;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MilkBucketItem.class)
public class MilkBucketItemMixin extends Item {
	public MilkBucketItemMixin(Settings settings) { super(settings); }

	@Inject(method="finishUsing", at = @At("HEAD"), cancellable = true)
	public void FinishUsing(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
		if (user instanceof ServerPlayerEntity serverPlayerEntity) {
			Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
			serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
		}
		if (user instanceof PlayerEntity player && !player.getAbilities().creativeMode) stack.decrement(1);
		if (!world.isClient) MilkUtil.ApplyMilk(world, user);
		cir.setReturnValue(stack.isEmpty() ? new ItemStack(Items.BUCKET) : stack);
	}
}
