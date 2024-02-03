package fun.wich.mixins.item;

import fun.wich.event.ModGameEvent;
import fun.wich.util.WaterBottleUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.PotionItem;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PotionItem.class)
public class PotionItemMixin extends Item {
	public PotionItemMixin(Settings settings) { super(settings); }

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) { return WaterBottleUtil.useOnBlock(context); }

	@Inject(method="finishUsing", at = @At("TAIL"))
	public void finishUsing(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
		PlayerEntity playerEntity = user instanceof PlayerEntity ? (PlayerEntity)user : null;
		if (playerEntity == null || !playerEntity.getAbilities().creativeMode && stack.isEmpty()) return;
		user.emitGameEvent(ModGameEvent.DRINK);
	}
}
