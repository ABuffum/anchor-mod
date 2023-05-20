package fun.mousewich.mixins.block;

import fun.mousewich.ModBase;
import fun.mousewich.sound.ModSoundEvents;
import net.minecraft.block.AbstractPressurePlateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PressurePlateBlock.class)
public abstract class PressurePlateBlockMixin extends AbstractPressurePlateBlock {
	protected PressurePlateBlockMixin(Settings settings) {
		super(settings);
	}

	@Inject(method="playPressSound", at = @At("HEAD"), cancellable = true)
	protected void PlayPressSound(WorldAccess world, BlockPos pos, CallbackInfo ci) {
		Block block = this;
		if (block == ModBase.BAMBOO_PRESSURE_PLATE.asBlock()) {
			world.playSound(null, pos, ModSoundEvents.BLOCK_BAMBOO_WOOD_PRESSURE_PLATE_CLICK_ON, SoundCategory.BLOCKS, 1, 1);
			ci.cancel();
		}
		else if (block == Blocks.CRIMSON_PRESSURE_PLATE || block == Blocks.WARPED_PRESSURE_PLATE) {
			world.playSound(null, pos, ModSoundEvents.BLOCK_NETHER_WOOD_PRESSURE_PLATE_CLICK_ON, SoundCategory.BLOCKS, 1, 1);
			ci.cancel();
		}
	}

	@Inject(method="playDepressSound", at = @At("HEAD"), cancellable = true)
	protected void PlayDepressSound(WorldAccess world, BlockPos pos, CallbackInfo ci) {
		Block block = this;
		if (block == ModBase.BAMBOO_PRESSURE_PLATE.asBlock()) {
			world.playSound(null, pos, ModSoundEvents.BLOCK_BAMBOO_WOOD_PRESSURE_PLATE_CLICK_OFF, SoundCategory.BLOCKS, 1, 1);
			ci.cancel();
		}
		else if (block == Blocks.CRIMSON_PRESSURE_PLATE || block == Blocks.WARPED_PRESSURE_PLATE) {
			world.playSound(null, pos, ModSoundEvents.BLOCK_NETHER_WOOD_PRESSURE_PLATE_CLICK_OFF, SoundCategory.BLOCKS, 1, 1);
			ci.cancel();
		}
	}
}
