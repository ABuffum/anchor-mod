package fun.mousewich.mixins.block;

import fun.mousewich.ModBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TntBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TntBlock.class)
public abstract class TntBlockMixin {
	@Shadow private static void primeTnt(World world, BlockPos pos, LivingEntity igniter) { }

	@Inject(method="onUse", at=@At("HEAD"), cancellable=true)
	public void UseLavaBottle(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(ModBase.LAVA_BOTTLE)) {
			primeTnt(world, pos, player);
			world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
			Item item = itemStack.getItem();
			if (!player.isCreative()) {
				itemStack.decrement(1);
				player.giveItemStack(new ItemStack(ModBase.LAVA_BOTTLE.getRecipeRemainder()));
			}
			player.incrementStat(Stats.USED.getOrCreateStat(item));
			cir.setReturnValue(ActionResult.success(world.isClient));
		}
	}
}
