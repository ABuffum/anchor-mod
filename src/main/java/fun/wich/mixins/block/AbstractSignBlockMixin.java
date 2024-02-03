package fun.wich.mixins.block;

import fun.wich.item.basic.ModDyeItem;
import fun.wich.util.dye.ModDyedSign;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractSignBlock.class)
public abstract class AbstractSignBlockMixin extends BlockWithEntity implements Waterloggable {
	protected AbstractSignBlockMixin(Settings settings) { super(settings); }

	@Inject(method="onUse", at=@At("HEAD"), cancellable=true)
	private void UseModColor(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
		ItemStack itemStack = player.getStackInHand(hand);
		Item item = itemStack.getItem();
		if (!(item instanceof ModDyeItem dye)) return;
		boolean bl4 = player.getAbilities().allowModifyWorld;
		if (world.isClient) {
			cir.setReturnValue(bl4 ? ActionResult.SUCCESS : ActionResult.CONSUME);
			return;
		}
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof SignBlockEntity signBlockEntity && signBlockEntity instanceof ModDyedSign dyed) {
			if (bl4) {
				world.playSound(null, pos, SoundEvents.ITEM_DYE_USE, SoundCategory.BLOCKS, 1.0f, 1.0f);
				boolean bl6 = dyed.SetModTextColor(dye.getColor());
				if (bl6) {
					if (!player.isCreative()) {
						itemStack.decrement(1);
					}
					player.incrementStat(Stats.USED.getOrCreateStat(item));
				}
			}
			cir.setReturnValue(signBlockEntity.onActivate((ServerPlayerEntity)player) ? ActionResult.SUCCESS : ActionResult.PASS);
			return;
		}
		cir.setReturnValue(ActionResult.PASS);
	}
}
