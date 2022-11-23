package haven.blocks.basic;

import net.minecraft.block.WoodenButtonBlock;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class HavenWoodenButtonBlock extends WoodenButtonBlock {
	private final SoundEvent clickOnSound;
	private final SoundEvent clickOffSound;
	public HavenWoodenButtonBlock(Settings settings) {
		this(settings, SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_ON, SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_OFF);
	}
	public HavenWoodenButtonBlock(Settings settings, SoundEvent clickOffSound, SoundEvent clickOnSound) {
		super(settings);
		this.clickOnSound = clickOnSound;
		this.clickOffSound = clickOffSound;
	}

	protected SoundEvent getClickSound(boolean powered) { return powered ? clickOnSound : clickOffSound; }
}
