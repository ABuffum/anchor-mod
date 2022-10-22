package haven.containers;

import haven.HavenMod;
import haven.containers.PottedBlockContainer;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.Material;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;

public class FlowerContainer extends PottedBlockContainer {
	public static AbstractBlock.Settings Settings() {
		return AbstractBlock.Settings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS);
	}
	public static AbstractBlock.Settings TallSettings() {
		return AbstractBlock.Settings.of(Material.REPLACEABLE_PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS);
	}

	public static Item.Settings SeedSettings() { return HavenMod.ItemSettings().group(HavenMod.FLOWER_GROUP); }
	public static Item.Settings PetalSettings() { return HavenMod.ItemSettings().group(HavenMod.FLOWER_GROUP); }

	public final Item PETALS;
	public final FlowerSeedContainer SEEDS;

	public FlowerContainer(StatusEffect effect, int effectDuration) {
		this(effect, effectDuration, Settings());
	}
	public FlowerContainer(StatusEffect effect, int effectDuration, AbstractBlock.Settings settings) {
		this(new FlowerBlock(effect, effectDuration, settings), PetalSettings());
	}
	public FlowerContainer(FlowerBlock block) { this(block, PetalSettings()); }
	public FlowerContainer(FlowerBlock block, Item.Settings petalSettings) {
		super(block, HavenMod.ItemSettings().group(HavenMod.FLOWER_GROUP));
		this.PETALS = new Item(petalSettings);
		this.SEEDS = new FlowerSeedContainer(block);
	}
}
