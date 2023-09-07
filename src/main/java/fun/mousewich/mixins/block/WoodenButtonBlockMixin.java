package fun.mousewich.mixins.block;

import fun.mousewich.ModBase;
import fun.mousewich.registry.ModBambooRegistry;
import fun.mousewich.sound.ModSoundEvents;
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
	protected WoodenButtonBlockMixin(boolean wooden, Settings settings) { super(wooden, settings); }

	@Inject(method="getClickSound", at = @At("HEAD"), cancellable = true)
	protected void GetClickSound(boolean powered, CallbackInfoReturnable<SoundEvent> cir) {
		Block block = this;
		if (block == ModBambooRegistry.BAMBOO_BUTTON.asBlock() || block == ModBambooRegistry.DRIED_BAMBOO_BUTTON.asBlock()) {
			cir.setReturnValue(powered ? ModSoundEvents.BLOCK_BAMBOO_WOOD_BUTTON_CLICK_ON : ModSoundEvents.BLOCK_BAMBOO_WOOD_BUTTON_CLICK_OFF);
		}
		else if (block == Blocks.CRIMSON_BUTTON || block == Blocks.WARPED_BUTTON) {
			cir.setReturnValue(powered ? ModSoundEvents.BLOCK_NETHER_WOOD_BUTTON_CLICK_ON : ModSoundEvents.BLOCK_NETHER_WOOD_BUTTON_CLICK_OFF);
		}
	}
}
