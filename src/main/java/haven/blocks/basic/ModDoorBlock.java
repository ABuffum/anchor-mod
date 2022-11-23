package haven.blocks.basic;

import net.minecraft.block.DoorBlock;
import net.minecraft.sound.SoundEvent;

public class ModDoorBlock extends DoorBlock {
	public final SoundEvent openSound;
	public final SoundEvent closeSound;
	public ModDoorBlock(Settings settings, SoundEvent openSound, SoundEvent closeSound) {
		super(settings);
		this.openSound = openSound;
		this.closeSound = closeSound;
	}
}
