package fun.wich.block.dust;

import fun.wich.sound.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.sound.SoundEvent;

public interface Brushable {
	BlockState getBrushedState(BlockState state);
	default SoundEvent getBrushSound() { return ModSoundEvents.ITEM_BRUSH_BRUSHING_GENERIC; }
	SoundEvent getBrushCompleteSound();
}
