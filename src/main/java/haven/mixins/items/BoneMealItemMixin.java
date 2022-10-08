package haven.mixins.items;

import haven.HavenMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoneMealItem.class)
public class BoneMealItemMixin {
	@Inject(method="useOnBlock", at = @At("HEAD"), cancellable = true)
	public void UseOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
		World world = context.getWorld();
		BlockPos pos = context.getBlockPos();
		//Get more spore blossoms by bone mealing a spore blossom
		if (world.getBlockState(pos).isOf(Blocks.SPORE_BLOSSOM)) {
			ItemStack stack = new ItemStack(Items.SPORE_BLOSSOM, 1);
			ItemEntity itemEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, stack);
			world.spawnEntity(itemEntity);
			world.syncWorldEvent(WorldEvents.BONE_MEAL_USED, pos, 0);
			if (!context.getPlayer().getAbilities().creativeMode) context.getStack().decrement(1);
			cir.setReturnValue(ActionResult.SUCCESS);
		}
	}
}
