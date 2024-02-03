package fun.wich.mixins.block;

import fun.wich.ModBase;
import net.minecraft.block.*;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MelonBlock.class)
public abstract class MelonBlockMixin extends GourdBlock {
	public MelonBlockMixin(Settings settings) { super(settings); }
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.getItem() instanceof ShearsItem) {
			if (!world.isClient) {
				Direction direction = hit.getSide();
				Direction direction2 = direction.getAxis() == Direction.Axis.Y ? player.getHorizontalFacing().getOpposite() : direction;
				world.playSound(null, pos, SoundEvents.BLOCK_PUMPKIN_CARVE, SoundCategory.BLOCKS, 1F, 1F);
				world.setBlockState(pos, ModBase.CARVED_MELON.asBlock().getDefaultState().with(CarvedPumpkinBlock.FACING, direction2), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
				ItemEntity itemEntity = new ItemEntity(world, pos.getX() + 0.5 + direction2.getOffsetX() * 0.65, pos.getY() + 0.1D, pos.getZ() + 0.5 + direction2.getOffsetZ() * 0.65, new ItemStack(Items.MELON_SEEDS, 4));
				itemEntity.setVelocity(0.05 * (double)direction2.getOffsetX() + world.random.nextDouble() * 0.02, 0.05, 0.05 * direction2.getOffsetZ() + world.random.nextDouble() * 0.02);
				world.spawnEntity(itemEntity);
				itemStack.damage(1, (LivingEntity)player, p -> p.sendToolBreakStatus(hand));
				world.emitGameEvent(player, GameEvent.SHEAR, pos);
				player.incrementStat(Stats.USED.getOrCreateStat(Items.SHEARS));
			}
			return ActionResult.success(world.isClient);
		}
		else return super.onUse(state, world, pos, player, hand, hit);
	}
}
