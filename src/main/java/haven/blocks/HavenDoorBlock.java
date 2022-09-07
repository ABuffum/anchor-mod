package haven.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.DoorBlock;

public class HavenDoorBlock extends DoorBlock {
	public HavenDoorBlock(Block block) {
		this(Settings.copy(block));
	}
	public HavenDoorBlock(Settings settings) { super(settings); }
}
