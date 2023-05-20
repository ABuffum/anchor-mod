package fun.mousewich.block.basic;

import net.minecraft.block.WoodenButtonBlock;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class ModWoodenButtonBlock extends WoodenButtonBlock {
	private final SoundEvent clickOnSound;
	private final SoundEvent clickOffSound;
	public ModWoodenButtonBlock(Settings settings) {
		this(settings, SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_ON, SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_OFF);
	}
	public ModWoodenButtonBlock(Settings settings, SoundEvent clickOffSound, SoundEvent clickOnSound) {
		super(settings);
		this.clickOnSound = clickOnSound;
		this.clickOffSound = clickOffSound;
	}
	protected SoundEvent getClickSound(boolean powered) { return powered ? clickOnSound : clickOffSound; }
}
