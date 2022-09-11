package haven.materials;

import haven.blocks.PropaguleBlock;
import haven.util.PottedBlock;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;

public class MangroveMaterial extends BaseTreeMaterial {
	public final PottedBlock PROPAGULE;

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
		PROPAGULE = new PottedBlock(new PropaguleBlock(AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)));
	}
}
