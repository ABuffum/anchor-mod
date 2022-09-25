package haven.materials.wood;

import haven.blocks.PropaguleBlock;
import haven.containers.PottedBlockContainer;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;

public class MangroveMaterial extends BaseTreeMaterial {
	public final PottedBlockContainer PROPAGULE;

	public MangroveMaterial(String name, MapColor mapColor) {
		this(name, mapColor, true);
	}
	public MangroveMaterial(String name, MapColor mapColor, boolean isFlammable) {
		this(name, mapColor, BlockSoundGroup.GRASS, isFlammable);
	}
	public MangroveMaterial(String name, MapColor mapColor, BlockSoundGroup leafSounds) {
		this(name, mapColor, leafSounds, true);
	}
	public MangroveMaterial(String name, MapColor mapColor, BlockSoundGroup leafSounds, boolean isFlammable) {
		super(name, mapColor, leafSounds, isFlammable);
		PROPAGULE = new PottedBlockContainer(new PropaguleBlock(AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)));
	}

	public boolean contains(Block block) {
		return PROPAGULE.contains(block) || super.contains(block);
	}
	public boolean contains(Item item) {
		return PROPAGULE.contains(item) || super.contains(item);
	}
}
