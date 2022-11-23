package haven.mixins.blocks;

import haven.ModBase;
import haven.sounds.ModSoundEvents;
import net.minecraft.block.*;
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
		if (block == ModBase.VANILLA_BAMBOO_MATERIAL.getPressurePlate().getBlock() || block == ModBase.BAMBOO_MATERIAL.getPressurePlate().getBlock() || block == ModBase.DRIED_BAMBOO_MATERIAL.getPressurePlate().getBlock()) {
			world.playSound(null, pos, ModSoundEvents.BLOCK_BAMBOO_WOOD_PRESSURE_PLATE_CLICK_ON, SoundCategory.BLOCKS, 1, 1);
			ci.cancel();
		}
		else if (block == Blocks.CRIMSON_PRESSURE_PLATE || block == Blocks.WARPED_PRESSURE_PLATE || block == ModBase.GILDED_MATERIAL.getPressurePlate().getBlock()) {
			world.playSound(null, pos, ModSoundEvents.BLOCK_NETHER_WOOD_PRESSURE_PLATE_CLICK_ON, SoundCategory.BLOCKS, 1, 1);
			ci.cancel();
		}
	}

	@Inject(method="playDepressSound", at = @At("HEAD"), cancellable = true)
	protected void PlayDepressSound(WorldAccess world, BlockPos pos, CallbackInfo ci) {
		Block block = this;
		if (block == ModBase.VANILLA_BAMBOO_MATERIAL.getPressurePlate().getBlock() || block == ModBase.BAMBOO_MATERIAL.getPressurePlate().getBlock() || block == ModBase.DRIED_BAMBOO_MATERIAL.getPressurePlate().getBlock()) {
			world.playSound(null, pos, ModSoundEvents.BLOCK_BAMBOO_WOOD_PRESSURE_PLATE_CLICK_OFF, SoundCategory.BLOCKS, 1, 1);
			ci.cancel();
		}
		else if (block == Blocks.CRIMSON_PRESSURE_PLATE || block == Blocks.WARPED_PRESSURE_PLATE || block == ModBase.GILDED_MATERIAL.getPressurePlate().getBlock()) {
			world.playSound(null, pos, ModSoundEvents.BLOCK_NETHER_WOOD_PRESSURE_PLATE_CLICK_OFF, SoundCategory.BLOCKS, 1, 1);
			ci.cancel();
		}
	}
}
