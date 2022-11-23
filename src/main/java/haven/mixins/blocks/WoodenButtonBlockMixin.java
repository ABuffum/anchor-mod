package haven.mixins.blocks;

import haven.ModBase;
import haven.sounds.ModSoundEvents;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.WoodenButtonBlock;
import net.minecraft.sound.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WoodenButtonBlock.class)
public abstract class WoodenButtonBlockMixin extends AbstractButtonBlock {
	protected WoodenButtonBlockMixin(boolean wooden, Settings settings) {
		super(wooden, settings);
	}

	@Inject(method="getClickSound", at = @At("HEAD"), cancellable = true)
	protected void getClickSound(boolean powered, CallbackInfoReturnable<SoundEvent> cir) {
		Block block = this;
		if (block == ModBase.VANILLA_BAMBOO_MATERIAL.getButton().getBlock() || block == ModBase.BAMBOO_MATERIAL.getButton().getBlock() || block == ModBase.DRIED_BAMBOO_MATERIAL.getButton().getBlock()) {
			cir.setReturnValue(powered ? ModSoundEvents.BLOCK_BAMBOO_WOOD_BUTTON_CLICK_ON : ModSoundEvents.BLOCK_BAMBOO_WOOD_BUTTON_CLICK_OFF);
		}
		else if (block == Blocks.CRIMSON_BUTTON || block == Blocks.WARPED_BUTTON || block == ModBase.GILDED_MATERIAL.getButton().getBlock()) {
			cir.setReturnValue(powered ? ModSoundEvents.BLOCK_NETHER_WOOD_BUTTON_CLICK_ON : ModSoundEvents.BLOCK_NETHER_WOOD_BUTTON_CLICK_OFF);
		}
	}
}
