package fun.wich.mixins.block;

import fun.wich.block.JukeboxBlockExtension;
import fun.wich.event.ModGameEvent;
import fun.wich.util.JukeboxUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(JukeboxBlock.class)
public abstract class JukeboxBlockMixin extends BlockWithEntity {
	protected JukeboxBlockMixin(Settings settings) { super(settings); }

	@Inject(method="onUse", at=@At(value="INVOKE",shift=At.Shift.BEFORE,target="Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"))
	private void InjectJukeboxStopPlay(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
		world.emitGameEvent(ModGameEvent.JUKEBOX_STOP_PLAY, pos);
	}
	@Inject(method="onUse", at=@At(value="INVOKE", shift=At.Shift.AFTER, target="Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"))
	private void InjectBlockChangeOnUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
		world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
	}
	@Inject(method="setRecord", at=@At(value="INVOKE", shift=At.Shift.AFTER, target="Lnet/minecraft/world/WorldAccess;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"))
	private void InjectBlockChangeAfterSetRecord(WorldAccess world, BlockPos pos, BlockState state, ItemStack stack, CallbackInfo ci) {
		world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos);
	}

	@Inject(method="setRecord", at=@At(value="INVOKE", shift=At.Shift.AFTER, target="Lnet/minecraft/block/entity/JukeboxBlockEntity;setRecord(Lnet/minecraft/item/ItemStack;)V"))
	private void StartPlaying(WorldAccess world, BlockPos pos, BlockState state, ItemStack stack, CallbackInfo ci) {
		if (world.getBlockEntity(pos) instanceof JukeboxBlockExtension jukebox) jukebox.startPlaying();
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		if (state.get(JukeboxBlock.HAS_RECORD)) return JukeboxBlock.checkType(type, BlockEntityType.JUKEBOX, JukeboxUtil::tick);
		return null;
	}

}
