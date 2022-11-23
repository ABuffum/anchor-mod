package haven.blocks.basic;

import net.minecraft.block.FenceGateBlock;
import net.minecraft.sound.SoundEvent;

public class HavenFenceGateBlock extends FenceGateBlock {
	public final SoundEvent openSound;
	public final SoundEvent closeSound;
	public HavenFenceGateBlock(Settings settings, SoundEvent openSound, SoundEvent closeSound) {
		super(settings);
		this.openSound = openSound;
		this.closeSound = closeSound;
	}
}
