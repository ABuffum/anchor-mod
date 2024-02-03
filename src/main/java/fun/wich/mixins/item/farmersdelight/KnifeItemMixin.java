package fun.wich.mixins.item.farmersdelight;

import com.nhoryzon.mc.farmersdelight.item.KnifeItem;
import fun.wich.block.gourd.CarvableGourdBlock;
import fun.wich.ModBase;
import fun.wich.gen.data.tag.ModItemTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KnifeItem.class)
public abstract class KnifeItemMixin {
	@Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
	public void UseOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
		World world = context.getWorld();
		ItemStack tool = context.getStack();
		if (tool.isIn(ModItemTags.KNIVES)) {
			BlockPos pos = context.getBlockPos();
			BlockState state = world.getBlockState(pos);
			BlockState outState = null;
			SoundEvent carveSound = SoundEvents.BLOCK_PUMPKIN_CARVE;
			ItemStack dropStack = ItemStack.EMPTY;
			if (state.getBlock() instanceof CarvableGourdBlock gourd) {
				carveSound = gourd.getCarveSound();
				outState = gourd.getCarvedBlock().getDefaultState();
				dropStack = gourd.getCarveDrop();
			}
			else if (state.getBlock() == Blocks.MELON) {
				outState = ModBase.CARVED_MELON.asBlock().getDefaultState();
				dropStack = new ItemStack(Items.MELON_SEEDS, 4);
			}
			if (outState != null) {
				PlayerEntity player = context.getPlayer();
				if (player != null && !world.isClient()) {
					Direction facing = context.getSide();
					Direction direction = facing.getAxis() == Direction.Axis.Y ? player.getHorizontalFacing().getOpposite() : facing;
					world.playSound(null, pos, carveSound, SoundCategory.BLOCKS, 1.0F, 1.0F);
					world.setBlockState(pos, outState.with(CarvedPumpkinBlock.FACING, direction), 11);
					ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5 + (double)direction.getOffsetX() * 0.65, (double)pos.getY() + 0.1, (double)pos.getZ() + 0.5 + (double)direction.getOffsetZ() * 0.65, dropStack);
					itemEntity.setVelocity(0.05 * (double)direction.getOffsetX() + world.getRandom().nextDouble() * 0.02, 0.05, 0.05 * (double)direction.getOffsetZ() + world.getRandom().nextDouble() * 0.02);
					world.spawnEntity(itemEntity);
					tool.damage(1, player, (playerIn) -> playerIn.sendToolBreakStatus(context.getHand()));
				}
				cir.setReturnValue(ActionResult.success(world.isClient()));
			}
		}
	}
}
