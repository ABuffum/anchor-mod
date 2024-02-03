package fun.wich.mixins.block;

import fun.wich.ModBase;
import fun.wich.block.basic.ModCandleCakeBlock;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CandleCakeBlock.class)
public class CandleCakeBlockMixin {
	@Inject(method="getCandleCakeFromCandle", at = @At("HEAD"), cancellable = true)
	private static void GetCandleCakeFromCandle(Block candle, CallbackInfoReturnable<BlockState> cir) {
		if (candle == ModBase.SOUL_CANDLE.asBlock()) cir.setReturnValue(ModBase.SOUL_CANDLE_CAKE.getDefaultState());
		else if (candle == ModBase.ENDER_CANDLE.asBlock()) cir.setReturnValue(ModBase.ENDER_CANDLE_CAKE.getDefaultState());
		else if (candle == ModBase.NETHERRACK_CANDLE.asBlock()) cir.setReturnValue(ModBase.NETHERRACK_CANDLE_CAKE.getDefaultState());
		else if (candle == ModBase.BEIGE_CANDLE.asBlock()) cir.setReturnValue(ModBase.BEIGE_CANDLE_CAKE.getDefaultState());
		else if (candle == ModBase.BURGUNDY_CANDLE.asBlock()) cir.setReturnValue(ModBase.BURGUNDY_CANDLE_CAKE.getDefaultState());
		else if (candle == ModBase.LAVENDER_CANDLE.asBlock()) cir.setReturnValue(ModBase.LAVENDER_CANDLE_CAKE.getDefaultState());
		else if (candle == ModBase.MINT_CANDLE.asBlock()) cir.setReturnValue(ModBase.MINT_CANDLE_CAKE.getDefaultState());
	}

	@Inject(method="canBeLit", at = @At("HEAD"), cancellable = true)
	private static void CanBeLit(BlockState state, CallbackInfoReturnable<Boolean> cir) {
		Block block = state.getBlock();
		if (block instanceof ModCandleCakeBlock) {
			cir.setReturnValue(state.contains(ModCandleCakeBlock.LIT) && !state.get(ModCandleCakeBlock.LIT));
		}
	}

	@Inject(method="onUse", at=@At("HEAD"), cancellable=true)
	public void IgnoreLavaBottle(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(ModBase.LAVA_BOTTLE)) cir.setReturnValue(ActionResult.PASS);
	}
}
