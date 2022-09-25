package haven.blocks.gourd;

import net.minecraft.block.*;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class CarvableGourdBlock extends HavenGourdBlock {
	private final SoundEvent carveSound;
	public SoundEvent getCarveSound() { return carveSound; }
	private final Supplier<CarvedGourdBlock> carved;
	public Block getCarvedBlock() { return carved.get(); }
	private final Supplier<Item> carveDrop;
	public Item getCarveDrop() { return carveDrop.get(); }

	public CarvableGourdBlock(Settings settings, SoundEvent carveSound, Supplier<StemBlock> stem, Supplier<AttachedStemBlock> attachedStem, Supplier<CarvedGourdBlock> carved, Supplier<Item> dropOnCarve) {
		super(settings, stem, attachedStem);
		this.carveSound = carveSound;
		this.carved = carved;
		carveDrop = dropOnCarve;
	}

	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(Items.SHEARS)) {
			if (!world.isClient) {
				Direction direction = hit.getSide();
				Direction direction2 = direction.getAxis() == Direction.Axis.Y ? player.getHorizontalFacing().getOpposite() : direction;
				world.playSound(null, pos, getCarveSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
				world.setBlockState(pos, getCarvedBlock().getDefaultState().with(HorizontalFacingBlock.FACING, direction2), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
				if (carveDrop != null) {
					ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5D + (double)direction2.getOffsetX() * 0.65D, (double)pos.getY() + 0.1D, (double)pos.getZ() + 0.5D + (double)direction2.getOffsetZ() * 0.65D, new ItemStack(getCarveDrop(), 4));
					itemEntity.setVelocity(0.05D * (double)direction2.getOffsetX() + world.random.nextDouble() * 0.02D, 0.05D, 0.05D * (double)direction2.getOffsetZ() + world.random.nextDouble() * 0.02D);
					world.spawnEntity(itemEntity);
				}
				itemStack.damage(1, (LivingEntity)player, (Consumer)((playerx) -> ((LivingEntity)playerx).sendToolBreakStatus(hand)));
				world.emitGameEvent(player, GameEvent.SHEAR, pos);
				player.incrementStat(Stats.USED.getOrCreateStat(Items.SHEARS));
			}
			return ActionResult.success(world.isClient);
		}
		else return super.onUse(state, world, pos, player, hand, hit);
	}
}
