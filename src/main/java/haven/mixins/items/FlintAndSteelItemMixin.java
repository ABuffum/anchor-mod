package haven.mixins.items;

import haven.HavenMod;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

@Mixin(FlintAndSteelItem.class)
public class FlintAndSteelItemMixin {
	@Inject(method="useOnBlock", at = @At("HEAD"), cancellable = true)
	public void UseOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
		PlayerEntity playerEntity = context.getPlayer();
		World world = context.getWorld();
		BlockPos blockPos = context.getBlockPos();
		BlockState blockState = world.getBlockState(blockPos);
		Block block = blockState.getBlock();
		if (HavenMod.UNLIT_LANTERNS.containsValue(block)) {
			world.playSound(playerEntity, blockPos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
			BlockState outState = HavenMod.UNLIT_LANTERNS.inverse().get(block).getDefaultState().with(LanternBlock.HANGING, blockState.get(LanternBlock.HANGING)).with(LanternBlock.WATERLOGGED, blockState.get(LanternBlock.WATERLOGGED));
			world.setBlockState(blockPos, outState, Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
			world.emitGameEvent(playerEntity, GameEvent.BLOCK_PLACE, blockPos);
			ItemStack itemStack = context.getStack();
			if (playerEntity instanceof ServerPlayerEntity) {
				Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity)playerEntity, blockPos, itemStack);
				itemStack.damage(1, (LivingEntity)playerEntity, (Consumer)((p) -> {
					((LivingEntity)p).sendToolBreakStatus(context.getHand());
				}));
			}
			cir.setReturnValue(ActionResult.success(world.isClient()));
		}
	}
}
