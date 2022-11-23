package haven.blocks.basic;

import net.minecraft.block.Material;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;

public class HavenPressurePlateBlock extends PressurePlateBlock {
	private final SoundEvent clickOnSound;
	private final SoundEvent clickOffSound;
	public static HavenPressurePlateBlock Wooden(ActivationRule type, Settings settings) {
		return new HavenPressurePlateBlock(type, settings, SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_ON, SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_OFF);
	}
	public static HavenPressurePlateBlock Stone(ActivationRule type, Settings settings) {
		return new HavenPressurePlateBlock(type, settings, SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_ON, SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_OFF);
	}
	public HavenPressurePlateBlock(ActivationRule type, Settings settings, SoundEvent clickOnSound, SoundEvent clickOffSound) {
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
