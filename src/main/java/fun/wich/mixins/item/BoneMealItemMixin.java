package fun.wich.mixins.item;

import fun.wich.ModBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
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
		BlockState state = world.getBlockState(pos);
		//Get more spore blossoms by using bone meal on a spore blossom
		if (state.isOf(Blocks.SPORE_BLOSSOM)) {
			world.spawnEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, new ItemStack(Items.SPORE_BLOSSOM, 1)));
			world.syncWorldEvent(WorldEvents.BONE_MEAL_USED, pos, 0);
			if (context.getPlayer() != null && !context.getPlayer().getAbilities().creativeMode) context.getStack().decrement(1);
			cir.setReturnValue(ActionResult.SUCCESS);
		}
		//Try growing a tall allium flower
		else if (state.isOf(Blocks.ALLIUM)) {
			if (world.getBlockState(pos.up()).isAir()) {
				world.setBlockState(pos, ModBase.TALL_ALLIUM.asBlock().getDefaultState(), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
				world.setBlockState(pos.up(), ModBase.TALL_ALLIUM.asBlock().getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER), Block.NOTIFY_ALL);
				world.playSound(null, pos, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
				world.syncWorldEvent(WorldEvents.BONE_MEAL_USED, pos, 0);
				if (context.getPlayer() != null && !context.getPlayer().getAbilities().creativeMode) context.getStack().decrement(1);
				cir.setReturnValue(ActionResult.SUCCESS);
			}
		}
	}
}
