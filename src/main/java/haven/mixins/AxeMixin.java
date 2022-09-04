package haven.mixins;

import com.google.common.collect.BiMap;
import haven.HavenMod;
import haven.util.OxidationScale;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoneycombItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;
import java.util.function.Consumer;

@Mixin(AxeItem.class)
public class AxeMixin {
	@Shadow protected static Map<Block, Block> STRIPPED_BLOCKS;

	@Inject(method = "<clinit>", at = @At("TAIL"))
	private static void AddStrippedBlocks(CallbackInfo ci) {
		STRIPPED_BLOCKS = new HashMap<Block, Block>(STRIPPED_BLOCKS);
		//Add our own stripped blocks
		STRIPPED_BLOCKS.putAll(HavenMod.STRIPPED_BLOCKS);
	}

	@Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
	private void DropCinnamon(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
		World world = context.getWorld();
		BlockPos blockPos = context.getBlockPos();
		Block block = world.getBlockState(blockPos).getBlock();
		//Strip cassia logs for cinnamon
		boolean optional1 = false;
		if (block == HavenMod.CASSIA.LOG.BLOCK) {
			world.spawnEntity(new ItemEntity(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), new ItemStack(HavenMod.CINNAMON, 4)));
			optional1 = true;
		}
		else if (block == HavenMod.CASSIA.WOOD.BLOCK) {
			world.spawnEntity(new ItemEntity(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), new ItemStack(HavenMod.CINNAMON, 6)));
			optional1 = true;
		}
		if (!optional1) {
			BlockState blockState = world.getBlockState(blockPos);
			PlayerEntity playerEntity = context.getPlayer();
			ItemStack itemStack = context.getStack();
			//Axe Scrape
			Optional<BlockState> optional2 = OxidationScale.getDecreasedOxidationState(blockState);
			//Wax Off
			Optional<BlockState> optional3 = Optional.ofNullable((Block)((BiMap)OxidationScale.WaxedToUnwaxedBlocks().get()).get(blockState.getBlock())).map((b) -> {
				return b.getStateWithProperties(blockState);
			});
			Optional<BlockState> optional4 = Optional.empty();
			//Axe Scrape
			if (optional2.isPresent()) {
				world.playSound(playerEntity, blockPos, SoundEvents.ITEM_AXE_SCRAPE, SoundCategory.BLOCKS, 1.0F, 1.0F);
				world.syncWorldEvent(playerEntity, WorldEvents.BLOCK_SCRAPED, blockPos, 0);
				optional4 = optional2;
			}
			//Wax Off
			else if (optional3.isPresent()) {
				world.playSound(playerEntity, blockPos, SoundEvents.ITEM_AXE_WAX_OFF, SoundCategory.BLOCKS, 1.0F, 1.0F);
				world.syncWorldEvent(playerEntity, WorldEvents.WAX_REMOVED, blockPos, 0);
				optional4 = optional3;
			}
			if (optional4.isPresent()) {
				if (playerEntity instanceof ServerPlayerEntity) {
					Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity)playerEntity, blockPos, itemStack);
				}
				world.setBlockState(blockPos, (BlockState)optional4.get(), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
				if (playerEntity != null) {
					itemStack.damage(1, (LivingEntity)playerEntity, (Consumer)((p) -> {
						((LivingEntity)p).sendToolBreakStatus(context.getHand());
					}));
				}
				cir.setReturnValue(ActionResult.success(world.isClient));
			}
		}
	}

	@Shadow private Optional<BlockState> getStrippedState(BlockState state) { return null; }
}
