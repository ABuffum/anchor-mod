package fun.mousewich.block.basic;

import net.minecraft.block.FenceGateBlock;
import net.minecraft.sound.SoundEvent;

public class ModFenceGateBlock extends FenceGateBlock {
	public final SoundEvent openSound;
	public final SoundEvent closeSound;
	public ModFenceGateBlock(Settings settings, SoundEvent openSound, SoundEvent closeSound) {
		super(settings);
		this.openSound = openSound;
		this.closeSound = closeSound;
	}
}
