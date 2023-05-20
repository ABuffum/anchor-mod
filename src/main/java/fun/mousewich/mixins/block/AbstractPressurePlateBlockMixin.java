package fun.mousewich.mixins.block;

import fun.mousewich.event.ModGameEvent;
import net.minecraft.block.AbstractPressurePlateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractPressurePlateBlock.class)
public abstract class AbstractPressurePlateBlockMixin extends Block {

	public AbstractPressurePlateBlockMixin(Settings settings) { super(settings); }

	@Shadow
	protected abstract int getRedstoneOutput(World var1, BlockPos var2);

	@Inject(method = "updatePlateState", at = @At("TAIL"))
	protected void updatePlateState(Entity entity, World world, BlockPos pos, BlockState state, int output, CallbackInfo ci) {
		boolean bl = output > 0;
		boolean bl2 = this.getRedstoneOutput(world, pos) > 0;
		if (!bl2 && bl) world.emitGameEvent(entity, ModGameEvent.BLOCK_DEACTIVATE, pos);
		else if (bl2 && !bl) world.emitGameEvent(entity, ModGameEvent.BLOCK_ACTIVATE, pos);
	}
}
