package fun.mousewich.block;

import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

import java.util.function.Supplier;

public class GrowableFlowerBlock extends FlowerBlock {
	private final Supplier<TallFlowerBlock> tallBlock;
	public TallFlowerBlock getTallBlock() { return tallBlock.get(); }

	public GrowableFlowerBlock(StatusEffect suspiciousStewEffect, int effectDuration, Settings settings, Supplier<TallFlowerBlock> tallBlock) {
		super(suspiciousStewEffect, effectDuration, settings);
		this.tallBlock = tallBlock;
	}

	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(Items.BONE_MEAL)) {
			if (world.getBlockState(pos.up()).isAir()) {
				world.setBlockState(pos, getTallBlock().getDefaultState(), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
				world.setBlockState(pos.up(), getTallBlock().getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER), Block.NOTIFY_ALL);
				world.playSound(null, pos, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
				if (!world.isClient) world.syncWorldEvent(WorldEvents.BONE_MEAL_USED, pos, 0);
				return ActionResult.success(world.isClient);
			}
		}
		return super.onUse(state, world, pos, player, hand, hit);
	}
}