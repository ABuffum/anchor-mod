package fun.mousewich.block;

import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class MetalButtonBlock extends AbstractButtonBlock {
	public MetalButtonBlock(Settings settings) { super(false, settings); }
	@Override
	protected SoundEvent getClickSound(boolean powered) {
		return powered ? SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON : SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_OFF;
	}
}
