package haven.blocks;

import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MetalButtonBlock extends AbstractButtonBlock {
	public MetalButtonBlock(Settings settings) {
		super(false, settings);
	}
	@Override
	protected SoundEvent getClickSound(boolean powered) {
		return powered ? SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON : SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_OFF;
	}
}
