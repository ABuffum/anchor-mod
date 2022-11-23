package haven.blocks.basic;

import net.minecraft.block.Block;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.sound.SoundEvent;

public class HavenTrapdoorBlock extends TrapdoorBlock {
	public final SoundEvent openSound;
	public final SoundEvent closeSound;
	public HavenTrapdoorBlock(Settings settings, SoundEvent openSound, SoundEvent closeSound) {
		super(settings);
		this.openSound = openSound;
		this.closeSound = closeSound;
	}
}
