package haven.materials;

import haven.blocks.basic.HavenSlabBlock;
import haven.blocks.basic.HavenStairsBlock;
import haven.blocks.basic.HavenWallBlock;
import haven.containers.BlockContainer;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.*;
import haven.sounds.HavenBlockSoundGroups;
import net.minecraft.block.*;

public class MudMaterial extends BaseMaterial implements PackedProvider,
		BricksProvider, BrickSlabProvider, BrickStairsProvider, BrickWallProvider {

	private final BlockContainer packed;
	public BlockContainer getPacked() { return packed; }
	private final BlockContainer bricks;
	public BlockContainer getBricks() { return bricks; }
	private final BlockContainer brick_slab;
	public BlockContainer getBrickSlab() { return brick_slab; }
	private final BlockContainer brick_stairs;
	public BlockContainer getBrickStairs() { return brick_stairs; }
	private final BlockContainer brick_wall;
	public BlockContainer getBrickWall() { return brick_wall; }

	public MudMaterial() {
		super("mud", false);
		packed = new BlockContainer(new Block(AbstractBlock.Settings.copy(Blocks.DIRT).strength(1.0f, 3.0f).sounds(HavenBlockSoundGroups.PACKED_MUD)));
		bricks = new BlockContainer(new Block(AbstractBlock.Settings.of(Material.STONE, MapColor.TERRACOTTA_LIGHT_GRAY).requiresTool().strength(1.5f, 3.0f).sounds(HavenBlockSoundGroups.MUD_BRICKS)));
		brick_slab = new BlockContainer(new HavenSlabBlock(bricks.BLOCK));
		brick_stairs = new BlockContainer(new HavenStairsBlock(bricks.BLOCK));
		brick_wall = new BlockContainer(new HavenWallBlock(bricks.BLOCK));
	}
}
