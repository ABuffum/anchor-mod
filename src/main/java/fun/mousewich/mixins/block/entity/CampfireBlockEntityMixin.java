package fun.mousewich.mixins.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Clearable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CampfireBlockEntity.class)
public abstract class CampfireBlockEntityMixin extends BlockEntity implements Clearable {
	public CampfireBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) { super(type, pos, state); }

	@Inject(method="litServerTick", at = @At(value="INVOKE", shift=At.Shift.AFTER, target="Lnet/minecraft/world/World;updateListeners(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/block/BlockState;I)V"))
	private static void InjectBlockChangeAfterLitServerTicks(World world, BlockPos pos, BlockState state, CampfireBlockEntity campfire, CallbackInfo ci) {
		world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos);
	}
	@Inject(method="addItem", at = @At(value="INVOKE", shift=At.Shift.BEFORE, target="Lnet/minecraft/block/entity/CampfireBlockEntity;updateListeners()V"))
	private void InjectBlockChangeWhenItemAdded(ItemStack item, int cookTime, CallbackInfoReturnable<Boolean> cir) {
		if (this.world != null) this.world.emitGameEvent(GameEvent.BLOCK_CHANGE, this.getPos());
	}
}
