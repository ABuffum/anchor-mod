package fun.wich.block.basic;

import net.minecraft.block.PressurePlateBlock;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;

public class ModPressurePlateBlock extends PressurePlateBlock {
	private final SoundEvent clickOnSound;
	private final SoundEvent clickOffSound;
	public ModPressurePlateBlock(ActivationRule type, Settings settings, SoundEvent clickOnSound, SoundEvent clickOffSound) {
		super(type, settings);
		this.clickOnSound = clickOnSound;
		this.clickOffSound = clickOffSound;
	}
	protected void playPressSound(WorldAccess world, BlockPos pos) {
		world.playSound(null, pos, clickOnSound, SoundCategory.BLOCKS, 1, 1);
	}
	protected void playDepressSound(WorldAccess world, BlockPos pos) {
		world.playSound(null, pos, clickOffSound, SoundCategory.BLOCKS, 1, 1);
	}
}
