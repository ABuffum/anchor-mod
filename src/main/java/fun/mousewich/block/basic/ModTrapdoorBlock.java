package fun.mousewich.block.basic;

import net.minecraft.block.Block;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.shape.VoxelShape;

public class ModTrapdoorBlock extends TrapdoorBlock {
	public final SoundEvent openSound;
	public final SoundEvent closeSound;
	public ModTrapdoorBlock(Settings settings) {
		this(settings, SoundEvents.BLOCK_WOODEN_DOOR_OPEN, SoundEvents.BLOCK_WOODEN_DOOR_CLOSE);
	}
	public ModTrapdoorBlock(Settings settings, SoundEvent openSound, SoundEvent closeSound) {
		super(settings);
		this.openSound = openSound;
		this.closeSound = closeSound;
	}
}
