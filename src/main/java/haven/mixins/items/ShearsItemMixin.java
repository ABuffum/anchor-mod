package haven.mixins.items;

import haven.ModBase;
import haven.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallFlowerBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

@Mixin(ShearsItem.class)
public abstract class ShearsItemMixin extends Item {
	public ShearsItemMixin(Settings settings) { super(settings); }

	@Inject(method="getMiningSpeedMultiplier", at = @At("HEAD"), cancellable = true)
	public void GetMiningSpeedMultiplier(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> cir) {
		if (state.isIn(ModTags.Blocks.FLEECE)) cir.setReturnValue(5.0F);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos pos = context.getBlockPos();
		BlockState state = world.getBlockState(pos);
		if (state.isOf(Blocks.ROSE_BUSH)) {
			boolean upper = state.get(TallFlowerBlock.HALF) == DoubleBlockHalf.UPPER;
			if (upper) {
				world.setBlockState(pos.down(), ModBase.ROSE.getBlock().getDefaultState(), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
				world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL);
			}
			else {
				world.setBlockState(pos, ModBase.ROSE.getBlock().getDefaultState(), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
				world.setBlockState(pos.up(), Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL);
			}
			world.spawnEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, new ItemStack(ModBase.ROSE.getItem(), 1)));
			PlayerEntity player = context.getPlayer();
			context.getStack().damage(1, (LivingEntity)player, (Consumer)((playerx) -> ((LivingEntity)playerx).sendToolBreakStatus(context.getHand())));
			world.emitGameEvent(player, GameEvent.SHEAR, pos);
			player.incrementStat(Stats.USED.getOrCreateStat(Items.SHEARS));
			return ActionResult.success(world.isClient);
		}
		return super.useOnBlock(context);
	}
}
