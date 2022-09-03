package haven.util;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.Material;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.sound.BlockSoundGroup;

public class HavenFlower extends PottedBlock {

	public static final AbstractBlock.Settings SETTINGS = AbstractBlock.Settings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS);
	public static final AbstractBlock.Settings TALL_SETTINGS = AbstractBlock.Settings.of(Material.REPLACEABLE_PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS);

	public HavenFlower(StatusEffect effect, int effectDuration) {
		this(effect, effectDuration, SETTINGS);
	}
	public HavenFlower(StatusEffect effect, int effectDuration, AbstractBlock.Settings settings) {
		super(new FlowerBlock(effect, effectDuration, settings));
	}
}
