package haven.containers;

import haven.containers.PottedBlockContainer;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.Material;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.sound.BlockSoundGroup;

public class FlowerContainer extends PottedBlockContainer {
	public static AbstractBlock.Settings Settings() {
		return AbstractBlock.Settings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS);
	}
	public static AbstractBlock.Settings TallSettings() {
		return AbstractBlock.Settings.of(Material.REPLACEABLE_PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS);
	}

	public FlowerContainer(StatusEffect effect, int effectDuration) {
		this(effect, effectDuration, Settings());
	}
	public FlowerContainer(StatusEffect effect, int effectDuration, AbstractBlock.Settings settings) {
		this(new FlowerBlock(effect, effectDuration, settings));
	}
	public FlowerContainer(FlowerBlock block) {
		super(block);
	}
}
