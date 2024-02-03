package fun.wich.block.basic;

import net.minecraft.block.DoorBlock;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class ModDoorBlock extends DoorBlock {
	public final SoundEvent openSound;
	public final SoundEvent closeSound;
	public ModDoorBlock(Settings settings) {
		this(settings, SoundEvents.BLOCK_WOODEN_DOOR_OPEN, SoundEvents.BLOCK_WOODEN_DOOR_CLOSE);
	}
	public ModDoorBlock(Settings settings, SoundEvent openSound, SoundEvent closeSound) {
		super(settings);
		this.openSound = openSound;
		this.closeSound = closeSound;
	}
}
