package haven.mixins.items;

import haven.items.consumable.milk.CopperMilkBucketItem;
import haven.items.consumable.milk.WoodMilkBucketItem;
import haven.util.BucketUtils;
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
	public MilkBucketItemMixin(Settings settings) {
		super(settings);
	}

	@Inject(method="finishUsing", at = @At("HEAD"), cancellable = true)
	public void FinishUsing(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
		Item item = stack.getItem();
		if (item instanceof WoodMilkBucketItem || item instanceof CopperMilkBucketItem) return;
		if (user instanceof ServerPlayerEntity) {
			ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)user;
			Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
			serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
		}
		if (user instanceof PlayerEntity && !((PlayerEntity)user).getAbilities().creativeMode) {
			stack.decrement(1);
		}
		if (!world.isClient) {
			BucketUtils.milkClearStatusEffects(world, user);
			user.clearStatusEffects();
		}
		cir.setReturnValue(stack.isEmpty() ? new ItemStack(Items.BUCKET) : stack);
	}
}
