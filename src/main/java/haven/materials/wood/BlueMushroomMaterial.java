package haven.materials.wood;

import haven.HavenMod;
import haven.containers.*;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.*;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;

public class BlueMushroomMaterial extends MushroomMaterial implements Provider, PottedProvider, BlockProvider {
	private BlockContainer provider;
	public BlockContainer get() { return provider; }
	private PottedBlockContainer potted;
	public PottedBlockContainer getPotted() { return potted; }
	private BlockContainer block;
	public BlockContainer getBlock() { return block; }

	public BlueMushroomMaterial(String name, MapColor mapColor, boolean flammable) {
		super(name, mapColor, flammable);
		provider = new BlockContainer(new MushroomPlantBlock(AbstractBlock.Settings.of(Material.PLANT, mapColor).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS).postProcess(BaseMaterial::always), () -> HavenMod.HUGE_BLUE_MUSHROOM));
		potted = new PottedBlockContainer(provider.BLOCK);
		block = new BlockContainer(new MushroomBlock(AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(0.2F).sounds(BlockSoundGroup.WOOD)));
	}
}
