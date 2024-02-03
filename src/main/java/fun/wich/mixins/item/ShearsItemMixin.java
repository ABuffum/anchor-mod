package fun.wich.mixins.item;

import fun.wich.ModBase;
import fun.wich.block.plushie.PlushieBlock;
import fun.wich.block.sculk.SculkTurfBlock;
import fun.wich.container.PottedBlockContainer;
import fun.wich.gen.data.tag.ModBlockTags;
import fun.wich.sound.ModSoundEvents;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShearsItem.class)
public class ShearsItemMixin extends Item {
	public ShearsItemMixin(Settings settings) { super(settings); }

	@Inject(method="getMiningSpeedMultiplier", at = @At("HEAD"), cancellable = true)
	public void GetMiningSpeedMultiplier(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> cir) {
		if (state.isIn(ModBlockTags.WOOL_SLABS) || state.isIn(ModBlockTags.WOOL_CARPETS)) cir.setReturnValue(5F);
		if (state.isIn(ModBlockTags.FLEECE) || state.isIn(ModBlockTags.FLEECE_SLABS) || state.isIn(ModBlockTags.FLEECE_CARPETS)) cir.setReturnValue(5F);
		if (state.getBlock() instanceof PlushieBlock) cir.setReturnValue(5F);
	}
	@Inject(method="isSuitableFor", at = @At("HEAD"), cancellable = true)
	public void IsSuitableFor(BlockState state, CallbackInfoReturnable<Boolean> cir) {
		if (state.isOf(ModBase.GUNPOWDER_FUSE.asBlock())) cir.setReturnValue(true);
	}
	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		PlayerEntity player = context.getPlayer();
		World world = context.getWorld();
		BlockPos pos = context.getBlockPos();
		BlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		boolean useUp = false;
		boolean first;
		if ((first = state.isOf(Blocks.ROSE_BUSH)) || state.isOf(Blocks.PEONY)) {
			BlockPos airPos = pos.up(), flowerPos = pos;
			if (state.get(TallFlowerBlock.HALF) == DoubleBlockHalf.UPPER) {
				flowerPos = pos.down();
				airPos = pos;
			}
			PottedBlockContainer flower = first ? ModBase.ROSE : ModBase.PAEONIA;
			world.setBlockState(flowerPos, flower.asBlock().getDefaultState(), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
			world.setBlockState(airPos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL);
			world.playSound(null, pos, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);
			world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
			world.spawnEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, new ItemStack(flower.asItem(), 1)));
			useUp = true;
		}
		else if (block instanceof SculkTurfBlock turf) {
			if (context.getSide() != Direction.DOWN) {
				world.setBlockState(pos, turf.getBlock().getDefaultState());
				world.playSound(null, pos, ModSoundEvents.BLOCK_SCULK_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);
				world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
				world.spawnEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, new ItemStack(ModBase.SCULK_VEIN, 1)));
				useUp = true;
			}
		}
		if (useUp) {
			if (player != null) {
				context.getStack().damage(1, player, p -> p.sendToolBreakStatus(context.getHand()));
				world.emitGameEvent(player, GameEvent.SHEAR, pos);
				player.incrementStat(Stats.USED.getOrCreateStat(Items.SHEARS));
			}
			return ActionResult.success(world.isClient);
		}
		if (block instanceof AbstractPlantStemBlock && !(state.get(AbstractPlantStemBlock.AGE) == AbstractPlantStemBlock.MAX_AGE)) {
			PlayerEntity playerEntity = context.getPlayer();
			ItemStack itemStack = context.getStack();
			if (playerEntity instanceof ServerPlayerEntity) Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity)playerEntity, pos, itemStack);
			world.playSound(playerEntity, pos, SoundEvents.BLOCK_CROP_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);
			world.setBlockState(pos, state.with(AbstractPlantStemBlock.AGE, AbstractPlantStemBlock.MAX_AGE));
			context.getWorld().emitGameEvent(context.getPlayer(), GameEvent.BLOCK_CHANGE, context.getBlockPos());
			if (playerEntity != null) itemStack.damage(1, playerEntity, p -> p.sendToolBreakStatus(context.getHand()));
			return ActionResult.success(world.isClient);
		}
		return super.useOnBlock(context);
	}
}
